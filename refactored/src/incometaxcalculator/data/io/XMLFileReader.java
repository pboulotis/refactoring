package incometaxcalculator.data.io;

public class XMLFileReader extends FileReader {

  protected int checkForFileType(String[] values) {
    if (values[0].equals("<ReceiptID>")) {
      return Integer.parseInt(values[1].trim()); 
    }
    return -1;
  }

  protected String getValueOfFieldPerFileType(String[] values) {
    String valueReversed[] = new StringBuilder(values[1]).reverse().toString().trim()
        .split(" ", 2);
    return new StringBuilder(valueReversed[1]).reverse().toString();    
  }

}
