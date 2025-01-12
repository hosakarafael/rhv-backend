package com.rafaelhosaka.rhv.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse extends Response{
    private Integer id;
    private String name;
    private String email;
    private String imageUrl;
    private Date createdAt;
    private List<SubscriptionResponse> subscribedUsers;
    private int subscribers;
    private String profileImageUrl;
}
