package com.example.oams.items;

public class Dashboard {
   private int percentage,wallet,shortage;

    public Dashboard(int percentage, int wallet, int shortage) {
        this.percentage = percentage;
        this.wallet = wallet;
        this.shortage = shortage;
    }

    public int getPercentage() {
        return percentage;
    }

    public int getWallet() {
        return wallet;
    }

    public int getShortage() {
        return shortage;
    }
}
