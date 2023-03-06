package incometaxcalculator.data.io;

public class FileReaderFactory {

  public static FileReader getReaderFormat(String fileFormat) {
    if(fileFormat.equals("txt")){
      return new TXTFileReader();
    }
    else if(fileFormat.equals("xml")){
      return new XMLFileReader();
   }
   return null;
  }
}