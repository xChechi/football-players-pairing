package com.example.footballpairing.converter;

import com.example.footballpairing.dto.team.TeamRequest;
import com.example.footballpairing.dto.team.TeamResponse;
import com.example.footballpairing.entity.Team;
import org.springframework.stereotype.Component;

@Component
public class TeamConverter implements EntityConverter<Team, TeamRequest, TeamResponse> {

    @Override
    public Team create(TeamRequest request) {
        return Team.builder()
                .name(request.getName())
                .manager(request.getManager())
                .group(request.getGroup())
                .build();
    }

    @Override
    public TeamResponse toResponse(Team entity) {
        return TeamResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .manager(entity.getManager())
                .group(entity.getGroup())
                .players(entity.getPlayers())
                .build();
    }
}
