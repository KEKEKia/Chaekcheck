package com.cc.auth.domain.member.service;

import com.cc.auth.domain.member.domain.Member;
import com.cc.auth.domain.member.dto.DeleteMemberResponseDto;
import com.cc.auth.domain.member.dto.UpdateNicknameResponseDto;
import com.cc.auth.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    public DeleteMemberResponseDto deleteMember(Member member) {
        memberRepository.delete(member);
        return DeleteMemberResponseDto.of(member);
    }

    public UpdateNicknameResponseDto updateNickname(Member member, String nickname) {
        member.updateNickname(nickname);
        return UpdateNicknameResponseDto.of(member);
    }
}
