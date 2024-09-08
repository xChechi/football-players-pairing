package com.example.footballpairing.service;

import com.example.footballpairing.dto.match.MatchRequest;
import com.example.footballpairing.dto.match.MatchResponse;
import com.example.footballpairing.dto.match.MatchUpdateRequest;
import com.example.footballpairing.entity.Team;

import java.time.LocalDate;
import java.util.List;

public interface MatchService {

    List<MatchResponse> getAllMatches ();
    MatchResponse getMatchById (Integer id);
    MatchResponse createMatch (MatchRequest request);
    MatchResponse updateMatch (Integer id, MatchUpdateRequest request);
    void deleteMatchById (Integer id);

    boolean matchExists(Team firstTeam, Team secondTeam, LocalDate date);
}
