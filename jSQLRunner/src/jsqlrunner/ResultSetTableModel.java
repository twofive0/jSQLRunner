/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsqlrunner;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author devin
 */
public class ResultSetTableModel {
    
    public static TableModel resultSetToTableModel(ResultSet rs) {
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Get the column names
            for (int column = 0; column < numberOfColumns; column++) {
                columnNames.addElement(metaData.getColumnLabel(column + 1));
            }

            // Get all rows.
            Vector rows = new Vector();

            while (rs.next()) {
                Vector newRow = new Vector();

                for (int i = 1; i <= numberOfColumns; i++) {
                    newRow.addElement(rs.getObject(i));
                }

                rows.addElement(newRow);
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }

    public static TableModel csvToTableModel(String csvText) {
        try {
            //ResultSetMetaData metaData = rs.getMetaData();
            //int numberOfColumns = metaData.getColumnCount();
            Vector columnNames = new Vector();

            // Split the csv into parts
            String[] lines = csvText.split(System.getProperty("line.separator"));
            
            if (lines.length == 0) return null;

            String[] header = lines[0].split(",");
            if (header.length == 0) return null;
            
            // Get the column names
            for (int column = 0; column < header.length; column++) {
                columnNames.addElement(header[column]);
            }

            // Get all rows.
            Vector rows = new Vector();

            for (int rowno = 1; rowno < lines.length; rowno++){
                Vector newRow = new Vector();
                String[] currentRow = lines[rowno].split(",");

                if (currentRow.length == header.length)
                {
                    for (int i = 0; i < header.length; i++) {
                        newRow.addElement(currentRow[i]);
                    }

                    rows.addElement(newRow);
                }
            }

            return new DefaultTableModel(rows, columnNames);
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }    
}
