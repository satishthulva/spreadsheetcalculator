package thulva.satish.spreadsheet.calculator.expressions;

import thulva.satish.spreadsheet.calculator.CellValueStore;

/**
 * An expression whose value is computed just once and cached from then on to save computation time
 *
 * @author satish.thulva. Generated on 07/11/18
 **/
public abstract class CachedExpression implements Expression {

    protected Double value;

    private void cacheExpressionValue(CellValueStore cellValueStore) {
        value = evaluateExpression(cellValueStore);
    }

    protected abstract double evaluateExpression(CellValueStore cellValueStore);

    @Override
    public double getValue(CellValueStore cellValueStore) {
        if (value == null) {
            cacheExpressionValue(cellValueStore);
        }

        return value.doubleValue();
    }
}
