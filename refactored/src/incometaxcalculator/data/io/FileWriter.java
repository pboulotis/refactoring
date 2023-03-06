package incometaxcalculator.data.io;

import java.io.IOException;

public interface FileWriter {

  public abstract void generateFile(int taxRegistrationNumber) throws IOException;
  
}