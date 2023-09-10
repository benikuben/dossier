package ru.neoflex.dossier.services;

import ru.neoflex.openapi.dtos.Application;

import java.io.File;
import java.util.List;

public interface DocumentService {
    List<File> createDocuments(Application application, Long id);
}
