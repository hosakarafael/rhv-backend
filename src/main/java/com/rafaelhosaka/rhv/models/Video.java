package com.rafaelhosaka.rhv.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Table(name = "videos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 100)
    private String title;
    @Column(length = 5000)
    private String description;
    @OneToMany
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Set<View> views = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Date createdAt;
    @OneToMany
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private Set<Like> likes = new HashSet<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "video_id", referencedColumnName = "id")
    private List<Comment> comments = new ArrayList<>();
    private Visibility visibility;
    private String thumbnailUrl;
    private String videoUrl;
}
