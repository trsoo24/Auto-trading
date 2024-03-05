package com.project.market.controller;

import com.project.market.service.UpbitFindCoin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coin")
public class UseUpbitAPI {
    private final UpbitFindCoin upbitFindCoin;

    @GetMapping("/list")
    public void findCoinList() { // DB에 원화 시장 마켓 추가
        upbitFindCoin.addCoin();
    }

    @GetMapping("/fee")
    public void addCoinFee() throws NoSuchAlgorithmException { upbitFindCoin.addCoinFeeInformation();}
}
