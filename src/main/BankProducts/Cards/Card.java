package main.BankProducts.Cards;

import main.BankProducts.BankProduct;

public abstract class Card extends BankProduct {

    public Card(String currency, double balance, String name) {
        super(currency, balance, name);
    }

    public void deposit(double amount) {
        if(amount >= 0){
            balance += amount;
        } else {
            throw new IllegalArgumentException("The amount cannot be negative");
        }

    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    public double checkBalance() {
        return balance;
    }
}

