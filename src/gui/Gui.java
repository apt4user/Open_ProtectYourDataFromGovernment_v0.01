/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Gui extends Frame implements ActionListener{

 
 private JTextField servPort;
 private Button startServerButton;
 Label label, labelPubK;
    JTextField tfName;
    JTextArea textfeld;
    JButton generate;
    
  
     //Menüleiste
    JMenuBar menueLeiste;
    //Elemente der Menüleiste
    JMenu datei;
     // Datei
     JMenuItem encrypt, decrypt;
     



 
 public Gui() {
  JFrame RSAJFrame = new JFrame();
        RSAJFrame.setTitle("Open_ProtectYourDataFromGovernment v0.01");
        RSAJFrame.addWindowListener(new TestWindowListener());
        RSAJFrame.setSize(400, 250);
        RSAJFrame.setResizable(false);
        JPanel panel = new JPanel();
        
        // Textfeld wird erstellt
       
        tfName = new JTextField(15);
    
        // Textfeld wird hinzugefügt
        panel.add(tfName);
        JLabel label = new JLabel("Name des Gesprächspartners");
        panel.add(label);
        
         //5-zeiliges und 20-spaltiges Textfeld 
          this.textfeld = new JTextArea("In diesem Feld wird nach Betätigung des Buttons der Pfad zum Ordner gesetzt, in dem alles nötige gespeichert wird",5, 20);
       
 
        //Text für das Textfeld wird gesetzt
        
        //Zeilenumbruch wird eingeschaltet
        textfeld.setLineWrap(true);
 
        //Zeilenumbrüche erfolgen nur nach ganzen Wörtern
        textfeld.setWrapStyleWord(true);
 
        //Ein JScrollPane, der das Textfeld beinhaltet, wird erzeugt
        JScrollPane scrollpane = new JScrollPane(textfeld);       
 
        //Scrollpane wird unserem Panel hinzugefügt
        panel.add(scrollpane);
 
      
 
        JLabel labelPubK = new JLabel("Pfad zum Ordner");
        panel.add(labelPubK);
 
                
        generate = new JButton("Gesprächspartner anlegen");
        panel.add(generate);
        generate.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  
                  GenerateRSAKeysForUser grkfu = new GenerateRSAKeysForUser();
                  grkfu.createDirectory(tfName.getText());
                  grkfu.initializeRSAKeys();
                  grkfu.writeOwnPublicKey(tfName.getText());
                  grkfu.writeOwnPrivateKey(tfName.getText());
                  textfeld.setText("Der Pfad zum Ordner lautet: "+grkfu.getPath()+"\nDort findest du alles was du für einen Plausch brauchst.");
                  
              }
              
              
          });
        
        
        
         //MenüBar hinzufügen
          menueLeiste = new JMenuBar();
          
          // Menüelemente erzeugen
          datei = new JMenu("Datei");

          // Untermenüelemente erzeugen
          encrypt = new JMenuItem("Eine Nachricht verschlüsseln");
         encrypt.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  
                 new GuiEncryptMessage();
                  
              }
          });         
          decrypt = new JMenuItem("Eine Nachricht entschlüsseln");
           decrypt.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
                  
                  new GuiDecryptMessage();
                  
              }
          });
          // Menüelemente hinzufügen
          menueLeiste.add(datei);
          
           // Untermenüelemente hinzufügen
          datei.add(encrypt);
          datei.add(decrypt);
          RSAJFrame.add(menueLeiste, BorderLayout.NORTH);
        
      
        RSAJFrame.add(panel);
        RSAJFrame.setVisible(true);
 }


 
  public void actionPerformed(ActionEvent e){



 }
 
 
 public class GuiEncryptMessage extends Frame implements ActionListener  
{
    
    
    Label label, labelName, labelPubK, labelIV, labelSecrK;
     JTextField tfName;
    JTextArea textfeld;
    JButton encryptMessage, choosePublicKey;
    String message;
  
     //Menüleiste
    JMenuBar menueLeiste;
    //Elemente der Menüleiste
    JMenu datei;
     // Datei
     JMenuItem encrypt, decrypt;
     //FileExplorer
     JFileChooser chooser;
     //File für InputStream
     File file;
     
