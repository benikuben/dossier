package ru.neoflex.dossier.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailServiceTest {
    @Autowired
    private EmailService emailService;

    @Test
    void createFinishRegistrationMsg() {
        String expected = "Hello, your loan application №1 approved!\nNow you should finish registration by the following link: http://localhost:8080/swagger-ui/index.html#/application/finishRegistration";
        String actual = emailService.createFinishRegistrationMsg(1L);
        assertEquals(actual, expected);
    }

    @Test
    void createCreateDocumentsMsg() {
        String expected = "Hello, your loan application №1 passed all checks!\nNow you should send creating documents request by the following link: http://localhost:8080/swagger-ui/index.html#/document/sendDocuments";
        String actual = emailService.createCreateDocumentsMsg(1L);
        assertEquals(actual, expected);
    }

    @Test
    void createSendDocumentsMsg() {
        String expected = "Hello, here is your loan documents for application №1.\nNow you should send signing documents request by the following link: http://localhost:8080/swagger-ui/index.html#/document/signDocuments";
        String actual = emailService.createSendDocumentsMsg(1L);
        assertEquals(actual, expected);
    }

    @Test
    void createSendSesMsg() {
        String expected = "Hello, here is your Simple Electric code 1111 for your application №1.\nNow you should send this code to: http://localhost:8080/swagger-ui/index.html#/document/verifyCode";
        String actual = emailService.createSendSesMsg(1L, String.valueOf(1111));
        assertEquals(actual, expected);
    }

    @Test
    void createCreditIssuedMsg() {
        String expected = "Hello, credit for your application №1 has already issued!\nMoney will transfer to your account soon.\nThanks!";
        String actual = emailService.createCreditIssuedMsg(1L);
        assertEquals(actual, expected);
    }

    @Test
    void createApplicationDeniedMsg() {
        String expected = "Hello, your loan application №1 denied.";
        String actual = emailService.createApplicationDeniedMsg(1L);
        assertEquals(actual, expected);
    }
}