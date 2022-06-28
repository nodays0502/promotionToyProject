package com.nodayst.promotion.promotion.application;

import com.nodayst.promotion.exception.promotion.NotFoundPromotionException;
import com.nodayst.promotion.promotion.domain.PromotionRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class DeletePromotionService {
    private final PromotionRepository promotionRepository;

    public void deletePromotion(long promotionId){
        if(!promotionRepository.existsById(promotionId)){
            throw new NotFoundPromotionException();
        }
        promotionRepository.deleteById(promotionId);
    }
}

