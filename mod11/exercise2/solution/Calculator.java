public class Calculator {

  private static final char NO_OP = '\0';
  private static final char PLUS = '+';
  private static final char SUBTRACT = '-';
  private static final char MULTIPLY = '*';
  private static final char DIVIDE = '/';

  private float  number1 = 0.0F;
  //private float  number2 = Float.NaN;
  private char   operator = NO_OP;

  public String opEquals(String number) {
    float result;

    if ( operator == NO_OP ) {
      result = parseNumber(number);
    } else {
      result = performOperation(parseNumber(number));
    }
    operator = NO_OP;

    number1 = result;

    return Float.toString(result);
  }

  public String opAdd(String number) {
    float result;

    if ( operator == NO_OP ) {
      result = parseNumber(number);
    } else {
      result = performOperation(parseNumber(number));
    }
    operator = PLUS;

    number1 = result;

    return Float.toString(result);
  }

  public String opSubtract(String number) {
    float result;

    if ( operator == NO_OP ) {
      result = parseNumber(number);
    } else {
      result = performOperation(parseNumber(number));
    }
    operator = SUBTRACT;

    number1 = result;

    return Float.toString(result);
  }

  public String opMultiply(String number) {
    float result;

    if ( operator == NO_OP ) {
      result = parseNumber(number);
    } else {
      result = performOperation(parseNumber(number));
    }
    operator = MULTIPLY;

    number1 = result;

    return Float.toString(result);
  }

  public String opDivide(String number) {
    float result;

    if ( operator == NO_OP ) {
      result = parseNumber(number);
    } else {
      result = performOperation(parseNumber(number));
    }
    operator = DIVIDE;

    number1 = result;

    return Float.toString(result);
  }

  private float performOperation(float number2) {
    float result = 0.0F;

    switch ( operator ) {
    case PLUS:
      result = number1 + number2;
      break;
    case SUBTRACT:
      result = number1 - number2;
      break;
    case MULTIPLY:
      result = number1 * number2;
      break;
    case DIVIDE:
      result = number1 / number2;
      break;
    }

    return result;
  }

  private static float parseNumber(String number) {
    float real_number;

    try {
      real_number = Float.parseFloat(number);
    } catch (NumberFormatException e) {
      real_number = Float.NaN;
    }

    return real_number;
  }

  public static void main(String[] args) {
    Calculator calc = new Calculator();

    System.out.println("2.0 + >> " + calc.opAdd("2.0"));
    System.out.println("3.0 = >> " + calc.opEquals("3.0"));
    System.out.println("15.3 * >> " + calc.opMultiply("15.3"));
    System.out.println("2.0 / >> " + calc.opDivide("2.0"));
  }
}
