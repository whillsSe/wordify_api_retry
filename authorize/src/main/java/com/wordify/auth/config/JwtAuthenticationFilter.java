package com.wordify.auth.config;

import com.wordify.auth.service.JwtTokenService;
import com.wordify.auth.service.JwtTokenServiceImpl;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/token/refresh/notRequired")  
public class JwtAuthenticationFilter implements Filter {
    private JwtTokenService jwtTokenService;
    public void init(){
        this.jwtTokenService = new JwtTokenServiceImpl();
        System.out.println("JwtAuthenticationFilter#init");
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, java.io.IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        //String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        // リクエストパスが /login であれば、フィルタの処理をスキップします
         /* 
        if (path.startsWith("/login")) {
            chain.doFilter(request, response);
            return;
        }
        */
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            try {
                String userIdString = jwtTokenService.parseClaimsToken(jwtToken);
                httpRequest.setAttribute("user", Integer.parseInt(userIdString));
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
