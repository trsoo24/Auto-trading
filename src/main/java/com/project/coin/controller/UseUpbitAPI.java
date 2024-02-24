package com.project.coin.controller;

import com.project.coin.service.UpbitFindCoin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coin")
public class UseUpbitAPI {
    private final UpbitFindCoin upbitFindCoin;

    @GetMapping("/list")
    public void findCoinList() {
        upbitFindCoin.addCoin();
    }
}
