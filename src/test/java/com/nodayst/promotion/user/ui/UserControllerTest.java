package com.nodayst.promotion.user.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.nodayst.promotion.user.domain.User;
import com.nodayst.promotion.user.domain.UserState;
import com.nodayst.promotion.user.domain.UserType;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager em;

    @Test
    @Transactional
    public void registerUserTest() throws Exception {
        String name = "이수린";
        UserType type = UserType.NORMAL;
        UserState state = UserState.NORMAL;
        mvc.perform(post("/api/user")
                .content(
                    "{ \"name\":\"" + name + "\","
                        + "\"type\":\"" + type.name() + "\","
                        + "\"state\":\"" + state.name() + "\"}"
                ).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    public void resignUserTest() throws Exception {
        String name = "이수린";
        UserType type = UserType.NORMAL;
        UserState state = UserState.NORMAL;
        User user = User.withOutId(name, type, state);
        em.persist(user);
        long userId = em.createQuery("select u.id from User u where u.name = :name",Long.class)
            .setParameter("name", name).getSingleResult();
        mvc.perform(post("/api/user/"+userId+"/resign"))
            .andExpect(status().isOk());
        User resignUser = em.find(User.class,userId);
        assertEquals(UserState.RESIGN,resignUser.getState());
    }
}