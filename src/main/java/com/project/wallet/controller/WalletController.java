package com.project.wallet.controller;

import com.project.wallet.dto.WalletOperationDto;
import com.project.wallet.dto.WalletResponseDto;
import com.project.wallet.model.Wallet;
import com.project.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/wallets")
    public ResponseEntity<Wallet> createWallet() {
        Wallet wallet = walletService.createWallet();
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/wallet")
    public ResponseEntity<WalletResponseDto> operateWallet(@RequestBody WalletOperationDto dto) {
        Wallet wallet = walletService.operate(dto.getWalletId(), dto.getOperationType(), dto.getAmount());
        WalletResponseDto response = new WalletResponseDto(wallet.getId(), wallet.getBalance());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<WalletResponseDto> getWalletBalance(@PathVariable UUID walletId) {
        Wallet wallet = walletService.getWallet(walletId);
        WalletResponseDto response = new WalletResponseDto(wallet.getId(), wallet.getBalance());
        return ResponseEntity.ok(response);
    }
}
