package chatuniformconeccion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author user2
 */
public class ConfirmFriend extends javax.swing.JFrame {

    /**
     * Creates new form ConfirmFriend
     */
    private Statement st;
    private String usuario = "";
    private ResultSet rs = null;
    private Connection conexion = null;

    public ConfirmFriend(String usuario, Connection conexion) {
        initComponents();
        this.conexion = conexion;
        this.usuario = usuario;
        try {
            st = conexion.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(IconsForm.class.getName()).log(Level.SEVERE, null, ex);
        }





    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void refrescarLista() {
        String query = "SELECT * FROM webchat_users WHERE name = '" + usuario + "'";
        ResultSet rs2 = null;
        try {
            rs2 = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (rs2.next()) {



                String amigos = rs2.getString("friendsrqr");

                
                if (amigos != null && !amigos.equals("")) {
                    String[] arrayAmigos = amigos.split(", ");
                    btnConfirmar.setEnabled(true);
                    listaSolicitudes.setListData(arrayAmigos);
                } else // print the results
                {
                    String[] nadita = {"No tienes solicitudes nuevas"};
                    listaSolicitudes.setListData(nadita);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        listaSolicitudes = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Confirmar solicitud de amigo");

        jScrollPane1.setViewportView(listaSolicitudes);

        jLabel1.setText("Confirmar solicitud de amigo");

        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });

        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnConfirmar)
                            .addComponent(btnActualizar)))
                    .addComponent(jLabel1)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnActualizar)
                        .addGap(11, 11, 11)
                        .addComponent(btnConfirmar)
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        refrescarLista();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        int index = listaSolicitudes.getSelectedIndex();
        Object o = listaSolicitudes.getModel().getElementAt(index);
        String confirmado = o.toString();
        //*****************************   request recibidas
        String query = "SELECT * FROM webchat_users WHERE name = '" + usuario + "'";

        try {
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(AddFriend.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (rs.next()) {

                String amigosrqr = rs.getString("friendsrqr");

                if (amigosrqr != null && !amigosrqr.equals("")) {
                    String[] arrayAmigos = amigosrqr.split(", ");
                    String amigosNuevos = "";
                    
                    for (int i = 0; i < arrayAmigos.length; i++) {
                       
                        if (!arrayAmigos[i].equals(confirmado)) {
                            if (amigosNuevos != null && !amigosNuevos.equals("")) {
                                amigosNuevos += ", " + arrayAmigos[i];
                            } else {
                                amigosNuevos = arrayAmigos[i];
                            }  
                        }
                    }
                    String amigos = rs.getString("friends");
                    if (amigos != null && !amigos.equals("")) {
                        System.out.println("anterior " + amigos);
                        amigos += ", " + confirmado;
                    } else {
                        amigos = confirmado;
                    }
                    System.out.println(amigos);
                    System.out.println("actualizado amigos con:\n" + amigosNuevos + "\n");
                    st.executeUpdate("UPDATE webchat_users SET friendsrqr='" + amigosNuevos + "', friends='" + amigos + "' WHERE name='" + usuario + "'");
                    refrescarLista();
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
        }
        //*****************************   request enviadas
        query = "SELECT * FROM webchat_users WHERE name = '" + confirmado + "'";

        try {
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(AddFriend.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (rs.next()) {

                String amigosrq = rs.getString("friendsrq");

                if (amigosrq != null && !amigosrq.equals("")) {
                    String[] arrayAmigos = amigosrq.split(", ");
                    String amigosNuevos = "";
                    for (int i = 0; i < arrayAmigos.length; i++) {
                        //System.out.println("\n"+arrayAmigos[i]+" nick "+nick+"\n");
                        if (!arrayAmigos[i].equals(usuario)) {
                            if (amigosNuevos != null && !amigosNuevos.equals("")) {
                                amigosNuevos += ", " + arrayAmigos[i];
                            } else {
                                amigosNuevos = arrayAmigos[i];
                            }
                            
                        }
                    }

                    String amigos = rs.getString("friends");
                    if (amigos != null && !amigos.equals("")) {
                        System.out.println("anterior " + amigos);
                        amigos += ", " + usuario;
                    } else {
                        amigos = usuario;
                    }
                    System.out.println(amigos);
                    System.out.println("actualizado amigos con:\n" + amigosNuevos + "\n");
                    st.executeUpdate("UPDATE webchat_users SET friendsrq='" + amigosNuevos + "', friends='" + amigos + "' WHERE name='" + confirmado + "'");
                    refrescarLista();

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_btnConfirmarActionPerformed

    @Override
    public void dispose() {
        try {
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConfirmFriend.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList listaSolicitudes;
    // End of variables declaration//GEN-END:variables
}
