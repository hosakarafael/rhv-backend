package com.rafaelhosaka.rhv.utils;

import com.rafaelhosaka.rhv.response.HistoryResponse;
import com.rafaelhosaka.rhv.models.History;
import com.rafaelhosaka.rhv.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HistoryMapper {
    private final VideoService videoService;

    public HistoryResponse toHistoryResponse(History history){
        return HistoryResponse.builder()
                .userId(history.getUserId())
                .videoId(history.getVideoId())
                .watchedAt(history.getWatchedAt())
                .isVideoVisible(true)
                .isVideoDeleted(false)
                .build();
    }
}
