package com.yourstech.springform.controller;

import com.yourstech.springform.dto.request.AllowedDomainRequest;
import com.yourstech.springform.dto.response.AllowedDomainResponse;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.service.AllowedDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/forms/{formSlug}/allowed-domains")
@RequiredArgsConstructor
public class AllowedDomainController {
    private final AllowedDomainService allowedDomainService;

    @PostMapping
    public ResponseEntity<CommonResponse<List<AllowedDomainResponse>>> addAllowedDomains(
            @PathVariable String formSlug,
            @RequestBody AllowedDomainRequest domains) {
        CommonResponse<List<AllowedDomainResponse>> response = allowedDomainService.addAllowedDomains(formSlug, domains);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<AllowedDomainResponse>>> getAllowedDomains(@PathVariable String formSlug) {
        CommonResponse<List<AllowedDomainResponse>> response = allowedDomainService.getAllowedDomains(formSlug);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> removeAllowedDomains(
            @PathVariable String formSlug,
            @RequestBody AllowedDomainRequest domains) {
        CommonResponse<Void> response = allowedDomainService.removeAllowedDomains(formSlug, domains);
        return ResponseEntity.ok(response);
    }
}