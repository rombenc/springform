package com.yourstech.springform.model;

import com.yourstech.springform.model.enums.ChoiceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ChoiceType choiceType;

    private Boolean isRequired;

    @ElementCollection
    private List<String> choices;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @OneToMany(mappedBy = "question")
    private List<Answer> answers;
}
