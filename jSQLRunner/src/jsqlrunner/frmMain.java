/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jsqlrunner;

import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.table.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.fife.ui.rsyntaxtextarea.*;
import javax.swing.JFileChooser;
import java.io.*;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import DBConnectionManager.DBConnMan;
import DBConnectionManager.DBConnection;
/**
 *
 * @author Devin
 */
public class frmMain extends javax.swing.JFrame {
    public RSyntaxTextArea currentTextArea;
    public DBConnMan connMan;
    public DBConnection currentConnection;
    private boolean waitDBChange = false;
    
    /**
     * Creates new form frmMain
     */
    public frmMain() {
        
        initComponents();
      
      currentTextArea = new RSyntaxTextArea();      
      currentTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
      currentTextArea.setCodeFoldingEnabled(true);
      currentTextArea.setAntiAliasingEnabled(true);
      currentTextArea.setHighlightCurrentLine(false);
      this.jTabbedPane1.add("New1",currentTextArea);
      connMan = new DBConnMan();
      currentConnection = connMan.getSelectedConnection();
        
      if (connMan.ConnectionList.size() > 0)
      {
        try {
             loadTables(); }
        catch (ClassNotFoundException cnfe) {
        }
      }
      else
      {
          if (connMan.ConnectionList.size() == 0) connMan.addDemoData();
          connMan.setVisible(true);
      }
                
    }
    
    private void newTab(String tabName)
    {
      if (tabName.isEmpty()) tabName = "New" + (jTabbedPane1.getTabCount() + 1);
        
      currentTextArea = new RSyntaxTextArea();      
      currentTextArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
      currentTextArea.setCodeFoldingEnabled(true);
      currentTextArea.setAntiAliasingEnabled(true);
      currentTextArea.setHighlightCurrentLine(false);
      this.jTabbedPane1.add(tabName,currentTextArea);
      this.jTabbedPane1.setSelectedIndex(jTabbedPane1.getTabCount() - 1);
    }
    
    public void loadTables() throws ClassNotFoundException {
        
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        DefaultListModel listData = new DefaultListModel();
        String showTblCmd = "";
   
        String url,drv;
        url = "";
            
        switch(currentConnection.ConnectionType)
        {
            case "MySQL":
                url = "jdbc:mysql://" + currentConnection.Server + "/" + currentConnection.Database;
                drv = "com.mysql.jdbc.Driver";
                showTblCmd = "SHOW TABLES";
                break;
            case "SQL Server":
                if (currentConnection.Port.equals(""))
                {
                    url = "jdbc:sqlserver://" + currentConnection.Server + ";DatabaseName=" + currentConnection.Database;
                }
                else
                {
                    url = "jdbc:sqlserver://" + currentConnection.Server + ":" + currentConnection.Port + ";DatabaseName=" + currentConnection.Database;
                }
                drv = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                showTblCmd = "SELECT TABLE_NAME AS name FROM information_schema.TABLES ORDER BY name";
                break;
            case "SQLite":
                url = "jdbc:sqlite:" + currentConnection.FileLocation;
                drv = "org.sqlite.JDBC";
                Class.forName(drv);
                showTblCmd = "SELECT name FROM sqlite_master WHERE type='table'";
                break;
        }
      
        
        try {
            
            if (currentConnection.ConnectionType.equals("SQLite")) {
                con = DriverManager.getConnection(url);
                st = con.createStatement(); }
            else {
                con = DriverManager.getConnection(url, currentConnection.UserName, currentConnection.UserPassword);
                st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }
            con = DriverManager.getConnection(url, currentConnection.UserName, currentConnection.UserPassword);

            
            rs = st.executeQuery(showTblCmd);
            
            this.jList1.setModel(listData);

            
            //rs.first();
            while (rs.next()){
                listData.addElement(rs.getObject(1));
            }
            
            lblStatus.setText("Status: Connected to " + this.currentConnection.ConnectionName);

        } catch (SQLException ex) {
            lblStatus.setText("Status: Connection Error");
            JOptionPane.showMessageDialog(this,"ERROR: " + ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {

            }
        }

    }
    
        private void loadData()
        {
        
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        DefaultListModel listData = new DefaultListModel();
        ResultSetTableModel rst = new ResultSetTableModel();
        String showTblCmd = "";
   
        String url,drv;
        url = "";
        drv = "";
            
        switch(currentConnection.ConnectionType)
        {
            case "MySQL":
                url = "jdbc:mysql://" + currentConnection.Server + "/" + currentConnection.Database;
                drv = "com.mysql.jdbc.Driver";
                break;
            case "SQL Server":
                if (currentConnection.Port.equals(""))
                {
                    url = "jdbc:sqlserver://" + currentConnection.Server  + ";DatabaseName=" + currentConnection.Database;
                }
                else
                {
                    url = "jdbc:sqlserver://" + currentConnection.Server  + ":" + currentConnection.Port + ";DatabaseName=" + currentConnection.Database;
                }
                drv = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                break;
            case "SQLite":
                url = "jdbc:sqlite:" + currentConnection.FileLocation;
                drv = "org.sqlite.JDBC";
                break;
        }
        
        try
        {
            Class.forName(drv);
        }
        catch (Exception e)
        {}
      
        
        try {
            
           
            if (currentConnection.ConnectionType.equals("SQLite")) {
                con = DriverManager.getConnection(url);
                st = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);}
            else {
                con = DriverManager.getConnection(url, currentConnection.UserName, currentConnection.UserPassword);
                st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }
            con = DriverManager.getConnection(url, currentConnection.UserName, currentConnection.UserPassword);
            
            
            rs = st.executeQuery(this.currentTextArea.getText());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            lblStatus.setText("Status: Query Ran @ " + dateFormat.format(date));

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"ERROR: " + ex);
        }
        
