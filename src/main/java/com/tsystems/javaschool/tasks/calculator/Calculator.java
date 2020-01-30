package com.tsystems.javaschool.tasks.calculator;

import java.util.*;

public class Calculator {
    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        try {
            if (statement == null || statement.isEmpty()){
                return null;
            }
            char[] symbols = statement.toCharArray();
            Stack<Double> numbers = new Stack<>();
            Stack<Character> operations = new Stack<>();

            for (int i = 0; i < symbols.length; i++) {
                if (symbols[i] == ' '){
                    continue;
                } else if (symbols[i] >= '0' && symbols[i] <= '9'){
                    int numberOfDigits = 0;
                    boolean isDotPresent = false;
                    StringBuffer currentNumber = new StringBuffer();
                    while (i+numberOfDigits < symbols.length
                            && (symbols[i+numberOfDigits] >= '0' && symbols[i+numberOfDigits] <= '9'
                            || symbols[i+numberOfDigits] == '.')){
                        if (isDotPresent == false && symbols[i+numberOfDigits] == '.'){
                            currentNumber.append(symbols[i + numberOfDigits]);
                            isDotPresent = true;
                        } else {
                            currentNumber.append(symbols[i + numberOfDigits]);
                        }
                        numberOfDigits++;
                    }
                    i += numberOfDigits - 1;
                    numbers.add(Double.valueOf(currentNumber.toString()));
                } else if (symbols[i] == '(') {
                    operations.push(symbols[i]);
                } else if (symbols[i] == ')') {
                    while (operations.peek() != '('){
                        numbers.push(compute(operations.pop(), numbers.pop(), numbers.pop()));
                    }
                    operations.pop();
                } else if (symbols[i] == '+' || symbols[i] == '-' ||  symbols[i] == '*' || symbols[i] == '/') {
                    while (!operations.empty() && isOperationPrioritized(symbols[i], operations.peek())){
                        numbers.push(compute(operations.pop(), numbers.pop(), numbers.pop()));
                    }
                    operations.push(symbols[i]);
                } else {
                    return null;
                }
            }

            while (!operations.empty())
                numbers.push(compute(operations.pop(), numbers.pop(), numbers.pop()));
            Double result = numbers.pop();
            if (result == null) {
                return null;
            }
            return result % 1 == 0 ? String.valueOf(result.intValue()) : Double.toString(result);
        } catch (NumberFormatException | EmptyStackException e) {
            return null;
        }
    }

    private Double compute(char operation, double a, double b){
        Double result = 0d;
        switch (operation){
            case '+':
                result = a + b;
                break;
            case '-':
                result = b - a;
                break;
            case '*':
                result = a * b;
                break;
            case '/':
                if (a == 0) {
                    result = null;
                } else {
                    result = b / a;
                }
                break;
        }
        return result;
    }

    private boolean isOperationPrioritized(char currentOperation, char peekedOperation) {
        if ((currentOperation == '-' || currentOperation == '+')
                && (peekedOperation == '*' || peekedOperation == '/')) {
            return true;
        } else if ((currentOperation == '-' || currentOperation == '+')
                && (peekedOperation == '-' || peekedOperation == '+')) {
            return true;
        } else if ((currentOperation == '*' || currentOperation == '/')
                && (peekedOperation == '*' || peekedOperation == '/')) {
            return true;
        } else if ((currentOperation == '*' || currentOperation == '/')
                && (peekedOperation == '+' || peekedOperation == '-')) {
            return false;
        } else if (peekedOperation == '(' || peekedOperation == ')'){
            return false;
        } else {
            return false;
        }
    }
}
