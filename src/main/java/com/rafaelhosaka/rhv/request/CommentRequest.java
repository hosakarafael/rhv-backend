package com.rafaelhosaka.rhv.request;

public record CommentRequest(Integer id,Integer userId, Integer videoId, String text) {
}
