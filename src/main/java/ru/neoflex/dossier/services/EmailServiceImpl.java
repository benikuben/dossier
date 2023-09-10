package ru.neoflex.dossier.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public String createFinishRegistrationMsg(Long applicationId) {
        return new StringBuilder()
                .append("Hello, your loan application №")
                .append(applicationId)
                .append(" approved!\nNow you should finish registration by the following link: http://localhost:8080/swagger-ui/index.html#/application/finishRegistration").toString();
    }


    @Override
    public String createCreateDocumentsMsg(Long applicationId) {
        return new StringBuilder()
                .append("Hello, your loan application №")
                .append(applicationId)
                .append(" passed all checks!\nNow you should send creating documents request by the following link: http://localhost:8080/swagger-ui/index.html#/document/sendDocuments").toString();
    }

    @Override
    public String createSendDocumentsMsg(Long applicationId) {
        return new StringBuilder()
                .append("Hello, here is your loan documents for application №")
                .append(applicationId)
                .append(".\nNow you should send signing documents request by the following link: http://localhost:8080/swagger-ui/index.html#/document/signDocuments").toString();
    }

    @Override
    public String createSendSesMsg(Long applicationId, String sesCode) {
        return new StringBuilder()
                .append("Hello, here is your Simple Electric code ")
                .append(sesCode)
                .append(" for your application №")
                .append(applicationId)
                .append(".\nNow you should send this code to: http://localhost:8080/swagger-ui/index.html#/document/verifyCode").toString();
    }

    @Override
    public String createCreditIssuedMsg(Long applicationId) {
        return new StringBuilder()
                .append("Hello, credit for your application №")
                .append(applicationId)
                .append(" has already issued!\nMoney will transfer to your account soon.\nThanks!").toString();
    }

    @Override
    public String createApplicationDeniedMsg(Long applicationId) {
        return new StringBuilder()
                .append("Hello, your loan application №")
                .append(applicationId)
                .append(" denied.").toString();
    }
}