     GuiEncryptMessage(){  
         
          JFrame encryptJFrame = new JFrame();
        encryptJFrame.setTitle("CryptoMessenger");
        encryptJFrame.addWindowListener(new TestWindowListener());
        encryptJFrame.setSize(400, 280);
        encryptJFrame.setResizable(false);
        JPanel panel = new JPanel();
        
           
        tfName = new JTextField(15);
         panel.add(tfName);
        JLabel label = new JLabel("Name des Gesprächspartners");
        panel.add(label);
        
        // Textfeld wird Panel hinzugefügt
        
        choosePublicKey = new JButton("PublicKey des Gesprächspartners");
        panel.add(choosePublicKey);
        choosePublicKey.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                    chooser = new JFileChooser();
                    JFrame filePath = new JFrame("Dateiauswahl");
                      int returnVal = chooser.showOpenDialog(chooser);

               if (returnVal == JFileChooser.APPROVE_OPTION) {
                  file = chooser.getSelectedFile();
        
                        
               } else {
                        System.out.println("Es wurde keine Datei ausgewählt");
                     }

              }
          });
        
                
        
        
        //5-zeiliges und 20-spaltiges Textfeld wird erzeugt
          this.textfeld = new JTextArea(5, 20);
       
         
        //Text für das Textfeld wird gesetzt
        
        //Zeilenumbruch wird eingeschaltet
        textfeld.setLineWrap(true);
 
        //Zeilenumbrüche erfolgen nur nach ganzen Wörtern
        textfeld.setWrapStyleWord(true);
 
        //Ein JScrollPane, der das Textfeld beinhaltet, wird erzeugt
        JScrollPane scrollpane = new JScrollPane(textfeld);       
 
        //Scrollpane wird unserem Panel hinzugefügt
        panel.add(scrollpane);
 
      
 
        JLabel labelPubK = new JLabel("Meine Nachricht");
        panel.add(labelPubK);
 
                
        encryptMessage = new JButton("Nachricht verschlüsseln!");
        panel.add(encryptMessage);
        
         encryptMessage.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  System.out.println(chooser.getSelectedFile().getAbsolutePath());
                          
               
                  
                  EncryptMessage em = new EncryptMessage();

                  em.initializeAlgorithm(file, tfName.getText(), textfeld.getText());

                  
              }
          });
        
         //MenüBar hinzufügen
          menueLeiste = new JMenuBar();
          
          // Menüelemente erzeugen
          datei = new JMenu("Datei");

          // Untermenüelemente erzeugen
          encrypt = new JMenuItem("Eine Nachricht verschlüsseln");
           encrypt.addActionListener(this);
           
            decrypt = new JMenuItem("Eine Nachricht entschlüsseln");
           decrypt.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  
                  new GuiDecryptMessage();
                  
              }
          });
             // Menüelemente hinzufügen
          menueLeiste.add(datei);
          
           // Untermenüelemente hinzufügen
          datei.add(encrypt);
           datei.add(decrypt);
           encryptJFrame.add(menueLeiste, BorderLayout.NORTH);
      
        encryptJFrame.add(panel);
        encryptJFrame.setVisible(true);
        
        
     }  
     
     
     @Override
        public void actionPerformed(ActionEvent e) {

            
            
       }
     
           class TestWindowListener extends WindowAdapter {
        TestWindowListener() {}
        public void windowClosing(WindowEvent e) {
            e.getWindow().dispose();
            System.exit(0);
        }
    }
        
        

}  

           
           
           
           
           
            public class GuiDecryptMessage extends Frame implements ActionListener  
{
    
    
    Label  lableMessage ;
    JTextField tfPrivateKey, tfIV, tfSecrK;
    JTextArea textfeld;
    JButton decryptMessage, choosePrivateKey, chooseIV, chooseSecretKey, chooseMessage;
    String message;
  
