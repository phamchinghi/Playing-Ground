package com.pcn.playing_ground.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(fluent = true)
public class ApiResponseDto<T> {
    private boolean isSuccess;
    private String message;
    private T response;
}
