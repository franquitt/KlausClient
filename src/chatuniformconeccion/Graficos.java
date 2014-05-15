package chatuniformconeccion;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author franco
 */
 /*
 CREATE TABLE `enfrentamientos` (
`id` int(30) NOT NULL,
  `ataque` varchar(30) NOT NULL,
  `defensa` varchar(30) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `groupchat_lines`
--

CREATE TABLE `groupchat_lines` (
`id` int(11) NOT NULL,
  `author` varchar(40) NOT NULL,
  `grouprec` varchar(40) NOT NULL,
  `texte` text NOT NULL,
  `ts` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `movimientos`
--

CREATE TABLE `movimientos` (
`id` int(6) NOT NULL,
  `user1` varchar(40) NOT NULL,
  `user2` varchar(40) NOT NULL,
  `turno` varchar(40) NOT NULL,
  `xy` varchar(40) NOT NULL,
  `tipo` varchar(40) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
`id` int(10) NOT NULL,
  `name` varchar(40) NOT NULL,
  `estado` varchar(40) NOT NULL DEFAULT 'offline',
  `numunic` varchar(60) NOT NULL
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=77 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `estado`, `numunic`) VALUES
(74, 'Franco', 'offline', '000000000000000'),
(72, 'franco', 'offline', '353227051191734'),
(75, 'gyuu', 'online', '353861056225848'),
(76, 'fhfu\n', 'offline', '355259050673477');

-- --------------------------------------------------------

--
-- Table structure for table `webchat_groups`
--

CREATE TABLE `webchat_groups` (
`id` int(11) NOT NULL,
  `creador` varchar(40) NOT NULL,
  `name` varchar(40) NOT NULL,
  `friends` text NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

--
-- Table structure for table `webchat_lines`
--

CREATE TABLE `webchat_lines` (
`id` int(11) NOT NULL,
  `author` varchar(40) NOT NULL,
  `receptor` varchar(40) NOT NULL,
  `text` text NOT NULL,
  `ts` varchar(20) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=8 ;


--
-- Table structure for table `webchat_users`
--

CREATE TABLE `webchat_users` (
`id` int(11) NOT NULL,
  `pass` varchar(200) DEFAULT NULL,
  `conectado` varchar(10) NOT NULL DEFAULT 'NO',
  `usuario` varchar(40) DEFAULT NULL,
  `name` varchar(40) NOT NULL,
  `usuarioenc` varchar(100) NOT NULL,
  `email` varchar(30) NOT NULL,
  `validado` varchar(10) NOT NULL,
  `friends` text,
  `friendsrq` text,
  `friendsrqr` text,
  `groups` text,
  `groupsrq` text
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;


-- Indexes for dumped tables
--

--
-- Indexes for table `enfrentamientos`
--
ALTER TABLE `enfrentamientos`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `groupchat_lines`
--
ALTER TABLE `groupchat_lines`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `movimientos`
--
ALTER TABLE `movimientos`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
 ADD PRIMARY KEY (`id`), ADD UNIQUE KEY `numunic` (`numunic`);

--
-- Indexes for table `webchat_groups`
--
ALTER TABLE `webchat_groups`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `webchat_lines`
--
ALTER TABLE `webchat_lines`
 ADD PRIMARY KEY (`id`);

--
-- Indexes for table `webchat_users`
--
ALTER TABLE `webchat_users`
 ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `enfrentamientos`
--
ALTER TABLE `enfrentamientos`
MODIFY `id` int(30) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `groupchat_lines`
--
ALTER TABLE `groupchat_lines`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `movimientos`
--
ALTER TABLE `movimientos`
MODIFY `id` int(6) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
MODIFY `id` int(10) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=77;
--
-- AUTO_INCREMENT for table `webchat_groups`
--
ALTER TABLE `webchat_groups`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `webchat_lines`
--
ALTER TABLE `webchat_lines`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `webchat_users`
--
ALTER TABLE `webchat_users`
MODIFY `id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Toolkit;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class Graficos extends javax.swing.JFrame {

    /**
     * Creates new form Graficos
     */
    private Statement st;
    private int activosTot = 0;
    private Statement st2;
    private Connection conexion = null;
    private String usuario = "", password = "", amigos = "", receptor = "", grupos = "", passInd = "", passGrup = "";
    private ThreadLista threadlista = null;
    private ThreadListaGroups threadlistaG = null;
    private IconsForm iconsV = null;//new IconsForm();
    private IconsForm iconsVG = null;//new IconsForm();
    String receptorGroup = "";
