package chatuniformconeccion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.*;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author franco
 */
public class ThreadListaGroups extends Thread {

    private Statement st;
    private javax.swing.JTextArea area;
    private ResultSet rs = null;
    private String emisor = "", receptor = "", pass;
    private String toSend = "";
    private String[] toSend2;
    private Connection conexion = null;
    private javax.swing.JTextPane panel;
    String directorioTrabajo = System.getProperty("user.dir");

    public ThreadListaGroups(String emisor, String receptor/*
             * , JTextArea area
             */, JTextPane panel, Connection conexion, String pass) {
        this.emisor = emisor;
        this.receptor = receptor;

        //this.area = area;
        this.panel = panel;
        this.pass=pass;
        this.conexion = conexion;
        try {
            st = conexion.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IconsForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    @Override
    public void run() {




        String[] idUsadas = new String[100000];
        StyledDocument doc = panel.getStyledDocument();
        SimpleAttributeSet keyWord = new SimpleAttributeSet();
        StyleConstants.setBold(keyWord, true);
        StyleConstants.setFontSize(keyWord, 16);
        int id;
        while (true) {
            try {
                
                rs = st.executeQuery("SELECT * FROM groupchat_lines ORDER BY id");
            } catch (SQLException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                try {
                    while (rs.next())// Mientras halla resultados desde la base de datos
                    {
                        boolean usado = false;
                        String autorR = "" + rs.getObject("author");
                        id = Integer.parseInt("" + rs.getObject("id"));
                        String receptorR = "" + rs.getObject("grouprec");
                        String texto = "" + rs.getObject("texte");
                        texto = decrypt(texto);
                        String fecha = "" + rs.getObject("ts");
                                               
                        for (int subI = 0; subI < 1000; subI++) {
                            if (idUsadas[subI] != null) {
                                int acomparar = Integer.parseInt(idUsadas[subI]);
                               
                                if (acomparar == id) {
                                    usado = true;                                    
                                    break;
                                }
                            }
                        }
                        if (usado == false) {

                            if (receptorR.equals(receptor)) {
                                String textito1 = "\n" + autorR + ":  ";
                                String textito2 =  fecha + "\n";
                                try {
                                    doc.insertString(doc.getLength(), textito1, keyWord);
                                    doc.insertString(doc.getLength(), textito2, null);
                                    // Caritas ****************************
                                    catchFace(texto, doc);

                                    panel.setCaretPosition(panel.getDocument().getLength());
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, ex.toString());

                                }
                               
                            }                          
                            for (int i2 = 0; i2 < idUsadas.length; i2++) {

                               
                                if (idUsadas[i2] == null) {
                                    idUsadas[i2] = "" + id;
                                    //System.out.println(id + " Agregado a usadas");
                                    break;
                                }

                            }
                           
                        }

                    }


                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());

                }
            } catch (NullPointerException e) {
                try {
                    rs = st.executeQuery("SELECT * FROM groupchat_lines ORDER BY id");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());

                }
                try {
                    while (rs.next())// Mientras halla resultados desde la base de datos
                    {
                        boolean usado = false;

                        String autorR = "" + rs.getObject("author");
                        id = Integer.parseInt("" + rs.getObject("id"));
                        String receptorR = "" + rs.getObject("grouprec");
                        String texto = "" + rs.getObject("texte");
                        texto = decrypt(texto);
                        String fecha = "" + rs.getObject("ts");
                        
                        for (int subI = 0; subI < 1000; subI++) {
                            if (idUsadas[subI] != null) {
                                int acomparar = Integer.parseInt(idUsadas[subI]);
                                
                                if (acomparar == id) {
                                    usado = true;
                                    
                                    break;
                                }

                            }
                        }
                        if (usado == false) {
                            if (receptorR.equals(receptor)) {
                                String textito1 = "\n" + autorR + ":  ";
                                String textito2 =  fecha + "\n";
                                try {
                                    doc.insertString(doc.getLength(), textito1, keyWord);
                                    doc.insertString(doc.getLength(), textito2, null);
                                    // Caritas ****************************
                                    catchFace(texto, doc);

                                    panel.setCaretPosition(panel.getDocument().getLength());
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, ex.toString());

                                }
                                
                            }
                        }
                       
                        for (int i2 = 0; i2 < idUsadas.length; i2++) {

                            
                            if (idUsadas[i2] == null) {
                                idUsadas[i2] = "" + id;
                              
                                break;
                            }
                        }                               
                    }
                } catch (Exception ex22) {
                    JOptionPane.showMessageDialog(null, ex22.toString());
                }
            }
        }
    }
    public String decrypt(String cadena) {
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
        s.setPassword(pass);
        String devuelve;
        try {
            devuelve = s.decrypt(cadena);
        } catch (Exception e) {
            devuelve = "Inaccesible, compruebe la contraseña de encriptación";
        }
        return devuelve;
    }

    private void catchFace(String texto, StyledDocument doc) {
        if (texto.equals("^^")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/avergonzado.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals("¯ε¯")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/besito.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":boo:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/boo.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":boom:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/boom.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":borracho:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/borracho.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":enfermo:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/enfermo.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":enojado:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/enojado.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":fuego:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/fuego.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":god:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/god.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":graffiti:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/graffiti.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals("=D")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/happy.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":lagrima:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/lagrima.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals("<3")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/love.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":malote:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/malote.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":mareado:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/mareado.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":meaw:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/meaw.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":molesto:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/molesto.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":musica:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/musica.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":ninja:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/ninja.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":nop:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/nop.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":omg:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/omg.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":oohhh:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/oohhh.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":ouch:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/ouch.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":porque:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/porque.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":relleno:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/relleno.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":ruborizado:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/ruborizado.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":serio:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/serio.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":)")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/sonrisa.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":sospechoso:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/sospechoso.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":tontito:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/tontito.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":TT:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/tt.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":vamos:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/vamos!.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":want:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/want.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":wow:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/wow.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":wut:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/wut.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals("x_x")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/x_x.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals("XD")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/xd.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":yeah:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/yeah.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals("zZz")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/zZz.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (texto.equals(":zombie:")) {
            Style style3 = doc.addStyle("StyleName", null);
            StyleConstants.setIcon(style3, new ImageIcon(directorioTrabajo + "/icons/zombie.png"));
            try {
                doc.insertString(doc.getLength(), texto, style3);


            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                doc.insertString(doc.getLength(), texto, null);
            } catch (BadLocationException ex) {
                Logger.getLogger(ThreadLista.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}