package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;

import incometaxcalculator.data.management.TaxpayerManager;

public abstract class LogWriter implements FileWriter {
  
  private String[] receiptKinds = {"Entertainment","Basic", "Travel", "Health", "Other"};
  private TaxpayerManager manager = new TaxpayerManager();
  
  public void generateFile(int taxRegistrationNumber) throws IOException {
    PrintWriter outputStream = new PrintWriter(
        new java.io.FileWriter(taxRegistrationNumber + "_LOG."+ writeTypeOfFile()));
    displayTaxpayerInfo(outputStream, taxRegistrationNumber);
    displayTaxInfo(outputStream, taxRegistrationNumber);
    displayKinds(outputStream, taxRegistrationNumber);
    outputStream.close();
  }
  
  private void displayTaxpayerInfo(PrintWriter outputStream, int taxRegistrationNumber) {
    outputStream.println(correctForm("Name", 0) + manager.getTaxpayerName(taxRegistrationNumber) + correctForm("Name", 1));
    outputStream.println(correctForm("AFM", 0) + taxRegistrationNumber + correctForm("AFM", 1));
    outputStream.println(correctForm("Income", 0) + manager.getTaxpayerIncome(taxRegistrationNumber) + correctForm("Income", 1));    
  }
  
  private void displayTaxInfo(PrintWriter outputStream, int taxRegistrationNumber) {
    outputStream
    .println(correctForm("Basic Tax", 0) + manager.getTaxpayerBasicTax(taxRegistrationNumber) + correctForm("Basic Tax", 1));
    if (manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0) {
      outputStream.println(correctForm("Tax Increase", 0)
          + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + correctForm("Tax Increase", 1));
    } else {
      outputStream.println(correctForm("Tax Decrease", 0)
          + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + correctForm("Tax Decrease", 1));
    }
    outputStream
        .println(correctForm("Total Tax", 0) + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + correctForm("Total Tax", 1));
    outputStream.println(
        correctForm("Receipts", 0) + manager.getTaxpayerTotalReceiptsGathered(taxRegistrationNumber) + correctForm("Receipts", 1));
  }
  
  private void displayKinds(PrintWriter outputStream, int taxRegistrationNumber) {
    int kind = 0;
    for(int i=0;i<receiptKinds.length;i++) { 
      outputStream.println(
          correctForm(receiptKinds[i], 0) + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, (short) kind)
          + correctForm(receiptKinds[i], 1));
    }
  }

  protected abstract String writeTypeOfFile();

  protected abstract String correctForm(String string, int i);
}
