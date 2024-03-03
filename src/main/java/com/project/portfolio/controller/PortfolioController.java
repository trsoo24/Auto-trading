package com.project.portfolio.controller;

import com.project.portfolio.entity.dto.TradeCurrencyDto;
import com.project.portfolio.service.PortfolioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @PostMapping
    public String buyVirtualCurrency(HttpServletRequest request, @RequestBody @Valid TradeCurrencyDto dto) {
        return portfolioService.buyVirtualCurrency(request, dto);
    }

    @PutMapping("/bye")
    public String sellVirtualCurrency(HttpServletRequest request, @RequestBody @Valid TradeCurrencyDto dto) {
        return portfolioService.sellVirtualCurrency(request, dto);
    }
}
