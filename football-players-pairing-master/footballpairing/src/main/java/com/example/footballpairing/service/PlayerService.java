package com.example.footballpairing.service;

import com.example.footballpairing.dto.player.PlayerRequest;
import com.example.footballpairing.dto.player.PlayerResponse;
import com.example.footballpairing.dto.player.PlayerUpdateRequest;

import java.util.List;

public interface PlayerService {

    List<PlayerResponse> getAllPlayers ();
    PlayerResponse getPlayerById (Integer id);
    void deletePlayerById (Integer id);
    PlayerResponse createPlayer (PlayerRequest request);
    PlayerResponse updatePlayer (Integer id, PlayerUpdateRequest request);

    boolean existByFullName (String fullName);
}
