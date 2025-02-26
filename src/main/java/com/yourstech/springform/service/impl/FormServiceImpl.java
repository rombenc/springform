package com.yourstech.springform.service.impl;

import com.yourstech.springform.dto.request.FormRequest;
import com.yourstech.springform.dto.response.CommonResponse;
import com.yourstech.springform.dto.response.FormDetailResponse;
import com.yourstech.springform.dto.response.FormResponse;
import com.yourstech.springform.dto.response.QuestionResponse;
import com.yourstech.springform.mapper.FormMapper;
import com.yourstech.springform.model.Form;
import com.yourstech.springform.repository.FormRepository;
import com.yourstech.springform.service.FormService;
import com.yourstech.springform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormServiceImpl implements FormService {
    private final FormRepository formRepository;
    private final UserService userService;
    private final FormMapper mapper;

    @Override
    public CommonResponse<FormResponse> createForm(FormRequest request) {
        var user = userService.getLoginUser();

        if (formRepository.existsBySlug(request.getSlug())) {
            throw new RuntimeException("Slug has already been taken.");
        }

        if (!request.getSlug().matches("^[a-zA-Z0-9.-]+$")) {
            throw new RuntimeException("Slug must be alphanumeric with only '-' and '.'");
        }

        var form = Form.builder()
                .user(user)
                .slug(request.getSlug())
                .name(request.getName())
                .description(request.getDescription())
                .limitOneResponse(request.getLimitOneResponse())
                .build();
        var saved = formRepository.save(form);
        return CommonResponse.<FormResponse>builder()
                .status(201)
                .message("Create form success")
                .data(mapper.form(saved))
                .build();
    }

    @Override
    public CommonResponse<List<FormResponse>> getAllForms() {
        List<FormResponse> forms = formRepository.findAll().stream()
                .map(mapper::form)
                .toList();

        return CommonResponse.<List<FormResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get all forms success")
                .data(forms)
                .build();
    }

    @Override
    public CommonResponse<FormDetailResponse> getFormBySlug(String slug) {
        Form form = formRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        var response = FormDetailResponse.builder()
                .id(form.getId())
                .name(form.getName())
                .slug(form.getSlug())
                .description(form.getDescription())
                .limitOneResponse(form.getLimitOneResponse())
                .allowedDomains(String.valueOf(form.getAllowedDomains()))
                .questions(form.getQuestions().stream()
                        .map(q -> QuestionResponse.builder()
                                .id(q.getId())
                                .name(q.getName())
                                .choiceType(String.valueOf(q.getChoiceType()))
                                .choices(q.getChoices())
                                .isRequired(q.getIsRequired())
                                .build())
                        .collect(Collectors.toList()))
                .build();

        return CommonResponse.<FormDetailResponse>builder()
                .status(HttpStatus.OK.value())
                .message("Get form success")
                .data(mapper.toFormDetailResponse(form))
                .build();
    }


    @Transactional
    @Override
    public CommonResponse<Void> deleteForm(String slug) {
        Form form = formRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Form not found"));
        formRepository.delete(form);

        return CommonResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message("Delete form success")
                .build();
    }
}