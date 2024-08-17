package org.data.homework03.model.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponseOrder {
    private Long id;
    private LocalDate orderDate;
    private Float totalAmount;
    private String status;
    private List<ProductResponse> productList;
}
