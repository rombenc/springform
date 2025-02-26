package com.yourstech.springform.service;

import com.yourstech.springform.dto.request.SubmitRequest;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.dto.response.SubmitDetailResponse;
import com.yourstech.springform.dto.response.SubmitResponse;

import java.util.List;

public interface SubmitService {
    CommonResponse<SubmitResponse> submit(String formSlug, SubmitRequest request);
    CommonResponse<List<SubmitDetailResponse>> getAllSubmissions(String formSlug);
}
