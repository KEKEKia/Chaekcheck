package com.cc.auth.domain.auth.service.oauth2.provider;

import com.cc.auth.domain.member.domain.OauthType;

import java.io.IOException;
import java.util.Map;

public class GoogleUserInfo implements OAuth2UserInfo{

    private Map<String, Object> attributes;

    public GoogleUserInfo(Map<String, Object> attributes){
        this.attributes=attributes;
    }
    @Override
    public String getProviderId() {
        return attributes.get("sub").toString();
    }

    @Override
    public OauthType getProvider() {
        return OauthType.GOOGLE;
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }

    @Override
    public String getNickname() throws IOException {
        return (String) attributes.get("name");
    }
}