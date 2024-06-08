package main.BankProducts;

public abstract class BankProduct {
    protected String currency;
    protected double balance;
    protected String name;

    public BankProduct(String currency, double balance, String name) {
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
}

