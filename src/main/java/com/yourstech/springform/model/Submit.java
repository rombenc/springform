package com.yourstech.springform.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "submit") // Tetap sesuai nama tabel di ERD
public class Submit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "submit", cascade = CascadeType.ALL)
    private List<Answer> answers;

    private LocalDateTime createdAt;
}
