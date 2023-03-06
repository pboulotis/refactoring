package incometaxcalculator.data.management;

import java.util.HashMap;

import incometaxcalculator.exceptions.WrongReceiptKindException;

public class Taxpayer {

  protected final String fullname;
  protected final int taxRegistrationNumber;
  protected final float income;
  private float amountPerReceiptsKind[] = new float[5];
  private int totalReceiptsGathered = 0;
  private HashMap<Integer, Receipt> receiptHashMap = new HashMap<Integer, Receipt>(0);
  private String[] receiptKinds = {"Entertainment","Basic", "Travel", "Health", "Other"};
  
  private int taxpayerStatus;
  private double [] taxPercentages = {0.0535, 0.0705, 0.705, 0.0785, 0.098};
  private double [][] incomeLowerThanTresholds =
    {
    {30390, 90000, 122110, 203390},   //Head of household
    {36080, 90000, 143350, 254240},   //Married filing jointly
    {18040, 71680, 90000, 127120},    //Married filing separately
    {24680, 81080, 90000, 152540}     //Single and ready to mingle
    };
  private double [][] baseTaxValues =
    {
      {0, 1625.87, 5828.38, 8092.13, 14472.61},
      {0, 1930.28, 5731.64, 9492.82, 18197.69},
      {0, 965.14, 4746.76, 6184.88, 9098.80},
      {0, 1320.38, 5296.58, 5996.80, 10906.19}
    };
  private double [][] taxValuesSubtracted =
    {
      {0, 30390, 90000, 122110, 203390},
      {0, 36080, 90000, 143350, 254240},
      {0, 18040, 71680, 90000, 127120},
      {0, 24680, 81080, 90000, 152540}
    };
  
  public Taxpayer(String fullname, int taxRegistrationNumber, float income, int taxpayerStatus) {
    this.fullname = fullname;
    this.taxRegistrationNumber = taxRegistrationNumber;
    this.income = income;
    this.taxpayerStatus = taxpayerStatus;
  }
  
  public double calculateBasicTax() {
    
    for (int i =0; i <= 3; i++) { 
      if(taxpayerStatus == i) {
         return calculateBasicTaxByStatus(taxpayerStatus);
      }
    }
    return 0;
  }
  
  private double calculateBasicTaxByStatus(int status) {
    
    for (int j =0; j<incomeLowerThanTresholds[status].length; j++) {
      if (income < incomeLowerThanTresholds[status][j]) {
        return calculateBasicTaxAlgorithm(status,j);
      }
    }
    return calculateBasicTaxAlgorithm(status,4);
  }
  
  private double calculateBasicTaxAlgorithm(int status, int percentage) {
    return baseTaxValues[status][percentage] + taxPercentages[percentage]
        * (income - taxValuesSubtracted[status][percentage]);
  }
  
  public void addReceipt(Receipt receipt) throws WrongReceiptKindException {
    
    for (int i = 0; i <= 6; i++) {
      if (i==5) {
        throw new WrongReceiptKindException();
      }
      if (receipt.getKind().equals(receiptKinds[i])) {
        amountPerReceiptsKind[i] += receipt.getAmount();
        break;
      }
    }
    
    receiptHashMap.put(receipt.getId(), receipt);
    totalReceiptsGathered++;
  }

  public void removeReceipt(int receiptId) throws WrongReceiptKindException {
    Receipt receipt = receiptHashMap.get(receiptId);
    
    for (int i = 0; i <= 6; i++) {
      if (i==5) {
        throw new WrongReceiptKindException();
      }
      if (receipt.getKind().equals(receiptKinds[i])) {
        amountPerReceiptsKind[i] -= receipt.getAmount();
        break;
      }
    }
    
    totalReceiptsGathered--;
    receiptHashMap.remove(receiptId);
  }

  public String getFullname() {
    return fullname;
  }

  public int getTaxRegistrationNumber() {
    return taxRegistrationNumber;
  }

  public float getIncome() {
    return income;
  }

  public HashMap<Integer, Receipt> getReceiptHashMap() {
    return receiptHashMap;
  }

  public double getVariationTaxOnReceipts() {
    float totalAmountOfReceipts = getTotalAmountOfReceipts();
    double[] receiptsToIncomePercentages =  {0.2, 0.4, 0.6};
    double[] variationTaxPercentages = {0.08, 0.04, -0.15};
    
    for (int i = 0; i < receiptsToIncomePercentages.length; i++) {
      if (totalAmountOfReceipts < receiptsToIncomePercentages[i] * income) {
        return calculateBasicTax() * variationTaxPercentages[i];
      }
    }
    return -calculateBasicTax() * 0.3;
  }

  private float getTotalAmountOfReceipts() {
    int sum = 0;
    for (int i = 0; i < 5; i++) {
      sum += amountPerReceiptsKind[i];
    }
    return sum;
  }

  public int getTotalReceiptsGathered() {
    return totalReceiptsGathered;
  }
  
  public int getTaxpayerStatus() { //EGO TO EVALA AYTO
    return taxpayerStatus;
  }

  public float getAmountOfReceiptKind(short kind) {
    return amountPerReceiptsKind[kind];
  }

  public double getTotalTax() {
    return calculateBasicTax() + getVariationTaxOnReceipts();
  }

  public double getBasicTax() {
    return calculateBasicTax();
  }

}