package com.example.olebackend.login.handler;

import com.example.olebackend.apiPayLoad.ApiResponse;
import com.example.olebackend.converter.MemberConverter;
import com.example.olebackend.jwt.service.JwtService;
import com.example.olebackend.repository.MemberRepository;
import com.example.olebackend.web.dto.MemberResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final MemberRepository memberRepository;

    @Value("${jwt.access.expiration}")
    private String accessTokenExpiration;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String email = extractUsername(authentication); // 인증 정보에서 Username(email) 추출
        String accessToken = jwtService.createAccessToken(email); // JwtService의 createAccessToken을 사용하여 AccessToken 발급
        String refreshToken = jwtService.createRefreshToken(); // JwtService의 createRefreshToken을 사용하여 RefreshToken 발급

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken); // 응답 헤더에 AccessToken, RefreshToken 실어서 응답

        memberRepository.findByEmail(email)
                .ifPresent(user -> {
                    user.updateRefreshToken(refreshToken);
                    memberRepository.saveAndFlush(user);
                });
        log.info("로그인에 성공하였습니다. 이메일 : {}", email);
        log.info("로그인에 성공하였습니다. AccessToken : {}", accessToken);
        log.info("발급된 AccessToken 만료 기간 : {}", accessTokenExpiration);
        // ApiResponse 객체를 JSON 문자열로 변환

        ObjectMapper objectMapper = new ObjectMapper();
        ApiResponse<MemberResponse.getLoginResultDTO> apiResponse = loginSuccessResponse(authentication);
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);

        // HTTP 응답 본문에 ApiResponse JSON 문자열 작성
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonResponse);
    }
    public ApiResponse<MemberResponse.getLoginResultDTO> loginSuccessResponse(Authentication authentication) {
        String email = extractUsername(authentication);
        Long memberId = memberRepository.findByEmail(email).get().getId();
        String name = memberRepository.findByEmail(email).get().getName();
        return ApiResponse.onSuccess(MemberConverter.toLoginResultDTO(memberId, name));
    }

    private String extractUsername(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getUsername();
    }
}