package com.irnproj.easycollab.module.team.dto;

import com.irnproj.easycollab.module.team.entity.TeamMember;
import com.irnproj.easycollab.module.user.entity.User;
import lombok.*;

@Getter
@Builder
public class TeamMemberResponseDto {
    private Long userId;
    private String nickname;
    private String roleName;
}