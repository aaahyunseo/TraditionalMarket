package com.example.traditionalmarket.authentication;

import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.exception.NotFoundException;
import com.example.traditionalmarket.exception.errorcode.ErrorCode;
import com.example.traditionalmarket.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final UserRepository userRepository;
    private final AuthenticationContext authenticationContext;
    private final AccessTokenProvider accessTokenProvider;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return true;
        }

        String accessToken = authorizationHeader.substring(7);

        try {
            UUID userId = UUID.fromString(accessTokenProvider.getPayload(accessToken));
            User user = findExistingUser(userId);
            authenticationContext.setPrincipal(user);
        } catch (Exception e) {
            log.warn("Invalid token during authentication: {}", e.getMessage());
            throw new NotFoundException(ErrorCode.INVALID_TOKEN);
        }
        return true;
    }

    private User findExistingUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
    }
}

