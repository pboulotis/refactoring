package incometaxcalculator.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import incometaxcalculator.data.management.*;
import incometaxcalculator.exceptions.WrongFileEndingException;
import incometaxcalculator.exceptions.WrongFileFormatException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

public class TestFileManager {
  private TaxpayerManager manager = new TaxpayerManager();
  private FileManager fileManager = new FileManager();
  private String testFileLinesTXT[] = 
    {"Name: Indiana Jones",
    "AFM: 987654321",
    "Income: 22570.0",
    "Basic Tax: 1207.495",
    "Tax Increase: 96.5996",
    "Total Tax: 96.5996",
    "TotalReceiptsGathered: 0",
    "Entertainment: 0.0",
    "Basic: 0.0",
    "Travel: 0.0",
    "Health: 0.0",
    "Other: 0.0"}; 
  
  private String testFileLinesXML[] = 
    {"<Name> Indiana Jones </Name>",
    "<AFM> 987654321 </AFM>",
    "<Income> 22570.0 </Income>",
    "<BasicTax> 1207.495 </BasicTax>",
    "<TaxIncrease> 96.5996 </TaxIncrease>",
    "<TotalTax> 96.5996 </TotalTax>",
    "<Receipts> 0 </Receipts>",
    "<Entertainment> 0.0 </Entertainment>",
    "<Basic> 0.0 </Basic>",
    "<Travel> 0.0 </Travel>",
    "<Health> 0.0 </Health>",
    "<Other> 0.0 </Other>"};
  
  @Test
  public void testLoadTaxpayer() throws NumberFormatException, IOException, WrongFileFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException{
    Exception fileFormatException = assertThrows(WrongFileFormatException.class, ()  
        ->     fileManager.loadTaxpayer("123456789_INFO.avi"));
    assertEquals("Please check your file format and try again!", fileFormatException.getMessage());
        
    fileManager.loadTaxpayer("123456789_INFO.txt");
    assertEquals(manager.containsTaxpayer(123456789), true);
  }

  @Test
  public void testSaveLogFile() throws IOException, WrongFileFormatException, NumberFormatException, WrongFileEndingException, WrongTaxpayerStatusException, WrongReceiptKindException, WrongReceiptDateException{
    fileManager.loadTaxpayer("987654321_INFO.txt");
    fileManager.saveLogFile(987654321, "txt");
    fileManager.saveLogFile(987654321, "xml");
    BufferedReader readerTXT = new BufferedReader(new FileReader("987654321_LOG.txt")); //This is FileReader from java.io, NOT our class FileReader
    String lineTXT = readerTXT.readLine();
    int i = 0;
    while (lineTXT != null) {
        assertEquals(lineTXT, testFileLinesTXT[i]);
        lineTXT = readerTXT.readLine();
        i++;
    }
    readerTXT.close();
    
    BufferedReader readerXML = new BufferedReader(new FileReader("987654321_LOG.xml"));
    String lineXML = readerXML.readLine();
    i = 0;
    while (lineXML != null) {
        assertEquals(lineXML, testFileLinesXML[i]);
        lineXML = readerXML.readLine();
        i++;
    }
    readerXML.close();   
  }
}
