package com.rafaelhosaka.rhv.request;

import java.util.Date;

public record HistoryRequest(Integer userId, Integer videoId, Date watchedAt) {
}
