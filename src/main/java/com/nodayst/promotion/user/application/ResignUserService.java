package com.nodayst.promotion.user.application;

import com.nodayst.promotion.user.domain.UserRepository;
import com.nodayst.promotion.exception.user.NotFoundUserException;
import com.nodayst.promotion.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResignUserService {

    private final UserRepository userRepository;

    public void resign(long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundUserException());
        user.resign();
    }
}

