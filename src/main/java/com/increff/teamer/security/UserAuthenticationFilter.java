package com.increff.teamer.security;

import com.increff.teamer.api.AccessApi;
import com.increff.teamer.api.UserApi;
import com.increff.teamer.exception.CommonApiException;
import com.increff.teamer.model.data.UserData;
import com.increff.teamer.util.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserApi userApi;
    @Autowired
    private AccessApi accessApi;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String requestTokenHeader =  request.getHeader("Authorization");
//        System.out.println("this is token "+ requestTokenHeader);
//        System.out.println("this is uri + " + request.getRequestURI());
//        System.out.println("this is cookie "+ request.getAttribute("Cookies"));
        HttpSession session = request.getSession();
        System.out.println("this is session "+ session.getId());
        final String update = request.getHeader("Upgrade");
        Cookie[] cookies = request.getCookies();
//        if(cookies == null){
//            Cookie cookie = new Cookie("sessionId", "123456");
//            response.addCookie(cookie);
//        }
//        if(cookies != null){
//            System.out.println("this is cookies "+getCookieByName(request, "sessionId"));
//        }
        Cookie jwtCookie = getCookieByName(request, "jwtToken");

        String username = null;
        String jwtToken = null;
        if((requestTokenHeader !=null && requestTokenHeader.startsWith("Bearer ")) || Objects.equals(update, "websocket")){
            if(requestTokenHeader != null){
                jwtToken = requestTokenHeader.substring(7);
            }else {
//                System.out.println("this is url "+request.getRequestURI());
                if(jwtCookie != null){
                    jwtToken = jwtCookie.getValue();
                }
                else {
                    if(request.getRequestURI().split("/").length >2){
                        jwtToken = request.getRequestURI().split("/")[2];
                    }
                }
//                jwtToken = jwtCookie.getValue();
            }
            try {
                username = jwtUtils.extractUsername(jwtToken);
            }catch (ExpiredJwtException e){
//                e.printStackTrace();
                System.out.println("Token is Expired");
            }catch (Exception e){
//                 e.printStackTrace();
                 System.out.println("something went wrong when we are getting usrename from jwttoken");
            }

        }else {
            System.out.println("Token is not valid ");
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() ==null){
            UserData userData = (UserData) userApi.loadUserByUsername(username);
            try {
                userData.setAuthorities(accessApi.getUserAuthority(userData.getUserId()));
            } catch (CommonApiException e) {
                throw new RuntimeException(e);
            }
            final UserDetails userDetails = (UserDetails)  userData;

            try {
                if(jwtUtils.validateToken(jwtToken,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }else {
                    System.out.println("token validation failed");
                }
            } catch (CommonApiException e) {
                throw new RuntimeException(e);
            }
        }else {
            System.out.println("not able to get username from jwtutil extractusername methed");
        }
        filterChain.doFilter(request,response);
    }

    private Cookie getCookieByName(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookieName.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        return null;
    }
}
