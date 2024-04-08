package com.github.taven.springboot3mqttv5;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.List;
import java.util.Map;

public class JwtGenerator {

    public static String generateJwt(String username) {

        String secret = "emqxsecretemqxsecretemqxsecretemqxsecret";

        // 使用密钥生成 Key 对象
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        // 构建JWT
        String jwt = Jwts.builder()
                .claim("username", username) // 自定义声明
                .claim("acl",
                        Map.of("sub", List.of("testtopic/4", "testtopic/5"),
                        "pub", List.of("testtopic/4", "testtopic/5"))
                )
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        System.out.println("Generated JWT: " + jwt);
        return jwt;
    }

}
