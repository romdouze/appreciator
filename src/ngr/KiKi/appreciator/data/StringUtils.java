/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator.data;

/**
 *
 * @author KiKi
 */
public class StringUtils
{

	private static String LEADING_STUFF = "  * ";
	
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
}
