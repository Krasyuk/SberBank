package tests;

import main.BankProducts.Cards.CreditCard;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import static org.junit.Assert.assertEquals;

public class CreditCardTests {
    @Test
    @Tag("Positive")
    @DisplayName("Credit card replenishment")
    public void testDeposit() {
        CreditCard creditCard = new CreditCard("RUB", 0, "My Credit Card", 0.05);
        creditCard.deposit(500);
        assertEquals(500, creditCard.getBalance(), 0.01);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Withdrawal of the amount")
    public void testWithdraw() {
        CreditCard creditCard = new CreditCard("RUB", 0, "My Credit Card", 0.05);
        creditCard.deposit(500);
        creditCard.withdraw(300);
        assertEquals(200, creditCard.getBalance(), 0.01);
    }
    @Test(expected = IllegalArgumentException.class)
    @Tag("Negative")
    @DisplayName("Withdrawal of the amount when amount > balance")
    public void testWithdrawNegative() {
        CreditCard creditCard = new CreditCard("RUB", 0, "My Credit Card", 0.05);
        creditCard.deposit(500);
        creditCard.withdraw(501);
    }

    @Test
    @Tag("Positive")
    @DisplayName("Check dept")
    public void testGetDebt(){
        CreditCard creditCard = new CreditCard("RUB", -212, "My Credit Card", 0.05);
        creditCard.getDebt();
        assertEquals(212, creditCard.getDebt(), 0.01);
    }
    @Test
    @Tag("Positive")
    @DisplayName("Check interest rate")
    public void testGetInterestRate() {
        CreditCard creditCard = new CreditCard("RUB", 0, "My Credit Card", 0.05);
        assertEquals(0.05, creditCard.getInterestRate(), 0.01);
    }
}
