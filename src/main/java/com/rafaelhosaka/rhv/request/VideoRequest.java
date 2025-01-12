package com.rafaelhosaka.rhv.request;

import com.rafaelhosaka.rhv.models.Visibility;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public record VideoRequest(Integer id, String title, String description, Integer userId , MultipartFile videoFile, Date createdAt, Visibility visibility) {
}
