package com.cc.auth.domain.auth.service;

import com.cc.auth.domain.auth.dto.PrincipalDetails;
import com.cc.auth.domain.auth.service.oauth2.provider.GoogleUserInfo;
import com.cc.auth.domain.auth.service.oauth2.provider.KakaoUserInfo;
import com.cc.auth.domain.auth.service.oauth2.provider.OAuth2UserInfo;
import com.cc.auth.domain.member.domain.Member;
import com.cc.auth.domain.member.domain.OauthType;
import com.cc.auth.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;
//    private final MetaDataService metaDataService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;

        if(registrationId.equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }else if (registrationId.equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }

        OauthType provider = oAuth2UserInfo.getProvider(); // kakao
        String providerId = oAuth2UserInfo.getProviderId();

        String nickname = null;
        try {
            nickname = oAuth2UserInfo.getNickname();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("nickanme"+ nickname);

        String username = provider+"_"+providerId;  // kakao_123102401051349
        String email = oAuth2UserInfo.getEmail();
//      Role role = Role.GUEST;

        Optional<Member> memberOptional = memberRepository.findByOauthIdentifier(username);

        String finalNickname = nickname;
        Member memberEntity = memberOptional.orElseGet(() -> {
            return Member.builder()
                    .oauthType(provider)
                    .oauthIdentifier(username)
                    .activated(true)
                    .nickname(finalNickname)
//                  .role(role)
                    .build();
        });
        memberOptional.ifPresentOrElse(
                member -> {  },
                () -> memberRepository.save(memberEntity)
        );

        return new PrincipalDetails(memberEntity, oAuth2User.getAttributes());
    }
}