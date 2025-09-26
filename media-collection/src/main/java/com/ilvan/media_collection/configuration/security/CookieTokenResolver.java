package com.ilvan.media_collection.configuration.security;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.oauth2.server.resource.web.BearerTokenResolver;

public class CookieTokenResolver implements BearerTokenResolver {

    private final String cookieName;

    public CookieTokenResolver(String cookieName) {
        this.cookieName = cookieName;
    }

    @Override
    public String resolve(HttpServletRequest request) {

        if(request.getCookies() != null) {
            for (Cookie cookie: request.getCookies()) {
                if (cookieName.equals(cookie.getName())) {
                    var token = cookie.getName();
                    System.out.printf("DEBUG: Token extraído do Cookie: " + token);
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
