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
public class InviteGroup extends javax.swing.JFrame {

    /**
     * Creates new form InviteGroup
     */
    private Statement st;
    private String usuario = "", invitado = "";
    private ResultSet rs = null;
    private Connection conexion = null;

    public InviteGroup(String usuario, String invitado, Connection conexion) {
        initComponents();

        this.usuario = usuario;
        this.invitado = invitado;
        this.conexion = conexion;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        invitar = new javax.swing.JButton();
        actualizarL = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Invitar amigo a grupo");

        jScrollPane1.setViewportView(jList1);

        invitar.setText("Invitar");
        invitar.setEnabled(false);
        invitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invitarActionPerformed(evt);
            }
        });

        actualizarL.setText("Actualizar");
        actualizarL.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarLActionPerformed(evt);
            }
        });

        jLabel1.setText("Agregar amigo a un grupo");

        jLabel2.setText("-");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(invitar)
                                    .addComponent(actualizarL)))
                            .addComponent(jLabel1)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(actualizarL)
                        .addGap(11, 11, 11)
                        .addComponent(invitar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jLabel2))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void invitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_invitarActionPerformed
        String nick = invitado;
        int index = jList1.getSelectedIndex();
        if (index != -1) {
            Object o = jList1.getModel().getElementAt(index);
            String grupo = o.toString();

            String query = "SELECT * FROM webchat_users WHERE name = '" + nick + "'";
            System.out.println(nick);
            boolean yaUsado = false;
            try {
                rs = st.executeQuery(query);
            } catch (SQLException ex) {
                Logger.getLogger(AddFriend.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (!rs.next()) {
                    jLabel2.setText("No se encuentra el usuario");

                } else {
                    boolean yaAmigo = false;

                    String grupos = rs.getString("groups");
                    String gruposrq = rs.getString("groupsrq");
                    if (grupos != null && !grupos.equals("")) {
                        String[] arrayGrupos = grupos.split(", ");
                        for (int i = 0; i < arrayGrupos.length; i++) {
                            if (arrayGrupos[i].equals(grupo)) {
                                yaAmigo = true;
                                break;
                            }
                        }
                    }

                    if (yaAmigo) {
                        jLabel2.setText("Ese contacto ya esta en ese grupo");
                    } else {
                        jLabel2.setText("Ese contacto no esta en ese grupo");
                        boolean yaInvitado = false;

                        if (gruposrq != null && !gruposrq.equals("")) {
                            String[] arrayGruposrq = gruposrq.split(", ");
                            for (int i = 0; i < arrayGruposrq.length; i++) {
                                if (arrayGruposrq[i].equals(grupo)) {
                                    yaInvitado = true;
                                    break;
                                }
                            }
                        }
                        if (yaInvitado) {
                            jLabel2.setText("Ese contacto ya esta invitado a ese grupo");
                        } else {
                            if (gruposrq != null && !gruposrq.equals("")) {
                                gruposrq += ", " + grupo;
                            } else {
                                gruposrq = grupo;
                            }
                            st.executeUpdate("UPDATE webchat_users SET groupsrq='" + gruposrq + "' WHERE name='" + nick + "'");
                            jLabel2.setText("Se ha invitado con exito al grupo");
                        }

                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_invitarActionPerformed
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
                String amigos = rs2.getString("groups");

                if (amigos != null && !amigos.equals("")) {
                    String[] arrayAmigos = amigos.split(", ");
                    invitar.setEnabled(true);
                    jList1.setListData(arrayAmigos);
                } else // print the results
                {
                    String[] nadita = {"No estas en ningun grupo"};
                    jList1.setListData(nadita);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void actualizarLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarLActionPerformed
       refrescarLista();

    }//GEN-LAST:event_actualizarLActionPerformed

    @Override
    public void dispose() {
        try {
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(InviteGroup.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.dispose(); //To change body of generated methods, choose Tools | Templates.
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actualizarL;
    private javax.swing.JButton invitar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
