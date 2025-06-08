package sia.taskmanager.Services;

import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import sia.taskmanager.Models.EmailMessage;

@Component
@Slf4j
public class KafkaConsumerService {
    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "email-topic", groupId = "email-group")
    public void receiveEmail(EmailMessage emailMessage) {
        log.info("Received email message: " + emailMessage);
        try{
            emailService.sendEmail(emailMessage);
        } catch (MessagingException e) {
            log.error("Error sending email" + e.getMessage());
        }
    }

}
