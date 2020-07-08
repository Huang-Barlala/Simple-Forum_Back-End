package com.barlala.forum.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.barlala.forum.entity.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/22 下午7:01
 */
@Service
public class AuthenticationService {
    public static final String SECRET = "55lt%%TIIBvl$jdcKU^ESUuG%aEwyErr$VFHDsS0mo4a%mjqlT4fui&*kSqqzpe53FNvSv3ky#$MPCl1^m7*x3$dAo9U8KCN%8s";

    public String createToken(User user) {
        String token = "";
        try {
            token = JWT.create()
                    .withIssuedAt(new Date())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 86400000))
                    .withClaim("id", user.getId())
                    .withClaim("username", user.getUsername())
                    .withClaim("avatar", user.getAvatarUrl())
                    .withClaim("permission", user.getPermission())
                    .sign(Algorithm.HMAC256(SECRET));
        } catch (JWTCreationException ignored) {
        }
        return token;
    }

    public int verifyToken(String token) {
        int id = -1;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(AuthenticationService.SECRET)).build();
            DecodedJWT jwt = verifier.verify(token);
            id = jwt.getClaim("id").asInt();
        } catch (JWTVerificationException exception) {
            exception.printStackTrace();
        }
        return id;
    }

    public String getUsername(String token) {
        String username = "";
        try {
            DecodedJWT jwt = JWT.decode(token);
            username = jwt.getClaim("username").asString();
        } catch (JWTDecodeException ignore) {
        }
        return username;
    }

    public String getAvatar(String token) {
        String avatar = "";
        try {
            DecodedJWT jwt = JWT.decode(token);
            avatar = jwt.getClaim("avatar").asString();
        } catch (JWTDecodeException ignore) {
        }
        return avatar;
    }

    public String getPermission(String token) {
        String permission = "";
        try {
            DecodedJWT jwt = JWT.decode(token);
            permission = jwt.getClaim("permission").asString();
        } catch (JWTDecodeException ignore) {
        }
        return permission;
    }

    public void refreshCookie(User user, HttpServletResponse response) {
        Cookie cookie = new Cookie("jwt", createToken(user));
        cookie.setMaxAge(86400);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
