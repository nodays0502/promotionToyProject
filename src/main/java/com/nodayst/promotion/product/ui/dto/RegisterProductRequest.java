package com.nodayst.promotion.product.ui.dto;


import com.nodayst.promotion.product.domain.Money;
import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterProductRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String desc;
    @Min(1)
    private long price;
    @NotNull
    private ProductType type;

    public static Product mapToProduct(RegisterProductRequest registerProductRequestDto) {
        return Product.withOutId(registerProductRequestDto.getName(),
            registerProductRequestDto.getDesc(), new Money(registerProductRequestDto.getPrice()),
            registerProductRequestDto.getType());
    }
}
