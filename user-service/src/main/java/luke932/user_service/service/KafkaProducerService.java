package luke932.user_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static final String TOPIC_USER_CREATED = "user_created";
    private static final String TOPIC_USER_UPDATED = "user_updated";
    private static final String TOPIC_USER_EVENT = "user_event";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendUserCreatedEvent(String userJson) {
        kafkaTemplate.send(TOPIC_USER_CREATED, userJson);
    }

    public void sendUserUpdatedEvent(String userJson) {
        kafkaTemplate.send(TOPIC_USER_UPDATED, userJson);
    }
    
    public void sendUserEvent(String userJson, String eventType) {
        String topic = TOPIC_USER_EVENT + "_" + eventType;
        kafkaTemplate.send(topic, userJson);
    }
}

