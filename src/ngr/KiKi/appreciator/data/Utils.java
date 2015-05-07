/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator.data;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 *
 * @author KiKi
 */
public class Utils
{

	public final static String LEADING_STUFF = "  * "; // Balise qui précéde chaque appréciation dans le fichier
	public final static int MAX_APPRECIATIONS = 1500; // Nombre arbitraire de lignes à parcourir dans le fichier

	public static int countOccurences (String s, char c)
	{
		int count = 0;
		for (int i = 0; i < s.length (); i++)
			if (s.charAt (i) == c)
				count++;

		return count;
	}

	public static String removeLead (String s)
	{
		return s.substring (LEADING_STUFF.length ());
	}

	public static void sendToClipboard (String text)
	{
		StringSelection ss = new StringSelection (text);
		Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		clpbrd.setContents (ss, null);
	}

	public static String implementationTitle = "Appreciator";
	public static String builtDate = "";
	public static String builtBy = "Romain Mouchel-Vallon";
}
