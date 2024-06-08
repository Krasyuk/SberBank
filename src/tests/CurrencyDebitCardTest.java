package tests;

import main.BankProducts.Cards.CurrencyDebitCard;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import static org.junit.Assert.*;

public class CurrencyDebitCardTest {
    @Test
    @Tag("Positive")
    @DisplayName("Currency debit card replenishment")
    public void testDeposit() {
        CurrencyDebitCard currencyDebitCard = new CurrencyDebitCard("EUR", 1000, "My Currency Debit Card");
        currencyDebitCard.deposit(500);
        assertEquals(1500, currencyDebitCard.getBalance(), 0.01);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Withdrawal of the amount")
    public void testWithdraw() {
        CurrencyDebitCard currencyDebitCard = new CurrencyDebitCard("EUR", 1000, "My Currency Debit Card");
            currencyDebitCard.withdraw(200);
            assertEquals(800, currencyDebitCard.getBalance(), 0.01);
    }

    @Test
    @Tag("Negative")
    @DisplayName("Withdraw more than balance")
    public void testWithdrawInsufficientFunds() {
        CurrencyDebitCard currencyDebitCard = new CurrencyDebitCard("EUR", 1000, "My Currency Debit Card");
        Exception exception = assertThrows(Exception.class, () -> {
            currencyDebitCard.withdraw(2000);
        });
        assertEquals("Insufficient funds", exception.getMessage());
    }
}
