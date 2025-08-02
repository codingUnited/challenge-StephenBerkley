package Hard;

import java.text.NumberFormat;
import java.util.Scanner;

public class Calculator_Hard {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        System.out.println("***Enter q to quit***");

        while (true) {    
            
            System.out.print("Enter an equation (e.g., 5+3-2): ");
            String input = scnr.nextLine();
            
            if (input.equalsIgnoreCase("q")) {     // exit program if input is 'q'
                System.out.println("Goodbye!");
                break;
            }

            // split the input into parts: number, operator, number, ...
            String[] tokens = input.split("(?<=[-+*/()])|(?=[-+*/()])");

            double num1;           
            try {
                num1 = Double.parseDouble(tokens[0]);      // if input1 was a number, then change string to double
            } 
            catch (NumberFormatException error) {
                System.out.println(error + " is an invalid input. That was not a number. Try again!");
                continue;
            }
            
            // go through the rest in pairs: operator, number
            for (int i = 1; i < tokens.length; i += 2) {
                String operator = tokens[i];
                double number = Double.parseDouble(tokens[i + 1]);

                NumberFormat add_commas = NumberFormat.getInstance();   // add commas to longer numbers

                // using this NumberFormat removes the .0 on doubles if it's a whole number. Complete accident 
                // discovery!!!
                System.out.println("Answer: " + add_commas.format(calculate(num1, operator, number)));  
            }      

            scnr.close();
        }
    }

    public static boolean isDenomZero(double number)
    {
        if (number == 0) 
            return true; 
        
        else
            return false;
    }
    
    public static double calculate(double num1, String operator, double number) {
        
        switch (operator) {
            case "+":
                return num1 += number;
            case "-":
                return num1 -= number;
            case "*":
                return num1 *= number;
            case "/":
                
                if (!isDenomZero(number)) {  // if denominator is != 0 return a result
                    return num1 / number;
                } 
                else {
                    System.out.println("**Unable to divide by " + number + "!**");
                    return -999999999;   // easily show that it's an error 
                }                                  

            default:
                System.out.println("**" + operator + " is an invalid input!**");
                return -999999999;              // easily show that it's an error

        }
    }
    
}
