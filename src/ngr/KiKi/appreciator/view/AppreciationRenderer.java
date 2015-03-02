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
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JTextArea;
import ngr.KiKi.appreciator.JFrameMain;
import ngr.KiKi.appreciator.data.Student;

/**
 *
 * @author KiKi
 */
public class AppreciationRenderer extends JTextArea implements MouseListener
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

		addMouseListener (this);

		parent = p;
		appreciation = a;
	}

	@Override
	public void mouseClicked (MouseEvent me)
	{
		String text = getText ();
		StringSelection ss = new StringSelection (text);
		Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		clpbrd.setContents (ss, null);
		parent.setCustomAreaText (text);
		JFrameMain.setStatus (text);
	}

	@Override
	public void mousePressed (MouseEvent me)
	{

	}

	@Override
	public void mouseReleased (MouseEvent me)
	{

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

	@Override
	public Dimension getPreferredSize ()
	{
		return new Dimension (450, 30);
	}
}
