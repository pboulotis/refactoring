package incometaxcalculator.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DecimalFormat;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import incometaxcalculator.data.management.*;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;

public class TestTaxpayer {
  
  private ArrayList<Taxpayer> taxpayerList = new ArrayList<>();
  private ArrayList<Receipt> receiptList = new ArrayList<>();
  private static final DecimalFormat df = new DecimalFormat("0.00");

  private float [] incomeList = {
      3000,36090,12000,143360,254250,
      18020,70000,88950,90050,200000,
      24675, 81075, 89995, 152535, 152541,
      30385, 89995, 122105, 203385, 203395
    };
  private String [] taxpayerNames = {
      "Luke Cage", "Jessica Jones","Hank Pym", "Sue Storm", "Reed Richards",
      "Selina Kyle","Wally West", "Lois Lane", "Clark Kent", "Oliver Queen",
      "Monkey Luffy", "Roronoa Zoro", "Nico Robin", "Vinsmoke Sanji", "Tony Chopper",
      "Knuckles Echidna", "Shadow Hedgehog", "Amy Rose", "Tails Fox", "Sonic Hedgehog"
      };
  private double [] basicTaxResults= {
      160.5, 1930.985, 642.0, 9493.605, 18198.67,
      964.069, 4628.32, 6102.455, 6188.805, 16277.48,
      1320.1125, 5296.22749, 5996.4075, 10905.7975, 10906.2885, 
      1625.59749, 5828.02249, 8091.78249, 14472.21749, 14473.1025 
    };
  private String[] receiptKinds = {"Entertainment","Basic", "Travel", "Health", "Other"};
  private Company company = new Company("Hydra", "Everywhere", "classified", "classified", 0);
  
  @Before
  public void setUp() throws Exception {
    int status = 0;
    for(int i=0; i<20;i++) {
      
      if (i%5 == 0 && i!=0) {
        status++;        
      }
      taxpayerList.add(new Taxpayer(taxpayerNames[i], i ,incomeList[i] ,status));    
      if(i<5) {
        receiptList.add(new Receipt(i, "1/1/2023", 10 , receiptKinds[i], company));
      }
    }    
  }
  
  @Test
  public void testCalculateTax() {
    
    int i = 0;
    for(Taxpayer taxpayer: taxpayerList) {
      assertEquals(df.format(taxpayer.calculateBasicTax()), df.format(basicTaxResults[i]));  
      i++;
    }   
  }
  
  @Test
  public void TestAddReceipt() throws WrongReceiptKindException {
    int currentNumberOfTotalReceipts;
    float currentAmmountPerReceiptKind;
    int kind = 0;
    Taxpayer taxpayer = taxpayerList.get(0);
    for(Receipt receipt: receiptList) {
      currentNumberOfTotalReceipts = taxpayer.getTotalReceiptsGathered();
      currentAmmountPerReceiptKind = taxpayer.getAmountOfReceiptKind((short) kind);
      taxpayer.addReceipt(receipt);
      
      assertEquals(taxpayer.getTotalReceiptsGathered(), currentNumberOfTotalReceipts+1);
      assertEquals(taxpayer.getReceiptHashMap().containsKey(receipt.getId()), true);  
      assertEquals(taxpayer.getAmountOfReceiptKind((short) kind),
          currentAmmountPerReceiptKind + receipt.getAmount());
      kind++;
    }
  }
  
  @Test
  public void TestRemoveReceipt() throws WrongReceiptKindException {
    int currentNumberOfTotalReceipts;
    float currentAmmountPerReceiptKind;
    int kind = 0;
    Taxpayer taxpayer = taxpayerList.get(0);
    for(Receipt receipt: receiptList) {
      taxpayer.addReceipt(receipt);
        
      currentNumberOfTotalReceipts = taxpayer.getTotalReceiptsGathered();
      currentAmmountPerReceiptKind = taxpayer.getAmountOfReceiptKind((short) kind);
      taxpayer.removeReceipt(receipt.getId());
      assertEquals(taxpayer.getTotalReceiptsGathered(), currentNumberOfTotalReceipts-1);
      assertEquals(taxpayer.getReceiptHashMap().containsKey(receipt.getId()), false);  
      assertEquals(taxpayer.getAmountOfReceiptKind((short) kind), currentAmmountPerReceiptKind - receipt.getAmount());
      kind++;
    }
  }
  
  @Test
  public void testGetVariationTaxOnReceipts() throws WrongReceiptDateException, WrongReceiptKindException {
    Taxpayer taxpayer = new Taxpayer("Michael Morbius", 50, 100, 3);
    receiptList.add(new Receipt(5, "1/1/2023", 10 , receiptKinds[4], company));
    receiptList.add(new Receipt(6, "1/1/2023", 10 , receiptKinds[4], company));
    for(Receipt receipt: receiptList) {
      taxpayer.addReceipt(receipt);
    }
    assertEquals(df.format(taxpayer.getVariationTaxOnReceipts()),df.format(-1.605));
    taxpayer.removeReceipt(6);
    taxpayer.removeReceipt(5);
    assertEquals(df.format(taxpayer.getVariationTaxOnReceipts()),df.format(-0.8025));
    taxpayer.removeReceipt(4);
    taxpayer.removeReceipt(3);
    assertEquals(df.format(taxpayer.getVariationTaxOnReceipts()),df.format(0.214));
    taxpayer.removeReceipt(2);
    taxpayer.removeReceipt(1);
    assertEquals(df.format(taxpayer.getVariationTaxOnReceipts()),df.format(0.428));
  }
  
}
