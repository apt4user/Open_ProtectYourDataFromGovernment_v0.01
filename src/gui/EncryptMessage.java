
package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class EncryptMessage {
    
    
    
    public void initializeAlgorithm(File file, String name, String message){
        try{
           
        Cipher cipherRSA = Cipher.getInstance("RSA/ECB/PKCS1Padding","SunJCE");
        Cipher cipherAES = Cipher.getInstance("AES/CTR/NoPadding", "SunJCE");
        
        //InputStream für PublicKey definieren
        InputStream is = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(is);
        PublicKey senderPubKey =  (PublicKey) ois.readObject();
        
           
        
        //IV erstellen
        IvParameterSpec seriv = new IvParameterSpec(new SecureRandom().generateSeed(16));
        byte[] ivInBytes = seriv.getIV();
           
            System.out.println("UNVERSCHLÜSSELTER IV");
            for(int i =0; i<ivInBytes.length; i++){
                System.out.println(ivInBytes[i]);
            }
            
        //SecretKey erstellen
         KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(new SecureRandom());
            SecretKey secrKey = kg.generateKey();
            byte [] encodedKey = Base64.getEncoder().encode(secrKey.getEncoded());  
            SecretKeySpec sks = new SecretKeySpec(encodedKey ,"AES");
            System.out.println("MIT BASE64 UNVERSCHLÜSSELTER AES");
            String str = new String(encodedKey);
            System.out.println(str);
           for(int i = 0; i< encodedKey.length; i++){
               System.out.println(encodedKey[i]);
           }
         
         
         //IV mit Decrypters RSAPublicKey verschlüsseln
         cipherRSA.init(Cipher.ENCRYPT_MODE,senderPubKey);
         //byte [] encryptedIV = cipherRSA.doFinal(Base64.getEncoder().encode(ivInBytes));
          byte [] encryptedIV = cipherRSA.doFinal(ivInBytes);
        
            System.out.println("VERSCHLÜSSELTER IV");
            for(int i = 0; i< encryptedIV.length; i++){
                System.out.println(encryptedIV[i]);
            }
          
             String encryptedIVString = new String(encryptedIV);
            
            
            //IV in den OutputStream schreiben
            OutputStream osIV = new FileOutputStream(name+"\\IV fuer "+name+".pem");

            osIV.write(encryptedIV);
            osIV.flush();
            osIV.close();
           
            
         //SecretKey mit Decrypters RSAPublicKey verschlüsseln
            System.out.println("");
         
            
            
         byte[] encryptedSecretKey = cipherRSA.doFinal(Base64.getEncoder().encode(secrKey.getEncoded()));
            System.out.println("VERSCHLÜSSELTER AES");
            
            for(int i =0; i<encryptedSecretKey.length; i++ ){
                System.out.println(encryptedSecretKey[i]);
            }
         
            //SecretKey in den OutputStream schreiben
            OutputStream osSec = new FileOutputStream(name+"\\SecretKey fuer "+name+".pem");
            
            osSec.write(encryptedSecretKey);
            //oosSec.writeObject(encryptedSecretKeyStr);
           
            osSec.flush();
            osSec.close();
           
            
            //Nachricht Verschlüsseln
            
            OutputStream mesos = new FileOutputStream(name+"\\Nachricht fuer "+name+".pem");
            System.out.println(" NACHRICHT vor verschlüsselung:");
            byte[] b = message.getBytes();
            
            for(int i = 0; i < b.length; i++){
                System.out.println(b[i]);
            }
            
             
            cipherAES.init(Cipher.ENCRYPT_MODE, sks, seriv);
            byte[] encryptedMessage = cipherAES.doFinal(message.getBytes());
            String str2 = new String(encryptedMessage);
            System.out.println("NACHRICHT verschlüsselt");
            for(int i = 0; i < encryptedMessage.length; i++){
                System.out.println(encryptedMessage[i]);
            }
            
            System.out.println(str2);
            mesos.write(encryptedMessage);
            mesos.flush();
            mesos.close();
            
           
            is.close();
            
        
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(EncryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(EncryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(EncryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(EncryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(EncryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EncryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EncryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EncryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(EncryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(EncryptMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}
