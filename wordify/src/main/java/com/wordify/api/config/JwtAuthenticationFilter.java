package com.wordify.api.config;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")  
public class JwtAuthenticationFilter implements Filter {
    private JwtParser jwtParser;  
    public void init(){
        Properties prop = new Properties();
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream input = classLoader.getResourceAsStream("config.properties")) {
            prop.load(input);
            byte[] keyBytes = prop.getProperty("jwt.key").getBytes(StandardCharsets.UTF_8);
            this.jwtParser = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(keyBytes)).build();
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, java.io.IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            try {
                // JWTの検証
                Jws<Claims> jwsClaims = jwtParser.parseClaimsJws(jwtToken);
                // JWTトークンの検証が成功したら、クレームからユーザー情報を取り出し、リクエストの属性に設定します。
                httpRequest.setAttribute("user", jwsClaims.getBody().getSubject());
                chain.doFilter(request, response);
            } catch (JwtException e) {
                // トークンの検証が失敗したら、エラーレスポンスを送ります。
                ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    //((HttpServletResponse) response).setContentType("text/plain");
                response.getWriter().write("Invalid JWT token");
            }
        } else {
            // JWTトークンがヘッダーに含まれていない場合も、エラーレスポンスを送ります。
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                //((HttpServletResponse) response).setContentType("text/plain");
            response.getWriter().write("Missing Authorization header");
        }
    }
}
