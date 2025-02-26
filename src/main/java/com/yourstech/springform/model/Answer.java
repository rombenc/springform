package com.yourstech.springform.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "answers")
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "response_id") // Sesuai ERD
    private Submit submit;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}
