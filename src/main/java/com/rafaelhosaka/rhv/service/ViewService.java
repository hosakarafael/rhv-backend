package com.rafaelhosaka.rhv.service;

import com.rafaelhosaka.rhv.response.Response;
import com.rafaelhosaka.rhv.request.ViewRequest;
import com.rafaelhosaka.rhv.models.View;
import com.rafaelhosaka.rhv.repository.VideoRepository;
import com.rafaelhosaka.rhv.repository.ViewRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViewService {
    private final ViewRepository viewRepository;
    private final VideoRepository videoRepository;

    public Response increaseViews(ViewRequest viewRequest) {
        var ip = viewRequest.ip();
        var video =  videoRepository.findById(viewRequest.videoId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Video with ID "+viewRequest.videoId()+" not found")
                );
        if(video.getViews().stream().anyMatch(view -> view.getIp().equals(ip))){
            return new Response("this user already watched this video");
        }
        var view = new View(ip,video.getId());
        viewRepository.save(view);

        return new Response("view increase successfully");
    }
}
