package ru.neoflex.dossier.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.dossier.exceptions.DossierException;
import ru.neoflex.openapi.dtos.Application;
import ru.neoflex.openapi.dtos.Client;
import ru.neoflex.openapi.dtos.Credit;

import java.io.*;
import java.util.List;

@Slf4j
@Service
public class DocumentServiceImpl implements DocumentService {
    private final static String TXT_EXTENSION = ".TXT";

    @Override
    public List<File> createDocuments(Application application, Long id) {
        log.info("ApplicationDTO - {}", application);
        return List.of(
                createFile(application, id, "CreditApplication"),
                createFile(application, id, "CreditContract"),
                createFile(application, id, "CreditPaymentSchedule")
        );
    }

    private File createFile(Application application, Long id, String docName) {
        File file;
        try {
            file = File.createTempFile(docName, TXT_EXTENSION);

            try (FileWriter writer = new FileWriter(file)) {
                String text;
                switch (docName) {
                    case "CreditApplication" -> text = createCreditApplicationText(application, id);
                    case "CreditContract" -> text = createCreditContractText(application, id);
                    case "CreditPaymentSchedule" -> text = application.getCreditId().getPaymentSchedule().toString();
                    default -> {
                        log.warn("{} - unhandled document name", docName);
                        text = "";
                    }
                }
                log.info("CreditApplication document text - {}", text);
                writer.write(text);
            }
        } catch (IOException e) {
            log.warn("IOException during writing to file CreditApplication {}", e.getMessage());
            throw new DossierException();
        }
        log.info("{} document created", docName);
        return file;
    }

    private String createCreditApplicationText(Application application, Long applicationId) {
        Client client = application.getClientId();
        return new StringBuilder()
                .append("Credit application ").append(applicationId).append(" from ").append(application.getCreationDate())
                .append("\nClient info:")
                .append("\nClient's full name: ").append(client.getLastName()).append(" ")
                .append(client.getFirstName()).append(" ")
                .append(client.getMiddleName() != null ? client.getMiddleName() : "")
                .append("\nBirthdate: " + client.getBirthdate())
                .append("\nGender: " + client.getGender())
                .append("\nPassport: ").append(client.getPassport().getSeries()).append(client.getPassport().getNumber())
                .append(" issued ").append(client.getPassport().getIssueDate())
                .append(" branch code ").append(client.getPassport().getIssueBranch())
                .append("\nEmail: ").append(client.getEmail())
                .append("\nMarital status: ").append(client.getMaritalStatus())
                .append("\nDependent amount: ").append(client.getDependentAmount())
                .append("\nEmployment: ").append(client.getEmployment()).toString();
    }

    private String createCreditContractText(Application application, Long applicationId) {
        Credit credit = application.getCreditId();
        Client client = application.getClientId();
        return new StringBuilder()
                .append("Credit contract ").append(applicationId).append(" from ").append(application.getCreationDate())
                .append("\nClient's full name: ").append(client.getLastName()).append(" ")
                .append(client.getFirstName()).append(" ")
                .append(client.getMiddleName() != null ? client.getMiddleName() : "")
                .append("\nClient's passport: ").append(client.getPassport().getSeries()).append(client.getPassport().getNumber())
                .append(" issued ").append(client.getPassport().getIssueDate())
                .append(" branch code ").append(client.getPassport().getIssueBranch())
                .append("\nCredit info:")
                .append("\nAmount: ").append(credit.getAmount())
                .append("\nTerm: ").append(credit.getTerm())
                .append("\nMonthly payment: ").append(credit.getMonthlyPayment())
                .append("\nRate: ").append(credit.getRate())
                .append("\nPSK: ").append(credit.getPsk())
                .append("\nServices:")
                .append("\nInsurance: ").append(credit.getIsInsuranceEnabled())
                .append("\nSalary client: ").append(credit.getIsSalaryClient()).toString();
    }
}
