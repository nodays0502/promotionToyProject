package com.nodayst.promotion.product.ui.dto;

import com.nodayst.promotion.product.domain.Money;
import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductType;
import com.nodayst.promotion.promotion.domain.Promotion;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetPromotionInfoResponse {

    private long id;
    private String name;
    private String desc;
    private long money;
    private long promotionAppliedMoney;
    private ProductType type;
    private List<PromotionDto> productDtoList;

    public static GetPromotionInfoResponse mapProductToGetPromotionInfoResponseDto(
        Product product, List<Promotion> promotionList , Money promotionAppliedMoney) {
        List<PromotionDto> promotions = promotionList.stream()
            .map(PromotionDto::mapPromotionToPromotionDto).collect(
                Collectors.toList());
        return new GetPromotionInfoResponse(product.getId(), product.getName(), product.getDesc(),
            product.getPrice().getValue(), promotionAppliedMoney.getValue(),product.getType(),promotions);
    }
}