package cinema;

import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Scanner;

public class Cinema {

    public static void main(String[] args) {

        int rows = rowsInput();
        int noOfSeats = seatsInput();

        String[][] seats = new String[rows][noOfSeats];
        fillSeats(seats, rows, noOfSeats);

        System.out.println();

        int menu = showMenu();
        while (menu != 0) {
            switch(menu) {
                case 1 :
                    showSeats(seats, rows, noOfSeats);
                    System.out.println();
                    break;
                case 2 :
                    buyTicket(seats,rows,noOfSeats);
                    System.out.println();
                    break;
                case 3 :
                    stats(seats, rows, noOfSeats);
                    System.out.println();
                    break;
            }
            menu = showMenu();
        }
    }

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public static int takeInput() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        return n;
    }

    public static void showSeats(String[][] seats, int rows, int noOfSeats) {
        System.out.println("Cinema:");
        System.out.print("  ");
        for (int i = 1; i <= noOfSeats; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 1; i <= rows; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < noOfSeats; j++) {
                System.out.print(seats[i - 1][j] + " ");
            }
            System.out.println();
        }

    }

    public static int totalSeats(int rows, int noOfSeats) {
        int totalSeats = rows * noOfSeats;
        return totalSeats;
    }

    public static int currIncome (String[][] seats, int rows, int noOfSeats) {
        int income = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < noOfSeats; j++) {
                if (Objects.equals(seats[i][j], "B")) {
                    income += ticketPrice(rows, noOfSeats, i + 1, j + 1);
                }
            }
        }

        return income;
    }

    public static int ticketPrice(int rows, int noOfSeats, int rowNumber, int seatNumber) {
        int totalSeats = totalSeats(rows, noOfSeats);
        if (totalSeats <= 60) {
            return 10;
        } else {
            if(rows % 2 == 0) {
                int half = rows / 2;
                if (rowNumber > half) {
                    return 8;
                } else {
                    return 10;
                }
            } else {
                int front = (rows - 1) / 2;
                int back = rows - front;
                if (rowNumber <=front) {
                    return 10;
                } else {
                    return 8;
                }
            }
        }
    }

    public static int rowsInput() {
        System.out.println("Enter the number of rows:");
        int rows = takeInput();
        return rows;
    }

    public static int seatsInput() {
        System.out.println("Enter the number of seats in each row:");
        int noOfSeats = takeInput();
        return noOfSeats;
    }

    public static int rowNumInput() {
        System.out.println("Enter a row number:");
        int rowNumber = takeInput();
        return rowNumber;
    }

    public static int seatNumberInput() {
        System.out.println("Enter a seat number in that row:");
        int seatNumber = takeInput();
        return seatNumber;
    }

    public static int showMenu() {
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");

        int menu = takeInput();
        System.out.println();
        return menu;
    }

    public static void buyTicket(String[][] seats, int rows, int noOfSeats) {
        int rowNumber = rowNumInput();
        int seatNumber = seatNumberInput();
        System.out.println();

        if (rowNumber > rows || seatNumber > noOfSeats || rowNumber <= 0 || seatNumber <= 0) {
            System.out.println("Wrong input!");
            System.out.println();
            buyTicket(seats,rows,noOfSeats);
            return;
        }

        if (Objects.equals(seats[rowNumber - 1][seatNumber - 1],"B")) {
            System.out.println("That ticket has already been purchased!");
            System.out.println();
            buyTicket(seats,rows,noOfSeats);
            return;
        }

        int ticketPrice = ticketPrice(rows,noOfSeats,rowNumber,seatNumber);
        System.out.println("Ticket price: $" + ticketPrice);

        seats[rowNumber - 1][seatNumber - 1] = "B";
    }

    public static void fillSeats(String[][] seats, int rows, int noOfSeats) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < noOfSeats; j++) {
                seats[i][j] = "S";
            }
        }
    }

    public static int purchasedTickets(String[][] seats, int rows, int noOfSeats) {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < noOfSeats; j++) {
                if (Objects.equals(seats[i][j], "B")) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int totalIncome(int rows, int noOfSeats) {
        int price = 0;

        int totalSeats = rows * noOfSeats;

        if(totalSeats <= 60) {
            price = totalSeats * 10;
        }
        else {
            if(rows % 2 == 0) {
                int half = rows / 2;
                price = half * noOfSeats * 10 + half * noOfSeats * 8;
            } else {
                int front = (rows - 1) / 2;
                int back = rows - front;
                price = front * noOfSeats * 10 + back * noOfSeats * 8;
            }
        }

        return price;
    }

    public static float percentTickets(int purchased, int totalSeats) {
        return ((float) purchased / totalSeats) * 100.0f;
    }

    public static void stats(String[][] seats, int rows, int noOfSeats) {
        int totalSeats = totalSeats(rows, noOfSeats);
        int purchased = purchasedTickets(seats, rows, noOfSeats);
        float percent = percentTickets(purchased, totalSeats);
        int totalIncome = totalIncome(rows, noOfSeats);
        int currIncome = currIncome(seats, rows, noOfSeats);

        System.out.println("Number of purchased tickets: " + purchased);
        System.out.println("Percentage: " + df.format(percent) + "%");
        System.out.println("Current income: $" + currIncome);
        System.out.println("Total income: $" + totalIncome);
    }
}