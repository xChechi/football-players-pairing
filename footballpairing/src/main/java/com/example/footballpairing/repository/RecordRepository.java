package com.example.footballpairing.repository;

import com.example.footballpairing.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Integer> {

    @Query(value = """
    WITH PlayerPairTimes AS (
        SELECT
            r1.player_id AS player1Id,
            r2.player_id AS player2Id,
            r1.match_id AS matchId,
            LEAST(COALESCE(r1.to_minutes, 90), COALESCE(r2.to_minutes, 90)) -
            GREATEST(COALESCE(r1.from_minutes, 0), COALESCE(r2.from_minutes, 0)) AS playedTimeInMatch
        FROM records r1
        JOIN records r2 ON r1.match_id = r2.match_id AND r1.player_id <> r2.player_id
    ),
    TotalPlayedTimes AS (
        SELECT
            player1Id,
            player2Id,
            SUM(playedTimeInMatch) AS totalPlayedTime
        FROM PlayerPairTimes
        GROUP BY player1Id, player2Id
    ),
    MaxPlayedTime AS (
        SELECT MAX(totalPlayedTime) AS maxPlayedTime
        FROM TotalPlayedTimes
    )
    SELECT
        ppt.player1Id AS player1Id,
        ppt.player2Id AS player2Id,
        tpt.totalPlayedTime AS totalPlayedTime,
        ppt.matchId AS matchId,
        ppt.playedTimeInMatch AS playedTimeInMatch
    FROM PlayerPairTimes ppt
    JOIN TotalPlayedTimes tpt ON ppt.player1Id = tpt.player1Id AND ppt.player2Id = tpt.player2Id
    JOIN MaxPlayedTime mpt ON tpt.totalPlayedTime = mpt.maxPlayedTime
    """, nativeQuery = true)
    List<Object[]> findMaxPlayingPairsWithMatchDetails();

}
