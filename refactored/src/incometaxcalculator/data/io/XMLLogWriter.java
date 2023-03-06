package incometaxcalculator.data.io;

public class XMLLogWriter extends LogWriter {
  
  private String [] wrongFormatList = {"Basic Tax", "Tax Increase","Total Tax"};
  private String [] correctFormatList = {"BasicTax", "TaxIncrease","TotalTax"};
  
  protected String correctForm(String fieldName, int positionFlag) {
    for(int i=0; i< wrongFormatList.length; i++) {
      if(fieldName.equals(wrongFormatList[i])) {
        fieldName = correctFormatList[i];
      }      
    }
    
    if (positionFlag == 0) {
        return "<"+fieldName+"> ";
    }
    return " </"+fieldName+">";
  }

  protected String writeTypeOfFile() {
    return "xml";
  }

}
