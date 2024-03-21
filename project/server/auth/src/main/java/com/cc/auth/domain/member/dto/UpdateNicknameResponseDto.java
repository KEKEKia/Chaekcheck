package com.cc.auth.domain.member.dto;

import com.cc.auth.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateNicknameResponseDto {

    private int memberId;
    private String nickname;

    public static UpdateNicknameResponseDto of(Member member){

        return UpdateNicknameResponseDto.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}