/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

/**
 *
 * @author KiKi
 */
public class AppreciationCellRenderer extends JTextArea implements ListCellRenderer
{
	
	private static final Color HIGHLIGHT_COLOR = Color.LIGHT_GRAY;
	
	public AppreciationCellRenderer ()
	{
		setOpaque (true);
//		setBorder (new LineBorder (Color.BLACK));
		setLineWrap (true);
		setWrapStyleWord (true);
		setFont (Font.decode ("Tahoma-13"));
		setMargin (new Insets (5, 5, 5, 5));
		setRows (2);
	}
	
	@Override
	public Component getListCellRendererComponent (JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus)
	{
		Appreciation entry = (Appreciation) value;
		String text = entry.getText ();
		setText (text);
		
		if (isSelected)
		{
			setBackground (HIGHLIGHT_COLOR);
			setForeground (Color.white);
		}
		else
		{
			setBackground (Color.white);
			setForeground (Color.black);
		}
		
		JScrollPane scrollpane = new JScrollPane (this);
		scrollpane.setWheelScrollingEnabled (true);
		return scrollpane;
	}
}
