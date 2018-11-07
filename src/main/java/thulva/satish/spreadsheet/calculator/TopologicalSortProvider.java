package thulva.satish.spreadsheet.calculator;

import thulva.satish.spreadsheet.Cell;
import thulva.satish.spreadsheet.CellUtils;
import thulva.satish.spreadsheet.Spreadsheet;
import thulva.satish.spreadsheet.calculator.expressions.Expression;

import java.util.*;

/**
 * @author satish.thulva. Generated on 08/11/18
 **/
public class TopologicalSortProvider {

    public List<Cell> getTopologicalOrder(Spreadsheet spreadsheet) {
        Map<Cell, Integer> inDegreeMap = new HashMap<>();
        Map<Cell, Set<Cell>> dependingCellMap = new HashMap<>();

        for (Map.Entry<Cell, Expression> cellExpressionEntry : spreadsheet.getCellExpressionMap().entrySet()) {
            Cell dependingCell = cellExpressionEntry.getKey();
            Collection<Cell> dependsOn = cellExpressionEntry.getValue().getReferencedCells();
            inDegreeMap.put(dependingCell, dependsOn.size());
            for (Cell dependentOn : dependsOn) {
                if (!CellUtils.isValidCell(dependentOn, spreadsheet.getNumRows(), spreadsheet.getNumColumns())) {
                    return null;
                }
                if (!dependingCellMap.containsKey(dependentOn)) {
                    dependingCellMap.put(dependentOn, new HashSet<>());
                }
                dependingCellMap.get(dependentOn).add(dependingCell);
            }
        }

        Queue<Cell> zeroInDegreeCells = new LinkedList<>();
        for (Map.Entry<Cell, Integer> cellInDegreeEntry : inDegreeMap.entrySet()) {
            if (cellInDegreeEntry.getValue() == 0) {
                zeroInDegreeCells.add(cellInDegreeEntry.getKey());
            }
        }

        List<Cell> topologicalOrder = new ArrayList<>();

        int visitedCellCount = 0;
        while (!zeroInDegreeCells.isEmpty()) {
            Cell cell = zeroInDegreeCells.poll();
            topologicalOrder.add(cell);

            if (dependingCellMap.containsKey(cell)) {
                for (Cell dependent : dependingCellMap.get(cell)) {
                    inDegreeMap.put(dependent, inDegreeMap.get(dependent) - 1);
                    if (inDegreeMap.get(dependent) == 0) {
                        zeroInDegreeCells.add(dependent);
                    }
                }
            }

            visitedCellCount += 1;
        }

        if (visitedCellCount != spreadsheet.size()) {
            return null;
        }

        return topologicalOrder;
    }

}