/**
 * Devuelve la imagen del programa
 * @return imagen Imagen
 */
    @Override
    public Image getIconImage() {
        Image retValue = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("icons/klausfinal.png"));


        return retValue;
    }

    public Graficos() {
        initComponents();     
        menuAmigos.setEnabled(false);
        desconectarCuenta.setEnabled(false);
        menuGrupos.setEnabled(false);
        ventanaValidar.setTitle("Activar Cuenta");
        ventanaValidar.setSize(500, 210);
        jTabbedPane1.removeTabAt(2);
        jTabbedPane1.removeTabAt(1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ventanaValidar = new javax.swing.JFrame();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pass1Validar = new javax.swing.JPasswordField();
        pass2Validar = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        codeValidar = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnActivar = new javax.swing.JButton();
        lblEstadoValidacion = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        conectarCuenta = new javax.swing.JButton();
        passUsuario = new javax.swing.JPasswordField();
        txtUsuario = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaAmigos = new javax.swing.JList();
        abrirChat = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        enviar = new javax.swing.JButton();
        actualizarChat = new javax.swing.JButton();
        lblUsuario = new javax.swing.JLabel();
        abrirIconos = new javax.swing.JButton();
        aEnviarIndividual = new javax.swing.JTextField();
        invitarAlGrupo = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        encInd = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaGrupos = new javax.swing.JList();
        actualizarGrupos = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaIntegrantes = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        tPGrupo = new javax.swing.JTextPane();
        txtEnviarGrupo = new javax.swing.JTextField();
        enviarGrupo = new javax.swing.JButton();
        labelGrupoActual = new javax.swing.JLabel();
        abrirGrupo = new javax.swing.JButton();
        quitarDelGrupo = new javax.swing.JButton();
        salirGrupo = new javax.swing.JButton();
        abrirIconsG = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        encGrup = new javax.swing.JPasswordField();
        jMenuBar1 = new javax.swing.JMenuBar();
        validarCuenta = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        desconectarCuenta = new javax.swing.JMenuItem();
        menuAmigos = new javax.swing.JMenu();
        addAmigo = new javax.swing.JMenuItem();
        confirmarAmigo = new javax.swing.JMenuItem();
        menuGrupos = new javax.swing.JMenu();
        confirmarGrupo = new javax.swing.JMenuItem();
        crearGrupo = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        acercaDe = new javax.swing.JMenuItem();
        opcionCuenta = new javax.swing.JMenuItem();

        jLabel2.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        jLabel2.setText("Activar Cuenta ");

        jLabel4.setText("Contraseña");

        jLabel5.setText("Repita contraseña");

        jLabel6.setText("Código de activacion");

        btnActivar.setText("Activar");
        btnActivar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActivarActionPerformed(evt);
            }
        });

        lblEstadoValidacion.setText("Ingrese el codigo que se muestra en la pagina web");

        javax.swing.GroupLayout ventanaValidarLayout = new javax.swing.GroupLayout(ventanaValidar.getContentPane());
        ventanaValidar.getContentPane().setLayout(ventanaValidarLayout);
        ventanaValidarLayout.setHorizontalGroup(
            ventanaValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaValidarLayout.createSequentialGroup()
                .addGroup(ventanaValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ventanaValidarLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2))
                    .addGroup(ventanaValidarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ventanaValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(ventanaValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pass1Validar)
                            .addComponent(pass2Validar)
                            .addComponent(codeValidar, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(ventanaValidarLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnActivar)
                        .addGap(44, 44, 44)
                        .addComponent(lblEstadoValidacion)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ventanaValidarLayout.setVerticalGroup(
            ventanaValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ventanaValidarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(ventanaValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(pass1Validar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ventanaValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pass2Validar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ventanaValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeValidar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ventanaValidarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnActivar)
                    .addComponent(lblEstadoValidacion))
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Klaus Client");
        setIconImage(getIconImage());

        conectarCuenta.setText("Conectar");
        conectarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conectarCuentaActionPerformed(evt);
            }
        });

        passUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passUsuarioActionPerformed(evt);
            }
        });

        jLabel1.setText("Password");

        jLabel3.setText("Usuario");

        lblEstado.setFont(new java.awt.Font("Ubuntu", 0, 15)); // NOI18N
        lblEstado.setText("Conectate para empezar");

        jLabel8.setText("@MadProgrammer");

        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/medklaus.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(conectarCuenta)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel1))
                                        .addGap(18, 18, 18)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(passUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(lblEstado, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGap(31, 31, 31)))))
                .addContainerGap(368, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(conectarCuenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblEstado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 295, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Log In", new javax.swing.ImageIcon(getClass().getResource("/icons/miniklaus.png")), jPanel1); // NOI18N

        listaAmigos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(listaAmigos);

        abrirChat.setText("Abrir Chat");
        abrirChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirChatActionPerformed(evt);
            }
        });

        jTextPane1.setEditable(false);
        jScrollPane2.setViewportView(jTextPane1);

        enviar.setText("Enviar");
        enviar.setEnabled(false);
        enviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviar(evt);
            }
        });

        actualizarChat.setText("Actualizar");
        actualizarChat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarChatActionPerformed(evt);
            }
        });

        lblUsuario.setText("Usuario");

        abrirIconos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sonrisa.png"))); // NOI18N
        abrirIconos.setBorderPainted(false);
        abrirIconos.setContentAreaFilled(false);
        abrirIconos.setEnabled(false);
        abrirIconos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/happy.png"))); // NOI18N
        abrirIconos.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ouch.png"))); // NOI18N
        abrirIconos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirIconosActionPerformed(evt);
            }
        });

        aEnviarIndividual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aEnviarIndividualActionPerformed(evt);
            }
        });

        invitarAlGrupo.setText("Invitar");
        invitarAlGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                invitarAlGrupoActionPerformed(evt);
            }
        });

        jLabel7.setText("Encriptación");

        encInd.setText("clave");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(abrirChat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(actualizarChat))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(invitarAlGrupo)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 124, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addGap(18, 18, 18)
                                        .addComponent(encInd, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(aEnviarIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, 496, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(enviar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(abrirIconos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(29, 29, 29))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblUsuario)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblUsuario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(enviar)
                    .addComponent(aEnviarIndividual, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(actualizarChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(abrirChat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(abrirIconos, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(invitarAlGrupo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(22, 22, 22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(encInd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        jTabbedPane1.addTab("Chat", jPanel2);

        jLabel9.setText("Grupos");

        jScrollPane3.setViewportView(listaGrupos);

        actualizarGrupos.setText("Actualizar");
        actualizarGrupos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actualizarGruposActionPerformed(evt);
            }
        });

        jLabel10.setText("Integrantes");

        jScrollPane4.setViewportView(listaIntegrantes);

        tPGrupo.setEditable(false);
        jScrollPane5.setViewportView(tPGrupo);

        txtEnviarGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEnviarGrupoActionPerformed(evt);
            }
        });

        enviarGrupo.setText("Enviar");
        enviarGrupo.setEnabled(false);
        enviarGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarGrupoActionPerformed(evt);
            }
        });

        labelGrupoActual.setText("Actual");

        abrirGrupo.setText("Abrir");
        abrirGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirGrupoActionPerformed(evt);
            }
        });

        quitarDelGrupo.setText("Quitar");
        quitarDelGrupo.setEnabled(false);
        quitarDelGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitarDelGrupoActionPerformed(evt);
            }
        });

        salirGrupo.setText("Salir");
        salirGrupo.setEnabled(false);
        salirGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salirGrupoActionPerformed(evt);
            }
        });

        abrirIconsG.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/sonrisa.png"))); // NOI18N
        abrirIconsG.setBorderPainted(false);
        abrirIconsG.setContentAreaFilled(false);
        abrirIconsG.setEnabled(false);
        abrirIconsG.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/happy.png"))); // NOI18N
        abrirIconsG.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/ouch.png"))); // NOI18N
        abrirIconsG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                abrirIconsGActionPerformed(evt);
            }
        });

        jLabel12.setText("Encriptación");

        encGrup.setText("clave");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(actualizarGrupos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(abrirGrupo))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtEnviarGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(enviarGrupo))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(labelGrupoActual)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(encGrup, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(quitarDelGrupo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(abrirIconsG))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(salirGrupo))
                                .addGap(0, 24, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(labelGrupoActual)
                    .addComponent(jLabel12)
                    .addComponent(encGrup, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(actualizarGrupos)
                            .addComponent(txtEnviarGrupo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(enviarGrupo)
                            .addComponent(abrirGrupo)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(salirGrupo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(abrirIconsG)
                            .addComponent(quitarDelGrupo)))))
        );

        jTabbedPane1.addTab("Grupos de Chat", jPanel3);

        validarCuenta.setText("Cuenta");

        jMenuItem1.setText("validar");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        validarCuenta.add(jMenuItem1);

        desconectarCuenta.setText("Desconectar");
        desconectarCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                desconectarCuentaActionPerformed(evt);
            }
        });
        validarCuenta.add(desconectarCuenta);

        jMenuBar1.add(validarCuenta);

        menuAmigos.setText("Amigos");

        addAmigo.setText("Agregar");
        addAmigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addFriend(evt);
            }
        });
        menuAmigos.add(addAmigo);

        confirmarAmigo.setText("Confirmar");
        confirmarAmigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarAmigoActionPerformed(evt);
            }
        });
        menuAmigos.add(confirmarAmigo);

        jMenuBar1.add(menuAmigos);

        menuGrupos.setText("Grupos");

        confirmarGrupo.setText("Confirmar");
        confirmarGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmarGrupoActionPerformed(evt);
            }
        });
        menuGrupos.add(confirmarGrupo);

        crearGrupo.setText("Crear");
        crearGrupo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearGrupoActionPerformed(evt);
            }
        });
        menuGrupos.add(crearGrupo);

        jMenuBar1.add(menuGrupos);

        menuAyuda.setText("Ayuda");

        acercaDe.setText("Acerca De");
        acercaDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acercaDeActionPerformed(evt);
            }
        });
        menuAyuda.add(acercaDe);

        opcionCuenta.setText("Cuenta");
        opcionCuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCuentaActionPerformed(evt);
            }
        });
        menuAyuda.add(opcionCuenta);

        jMenuBar1.add(menuAyuda);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void conectarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conectarCuentaActionPerformed
        String pass = new String(passUsuario.getPassword());
        password = encript(pass);
        usuario = txtUsuario.getText();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://server:port/database", "user", "pass");
            st = conexion.createStatement();
            String query = "SELECT * FROM webchat_users WHERE name = '" + usuario + "'";
            ResultSet rs;
            rs = st.executeQuery(query);
            if (!rs.next()) {
                lblEstado.setText("No se encuentra el usuario");
            } else {                
                String password2 = rs.getString("pass");
                if (password.equals(password2)) {
                    lblEstado.setText("Logueado");
                    st.executeUpdate("UPDATE webchat_users SET conectado='SI' WHERE name='" + usuario + "'");
                    menuAmigos.setEnabled(true);
                    conectarCuenta.setEnabled(false);
                    desconectarCuenta.setEnabled(true);
                    menuGrupos.setEnabled(true);
                    passUsuario.setEnabled(false);
                    jTabbedPane1.removeAll();
                    jTabbedPane1.addTab("Chat", new javax.swing.ImageIcon(getClass().getResource("/icons/chat.png")), jPanel2);
                    jTabbedPane1.addTab("Grupos de Chat", new javax.swing.ImageIcon(getClass().getResource("/icons/chatG.png")), jPanel3);
                    jTabbedPane1.setSelectedIndex(0);
                    encInd.setText("clave");
                    iconsV = new IconsForm(conexion);
                    iconsVG = new IconsForm(conexion);
                    conectarCuenta.setEnabled(false);
                    refrescarListas();
                } else {
                    lblEstado.setText("contraseña incorrecta");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_conectarCuentaActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        ventanaValidar.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void btnActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarActionPerformed
        String pass1 = new String(pass1Validar.getPassword());
        String pass2 = new String(pass2Validar.getPassword());
        String usuarioenc = codeValidar.getText();
        if (!pass1.equals(pass2)) {
            lblEstadoValidacion.setText("Las contraseñas no coinciden");
        } else if ("".equals(usuarioenc)) {
            lblEstadoValidacion.setText("Rellene todos los datos");
        } else if ("".equals(pass1)) {
            lblEstadoValidacion.setText("Rellene todos los datos");
        } else {
            String passenc = encript(pass1);
            try {
                conexion = DriverManager.getConnection("jdbc:mysql://server:port/database", "user", "pass");
                try {
                    st2 = conexion.createStatement();
                    String query = "SELECT * FROM webchat_users WHERE usuarioenc='" + usuarioenc + "'";
                    ResultSet rs = null;
                    try {
                        rs = st2.executeQuery(query);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.toString());
                    }
                    try {
                        if (!rs.next()) {
                            lblEstadoValidacion.setText("Codigo incorrecto");
                        } else {
                            String validado = rs.getString("validado");
                            if (validado.equals("SI2")) {
                                lblEstadoValidacion.setText("Esta cuenta ya esta activada");
                            } else {
                                try {
                                    st2.executeUpdate("UPDATE webchat_users SET pass='" + passenc + "', validado='SI2' WHERE usuarioenc='" + usuarioenc + "'");
                                } catch (Exception ex) {
                                    JOptionPane.showMessageDialog(null, ex.toString());
                                }
                                lblEstadoValidacion.setText("Cuenta validada");
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.toString());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        }
    }//GEN-LAST:event_btnActivarActionPerformed

    private void desconectarCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_desconectarCuentaActionPerformed
        try {
            st.executeUpdate("UPDATE webchat_users SET conectado='NO' WHERE name='" + usuario + "'");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        try {            
            st.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        passUsuario.setText("");
        String[] vacio = {""};
        jTabbedPane1.removeAll();
        jTabbedPane1.addTab("Log In", new javax.swing.ImageIcon(getClass().getResource("/icons/miniklaus.png")), jPanel1);
        listaAmigos.setListData(vacio);
        listaGrupos.setListData(vacio);
        listaIntegrantes.setListData(vacio);
        lblEstado.setText("Desconectado");
        salirGrupo.setEnabled(false);
        quitarDelGrupo.setEnabled(false);
        lblUsuario.setText("Usuario");
        menuAmigos.setEnabled(false);
        enviar.setEnabled(false);
        menuGrupos.setEnabled(false);
        abrirIconos.setEnabled(false);
        abrirIconsG.setEnabled(false);
        passUsuario.setEnabled(true);
        conectarCuenta.setEnabled(true);
        desconectarCuenta.setEnabled(false);
        usuario = "";
        password = "";
        amigos = "";
        receptor = "";
        receptorGroup = "";
        grupos = "";
        threadlista = null;
        threadlistaG = null;
        jTextPane1.setText("");
        tPGrupo.setText("");
        labelGrupoActual.setText("Actual");
        try {
            threadlista.stop();
            threadlistaG.stop();
        } catch (NullPointerException e) {
            System.out.println("el thread era nulo!");
        }
    }//GEN-LAST:event_desconectarCuentaActionPerformed

    private void passUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passUsuarioActionPerformed
        String pass = new String(passUsuario.getPassword());
        password = encript(pass);
        usuario = txtUsuario.getText();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            try {
                conexion = DriverManager.getConnection("jdbc:mysql://server:port/database", "user", "pass");
                try {
                    st = conexion.createStatement();
                    String query = "SELECT * FROM webchat_users WHERE name = '" + usuario + "'";
                    ResultSet rs = null;
                    try {
                        rs = st.executeQuery(query);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.toString());
                    }
                    try {
                        if (!rs.next()) {
                            lblEstado.setText("No se encuentra el usuario");
                        } else {
                            String password2 = rs.getString("pass");
                            if (password.equals(password2)) {
                                lblEstado.setText("Logueado");
                                st.executeUpdate("UPDATE webchat_users SET conectado='SI' WHERE name='" + usuario + "'");
                                menuAmigos.setEnabled(true);
                                conectarCuenta.setEnabled(false);
                                desconectarCuenta.setEnabled(true);
                                menuGrupos.setEnabled(true);
                                passUsuario.setEnabled(false);
                                jTabbedPane1.removeAll();
                                jTabbedPane1.addTab("Chat", new javax.swing.ImageIcon(getClass().getResource("/icons/chat.png")), jPanel2);
                                jTabbedPane1.addTab("Grupos de Chat", new javax.swing.ImageIcon(getClass().getResource("/icons/chatG.png")), jPanel3);
                                jTabbedPane1.setSelectedIndex(0);
                                iconsV = new IconsForm(conexion);
                                iconsVG = new IconsForm(conexion);
                                conectarCuenta.setEnabled(false);
                                refrescarListas();
                            } else {
                                lblEstado.setText("contraseña incorrecta");
                            }
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.toString());
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.toString());
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_passUsuarioActionPerformed

    private void addFriend(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addFriend
        new AddFriend(usuario, conexion).setVisible(true);
    }//GEN-LAST:event_addFriend

    private void confirmarAmigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarAmigoActionPerformed
        new ConfirmFriend(usuario, conexion).setVisible(true);
    }//GEN-LAST:event_confirmarAmigoActionPerformed

    private void actualizarChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarChatActionPerformed
        refrescarListas();
    }//GEN-LAST:event_actualizarChatActionPerformed

    private void enviar(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviar
        ResultSet rs = null;
        String texto = aEnviarIndividual.getText();
        texto = encrypt2(texto, passInd);
        aEnviarIndividual.setText("");
        receptor = lblUsuario.getText();
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String hora = sdf.format(cal.getTime());
        String query = "INSERT INTO webchat_lines (author, receptor, text, ts) VALUES('" + usuario + "', '" + receptor + "', '" + texto + "', '" + hora + "')";
        try {
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no se pudo hacer");
        }
    }//GEN-LAST:event_enviar

    private void abrirChatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirChatActionPerformed
        int index = listaAmigos.getSelectedIndex();
        if (index != -1) {
            Object o = listaAmigos.getModel().getElementAt(index);
            receptor = o.toString();
            int largo = receptor.length();
            largo = largo - 6;
            passInd = new String(encInd.getPassword());
            receptor = receptor.substring(0, largo);
            System.out.println("//" + receptor + "//");
            jTextPane1.setText("");
            lblUsuario.setText(receptor);
            abrirIconos.setEnabled(true);
            iconsV.setUsuario(usuario);
            iconsV.setPass(passInd);
            iconsV.setTipo("Individual");
            iconsV.setReceptor(receptor);
            enviar.setEnabled(true);
            try {
                threadlista.stop();
                jTextPane1.setText("");
            } catch (NullPointerException e) {
                System.out.println("el thread era nulo!");
            }
            threadlista = new ThreadLista(usuario, receptor
                    , jTextPane1, conexion, passInd);
            threadlista.start();
        }
    }//GEN-LAST:event_abrirChatActionPerformed

    private void abrirIconosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirIconosActionPerformed
       iconsV.setVisible(true);
    }//GEN-LAST:event_abrirIconosActionPerformed

    private void aEnviarIndividualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aEnviarIndividualActionPerformed
        ResultSet rs = null;
        String texto = aEnviarIndividual.getText();
        texto = encrypt2(texto, passInd);
        aEnviarIndividual.setText("");
        receptor = lblUsuario.getText();
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String hora = sdf.format(cal.getTime());
        String query = "INSERT INTO webchat_lines (author, receptor, text, ts) VALUES('" + usuario + "', '" + receptor + "', '" + texto + "', '" + hora + "')";
        try {
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no se pudo hacer");
        }
    }//GEN-LAST:event_aEnviarIndividualActionPerformed

    private void crearGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearGrupoActionPerformed
        new AddGroup(usuario, conexion).setVisible(true);
    }//GEN-LAST:event_crearGrupoActionPerformed

    private void actualizarGruposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actualizarGruposActionPerformed
        refrescarListas();
    }//GEN-LAST:event_actualizarGruposActionPerformed

    private void abrirGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirGrupoActionPerformed
        int index = listaGrupos.getSelectedIndex();
        if (index != -1) {
            Object o = listaGrupos.getModel().getElementAt(index);
            receptorGroup = o.toString();
            tPGrupo.setText("");
            labelGrupoActual.setText(receptorGroup);
            abrirIconsG.setEnabled(true);
            passGrup = new String(encGrup.getPassword());
            iconsVG.setUsuario(usuario);
            iconsVG.setTipo("Grupo");
            iconsVG.setPass(passGrup);
            iconsVG.setReceptor(receptorGroup);
            enviarGrupo.setEnabled(true);
            try {
                threadlistaG.stop();
                tPGrupo.setText("");
            } catch (NullPointerException e) {
                System.out.println("el thread era nulo!");
            }
            threadlistaG = new ThreadListaGroups(usuario, receptorGroup
                     , tPGrupo, conexion, passGrup);
            threadlistaG.start();
        }
        if (enviarGrupo.isEnabled()) {
            String query = "SELECT * FROM webchat_groups WHERE name = '" + receptorGroup + "'";
            ResultSet rs = null;
            try {
                rs = st.executeQuery(query);
            } catch (SQLException ex) {
                Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (rs.next()) {
                    amigos = rs.getString("friends");
                    String creador = rs.getString("creador");
                    String[] arrayAmigos = amigos.split(", ");
                    listaIntegrantes.setListData(arrayAmigos);
                    if (usuario.equals(creador)) {
                        salirGrupo.setText("Eliminar Grupo");
                        salirGrupo.setEnabled(true);
                        quitarDelGrupo.setEnabled(true);
                    } else {
                        salirGrupo.setText("Salir");
                        salirGrupo.setEnabled(true);
                        quitarDelGrupo.setEnabled(false);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            }
            refrescarListas();
        }
    }//GEN-LAST:event_abrirGrupoActionPerformed

    private void abrirIconsGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_abrirIconsGActionPerformed
        iconsVG.setVisible(true);
    }//GEN-LAST:event_abrirIconsGActionPerformed

    private void txtEnviarGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEnviarGrupoActionPerformed
        String texto = txtEnviarGrupo.getText();
        txtEnviarGrupo.setText("");
        texto = encrypt2(texto, passGrup);
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String hora = sdf.format(cal.getTime());
        String query = "INSERT INTO groupchat_lines (author, grouprec, texte, ts) VALUES('" + usuario + "', '" + receptorGroup + "', '" + texto + "', '" + hora + "')";
        try {
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no se pudo hacer");
        }
    }//GEN-LAST:event_txtEnviarGrupoActionPerformed

    private void enviarGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enviarGrupoActionPerformed
        String texto = txtEnviarGrupo.getText();
        txtEnviarGrupo.setText("");
        texto = encrypt2(texto, passGrup);
        Calendar cal = Calendar.getInstance();
        cal.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String hora = sdf.format(cal.getTime());
        String query = "INSERT INTO groupchat_lines (author, grouprec, texte, ts) VALUES('" + usuario + "', '" + receptorGroup + "', '" + texto + "', '" + hora + "')";
        try {
            st.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no se pudo hacer");
        }
    }//GEN-LAST:event_enviarGrupoActionPerformed

    private void invitarAlGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_invitarAlGrupoActionPerformed
        int index = listaAmigos.getSelectedIndex();
        if (index != -1) {
            Object o = listaAmigos.getModel().getElementAt(index);
            String receptorcito = o.toString();
            System.out.println(receptorcito.substring(0, receptorcito.length()-6));
            receptorcito = receptorcito.substring(0, receptorcito.length()-6);
            new InviteGroup(usuario, receptorcito, conexion).setVisible(true);
        }
    }//GEN-LAST:event_invitarAlGrupoActionPerformed

    private void confirmarGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmarGrupoActionPerformed
        // TODO add your handling code here:
        new ConfirmGroup(usuario, conexion).setVisible(true);
    }//GEN-LAST:event_confirmarGrupoActionPerformed

    private void salirGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_salirGrupoActionPerformed
        try {
            threadlistaG.stop();
            tPGrupo.setText("");
        } catch (NullPointerException e) {
            System.out.println("el thread era nulo!");
        }
        String label = salirGrupo.getText();
        ResultSet rs = null;
        String amigosrqr = "", friendsgrupo = "";
        String query = "SELECT * FROM webchat_groups WHERE name = '" + labelGrupoActual.getText() + "'";
        try {
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(AddFriend.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (rs.next()) {
                amigosrqr = rs.getString("friends");
                friendsgrupo = amigosrqr;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (label.equals("Salir")) {
            System.out.println("era para salir");
            if (amigosrqr != null && !amigosrqr.equals("")) {
                String[] arrayAmigos = amigosrqr.split(", ");
                String amigosNuevos = "";
                for (int i = 0; i < arrayAmigos.length; i++) {
                    if (!arrayAmigos[i].equals(usuario)) {
                        if (amigosNuevos != null && !amigosNuevos.equals("")) {
                            amigosNuevos += ", " + arrayAmigos[i];
                        } else {
                            amigosNuevos = arrayAmigos[i];
                        }
                    }
                }
                System.out.println("actualizado amigos con:\n" + amigosNuevos + "\n");
                try {
                    st.executeUpdate("UPDATE webchat_groups SET friends='" + amigosNuevos + "' WHERE name='" + labelGrupoActual.getText() + "'");
                } catch (SQLException ex) {
                    Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            query = "SELECT * FROM webchat_users WHERE name = '" + usuario + "'";
            try {
                rs = st.executeQuery(query);
            } catch (SQLException ex) {
                Logger.getLogger(AddFriend.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (rs.next()) {
                    amigosrqr = rs.getString("groups");
                    if (amigosrqr != null && !amigosrqr.equals("")) {
                        String[] arrayAmigos = amigosrqr.split(", ");
                        String amigosNuevos = "";                        
                        for (int i = 0; i < arrayAmigos.length; i++) {
                            if (!arrayAmigos[i].equals(labelGrupoActual.getText())) {
                                if (amigosNuevos != null && !amigosNuevos.equals("")) {
                                    amigosNuevos += ", " + arrayAmigos[i];
                                } else {
                                    amigosNuevos = arrayAmigos[i];
                                }
                            }
                        }
                        System.out.println("actualizado amigos con:\n" + amigosNuevos + "\n");
                        st.executeUpdate("UPDATE webchat_users SET groups='" + amigosNuevos + "' WHERE name='" + usuario + "'");
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            }
            

// matar grupo *************************************************************
        } else {
            System.out.println("era para eliminar");
            String[] arrayAmigos = null;
            String[] arrayAmigos2;
            String amigosNuevos = "";
            System.out.println("amigos del grupo: " + friendsgrupo);
            if (friendsgrupo != null && !friendsgrupo.equals("")) {
                arrayAmigos = friendsgrupo.split(", ");
            }
            for (int i = 0; i < arrayAmigos.length; i++) {
                System.out.println("cambiando amigo " + arrayAmigos[i]);
                try {
                    query = "SELECT * FROM webchat_users WHERE name = '" + arrayAmigos[i] + "'";
                    try {
                        rs = st.executeQuery(query);
                    } catch (SQLException ex) {
                        Logger.getLogger(AddFriend.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        if (rs.next()) {
                            amigosrqr = rs.getString("groups");
                            if (amigosrqr != null && !amigosrqr.equals("")) {
                                arrayAmigos2 = amigosrqr.split(", ");
                                for (int i2 = 0; i2 < arrayAmigos2.length; i2++) {
                                    if (!arrayAmigos2[i2].equals(labelGrupoActual.getText())) {
                                        if (amigosNuevos != null && !amigosNuevos.equals("")) {
                                            amigosNuevos += ", " + arrayAmigos2[i2];
                                        } else {
                                            amigosNuevos = arrayAmigos2[i2];
                                        }
                                    }
                                }
                                System.out.println("actualizado grupos con: //" + amigosNuevos + "//\ndel usuario " + arrayAmigos[i]);
                                st.executeUpdate("UPDATE webchat_users SET groups='" + amigosNuevos + "' WHERE name='" + arrayAmigos[i] + "'");
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
            try {
                st.executeUpdate("DELETE FROM webchat_groups WHERE name='" + labelGrupoActual.getText() + "'");
                st.executeUpdate("DELETE FROM groupchat_lines WHERE grouprec='" + labelGrupoActual.getText() + "'");
            } catch (SQLException ex) {
                Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        refrescarListas();
        String[] vacio = {"",""};
        listaIntegrantes.setListData(vacio);
    }//GEN-LAST:event_salirGrupoActionPerformed

    private void quitarDelGrupoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitarDelGrupoActionPerformed
        int index = listaIntegrantes.getSelectedIndex();
        if (index != -1) {
            Object o = listaIntegrantes.getModel().getElementAt(index);
            String confirmado = o.toString();
            int largo = confirmado.length();
            largo = largo - 6;
            confirmado = confirmado.substring(0, largo);
            System.out.println("//" + receptor + "//");
            ResultSet rs = null;
            if (confirmado.equals(usuario)) {
                JOptionPane.showMessageDialog(null, "No puedes quitarte del grupo por ser el administrador.");
            } else {
                // para el usuario /*/*/*/*/*/*/*/*/*//*/*/*/*/*/*/*/*/**/
                String query = "SELECT * FROM webchat_users WHERE name = '" + confirmado + "'";
                try {
                    rs = st.executeQuery(query);
                } catch (SQLException ex) {
                    Logger.getLogger(AddFriend.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (rs.next()) {
                        String amigosrqr = rs.getString("groups");
                        if (amigosrqr != null && !amigosrqr.equals("")) {
                            String[] arrayAmigos = amigosrqr.split(", ");
                            String amigosNuevos = "";
                            for (int i = 0; i < arrayAmigos.length; i++) {
                                if (!arrayAmigos[i].equals(labelGrupoActual.getText())) {
                                    if (amigosNuevos != null && !amigosNuevos.equals("")) {
                                        amigosNuevos += ", " + arrayAmigos[i];
                                    } else {
                                        amigosNuevos = arrayAmigos[i];
                                    }
                                }
                            }
                            st.executeUpdate("UPDATE webchat_users SET groups = '" + amigosNuevos + "' WHERE name='" + confirmado + "'");
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
                }
                // para el grupo /*/*/*/*/*/*/*/*/*//*/*/*/*/*/*/*/*/**/
                query = "SELECT * FROM webchat_groups WHERE name = '" + labelGrupoActual.getText() + "'";
                try {
                    rs = st.executeQuery(query);
                } catch (SQLException ex) {
                    Logger.getLogger(AddFriend.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (rs.next()) {
                        String amigosrqr = rs.getString("friends");
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
                            st.executeUpdate("UPDATE webchat_groups SET friends='" + amigosNuevos + "' WHERE name = '" + labelGrupoActual.getText() + "'");
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
                }
                refrescarListas();
            }
        }
    }//GEN-LAST:event_quitarDelGrupoActionPerformed

    private void opcionCuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCuentaActionPerformed
        new VentanaCuenta().setVisible(true);
    }//GEN-LAST:event_opcionCuentaActionPerformed

    private void acercaDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acercaDeActionPerformed
        new VentanaAcercaDe().setVisible(true);
    }//GEN-LAST:event_acercaDeActionPerformed
    /**
     * Metodo de encriptacion donde se le da como parametro una contraseña y un
     * texto a encriptar.El texto podra ser desencriptado.
     *
     * @param cadena String
     * @param pass String
     * @return textoEncriptado
     */
    public String encrypt2(String cadena, String pass) {
        StandardPBEStringEncryptor s = new StandardPBEStringEncryptor();
        s.setPassword(pass);
        return s.encrypt(cadena);
    }

    /**
     * se le entrega una contraseña y la encripta de manera que no se pueda
     * desencriptar para tratar con la base de datos
     *
     * @param pass String
     * @return encPassword String
     */
    public String encript(String pass) {
        SHA1 s = new SHA1();
        String basura = "guacho";
        try {
            return s.getHash(s.getHash(pass + basura) + basura);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            return "0";
        }
    }

    /**
     * Actualiza las listas de todo el programa con datos actuales
     *
     */
    public void refrescarListas() {
        String query = "SELECT * FROM webchat_users WHERE name = '" + usuario + "'";
        String[] arrayAmigos = null;
        String conectados = "";
        String conectadosG = "";
        ResultSet rs = null, rs2 = null;
        try {
            rs = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (rs.next()) {
                int id = rs.getInt("id");
                amigos = rs.getString("friends");
                grupos = rs.getString("groups");
                String[] vacio = {""};
                String password2 = rs.getString("pass");
                if (password.equals(password2)) {
                    if (amigos != null && !amigos.equals("")) {
                        arrayAmigos = amigos.split(", ");
                    } else {
                        listaAmigos.setListData(vacio);
                    }
                    if (grupos != null && !grupos.equals("")) {
                        String[] arrayGrupos = grupos.split(", ");

                        listaGrupos.setListData(arrayGrupos);
                    } else {
                        listaGrupos.setListData(vacio);
                    }
                } else {
                    lblEstado.setText("contraseña incorrecta");
                }
            } else {
                lblEstado.setText("No se encuentra el usuario");
            }
            for (int i = 0; i < arrayAmigos.length; i++) {
                query = "SELECT * FROM webchat_users WHERE name = '" + arrayAmigos[i] + "'";
                try {
                    rs2 = st.executeQuery(query);
                } catch (SQLException ex) {
                    Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (rs2.next()) {
                    String conectado = rs2.getString("conectado");
                    if (!conectados.equals("")) {
                        conectados += "," + conectado;
                    } else {
                        conectados += conectado;
                    }
                }
            }
            String[] arrayConectados = conectados.split(",");
            String[] vectorFinal = getConectados(arrayAmigos, arrayConectados);
            listaAmigos.setListData(vectorFinal);
            listaAmigos.setCellRenderer(new FileListCellRenderer());
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
        }
        String[] arrayAmigosG = {""};
        if (enviarGrupo.isEnabled()) {
            query = "SELECT * FROM webchat_groups WHERE name = '" + receptorGroup + "'";
            rs = null;
            try {
                rs = st.executeQuery(query);
            } catch (SQLException ex) {
                Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                if (rs.next()) {
                    amigos = rs.getString("friends");
                    arrayAmigosG = amigos.split(", ");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
            }
            for (int i = 0; i < arrayAmigosG.length; i++) {
                query = "SELECT * FROM webchat_users WHERE name = '" + arrayAmigosG[i] + "'";
                try {
                    rs2 = st.executeQuery(query);
                } catch (SQLException ex) {
                    Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    if (rs2.next()) {
                        String conectado = rs2.getString("conectado");
                        if (!conectadosG.equals("")) {
                            conectadosG += "," + conectado;
                        } else {
                            conectadosG += conectado;
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String[] arrayConectadosG = conectadosG.split(",");
            String[] vectorFinal = getConectados(arrayAmigosG, arrayConectadosG);
            listaIntegrantes.setListData(vectorFinal);
            listaIntegrantes.setCellRenderer(new FileListCellRenderer());
        }
    }

    /**
     * Procesa un vector de usuarios y otro con valores SI y NO para determinar
     * quien esta conectado y quien no.Es complementario a la clase
     * FileListCellRenderer()
     *
     * @param usuarios String[]
     * @param conectados String []
     * @return usuariosConectados String []
     */
    public String[] getConectados(String[] usuarios, String[] conectados) {
        String[] resultado = new String[usuarios.length];
        if (usuarios.length == conectados.length) {
            for (int i = 0; i < usuarios.length; i++) {
                if (conectados[i].equals("SI")) {
                    resultado[i] = usuarios[i] + ".*/*SI";
                } else if (conectados[i].equals("NO")) {
                    resultado[i] = usuarios[i] + ".*/*NO";
                }
            }
        }
        return resultado;
    }

    @Override
    public void dispose() {
        try {
            st.close();
            conexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Graficos.class.getName()).log(Level.SEVERE, null, ex);
        }
        super.dispose();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Graficos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Graficos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Graficos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Graficos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Graficos().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aEnviarIndividual;
    private javax.swing.JButton abrirChat;
    private javax.swing.JButton abrirGrupo;
    private javax.swing.JButton abrirIconos;
    private javax.swing.JButton abrirIconsG;
    private javax.swing.JMenuItem acercaDe;
    private javax.swing.JButton actualizarChat;
    private javax.swing.JButton actualizarGrupos;
    private javax.swing.JMenuItem addAmigo;
    private javax.swing.JButton btnActivar;
    private javax.swing.JTextField codeValidar;
    private javax.swing.JButton conectarCuenta;
    private javax.swing.JMenuItem confirmarAmigo;
    private javax.swing.JMenuItem confirmarGrupo;
    private javax.swing.JMenuItem crearGrupo;
    private javax.swing.JMenuItem desconectarCuenta;
    private javax.swing.JPasswordField encGrup;
    private javax.swing.JPasswordField encInd;
    private javax.swing.JButton enviar;
    private javax.swing.JButton enviarGrupo;
    private javax.swing.JButton invitarAlGrupo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel labelGrupoActual;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblEstadoValidacion;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JList listaAmigos;
    private javax.swing.JList listaGrupos;
    private javax.swing.JList listaIntegrantes;
    private javax.swing.JMenu menuAmigos;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuGrupos;
    private javax.swing.JMenuItem opcionCuenta;
    private javax.swing.JPasswordField pass1Validar;
    private javax.swing.JPasswordField pass2Validar;
    private javax.swing.JPasswordField passUsuario;
    private javax.swing.JButton quitarDelGrupo;
    private javax.swing.JButton salirGrupo;
    private javax.swing.JTextPane tPGrupo;
    private javax.swing.JTextField txtEnviarGrupo;
    private javax.swing.JTextField txtUsuario;
    private javax.swing.JMenu validarCuenta;
    private javax.swing.JFrame ventanaValidar;
    // End of variables declaration//GEN-END:variables
}

class FileListCellRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = -7799441088157759804L;
    private FileSystemView fileSystemView;
    private JLabel label;
    private Color textSelectionColor = Color.BLACK;
    private Color backgroundSelectionColor = Color.CYAN;
    private Color textNonSelectionColor = Color.BLACK;
    private Color backgroundNonSelectionColor = Color.WHITE;
    
    FileListCellRenderer() {
        label = new JLabel();
        label.setOpaque(true);
    }
    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean selected,
            boolean expanded) {
        String directorioTrabajo = System.getProperty("user.dir");
        try {
            String stringObjeto = value.toString();
            if (stringObjeto.indexOf(".*/*SI") != -1) {
                int largo = stringObjeto.length();
                largo = largo - 6;
                label.setText(stringObjeto.substring(0, largo));
                label.setIcon(new ImageIcon(directorioTrabajo + "/icons/conectado.png"));
            }
            if (stringObjeto.indexOf(".*/*NO") != -1) {
                int largo = stringObjeto.length();
                largo = largo - 6;
                label.setText(stringObjeto.substring(0, largo));
                label.setIcon(new ImageIcon(directorioTrabajo + "/icons/desconectado.png"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        if (selected) {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }
        return label;
    }
}
