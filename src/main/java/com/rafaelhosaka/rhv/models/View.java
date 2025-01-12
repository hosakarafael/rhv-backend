package com.rafaelhosaka.rhv.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "views")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(ViewId.class)
public class View {
    @Id
    private String ip;
    @Id
    @Column(name = "video_id")
    private Integer videoId;
}
