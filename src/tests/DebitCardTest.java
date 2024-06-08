package tests;

import main.BankProducts.Cards.DebitCard;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DebitCardTest {
    @Test
    @Tag("Positive")
    @DisplayName("Debit card replenishment")
    public void testDeposit() {
        DebitCard debitCard = new DebitCard("USD", 1000, "My Debit Card");
        debitCard.deposit(500);
        assertEquals(1500, debitCard.getBalance(), 0.01);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Withdrawal of the amount")
    public void testWithdraw() {
        DebitCard debitCard = new DebitCard("USD", 1000, "My Debit Card");

        debitCard.withdraw(200);
        Assertions.assertEquals(800, debitCard.getBalance(), 0.01);

    }

    @Test
    @Tag("Negative")
    @DisplayName("Withdraw more than balance")
    public void testWithdrawInsufficientFunds() {
        DebitCard debitCard = new DebitCard("USD", 1000, "My Debit Card");
        Exception exception = assertThrows(Exception.class, () -> {
            debitCard.withdraw(2000);
        });
        assertEquals("Insufficient funds", exception.getMessage());
    }
}
