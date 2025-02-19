package com.example.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private val currentInput = StringBuilder()
    private val history = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)

        val buttonIds = listOf(
            R.id.buttonNum, R.id.buttonDivide, R.id.buttonMultiply, R.id.buttonSubtract,
            R.id.button7, R.id.button8, R.id.button9, R.id.buttonAdd,
            R.id.button4, R.id.button5, R.id.button6, R.id.buttonAdd,
            R.id.button1, R.id.button2, R.id.button3, R.id.buttonEnter,
            R.id.button0, R.id.button0, R.id.buttonDot, R.id.buttonEnter
        )

        for (id in buttonIds) {
            findViewById<Button>(id).setOnClickListener { onButtonClick(it as Button) }
        }
    }

    private fun onButtonClick(button: Button) {
        val command = button.text.toString()

        when {
            command.all { it.isDigit() } || command == "." -> {
                currentInput.append(command)
                display.text = currentInput.toString()
            }
            command == "Ent" -> {
                try {
                    val infix = currentInput.toString()
                    val rpn = convertToRPN(infix)
                    val result = evaluateRPN(rpn)
                    display.text = result.toString()
                    history.add("$infix = $result")
                    currentInput.clear()
                } catch (ex: Exception) {
                    display.text = "Error"
                    currentInput.clear()
                }
            }
            else -> {
                currentInput.append(" $command ")
                display.text = currentInput.toString()
            }
        }
    }

    private fun convertToRPN(infix: String): String {
        val output = StringBuilder()
        val stack = Stack<Char>()
        for (c in infix.toCharArray()) {
            when {
                c.isDigit() || c == '.' -> output.append(c)
                c == ' ' -> output.append(' ')
                else -> {
                    output.append(' ')
                    while (stack.isNotEmpty() && precedence(stack.peek()) >= precedence(c)) {
                        output.append(stack.pop()).append(' ')
                    }
                    stack.push(c)
                }
            }
        }
        while (stack.isNotEmpty()) {
            output.append(' ').append(stack.pop())
        }
        return output.toString().trim()
    }

    private fun precedence(op: Char): Int {
        return when (op) {
            '+', '-' -> 1
            '*', '/' -> 2
            else -> -1
        }
    }

    private fun evaluateRPN(expression: String): Double {
        val tokens = expression.split("\\s+".toRegex()).toTypedArray()
        val stack = Stack<Double>()

        for (token in tokens) {
            when {
                token.matches("-?\\d+(\\.\\d+)?".toRegex()) -> stack.push(token.toDouble())
                else -> {
                    if (stack.size < 2) throw IllegalArgumentException("Invalid RPN expression")
                    val b = stack.pop()
                    val a = stack.pop()
                    val result = when (token) {
                        "+" -> a + b
                        "-" -> a - b
                        "*" -> a * b
                        "/" -> a / b
                        else -> throw IllegalArgumentException("Unknown operator: $token")
                    }
                    stack.push(result)
                }
            }
        }
        if (stack.size != 1) throw IllegalArgumentException("Invalid RPN expression")
        return stack.pop()
    }
}
