package com.rafaelhosaka.gateway.dto;

import java.util.Date;

public record UserResponse(Integer id, String name, String email , String imageUrl, Date createdAt) {
}
