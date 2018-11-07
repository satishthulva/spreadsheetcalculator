package thulva.satish.spreadsheet.calculator.expressions;

import thulva.satish.spreadsheet.Cell;
import thulva.satish.spreadsheet.calculator.CellValueStore;

import java.util.HashSet;
import java.util.Set;

/**
 * @author satish.thulva. Generated on 07/11/18
 **/
public class ArithmeticExpression extends CachedExpression {

    private Expression leftOperand;

    private Expression rightOperand;

    private ArithmeticOperator operator;

    private Set<Cell> referencedCells = new HashSet<>();

    public ArithmeticExpression(Expression leftOperand,
                                Expression rightOperand,
                                ArithmeticOperator operator) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.operator = operator;
        referencedCells.addAll(leftOperand.getReferencedCells());
        if (rightOperand != null) {
            referencedCells.addAll(rightOperand.getReferencedCells());
        }
    }

    @Override
    protected double evaluateExpression(CellValueStore cellValueStore) {
        if (operator == ArithmeticOperator.INCREMENT) {
            return leftOperand.getValue(cellValueStore) + 1;
        } else if (operator == ArithmeticOperator.DECREMENT) {
            return leftOperand.getValue(cellValueStore) - 1;
        } else {
            double leftValue = leftOperand.getValue(cellValueStore);
            double rightValue = rightOperand.getValue(cellValueStore);
            return evaluateBinaryArithmeticExpression(leftValue, rightValue, operator);
        }
    }

    private double evaluateBinaryArithmeticExpression(double left, double right, ArithmeticOperator operator) {
        switch (operator) {
            case ADDITION:
                return left + right;
            case SUBTRACTION:
                return left - right;
            case MULTIPLICATION:
                return left * right;
            case DIVISION:
                if (right == 0.0) {
                    throw new IllegalExpressionException("Division operation with denominator 0");
                }
                return left / right;
            default:
                throw new IllegalExpressionException("Unexpected binary expression " + operator);
        }
    }

    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.ARITHMETIC_OPERATION;
    }

    @Override
    public Set<Cell> getReferencedCells() {
        return referencedCells;
    }
}
