package incometaxcalculator.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import incometaxcalculator.data.management.*;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;
public class TestTaxpayerManager {
  private TaxpayerManager manager = new TaxpayerManager();

  
  @Test
  public void testCreateTaxpayer() throws WrongTaxpayerStatusException {
    Exception exception = assertThrows(WrongTaxpayerStatusException.class, ()  
        -> manager.createTaxpayer("Tony Hawk", 1, "something", 100));
    assertEquals("Please check taxpayer's status and try again!", exception.getMessage());

    manager.createTaxpayer("Tony Hawk", 1, "Married Filing Jointly", (float) 1005);
    assertTrue(manager.containsTaxpayer(1));
  }
  
  @Test
  public void TestCreateReceipt() throws WrongReceiptKindException, WrongReceiptDateException, WrongTaxpayerStatusException {
    manager.createTaxpayer("Lara Croft", 2, "Single", (float) 5000000);
    
    Exception wrongKindException = assertThrows(WrongReceiptKindException.class, ()  
        -> manager.createReceipt(1, "1/1/2023", 50, "Fun", "Croft Inc.", "England", "London", "Oxford", 20, 2));
    assertEquals("Please check receipt's kind and try again.", wrongKindException.getMessage());
    
    Exception wrongDateException = assertThrows(WrongReceiptDateException.class, ()  
        -> manager.createReceipt(1, "today", 50, "Basic", "Croft Inc.", "England", "London", "Oxford", 20, 2));
    assertEquals("Please make sure your date is DD/MM/YYYY and try again.", wrongDateException.getMessage());
    
    manager.createReceipt(1, "1/1/2023", 50, "Basic", "Croft Inc.", "England", "London", "Oxford", 20, 2);
    HashMap<Integer, Receipt> receiptsHashMap = manager.getReceiptHashMap(2);
    assertNotNull(receiptsHashMap);
  }
  
  @Test
  public void testRemoveTaxpayer() throws WrongTaxpayerStatusException {
    manager.createTaxpayer("Slade Wilson", 5, "Married Filing Separately", (float) 500000);
    manager.removeTaxpayer(5);
    assertFalse(manager.containsTaxpayer(5));
    Exception exception = assertThrows(NullPointerException.class, ()  
        -> manager.getReceiptHashMap(5));
    assertNotNull(exception.getMessage());

  }
  
}
