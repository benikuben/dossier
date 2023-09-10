package ru.neoflex.dossier.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.neoflex.openapi.dtos.Application;

@FeignClient(name = "deal", url = "${deal.url}", path = "/deal")
public interface DealClient {
    @GetMapping("/admin/application/{applicationId}")
    ResponseEntity<Application> getApplicationById(@PathVariable("applicationId") Long applicationId);

    @PutMapping("/admin/application/{applicationId}/status")
    ResponseEntity<Void> updateApplicationStatus(@PathVariable("applicationId") Long applicationId, @RequestBody String body);
}
