import java.util.*;

public class Postfix2 {
    private static final int right = 0;
    private static final int left = 1;
    
    public static String infixToPostfix(Scanner in){
        String postfix = "";
        Stack <String> op = new Stack<String>();
        while(in.hasNext()){
            String str = in.next();
            if(isOperator(str)){
                while(op.size() >= 1 && precedence(str) <= precedence(op.peek()) && !str.equals("^")){
                    postfix = postfix + op.pop() + " ";
                }
                if(str.equals("^")){
                    op.push(str);
                }else{
                    op.push(str);
                }                                                      
            }else if(str.equals(")")) {
                while(!op.peek().equals("(")){
                    postfix = postfix + op.pop() + " ";
                } 
                if(op.peek().equals("(")){
                    op.pop();
                }               
            }else if(str.equals("(")) {
                op.push(str);
            } else {
                postfix = postfix + str + " "; 
            }
        }
        while(op.size()>0){
            postfix = postfix + op.pop() + " ";
        } 
        return postfix;
    }
    
    public static int precedence(String str) {
        int precedence = 0;
        if(str.equals("+") || str.equals("-")) {
            precedence = 0;
        }else if(str.equals("*") || str.equals("/")) {
            precedence = 1;
        }else if(str.equals("^")) {
            precedence = 2;
        }else if(str.equals("(") || str.equals(")")) {
            precedence = -1;
        }
        return precedence;
    }
    
    private static boolean isOperator(String str) {
        return (str.equals("+") ||
                str.equals("-") ||
                str.equals("*") ||
                str.equals("/") ||
                str.equals("^"));
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
            throw new RuntimeException();
        }
    }
    
    public static void main(String[] args) {        
        System.out.print("Infix: ");
        Scanner in = new Scanner(System.in);
        String postfix = "";
        try{            
            postfix = infixToPostfix(new Scanner(in.nextLine()));
            System.out.println("Postfix: " + postfix);
        }catch (Exception e1){
            System.out.print("Postfix: " + postfix);
            System.out.println("Parse error");
        }
        System.out.print("Evaluates to: ");
        try {            
            int result = evalPostfix(postfix);
            System.out.println(result);
        }catch (Exception e2){
            System.out.println("Parse error");
        }
    }
}