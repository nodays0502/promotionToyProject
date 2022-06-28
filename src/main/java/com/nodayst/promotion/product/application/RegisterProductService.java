package com.nodayst.promotion.product.application;

import com.nodayst.promotion.product.domain.Product;
import com.nodayst.promotion.product.domain.ProductRepository;
import com.nodayst.promotion.product.ui.dto.RegisterProductRequest;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class RegisterProductService {

    private final ProductRepository productRepository;

    public void registerProduct(RegisterProductRequest registerProductRequestDto) {
        Product product = RegisterProductRequest.mapToProduct(registerProductRequestDto);
        productRepository.save(product);
    }
}
