package incometaxcalculator.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import incometaxcalculator.data.management.*;

public class TestTaxpayer {
  
  private Taxpayer taxpayer;
  
  @Before
  public void setUp() throws Exception {
      taxpayer = new Taxpayer("Victor Doom", 123456789 ,203395 , 3);
  }
  
  @Test
  public void testCalculateTax() {
    assertEquals(taxpayer.calculateBasicTax(), 15889.98);
  }
  
}
