/*
   HW 11
   CSC 20, Fall 2014, 12/6/2014
   Written by: Class ID 1913
   This program takes infix expressions (as strings), converts
   them into postfix expressions, and evaluates their integer
   results.
*/
import java.util.*;
import java.io.*;

public class Postfix {
public static final int ADDITIVE = 0;
public static final int MULTIPLICATIVE = 2;
public static final int PARENTHESES = 1;
public static final int EXPONENTIATION = -1;
public static final int RIGHT_ASSOCIATIVE = 0;
public static final int LEFT_ASSOCIATIVE = 1;

   public static void main(String[] args) {
      convertAndEval("3 - -2");
      System.out.println();
      convertAndEval("3 / 2");
      System.out.println();
      convertAndEval("3 ^ 2");
      System.out.println();
      convertAndEval("4 + 3 * 2");
      System.out.println();
      convertAndEval("4 * 3 + 2");
      System.out.println();
      convertAndEval("( 4 + 3 ) * 2");
      System.out.println();
      convertAndEval("4 * ( 3 + 2 )");
      System.out.println();
      convertAndEval("2 + 3 * 4 * 5 + 6");
      System.out.println();
      convertAndEval("4 ^ 3 ^ 2");
      System.out.println();
      convertAndEval("2 * 4 ^ 3 ^ 2 ^ 2");
      System.out.println();
      convertAndEval("2 * ( 4 ^ 3 ) ^ 2 * 2");
      System.out.println();
      convertAndEval("( ( ( 2 ) ) ) ^ 2");
      System.out.println();
      try {
         convertAndEval("3 2");
      } catch(EmptyStackException e) {
      }
      System.out.println();
      convertAndEval(") 0 (");
      System.out.println();
      convertAndEval("2 + * 3");
   }
   
   public static String infixToPostfix(String infix) {
      String output =  "";
      Stack<String> operations = new Stack<String>();
      Scanner scan = new Scanner(infix);
      while(scan.hasNext()) {
         String grab = scan.next();
         if(!isOperator(grab)) {
            output = output + grab + " ";
         } else if(grab.equals("(")) {
            operations.push(grab);
         } else if(grab.equals(")")) {
            while(!operations.peek().equals("(")) {
               output = output + operations.pop() + " ";
            }
            if(!operations.isEmpty() && operations.peek().equals("(")) {
               operations.pop();
            }
         } else {
            while(operations.size() > 0 && precedes(operations.peek(), grab)) {
               if(!operations.peek().equals("(")) {
                  output = output + operations.pop() + " ";
               } else {
                  operations.pop();
               }
            }
            if(!grab.equals(")")) {
               operations.push(grab);
            }
         }
      }
      while(operations.size() > 0) {
         output = output + operations.pop() + " ";
      }
      return output;
   }
   
   public static int getAssociativity(String operator) {
      switch(operator) {
         case "^":
            return RIGHT_ASSOCIATIVE;
            
         case "+":
         case "-":
         case "*":
         case "/":
            return LEFT_ASSOCIATIVE;
            
         default:
            throw new IllegalArgumentException();
      }
   }
   
   public static boolean precedes(String left, String right) {
      return (getPrecedence(left) > getPrecedence(right)
              || (getPrecedence(left) == getPrecedence(right)
              && getAssociativity(left) == LEFT_ASSOCIATIVE));
   }
   
   public static int getPrecedence(String operator) {
      switch(operator) {
         case "+":
         case "-":
            return ADDITIVE;
            
         case "*":
         case "/":
            return MULTIPLICATIVE;
            
         case "^":
            return EXPONENTIATION;
            
         case "(":
         case ")":
            return PARENTHESES;
            
         default:
            throw new IllegalArgumentException();
      }
   }
   
   public static boolean isNumber(String num) {
      return (num.charAt(0) >= '0' && num.charAt(0) <= '9');
   }
   
   public static boolean isOperator(String operator) {
      return (operator.length() == 1 && "()*-/+^".indexOf(operator.charAt(0)) != -1);
   }
   
   public static int evalPostfix(String postfix) {
        Stack<Integer> s = new Stack<Integer>();
        Scanner in = new Scanner(postfix);
        while (in.hasNext()) {
            if(in.hasNextInt()) {
                s.push(in.nextInt());
            } else {
                String op = in.next();
                int a = s.pop();
                int b = s.pop();
                if (op.equals("+")) {
                    s.push(a+b);
                } else if (op.equals("*")) {
                    s.push(a*b);
                } else if (op.equals("/")) {
                    s.push(b/a);
                } else if (op.equals("-")) {
                    s.push(b-a);
                } else if (op.equals("^")) {
                    s.push((int)Math.pow(b,a));
                } else {
                    throw new RuntimeException();
                }
            }
        }
        if (s.size()==1) {
            return s.pop();
        } else {
            throw new EmptyStackException();
        }
   }
   
   public static void convertAndEval(String infix) {
      System.out.println("Infix Expression: " + infix);
      System.out.print("Postfix Equivalent: ");
      String postfix = "";
      try {
         postfix = postfix + infixToPostfix(infix);
      } catch(EmptyStackException e1) {
         System.out.print("Parse error");
      }
      System.out.println(postfix);
      System.out.print("Evaluated Result: ");
      try {
         System.out.println(evalPostfix(postfix));
      } catch(EmptyStackException e2) {
         System.out.println("Parse error");
      }
   }
}