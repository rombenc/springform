package com.yourstech.springform.controller;

import com.yourstech.springform.dto.request.FormRequest;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.dto.response.FormDetailResponse;
import com.yourstech.springform.dto.response.FormResponse;
import com.yourstech.springform.service.FormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class FormController {
    private final FormService formService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommonResponse<FormResponse>> login(@Valid @RequestBody FormRequest request) {
        CommonResponse<FormResponse> response = formService.createForm(request);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<FormResponse>>> getAllForms() {
        CommonResponse<List<FormResponse>> response = formService.getAllForms();
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<CommonResponse<FormDetailResponse>> getFormBySlug(@PathVariable String slug) {
        CommonResponse<FormDetailResponse> response = formService.getFormBySlug(slug);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{slug}")
    public ResponseEntity<CommonResponse<Void>> deleteForm(@PathVariable String slug) {
        CommonResponse<Void> response = formService.deleteForm(slug);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}




