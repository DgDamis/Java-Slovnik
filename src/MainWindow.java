
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author maturita
 */
public class MainWindow extends javax.swing.JFrame {

    private DefaultTableModel model;
    //private TableRowSorter sorter; 
    //private final DefaultTableModel modelFetch;
    private Connection spojeni;
    private String filteredString = ".*.*";
    //private String lastWord = "";
    //private String searchRequest = "null";

    
    /**
     * Creates new form MainWindow
     */
    public MainWindow() {
        initComponents();
        //
        //addMouseListener(this);
        /* Broken
        tabulka.getTableHeader().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
        public void mouseClicked(MouseEvent e) {
        int col = tabulka.columnAtPoint(e.getPoint());
        String name = tabulka.getColumnName(col);
        System.out.println("Column index selected " + col + " " + name);
        searchRequest = name;
    }   
        });
         */
        
        //String[] columnNames = { "id", "cs", "en"};
        
        //Inicializace řazení
        tabulka.setAutoCreateRowSorter(true);
        
        //
        model = (DefaultTableModel) tabulka.getModel();
       // sorter = new TableRowSorter(model);
        
       // tabulka = new JTable((TableModel) model);
        //tabulka.setRowSorter(sorter);
        //modelFetch = (DefaultTableModel)
        //modelFetch = new DefaultTableModel(new Object[]{"id", "cesky", "anglicky"},30);
        //modelFetch = new DefaultTableModel();
        
        //DefaultTableModel model = new DefaultTableModel();
        //sorter = new TableRowSorter<DefaultTableModel>(model);
        //tabulka = new JTable(model);
        
        
        if (!dbConnection()) {
            System.exit(0);
        }
        listData(getAllRecords());

    }

    private boolean dbConnection() {
        try {
            spojeni = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/slovnik?useUnicode=true&characterEncoding=utf-8", "root", "");
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Nedošlo k připojení databáze",
                    "Chyba", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private ResultSet getAllRecords() {
        ResultSet vysledky = null;
        try {
            PreparedStatement dotaz = spojeni.prepareStatement("SELECT * FROM slovnicek");
            vysledky = dotaz.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Chyba při komunikaci s databází",
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }
        return vysledky;
    }

    private void listData(ResultSet data) {
        // Inicializace filtrování
            System.out.println(filteredString); // Debug Regex
            /*
            TableRowSorter sorter = new TableRowSorter(model);
            tabulka = new JTable((TableModel) model);
            tabulka.setRowSorter(new TableRowSorter(model));
            System.out.println(RowFilter.regexFilter(filteredString));
            sorter.setRowFilter(RowFilter.regexFilter(filteredString));
            */
            //TableRowSorter sorter = new TableRowSorter(model);
            //sorter.setRowFilter(RowFilter.regexFilter(filteredString));
            
        /* Odstranění všech řádků z tabulky */
        for (int i = tabulka.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
            //modelFetch.removeRow(i);
        }
        try {
            /* Vložení řádků do tabulky a jejich naplnění daty ze získané dynamické sady */
            while (data.next()) {
                int id = data.getInt(1);
                String cesky = data.getString("cs");
                //System.out.println(cesky);
                String anglicky = data.getString("en");
                //System.out.println(isInAlphabetOrder(cesky,anglicky));
                //if(cesky.contains(filteredString) || anglicky.contains(filteredString)){
                model.addRow(new Object[]{id, cesky, anglicky});
                //modelFetch.addRow(new Object[]{id, cesky, anglicky});
                //}
            }
            /*
            switch (searchRequest) {
                case "null": //System.out.println("debug");
                    //model = modelFetch;
                    break;
                case "id":
                    break;
                case "cs": //System.out.println("debug");
                    //tabulka = new JTable(new SampleSortingTableModel(modelFetch, 0));;
                    break;
            }
            */
            /*
            for(int i = 0;i< model.getRowCount();i++){
                System.out.println(model.getValueAt(i, 1));
            }
             */
 

            
            
            
            
            //
            //
            /* Zapnutí nebo vypnutí tlačítek Změnit a Smazat v závislosti na existenci záznamů
            (řádků) v tabulce */
            if (tabulka.getRowCount() > 0) {
                tabulka.setRowSelectionInterval(0, 0);
                /* Označení prvního řádku tabulky */
                update.setEnabled(true);
                delete.setEnabled(true);
            } else {
                update.setEnabled(false);
                delete.setEnabled(false);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Chyba při komunikaci s databází",
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }
    }

    private int insertRecord(String enWord, String csWord) {
        int numRows = 0;
        try {
            PreparedStatement dotaz
                    = spojeni.prepareStatement("INSERT INTO slovnicek (cs, en) VALUES (?, ?)");
            dotaz.setString(1, csWord);
            dotaz.setString(2, enWord);
            numRows = dotaz.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Chyba při komunikaci s databází",
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }
        return numRows;
    }

    private int updateRecord(int id, String enWord, String csWord) {
        int numRows = 0;
        try {
            PreparedStatement dotaz
                    = spojeni.prepareStatement("UPDATE slovnicek SET cs=?, en=? WHERE id=?");
            dotaz.setString(1, csWord);
            dotaz.setString(2, enWord);
            dotaz.setInt(3, id);
            numRows = dotaz.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Chyba při komunikaci s databází",
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }
        return numRows;
    }

    private int deleteRecord(int id) {
        int numRows = 0;
        try {
            PreparedStatement dotaz = spojeni.prepareStatement("DELETE FROM slovnicek WHERE id=?");
            dotaz.setInt(1, id);
            numRows = dotaz.executeUpdate();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Chyba při komunikaci s databází",
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }
        return numRows;
    }

    private ResultSet searchEn(String enWord) {
        ResultSet vysledky = null;
        try {
            PreparedStatement dotaz = spojeni.prepareStatement("SELECT * FROM slovnicek WHERE en=?");
            dotaz.setString(1, enWord);
            vysledky = dotaz.executeQuery();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Chyba při komunikaci s databází",
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }
        return vysledky;
    }

    private Boolean isInAlphabetOrder(String string1, String string2) {
        char[] chStr1 = string1.toCharArray();
        char[] chStr2 = string2.toCharArray();
        int positionOfStr1 = 0;
        for (char c : chStr1) {
            int temp = (int) c;
            int temp_integer = 96; //for lower case
            if (temp <= 122 & temp >= 97) {
                positionOfStr1 = temp - temp_integer;
            }
        }
        for (char c : chStr2) {
            int temp = (int) c;
            int temp_integer = 96; //for lower case
            if (temp <= 122 & temp >= 97) {
                if ((temp - temp_integer) < positionOfStr1) {
                    return true;
                }
            }
        }
        return false;
    }

    private void newFilter(){
        /*
        System.out.println("Nový filter.");
        RowFilter rf = null;
        try{
            rf = RowFilter.regexFilter(filter.getText(),0);
            System.out.print(rf);
        }catch(java.util.regex.PatternSyntaxException e){
            return;
        }
        sorter.setRowFilter(rf);
        sorter.sort();
        */
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tabulka = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        insert = new javax.swing.JButton();
        update = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        search = new javax.swing.JButton();
        filter = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Slovník");

        tabulka.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "cs", "en"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabulka);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        insert.setText("Nový");
        insert.setFocusable(false);
        insert.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        insert.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertActionPerformed(evt);
            }
        });

