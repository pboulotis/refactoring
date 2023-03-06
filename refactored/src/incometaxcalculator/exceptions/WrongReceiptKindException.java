package incometaxcalculator.exceptions;

public class WrongReceiptKindException extends Exception {

  private static final long serialVersionUID = -4395008012791682924L;

  public WrongReceiptKindException() {
    super("Please check receipt's kind and try again.");
  }
}
