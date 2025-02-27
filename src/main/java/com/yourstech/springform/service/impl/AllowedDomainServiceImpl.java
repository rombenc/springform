package com.yourstech.springform.service.impl;

import com.yourstech.springform.dto.request.AllowedDomainRequest;
import com.yourstech.springform.dto.response.AllowedDomainResponse;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.model.AllowedDomain;
import com.yourstech.springform.model.Form;
import com.yourstech.springform.repository.AllowedDomainRepository;
import com.yourstech.springform.repository.FormRepository;
import com.yourstech.springform.service.AllowedDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllowedDomainServiceImpl implements AllowedDomainService {
    private final FormRepository formRepository;
    private final AllowedDomainRepository allowedDomainRepository;

    @Transactional
    @Override
    public CommonResponse<List<AllowedDomainResponse>> addAllowedDomains(String formSlug, AllowedDomainRequest allowedDomainRequest) {
        Form form = formRepository.findBySlug(formSlug)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        List<AllowedDomain> newAllowedDomains = allowedDomainRequest.getDomain().stream()
                .map(domain -> AllowedDomain.builder()
                        .domain(domain)
                        .form(form)
                        .build())
                .toList();

        // Simpan di database
        allowedDomainRepository.saveAll(newAllowedDomains);

        return CommonResponse.<List<AllowedDomainResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Allowed domains added successfully")
                .data(newAllowedDomains.stream()
                        .map(domain -> new AllowedDomainResponse(domain.getId(), domain.getDomain()))
                        .toList())
                .build();
    }

    @Override
    public CommonResponse<List<AllowedDomainResponse>> getAllowedDomains(String formSlug) {
        Form form = formRepository.findBySlug(formSlug)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        List<AllowedDomainResponse> response = form.getAllowedDomains().stream()
                .map(domain -> new AllowedDomainResponse(domain.getId(), domain.getDomain()))
                .toList();

        return CommonResponse.<List<AllowedDomainResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Allowed domains retrieved successfully")
                .data(response)
                .build();
    }


    @Transactional
    @Override
    public CommonResponse<Void> removeAllowedDomains(String formSlug, AllowedDomainRequest allowedDomainRequest) {
        Form form = formRepository.findBySlug(formSlug)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        // Ambil list domain yang akan dihapus
        List<AllowedDomain> toRemove = form.getAllowedDomains().stream()
                .filter(domain -> allowedDomainRequest.getDomain().contains(domain.getDomain()))
                .toList();

        if (toRemove.isEmpty()) {
            throw new RuntimeException("No matching allowed domains found");
        }

        allowedDomainRepository.deleteAll(toRemove);

        return CommonResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Allowed domains removed successfully")
                .build();
    }

}

