package hello;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
     FileOutputStream fop = null;
  File file;
  String content = "[]";
  try {
   file = new File("projects.txt");
   fop = new FileOutputStream(file);
   // if file doesnt exists, then create it
   if (!file.exists()) {
    file.createNewFile();
   }
   // get the content in bytes
   byte[] contentInBytes = content.getBytes();
   fop.write(contentInBytes);
   fop.flush();
   fop.close();
  } catch (IOException e) {
   e.printStackTrace();
  } finally {
   try {
    if (fop != null) {
     fop.close();
    }
   } catch (IOException e) {
    e.printStackTrace();
   }
  }                         
     
        SpringApplication.run(Application.class, args);
    }
}