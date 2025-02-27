package com.yourstech.springform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "allowed_domains")
public class AllowedDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String domain;
    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;

    public AllowedDomain(String domain, Form form) {
    }
}
