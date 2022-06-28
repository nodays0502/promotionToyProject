package com.nodayst.promotion.product.application;

import com.nodayst.promotion.product.domain.ProductRepository;
import com.nodayst.promotion.product.domain.ProductType;
import com.nodayst.promotion.user.domain.UserRepository;
import com.nodayst.promotion.exception.user.NotFoundUserException;
import com.nodayst.promotion.exception.user.ResignedUserException;
import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.ui.dto.ProductDto;
import com.nodayst.promotion.user.domain.User;
import com.nodayst.promotion.user.domain.UserState;
import com.nodayst.promotion.user.domain.UserType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class GetProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public List<ProductDto> getProduct(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new NotFoundUserException();
        });
        if (user.getState() == UserState.RESIGN) {
            throw new ResignedUserException();
        }
        List<ProductType> types = new ArrayList<>();
        types.add(ProductType.NORMAL);
        if (user.getType() == UserType.BUSINESS) {
            types.add(ProductType.BUSINESS);
        }
        List<Product> products = productRepository.findProductsByType(types);
        return products.stream().map(ProductDto::mapProductToProductDto)
            .collect(Collectors.toList());
    }
}
