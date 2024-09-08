package com.example.footballpairing.init;

import com.example.footballpairing.entity.Match;
import com.example.footballpairing.entity.Player;
import com.example.footballpairing.entity.Team;
import com.example.footballpairing.entity.Record;
import com.example.footballpairing.exception.MatchNotFoundException;
import com.example.footballpairing.exception.PlayerNotFoundException;
import com.example.footballpairing.exception.TeamNotFoundException;
import com.example.footballpairing.exception.UnsupportedDateFormatException;
import com.example.footballpairing.repository.MatchRepository;
import com.example.footballpairing.repository.PlayerRepository;
import com.example.footballpairing.repository.RecordRepository;
import com.example.footballpairing.repository.TeamRepository;
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
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@AllArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final RecordRepository recordRepository;

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

                LocalDate matchDate = parseDateWithMultipleFormats(values[3]);

                var match = Match.builder()
                        .firstTeam(firstTeam)
                        .secondTeam(secondTeam)
                        .date(matchDate)
                        .regularScore(extractRegularScore(values[4]))
                        .penaltyScore(extractPenaltyScore(values[4]))
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

    private String extractRegularScore(String score) {
        Pattern patternWithPenalties = Pattern.compile("(\\d+)?\\((\\d+)\\)-(\\d+)?\\((\\d+)\\)");
        Matcher matcherWithPenalties = patternWithPenalties.matcher(score);

        if (matcherWithPenalties.find()) {
            String firstTeamScore = matcherWithPenalties.group(1) != null ? matcherWithPenalties.group(1) : "0";
            String secondTeamScore = matcherWithPenalties.group(3) != null ? matcherWithPenalties.group(3) : "0";
            return firstTeamScore + "-" + secondTeamScore;
        }

        Pattern patternWithoutPenalties = Pattern.compile("(\\d+)-(\\d+)");
        Matcher matcherWithoutPenalties = patternWithoutPenalties.matcher(score);

        if (matcherWithoutPenalties.find()) {
            return matcherWithoutPenalties.group(1) + "-" + matcherWithoutPenalties.group(2);
        }
        return "";
    }

    private String extractPenaltyScore(String score) {
        Pattern pattern = Pattern.compile("\\d+\\((\\d+)\\)-\\d+\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(score);
        if (matcher.find()) {
            return matcher.group(1) + "-" + matcher.group(2);
        }
        return "";
    }

    private LocalDate parseDateWithMultipleFormats(String dateStr) {
        List<DateTimeFormatter> formatters = List.of(
                DateTimeFormatter.ofPattern("M/d/yyyy"),
                DateTimeFormatter.ofPattern("MM/dd/yyyy"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd"),
                DateTimeFormatter.ofPattern("d MMM yyyy"),
                DateTimeFormatter.ofPattern("dd MMM yyyy"),
                DateTimeFormatter.ofPattern("d-MMM-yyyy"),
                DateTimeFormatter.ofPattern("dd-MMM-yyyy"),
                DateTimeFormatter.ofPattern("d MMMM yyyy"),
                DateTimeFormatter.ofPattern("dd MMMM yyyy"),
                DateTimeFormatter.ISO_DATE,
                DateTimeFormatter.ISO_LOCAL_DATE
        );

        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException ignored) {
            }
        }

        throw new UnsupportedDateFormatException("Invalid Date format");
    }

}
