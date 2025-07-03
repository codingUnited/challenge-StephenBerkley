package Medium;

import java.text.NumberFormat;
import java.util.Scanner;

public class Calculator_Medium {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        System.out.println("***Enter q to quit***");

        while (true) {    
            
            System.out.print("\nEnter your first number: ");
            String input1 = scnr.next();

            if (input1.equalsIgnoreCase("q")) {     // exit program if input is 'q'
                System.out.println("Goodbye!");
                break;
            }

            double num1;           
            try {
                num1 = Double.parseDouble(input1);      // if input1 was a number, then change string to double
            } 

            catch (NumberFormatException error) {
                System.out.println(error + " is an invalid input. That was not a number. Try again!");
                continue;
            }
            
            System.out.print("Enter operation(+,-,*,/): ");
            char ch = scnr.next().charAt(0);

            System.out.print("Enter your second number: ");
            String input2 = scnr.next();

            double num2;           
            try {
                num2 = Double.parseDouble(input2);      // if input2 was a number, then change string to double
            } 
            
            catch (NumberFormatException error) {
                System.out.println(error + " is an invalid input. That was not a number. Try again!");
                continue;
            }

            NumberFormat add_commas = NumberFormat.getInstance();   // add commas to longer numbers

            // using this NumberFormat removes the .0 on doubles if it's a whole number. Complete accident 
            // discovery!!!
            System.out.println(add_commas.format(num1) + " " + ch + " " + add_commas.format(num2) + " = "
                                + add_commas.format(calculate(num1, ch, num2)));  
        }      
    }

    public static boolean isDenomZero(double num2)
    {
        if (num2 == 0) 
            return true; 
        
        else
            return false;
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
                
                if (!isDenomZero(num2)) {  // if denominator is != 0 return a result
                    return num1 / num2;
                } 
                else {
                    System.out.println("**Unable to divide by " + num2 + "!**");
                    return -999999999;   // easily show that it's an error 
                }                                  

            default:
                System.out.println("**" + ch + " is an invalid input!**");
                return -999999999;              // easily show that it's an error

        }
    }
    
}
