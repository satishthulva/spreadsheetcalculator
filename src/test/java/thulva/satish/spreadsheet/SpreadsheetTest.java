package thulva.satish.spreadsheet;

import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * @author satish.thulva. Generated on 08/11/18
 **/
public class SpreadsheetTest {

    @Test
    public void singleCellConstantTest() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(new ByteArrayInputStream("1 1\n10".getBytes()));
            Assert.assertTrue(spreadsheet.evaluateCells());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            spreadsheet.writeEvaluatedSpreadsheet(byteArrayOutputStream);
            Assert.assertEquals("1 1\n10.00000\n", new String(byteArrayOutputStream.toByteArray()));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void singleCellCyclicDependencyTest() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(
                    new ByteArrayInputStream("1 1\nA1".getBytes()));
            Assert.assertFalse(spreadsheet.evaluateCells());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void singleCellAdditionExpressionTest() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(new ByteArrayInputStream("1 1\n10 3 +".getBytes()));
            Assert.assertTrue(spreadsheet.evaluateCells());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            spreadsheet.writeEvaluatedSpreadsheet(byteArrayOutputStream);
            Assert.assertEquals("1 1\n13.00000\n", new String(byteArrayOutputStream.toByteArray()));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void singleCellDeletionExpressionTest() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(new ByteArrayInputStream("1 1\n10 3 -".getBytes()));
            Assert.assertTrue(spreadsheet.evaluateCells());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            spreadsheet.writeEvaluatedSpreadsheet(byteArrayOutputStream);
            Assert.assertEquals("1 1\n7.00000\n", new String(byteArrayOutputStream.toByteArray()));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void singleCellMultiplicationTest() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(new ByteArrayInputStream("1 1\n10 3 *".getBytes()));
            Assert.assertTrue(spreadsheet.evaluateCells());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            spreadsheet.writeEvaluatedSpreadsheet(byteArrayOutputStream);
            Assert.assertEquals("1 1\n30.00000\n", new String(byteArrayOutputStream.toByteArray()));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void singleCellDivisionExpressionTest() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(new ByteArrayInputStream("1 1\n10 3 /".getBytes()));
            Assert.assertTrue(spreadsheet.evaluateCells());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            spreadsheet.writeEvaluatedSpreadsheet(byteArrayOutputStream);
            Assert.assertEquals("1 1\n3.33333\n", new String(byteArrayOutputStream.toByteArray()));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void singleCellDivisionByZeroExpressionTest() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(new ByteArrayInputStream("1 1\n10 0 /".getBytes()));
            Assert.assertTrue(spreadsheet.evaluateCells());
            Assert.fail("Did not catch division by 0");
        } catch (Exception e) {
        }
    }

    @Test
    public void singleRowMultiCellExpression() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(
                    new ByteArrayInputStream("2 1\nA2\n10 3 /".getBytes()));
            Assert.assertTrue(spreadsheet.evaluateCells());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            spreadsheet.writeEvaluatedSpreadsheet(byteArrayOutputStream);
            Assert.assertEquals("2 1\n3.33333\n3.33333\n", new String(byteArrayOutputStream.toByteArray()));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void singleColumnMultiRowTest() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(
                    new ByteArrayInputStream("1 2\nB1\n10 3 /".getBytes()));
            Assert.assertTrue(spreadsheet.evaluateCells());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            spreadsheet.writeEvaluatedSpreadsheet(byteArrayOutputStream);
            Assert.assertEquals("1 2\n3.33333\n3.33333\n", new String(byteArrayOutputStream.toByteArray()));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void NonSenseCellDependencyTest() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(
                    new ByteArrayInputStream("1 2\nC1\n10 3 /".getBytes()));
            Assert.assertFalse(spreadsheet.evaluateCells());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void multiRowMultiCellTest() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(
                    new ByteArrayInputStream("2 2\nB1\n10 3 /\n13 ++\n10 --".getBytes()));
            Assert.assertTrue(spreadsheet.evaluateCells());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            spreadsheet.writeEvaluatedSpreadsheet(byteArrayOutputStream);
            Assert.assertEquals("2 2\n14.00000\n3.33333\n14.00000\n9.00000\n",
                    new String(byteArrayOutputStream.toByteArray()));
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void multiRowMultiCellCyclicDependencyTest() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(
                    new ByteArrayInputStream("2 2\nB1\n1\nA1\n10 --".getBytes()));
            Assert.assertFalse(spreadsheet.evaluateCells());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void multiRowMultiCellCyclicDependencyTestTwo() {
        try {
            Spreadsheet spreadsheet = Spreadsheet.parseSpreadsheet(
                    new ByteArrayInputStream("2 2\nB1\nA1\nA2\n10 --".getBytes()));
            Assert.assertFalse(spreadsheet.evaluateCells());
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
    }

}
