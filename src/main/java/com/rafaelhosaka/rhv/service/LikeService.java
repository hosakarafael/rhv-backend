package com.rafaelhosaka.rhv.service;

import com.rafaelhosaka.rhv.request.LikeRequest;
import com.rafaelhosaka.rhv.response.Response;
import com.rafaelhosaka.rhv.models.Like;
import com.rafaelhosaka.rhv.repository.LikeRepository;
import com.rafaelhosaka.rhv.repository.VideoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final VideoRepository videoRepository;
    private final UserService userService;

    public Response like(LikeRequest likeRequest) throws Exception {
        var user = userService.findById(likeRequest.userId());

        if(user == null){
            throw new Exception("user body is null or user email is null");
        }

        var userId = user.getId();
        var video =  videoRepository.findById(likeRequest.videoId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Video with ID "+likeRequest.videoId()+" not found")
                );
        if(video.getLikes().stream().anyMatch(likes -> likes.getUserId().equals(userId))){
            return new Response("this user already liked this video");
        }
        var like = new Like(userId,video.getId());
        likeRepository.save(like);
        return new Response("Liked successfully");
    }

    public Response unlike(LikeRequest likeRequest) throws Exception {
        var user = userService.findById(likeRequest.userId());

        if(user == null){
            throw new Exception("user body is null or user email is null");
        }

        var userId = user.getId();
        var video =  videoRepository.findById(likeRequest.videoId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Video with ID "+likeRequest.videoId()+" not found")
                );
        if(video.getLikes().stream().noneMatch(likes -> likes.getUserId().equals(userId))){
            return new Response("this user did not liked this video");
        }
        var like = new Like(userId,video.getId());
        likeRepository.delete(like);
        return new Response("Unliked successfully");
    }
}
