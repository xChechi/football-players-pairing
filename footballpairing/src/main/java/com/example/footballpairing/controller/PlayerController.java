package com.example.footballpairing.controller;

import com.example.footballpairing.dto.player.PlayerRequest;
import com.example.footballpairing.dto.player.PlayerResponse;
import com.example.footballpairing.dto.player.PlayerUpdateRequest;
import com.example.footballpairing.service.PlayerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/players")
@AllArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    public ResponseEntity<List<PlayerResponse>> getAllPlayers () {
        return ResponseEntity.status(HttpStatus.OK).body(playerService.getAllPlayers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerResponse> getPlayerById (@PathVariable Integer id) {
        PlayerResponse response = playerService.getPlayerById(id);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<PlayerResponse> createPlayer (@RequestBody @Valid PlayerRequest request) {
        PlayerResponse createdPlayer = playerService.createPlayer(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPlayer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayerById (@PathVariable Integer id) {
        playerService.deletePlayerById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Player deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerResponse> updatePlayerById (@PathVariable Integer id, @Valid @RequestBody PlayerUpdateRequest request) {
        PlayerResponse updatedPlayer = playerService.updatePlayer(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedPlayer);
    }
}
