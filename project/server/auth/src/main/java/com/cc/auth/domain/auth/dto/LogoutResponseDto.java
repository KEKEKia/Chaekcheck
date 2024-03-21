package com.cc.auth.domain.auth.dto;

import com.cc.auth.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LogoutResponseDto {

    private int memberid;

    public static LogoutResponseDto of(Member member){
        return LogoutResponseDto.builder()
                .memberid(member.getId())
                .build();
    }
}
