package com.example.footballpairing.controller;

import com.example.footballpairing.dto.team.TeamRequest;
import com.example.footballpairing.dto.team.TeamResponse;
import com.example.footballpairing.dto.team.TeamUpdateRequest;
import com.example.footballpairing.service.TeamService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@AllArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    public ResponseEntity<List<TeamResponse>> getAllTeams () {
        return ResponseEntity.status(HttpStatus.OK).body(teamService.getAllTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamResponse> getTeamById (@PathVariable Integer id) {
        TeamResponse response = teamService.getTeamById(id);
        if (response != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<TeamResponse> createTeam (@RequestBody @Valid TeamRequest request) {
        TeamResponse createdTeam = teamService.createTeam(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTeam);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeamById (@PathVariable Integer id) {
        teamService.deleteTeamById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Team deleted successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamResponse> updateTeam (@PathVariable Integer id, @RequestBody @Valid TeamUpdateRequest request) {
        TeamResponse updatedTeam = teamService.updateTeam(id, request);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTeam);
    }
}
