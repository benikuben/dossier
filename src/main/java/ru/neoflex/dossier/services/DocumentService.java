package ru.neoflex.dossier.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.neoflex.openapi.dtos.ApplicationDTO;
import ru.neoflex.openapi.dtos.ClientDTO;
import ru.neoflex.openapi.dtos.CreditDTO;
import ru.neoflex.openapi.dtos.PaymentScheduleElement;

import java.io.*;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class DocumentService {
    private final static String TXT_EXTENSION = ".TXT";

    public List<File> createDocuments(ApplicationDTO application, Long id) {
        log.info("ApplicationDTO - {}", application);
        ClientDTO client = application.getClientId();
        CreditDTO credit = application.getCreditId();
        LocalDateTime creationDate = application.getCreationDate();

        return List.of(
                createCreditApplicationFile(client, id, creationDate),
                createCreditContractFile(client, credit, id, creationDate),
                createCreditPaymentScheduleFile(credit.getPaymentSchedule(), id)
        );
    }

    private File createCreditApplicationFile(ClientDTO client, Long applicationId, LocalDateTime creationDate) {
        File file;
        try {
            file = File.createTempFile("CreditApplication", TXT_EXTENSION);
            FileWriter writer = new FileWriter(file);
            String text = createCreditApplicationText(client, applicationId, creationDate);
            log.info("CreditApplication document text - {}", text);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            log.warn("IOException during writing to file CreditApplication {}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("CreditApplication document created");
        return file;
    }

    private String createCreditApplicationText(ClientDTO client, Long applicationId, LocalDateTime creationDate) {
        return new StringBuilder()
                .append("Credit application ").append(applicationId).append(" from ").append(creationDate)
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

    private File createCreditContractFile(ClientDTO client, CreditDTO credit, Long applicationId, LocalDateTime creationDate) {
        File file;
        try {
            file = File.createTempFile("CreditContract", TXT_EXTENSION);

            FileWriter writer = new FileWriter(file);
            String text = createCreditContractText(client, credit, applicationId, creationDate);
            log.info("CreditContract document text - {}", text);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            log.warn("IOException during writing to file CreditContract {}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("CreditContract document created");
        return file;
    }

    private String createCreditContractText(ClientDTO client, CreditDTO credit, Long applicationId, LocalDateTime creationDate) {
        return new StringBuilder()
                .append("Credit contract ").append(applicationId).append(" from ").append(creationDate)
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

    private File createCreditPaymentScheduleFile(List<PaymentScheduleElement> payments, Long applicationId) {
        File file;
        try {
            file = File.createTempFile("CreditPaymentSchedule", TXT_EXTENSION);
            FileWriter writer = new FileWriter(file);
            String text = payments.toString();
            log.info("CreditPaymentSchedule document text - {}", text);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            log.warn("IOException during writing to file CreditPaymentSchedule {}", e.getMessage());
            throw new RuntimeException(e);
        }
        log.info("CreditPaymentSchedule document created");
        return file;
    }
}
