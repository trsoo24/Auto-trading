package com.project.portfolio.controller;

import com.project.portfolio.entity.dto.Quote;
import com.project.portfolio.service.QuoteService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quote")
public class QuoteController {
    private final QuoteService quoteService;

    @GetMapping
    public ResponseEntity<List<Quote>> getQuoteList(HttpServletRequest request) {
        return ResponseEntity.ok(quoteService.getPortfolioQuoteList(request));
    }
}
