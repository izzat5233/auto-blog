package com.izzatalsharif.openai.autoblog.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "article")
public class Article {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "keywords", nullable = false, unique = true)
    private String keywords;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "introduction", nullable = false, columnDefinition = "TEXT")
    private String introduction;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Section> sections;

    @Column(name = "conclusion", nullable = false, columnDefinition = "TEXT")
    private String conclusion;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createAt;

    @PrePersist
    protected void onCreate() {
        createAt = LocalDateTime.now();
    }

}
