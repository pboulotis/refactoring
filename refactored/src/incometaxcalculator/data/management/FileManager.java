package incometaxcalculator.data.management;

import java.io.IOException;

import incometaxcalculator.data.io.FileReader;
import incometaxcalculator.data.io.FileReaderFactory;
import incometaxcalculator.data.io.FileWriter;
import incometaxcalculator.data.io.FileWriterFactory;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class FileManager {
  
  public void saveLogFile(int taxRegistrationNumber, String fileFormat)
      throws IOException, WrongFileFormatException {
    
    if (!isCorrectFileFormat(fileFormat)) {
      throw new WrongFileFormatException();
    }
    FileWriter writer = FileWriterFactory.getWriterFormat(fileFormat,"log");
    writer.generateFile(taxRegistrationNumber);
  }
   
  public void loadTaxpayer(String fileName)
      throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException,
      WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {
    
    String ending[] = fileName.split("\\.");
    if (!isCorrectFileFormat(ending[1])) {
      throw new WrongFileFormatException();
    }
    FileReader reader = FileReaderFactory.getReaderFormat(ending[1]);
    reader.readFile(fileName);
  }
  
  private boolean isCorrectFileFormat(String fileFormat) {
    if (fileFormat.equals("txt") || fileFormat.equals("xml")) {
      return true;
    }
    return false;
  }
}
