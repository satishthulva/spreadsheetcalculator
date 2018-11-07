package thulva.satish.spreadsheet.calculator.expressions;

/**
 * @author satish.thulva. Generated on 07/11/18
 **/
public enum ArithmeticOperator {
    ADDITION("+"),
    SUBTRACTION("-"),
    MULTIPLICATION("*"),
    DIVISION("/"),
    INCREMENT("++"),
    DECREMENT("--");

    private final String display;

    public String getDisplay() {
        return display;
    }

    private ArithmeticOperator(String display) {
        this.display = display;
    }

    public static ArithmeticOperator findOperator(String display) {
        for (ArithmeticOperator operator : values()) {
            if (operator.getDisplay().equals(display)) {
                return operator;
            }
        }

        throw new IllegalOperatorException("No operator found with display name " + display);
    }

}
