package com.yourstech.springform.service;

import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.model.AllowedDomain;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AllowedDomainService {
    CommonResponse<List<AllowedDomain>> addAllowedDomains(String formSlug, List<AllowedDomain> domains);
    CommonResponse<List<AllowedDomain>> getAllowedDomains(String formSlug);
    @Transactional
    CommonResponse<Void> removeAllowedDomains(String formSlug, List<AllowedDomain> domains);
}