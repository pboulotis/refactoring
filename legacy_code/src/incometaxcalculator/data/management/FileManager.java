package incometaxcalculator.data.management;

import java.io.File;
import java.io.IOException;


import incometaxcalculator.data.io.FileReader;
import incometaxcalculator.data.io.TXTFileReader;
import incometaxcalculator.data.io.TXTInfoWriter;
import incometaxcalculator.data.io.TXTLogWriter;
import incometaxcalculator.data.io.XMLFileReader;
import incometaxcalculator.data.io.XMLInfoWriter;
import incometaxcalculator.data.io.XMLLogWriter;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class FileManager {
  
  public void saveLogFile(int taxRegistrationNumber, String fileFormat)
      throws IOException, WrongFileFormatException {
    if (fileFormat.equals("txt")) {
      TXTLogWriter writer = new TXTLogWriter();
      writer.generateFile(taxRegistrationNumber);
    } else if (fileFormat.equals("xml")) {
      XMLLogWriter writer = new XMLLogWriter();
      writer.generateFile(taxRegistrationNumber);
    } else {
      throw new WrongFileFormatException();
    }
  }
   
  public void loadTaxpayer(String fileName)
      throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException,
      WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException {

    String ending[] = fileName.split("\\.");
    if (ending[1].equals("txt")) {
      FileReader reader = new TXTFileReader();
      reader.readFile(fileName);
    } else if (ending[1].equals("xml")) {
      FileReader reader = new XMLFileReader();
      reader.readFile(fileName);
    } else {
      throw new WrongFileEndingException();
    }
  }
  
}
