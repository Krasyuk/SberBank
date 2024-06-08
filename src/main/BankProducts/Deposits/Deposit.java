package main.BankProducts.Deposits;

import main.BankProducts.BankProduct;

public class Deposit extends BankProduct {
    private boolean isClosed;

    public Deposit(String currency, double balance, String name) {
        super(currency, balance, name);
        this.isClosed = false;
    }

    public void deposit(double amount) {
        if (!isClosed) {
            balance += amount;
        } else {
            throw new IllegalStateException("Deposit is closed");
        }
    }

    public void close() {
        isClosed = true;
        balance = 0;
    }

    public boolean isClosed() {
        return isClosed;
    }
}

