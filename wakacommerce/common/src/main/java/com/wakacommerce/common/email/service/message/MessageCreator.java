

package com.wakacommerce.common.email.service.message;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.wakacommerce.common.email.domain.EmailTarget;
import com.wakacommerce.common.email.service.info.EmailInfo;

import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;

public abstract class MessageCreator {

    private JavaMailSender mailSender;

    public MessageCreator(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMessage(final Map<String, Object> props) throws MailException {
        MimeMessagePreparator preparator = buildMimeMessagePreparator(props);
        this.mailSender.send(preparator);
    }

    public abstract String buildMessageBody(EmailInfo info, Map<String, Object> props);

    public MimeMessagePreparator buildMimeMessagePreparator(final Map<String, Object> props) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {

            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                EmailTarget emailUser = (EmailTarget) props.get(EmailPropertyType.USER.getType());
                EmailInfo info = (EmailInfo) props.get(EmailPropertyType.INFO.getType());
                boolean isMultipart = CollectionUtils.isNotEmpty(info.getAttachments());
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, info.getEncoding());
                message.setTo(emailUser.getEmailAddress());
                message.setFrom(info.getFromAddress());
                message.setSubject(info.getSubject());
                if (emailUser.getBCCAddresses() != null && emailUser.getBCCAddresses().length > 0) {
                    message.setBcc(emailUser.getBCCAddresses());
                }
                if (emailUser.getCCAddresses() != null && emailUser.getCCAddresses().length > 0) {
                    message.setCc(emailUser.getCCAddresses());
                }
                String messageBody = info.getMessageBody();
                if (messageBody == null) {
                    messageBody = buildMessageBody(info, props);
                }
                message.setText(messageBody, true);
                for (Attachment attachment : info.getAttachments()) {
                    ByteArrayDataSource dataSource = new ByteArrayDataSource(attachment.getData(), attachment.getMimeType());
                    message.addAttachment(attachment.getFilename(), dataSource);
                }
            }
        };
        return preparator;

    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
}
