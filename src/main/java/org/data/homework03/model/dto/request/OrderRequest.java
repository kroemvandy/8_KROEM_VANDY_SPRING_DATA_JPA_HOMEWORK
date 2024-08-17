package org.data.homework03.model.dto.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private Integer quantity;
    private Integer productId;
}
