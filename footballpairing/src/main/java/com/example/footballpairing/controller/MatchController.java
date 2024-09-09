package com.example.footballpairing.controller;

import com.example.footballpairing.dto.match.MatchRequest;
import com.example.footballpairing.dto.match.MatchResponse;
import com.example.footballpairing.dto.match.MatchUpdateRequest;
import com.example.footballpairing.service.MatchService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
@AllArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping
    public ResponseEntity<List<MatchResponse>> getAllMatches () {
        return ResponseEntity.status(HttpStatus.OK).body(matchService.getAllMatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchResponse> getMatchById (@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(matchService.getMatchById(id));
    }

    @PostMapping
    public ResponseEntity<MatchResponse> createMatch (@Valid @RequestBody MatchRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.createMatch(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchResponse> updateMatchById (@PathVariable Integer id, @Valid @RequestBody MatchUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(matchService.updateMatch(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMatchById (@PathVariable Integer id) {
        matchService.deleteMatchById(id);
        return ResponseEntity.status(HttpStatus.OK).body("Match deleted successfully");
    }
}
