package example.java;


import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        Stack<Integer> in=new Stack<>();
        Stack<Integer> out = new Stack<>();
        for (int j = 0; j < n; j++) {
            String cmd = scanner.next();
            cmd=cmd.substring(0,2);
            if (cmd.equals("pe"))
                if (!out.isEmpty()) System.out.println(out.peek());
                else {
                    while(!in.isEmpty()) out.push(in.pop());
                    System.out.println(out.peek());
                }
            else if (cmd.equals("po"))
                if (!out.isEmpty()) out.pop();
                else {
                    while(!in.isEmpty()) out.push(in.pop());
                    out.pop();
                }
            else {
                Integer num = scanner.nextInt();
                in.push(num);
            }
        }
    }
}
