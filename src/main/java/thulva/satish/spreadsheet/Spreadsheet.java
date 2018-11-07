package thulva.satish.spreadsheet;

import thulva.satish.spreadsheet.calculator.TopologicalSortProvider;
import thulva.satish.spreadsheet.calculator.CellValueStore;
import thulva.satish.spreadsheet.calculator.expressions.Expression;
import thulva.satish.spreadsheet.calculator.expressions.ExpressionParser;
import thulva.satish.util.CloseShieldedInputStream;
import thulva.satish.util.CloseShieldedOutputStream;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Represents a spreadsheet with <code>numColumns</code> width and <code>numRows</code> height
 *
 * @author satish.thulva. Generated on 07/11/18
 **/
public class Spreadsheet {
    /**
     * Height of the spreadsheet
     */
    private int numRows;
    /**
     * Width of the spreadsheet
     */
    private int numColumns;
    /**
     * The arithmetic expressions found at the cells
     */
    private Map<Cell, Expression> cellExpressionMap = new TreeMap<>();
    /**
     * Final computed value provider for each cell of the spreadsheet
     */
    private CellValueStore cellValueStore = new CellValueStore();

    /**
     * Create a new spreadsheet with given width, height, and cell expressions
     *
     * @param height            Height of the spreadsheet
     * @param width             Width of the spreadsheet
     * @param cellExpressionMap Expressions found at the cells
     */
    public Spreadsheet(int height, int width,
                       Map<Cell, Expression> cellExpressionMap) {
        this.numRows = height;
        this.numColumns = width;
        if (cellExpressionMap != null) {
            this.cellExpressionMap = cellExpressionMap;
        }
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumColumns() {
        return numColumns;
    }

    /**
     * @return Total number of cells in the spreadsheet
     */
    public int size() {
        return numRows * numColumns;
    }

    public Map<Cell, Expression> getCellExpressionMap() {
        return cellExpressionMap;
    }

    /**
     * Try to evaluate the expressions found in all cells. If the evaluation is not possible because of certain
     * validation criteria, this method returns <code>false</code>. Otherwise, returns <code>true</code>
     *
     * @return Result of the attempt to evaluate all cells. If the evaluation is not possible because of certain
     * validation criteria, this method returns <code>false</code>. Otherwise, returns <code>true</code>
     */
    public boolean evaluateCells() {
        TopologicalSortProvider topologicalSortProvider = new TopologicalSortProvider();
        List<Cell> evaluationOrder = topologicalSortProvider.getTopologicalOrder(this);
        if (evaluationOrder == null) {
            return false;
        }
        for (Cell cell : evaluationOrder) {
            double cellValue = cellExpressionMap.get(cell).getValue(cellValueStore);
            cellValueStore.recordCellValue(cell, cellValue);
        }
        return true;
    }

    /**
     * Write the evaluated values of each cell to given output stream
     *
     * @param outputStream destination for the data
     * @throws IOException
     */
    public void writeEvaluatedSpreadsheet(OutputStream outputStream) throws IOException {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(
                new OutputStreamWriter(new CloseShieldedOutputStream(outputStream))))) {
            writer.println(numColumns + " " + numRows);
            for (Cell cell : cellExpressionMap.keySet()) {
                writer.println(String.format("%.5f", cellValueStore.getCellValue(cell)));
            }
        }
    }

    /**
     * Try to parse the spreadsheet readig data from given input stream
     *
     * @param inputStream source of data
     * @return Spreadsheet, if parsing is successful
     * @throws IOException
     */
    public static Spreadsheet parseSpreadsheet(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new CloseShieldedInputStream(inputStream)))) {
            String line = null;

            line = reader.readLine();
            String[] fields = line.split(FIELD_SEPARATOR, -1);
            int numRows = Integer.parseInt(fields[1]);
            int numColumns = Integer.parseInt(fields[0]);

            ExpressionParser expressionParser = new ExpressionParser();
            Map<Cell, Expression> cellExpressionMap = new HashMap<>();

            for (int rowIndex = 1; rowIndex <= numRows; rowIndex += 1) {
                for (int columnIndex = 1; columnIndex <= numColumns; columnIndex += 1) {
                    Cell cell = CellUtils.getCell(rowIndex, columnIndex);
                    Expression expression = expressionParser.parseExpression(reader.readLine());
                    cellExpressionMap.put(cell, expression);
                }
            }

            return new Spreadsheet(numRows, numColumns, cellExpressionMap);
        }
    }

    private static final String FIELD_SEPARATOR = "\\s+";

    public static void main(String[] args) {
        try {
            Spreadsheet spreadsheet = parseSpreadsheet(System.in);
            if (!spreadsheet.evaluateCells()) {
                System.err.println(
                        "Could not compute spreadsheet cell expression values because of invalid data / expression");
                System.exit(2);
            } else {
                spreadsheet.writeEvaluatedSpreadsheet(System.out);
            }
        } catch (Exception e) {
            System.err.println(
                    "Could not compute spreadsheet cell expression values because of invalid data / expression");
            System.exit(1);
        }
    }

}
