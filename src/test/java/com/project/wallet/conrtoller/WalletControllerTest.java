package com.project.wallet.conrtoller;


import com.project.wallet.controller.WalletController;
import com.project.wallet.dto.WalletOperationDto;
import com.project.wallet.exception.InsufficientFundsException;
import com.project.wallet.exception.WalletNotFoundException;
import com.project.wallet.model.OperationType;
import com.project.wallet.model.Wallet;
import com.project.wallet.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.math.BigDecimal;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private WalletService walletService;

    @Test
    public void testCreateWallet() throws Exception {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId, BigDecimal.ZERO);

        when(walletService.createWallet()).thenReturn(wallet);

        mockMvc.perform(post("/api/v1/wallets")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect( jsonPath("$.balance").value(0));
    }

    @Test
    public void testOperateWalletDeposit() throws Exception {
        UUID walletId = UUID.randomUUID();
        WalletOperationDto dto = new WalletOperationDto(walletId, OperationType.DEPOSIT, BigDecimal.valueOf(100));
        Wallet wallet = new Wallet(walletId, BigDecimal.valueOf(100));

        when(walletService.operate(any(), any(), any())).thenReturn(wallet);

        String json = """
                {
                    "walletId": "%s",
                    "operationType": "DEPOSIT",
                    "amount": 100
                }
                """.formatted(walletId);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(100));
    }

    @Test
    public void testOperateWalletInsufficientFunds() throws Exception {
        UUID walletId = UUID.randomUUID();

        when(walletService.operate(any(), any(), any()))
                .thenThrow(new InsufficientFundsException("Not enough funds"));

        String json = """
                {
                    "walletId": "%s",
                    "operationType": "WITHDRAW",
                    "amount": 100
                }
                """.formatted(walletId);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Not enough funds"));
    }

    @Test
    public void testGetWalletBalance() throws Exception {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId, BigDecimal.valueOf(500));

        when(walletService.getWallet(walletId)).thenReturn(wallet);

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(500));
    }

    @Test
    public void testGetWalletNotFound() throws Exception {
        UUID walletId = UUID.randomUUID();
        when(walletService.getWallet(walletId))
                .thenThrow(new WalletNotFoundException("Wallet not found: " + walletId));

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Wallet not found: " + walletId));
    }
}