        try 
        {
            this.jTable1.setModel(ResultSetTableModel.resultSetToTableModel(rs));
            this.jTable1.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
            this.lblRecords.setText("0/" + Integer.toString(jTable1.getRowCount()));
            TableColumnAdjuster tca = new TableColumnAdjuster(jTable1);
            tca.adjustColumns();
        }
        catch (Exception e)
        {}
        finally
        {
                        try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
                
                        //this.jTable1.setModel(ResultSetTableModel.buildTableModel(rs));

            } catch (SQLException ex) {

            }
        }

        
        }
        
    private void loadcsvData(String csvText)
        {
        
        ResultSetTableModel rst = new ResultSetTableModel();
        
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        lblStatus.setText("Status: Loaded CSV @ " + dateFormat.format(date));
        
        try 
        {
            this.jTable1.setModel(ResultSetTableModel.csvToTableModel(csvText));
            this.jTable1.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
            this.lblRecords.setText("0/" + Integer.toString(jTable1.getRowCount()));
            TableColumnAdjuster tca = new TableColumnAdjuster(jTable1);
            tca.adjustColumns();
        }
        catch (Exception e)
        {}
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        rSyntaxTextArea1 = new org.fife.ui.rsyntaxtextarea.RSyntaxTextArea();
        mnuTabPopUp = new javax.swing.JPopupMenu();
        mnuCloseTab = new javax.swing.JMenuItem();
        mnuNewTab = new javax.swing.JMenuItem();
        mnuRenameTab = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jProgressBar1 = new javax.swing.JProgressBar();
        lblStatus = new javax.swing.JLabel();
        lblRecords = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        mnuImport = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        mnuConnMan = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        btnAddTab = new javax.swing.JMenuItem();
        btnRemoveTab = new javax.swing.JMenuItem();
        btnRenameTab = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        rSyntaxTextArea1.setColumns(20);
        rSyntaxTextArea1.setRows(5);
        jScrollPane2.setViewportView(rSyntaxTextArea1);

        mnuCloseTab.setText("Close");
        mnuCloseTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCloseTabActionPerformed(evt);
            }
        });
        mnuTabPopUp.add(mnuCloseTab);

        mnuNewTab.setText("New");
        mnuNewTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuNewTabActionPerformed(evt);
            }
        });
        mnuTabPopUp.add(mnuNewTab);

        mnuRenameTab.setText("Rename");
        mnuRenameTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuRenameTabActionPerformed(evt);
            }
        });
        mnuTabPopUp.add(mnuRenameTab);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("jSQLRunner v0.8");
        setFont(new java.awt.Font("Calibri", 0, 10)); // NOI18N
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jToolBar1.setRollover(true);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsqlrunner/images/media-playback-start.png"))); // NOI18N
        jButton1.setText("Run");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setIconTextGap(6);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsqlrunner/images/gnome-app-install-star.png"))); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setIconTextGap(0);
        jButton3.setLabel("Run Action");
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);
        jToolBar1.add(jSeparator2);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jsqlrunner/images/document-save-as.png"))); // NOI18N
        jButton2.setText("Export to:");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Delimited (Pipe)", "CSV", "XML", "MultiSet" }));
        jToolBar1.add(jComboBox1);

        jSplitPane1.setDividerLocation(180);

        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ListBox_Clicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        jSplitPane2.setDividerLocation(191);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jTable1.setAutoCreateRowSorter(true);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jTable1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                jTable1CaretPositionChanged(evt);
            }
        });
        jTable1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTable1PropertyChange(evt);
            }
        });
        jTable1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTable1KeyReleased(evt);
            }
        });
        jScrollPane3.setViewportView(jTable1);

        jSplitPane2.setRightComponent(jScrollPane3);

        jTabbedPane1.setComponentPopupMenu(mnuTabPopUp);
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });
        jSplitPane2.setLeftComponent(jTabbedPane1);

        jSplitPane1.setRightComponent(jSplitPane2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 842, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE)
        );

        lblStatus.setText("Status: ");

        lblRecords.setText("0/0");

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        mnuImport.setText("Import");
        mnuImport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuImportActionPerformed(evt);
            }
        });
        fileMenu.add(mnuImport);
        fileMenu.add(jSeparator1);

        mnuConnMan.setText("Connections");
        mnuConnMan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuConnManActionPerformed(evt);
            }
        });
        fileMenu.add(mnuConnMan);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Edit");

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        jMenu1.setLabel("Tabs");

        btnAddTab.setText("Add");
        btnAddTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTabActionPerformed(evt);
            }
        });
        jMenu1.add(btnAddTab);

        btnRemoveTab.setText("Remove Current");
        btnRemoveTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveTabActionPerformed(evt);
            }
        });
        jMenu1.add(btnRemoveTab);

        btnRenameTab.setText("Rename Current");
        btnRenameTab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRenameTabActionPerformed(evt);
            }
        });
        jMenu1.add(btnRenameTab);

        menuBar.add(jMenu1);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        aboutMenuItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                aboutMenuItemMouseReleased(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblRecords, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblStatus)
                        .addComponent(lblRecords))
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void aboutMenuItemMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_aboutMenuItemMouseReleased

        // TODO add your handling code here:
        JOptionPane.showMessageDialog(rootPane, "jSQLRunner v0.8");
    }//GEN-LAST:event_aboutMenuItemMouseReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            loadData();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ListBox_Clicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListBox_Clicked
        
        if (this.currentTextArea.getText().isEmpty() && this.jTabbedPane1.getComponentCount() == 1)
        {
           jTabbedPane1.setTitleAt(jTabbedPane1.getSelectedIndex(), this.jList1.getSelectedValue().toString());
        }
        else
        {
           newTab(this.jList1.getSelectedValue().toString());
        }
        
        if (currentConnection.ConnectionType.equals("MySQL")){
            this.currentTextArea.setText("Select * from " + this.jList1.getSelectedValue().toString() + " limit 1000");        
        }
        else{
            this.currentTextArea.setText("Select TOP 1000 * from " + this.jList1.getSelectedValue().toString()); 
        }
        
        switch(currentConnection.ConnectionType)
        {
            case "MySQL":
                this.currentTextArea.setText("Select * from " + this.jList1.getSelectedValue().toString() + " limit 1000");
                break;
            case "SQL Server":
                this.currentTextArea.setText("Select TOP 1000 * from " + this.jList1.getSelectedValue().toString());
                break;
            case "SQLite":
                this.currentTextArea.setText("Select * from " + this.jList1.getSelectedValue().toString() + " limit 1000");
                break;
        }
                

        this.currentTextArea.repaint();
        this.loadData();
    }//GEN-LAST:event_ListBox_Clicked

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
       
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int fcChoice = fc.showOpenDialog(this);
        
        if (fcChoice == JFileChooser.APPROVE_OPTION)
        {
            FileUtils srnUtil = new FileUtils();
            this.currentTextArea.setText(srnUtil.readFile(fc.getSelectedFile().toString()));
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int fcChoice = fc.showSaveDialog(this);
        
        if (fcChoice == JFileChooser.APPROVE_OPTION)
        {
            FileUtils srnUtil = new FileUtils();
            srnUtil.writeFile(fc.getSelectedFile().toString(), this.currentTextArea.getText());
        }       
    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
            switch (this.jComboBox1.getSelectedItem().toString()) {
            case "CSV":  sendToDelimited(",");
                     break;
            case "Delimited (Pipe)":  sendToDelimited("|");
                     break;
            case "MultiSet":  JOptionPane.showMessageDialog(this, "MultiSet Not Implemented");
                     break;
            default: sendToDelimited(",");
                     break;
        }

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
                
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String showTblCmd = "";
   
        String url,drv;
        url = "";
        drv = "";
            
        switch(currentConnection.ConnectionType)
        {
            case "MySQL":
                url = "jdbc:mysql://" + currentConnection.Server + "/" + currentConnection.Database;
                drv = "com.mysql.jdbc.Driver";
                break;
            case "SQL Server":
                if (currentConnection.Database.equals(""))
                {
                    url = "jdbc:sqlserver://" + currentConnection.Server + ";DatabaseName=" + currentConnection.Database;
                }
                else
                {
                    url = "jdbc:sqlserver://" + currentConnection.Server + ":" + currentConnection.Port + ";DatabaseName=" + currentConnection.Database;
                }
                drv = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                break;
            case "SQLite":
                url = "jdbc:sqlite:" + currentConnection.FileLocation;
                drv = "org.sqlite.JDBC";
                break;
        }
        
        try
        {
            Class.forName(drv);
        }
        catch (Exception e)
        {}
      
        
        try {
            
           
            if (currentConnection.ConnectionType.equals("SQLite")) {
                con = DriverManager.getConnection(url);
                st = con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);}
            else {
                con = DriverManager.getConnection(url, currentConnection.UserName, currentConnection.UserPassword);
                st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            }
            con = DriverManager.getConnection(url, currentConnection.UserName, currentConnection.UserPassword);
            
            int recNo = st.executeUpdate(this.currentTextArea.getText());
            
            JOptionPane.showMessageDialog(this, recNo + " Records Modified");
            

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,"ERROR: " + ex);
        }
        
        try 
        {
            this.jTable1.setModel(ResultSetTableModel.resultSetToTableModel(rs));
            this.jTable1.setAutoResizeMode(jTable1.AUTO_RESIZE_OFF);
            TableColumnAdjuster tca = new TableColumnAdjuster(jTable1);
            tca.adjustColumns();
        }
        catch (Exception e)
        {}
        finally
        {
                        try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
                
                        //this.jTable1.setModel(ResultSetTableModel.buildTableModel(rs));

            } catch (SQLException ex) {

            }
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAddTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTabActionPerformed
        newTab("");
    }//GEN-LAST:event_btnAddTabActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        if (jTabbedPane1.getTabCount() > 0)
        {
            this.currentTextArea = (RSyntaxTextArea)jTabbedPane1.getComponent(jTabbedPane1.getSelectedIndex());
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void btnRenameTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRenameTabActionPerformed
        String newName = JOptionPane.showInputDialog(this, "Enter new name","Rename Tab",JOptionPane.INFORMATION_MESSAGE);
        if (!newName.equals(""))
        {
            jTabbedPane1.setTitleAt(jTabbedPane1.getSelectedIndex(), newName);
        }
    }//GEN-LAST:event_btnRenameTabActionPerformed

    private void btnRemoveTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveTabActionPerformed
        if (jTabbedPane1.getTabCount() > 1)
        {
            jTabbedPane1.removeTabAt(jTabbedPane1.getSelectedIndex());
            jTabbedPane1.setSelectedIndex(jTabbedPane1.getTabCount());
        }
    }//GEN-LAST:event_btnRemoveTabActionPerformed

    private void mnuCloseTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCloseTabActionPerformed
        if (jTabbedPane1.getTabCount() > 1)
        {
            jTabbedPane1.removeTabAt(jTabbedPane1.getSelectedIndex());
            jTabbedPane1.setSelectedIndex(jTabbedPane1.getTabCount());
            this.currentTextArea = (RSyntaxTextArea)jTabbedPane1.getComponent(jTabbedPane1.getSelectedIndex());
        }          
    }//GEN-LAST:event_mnuCloseTabActionPerformed

    private void mnuRenameTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuRenameTabActionPerformed
        String newName = JOptionPane.showInputDialog(this, "Enter new name","Rename Tab",JOptionPane.INFORMATION_MESSAGE);
        if (!newName.equals(""))
        {
            jTabbedPane1.setTitleAt(jTabbedPane1.getSelectedIndex(), newName);
        }
    }//GEN-LAST:event_mnuRenameTabActionPerformed

    private void mnuNewTabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuNewTabActionPerformed
        this.newTab("");
    }//GEN-LAST:event_mnuNewTabActionPerformed

    private void mnuConnManActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuConnManActionPerformed
        waitDBChange = true;
        connMan.setVisible(true);
    }//GEN-LAST:event_mnuConnManActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        if (this.waitDBChange)
        {
            try
            {
                waitDBChange = false;
                currentConnection = connMan.getSelectedConnection();
                loadTables();
            } catch (Exception e) {}
        }
    }//GEN-LAST:event_formWindowGainedFocus

    private void jTable1CaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTable1CaretPositionChanged
        //this.lblRecords.setText(Integer.toString(jTable1.getSelectedRow()) + "/" + Integer.toString(jTable1.getRowCount()));
    }//GEN-LAST:event_jTable1CaretPositionChanged

    private void jTable1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTable1PropertyChange
        //this.lblRecords.setText(Integer.toString(jTable1.getSelectedRow()) + "/" + Integer.toString(jTable1.getRowCount()));
    }//GEN-LAST:event_jTable1PropertyChange

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        this.lblRecords.setText(Integer.toString(jTable1.getSelectedRow() + 1) + "/" + Integer.toString(jTable1.getRowCount()));
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable1KeyReleased
        this.lblRecords.setText(Integer.toString(jTable1.getSelectedRow() + 1) + "/" + Integer.toString(jTable1.getRowCount()));
    }//GEN-LAST:event_jTable1KeyReleased

    private void mnuImportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuImportActionPerformed
        
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int fcChoice = fc.showOpenDialog(this);
        
        if (fcChoice == JFileChooser.APPROVE_OPTION)
        {
            FileUtils srnUtil = new FileUtils();
            loadcsvData(srnUtil.readFile(fc.getSelectedFile().toString()));
            
        }
    }//GEN-LAST:event_mnuImportActionPerformed

    private void sendToDelimited(String delimiter){
        
        int columnCount = this.jTable1.getColumnCount();
        int currentColumn;
        int cellx, celly;
        JFileChooser fc = new JFileChooser();
        FileWriter fw;
        TableModel tm = this.jTable1.getModel();
        
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int fcChoice = fc.showSaveDialog(this);
        
        if (fcChoice == JFileChooser.APPROVE_OPTION)
        {
            try{
                fw = new FileWriter(fc.getSelectedFile().toString());   
                //Write the column headers
                for (currentColumn = 0; currentColumn < columnCount; currentColumn++){
                    fw.write(jTable1.getColumnName(currentColumn).toString());
                    if (currentColumn < columnCount - 1) {fw.write(delimiter);}
                }
                fw.write("\r\n");
                //Write the rest of the data
                for (cellx = 0; cellx < tm.getRowCount(); cellx++){
                    for (celly = 0; celly < tm.getColumnCount(); celly++){
                        if(tm.getValueAt(cellx, celly) != null){
                            fw.write(tm.getValueAt(cellx, celly).toString().replace(delimiter,""));                            
                        } else {
                            fw.append("");
                        }
                        if(celly < tm.getColumnCount() - 1) {fw.write(delimiter);}
                    }
                    fw.write("\r\n");
                }
                 
                fw.flush();
                fw.close();
            } catch (IOException ex){
                JOptionPane.showMessageDialog(this, "Cannot write to file" + ex.getMessage().toString());
            }
        }
        
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
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMain().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem btnAddTab;
    private javax.swing.JMenuItem btnRemoveTab;
    private javax.swing.JMenuItem btnRenameTab;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lblRecords;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem mnuCloseTab;
    private javax.swing.JMenuItem mnuConnMan;
    private javax.swing.JMenuItem mnuImport;
    private javax.swing.JMenuItem mnuNewTab;
    private javax.swing.JMenuItem mnuRenameTab;
    private javax.swing.JPopupMenu mnuTabPopUp;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private org.fife.ui.rsyntaxtextarea.RSyntaxTextArea rSyntaxTextArea1;
    private javax.swing.JMenuItem saveAsMenuItem;
    // End of variables declaration//GEN-END:variables
}
