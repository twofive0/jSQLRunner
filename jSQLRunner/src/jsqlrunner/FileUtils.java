/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsqlrunner;

import java.io.*;

/**
 *
 * @author Devin
 */
public class FileUtils {
    
public String readFile(String filename)
{
   String content = null;
   File file = new File(filename); //for ex foo.txt
   try {
       FileReader reader = new FileReader(file);
       char[] chars = new char[(int) file.length()];
       reader.read(chars);
       content = new String(chars);
       reader.close();
   } catch (IOException e) {
       e.printStackTrace();
   }
return content;
}

public Boolean writeFile(String filename, String textToWrite)
{
   Boolean writeSuccess = false;
   File file = new File(filename); //for ex foo.txt
   try {
       FileWriter writer = new FileWriter(file);
       writer.write(textToWrite);
       writeSuccess = true;
       writer.close();
   } catch (IOException e) {
       e.printStackTrace();
   }
return writeSuccess;
}
    
}
