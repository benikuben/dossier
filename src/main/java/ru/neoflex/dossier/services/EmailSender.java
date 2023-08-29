package ru.neoflex.dossier.services;

import ru.neoflex.openapi.dtos.EmailMessage;

public interface EmailSender {
    void send(EmailMessage emailMessage);
}
