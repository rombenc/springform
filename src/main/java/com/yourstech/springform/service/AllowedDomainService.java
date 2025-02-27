package com.yourstech.springform.service;

import com.yourstech.springform.dto.request.AllowedDomainRequest;
import com.yourstech.springform.dto.response.AllowedDomainResponse;
import com.yourstech.springform.dto.response.CommonResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AllowedDomainService {

    @Transactional
    CommonResponse<List<AllowedDomainResponse>> addAllowedDomains(String formSlug, AllowedDomainRequest allowedDomainRequest);

    CommonResponse<List<AllowedDomainResponse>> getAllowedDomains(String formSlug);

    @Transactional
    CommonResponse<Void> removeAllowedDomains(String formSlug, AllowedDomainRequest allowedDomainRequest);
}