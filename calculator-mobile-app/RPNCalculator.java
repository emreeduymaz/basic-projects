package rummtas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;

public class RPNCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private StringBuilder currentInput;
    private ArrayList<String> history;

    public RPNCalculator() {
        setTitle("RPN Calculator");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        display = new JTextField();
        display.setFont(new Font("Arial", Font.PLAIN, 24));
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        String[] buttons = {
                "Num", "/", "*", "-",
                "7", "8", "9", "+",
                "4", "5", "6", "+",
                "1", "2", "3", "Ent",
                "0", "0", ".", "Ent",    
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.setBackground(Color.LIGHT_GRAY);
            button.setOpaque(true);
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);

        currentInput = new StringBuilder();
        history = new ArrayList<>();

        setVisible(true);
    }

    public static void main(String[] args) {
        new RPNCalculator();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.chars().allMatch(Character::isDigit) || command.equals(".")) {
            currentInput.append(command);
            display.setText(currentInput.toString());
        } else if (command.equals("Ent")) {
            try {
                String infix = currentInput.toString();
                String rpn = convertToRPN(infix);
                double result = evaluateRPN(rpn);
                display.setText(String.valueOf(result));
                history.add(infix + " = " + result);
                currentInput.setLength(0);
            } catch (Exception ex) {
                display.setText("Error");
                currentInput.setLength(0);
            }
        } else {
            currentInput.append(" ").append(command).append(" ");
            display.setText(currentInput.toString());
        }
    }

    private String convertToRPN(String infix) {
        StringBuilder output = new StringBuilder();
        Stack<Character> stack = new Stack<>();
        for (char c : infix.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                output.append(c);
            } else if (c == ' ') {
                output.append(' ');
            } else {
                output.append(' ');
                while (!stack.isEmpty() && precedence(stack.peek()) >= precedence(c)) {
                    output.append(stack.pop()).append(' ');
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()) {
            output.append(' ').append(stack.pop());
        }
        return output.toString().trim();
    }

    private int precedence(char op) {
        switch (op) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }

    private double evaluateRPN(String expression) {
        String[] tokens = expression.split("\\s+");
        Stack<Double> stack = new Stack<>();

        for (String token : tokens) {
            if (token.matches("-?\\d+(\\.\\d+)?")) {
                stack.push(Double.parseDouble(token));
            } else {
                if (stack.size() < 2) {
                    throw new IllegalArgumentException("Invalid RPN expression");
                }
                double b = stack.pop();
                double a = stack.pop();
                switch (token) {
                    case "+":
                        stack.push(a + b);
                        break;
                    case "-":
                        stack.push(a - b);
                        break;
                    case "*":
                        stack.push(a * b);
                        break;
                    case "/":
                        stack.push(a / b);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown operator: " + token);
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid RPN expression");
        }

        return stack.pop();
    }
}
