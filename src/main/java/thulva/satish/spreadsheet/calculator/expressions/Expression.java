package thulva.satish.spreadsheet.calculator.expressions;

import thulva.satish.spreadsheet.Cell;
import thulva.satish.spreadsheet.calculator.CellValueStore;

import java.util.Set;

/**
 * At the basic level, each cell content can be treated as an expression. A constant expression is nothing but an
 * integer. Cell-alias expression is just a reference to another cell. Arithmetic expression can be unary or binary
 *
 * @author satish.thulva. Generated on 07/11/18
 **/
public interface Expression {

    /**
     * Find the value of the given expression
     */
    double getValue(CellValueStore cellValueStore);

    /**
     * @return What kind of expression is this ?
     */
    ExpressionType getExpressionType();

    /**
     * @return All the cells of spreadsheet referenced in this expression
     */
    Set<Cell> getReferencedCells();

}
