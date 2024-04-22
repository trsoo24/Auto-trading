package com.project.market.controller;

import com.project.market.entity.Candle;
import com.project.market.entity.dto.CandleDto;
import com.project.market.entity.dto.CandleToNowDto;
import com.project.market.entity.dto.MinuteCandleDto;
import com.project.market.entity.dto.MinuteCandleToNowDto;
import com.project.market.service.candle.CandleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/candle")
@RequiredArgsConstructor
public class CandleController {
    private final CandleService candleService;

    @GetMapping
    public ResponseEntity<List<Candle>> getCandle(@RequestBody @Valid CandleDto dto) {
        return ResponseEntity.ok(candleService.generateCandleUrl(dto));
    }

    @GetMapping
    public ResponseEntity<List<Candle>> getCandle(@RequestBody @Valid CandleToNowDto dto) {
        return ResponseEntity.ok(candleService.generateCandleUrl(dto));
    }

    @GetMapping("/minute")
    public ResponseEntity<List<Candle>> getMinuteCandle(@RequestBody @Valid MinuteCandleDto dto) {
        return ResponseEntity.ok(candleService.generateMinuteCandleUrl(dto));
    }

    @GetMapping("/minute")
    public ResponseEntity<List<Candle>> getMinuteCandle(@RequestBody @Valid MinuteCandleToNowDto dto) {
        return ResponseEntity.ok(candleService.generateMinuteCandleUrl(dto));
    }
}
