package sia.taskmanager.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sia.taskmanager.Models.EmailMessage;

@Service
@Slf4j
public class KafkaProducerService {
    @Autowired
    private KafkaTemplate<String, EmailMessage> kafkaTemplate;

    public void sendEmailMessage(EmailMessage emailMessage) {
        kafkaTemplate.send("email-topic", emailMessage.getTo(), emailMessage).whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("Email sent to " + emailMessage.getTo());
            }
            else {
                log.error("Failed to send email to " + emailMessage.getTo(), exception);
            }
        });
    }
}
