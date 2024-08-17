package org.data.homework03.model.dto.request;


import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {
    private String productName;
    private BigDecimal unitPrice;
    private String description;
}
