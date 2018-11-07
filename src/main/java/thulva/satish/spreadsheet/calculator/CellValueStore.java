package thulva.satish.spreadsheet.calculator;

import thulva.satish.spreadsheet.Cell;

import java.util.HashMap;
import java.util.Map;

/**
 * Storage for cell values
 *
 * @author satish.thulva. Generated on 07/11/18
 **/
public class CellValueStore {

    private Map<Cell, Double> cellValueMap = new HashMap<>();

    public void recordCellValue(Cell cell, double value) {
        cellValueMap.put(cell, value);
    }

    public Double getCellValue(Cell cell) {
        return cellValueMap.get(cell);
    }

}
