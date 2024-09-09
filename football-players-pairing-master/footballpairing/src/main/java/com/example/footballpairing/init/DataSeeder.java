package com.example.footballpairing.init;

import com.example.footballpairing.entity.Match;
import com.example.footballpairing.entity.Player;
import com.example.footballpairing.entity.Team;
import com.example.footballpairing.entity.Record;
import com.example.footballpairing.exception.MatchNotFoundException;
import com.example.footballpairing.exception.PlayerNotFoundException;
import com.example.footballpairing.exception.TeamNotFoundException;
import com.example.footballpairing.repository.MatchRepository;
import com.example.footballpairing.repository.PlayerRepository;
import com.example.footballpairing.repository.RecordRepository;
import com.example.footballpairing.repository.TeamRepository;
import com.example.footballpairing.utility.DateParser;
import com.example.footballpairing.utility.ScoreParser;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final RecordRepository recordRepository;
    private final DateParser dateParser;
    private final ScoreParser scoreParser;

    @Override
    public void run(String... args) throws Exception {
        if (teamRepository.count() == 0) {
            seedTeams();
        }
        if (playerRepository.count() == 0) {
            seedPlayers();
        }
        if (matchRepository.count() == 0) {
            seedMatches();
        }
        if (recordRepository.count() == 0) {
            seedRecords();
        }
    }

    private void seedPlayers() throws IOException {
        Resource resource = new ClassPathResource("csv/players.csv");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                Team team = teamRepository.findById(Integer.parseInt(values[4])).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));

                var player = Player.builder()
                        .teamNumber(values[1])
                        .position(values[2])
                        .fullName(values[3])
                        .team(team)
                        .build();

                playerRepository.save(player);
            }
        }
    }

    private void seedTeams() throws IOException {
        Resource resource = new ClassPathResource("csv/teams.csv");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                var team = Team.builder()
                        .name(values[1])
                        .manager(values[2])
                        .group(values[3])
                        .build();

                teamRepository.save(team);
            }
        }
    }

    private void seedMatches() throws IOException {
        Resource resource = new ClassPathResource("csv/matches.csv");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                Team firstTeam = teamRepository.findById(Integer.parseInt(values[1])).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));
                Team secondTeam = teamRepository.findById(Integer.parseInt(values[2])).orElseThrow(() -> new TeamNotFoundException("Team not exist in database"));

                LocalDate matchDate = dateParser.parseDate(values[3]);

                var match = Match.builder()
                        .firstTeam(firstTeam)
                        .secondTeam(secondTeam)
                        .date(matchDate)
                        .regularScore(scoreParser.extractRegularScore(values[4]))
                        .penaltyScore(scoreParser.extractPenaltyScore(values[4]))
                        .build();

                matchRepository.save(match);
            }
        }
    }

    private void seedRecords() throws IOException {
        Resource resource = new ClassPathResource("csv/records.csv");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                Player player = playerRepository.findById(Integer.parseInt(values[1])).orElseThrow(() -> new PlayerNotFoundException("Player not exist in database"));
                Match match = matchRepository.findById(Integer.parseInt(values[2])).orElseThrow(() -> new MatchNotFoundException("Match not exist in database"));

                var record = Record.builder()
                        .player(player)
                        .match(match)
                        .fromMinutes(Integer.parseInt(values[3]))
                        .toMinutes("NULL".equalsIgnoreCase(values[4].trim()) ? 90 : Integer.parseInt(values[4].trim()))
                        .build();

                recordRepository.save(record);
            }
        }
    }

}
