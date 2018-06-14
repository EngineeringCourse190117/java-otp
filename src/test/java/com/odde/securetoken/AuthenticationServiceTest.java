package com.odde.securetoken;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class AuthenticationServiceTest {

    @Test
    public void is_valid_test() {
        AuthenticationService target = new AuthenticationService();

        boolean actual = target.isValid("joey", "91000000");

        assertTrue(actual);
    }

}
