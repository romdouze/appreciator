/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextArea;
import ngr.KiKi.appreciator.data.Student;
import ngr.KiKi.appreciator.data.Utils;

/**
 *
 * @author KiKi
 */
public class AppreciationRenderer extends JTextArea
{

	private final Appreciation appreciation;
	private final JPanelSingleView parent;

	public AppreciationRenderer (JPanelSingleView p, Appreciation a, Student st)
	{
		super ();
		String tmp = a.getText ().replace ("X", st.getFirstname ());
		setText (tmp.replace ("Y", st.getFirstname ()));
		setLineWrap (true);
		setWrapStyleWord (true);
		setFont (Font.decode ("Tahoma-13"));
		setMargin (new Insets (5, 5, 5, 5));
		setRows (2);
		setEditable (false);
		setCursor (new Cursor (Cursor.HAND_CURSOR));

		addMouseListener (new MouseAdapter ()
		{

			@Override
			public void mouseClicked (MouseEvent me)
			{
				String text = getText ();
				Utils.sendToClipboard (text);
				parent.setCustomAreaText (text);
				parent.setStatus (text);
			}

			@Override
			public void mouseEntered (MouseEvent me)
			{
				setBackground (Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited (MouseEvent me)
			{
				setBackground (Color.WHITE);
			}
		});

		parent = p;
		appreciation = a;
	}

	public Appreciation getAppreciation ()
	{
		return appreciation;
	}

	@Override
	public Dimension getPreferredSize ()
	{
		return new Dimension (450, 30);
	}
}
