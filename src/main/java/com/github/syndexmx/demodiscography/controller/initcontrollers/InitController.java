package com.github.syndexmx.demodiscography.controller.initcontrollers;

import com.github.syndexmx.demodiscography.services.initservices.InitQueenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class InitController {

    private InitQueenService initQueenService;

    @Autowired
    public InitController(InitQueenService initQueenService) {
        this.initQueenService = initQueenService;
    }

    @PostMapping("/api/v0/init/queen")
    public ResponseEntity<String> initQueen() {
        log.info("POST @ /api/v0/init/queen" + " : Special test base initialization");
        return new ResponseEntity<>(initQueenService.createQueen(), HttpStatus.CREATED);
    }

}
