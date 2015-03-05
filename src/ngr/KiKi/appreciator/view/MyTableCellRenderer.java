/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator.view;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author KiKi
 */
public class MyTableCellRenderer extends DefaultTableCellRenderer
{

	@Override
	public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col)
	{

		//Cells are by default rendered as a JLabel.
		JLabel l = (JLabel) super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, col);

		//Get the status for the current row.
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel ();

		//Return the JLabel which renders the cell.
		return l;
	}
}
