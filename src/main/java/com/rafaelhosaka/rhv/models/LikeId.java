package com.rafaelhosaka.rhv.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LikeId implements Serializable {
    private Integer userId;
    private Integer videoId;
}
