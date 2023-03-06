package incometaxcalculator.data.io;

public class TXTInfoWriter extends InfoWriter {

   
  protected String correctForm(String fieldName, int positionFlag) {
    if (fieldName == "ReceiptID") {
      fieldName = "Receipt ID";
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