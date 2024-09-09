package com.example.footballpairing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<String> handlerPlayerNotFound (PlayerNotFoundException msg) {
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TeamNotFoundException.class)
    public ResponseEntity<String> handlerTeamNotFound (TeamNotFoundException msg) {
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MatchNotFoundException.class)
    public ResponseEntity<String> handlerMatchNotFound (MatchNotFoundException msg) {
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatePlayerException.class)
    public ResponseEntity<String> handlerDuplicatePlayer (DuplicatePlayerException msg) {
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateTeamException.class)
    public ResponseEntity<String> handlerDuplicateTeam (DuplicateTeamException msg) {
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UnsupportedDateFormatException.class)
    public ResponseEntity<String> handlerUnsupportedDateFormat (UnsupportedDateFormatException msg) {
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DuplicateMatchException.class)
    public ResponseEntity<String> handlerDuplicateMatch (DuplicateMatchException msg) {
        return new ResponseEntity<>(msg.getMessage(), HttpStatus.CONFLICT);
    }
}
