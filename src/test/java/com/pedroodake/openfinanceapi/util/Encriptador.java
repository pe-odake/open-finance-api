package com.pedroodake.openfinanceapi.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Encriptador {
    public static void main(String[] args) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String adminPass = System.getenv("ADMIN_PASS");
        String hash = encoder.encode(adminPass);
        System.out.println(hash);
    }
}