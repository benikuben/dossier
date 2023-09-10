package ru.neoflex.dossier.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import ru.neoflex.dossier.exceptions.DossierException;
import ru.neoflex.dossier.feignclient.DealClient;
import ru.neoflex.openapi.dtos.Application;
import ru.neoflex.openapi.dtos.EmailMessage;
import ru.neoflex.openapi.dtos.Theme;

import java.io.File;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailSenderImpl implements EmailSender {
    public static final String FINISH_REGISTRATION_SUBJECT = "Finish registration in credit-conveyor";
    public static final String CREATE_DOCUMENTS_SUBJECT = "Create documents for your loan application";
    public static final String SEND_DOCUMENTS_SUBJECT = "Your loan documents";
    public static final String SEND_SES_SUBJECT = "Your simple electric sign";
    public static final String CREDIT_ISSUED_SUBJECT = "Credit issued";
    public static final String APPLICATION_DENIED_SUBJECT = "Application denied";
    private final EmailService emailService;
    private final DocumentService documentService;
    private final DealClient dealClient;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void send(EmailMessage emailMessage) {
        Long id = emailMessage.getApplicationId();

        Theme currentTheme = emailMessage.getTheme();
        switch (currentTheme) {
            case FINISH_REGISTRATION -> {
                sendMail(emailMessage.getAddress(),
                        FINISH_REGISTRATION_SUBJECT,
                        emailService.createFinishRegistrationMsg(id));
                log.info("Finish registration email was sent to {}", emailMessage.getAddress());
            }
            case CREATE_DOCUMENTS -> {
                sendMail(emailMessage.getAddress(),
                        CREATE_DOCUMENTS_SUBJECT,
                        emailService.createCreateDocumentsMsg(id));
                log.info("Create documents email was sent to {}", emailMessage.getAddress());
            }
            case SEND_DOCUMENTS -> {
                Application application = dealClient.getApplicationById(id).getBody();
                sendMailWithAttachments(emailMessage.getAddress(),
                        SEND_DOCUMENTS_SUBJECT,
                        emailService.createSendDocumentsMsg(id),
                        documentService.createDocuments(application, id));
                log.info("Send documents email was sent to {}", emailMessage.getAddress());

                dealClient.updateApplicationStatus(id, "DOCUMENT_CREATED");
                log.info("Sending request to /deal/admin/application/{}/status. Updating status to DOCUMENT_CREATED", id);
            }
            case SEND_SES -> {
                Application application = dealClient.getApplicationById(id).getBody();
                sendMail(emailMessage.getAddress(),
                        SEND_SES_SUBJECT,
                        emailService.createSendSesMsg(id, application.getSesCode()));
                log.info("Send ses email was sent to {}", emailMessage.getAddress());
            }
            case CREDIT_ISSUED -> {
                sendMail(emailMessage.getAddress(),
                        CREDIT_ISSUED_SUBJECT,
                        emailService.createCreditIssuedMsg(id));
                log.info("Credit issued email was sent to {}", emailMessage.getAddress());
            }
            case APPLICATION_DENIED -> {
                sendMail(emailMessage.getAddress(),
                        APPLICATION_DENIED_SUBJECT,
                        emailService.createApplicationDeniedMsg(id));
                log.info("Application denied email was sent to {}", emailMessage.getAddress());
            }
            default -> log.warn("{} - unhandled theme enum constant", currentTheme);
        }
    }

    private void sendMail(String address, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(sender);
        mailMessage.setTo(address);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        javaMailSender.send(mailMessage);
        log.info("Message {} sent to {}", message, address);
    }

    private void sendMailWithAttachments(String address, String subject, String message, List<File> docs) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(sender);
            helper.setTo(address);
            helper.setSubject(subject);
            helper.setText(message);
            for (File doc : docs) {
                helper.addAttachment(doc.getName(), doc);
            }

            javaMailSender.send(mimeMessage);
            log.info("Message {} sent to {}", message, address);
        } catch (MessagingException e) {
            log.warn("MessagingException {}", e.getMessage());
            throw new DossierException();
        }
    }
}
