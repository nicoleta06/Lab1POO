package seriaf.poo.server.config;


import seriaf.poo.server.exceptions.MissingKeyException;

import seriaf.poo.server.exceptions.UnknownKeyException;

import seriaf.poo.server.exceptions.InvalidFormatException;

import java.io.FileInputStream;

import java.io.IOException;

import java.util.HashMap;

import java.util.Map;

import java.util.Scanner;



public class ServerConfig {

  
  private Map<String, String> mProperties;//lista
    private
  String[] mKnownProperties = {"TCP_PORT", "MAX_CLIENTS"};

 

   public ServerConfig(String filename) throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {

  
      mProperties = new HashMap<>();

    
    FileInputStream fileInputStream = new FileInputStream(filename);
  
      Scanner scanner = new Scanner(fileInputStream);

  
      while (scanner.hasNext()) {
       
     String line = scanner.nextLine().trim();
     
       //Orice linie care începe cu caracterul #, precedat sau nu de spații sau caractere "tab", se consideră a fi comentariu și nu se ia în considerare.
     
       if (line.startsWith("#") || line.isEmpty()) 
continue;
       
     }

//Liniile din fișier care nu conțin nici un caracter printabil, ci doar spații și/ sau tab-uri, nu se iau în considerare.
   
         if (!line.matches("[a-zA-Z_][a-zA-Z0-9_]*\\s*=\\s*[0-9]+")) {
   
             throw new InvalidFormatException("Linia " + line + " nu se potriveste cu formatul asteptat!");
//cream exceptia
           
 }

         
   processLine(line);
   
     }

       
 for (String property : mKnownProperties) {
 
           if (!mProperties.containsKey(property)) {
     
           throw new MissingKeyException("Cheia " + property + " nu exista in fisier.");
  
          }
  
      }
  
  }

  
  public ServerConfig() throws IOException, InvalidFormatException, UnknownKeyException, MissingKeyException {
  
      this("server.conf");
  
  }

   
 private void processLine(String line) throws UnknownKeyException {
 
       String[] words = line.split("=");
   
     String keyName = words[0].trim();
  
      checkKey(keyName);
      
  mProperties.put(keyName, words[1].trim());
   
 }

   
 private void checkKey(String keyName) throws UnknownKeyException {
  
      for (String knownKey : mKnownProperties) {
   
         if (keyName.equals(knownKey)) {
    
            return;
      
      }
     
   }

        throw new UnknownKeyException("Cheia " + keyName + " este necunoscuta.");
  
  }

public int getTcpPort() {
      
  return Integer.parseInt(mProperties.get("TCP_PORT"));
  
  }

   
 public int getMaxClients() {
   
     return Integer.parseInt(mProperties.get("MAX_CLIENTS"));
  
  }

}
