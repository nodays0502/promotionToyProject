package com.nodayst.promotion.promotion.domain;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface PromotionRepository extends Repository<Promotion, Long> {

    void save(Promotion promotion);

    void deleteById(long promotionId);

    boolean existsById(long promotionId);

    @Query("select pm from Promotion pm "
        + "where pm.productId = :productId "
        + "and pm.period.startDateTime <= :now "
        + "and pm.period.endDateTime >= :now")
    List<Promotion> findPromotionByProductIdAndPeriod(@Param("productId") long productId, @Param("now") LocalDateTime now);
}

