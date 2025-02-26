package com.yourstech.springform.controller;

import com.yourstech.springform.dto.request.SubmitRequest;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.dto.response.SubmitDetailResponse;
import com.yourstech.springform.dto.response.SubmitResponse;
import com.yourstech.springform.service.SubmitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submit")
@RequiredArgsConstructor
public class SubmitController {
    private final SubmitService submitService;

    @PostMapping("/{formSlug}")
    public ResponseEntity<CommonResponse<SubmitResponse>> submitForm(
            @PathVariable String formSlug,
            @RequestBody SubmitRequest request) {
        return ResponseEntity.ok(submitService.submit(formSlug, request));
    }

    @GetMapping("/{formSlug}/all")
    public ResponseEntity<CommonResponse<List<SubmitDetailResponse>>> getAllSubmissions(
            @PathVariable String formSlug) {
        return ResponseEntity.ok(submitService.getAllSubmissions(formSlug));
    }
}
