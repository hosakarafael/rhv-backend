package com.rafaelhosaka.rhv.request;

import java.util.Date;

public record UserRequest(Integer id, String name, String email, Date createdAt) {
}
