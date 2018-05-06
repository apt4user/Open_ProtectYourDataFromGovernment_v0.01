
package gui;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;



public class GenerateRSAKeysForUser {
    
    
    private  KeyPairGenerator kpg;
    private  KeyPair keyPa;
    private  PublicKey pubK;
    private  PrivateKey privK;
    private String path;
            
    
    public String getPath(){
        
        return path;
    }
    
            public void initializeRSAKeys(){
        
        try {
            //RSA Public- und PrivateKey erstellen
            kpg = KeyPairGenerator.getInstance("RSA","SunRsaSign");
            kpg.initialize(1024);
            keyPa = kpg.generateKeyPair();
            pubK = keyPa.getPublic();
            privK = keyPa.getPrivate();
           
             
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(GenerateRSAKeysForUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(GenerateRSAKeysForUser.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
            
            public void createDirectory(String name){
                
                           //File erzeugen
                File f = new File(name);
           
                f.mkdir();
                path = f.getAbsolutePath();
            }
            
            public void writeOwnPrivateKey(String name){
                OutputStream os =null;
        try {
            os = new FileOutputStream(name+"\\Mein PrivateKey.pem");
            ObjectOutputStream oos = new ObjectOutputStream(os);
          //PrivateKey in OutputStream
           oos.writeObject(privK);
           oos.flush();
            os.close();
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GenerateRSAKeysForUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenerateRSAKeysForUser.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                os.close();
            } catch (IOException ex) {
                Logger.getLogger(GenerateRSAKeysForUser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
                
                
            }
            
    
             public void writeOwnPublicKey(String name){
        
                   try{
           
          OutputStream os = new FileOutputStream(name+"\\PublicKey fuer "+name+".pem"); 
           ObjectOutputStream oos = new ObjectOutputStream(os);
           
           oos.writeObject(pubK);
           os.close();
    
    
    
    
        }catch (FileNotFoundException ex) {
            Logger.getLogger(GenerateRSAKeysForUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GenerateRSAKeysForUser.class.getName()).log(Level.SEVERE, null, ex);
        }
              
                   
    }
            
          
   

  
    
}
