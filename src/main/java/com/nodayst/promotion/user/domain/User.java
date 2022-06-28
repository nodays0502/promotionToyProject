package com.nodayst.promotion.user.domain;

import com.nodayst.promotion.exception.user.AlreadyUserStateIsResignException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String name;

    @Column(name = "USER_TYPE")
    @Enumerated(EnumType.STRING)
    private UserType type;

    @Column(name = "USER_STATE")
    @Enumerated(EnumType.STRING)
    private UserState state;

    private User(Long id, String name, UserType type, UserState state) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.state = state;
    }

    public static User withOutId(String name, UserType type, UserState state) {
        return new User(null, name, type, state);
    }

    public static User withId(Long id, String name, UserType type, UserState state) {
        return new User(id, name, type, state);
    }

    public void resign() {
        if (this.state == UserState.RESIGN) {
            throw new AlreadyUserStateIsResignException();
        }
        this.state = UserState.RESIGN;
    }
}

