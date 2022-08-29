package kr.co.fastcampus.eatgo.domain;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    public void creation() {
        User user = User.builder()
                .email("tester@exemple.com")
                .name("tester")
                .level(100L)
                .build();

        assertThat(user.getName(), is("tester"));
        assertThat(user.isAdmin(), is(true));
    }

}