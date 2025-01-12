package com.rafaelhosaka.rhv.utils;

import com.rafaelhosaka.rhv.request.CommentRequest;
import com.rafaelhosaka.rhv.response.CommentResponse;
import com.rafaelhosaka.rhv.request.VideoRequest;
import com.rafaelhosaka.rhv.response.VideoResponse;
import com.rafaelhosaka.rhv.models.Comment;
import com.rafaelhosaka.rhv.models.Like;
import com.rafaelhosaka.rhv.models.Video;
import com.rafaelhosaka.rhv.models.Visibility;
import com.rafaelhosaka.rhv.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VideoMapper {
    private final UserMapper userMapper;

    public Video toVideo(VideoRequest request){
        return Video.builder()
                .id(request.id())
                .title(request.title())
                .description(request.description())
                .createdAt(request.createdAt())
                .visibility(request.visibility() != null ? request.visibility() : Visibility.PUBLIC)
                .build();
    }

    public VideoResponse toVideoResponse(Video video){
        return VideoResponse.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .views(video.getViews().size())
                .createdAt(video.getCreatedAt())
                .likedUsers(video.getLikes().stream()
                        .map(Like::getUserId)
                        .collect(Collectors.toSet()))
                .comments(
                        video.getComments().stream()
                                .map(this::toCommentResponse)
                                .sorted(Comparator.comparing(CommentResponse::getCreatedAt).reversed())
                                .toList())
                .user(userMapper.toUserResponse(video.getUser()))
                .visibility(video.getVisibility())
                .thumbnailUrl(video.getThumbnailUrl())
                .videoUrl(video.getVideoUrl())
                .build();
    }

    public Comment toComment(CommentRequest request){
        return Comment.builder()
                .id(request.id())
                .userId(request.userId())
                .videoId(request.videoId())
                .text(request.text())
                .build();
    }

    public CommentResponse toCommentResponse(Comment comment){
        return CommentResponse.builder()
                .id(comment.getId())
                .text(comment.getText())
                .createdAt(comment.getCreatedAt())
                .user(null)
                .build();
    }
}
