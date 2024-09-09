    package com.example.footballpairing.service.impl;

    import com.example.footballpairing.converter.RecordConverter;
    import com.example.footballpairing.dto.record.PlayerPairDto;
    import com.example.footballpairing.repository.RecordRepository;
    import lombok.AllArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.util.*;
    import java.util.stream.Collectors;

    @Service
    @AllArgsConstructor
    public class RecordService {

        private final RecordRepository recordRepository;
        private final RecordConverter recordConverter;

        public List<PlayerPairDto> getMaxPlayingPairs() {
            List<Object[]> results = recordRepository.findMaxPlayingPairsWithMatchDetails();

            Map<AbstractMap.SimpleEntry<Integer, Integer>, List<Object[]>> playerPairResultsMap = new HashMap<>();

            for (Object[] result : results) {
                Integer player1Id = (Integer) result[0];
                Integer player2Id = (Integer) result[1];

                AbstractMap.SimpleEntry<Integer, Integer> playerPair = new AbstractMap.SimpleEntry<>(player1Id, player2Id);
                playerPairResultsMap.computeIfAbsent(playerPair, k -> new ArrayList<>()).add(result);

                AbstractMap.SimpleEntry<Integer, Integer> reversedPair = new AbstractMap.SimpleEntry<>(player2Id, player1Id);
                playerPairResultsMap.computeIfAbsent(reversedPair, k -> new ArrayList<>()).add(result);
            }

            Set<AbstractMap.SimpleEntry<Integer, Integer>> processedPairs = new HashSet<>();
            List<PlayerPairDto> playerPairDtos = new ArrayList<>();

            for (Map.Entry<AbstractMap.SimpleEntry<Integer, Integer>, List<Object[]>> entry : playerPairResultsMap.entrySet()) {
                AbstractMap.SimpleEntry<Integer, Integer> playerPair = entry.getKey();
                if (!processedPairs.contains(playerPair)) {
                    PlayerPairDto dto = recordConverter.toResponse(playerPair.getKey(), playerPair.getValue(), entry.getValue());
                    processedPairs.add(playerPair);
                    processedPairs.add(new AbstractMap.SimpleEntry<>(playerPair.getValue(), playerPair.getKey()));
                    playerPairDtos.add(dto);
                }
            }

            int maxPlayedTime = playerPairDtos.stream()
                    .mapToInt(PlayerPairDto::getTotalPlayedTime)
                    .max()
                    .orElse(0);

            return playerPairDtos.stream()
                    .filter(dto -> dto.getTotalPlayedTime() == maxPlayedTime)
                    .collect(Collectors.toList());
        }
    }


