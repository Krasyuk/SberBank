package main.cards;

import main.interfaces.BankProduct;

public abstract class Card implements BankProduct {
    protected String currency;
    protected double balance;
    protected String name;

    public Card(String currency, double balance, String name) {
        this.currency = currency;
        this.balance = balance;
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Недостаточно средств на счете!");
        }
    }
}
