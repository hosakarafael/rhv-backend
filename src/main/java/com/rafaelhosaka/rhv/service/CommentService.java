package com.rafaelhosaka.rhv.service;

import com.rafaelhosaka.rhv.request.CommentRequest;
import com.rafaelhosaka.rhv.response.ErrorCode;
import com.rafaelhosaka.rhv.response.Response;
import com.rafaelhosaka.rhv.repository.CommentRepository;
import com.rafaelhosaka.rhv.utils.VideoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final JwtService jwtService;
    private final UserService userService;
    private final VideoMapper videoMapper;

    public Response createComment(CommentRequest commentRequest) {
        var comment = videoMapper.toComment(commentRequest);
        comment.setCreatedAt(new Date());
        commentRepository.save(comment);
        return videoMapper.toCommentResponse(comment);
    }

    public Response deleteComment(Integer id, String authHeader) throws Exception {
        var comment = commentRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Comment with ID "+id+" not found")
                );

        var user = userService.findById(comment.getUserId());

        if(user == null){
            throw new Exception("user body is null");
        }

        if(!jwtService.isSameSubject(authHeader, user.getEmail())){
           return new Response("Requested user is not allowed to do this operation", ErrorCode.VS_FORBIDDEN_SUBJECT);
        }

        commentRepository.delete(comment);
        return new Response("Deleted comment successfully");
    }
}
