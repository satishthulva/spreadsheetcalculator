package thulva.satish.spreadsheet;

/**
 * Utility methods to work on cell creation and validation
 *
 * @author satish.thulva. Generated on 07/11/18
 **/
public class CellUtils {

    /**
     * Construct and return a cell with given row and column number
     *
     * @param rowNumber    Row number of the cell, assuming counting starts from 1
     * @param columnNumber column number of the cell, assuming counting starts from 1
     * @return Cell, with given row and column number
     */
    public static Cell getCell(int rowNumber, int columnNumber) {
        char alphabet = (char) ('A' + (rowNumber - 1));
        return new Cell(rowNumber, columnNumber, alphabet + "" + columnNumber);
    }

    /**
     * From given symbol to denote a cell, construct the cell instance and return
     *
     * @param cellSymbol Symbol to denote the cell
     * @return Cell instance with given symbol
     */
    public static Cell getCellForSymbol(String cellSymbol) {
        int rowNumber = cellSymbol.charAt(0) - 'A' + 1;
        int columnNumber = Integer.parseInt(cellSymbol.substring(1));

        return new Cell(rowNumber, columnNumber, cellSymbol);
    }

    /**
     * Find whether given cell is valid for a spreadsheet with given size
     *
     * @param cell    cell to verify validity
     * @param numRows height of the spreadsheet
     * @param numCols width of the spreadsheet
     * @return <code>true</code>, if cell is valid. <code>false</code>, otherwise
     */
    public static boolean isValidCell(Cell cell, int numRows, int numCols) {
        return cell.getRow() <= numRows && cell.getColumn() <= numCols;
    }

}
