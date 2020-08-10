package com.kellendonohue;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.HOURS;

public class Main {

    public static void main(String[] args) {

        Drink[] drinks = new Drink[] {
            new Drink(90, LocalTime.of(9, 0)),
            new Drink(80, LocalTime.of(10, 0)),
            new Drink(54, LocalTime.of(14, 0)),
            new Drink(54, LocalTime.of(15, 0)),
        };

        final LocalTime start = LocalTime.of(8, 0);

        Duration interval = Duration.ofMinutes(15);
        LocalTime time = start;

        CaffeineLevel caffeineLevel = new CaffeineLevel();

        while (time.isAfter(LocalTime.MIDNIGHT)) {
            time = time.plus(interval);
            for (Drink d : drinks) {
                if (d.isTime(time)) {
                    caffeineLevel.consumeDrink(d);
                }
            }
            caffeineLevel.passTime(interval);
            System.out.printf("%s %.02f%n", time, caffeineLevel.amount);
        }
    }
}

class CaffeineLevel {
    final Duration halfLife = Duration.of(6, HOURS);

    double amount;

    CaffeineLevel() {
        this.amount = 0;
    }

    void passTime(Duration interval) {
        amount = amount * Math.pow(.5, 1.0 / halfLife.dividedBy(interval));
    }

    void consumeDrink(Drink drink) {
        this.amount += drink.amountMg;
    }
}


class Drink {
    final int amountMg;
    final LocalTime startTime;

    @Override
    public String toString() {
        return "Drink{" + "amountMg=" + amountMg + ", startTime=" + startTime + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return amountMg == drink.amountMg && startTime.equals(drink.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountMg, startTime);
    }

    public Drink(int amountMg, LocalTime startTime) {
        this.amountMg = amountMg;
        this.startTime = startTime;
    }

    public boolean isTime(LocalTime time) {
        return startTime.equals(time);
    }
}
