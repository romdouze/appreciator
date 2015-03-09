/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator.data;

import java.util.ArrayList;

/**
 *
 * @author KiKi
 */
public class Student
{

	private int id;
	private String fullname;
	private String lastname = "";
	private String firstname = "";
	private Double[] notes;
	private Double mean;
	private String appreciation;
	private String previousAppreciation;
	private int behaviour;
	private int focus;

	public Student ()
	{
		fullname = "";
		notes = new Double[]
		{
			0.
		};
		mean = 0.;
		appreciation = "";
		previousAppreciation = "";
	}

	public Student (int i, String fn, Double[] n, Double m, String a)
	{
		id = i;
		fullname = fn;
		splitNames ();
		notes = n;
		mean = m;
		appreciation = a;
		previousAppreciation = "";
		behaviour = 2;
		focus = 2;
	}

	private void splitNames ()
	{
		String[] splitted = fullname.split (" ");
		switch (splitted.length)
		{
			case 2:
				lastname = splitted[0];
				firstname = splitted[1];
				break;
			case 1:
				lastname = firstname = fullname;
				break;
			default:
				int i;
				for (i = 0; i < splitted.length - 1; i++)
					lastname += splitted[i] + " ";
				firstname = splitted[i];
		}

		firstname = firstname.trim ();
		lastname = lastname.trim ();
	}

	public int getId ()
	{
		return id;
	}

	public String getfullname ()
	{
		return fullname;
	}

	public void setfullname (String fullname)
	{
		this.fullname = fullname;
	}

	public String getName ()
	{
		return lastname;
	}

	public void setName (String name)
	{
		this.lastname = name;
	}

	public String getFirstname ()
	{
		return firstname;
	}

	public void setFirstname (String firstname)
	{
		this.firstname = firstname;
	}

	public Double[] getNotes ()
	{
		return notes;
	}

	public void setNotes (Double[] notes)
	{
		this.notes = notes;
	}

	public Double getMean ()
	{
		return mean;
	}

	public void setMean (Double average)
	{
		this.mean = average;
	}

	public String getAppreciation ()
	{
		return appreciation;
	}

	public void setAppreciation (String appreciation)
	{
		this.appreciation = appreciation;
	}

	public String getPreviousAppreciation ()
	{
		return previousAppreciation;
	}

	public void setPreviousAppreciation (String previous)
	{
		previousAppreciation = previous;
	}

	public int getBehaviour ()
	{
		return behaviour;
	}

	public void setBehaviour (int behaviour)
	{
		this.behaviour = behaviour;
	}

	public int getFocus ()
	{
		return focus;
	}

	public void setFocus (int focus)
	{
		this.focus = focus;
	}
}
