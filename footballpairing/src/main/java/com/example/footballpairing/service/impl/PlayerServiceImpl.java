package com.example.footballpairing.service.impl;

import com.example.footballpairing.converter.PlayerConverter;
import com.example.footballpairing.dto.player.PlayerRequest;
import com.example.footballpairing.dto.player.PlayerResponse;
import com.example.footballpairing.dto.player.PlayerUpdateRequest;
import com.example.footballpairing.entity.Player;
import com.example.footballpairing.entity.Team;
import com.example.footballpairing.exception.DuplicatePlayerException;
import com.example.footballpairing.exception.PlayerNotFoundException;
import com.example.footballpairing.exception.TeamNotFoundException;
import com.example.footballpairing.repository.PlayerRepository;
import com.example.footballpairing.repository.TeamRepository;
import com.example.footballpairing.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerConverter playerConverter;
    private final TeamRepository teamRepository;

    @Override
    public List<PlayerResponse> getAllPlayers() {
        List<Player> players = playerRepository.findAll();
        List<PlayerResponse> responses = new ArrayList<>();

        for (Player p : players) {
            PlayerResponse response = playerConverter.toResponse(p);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public PlayerResponse getPlayerById(Integer id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player not exist in database"));
        return playerConverter.toResponse(player);
    }

    @Override
    public void deletePlayerById(Integer id) {
        playerRepository.deleteById(id);
    }

    @Override
    public PlayerResponse createPlayer(PlayerRequest request) {
        if (existByFullName(request.getFullName())) {
            throw new DuplicatePlayerException("Player already exist");
        }
        Player player = playerConverter.create(request);
        Player savedPlayer = playerRepository.save(player);
        return playerConverter.toResponse(savedPlayer);
    }

    @Override
    public PlayerResponse updatePlayer(Integer id, PlayerUpdateRequest request) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new PlayerNotFoundException("Player not exist in database"));
        Team team = teamRepository.findById(request.getTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));

        player.setTeamNumber(request.getTeamNumber());
        player.setPosition(request.getPosition());
        player.setFullName(request.getFullName());
        player.setTeam(team);

        Player savedPlayer = playerRepository.save(player);
        return playerConverter.toResponse(savedPlayer);
    }

    @Override
    public boolean existByFullName(String fullName) {
        return playerRepository.findByFullName(fullName).isPresent();
    }


}