     //Menüleiste
    JMenuBar menueLeiste;
    //Elemente der Menüleiste
    JMenu datei;
     // Datei
     JMenuItem encrypt, decrypt;
      //FileExplorer
     JFileChooser chooseFile;
     //File für InputStream
     File filePrivateKey, fileIV, fileSecretKey, fileMessage ;
    
     
     GuiDecryptMessage(){  
         
        JFrame decryptJFrame = new JFrame();
        decryptJFrame.setTitle("CryptoMessenger");
        decryptJFrame.addWindowListener(new TestWindowListener());
        decryptJFrame.setSize(400, 310);
        decryptJFrame.setResizable(false);
        JPanel panel = new JPanel();
 
      
         choosePrivateKey = new JButton("Mein PrivateKey");
        panel.add(choosePrivateKey);
        choosePrivateKey.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                    chooseFile = new JFileChooser();
                    JFrame filePath = new JFrame("Dateiauswahl");
                      int returnVal = chooseFile.showOpenDialog(chooseFile);

               if (returnVal == JFileChooser.APPROVE_OPTION) {
                  filePrivateKey = chooseFile.getSelectedFile();
        
                        
               } else {
                        System.out.println("Es wurde keine Datei ausgewählt");
                     }
                    

              }
          });
       
         
         chooseIV = new JButton("IV des Gesprächspartners");
        panel.add(chooseIV);
        chooseIV.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                    chooseFile = new JFileChooser();
                    JFrame filePath = new JFrame("Dateiauswahl");
                      int returnVal = chooseFile.showOpenDialog(chooseFile);

               if (returnVal == JFileChooser.APPROVE_OPTION) {
                  fileIV = chooseFile.getSelectedFile();
        
                        
               } else {
                        System.out.println("Es wurde keine Datei ausgewählt");
                     }
                    

              }
          });
       
        
       
         chooseSecretKey = new JButton("SecretKey des Gesprächspartners");
        panel.add(chooseSecretKey);
        chooseSecretKey.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                    chooseFile = new JFileChooser();
                    JFrame filePath = new JFrame("Dateiauswahl");
                      int returnVal = chooseFile.showOpenDialog(chooseFile);

               if (returnVal == JFileChooser.APPROVE_OPTION) {
                  fileSecretKey = chooseFile.getSelectedFile();
        
                        
               } else {
                        System.out.println("Es wurde keine Datei ausgewählt");
                     }

              }
          });
         
        
         chooseMessage = new JButton("Nachricht des Gesprächspartners");
        panel.add(chooseMessage);
        chooseMessage.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                    chooseFile = new JFileChooser();
                    JFrame filePath = new JFrame("Dateiauswahl");
                      int returnVal = chooseFile.showOpenDialog(chooseFile);

               if (returnVal == JFileChooser.APPROVE_OPTION) {
                  fileMessage = chooseFile.getSelectedFile();
        
                        
               } else {
                        System.out.println("Es wurde keine Datei ausgewählt");
                     }

              }
          });
        
        
       //5-zeiliges und 20-spaltiges Textfeld wird erzeugt
          this.textfeld = new JTextArea("Nach Betätigung des \"Nachricht entschlüsseln\"- Buttons, wird in diesem Feld die entschlüsselte Nachricht stehen",5, 20);
       
         
        //Text für das Textfeld wird gesetzt
        
        //Zeilenumbruch wird eingeschaltet
        textfeld.setLineWrap(true);
 
        //Zeilenumbrüche erfolgen nur nach ganzen Wörtern
        textfeld.setWrapStyleWord(true);
 
        //Ein JScrollPane, der das Textfeld beinhaltet, wird erzeugt
        JScrollPane scrollpane = new JScrollPane(textfeld);       
 
        //Scrollpane wird unserem Panel hinzugefügt
        panel.add(scrollpane);
 
      
 
        JLabel lableMessage = new JLabel("Entschlüsselte Nachricht");
        panel.add(lableMessage);
 
  
                
        decryptMessage = new JButton("Nachricht entschlüsseln!");
        panel.add(decryptMessage);
        
         decryptMessage.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                                   
                  DecryptMessage dm = new DecryptMessage();
                  dm.initializeAlgorithm(filePrivateKey, fileIV, fileSecretKey, fileMessage);
                  textfeld.setText(dm.getDecryptedMessagesStr());
                 
                  
                  
              }
          });
        
         //MenüBar hinzufügen
          menueLeiste = new JMenuBar();
          
          // Menüelemente erzeugen
          datei = new JMenu("Datei");

          // Untermenüelemente erzeugen
          encrypt = new JMenuItem("Eine Nachricht verschlüsseln");
           encrypt.addActionListener(new ActionListener() {
              @Override
              public void actionPerformed(ActionEvent e) {
                  
                  new GuiEncryptMessage();
                  
              }
          });
           
            decrypt = new JMenuItem("Eine Nachricht entschlüsseln");
           decrypt.addActionListener(this);
             // Menüelemente hinzufügen
          menueLeiste.add(datei);
          
           // Untermenüelemente hinzufügen
          datei.add(encrypt);
           datei.add(decrypt);
           decryptJFrame.add(menueLeiste, BorderLayout.NORTH);
      
        decryptJFrame.add(panel);
        decryptJFrame.setVisible(true);
        
        
     }  
     
     
        public void actionPerformed(ActionEvent e) {

            
            
       }
     
           class TestWindowListener extends WindowAdapter {
        TestWindowListener() {}
        public void windowClosing(WindowEvent e) {
            e.getWindow().dispose();
            System.exit(0);
        }
    }
        
}        
           
 
class TestWindowListener extends WindowAdapter {

    TestWindowListener() {}

 public void windowClosing(WindowEvent e) {
 e.getWindow().dispose();
 System.exit(0);
 }
 }
  
 
  public static void main(String[] args){
 new Gui();
 }
 
}
