package com.rafaelhosaka.rhv.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(unique = true)
    private String email;
    private Date createdAt;
    @ManyToMany
    @JoinTable(
            name = "subscriptions",
            joinColumns = @JoinColumn(name = "subscriber_id"),
            inverseJoinColumns = @JoinColumn(name = "creator_id")
    )
    private Set<User> subscribedUsers = new HashSet<>();
    private String profileImageUrl;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Video> videos;
}
