/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

/**
 * Multiline Table Cell Renderer.
 *
 * @author KiKi
 */
public class MultiLineTableCellRenderer extends JPanel
		implements TableCellRenderer
{

	private final List<List<Integer>> rowColHeight = new ArrayList<> ();
	private int highlighted;
	private boolean buttonsVisible;

	private final JTextArea textArea;
	private final JButton button;

	public MultiLineTableCellRenderer ()
	{
		buttonsVisible = false;
		textArea = new JTextArea ();
		textArea.setLineWrap (true);
		textArea.setWrapStyleWord (true);
		textArea.setOpaque (true);
		button = new JButton ("Copier");
		highlighted = -1;
	}

	@Override
	public Component getTableCellRendererComponent (
			JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column)
	{
		textArea.setForeground (table.getForeground ());
		textArea.setBackground (table.getBackground ());

		textArea.setFont (table.getFont ());

		if (row == highlighted)
			textArea.setBackground (Color.LIGHT_GRAY);
		if (isSelected)
		{
			textArea.setForeground (table.getSelectionForeground ());
			textArea.setBackground (table.getSelectionBackground ());
		}

		if (hasFocus)
		{
			textArea.setBorder (UIManager.getBorder ("Table.focusCellHighlightBorder"));
			if (table.isCellEditable (row, column))
			{
				textArea.setForeground (UIManager.getColor ("Table.focusCellForeground"));
				textArea.setBackground (UIManager.getColor ("Table.focusCellBackground"));
			}
		}
		else
			textArea.setBorder (new EmptyBorder (1, 2, 1, 2));
		if (value != null)
			textArea.setText (value.toString ());
		else
			textArea.setText ("");

		setLayout (new BorderLayout ());
		add (textArea, BorderLayout.CENTER);
		if (buttonsVisible)
			if (column == 1)
				add (button, BorderLayout.EAST);
			else
				remove (button);

		adjustRowHeight (table, row, column);
		return this;
	}

	public void setHighlightedRow (int c)
	{
		highlighted = c;
	}

	public void setButtonsvisible (boolean b)
	{
		buttonsVisible = b;
		remove (button);
	}

	/**
	 * Calculate the new preferred height for a given row, and sets the height on the table.
	 */
	private void adjustRowHeight (JTable table, int row, int column)
	{
		//The trick to get this to work properly is to set the width of the column to the 
		//textarea. The reason for this is that getPreferredSize(), without a width tries 
		//to place all the text in one line. By setting the size with the with of the column, 
		//getPreferredSize() returnes the proper height which the row should have in
		//order to make room for the text.
		int cWidth = table.getTableHeader ().getColumnModel ().getColumn (column).getWidth ();
		textArea.setSize (new Dimension (cWidth, 1000));
		int prefH = textArea.getPreferredSize ().height;
		while (rowColHeight.size () <= row)
			rowColHeight.add (new ArrayList<> (column));
		List<Integer> colHeights = rowColHeight.get (row);
		while (colHeights.size () <= column)
			colHeights.add (0);
		colHeights.set (column, prefH);
		int maxH = prefH;
		for (Integer colHeight : colHeights)
			if (colHeight > maxH)
				maxH = colHeight;
		if (table.getRowHeight (row) != maxH)
			table.setRowHeight (row, maxH);
	}
}
