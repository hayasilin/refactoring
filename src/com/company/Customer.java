package com.company;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {
    private String _name;
    private Vector _rentals = new Vector();

    public Customer(String name) {
        _name = name;
    }

    public void addRental(Rental arg) {
        _rentals.addElement(arg);
    }

    public String getName() {
        return _name;
    }

    public String statement() {
        double totalAmount = 0; // 總消費金額
        int frequentRenterPoints = 0; // 常客積點
        Enumeration rentals = _rentals.elements();
        String result = "Rental Record for " + getName() + "\n";

        while (rentals.hasMoreElements()) {
            double thisAmount = 0;
            Rental each = (Rental) rentals.nextElement(); // 取得一筆租借紀錄

            thisAmount = amountFor(each);

            // Add frequent renter points 累加常客積點
            frequentRenterPoints++;
            // Add onus for a two day new release rental
            if ((each.getMovie().getPriceCode() == Movie.NEW_RELEASE && each.getDaysRented() > 1)) {
                frequentRenterPoints++;
            }

            // Show figures for this rental 顯示此筆租借資料
            result += "\t" + each.getMovie().getTitle() + "\t" + String.valueOf(thisAmount) + "\n";
            totalAmount += thisAmount;
        }

        // Add footer lines
        result += "Ammount owed is " + String.valueOf(totalAmount) + "\n";
        result += "You earned " + String.valueOf(frequentRenterPoints) + " frequent renter points";
        return result;
    }

    private double amountFor(Rental aRental) {
        double result = 0;

        // Determine amounts for each line
        switch (aRental.getMovie().getPriceCode()) { // 取得影片出租價格
            case Movie.REGULAR:
                result += 2;
                if (aRental.getDaysRented() > 2) {
                    result += (aRental.getDaysRented() - 2) * 1.5;
                }
                break;

            case Movie.NEW_RELEASE:
                result += 1.5;
                if (aRental.getDaysRented() > 3) {
                    result += (aRental.getDaysRented() - 3) * 1.5;
                }
                break;

            case Movie.CHILDREN:
                result += 1.5;
                if (aRental.getDaysRented() > 3) {
                    result += (aRental.getDaysRented() - 3) * 1.5;
                }
                break;
        }
        return result;
    }
}
