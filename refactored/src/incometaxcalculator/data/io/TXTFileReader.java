package incometaxcalculator.data.io;

public class TXTFileReader extends FileReader {

  protected int checkForFileType(String[] values) {
    if (values[0].equals("Receipt")) {
      if (values[1].equals("ID:")) {
        return Integer.parseInt(values[2].trim());
      }
    }
    return -1;
  }

  protected String getValueOfFieldPerFileType(String[] values) {
    values[1] = values[1].trim();
    return values[1];
  }

}