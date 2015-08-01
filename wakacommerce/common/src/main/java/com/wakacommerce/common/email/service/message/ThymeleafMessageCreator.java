
package com.wakacommerce.common.email.service.message;

import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.wakacommerce.common.email.service.info.EmailInfo;
import com.wakacommerce.common.web.WakaRequestContext;

import java.util.Iterator;
import java.util.Map;

public class ThymeleafMessageCreator extends MessageCreator {

    private TemplateEngine templateEngine;
    
    public ThymeleafMessageCreator(TemplateEngine templateEngine, JavaMailSender mailSender) {
        super(mailSender);
        this.templateEngine = templateEngine;        
    }

    @Override
    public String buildMessageBody(EmailInfo info, Map<String,Object> props) {
        WakaRequestContext blcContext = WakaRequestContext.getWakaRequestContext();
        
        final Context thymeleafContext = new Context();
        if (blcContext != null && blcContext.getJavaLocale() != null) {
            thymeleafContext.setLocale(blcContext.getJavaLocale());             
        }           
        
        if (props != null) {
            Iterator<String> propsIterator = props.keySet().iterator();
            while(propsIterator.hasNext()) {
                String key = propsIterator.next();
                thymeleafContext.setVariable(key, props.get(key));
            }
        }
        
        return this.templateEngine.process( info.getEmailTemplate(), thymeleafContext); 
    }
}
