package incometaxcalculator.data.io;

public class XMLInfoWriter extends InfoWriter {
  
  protected String correctForm(String fieldName, int positionFlag) {
    if (positionFlag == 0) {
      return "<"+fieldName+"> ";
    }
    return " </"+fieldName+">";
  }
  
  protected String writeTypeOfFile() {
    return "xml";
  }

}