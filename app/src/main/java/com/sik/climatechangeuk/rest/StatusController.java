package com.sik.climatechangeuk.rest;

import com.sik.climatechangeuk.status.AdapterStatus;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class StatusController {
    private final AdapterStatus adapterStatus;

    @GetMapping("/status")
    public ResponseEntity<AdapterStatus> getStatus() {
        return new ResponseEntity<>(adapterStatus, HttpStatus.OK);
    }
}
