package com.project.wallet.dto;
import java.math.BigDecimal;
import java.util.UUID;

public class WalletResponseDto {

    private UUID walletId;
    private BigDecimal balance;
    private String message; // для ошибок или информационных сообщений

    public WalletResponseDto() {
    }

    public WalletResponseDto(UUID walletId, BigDecimal balance) {
        this.walletId = walletId;
        this.balance = balance;
        this.message = null;
    }

    public WalletResponseDto(UUID walletId, BigDecimal balance, String message) {
        this.walletId = walletId;
        this.balance = balance;
        this.message = message;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
