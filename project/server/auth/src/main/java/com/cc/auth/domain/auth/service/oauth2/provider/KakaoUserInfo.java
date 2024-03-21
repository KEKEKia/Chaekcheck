package com.cc.auth.domain.auth.service.oauth2.provider;

import com.cc.auth.domain.member.domain.OauthType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class KakaoUserInfo implements OAuth2UserInfo{

    private final Map<String, Object> attributes;

    public KakaoUserInfo(Map<String, Object> attributes){
        this.attributes=attributes;
    }
    @Override
    public String getProviderId() {
        return (String) attributes.get("id").toString();
    }

    @Override
    public OauthType getProvider() {
        return OauthType.KAKAO;
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getNickname() {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(this.attributes);

            JsonNode jsonNode = objectMapper.readTree(json);

            JsonNode properties = jsonNode.get("properties");
            String nickname = properties.get("nickname").asText();

            return nickname;
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace(); // 에러 메시지를 출력하거나 로그에 기록할 수 있음
            return null; // 예외 발생 시 반환할 값 또는 예외 처리 방법을 선택
        }
    }
}