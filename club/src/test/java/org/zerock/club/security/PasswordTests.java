package org.zerock.club.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author sskim
 */
@SpringBootTest
public class PasswordTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void testEncode() throws Exception {
        String password = "1111";
        String enPw = passwordEncoder.encode(password);
        System.out.println("enPw = " + enPw);
        boolean matches = passwordEncoder.matches(password, enPw);
        System.out.println("matches = " + matches);
    }
}