        update.setText("Změnit");
        update.setEnabled(false);
        update.setFocusable(false);
        update.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        update.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        delete.setText("Smazat");
        delete.setEnabled(false);
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        search.setText("Hledej");
        search.setFocusable(false);
        search.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        search.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });

        filter.setToolTipText("Filtrovaný výraz");
        filter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filterActionPerformed(evt);
            }
        });
        filter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                filterKeyReleased(evt);
            }
        });

        jLabel1.setText("Filter");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(insert)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(update)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delete)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(search))
                    .addComponent(filter, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(103, 103, 103))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(search)
                    .addComponent(delete)
                    .addComponent(update)
                    .addComponent(insert))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(filter)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertActionPerformed
        String[] slova = {"", ""};
        slovaDialog slovaDialog = new slovaDialog(this, true, slova);
        /* Zobrazení dialogového okna metodou showDialog() */
        if (slovaDialog.showDialog().equalsIgnoreCase("OK")) {
            insertRecord(slovaDialog.getAnglicky(), slovaDialog.getCesky());
        }
        listData(this.getAllRecords());
    }//GEN-LAST:event_insertActionPerformed


    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        int id = (int) tabulka.getModel().getValueAt(tabulka.getSelectedRow(), 0);
        String[] slova = {tabulka.getModel().getValueAt(tabulka.getSelectedRow(), 1).toString(),
            tabulka.getModel().getValueAt(tabulka.getSelectedRow(), 2).toString()};
        slovaDialog slovaDialog = new slovaDialog(this, true, slova);
        if (slovaDialog.showDialog().equalsIgnoreCase("OK")) {
            updateRecord(id, slovaDialog.getAnglicky(), slovaDialog.getCesky());
        }
        listData(this.getAllRecords());
    }//GEN-LAST:event_updateActionPerformed

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        int id = (int) tabulka.getModel().getValueAt(tabulka.getSelectedRow(), 0);
        deleteRecord(id);
        listData(this.getAllRecords());
    }//GEN-LAST:event_deleteActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        String hledany = JOptionPane.showInputDialog(this, "Zadej anglické slovo");
        try {
            ResultSet data = searchEn(hledany);
            data.next();
            int id = data.getInt(1);
            String cesky = data.getString("cs");
            String anglicky = data.getString("en");
            JOptionPane.showMessageDialog(this, "Anglicky: \"" + anglicky + "\"\nČesky: \""
                    + cesky + "\"", "Hledané slovo", JOptionPane.PLAIN_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Záznam nebyl nalezen",
                    "Chyba", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_searchActionPerformed

    private void filterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterActionPerformed
        System.out.println("Action");
        filteredString = "";
        filteredString += ".*";
        filteredString += filter.getText();
        filteredString += ".*";
        //listData(getAllRecords());
        newFilter();
    }//GEN-LAST:event_filterActionPerformed

    private void filterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_filterKeyReleased
        System.out.println("Key Typed");
        filteredString = "";
        filteredString += ".*";
        filteredString += filter.getText();
        filteredString += ".*";
        //listData(getAllRecords());
        newFilter();
    }//GEN-LAST:event_filterKeyReleased

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
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton delete;
    private javax.swing.JTextField filter;
    private javax.swing.JButton insert;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JButton search;
    private javax.swing.JTable tabulka;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables

}
