package com.cc.auth.domain.member.dto;

import com.cc.auth.domain.member.domain.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteMemberResponseDto {

    private int memberId;

    public static DeleteMemberResponseDto of(Member member){

        return DeleteMemberResponseDto.builder()
                .memberId(member.getId())
                .build();
    }
}