package incometaxcalculator.data.io;

public class TXTLogWriter extends LogWriter {

  protected String correctForm(String fieldName, int positionFlag) {
    if(fieldName.equals("Receipts")) {
      fieldName = "TotalReceiptsGathered";
    }      
    
    if (positionFlag == 0) {
        return fieldName+": ";
    }
    return "";
  }

  protected String writeTypeOfFile() {
    return "txt";
  }
}
