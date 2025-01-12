package com.rafaelhosaka.rhv.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "histories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(HistoryId.class)
public class History {
    @Id
    @Column(name = "user_id")
    private Integer userId;
    @Id
    @Column(name = "video_id")
    private Integer videoId;

    private Date watchedAt;
}
