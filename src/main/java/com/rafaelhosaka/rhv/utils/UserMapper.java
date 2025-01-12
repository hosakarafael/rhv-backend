package com.rafaelhosaka.rhv.utils;

import com.rafaelhosaka.rhv.response.SubscriptionResponse;
import com.rafaelhosaka.rhv.request.UserRequest;
import com.rafaelhosaka.rhv.response.UserResponse;
import com.rafaelhosaka.rhv.models.User;
import com.rafaelhosaka.rhv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final UserRepository userRepository;

    public User toUser(UserRequest request){
        return User.builder()
                .id(request.id())
                .name(request.name())
                .email(request.email())
                .createdAt(request.createdAt())
                .build();
    }

    public UserResponse toUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .subscribedUsers(toSubscriptionsResponse(user.getSubscribedUsers()))
                .subscribers(userRepository.countSubscribers(user.getId()))
                .profileImageUrl(user.getProfileImageUrl())
                .build();
    }

    public List<SubscriptionResponse> toSubscriptionsResponse(Set<User> subscribedUsers) {
        if(subscribedUsers == null){
            return new ArrayList<>();
        }
        return subscribedUsers.stream()
                .map(subscribedUser -> new SubscriptionResponse(
                        subscribedUser.getId(),
                        subscribedUser.getName(),
                        subscribedUser.getProfileImageUrl()))
                .collect(Collectors.toList());
    }
}
