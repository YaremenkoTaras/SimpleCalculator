package com.yaremenko.controller;

import java.util.Stack;

/**
 * @author Taras
 * @since 05.05.2017
 */

public class RPN {
	/*
	 * RPN - Reverse Polish Notation алгоритм расчета строковых выражений используя постфиксную форму
	 */
	
	//Метод Calculate принимает выражение в виде строки и возвращает результат, в своей работе использует другие методы класса
    public static Double calculate(String input) {

        String output = getExpression(input); //Преобразовываем выражение в постфиксную запись
        double result = counting(output); //Решаем полученное выражение
        return result; //Возвращаем результат

    }

    //Метод, преобразующий входную строку с выражением в постфиксную запись
    private static String getExpression(String input) {

        String output = new String(); //Строка для хранения выражения
        Stack<Character> operStack = new Stack<>(); //Стек для хранения операторов

        for (int i = 0; i < input.length(); i++) { //Для каждого символа в входной строке

            //Разделители пропускаем
            if (isDelimeter(input.charAt(i)))
                continue; //Переходим к следующему символу

            //Если символ - цифра, то считываем все число
            if (Character.isDigit(input.charAt(i))) { //Если цифра

                //Читаем до разделителя или оператора, что бы получить число
                while (!isDelimeter(input.charAt(i)) && !isOperator(input.charAt(i))) {
                    output += input.charAt(i); //Добавляем каждую цифру числа к нашей строке
                    i++; //Переходим к следующему символу

                    if (i == input.length()) break; //Если символ - последний, то выходим из цикла
                }

                output += " "; //Дописываем после числа пробел в строку с выражением
                i--; //Возвращаемся на один символ назад, к символу перед разделителем
            }

            //Если символ - оператор
            if (isOperator(input.charAt(i))) { //Если оператор

                if (input.charAt(i) == '(') //Если символ - открывающая скобка
                    operStack.push(input.charAt(i)); //Записываем её в стек
                else if (input.charAt(i) == ')'){ //Если символ - закрывающая скобка

                    //Выписываем все операторы до открывающей скобки в строку
                    Character s = operStack.pop();

                    while (s != '(') {
                        output += s.toString() + ' ';
                        s = operStack.pop();
                    }
                } else {//Если любой другой оператор

                    if (!operStack.empty()) { //Если в стеке есть элементы
                        //И если приоритет нашего оператора меньше или равен приоритету оператора на вершине стека
                        if (getPriority(input.charAt(i)) <= getPriority(operStack.peek()))
                            //То добавляем последний оператор из стека в строку с выражением
                            output += operStack.pop().toString() + " ";
                    }

                    operStack.push(input.charAt(i)); //Если стек пуст, или же приоритет оператора выше - добавляем операторов на вершину стека

                }
            }
        }
        //Когда прошли по всем символам, выкидываем из стека все оставшиеся там операторы в строку
        while (!operStack.empty())
            output += operStack.pop() + " ";

        return output; //Возвращаем выражение в постфиксной записи

    }

    //Метод, вычисляющий значение выражения, уже преобразованного в постфиксную запись
    private static Double counting(String input) {

        double result = 0; //Результат
        Stack<Double> temp = new Stack<>(); //Временный стек для решения

        for (int i = 0; i < input.length(); i++) { //Для каждого символа в строке

            //Если символ - цифра, то читаем все число и записываем на вершину стека
            if (Character.isDigit(input.charAt(i))) {
                String a = new String();

                while (!isDelimeter(input.charAt(i)) && !isOperator(input.charAt(i))) { //Пока не разделитель

                    a += input.charAt(i); //Добавляем
                    i++;
                    if (i == input.length()) break;
                }
                temp.push(Double.parseDouble(a)); //Записываем в стек
                i--;
            } else if (isOperator(input.charAt(i))) { //Если символ - оператор

                //Берем два последних значения из стека
                double a = temp.pop();
                double b = temp.pop();

                switch (input.charAt(i)) { //И производим над ними действие, согласно оператору

                    case '+':
                        result = b + a;
                        break;
                    case '-':
                        result = b - a;
                        break;
                    case '*':
                        result = b * a;
                        break;
                    case '/':
                        result = b / a;
                        break;
                    case '^':
                        result = Math.pow(b, a);
                        break;
                }
                temp.push(result); //Результат вычисления записываем обратно в стек
            }
        }
        return temp.peek(); //Забираем результат всех вычислений из стека и возвращаем его
    }

    //Метод возвращает true, если проверяемый символ - разделитель ("пробел" или "равно")
    static private boolean isDelimeter(char c) {
        if ((" =".indexOf(c) != -1))
            return true;
        return false;
    }

    //Метод возвращает true, если проверяемый символ - оператор
    static private boolean isOperator(char с) {
        if (("+-/*^()".indexOf(с) != -1))
            return true;
        return false;
    }

    //Метод возвращает приоритет оператора
    static private byte getPriority(char s) {
        switch (s) {
            case '(':
                return 0;
            case ')':
                return 1;
            case '+':
                return 2;
            case '-':
                return 3;
            case '*':
                return 4;
            case '/':
                return 4;
            case '^':
                return 5;
            default:
                return 6;
        }
    }

}
