package test;

import main.cards.CreditCard;
import main.cards.DebitCard;
import main.cards.ForeignCurrencyDebitCard;
import main.deposits.BankDeposit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BankProductTests {

    @Test
    public void testDebitCardOperations() {
        DebitCard card = new DebitCard("RUB", 1000, "Моя дебетовая карта");
        card.deposit(500);
        assertEquals(1500, card.getBalance(), 0.01);

        card.withdraw(200);
        assertEquals(1300, card.getBalance(), 0.01);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreditCardOperations() {
        CreditCard card = new CreditCard("RUB", 1000, "Моя кредитная карта", 15.0);
        card.deposit(500);
        card.withdraw(2000);
        assertEquals(1500, card.getBalance(), 0.01);

    }

    @Test
    public void testCreditCardOperationWithDebt(){
        CreditCard card = new CreditCard("RUB", -200, "Моя кредитная карта", 1.5);
        assertEquals(200, card.getDebt(), 0.01);
        assertEquals(-200, card.getBalance(), 0.01);
    }

    @Test
    public void testForeignCurrencyDebitCardOperations() {
        ForeignCurrencyDebitCard card = new ForeignCurrencyDebitCard("EUR", 1000, "Моя валютная карта");
        card.deposit(500);
        assertEquals(1500, card.getBalance(),0.01);

        card.withdraw(200);
        assertEquals(1300, card.getBalance(), 0.01);
    }

    @Test
    public void testBankDepositOperations() {
        BankDeposit deposit = new BankDeposit("RUB", 5000, "Мой депозит");
        deposit.deposit(2000);
        assertEquals(7000, deposit.getBalance(), 0.01);

        deposit.close();
        assertThrows(IllegalStateException.class, deposit::getBalance);
    }

}
