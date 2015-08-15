package com.wakacommerce.wechat;

import java.util.Map;

import com.wakacommerce.wechat.domain.base.BaseRequestMessage;
import com.wakacommerce.wechat.domain.base.BaseResponseMessage;

public interface WechatMessageHandler {
	
	WeChatReqMsgType[] support();
	
	BaseResponseMessage handle(BaseRequestMessage msg, Map<String, Object> context);
	
	Integer priority();
	
}
