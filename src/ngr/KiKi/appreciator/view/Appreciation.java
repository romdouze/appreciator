/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator.view;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author KiKi
 */
public class Appreciation
{

	private final String text;
	private final Map<Integer, Constraint> constraints;

	public Appreciation (String s)
	{
		text = s;
		constraints = new HashMap<> ();
	}

	public String getText ()
	{
		return text;
	}

	public boolean isWithin (Integer index, Double n)
	{
		return constraints.get (index).isWithin (n);
	}

	public void setConstraint (Integer index, Double inf, Double sup)
	{
		constraints.put (index, new Constraint (inf, sup));
	}

	private class Constraint
	{

		private final Double inf;
		private final Double sup;

		public Constraint (Double a, Double b)
		{
			inf = a;
			sup = b;
		}

		public boolean isWithin (Double n)
		{
			return n >= inf && n <= sup;
		}

		public Double getSup ()
		{
			return sup;
		}

		public Double getInf ()
		{
			return inf;
		}
	}
}
