package com.example.footballpairing.controller;

import com.example.footballpairing.dto.record.PlayerPairDto;
import com.example.footballpairing.service.impl.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/records")
public class RecordController {

    @Autowired
    private RecordService recordService;

    @GetMapping("/longest-playing-pairs")
    public ResponseEntity<List<PlayerPairDto>> getLongestPlayingPairs() {
        List<PlayerPairDto> result = recordService.getMaxPlayingPairs();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

