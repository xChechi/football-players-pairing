package com.example.footballpairing.converter;

import com.example.footballpairing.dto.player.PlayerResponse;
import com.example.footballpairing.dto.team.TeamRequest;
import com.example.footballpairing.dto.team.TeamResponse;
import com.example.footballpairing.entity.Team;
import com.example.footballpairing.repository.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class TeamConverter implements EntityConverter<Team, TeamRequest, TeamResponse> {

    private final PlayerConverter playerConverter;
    private final PlayerRepository playerRepository;

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

        List<PlayerResponse> players = playerRepository.findAll().stream()
                .filter(player -> player.getTeam().getId() == entity.getId())
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
