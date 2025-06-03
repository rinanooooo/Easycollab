package com.irnproj.easycollab.module.team.dto;

import com.irnproj.easycollab.module.team.entity.TeamMember;
import com.irnproj.easycollab.module.user.entity.User;
import lombok.*;

@Getter
@Builder
public class TeamMemberResponseDto {
    private Long id;                // 팀원 PK
    private String nickname;        // 사용자 닉네임
    private String roleCode;        // 역할 코드

    @Builder
    public TeamMemberResponseDto(Long id, String nickname, String roleCode ) {
        this.id = id;
        this.nickname = nickname;
        this.roleCode = roleCode;
    }

    public static TeamMemberResponseDto from(TeamMember teamMember) {
        return TeamMemberResponseDto.builder()
            .id(teamMember.getId())
            .nickname(teamMember.getUser().getNickname())
            .roleCode(teamMember.getRole() != null ? teamMember.getRole().getCode() : null)
            .build();
    }
}