package com.wakacommerce.wechat.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wakacommerce.wechat.WakaAsstConfig;
import com.wakacommerce.wechat.WeChatReqMsgType;
import com.wakacommerce.wechat.WechatMessageHandler;
import com.wakacommerce.wechat.domain.base.BaseRequestMessage;
import com.wakacommerce.wechat.domain.base.BaseResponseMessage;
import com.wakacommerce.wechat.domain.item.PicDescItemEntity;
import com.wakacommerce.wechat.domain.request.TextRequestMessage;
import com.wakacommerce.wechat.domain.response.PicDescResponseMessage;

public class MyTextHandler implements WechatMessageHandler {

	@Override
	public WeChatReqMsgType[] support() {
		return new WeChatReqMsgType[]{WeChatReqMsgType.TEXT};
	}

	@Override
	public BaseResponseMessage handle(BaseRequestMessage msg, Map<String, Object> context) {
		
		TextRequestMessage message = (TextRequestMessage) msg;
		if(message.getContent().equals("我的商城") || message.getContent().equals("1")) {
			
			PicDescResponseMessage resp = new PicDescResponseMessage();
			resp.setFromUserName(message.getToUserName());
			resp.setToUserName(message.getFromUserName());
			resp.setCreatedDate(new Date());
			resp.setMsgCreatedTime(new Date().getTime() / 1000);
			resp.setMsgType(WakaAsstConfig.WeChatRespMsgType.NEWS);
			List<PicDescItemEntity> articles = new ArrayList<PicDescItemEntity>();
			PicDescItemEntity article = new PicDescItemEntity();
			article.setPicUrl("");
			article.setUrl(new WakaAsstConfig().getSitefront());
			article.setTitle("");
			article.setDescription("我的商城");
			articles.add(article);
			resp.setArticles(articles);
			
			return resp;
		}
		
		return null;
	}

	@Override
	public Integer priority() {
		return -1;
	}

}
