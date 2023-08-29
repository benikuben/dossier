package ru.neoflex.dossier.services;

public interface EmailService {
    String createFinishRegistrationMsg(Long applicationId);

    String createCreateDocumentsMsg(Long applicationId);

    String createSendDocumentsMsg(Long applicationId);

    String createSendSesMsg(Long applicationId, String sesCode);

    String createCreditIssuedMsg(Long applicationId);

    String createApplicationDeniedMsg(Long applicationId);
}
