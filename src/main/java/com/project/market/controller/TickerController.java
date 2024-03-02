package com.project.market.controller;

import com.project.market.entity.Ticker;
import com.project.market.service.TickerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ticker")
@RequiredArgsConstructor
public class TickerController {
    private final TickerService tickerService;

    @GetMapping
    public ResponseEntity<List<Ticker>> searchMarketTicker(@RequestParam @Valid String marketNameList) {
        return ResponseEntity.ok(tickerService.searchCoinTicker(marketNameList));
    }
}
