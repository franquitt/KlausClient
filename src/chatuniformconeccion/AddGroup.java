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
 * @author franco
 */
public class AddGroup extends javax.swing.JFrame {

    /**
     * Creates new form AddFriend
     */
    private Statement st;
    private String usuario = "";
    private ResultSet rs = null;
    private Connection conexion = null;

    public AddGroup(String usuario, Connection conexion) {

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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNick = new javax.swing.JTextField();
        btnCrear = new javax.swing.JButton();
        lblResultado = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Crear Grupo");

        jLabel1.setText("Ingrese el nombre:");

        txtNick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNickActionPerformed(evt);
            }
        });

        btnCrear.setText("Crear");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        lblResultado.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        lblResultado.setText("Apriete para crear");

        jLabel3.setFont(new java.awt.Font("Ubuntu", 0, 17)); // NOI18N
        jLabel3.setText("Crear grupo de Chat");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCrear))
                    .addComponent(lblResultado)
                    .addComponent(jLabel3)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCrear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblResultado)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNickActionPerformed
                String nick = txtNick.getText();

        if (nick.indexOf(",") != -1) {
            lblResultado.setText("El nombre del grupo no puede tener comas");

        } else {
            String query = "SELECT * FROM webchat_groups WHERE name = '" + nick + "'";
            boolean yaUsado = false;
            try {
                rs = st.executeQuery(query);
            } catch (SQLException ex) {
                Logger.getLogger(AddFriend.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (rs.next()) {
                    lblResultado.setText("Ya existe un grupo con ese nombre");

                } else {
                    query = "INSERT INTO webchat_groups (creador, name, friends) VALUES('" + usuario + "', '" + nick + "', '" + usuario + "')";

                    try {
                        st.executeUpdate(query);
                        query = "SELECT * FROM webchat_users WHERE name = '" + usuario + "'";
                        rs = st.executeQuery(query);
                        rs.next();
                        String grupos = rs.getString("groups");
                        if (grupos == null || grupos.equals("")) {
                            grupos = nick;
                        } else {
                            grupos += ", " + nick;
                        }
                        st.executeUpdate("UPDATE webchat_users SET groups='" + grupos + "' WHERE name='" + usuario + "'");
                        lblResultado.setText("Se ha creado el grupo");
                    } catch (SQLException ex) {
                        Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
                        lblResultado.setText("No se pudo crear");
                    }
                }
               
            } catch (SQLException ex) {
                Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_txtNickActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        String nick = txtNick.getText();

        if (nick.indexOf(",") != -1) {
            lblResultado.setText("El nombre del grupo no puede tener comas");

        } else {
            String query = "SELECT * FROM webchat_groups WHERE name = '" + nick + "'";
            boolean yaUsado = false;
            try {
                rs = st.executeQuery(query);
            } catch (SQLException ex) {
                Logger.getLogger(AddFriend.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (rs.next()) {
                    lblResultado.setText("Ya existe un grupo con ese nombre");

                } else {
                    query = "INSERT INTO webchat_groups (creador, name, friends) VALUES('" + usuario + "', '" + nick + "', '" + usuario + "')";

                    try {
                        st.executeUpdate(query);
                        query = "SELECT * FROM webchat_users WHERE name = '" + usuario + "'";
                        rs = st.executeQuery(query);
                        rs.next();
                        String grupos = rs.getString("groups");
                        if (grupos == null || grupos.equals("")) {
                            grupos = nick;
                        } else {
                            grupos += ", " + nick;
                        }
                        st.executeUpdate("UPDATE webchat_users SET groups='" + grupos + "' WHERE name='" + usuario + "'");
                        lblResultado.setText("Se ha creado el grupo");
                    } catch (SQLException ex) {
                        Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
                        lblResultado.setText("No se pudo crear");
                    }
                }
               
            } catch (SQLException ex) {
                Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_btnCrearActionPerformed

    @Override
    public void dispose() {
        try {
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddGroup.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCrear;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblResultado;
    private javax.swing.JTextField txtNick;
    // End of variables declaration//GEN-END:variables
}
