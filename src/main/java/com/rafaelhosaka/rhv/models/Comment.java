package com.rafaelhosaka.rhv.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "video_id")
    private Integer videoId;
    @Column(name = "user_id")
    private Integer userId;
    @Column(length = 1000)
    private String text;
    private Date createdAt;
}
