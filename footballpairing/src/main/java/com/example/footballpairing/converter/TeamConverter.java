package com.example.footballpairing.converter;

import com.example.footballpairing.dto.player.PlayerResponse;
import com.example.footballpairing.dto.team.TeamRequest;
import com.example.footballpairing.dto.team.TeamResponse;
import com.example.footballpairing.entity.Team;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TeamConverter implements EntityConverter<Team, TeamRequest, TeamResponse> {

    private final PlayerConverter playerConverter;

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

        List<PlayerResponse> players = entity.getPlayers().stream()
                .map(playerConverter::toResponse)
                .toList();

        return TeamResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .manager(entity.getManager())
                .group(entity.getGroup())
                .players(players)
                .build();
    }
}
