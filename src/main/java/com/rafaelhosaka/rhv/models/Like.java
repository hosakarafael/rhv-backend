package com.rafaelhosaka.rhv.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "likes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(LikeId.class)
public class Like {
    @Id
    private Integer userId;
    @Id
    @Column(name = "video_id")
    private Integer videoId;
}
