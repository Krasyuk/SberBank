package main.BankProducts.Cards;

public class CreditCard extends Card {
    private double interestRate;

    public CreditCard(String currency, double balance, String name, double interestRate) {
        super(currency, balance, name);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getDebt() {
        return balance < 0 ? -balance : 0;
    }
}

