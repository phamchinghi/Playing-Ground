package com.pcn.playing_ground.dto.response;

import lombok.*;
import lombok.experimental.Accessors;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T> {
    private boolean success;
    private String message;
    private T response;
}

