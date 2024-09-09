package com.example.footballpairing.controller;

import com.example.footballpairing.dto.record.PlayerPairDto;
import com.example.footballpairing.service.impl.RecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/records")
@AllArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @GetMapping("/longest-playing-pairs")
    public ResponseEntity<List<PlayerPairDto>> getLongestPlayingPairs() {
        List<PlayerPairDto> result = recordService.getMaxPlayingPairs();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}

