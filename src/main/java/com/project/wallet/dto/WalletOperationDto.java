package com.project.wallet.dto;

import com.project.wallet.model.OperationType;
import java.math.BigDecimal;
import java.util.UUID;

public class WalletOperationDto {

    private UUID walletId;
    private OperationType operationType;
    private BigDecimal amount;

    public WalletOperationDto() {
    }

    public WalletOperationDto(UUID walletId, OperationType operationType, BigDecimal amount) {
        this.walletId = walletId;
        this.operationType = operationType;
        this.amount = amount;
    }

    public UUID getWalletId() {
        return walletId;
    }

    public void setWalletId(UUID walletId) {
        this.walletId = walletId;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
