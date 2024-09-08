package com.example.footballpairing.service.impl;

import com.example.footballpairing.converter.MatchConverter;
import com.example.footballpairing.dto.match.MatchRequest;
import com.example.footballpairing.dto.match.MatchResponse;
import com.example.footballpairing.dto.match.MatchUpdateRequest;
import com.example.footballpairing.entity.Match;
import com.example.footballpairing.entity.Team;
import com.example.footballpairing.exception.DuplicateMatchException;
import com.example.footballpairing.exception.MatchNotFoundException;
import com.example.footballpairing.exception.TeamNotFoundException;
import com.example.footballpairing.repository.MatchRepository;
import com.example.footballpairing.repository.TeamRepository;
import com.example.footballpairing.service.MatchService;
import com.example.footballpairing.utility.DateParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final MatchConverter matchConverter;
    private final TeamRepository teamRepository;
    private final DateParser dateParser;

    @Override
    public List<MatchResponse> getAllMatches() {
        List<Match> matches = matchRepository.findAll();
        List<MatchResponse> responses = new ArrayList<>();

        for (Match m : matches) {
            MatchResponse response = matchConverter.toResponse(m);
            responses.add(response);
        }
        return responses;
    }

    @Override
    public MatchResponse getMatchById(Integer id) {
        Match match = matchRepository.findById(id).orElseThrow(() -> new MatchNotFoundException("Match not exist in database"));
        return matchConverter.toResponse(match);
    }

    @Override
    public MatchResponse createMatch(MatchRequest request) {
        Team firstTeam = teamRepository.findById(request.getFirstTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));
        Team secondTeam = teamRepository.findById(request.getSecondTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));

        if (matchExists(firstTeam, secondTeam, request.getDate())) {
            throw new DuplicateMatchException("Match already exist in database");
        }

        Match match = matchConverter.create(request);
        Match savedMatch = matchRepository.save(match);
        return matchConverter.toResponse(savedMatch);
    }

    @Override
    public MatchResponse updateMatch(Integer id, MatchUpdateRequest request) {
        Team firstTeam = teamRepository.findById(request.getFirstTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));
        Team secondTeam = teamRepository.findById(request.getSecondTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));

        Match match = matchRepository.findById(id).orElseThrow(() -> new MatchNotFoundException("Match not exist in database"));

        var date = dateParser.parseDate(request.getDate());
        if(date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date must be in the past");
        }

        match.setFirstTeam(firstTeam);
        match.setSecondTeam(secondTeam);
        match.setDate(date);
        match.setRegularScore(request.getRegularScore());
        match.setPenaltyScore(request.getPenaltyScore());

        Match savedMatch = matchRepository.save(match);
        return matchConverter.toResponse(savedMatch);
    }

    @Override
    public void deleteMatchById(Integer id) {
        matchRepository.deleteById(id);
    }

    @Override
    public boolean matchExists(Team firstTeam, Team secondTeam, String date) {
        LocalDate parsedDate = dateParser.parseDate(date);
        return matchRepository.findAll().stream()
                .anyMatch(match -> match.getFirstTeam().equals(firstTeam) &&
                        match.getSecondTeam().equals(secondTeam) &&
                        match.getDate().equals(parsedDate));
    }

}
