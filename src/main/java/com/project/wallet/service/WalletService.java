package com.project.wallet.service;

import com.project.wallet.exception.InsufficientFundsException;
import com.project.wallet.exception.WalletNotFoundException;
import com.project.wallet.model.OperationType;
import com.project.wallet.model.Wallet;
import com.project.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet() {
        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);
        return walletRepository.save(wallet);
    }

    public Wallet operate(UUID walletId, OperationType operationType, BigDecimal amount) {
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found: " + walletId));

        if (operationType == OperationType.DEPOSIT) {
            wallet.deposit(amount);
        } else if (operationType == OperationType.WITHDRAW) {
            if (wallet.getBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Not enough funds in wallet: " + walletId);
            }
             wallet.withdraw(amount);
        }

        return walletRepository.save(wallet);
    }

    public Wallet getWallet(UUID walletId) {
        return walletRepository.findById(walletId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found: " + walletId));
    }
}
