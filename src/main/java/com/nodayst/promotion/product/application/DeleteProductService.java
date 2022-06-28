package com.nodayst.promotion.product.application;

import com.nodayst.promotion.product.domain.ProductRepository;
import com.nodayst.promotion.exception.product.NotFoundProductException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DeleteProductService {

    private final ProductRepository productRepository;

    public void deleteProduct(long productId) {
        if(!productRepository.existsById(productId)){
            throw new NotFoundProductException();
        }
        productRepository.deleteById(productId);
    }
}
