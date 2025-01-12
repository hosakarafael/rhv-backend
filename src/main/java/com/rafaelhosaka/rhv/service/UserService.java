package com.rafaelhosaka.rhv.service;

import com.rafaelhosaka.rhv.models.History;
import com.rafaelhosaka.rhv.models.User;
import com.rafaelhosaka.rhv.models.Visibility;
import com.rafaelhosaka.rhv.repository.HistoryRepository;
import com.rafaelhosaka.rhv.repository.UserRepository;
import com.rafaelhosaka.rhv.repository.VideoRepository;
import com.rafaelhosaka.rhv.request.HistoryRequest;
import com.rafaelhosaka.rhv.request.SubscribeRequest;
import com.rafaelhosaka.rhv.request.UserRequest;
import com.rafaelhosaka.rhv.response.*;
import com.rafaelhosaka.rhv.utils.HistoryMapper;
import com.rafaelhosaka.rhv.utils.UserMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final VideoRepository videoRepository;
    private final HistoryRepository historyRepository;
    private final VideoService videoService;
    private final UserMapper userMapper;
    private final HistoryMapper historyMapper;
    private final CloudinaryService cloudinaryService;
    private final JwtService jwtService;

    public UserResponse createUser(UserRequest userRequest){
        var user = userMapper.toUser(userRequest);
        user.setCreatedAt(new Date());
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    public UserResponse findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::toUserResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException("User with ID "+id+" not found")
                );
    }

    public UserResponse findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toUserResponse)
                .orElseThrow(
                        () -> new EntityNotFoundException("User with email "+email+" not found")
                );
    }

    public void registerHistory(HistoryRequest historyRequest) {
        var user = userRepository.findById(historyRequest.userId()).orElseThrow(
                () -> new EntityNotFoundException("User with ID "+historyRequest.userId()+" not found")
        );
        var video = videoRepository.findById(historyRequest.videoId()).orElseThrow(
                () -> new EntityNotFoundException("Video with ID "+historyRequest.videoId()+" not found")
        );
        var history = new History(user.getId(), video.getId(), new Date());
        historyRepository.save(history);
    }

    public List<HistoryResponse> findHistoryByUserId(Integer userId) {
        Sort sort = Sort.by(Sort.Order.desc("watchedAt"));
        return historyRepository.findByUserId(userId, sort).stream()
                .map(historyMapper::toHistoryResponse)
                .peek(result -> {
                    VideoResponse videoResponse = null;
                    try{
                        videoResponse = videoService.findById(result.getVideoId());
                        if (videoResponse != null) {
                            //video is now private
                            if(videoResponse.getVisibility() == Visibility.PRIVATE){
                                //user own this video
                                if(videoResponse.getUser().getId() == userId){
                                    result.setVideo(videoResponse);
                                    //user does not own this video
                                }else{
                                    result.setVideoVisible(false);
                                }
                                //video is now public
                            }else{
                                result.setVideo(videoResponse);
                            }
                        }
                    }catch (EntityNotFoundException e){
                        //Video may be deleted
                        result.setVideoDeleted(true);
                    }
                }).collect(Collectors.toList());
    }

    public Response subscribeToUser(SubscribeRequest subscribeRequest) throws Exception {
        if(Objects.equals(subscribeRequest.subscriberId(), subscribeRequest.creatorId())){
            throw new Exception("Subscriber and Creator cannot be the same");
        }

        User subscriber = userRepository.findById(subscribeRequest.subscriberId()).orElseThrow(() -> new Exception("subscriber with ID "+subscribeRequest.subscriberId()+" not found"));
        User creator = userRepository.findById(subscribeRequest.creatorId()).orElseThrow(() -> new Exception("creator with ID "+subscribeRequest.creatorId()+" not found"));

        if (!subscriber.getSubscribedUsers().contains(creator)) {
            subscriber.getSubscribedUsers().add(creator);
            userRepository.save(subscriber);
            return new Response("User "+subscriber.getId()+" subscribed to User "+creator.getId()+" successfully");
        }
        throw new Exception("User "+subscriber.getId()+" already subscribed to User "+creator.getId());
    }

    public Response unsubscribeFromUser(SubscribeRequest subscribeRequest) throws Exception {
        if(Objects.equals(subscribeRequest.subscriberId(), subscribeRequest.creatorId())){
            throw new Exception("Subscriber and Creator cannot be the same");
        }

        User subscriber = userRepository.findById(subscribeRequest.subscriberId()).orElseThrow(() -> new Exception("subscriber with ID "+subscribeRequest.subscriberId()+" not found"));
        User creator = userRepository.findById(subscribeRequest.creatorId()).orElseThrow(() -> new Exception("creator with ID "+subscribeRequest.creatorId()+" not found"));

        subscriber.getSubscribedUsers().remove(creator);
        userRepository.save(subscriber);
        return new Response("User "+subscriber.getId()+" unsubscribed from User "+creator.getId()+" successfully");
    }

    public Response uploadProfileImage(MultipartFile profileImage, String authHeader) throws IOException {
        String token = authHeader.startsWith("Bearer ") ?
                authHeader.substring(7) : null;
        var subject = jwtService.extractUsername(token);
        var user = userRepository.findByEmail(subject).orElseThrow(
                () -> new EntityNotFoundException("User with email "+subject+" not found"));
        var profileImageUrl = cloudinaryService.upload(profileImage.getBytes(), "users/"+user.getId(), "profile" , "image", "upload");
        user.setProfileImageUrl(profileImageUrl);
        userRepository.save(user);
        var userResponse = userMapper.toUserResponse(user);
        userResponse.setErrorCode(ErrorCode.US_SUCCESS);
        return userResponse;
    }
}
