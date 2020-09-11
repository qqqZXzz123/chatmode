package com.holo.chatmode.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Scanner;

public class Handler {
	public static Double calc(List<String> postfix) {
		Deque<Double> stack = new ArrayDeque<Double>();
		for (String x : postfix) {
			if (x.equals("sqrt")) stack.push(Math.sqrt(stack.pop()));
			else if (x.equals("cube")) {
				Double tmp = stack.pop();
				stack.push(tmp * tmp * tmp);
			}
			else if (x.equals("pow10")) stack.push(Math.pow(10, stack.pop()));
			else if (x.equals("+")) stack.push(stack.pop() + stack.pop());
			else if (x.equals("-")) {
				Double b = stack.pop(), a = stack.pop();
				stack.push(a - b);
			}
			else if (x.equals("*")) stack.push(stack.pop() * stack.pop());
			else if (x.equals("/")) {
				Double b = stack.pop(), a = stack.pop();
				stack.push(a / b);
			}
			else if (x.equals("u-")) stack.push(-stack.pop());
			else stack.push(Double.valueOf(x));
		}
		return stack.pop();
	}
	
	public static void main (String[] args) {
		Scanner in = new Scanner(System.in);
		String s = in.nextLine();
		Calculate n = new Calculate();
		List<String> expression = n.parse(s);
		boolean flag = n.flag;
		if (flag) {
			for (String x : expression) System.out.print(x + " ");
			System.out.println();
			System.out.println(calc(expression));
		}
	}
}
