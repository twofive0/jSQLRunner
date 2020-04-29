/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBConnectionManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import jsqlrunner.frmMain;

/**
 *
 * @author devin
 */
public class DBConnMan extends javax.swing.JFrame {

    public ArrayList<DBConnection> ConnectionList = new ArrayList<DBConnection>();
    DefaultListModel listModel = new DefaultListModel();
    //DBConnection CurrentConnection = new DBConnection();
    int CurrentIndex = 0;
    public String SavedConnectionsFileLocation;
    public boolean recordChanging = false;
    /**
     * Creates new form DBConnMan
     */
    public DBConnMan() {
        initComponents();
        
        //getSettingsFile();
        lstConnections.setModel(listModel);
        getSettingsFile();
        setPicklistValues();
        setupChangeEvents();
    }
    
    public DBConnection getSelectedConnection()
    {
        for (DBConnection dbc : ConnectionList)
        {
            if (dbc.InUse) return dbc;
        }
        
        return new DBConnection();
    }
    
    private void setupChangeEvents()
    {
        txtName.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              txtNameChanged();
            }
            public void removeUpdate(DocumentEvent e) {
              txtNameChanged();
            }
            public void insertUpdate(DocumentEvent e) {
              txtNameChanged();
            }
        });
        
        txtDatabase.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void removeUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void insertUpdate(DocumentEvent e) {
              textFieldChanged();
            }
        });
        
        txtFile.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void removeUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void insertUpdate(DocumentEvent e) {
              textFieldChanged();
            }
        });
        
        txtName.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void removeUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void insertUpdate(DocumentEvent e) {
              textFieldChanged();
            }
        });
        
        this.txtPassword.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void removeUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void insertUpdate(DocumentEvent e) {
              textFieldChanged();
            }
        });
        
        this.txtPort.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void removeUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void insertUpdate(DocumentEvent e) {
              textFieldChanged();
            }
        });
        
        this.txtServer.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void removeUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void insertUpdate(DocumentEvent e) {
              textFieldChanged();
            }
        });
        
        this.txtUser.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void removeUpdate(DocumentEvent e) {
              textFieldChanged();
            }
            public void insertUpdate(DocumentEvent e) {
              textFieldChanged();
            }
        });
    }
    
    private void txtNameChanged()
    {
        try
        {
            listModel.setElementAt(txtName.getText(), lstConnections.getSelectedIndex());
            ConnectionList.get(CurrentIndex).ConnectionName = txtName.getText();
        } catch (Exception e) {}
    }
    
    private void textFieldChanged()
    {
        if (recordChanging) return;
        
        ConnectionList.get(CurrentIndex).ConnectionName = txtName.getText();
        ConnectionList.get(CurrentIndex).ConnectionType = this.cboDBType.getSelectedItem().toString();
        ConnectionList.get(CurrentIndex).Database = txtDatabase.getText();
        ConnectionList.get(CurrentIndex).FileLocation = txtFile.getText();
        ConnectionList.get(CurrentIndex).FilePassword = new String(txtPassword.getPassword());
        ConnectionList.get(CurrentIndex).Port = txtPort.getText();
        ConnectionList.get(CurrentIndex).Server = txtServer.getText();
        ConnectionList.get(CurrentIndex).UserName = txtUser.getText();
        ConnectionList.get(CurrentIndex).UserPassword = new String(txtPassword.getPassword());
    }
    
    public void setPicklistValues()
    {
        //listModel = new DefaultListModel<String>();
        if (this.listModel.getSize() > 0) this.listModel.removeAllElements();
        //lstConnections.setModel(listModel);
        
        for (DBConnection dbc : ConnectionList)
        {
            listModel.addElement(dbc.ConnectionName);
        }
        
        if (listModel.getSize() > 0)
        {
            boolean foundSelected = false;
            for (DBConnection dbc : ConnectionList)
            {
                if (dbc.InUse)
                {
                    lstConnections.setSelectedValue(dbc.ConnectionName, true);
                    foundSelected = true;
                }
            }
            if (!foundSelected) lstConnections.setSelectedIndex(0);
            CurrentIndex = getDBIndexByName(lstConnections.getSelectedValue());
            loadFields();
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

        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstConnections = new javax.swing.JList<>();
        jPanel1 = new javax.swing.JPanel();
        txtName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtServer = new javax.swing.JTextField();
        txtDatabase = new javax.swing.JTextField();
        txtPort = new javax.swing.JTextField();
        txtUser = new javax.swing.JTextField();
        txtFile = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        btnSelectFile = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnNew = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        cboDBType = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        chkIntegratedSecurity = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Connection Manager");

        jSplitPane1.setDividerLocation(200);

        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 130));

        lstConnections.setMaximumSize(new java.awt.Dimension(600, 2000));
        lstConnections.setMinimumSize(new java.awt.Dimension(200, 185));
        lstConnections.setPreferredSize(new java.awt.Dimension(200, 185));
        lstConnections.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lstConnectionsMousePressed(evt);
            }
        });
        lstConnections.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                lstConnectionsKeyReleased(evt);
            }
        });
        lstConnections.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstConnectionsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstConnections);

        jSplitPane1.setLeftComponent(jScrollPane1);

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        txtName.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txtNamePropertyChange(evt);
            }
        });
        txtName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNameKeyReleased(evt);
            }
        });

        jLabel1.setText("Name");

        btnSelectFile.setText("Select");
        btnSelectFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectFileActionPerformed(evt);
            }
        });

        jLabel2.setText("Server");

        jLabel3.setText("Database");

        jLabel4.setText("Port");

        jLabel5.setText("User");

        jLabel6.setText("Password");

        jLabel7.setText("File");

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnNew.setText("New");
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.setToolTipText("");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        cboDBType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SQL Server", "SQLite", "MySQL", "PostgreSQL", "ODBC" }));
        cboDBType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDBTypeActionPerformed(evt);
            }
        });
        cboDBType.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                cboDBTypePropertyChange(evt);
            }
        });

        jLabel8.setText("Type");

        chkIntegratedSecurity.setText("Integrated Security");
        chkIntegratedSecurity.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                chkIntegratedSecurityStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnNew)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtFile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSelectFile, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnDelete)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 167, Short.MAX_VALUE)
                                .addComponent(btnCancel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSave))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(chkIntegratedSecurity)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtName)
                            .addComponent(jSeparator1)
                            .addComponent(txtServer)
                            .addComponent(txtDatabase)
                            .addComponent(txtPort)
                            .addComponent(txtUser)
                            .addComponent(txtPassword)
                            .addComponent(cboDBType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboDBType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDatabase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addComponent(chkIntegratedSecurity)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectFile)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnNew)
                    .addComponent(btnDelete)
                    .addComponent(btnCancel))
                .addGap(23, 23, 23))
        );

        jSplitPane1.setRightComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSplitPane1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectFileActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int fcChoice = fc.showOpenDialog(this);
        
        if (fcChoice == JFileChooser.APPROVE_OPTION)
        {
            this.txtFile.setText(fc.getSelectedFile().toString());
        }
    }//GEN-LAST:event_btnSelectFileActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveSettingsFile();
        this.dispose();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        recordChanging = true;
        int i = getDBIndexByName(lstConnections.getSelectedValue());
        listModel.removeElement(lstConnections.getSelectedValue());
        ConnectionList.remove(i);
        
        if (listModel.getSize() > 0)
        {
            lstConnections.setSelectedIndex(0);
            CurrentIndex = getDBIndexByName(lstConnections.getSelectedValue());
            loadFields();
        }
        recordChanging = false;
        setCurrentConnection();
        
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        recordChanging = true;
        DBConnection dbc = new DBConnection();
        dbc.ConnectionName = "NewConnection" + listModel.size();
        dbc.ConnectionType = "SQL Server";
        dbc.Database = "database";
        dbc.Server = "localhost";
        ConnectionList.add(dbc);
        listModel.addElement(dbc.ConnectionName);
        lstConnections.setSelectedValue(dbc.ConnectionName, true);
        CurrentIndex = getDBIndexByName(dbc.ConnectionName);
        loadFields();
        recordChanging = false;
        setCurrentConnection();
    }//GEN-LAST:event_btnNewActionPerformed

    private void cboDBTypePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_cboDBTypePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_cboDBTypePropertyChange

    private void lstConnectionsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstConnectionsValueChanged

    }//GEN-LAST:event_lstConnectionsValueChanged

    private void loadFields()
    {
        recordChanging = true;
        
        if (CurrentIndex == -1) return;
        
        try
        {
        txtName.setText(ConnectionList.get(CurrentIndex).ConnectionName);
        cboDBType.setSelectedItem(ConnectionList.get(CurrentIndex).ConnectionType);
        txtDatabase.setText(ConnectionList.get(CurrentIndex).Database);
        txtFile.setText(ConnectionList.get(CurrentIndex).FileLocation);
        txtPassword.setText(ConnectionList.get(CurrentIndex).UserPassword);
        txtPort.setText(ConnectionList.get(CurrentIndex).Port);        
        txtServer.setText(ConnectionList.get(CurrentIndex).Server);
        chkIntegratedSecurity.setSelected(ConnectionList.get(CurrentIndex).UseTrustedConnection);
        txtUser.setText(ConnectionList.get(CurrentIndex).UserName);
        }
        catch (Exception e)
        {
            
        }
        
        recordChanging = false;
        
    }
    
    private void commitFields()
    {
        if (CurrentIndex == -1) return;
        
        ConnectionList.get(CurrentIndex).ConnectionName = txtName.getText();
        try
        {
            ConnectionList.get(CurrentIndex).ConnectionType = cboDBType.getSelectedItem().toString();
        } catch (Exception e){}
        ConnectionList.get(CurrentIndex).Database = txtDatabase.getText();
        ConnectionList.get(CurrentIndex).FileLocation = txtFile.getText();
        ConnectionList.get(CurrentIndex).FilePassword = new String(txtPassword.getPassword());
        ConnectionList.get(CurrentIndex).InUse = true;
        ConnectionList.get(CurrentIndex).LastConnectionDate = "";
        ConnectionList.get(CurrentIndex).LastUser = "";
        ConnectionList.get(CurrentIndex).Port = txtPort.getText();        
        ConnectionList.get(CurrentIndex).Server = txtServer.getText();
        ConnectionList.get(CurrentIndex).TargetDatabase = "";
        ConnectionList.get(CurrentIndex).UseTrustedConnection = chkIntegratedSecurity.isSelected();
        ConnectionList.get(CurrentIndex).UserName = txtUser.getText();
        ConnectionList.get(CurrentIndex).UserPassword = new String(txtPassword.getPassword());      
    }
    
    private void chkIntegratedSecurityStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_chkIntegratedSecurityStateChanged
        
        if (chkIntegratedSecurity.isSelected())
        {
            this.txtUser.setEnabled(false);
            this.txtPassword.setEnabled(false);
        }
        else
        {
            this.txtUser.setEnabled(true);
            this.txtPassword.setEnabled(true);
        }
        
        ConnectionList.get(CurrentIndex).UseTrustedConnection = this.chkIntegratedSecurity.isSelected();
    }//GEN-LAST:event_chkIntegratedSecurityStateChanged

    private void txtNamePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txtNamePropertyChange
        //commitFields();
    }//GEN-LAST:event_txtNamePropertyChange

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        //commitFields();
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNameKeyReleased

    }//GEN-LAST:event_txtNameKeyReleased

    private void lstConnectionsMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lstConnectionsMousePressed
        //this.commitFields();
        CurrentIndex = getDBIndexByName(lstConnections.getSelectedValue());
        loadFields();
        setCurrentConnection();
    }//GEN-LAST:event_lstConnectionsMousePressed

    private void cboDBTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDBTypeActionPerformed
        if (this.cboDBType.getSelectedItem().toString().equals("SQLite"))
        {
            this.txtDatabase.setEnabled(false);
            this.txtServer.setEnabled(false);
            this.txtPort.setEnabled(false);
            this.txtUser.setEnabled(false);
            this.txtFile.setEnabled(true);
            this.btnSelectFile.setEnabled(true);
            this.chkIntegratedSecurity.setEnabled(false);
        }
        else
        {
            this.txtDatabase.setEnabled(true);
            this.txtServer.setEnabled(true);
            this.txtPort.setEnabled(true);
            this.txtUser.setEnabled(true);
            this.txtFile.setEnabled(false);
            this.btnSelectFile.setEnabled(false);
            this.chkIntegratedSecurity.setEnabled(true);
        }
        
    }//GEN-LAST:event_cboDBTypeActionPerformed

    private void lstConnectionsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lstConnectionsKeyReleased
        CurrentIndex = getDBIndexByName(lstConnections.getSelectedValue());
        loadFields();
        setCurrentConnection();
    }//GEN-LAST:event_lstConnectionsKeyReleased

        public String saveSettingsFile(){

        String strFile = System.getProperty("user.home") + "/.jsqlrunner/settings.cfg";
        String fileContents = "";
        File f = new File(strFile);
        File d = new File(System.getProperty("user.home") + "/.jsqlrunner");

        if (!ConnectionList.isEmpty())        
        {
            for (DBConnection dbc : ConnectionList)
            {
                fileContents = fileContents + dbc.getDelimitedString() + System.lineSeparator();
            }

            try {
                //let's try to create a settings file if it doesn't exist, shall we?
                if(!d.exists()){d.mkdir();}
                if(!f.exists()){f.createNewFile();}
                BufferedWriter br = new BufferedWriter(new FileWriter(f.getAbsoluteFile()));
                br.write(fileContents);
                br.close();
            }
            catch (Exception e){
                return e.getMessage();
            }
        }

    return "";

}
        
