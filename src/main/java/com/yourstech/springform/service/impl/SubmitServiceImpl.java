package com.yourstech.springform.service.impl;

import com.yourstech.springform.dto.request.SubmitRequest;
import com.yourstech.springform.dto.response.*;
import com.yourstech.springform.model.*;
import com.yourstech.springform.repository.*;
import com.yourstech.springform.service.SubmitService;
import com.yourstech.springform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubmitServiceImpl implements SubmitService {
    private final FormRepository formRepository;
    private final SubmitRepository submitRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final UserService userService;

    @Transactional
    @Override
    public CommonResponse<SubmitResponse> submit(String formSlug, SubmitRequest request) {
        var user = userService.getLoginUser();
        Form form = formRepository.findBySlug(formSlug)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        if (!isEmailAllowed(user.getEmail(), form.getAllowedDomains())) {
            throw new RuntimeException("Your email domain is not allowed to submit this form");
        }


        Submit submit = Submit.builder()
                .form(form)
                .user(user)
                .createdAt(LocalDateTime.now())
                .build();
        submitRepository.save(submit);

        List<Answer> answers = request.getAnswers().stream()
                .map(answerRequest -> {
                    Question question = questionRepository.findById(answerRequest.getQuestionId())
                            .orElseThrow(() -> new RuntimeException("Question not found"));
                    return Answer.builder()
                            .submit(submit)
                            .question(question)
                            .value(answerRequest.getValue())
                            .build();
                }).collect(Collectors.toList());

        answerRepository.saveAll(answers);

        SubmitResponse response = SubmitResponse.builder()
                .id(submit.getId())
                .formId(submit.getForm().getId())
                .userId(submit.getUser().getId())
                .createdAt(String.valueOf(submit.getCreatedAt()))
                .answers(answers.stream()
                        .map(answer -> new AnswerResponse(answer.getQuestion().getId(), answer.getValue()))
                        .collect(Collectors.toList()))
                .build();

        return CommonResponse.<SubmitResponse>builder()
                .status(HttpStatus.CREATED.value())
                .message("Submission submitted successfully")
                .data(response)
                .build();
    }

    private boolean isEmailAllowed(String email, List<AllowedDomain> allowedDomains) {
        if (allowedDomains == null || allowedDomains.isEmpty()) {
            return true; // Jika tidak ada batasan domain, izinkan semua email
        }

        String emailDomain = email.substring(email.indexOf("@") + 1); // Ambil teks setelah '@'

        // Ubah List<AllowedDomain> jadi List<String> dulu, baru cek
        return allowedDomains.stream()
                .map(AllowedDomain::getDomain) // Ambil hanya nama domainnya
                .anyMatch(domain -> domain.equalsIgnoreCase(emailDomain));
    }



    @Override
    public CommonResponse<List<SubmitDetailResponse>> getAllSubmissions(String formSlug) {
        List<SubmitDetailResponse> submissions = submitRepository.findByFormSlug(formSlug).stream()
                .map(submit -> {
                    List<Answer> answers = answerRepository.findBySubmitId(submit.getId());

                    UserResponse userResponse = UserResponse.builder()
                            .id(submit.getUser().getId())
                            .name(submit.getUser().getName())
                            .email(submit.getUser().getEmail())
                            .build();
                    //note : pake map supaya response sama seperti json
                    Map<String, String> answerMap = answers.stream()
                            .collect(Collectors.toMap(
                                    answer -> answer.getQuestion().getName(), // Key = nama pertanyaan
                                    Answer::getValue
                            ));

                    return SubmitDetailResponse.builder()
                            .date(submit.getCreatedAt().toString())
                            .user(userResponse)
                            .answers(answerMap)
                            .build();
                }).collect(Collectors.toList());

        return CommonResponse.<List<SubmitDetailResponse>>builder()
                .status(HttpStatus.OK.value())
                .message("Get responses success")
                .data(submissions)
                .build();
    }

}
