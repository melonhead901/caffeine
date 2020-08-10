package com.kellendonohue;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.HOURS;

public class Main {

    public static void main(String[] args) {

        Drink[] drinks = new Drink[]{
                new Drink(90, LocalTime.of(9, 0)),
                new Drink(80, LocalTime.of(10, 0)),
                new Drink(54, LocalTime.of(14, 0)),
                new Drink(54, LocalTime.of(15, 0)),
        };

        LocalTime t = drinks[0].startTime;

        Duration halfLife = Duration.of(6, HOURS);

        final LocalTime start = LocalTime.of(8, 0);

        Duration interval = Duration.ofMinutes(15);
        LocalTime time = start;

        double caffeine = 0;

        while (time.isAfter(LocalTime.MIDNIGHT)) {
            time = time.plus(interval);
            for (Drink d : drinks) {
                if (time.equals(d.startTime)) {
                    caffeine += d.amountMg;
                }
            }
            caffeine = caffeine * Math.pow(.5, 1.0/halfLife.dividedBy(interval));
            System.out.printf("%s %.02f%n", time, caffeine);
        }
    }
}

class Drink {
    int amountMg;
    LocalTime startTime;

    public int getAmountMg() {
        return amountMg;
    }

    public void setAmountMg(int amountMg) {
        this.amountMg = amountMg;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public String toString() {
        return "Drink{" +
                "amountMg=" + amountMg +
                ", startTime=" + startTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drink drink = (Drink) o;
        return amountMg == drink.amountMg &&
                startTime.equals(drink.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountMg, startTime);
    }

    public Drink(int amountMg, LocalTime startTime) {
        this.amountMg = amountMg;
        this.startTime = startTime;
    }
}
