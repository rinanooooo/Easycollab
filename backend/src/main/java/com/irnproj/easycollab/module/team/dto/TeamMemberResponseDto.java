package com.irnproj.easycollab.module.team.dto;

import com.irnproj.easycollab.module.team.entity.TeamMember;
import com.irnproj.easycollab.module.user.entity.User;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeamMemberResponseDto {
    private Long userId;
    private String username;
    private String nickname;
    private String email;
    private String role; // ex: "TEAM_LEADER", "TEAM_MEMBER"

    public static TeamMemberResponseDto fromEntity(TeamMember member) {
        User user = member.getUser();
        return TeamMemberResponseDto.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .nickname(user.getNickname())
            .email(user.getEmail())
            .role(member.getRole().getCode()) // ComCode 기준
            .build();
    }
}