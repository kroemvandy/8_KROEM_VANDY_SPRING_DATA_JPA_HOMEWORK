package org.data.homework03.model.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private HttpStatus status;
    private String message;
    private T payload;
    private Integer code;
    private LocalDate dateTime;
}
