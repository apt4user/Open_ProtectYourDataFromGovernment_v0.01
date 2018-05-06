/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author waynleo
 */
public class DecryptMessage {
     
    private String decryptedMessagesStr;

    public String getDecryptedMessagesStr() {
        return decryptedMessagesStr;
    }
    

    public void initializeAlgorithm(File ownPrivateKey, File encrIV, File encrSecretKey, File encrMessage){
        try {
            
            Cipher cipherRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding", "SunJCE");
            Cipher cipherAES = Cipher.getInstance("AES/CTR/NoPadding", "SunJCE");
            //Eigenen PrivateKey einholen
            InputStream is = new FileInputStream(ownPrivateKey);
             ObjectInputStream ois = new ObjectInputStream(is);
            PrivateKey privK =  (PrivateKey) ois.readObject();
            ois.close();
            is.close();
            System.out.println("wurde eingeholt");
            InputStream isIV = new FileInputStream(encrIV);
            //IV einhoeln
             System.out.println("VERSCHLÜSSELTER IV");
            byte [] encryptedIV = new byte[128];
             for(int i = 0; i < encryptedIV.length; i++ ){
              encryptedIV[i] = (byte)isIV.read();
                 System.out.println(encryptedIV[i]);
            }
             
           isIV.close();
             //IV decrypten
            cipherRSA.init(Cipher.DECRYPT_MODE,privK);
               
              byte[] decryptedIV = cipherRSA.doFinal(encryptedIV);
             System.out.println("ENTSCHLÜSSELTER IV");
             
             for(int i = 0; i < decryptedIV.length; i++){
                 System.out.println(decryptedIV[i]);
             }
             
              IvParameterSpec iv = new IvParameterSpec(decryptedIV);
              
             
             //SecretKey einholen
             
             InputStream isSec = new FileInputStream(encrSecretKey);
            
            
            System.out.println("VERSCHLÜSSELTER AES");
             byte[] encryptedAESKey = new byte[128]; 

                for(int i = 0; i < encryptedAESKey.length; i++ ){
                    
                    encryptedAESKey[i] =  (byte)isSec.read();
                    System.out.println(encryptedAESKey[i]);
                 }
                
                isSec.close();
                
                byte[] decryptedAESKey = cipherRSA.doFinal(encryptedAESKey);
             
                System.out.println("ENTSCHLÜSSELTER AES");
                
                for(int i = 0; i<decryptedAESKey.length; i++){
                    System.out.println(decryptedAESKey[i]);
                }
                
                System.out.println("ENTSCHLÜSSELTER AES MIT BASE64");
                
                byte[] decodedKey = Base64.getDecoder().decode(decryptedAESKey);
              
                for(int i = 0; i <decodedKey.length;i++){
                    System.out.println(decodedKey[i]);
                }
                
                
                //SecretKey erstellen
                 SecretKeySpec sks = new SecretKeySpec(decryptedAESKey ,"AES");
                 InputStream isMessage = new FileInputStream(encrMessage);
                 
                 
                 cipherAES.init(Cipher.DECRYPT_MODE,sks , iv );
                 
       List<Byte> encryptedMessageFromClient = new ArrayList<Byte>();
        
      
       int k = 0;
       int o = 0;
       while(o != -1){
           encryptedMessageFromClient.add((byte) isMessage.read());
           o = encryptedMessageFromClient.get(k);
           k++;
       }
          
           
          
            
         

                 
           int arrayValue = encryptedMessageFromClient.size();
          
          byte[] messageEncryp = new byte[arrayValue -1];
            System.out.println("VERSCHLÜSSELTE NACHRICHT");
          for(int i = 0; i < messageEncryp.length; i++){
              
              messageEncryp[i] = (byte)encryptedMessageFromClient.get(i);
              System.out.println(messageEncryp[i]);
          }
     
                byte[] decryptedMessage = cipherAES.doFinal(messageEncryp);
                System.out.println("entschlüsselt: ");
                
                for(int i = 0; i <decryptedMessage.length; i++ ){
                    System.out.println(decryptedMessage[i]);
                }
                
                decryptedMessagesStr = new String(decryptedMessage);
               System.out.println(decryptedMessagesStr);
               
              
                       
               
            is.close();
            isMessage.close();

            
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
             Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
         } catch (FileNotFoundException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(DecryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    
  
}
