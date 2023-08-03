package com.wordify.api.config;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Logger;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.IOException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(filterName = "JwtAuthentication" ,urlPatterns =  "/*")  
public class JwtAuthenticationFilter implements Filter {
    private JwtParser jwtParser;  
    public void init(FilterConfig filterConfig){
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
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        String authHeader = httpRequest.getHeader("Authorization");
        Logger logger = Logger.getLogger(JwtAuthenticationFilter.class.getName());
        logger.info("authHeader: " + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            try {
                // JWTの検証
                Jws<Claims> jwsClaims = jwtParser.parseClaimsJws(jwtToken);
                // JWTトークンの検証が成功したら、クレームからユーザー情報を取り出し、リクエストの属性に設定します。
                httpRequest.setAttribute("user", Integer.parseInt(jwsClaims.getBody().getSubject()));
                chain.doFilter(request, response);
            }catch (ExpiredJwtException e) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write("{\"error\":\"TOKEN_EXPIRED\",\"message\":\"Expired JWT token\"}");
            } catch (JwtException e) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write("{\"error\":\"INVALID_TOKEN\",\"message\":\"Invalid JWT token\"}");
            }
        } else {
            // JWTトークンがヘッダーに含まれていない場合も、エラーレスポンスを送ります。
            ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                //((HttpServletResponse) response).setContentType("text/plain");
            response.getWriter().write("Missing Authorization header");
        }
    }
    public void destroy() {}
}
