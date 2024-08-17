package org.data.homework03.model.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private String customerName;
    private String address;
    private String phoneNumber;
    private String email;
}
