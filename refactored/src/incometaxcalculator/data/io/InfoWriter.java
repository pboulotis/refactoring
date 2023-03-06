package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

public abstract class InfoWriter implements FileWriter {

  public TaxpayerManager manager = new TaxpayerManager();
    
  public void generateFile(int taxRegistrationNumber) throws IOException {

      PrintWriter outputStream = new PrintWriter(
          new java.io.FileWriter(taxRegistrationNumber + "_INFO." + writeTypeOfFile()));
      displayTaxpayerInfo(outputStream, taxRegistrationNumber);
      generateTaxpayerReceipts(taxRegistrationNumber, outputStream);
      outputStream.close();
  }

  private void generateTaxpayerReceipts(int taxRegistrationNumber, PrintWriter outputStream) {

      HashMap<Integer, Receipt> receiptsHashMap = manager.getReceiptHashMap(taxRegistrationNumber);
      Iterator<HashMap.Entry<Integer, Receipt>> iterator = receiptsHashMap.entrySet().iterator();
      while (iterator.hasNext()) {
        HashMap.Entry<Integer, Receipt> entry = iterator.next();
        Receipt receipt = entry.getValue();
        displayReceiptInfo(outputStream, receipt);
      }
      
  }
  
  private void displayReceiptInfo(PrintWriter outputStream, Receipt receipt) {
    
    outputStream.println(correctForm("ReceiptID", 0) + receipt.getId() + correctForm("ReceiptID", 1));
    outputStream.println(correctForm("Date", 0) + receipt.getIssueDate() + correctForm("Date", 1));
    outputStream.println(correctForm("Kind", 0) + receipt.getKind() + correctForm("Kind", 1));
    outputStream.println(correctForm("Amount", 0) + receipt.getAmount() + correctForm("Amount", 1));
    outputStream.println(correctForm("Company", 0) + receipt.getCompany().getName() + correctForm("Company", 1));
    outputStream.println(correctForm("Country", 0) + receipt.getCompany().getCountry() + correctForm("Country", 1));
    outputStream.println(correctForm("City", 0) + receipt.getCompany().getCity() + correctForm("City", 1));
    outputStream.println(correctForm("Street", 0) + receipt.getCompany().getStreet() + correctForm("Street", 1));
    outputStream.println(correctForm("Number", 0) + receipt.getCompany().getNumber() + correctForm("Number", 1));
    outputStream.println();
  }

  private void displayTaxpayerInfo(PrintWriter outputStream,int taxRegistrationNumber ) {
    
    outputStream.println(correctForm("Name", 0) + manager.getTaxpayerName(taxRegistrationNumber) + correctForm("Name", 1));
    outputStream.println(correctForm("AFM", 0) + taxRegistrationNumber + correctForm("AFM", 1));
    outputStream.println(correctForm("Status", 0) + manager.getTaxpayerStatusString(taxRegistrationNumber) + correctForm("Status", 1));
    outputStream.println(correctForm("Income", 0) + manager.getTaxpayerIncome(taxRegistrationNumber) + correctForm("Income", 1));
    outputStream.println();
    outputStream.println(correctForm("Receipts", 0));
    outputStream.println();    
  }

  protected abstract String writeTypeOfFile();

  protected abstract String correctForm(String string, int i);

}

