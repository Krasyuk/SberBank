package main.deposits;

import main.interfaces.BankProduct;

public abstract class Deposit implements BankProduct {
    protected String currency;
    protected double balance;
    protected String name;
    protected boolean isClosed;

    public Deposit(String currency, double balance, String name) {
        this.currency = currency;
        this.balance = balance;
        this.name = name;
        this.isClosed = false;
    }

    public String getCurrency() {
        return currency;
    }

    public double getBalance() {
        if (isClosed) {
            throw new IllegalStateException("Депозит закрыт!");
        }
        return balance;
    }

    public String getName() {
        return name;
    }

    public void deposit(double amount) {
        if (isClosed) {
            throw new IllegalStateException("Депозит закрыт!");
        }
        balance += amount;
    }

    public void close() {
        isClosed = true;
    }
}
