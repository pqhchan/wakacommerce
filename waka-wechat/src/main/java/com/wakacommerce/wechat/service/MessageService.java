package com.wakacommerce.wechat.service;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import com.wakacommerce.wechat.WechatMessageHandler;
import com.wakacommerce.wechat.WakaAsstConfig;
import com.wakacommerce.wechat.WakaAsstException;
import com.wakacommerce.wechat.WeChatReqMsgType;
import com.wakacommerce.wechat.WeChatRespMsgType;
import com.wakacommerce.wechat.domain.base.BaseRequestMessage;
import com.wakacommerce.wechat.domain.base.BaseResponseMessage;
import com.wakacommerce.wechat.domain.response.ImageResponseMessage;
import com.wakacommerce.wechat.domain.response.MusicResponseMessage;
import com.wakacommerce.wechat.domain.response.PicDescResponseMessage;
import com.wakacommerce.wechat.domain.response.TextResponseMessage;
import com.wakacommerce.wechat.domain.response.VideoResponseMessage;
import com.wakacommerce.wechat.domain.response.VoiceResponseMessage;

@Service
public class MessageService {
	
	private static final Logger log = Logger.getLogger(MessageService.class);
	
	protected static Map<WeChatReqMsgType, TreeSet<WechatMessageHandler>> handlers;
	
	static {
		handlers = new HashMap<WeChatReqMsgType, TreeSet<WechatMessageHandler>>();
		loadHandles();
	}
	
	public BaseRequestMessage parseXML(String xml) throws DocumentException, WakaAsstException {
		Element ele = DocumentHelper.parseText(xml).getRootElement();
		String msgType = null;
		if ((msgType = ele.elementText("MsgType")) == null) {
			throw new WakaAsstException("cannot find MsgType Node!\n" + xml);
		}
		
		WeChatReqMsgType msgTypeEnum = WeChatReqMsgType.inst(msgType);
		switch (msgTypeEnum) {
		case EVENT:
			return MessageHelper.parseEventReqMsg(ele);
		case IMAGE:
			return MessageHelper.parseImageReqMsg(ele);
		case LINK:
			return MessageHelper.parseLinkReqMsg(ele);
		case LOCATION:
			return MessageHelper.parseLocationReqMsg(ele);
		case TEXT:
			return MessageHelper.parseTextReqMsg(ele);
		case VIDEO:
			return MessageHelper.parseVideoReqMsg(ele);
		case VOICE:
			return MessageHelper.parseVoiceReqMsg(ele);
		default:
			// never happens
			break;
		}
		return null;
	}
	
	public BaseResponseMessage handleReqMsg(BaseRequestMessage msg) {
		TreeSet<WechatMessageHandler> handlerList = handlers.get(WeChatReqMsgType.inst(msg.getMsgType()));
		
		if(handlerList == null) {
			return defaultResult(msg.getToUserName(), msg.getFromUserName());
		}
		
		Map<String, Object> context = new HashMap<String, Object>();
		BaseResponseMessage result = null;
		for (WechatMessageHandler handler : handlerList) {
			result = handler.handle(msg, context);
		}
		
		if (result == null) {
			result = defaultResult(msg.getToUserName(), msg.getFromUserName());
		}
		return result;
	}
	
	public Element genXmlForRespMsg(BaseResponseMessage resp) throws DocumentException {
		WeChatRespMsgType type = WeChatRespMsgType.inst(resp.getMsgType());
		switch (type) {
		case IMAGE:
			return MessageHelper.doGenXmlForRespMsg((ImageResponseMessage) resp);
		case MUSIC:
			return MessageHelper.doGenXmlForRespMsg((MusicResponseMessage) resp, ((MusicResponseMessage) resp).getThumb());
		case NEWS:
			return MessageHelper.doGenXmlForRespMsg((PicDescResponseMessage) resp);
		case TEXT:
			return MessageHelper.doGenXmlForRespMsg((TextResponseMessage) resp);
		case VIDEO:
			return MessageHelper.doGenXmlForRespMsg((VideoResponseMessage) resp);
		case VOICE:
			return MessageHelper.doGenXmlForRespMsg((VoiceResponseMessage) resp);
		default:
			break;
		}
		return null;
	}
	
	protected TextResponseMessage defaultResult(String fromUserName, String toUserName) {
		TextResponseMessage result = new TextResponseMessage();
		result.setContent("功能菜单\n"+"1. 课表\n"+"2. 身份证\n" + "3. �?卡�?�\n" + "4. whoami\n");
		result.setCreatedDate(new Date());
		result.setMsgCreatedTime(new Date().getTime() / 1000);
		result.setFromUserName(fromUserName);
		result.setMsgType(WakaAsstConfig.WeChatRespMsgType.TEXT);
		result.setToUserName(toUserName);
		return result;
	}
	
	private static void loadHandles() {
		try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(MessageService.class.getResourceAsStream("/wakaasst-handlers.xml"));
            Element list = document.getRootElement();
            @SuppressWarnings("unchecked")
			List<Element> elements = list.elements();
            for (Element element : elements) {
                if (element.getName().equals("bean")) {
                    Attribute className = element.attribute("class");
                    log.debug(className.getStringValue());
                    
                    WechatMessageHandler handler = loadClass(className.getStringValue());
                    WeChatReqMsgType[] types = handler.support();
                    for(WeChatReqMsgType type : types){
                    	if(!handlers.containsKey(type)){
                    		handlers.put(type, new TreeSet<WechatMessageHandler>(new MessageHandlerComparator()));
                    	}
                    	handlers.get(type).add(handler);
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
    }

    private static WechatMessageHandler loadClass(String className) {
        try {
            Class<?> ins = Class.forName(className);
            return (WechatMessageHandler) ins.newInstance();
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

}

class MessageHandlerComparator implements Comparator<WechatMessageHandler> {

	public int compare(WechatMessageHandler o1, WechatMessageHandler o2) {
		if (o1.priority() > o2.priority()) {
			return -1;
		} else if (o1.priority() < o2.priority()) {
			return 1;
		} else {
			return 0;
		}
	}
	
}
