package org.demo.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private final UserInfo userInfo = new UserInfo("test", "");
    private User user1;
    private User user2;

    @BeforeEach
    void init() {
        user1 = new User(1L, userInfo);
        user2 = new User(2L, userInfo);
    }

    @Test
    void givenTwoUser_whenEqual_thenReturnFalse() {

        // when
        boolean value = user1.equals(user2);

        // then
        assertFalse(value);
    }

    @Test
    void givenSameIdUser_whenEqual_thenReturnTrue() {

        // given
        User sameUser = new User(1L, userInfo);

        // when
        boolean value = user1.equals(sameUser);

        // then
        assertTrue(value);
    }

    @Test
    void givenTwoUser_whenHashCode_thenNotEquals() {

        // when
        int hashCode1 = user1.hashCode();
        int hashCode2 = user2.hashCode();

        // then
        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    void givenTwoSameIdUser_whenHashCode_thenEquals() {

        // given
        User sameUser = new User(1L, userInfo);

        // when
        int hashCode1 = user1.hashCode();
        int sameUserHashCode = sameUser.hashCode();

        // then
        assertEquals(hashCode1, sameUserHashCode);
    }

    @Test
    void givenTwoUser_whenUser1FollowUser2_thenIncreaseCount() {
        // when
        user1.follow(user2);

        // then
        assertEquals(1, user1.getFollowingCount());
        assertEquals(0, user1.getFollowerCount());
        assertEquals(0, user2.getFollowingCount());
        assertEquals(1, user2.getFollowerCount());
    }

    @Test
    void givenTwoUserUser1FollowUser2_whenUnfollow_thenDecreaseCount() {
        // given
        user1.follow(user2);

        // when
        user1.unfollow(user2);

        // then
        assertEquals(0, user1.getFollowingCount());
        assertEquals(0, user1.getFollowerCount());
        assertEquals(0, user2.getFollowingCount());
        assertEquals(0, user2.getFollowerCount());
    }

    @Test
    void givenTwoUser_whenUnfollow_thenDecreaseCount() {
        // when
        user1.unfollow(user2);

        // then
        assertEquals(0, user1.getFollowingCount());
        assertEquals(0, user1.getFollowerCount());
        assertEquals(0, user2.getFollowingCount());
        assertEquals(0, user2.getFollowerCount());
    }
}
