package com.example.footballpairing.converter;

import com.example.footballpairing.dto.match.MatchRequest;
import com.example.footballpairing.dto.match.MatchResponse;
import com.example.footballpairing.entity.Match;
import com.example.footballpairing.entity.Team;
import com.example.footballpairing.exception.TeamNotFoundException;
import com.example.footballpairing.utility.DateParser;
import com.example.footballpairing.repository.MatchRepository;
import com.example.footballpairing.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MatchConverter implements EntityConverter<Match, MatchRequest, MatchResponse> {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final TeamConverter teamConverter;
    private final DateParser dateParser;

    @Override
    public Match create(MatchRequest request) {
        Team firstTeam = teamRepository.findById(request.getFirstTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));
        Team secondTeam = teamRepository.findById(request.getSecondTeamId()).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));

        return Match.builder()
                .firstTeam(firstTeam)
                .secondTeam(secondTeam)
                .date(dateParser.parseDate(request.getDate().toString()))
                .regularScore(request.getRegularScore())
                .penaltyScore(request.getPenaltyScore())
                .build();
    }

    @Override
    public MatchResponse toResponse(Match entity) {
        return MatchResponse.builder()
                .id(entity.getId())
                .firstTeamName(entity.getFirstTeam().getName())
                .secondTeamName(entity.getSecondTeam().getName())
                .date(entity.getDate())
                .regularScore(entity.getRegularScore())
                .penaltyScore(entity.getPenaltyScore())
                .build();
    }
}
