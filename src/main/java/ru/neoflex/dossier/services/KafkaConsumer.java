package ru.neoflex.dossier.services;

import org.springframework.kafka.annotation.KafkaListener;
import ru.neoflex.openapi.dtos.EmailMessage;

public interface KafkaConsumer {
    @KafkaListener(groupId = "dossier",
            topics = {KafkaConsumerImpl.FINISH_REGISTRATION, KafkaConsumerImpl.CREATE_DOCUMENTS, KafkaConsumerImpl.SEND_DOCUMENTS, KafkaConsumerImpl.SEND_SES, KafkaConsumerImpl.CREDIT_ISSUED, KafkaConsumerImpl.APPLICATION_DENIED})
    void consumeMessage(EmailMessage emailMessage);
}
