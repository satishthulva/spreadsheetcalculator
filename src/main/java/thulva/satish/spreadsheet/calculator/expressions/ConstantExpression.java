package thulva.satish.spreadsheet.calculator.expressions;

import thulva.satish.spreadsheet.Cell;
import thulva.satish.spreadsheet.calculator.CellValueStore;

import java.util.Collections;
import java.util.Set;

/**
 * @author satish.thulva. Generated on 07/11/18
 **/
public class ConstantExpression extends CachedExpression {

    public ConstantExpression(double value) {
        this.value = value;
    }

    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.CONSTANT;
    }

    @Override
    protected double evaluateExpression(CellValueStore cellValueStore) {
        return value;
    }

    @Override
    public Set<Cell> getReferencedCells() {
        return Collections.emptySet();
    }
}
