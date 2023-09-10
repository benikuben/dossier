package ru.neoflex.dossier.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.neoflex.openapi.dtos.Application;
import ru.neoflex.openapi.dtos.Client;
import ru.neoflex.openapi.dtos.Credit;
import ru.neoflex.openapi.dtos.Passport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DocumentServiceTest {
    @Autowired
    private DocumentService documentService;

    @Test
    void createDocuments() {
        Application application = new Application()
                .clientId(new Client().passport(new Passport()))
                .creditId(new Credit().paymentSchedule(new ArrayList<>()));
        List<File> docs= documentService.createDocuments(application, 1L);
        assertEquals(docs.size(), 3);
    }
}