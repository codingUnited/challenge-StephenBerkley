package Easy;

import java.util.Scanner;

public class Calculator_Easy {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        System.out.print("Enter your first number: ");
        double num1 = scnr.nextDouble();

        System.out.print("Enter operation(+,-,*,/): ");
        char ch = scnr.next().charAt(0);

        System.out.print("Enter your second number: ");
        double num2 = scnr.nextDouble();

        System.out.println(calculate(num1, ch, num2));        
    }

    
    public static double calculate(double num1, char ch, double num2) {
        
        switch (ch) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case '*':
                return num1 * num2;
            case '/':
                if (num2 == 0) {
                    System.out.println("Error, can't divide by 0. Setting number to 1.0");
                    num2 = 1.0;
                }
                return num1 * num2;
                
            default:
                return 1;

        }
    }
}
