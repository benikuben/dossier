package ru.neoflex.dossier.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.neoflex.openapi.dtos.EmailMessage;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumerImpl implements KafkaConsumer {
    public static final String FINISH_REGISTRATION = "finish-registration";
    public static final String CREATE_DOCUMENTS = "create-documents";
    public static final String SEND_DOCUMENTS = "send-documents";
    public static final String SEND_SES = "send-ses";
    public static final String CREDIT_ISSUED = "credit-issued";
    public static final String APPLICATION_DENIED = "application-denied";
    private final EmailSender emailSender;

    @Override
    @KafkaListener(groupId = "dossier",
            topics = {FINISH_REGISTRATION, CREATE_DOCUMENTS, SEND_DOCUMENTS, SEND_SES, CREDIT_ISSUED, APPLICATION_DENIED})
    public void consumeMessage(EmailMessage emailMessage) {
        log.info("Message consumed {}", emailMessage);

        emailSender.send(emailMessage);
    }
}

