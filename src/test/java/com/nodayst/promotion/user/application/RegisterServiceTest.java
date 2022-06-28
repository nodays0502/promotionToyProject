package com.nodayst.promotion.user.application;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nodayst.promotion.user.domain.User;
import com.nodayst.promotion.user.domain.UserState;
import com.nodayst.promotion.user.domain.UserType;
import com.nodayst.promotion.user.ui.dto.RegisterUserRequest;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class RegisterServiceTest {

    @Autowired
    private RegisterUserService registerUserService;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    void registerUserTest() {

        RegisterUserRequest registerUserRequestDto = new RegisterUserRequest("이수린", UserType.NORMAL,
            UserState.NORMAL);
        registerUserService.register(registerUserRequestDto);

        List<User> resultList = em.createQuery("select u from User u where u.name =: name",
                User.class)
            .setParameter("name", "이수린")
            .getResultList();

        assertEquals(1, resultList.size());

        assertEquals("이수린", resultList.get(0).getName());
        assertEquals(UserType.NORMAL, resultList.get(0).getType());
        assertEquals(UserState.NORMAL, resultList.get(0).getState());
    }
}
