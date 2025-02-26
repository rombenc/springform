package com.yourstech.springform.service.impl;

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
    public CommonResponse<List<AllowedDomain>> addAllowedDomains(String formSlug, List<AllowedDomain> domains) {
        Form form = formRepository.findBySlug(formSlug)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        form.getAllowedDomains().addAll(domains);
        formRepository.save(form);

        return CommonResponse.<List<AllowedDomain>>builder()
                .status(HttpStatus.OK.value())
                .message("Allowed domains added successfully")
                .data(form.getAllowedDomains())
                .build();
    }

    @Override
    public CommonResponse<List<AllowedDomain>> getAllowedDomains(String formSlug) {
        Form form = formRepository.findBySlug(formSlug)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        return CommonResponse.<List<AllowedDomain>>builder()
                .status(HttpStatus.OK.value())
                .message("Allowed domains retrieved successfully")
                .data(form.getAllowedDomains())
                .build();
    }

    @Transactional
    @Override
    public CommonResponse<Void> removeAllowedDomains(String formSlug, List<AllowedDomain> domains) {
        Form form = formRepository.findBySlug(formSlug)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        form.getAllowedDomains().removeAll(domains);
        formRepository.save(form);

        return CommonResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Allowed domains removed successfully")
                .build();
    }
}