public void setCurrentConnection()
{
    for (DBConnection dbc : ConnectionList)
    {
        if (lstConnections.getSelectedValue().equals(dbc.ConnectionName))
        {
            dbc.InUse = true;
        }
        else
        {
            dbc.InUse = false;
        }
    }
}

public String getSettingsFile(){

    String strFile = System.getProperty("user.home") + "/.jsqlrunner/settings.cfg";
    String fileContents = "";
    File f = new File(strFile);
    FileUtils fu = new FileUtils();
    

    try {
        //let's try to create a settings file if it doesn't exist, shall we?
        if(!f.exists()){return "";}

        fileContents = fu.readFile(f.getAbsolutePath());
        
        String fileLines[] = fileContents.split(System.lineSeparator());
        
        for ( String fileLine : fileLines)
        {
            DBConnection newDbc = new DBConnection();
            newDbc.setFromString(fileLine);
            this.ConnectionList.add(newDbc);
        }

    }
    catch (Exception e){
        DBConnection newConn = new DBConnection();
        newConn.ConnectionName = "New Connection";
        this.ConnectionList.add(newConn);
        return e.getMessage();
    }
    
    return "";

}

public void addDemoData()
{
    DBConnection nc1 = new DBConnection();
    nc1.ConnectionName = "NewConnection";
    nc1.ConnectionType = "SQL Server";
    nc1.Database = "database";
    nc1.Server = "localhost";
    nc1.TargetDatabase = "LeakDASv4";
    nc1.UserName = "user";
    nc1.UserPassword = "";
    nc1.InUse = true;
    
    ConnectionList.add(nc1);
    this.setPicklistValues();
    this.lstConnections.setSelectedIndex(0);
    
//    
//    DBConnection nc2 = new DBConnection();
//    nc2.ConnectionName = "TestConnection2";
//    nc2.ConnectionType = "SQLite";
//    nc2.Database = "";
//    nc2.Server = "";
//    nc2.TargetDatabase = "";
//    nc2.UserName = "";
//    nc2.UserPassword = "";
//    nc2.FileLocation = "/users/devin/Documents/p66Borger.db";
//    
//    ConnectionList.add(nc2);
}

public int getDBIndexByName(String connectionName)
{
    for (int i = 0; i < ConnectionList.size(); i++)
    {
        if (ConnectionList.get(i).ConnectionName.equals(connectionName))
        {
            return i;
        }        
    }
    
    return -1;
}
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DBConnMan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DBConnMan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DBConnMan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DBConnMan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DBConnMan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnNew;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSelectFile;
    private javax.swing.JComboBox<String> cboDBType;
    private javax.swing.JCheckBox chkIntegratedSecurity;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JList<String> lstConnections;
    private javax.swing.JTextField txtDatabase;
    private javax.swing.JTextField txtFile;
    private javax.swing.JTextField txtName;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtServer;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
