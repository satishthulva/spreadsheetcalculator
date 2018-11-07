package thulva.satish.spreadsheet;

import java.util.Objects;

/**
 * Represents a cell in the spreadsheet
 *
 * @author satish.thulva. Generated on 07/11/18
 **/
public class Cell implements Comparable<Cell> {

    private int row;

    private int column;

    private String symbol;

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cell)) {
            return false;
        }
        Cell cell = (Cell) o;
        return getRow() == cell.getRow() &&
                getColumn() == cell.getColumn() &&
                Objects.equals(getSymbol(), cell.getSymbol());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn(), getSymbol());
    }

    Cell(int row, int column, String symbol) {
        this.row = row;
        this.column = column;
        this.symbol = symbol;
    }

    @Override
    public int compareTo(Cell o) {
        int rowDiff = this.getRow() - o.getRow();
        if (rowDiff != 0) {
            return rowDiff;
        }
        return this.getColumn() - o.getColumn();
    }
}
