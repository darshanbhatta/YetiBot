package main.java;

import bot.discord.yeti.currency.Bank;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Sandbox {

    public static void main(String[] args) {

        Bank bank = new Bank();
        bank.addUser("dsdsds","sdsdsdsds",54545);

        bank.getAllBalance().get(0).setBalance(50);

bank.getAllBalance().get(0).setBalance(0);
        System.out.println(bank.getAllBalance().get(0).getBalance());

    }
}