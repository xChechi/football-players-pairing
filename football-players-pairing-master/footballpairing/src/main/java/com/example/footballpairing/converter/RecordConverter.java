package com.example.footballpairing.converter;

import com.example.footballpairing.dto.record.MatchDetailDto;
import com.example.footballpairing.dto.record.PlayerPairDto;
import com.example.footballpairing.entity.Match;
import com.example.footballpairing.entity.Player;
import com.example.footballpairing.exception.MatchNotFoundException;
import com.example.footballpairing.exception.PlayerNotFoundException;
import com.example.footballpairing.repository.MatchRepository;
import com.example.footballpairing.repository.PlayerRepository;
import com.example.footballpairing.utility.MatchDescriptionFormatter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class RecordConverter {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final MatchDescriptionFormatter matchDescriptionFormatter;

    public PlayerPairDto toResponse(Integer playerOneId, Integer playerTwoId, List<Object[]> results) {

        Player playerOne = playerRepository.findById(playerOneId).orElseThrow(() -> new PlayerNotFoundException("Player not exist in database"));
        Player playerTwo = playerRepository.findById(playerTwoId).orElseThrow(() -> new PlayerNotFoundException("Player not exist in database"));

        List<MatchDetailDto> matchDetails = results.stream()
                .filter(result -> playerOneId.equals(result[0]) && playerTwoId.equals(result[1]))
                .map(result -> {
                    Integer matchId = (Integer) result[3];
                    int playedTime = ((Number) result[4]).intValue();

                    Match match = matchRepository.findById(matchId).orElseThrow(() -> new MatchNotFoundException("Match not exist in database")); // You'll need a method to fetch the match

                    String matchDescription = matchDescriptionFormatter.formatMatchDescription(match);

                    return new MatchDetailDto(matchDescription, playedTime);
                })
                .collect(Collectors.toList());

        int totalPlayedTime = matchDetails.stream()
                .mapToInt(MatchDetailDto::getPlayedTime)
                .sum();

        return PlayerPairDto.builder()
                .playerOneFullName(playerOne.getFullName())
                .playerTwoFullName(playerTwo.getFullName())
                .matchDetails(matchDetails)
                .totalPlayedTime(totalPlayedTime)
                .build();
    }

}
