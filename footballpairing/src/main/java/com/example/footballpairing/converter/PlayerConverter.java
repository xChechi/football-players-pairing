package com.example.footballpairing.converter;

import com.example.footballpairing.dto.player.PlayerRequest;
import com.example.footballpairing.dto.player.PlayerResponse;
import com.example.footballpairing.entity.Player;
import com.example.footballpairing.entity.Team;
import com.example.footballpairing.exception.TeamNotFoundException;
import com.example.footballpairing.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PlayerConverter implements EntityConverter<Player, PlayerRequest, PlayerResponse> {

    private final TeamRepository teamRepository;

    @Override
    public Player create(PlayerRequest request) {

        Team team = teamRepository.findById(request.getTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));

        return Player.builder()
                .teamNumber(request.getTeamNumber())
                .position(request.getPosition())
                .fullName(request.getFullName())
                .team(team)
                .build();
    }

    @Override
    public PlayerResponse toResponse(Player entity) {
        return PlayerResponse.builder()
                .id(entity.getId())
                .teamNumber(entity.getTeamNumber())
                .position(entity.getPosition())
                .fullName(entity.getFullName())
                .teamName(entity.getTeam().getName())
                .build();
    }
}
