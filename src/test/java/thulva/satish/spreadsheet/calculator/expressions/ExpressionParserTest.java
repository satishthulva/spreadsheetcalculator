package thulva.satish.spreadsheet.calculator.expressions;

import org.junit.Assert;
import org.junit.Test;
import thulva.satish.spreadsheet.CellUtils;
import thulva.satish.spreadsheet.calculator.CellValueStore;

/**
 * @author satish.thulva. Generated on 08/11/18
 **/
public class ExpressionParserTest {

    private ExpressionParser expressionParser = new ExpressionParser();

    private CellValueStore cellValueStore = new CellValueStore();

    @Test
    public void parseConstantExpression() {
        Expression expression = expressionParser.parseExpression("10");
        Assert.assertEquals(ExpressionType.CONSTANT, expression.getExpressionType());
        Assert.assertEquals(10.0f, expression.getValue(cellValueStore), 0.00001f);
    }

    @Test
    public void parseCellAliasExpression() {
        Expression expression = expressionParser.parseExpression("A10");
        Assert.assertEquals(ExpressionType.CELL_ALIAS, expression.getExpressionType());
        cellValueStore.recordCellValue(CellUtils.getCellForSymbol("A10"), 10.0f);
        Assert.assertEquals(10.0f, expression.getValue(cellValueStore), 0.00001f);
    }

    @Test
    public void testAddition() {
        Expression expression = expressionParser.parseExpression("10 3 +");
        Assert.assertEquals(ExpressionType.ARITHMETIC_OPERATION, expression.getExpressionType());
        Assert.assertEquals(13.0f, expression.getValue(cellValueStore), 0.00001f);
    }

    @Test
    public void testUnaryOperator() {
        Expression expression = expressionParser.parseExpression("10 ++");
        Assert.assertEquals(ExpressionType.ARITHMETIC_OPERATION, expression.getExpressionType());
        Assert.assertEquals(11.0f, expression.getValue(cellValueStore), 0.00001f);
    }

    @Test
    public void testNegativeValues() {
        Expression expression = expressionParser.parseExpression("10 -3 +");
        Assert.assertEquals(ExpressionType.ARITHMETIC_OPERATION, expression.getExpressionType());
        Assert.assertEquals(7.0f, expression.getValue(cellValueStore), 0.00001f);
    }

}
