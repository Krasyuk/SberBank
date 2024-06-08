package tests;

import main.BankProducts.Deposits.Deposit;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class DepositTest {
    @Test
    @Tag("Positive")
    @DisplayName("Deposit replenishment")
    public void testDeposit() {
        Deposit deposit = new Deposit("USD", 2000, "My Deposit");
        deposit.deposit(1000);
        assertEquals(3000, deposit.getBalance(), 0.01);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Check close")
    public void testClose() {
        Deposit deposit = new Deposit("USD", 2000, "My Deposit");
        deposit.close();
        assertEquals(0, deposit.getBalance(), 0.01);
    }

    @Test
    @Tag("Negative")
    @DisplayName("Check operations after close")
    public void testDepositAfterClose() {
        Deposit deposit = new Deposit("USD", 2000, "My Deposit");
        deposit.close();
        Exception exception = assertThrows(Exception.class, () -> {
            deposit.deposit(2000);
        });
        assertEquals("Deposit is closed", exception.getMessage());
    }
}
