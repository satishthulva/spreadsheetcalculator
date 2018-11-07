package thulva.satish.spreadsheet.calculator.expressions;

import thulva.satish.spreadsheet.Cell;
import thulva.satish.spreadsheet.calculator.CellValueStore;

import java.util.HashSet;
import java.util.Set;

/**
 * Refers another cell
 *
 * @author satish.thulva. Generated on 07/11/18
 **/
public class CellAliasExpression extends CachedExpression {

    private Cell cell;

    private Set<Cell> referencedCells = new HashSet<>();

    public CellAliasExpression(Cell cell) {
        this.cell = cell;
        referencedCells.add(cell);
    }

    @Override
    public ExpressionType getExpressionType() {
        return ExpressionType.CELL_ALIAS;
    }

    @Override
    protected double evaluateExpression(CellValueStore cellValueStore) {
        return cellValueStore.getCellValue(cell);
    }

    @Override
    public Set<Cell> getReferencedCells() {
        return referencedCells;
    }
}
