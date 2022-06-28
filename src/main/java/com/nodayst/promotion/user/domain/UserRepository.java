package com.nodayst.promotion.user.domain;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {

    void save(User user);

    Optional<User> findById(long id);

}
