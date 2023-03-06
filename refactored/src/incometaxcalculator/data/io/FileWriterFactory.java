package incometaxcalculator.data.io;

public class FileWriterFactory {

  public static FileWriter getWriterFormat(String fileFormat, String ioType) {
    if(fileFormat.equals("txt")){
      if(ioType.equals("log")) {
        return new TXTLogWriter();
      }else if(ioType.equals("info")){
        return new TXTInfoWriter();
      }
    }
    else if(fileFormat.equals("xml")){
      if(ioType.equals("log")) {
        return new XMLLogWriter();
      }else if(ioType.equals("info")){
       return new XMLInfoWriter();
     }
   }
   return null;
  }
}
