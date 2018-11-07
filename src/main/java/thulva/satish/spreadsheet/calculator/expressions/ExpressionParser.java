package thulva.satish.spreadsheet.calculator.expressions;

import thulva.satish.spreadsheet.CellUtils;

import java.util.Scanner;
import java.util.Stack;

/**
 * Parses expression from string representation
 *
 * @author satish.thulva. Generated on 07/11/18
 **/
public class ExpressionParser {

    private static final String SEPARATOR_PATTERN = "\\s+";

    public Expression parseExpression(String expression) {
        expression = expression.trim();
        String[] fields = expression.split(SEPARATOR_PATTERN, -1);

        if (fields.length == 1) {
            return parseConstantOrCellAliasExpression(expression);
        } else if (fields.length == 2) {
            return parseUnaryOperatorExpression(fields[0], fields[1]);
        } else {
            return parseBinaryOperatorExpression(fields);
        }
    }

    private Expression parseConstantOrCellAliasExpression(String expression) {
        try (Scanner scanner = new Scanner(expression)) {
            if (!scanner.hasNextInt()) {
                return new CellAliasExpression(CellUtils.getCellForSymbol(expression));
            } else {
                return new ConstantExpression(scanner.nextInt());
            }
        }
    }

    private Expression parseUnaryOperatorExpression(String operand, String operator) {
        Expression leftExpression = parseExpression(operand);
        ArithmeticOperator unaryOperator = ArithmeticOperator.findOperator(operator);
        return new ArithmeticExpression(leftExpression, null, unaryOperator);
    }

    private Expression parseBinaryOperatorExpression(String[] fields) {
        Stack<Expression> expressionStack = new Stack<>();
        for (String field : fields) {
            ArithmeticOperator arithmeticOperator = isArithmeticOperator(field);
            if (arithmeticOperator == null) {
                expressionStack.push(parseConstantOrCellAliasExpression(field));
            } else if (arithmeticOperator == ArithmeticOperator.INCREMENT || arithmeticOperator == ArithmeticOperator.DECREMENT) {
                Expression unaryOpOperand = expressionStack.pop();
                expressionStack.push(new ArithmeticExpression(unaryOpOperand, null, arithmeticOperator));
            } else {
                Expression rightOperand = expressionStack.pop();
                Expression leftOperand = expressionStack.pop();
                expressionStack.push(new ArithmeticExpression(leftOperand, rightOperand, arithmeticOperator));
            }
        }
        return expressionStack.pop();
    }

    private ArithmeticOperator isArithmeticOperator(String element) {
        for (ArithmeticOperator operator : ArithmeticOperator.values()) {
            if (operator.getDisplay().equals(element)) {
                return operator;
            }
        }

        return null;
    }

}
