/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import ngr.KiKi.appreciator.view.Appreciation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 *
 * @author KiKi
 */
public class XLSHelper
{

	private static XLSHelper sInstance;
	private HSSFWorkbook book = null;
	private Sheet sheet = null;
	private File file = null;
	private int nbNotes;

	private XLSHelper ()
	{

	}

	public static XLSHelper getInstance ()
	{
		if (sInstance == null)
			sInstance = new XLSHelper ();

		return sInstance;
	}

	public boolean openBook (File f)
	{
		file = f;
		if (file.isFile ())
			try (FileInputStream input = new FileInputStream (file))
			{
				book = new HSSFWorkbook (input);
			}
			catch (IOException e)
			{
				System.out.println (e.getMessage ());
				return false;
			}
		if (book == null)
			return false;

		sheet = book.getSheet (Indices.SHEET);

		initIndices ();

		return sheet != null;
	}

	public boolean loadSecondaryAppreciations (File file, ArrayList<Student> list)
	{
		HSSFWorkbook sbook = null;
		Sheet ssheet;

		if (file != null && file.isFile ())
			try (FileInputStream input = new FileInputStream (file))
			{
				sbook = new HSSFWorkbook (input);
			}
			catch (IOException e)
			{
				System.out.println (e.getMessage ());
				return false;
			}
		if (sbook == null)
			return false;

		ssheet = sbook.getSheet (Indices.SHEET);

		if (ssheet == null)
			return false;

		Iterator<Student> it = list.iterator ();
		while (it.hasNext ())
		{
			Student student = it.next ();
			String appreciation = "";
			for (int i = 0; i < 150 && appreciation.isEmpty (); i++)
				if (rowExists (ssheet, i + Indices.FIRST_ROW))
				{
					Row r = ssheet.getRow (i + Indices.FIRST_ROW);
					if (r.getCell (Indices.STUDENT_COLUMN).getStringCellValue ().equals (student.getfullname ()))
						appreciation = r.getCell (Indices.APPRECIATION_COLUMN).getStringCellValue ();
				}

			if (!appreciation.isEmpty ())
				student.setPreviousAppreciation (appreciation);
		}

		return true;
	}

	public ArrayList<Appreciation> loadAppreciations (String path)
	{
		ArrayList<Appreciation> list = new ArrayList<> ();

		HSSFWorkbook abook = null;
		Sheet asheet;
		File aFile = new File (path);

		if (aFile.isFile ())
			try (FileInputStream input = new FileInputStream (aFile))
			{
				abook = new HSSFWorkbook (input);
			}
			catch (IOException e)
			{
				System.out.println (e.getMessage ());
				return list;
			}

		if (abook == null)
			return list;

		asheet = abook.getSheet (Indices.APPRECIATIONS_FILE_SHEET);

		if (asheet == null)
			return list;

		for (int i = 0; i < Utils.MAX_APPRECIATIONS; i++)
			if (rowExists (asheet, i))
			{
				Row r = asheet.getRow (i);
				Cell c = r.getCell (Indices.APPRECIATIONS_FILE_COLUMN);
				if (c != null && c.getCellType () == Cell.CELL_TYPE_STRING)
				{
					String s = c.getStringCellValue ();
					if (s != null && s.startsWith (Utils.LEADING_STUFF))
					{
						Appreciation a = new Appreciation (Utils.removeLead (s));
						Double T1inf = r.getCell (Indices.APPRECIATIONS_FILE_T[0]) != null && r.getCell (Indices.APPRECIATIONS_FILE_T[0]).getCellType () == Cell.CELL_TYPE_NUMERIC ? r.getCell (Indices.APPRECIATIONS_FILE_T[0]).getNumericCellValue () : 0.;
						Double T1sup = r.getCell (Indices.APPRECIATIONS_FILE_T[0] + 1) != null && r.getCell (Indices.APPRECIATIONS_FILE_T[0] + 1).getCellType () == Cell.CELL_TYPE_NUMERIC ? r.getCell (Indices.APPRECIATIONS_FILE_T[0] + 1).getNumericCellValue () : 20.;
						Double T2inf = r.getCell (Indices.APPRECIATIONS_FILE_T[1]) != null && r.getCell (Indices.APPRECIATIONS_FILE_T[1]).getCellType () == Cell.CELL_TYPE_NUMERIC ? r.getCell (Indices.APPRECIATIONS_FILE_T[1]).getNumericCellValue () : 0.;
						Double T2sup = r.getCell (Indices.APPRECIATIONS_FILE_T[1] + 1) != null && r.getCell (Indices.APPRECIATIONS_FILE_T[1] + 1).getCellType () == Cell.CELL_TYPE_NUMERIC ? r.getCell (Indices.APPRECIATIONS_FILE_T[1] + 1).getNumericCellValue () : 20.;
						Double T3inf = r.getCell (Indices.APPRECIATIONS_FILE_T[2]) != null && r.getCell (Indices.APPRECIATIONS_FILE_T[2]).getCellType () == Cell.CELL_TYPE_NUMERIC ? r.getCell (Indices.APPRECIATIONS_FILE_T[2]).getNumericCellValue () : 0.;
						Double T3sup = r.getCell (Indices.APPRECIATIONS_FILE_T[2] + 2) != null && r.getCell (Indices.APPRECIATIONS_FILE_T[2] + 1).getCellType () == Cell.CELL_TYPE_NUMERIC ? r.getCell (Indices.APPRECIATIONS_FILE_T[2] + 1).getNumericCellValue () : 20.;
						Double Ainf = r.getCell (Indices.APPRECIATIONS_FILE_A) != null && r.getCell (Indices.APPRECIATIONS_FILE_A).getCellType () == Cell.CELL_TYPE_NUMERIC ? r.getCell (Indices.APPRECIATIONS_FILE_A).getNumericCellValue () : 0.;
						Double Asup = r.getCell (Indices.APPRECIATIONS_FILE_A + 1) != null && r.getCell (Indices.APPRECIATIONS_FILE_A + 1).getCellType () == Cell.CELL_TYPE_NUMERIC ? r.getCell (Indices.APPRECIATIONS_FILE_A + 1).getNumericCellValue () : 20.;
						Double Cinf = r.getCell (Indices.APPRECIATIONS_FILE_C) != null && r.getCell (Indices.APPRECIATIONS_FILE_C).getCellType () == Cell.CELL_TYPE_NUMERIC ? r.getCell (Indices.APPRECIATIONS_FILE_C).getNumericCellValue () : 0.;
						Double Csup = r.getCell (Indices.APPRECIATIONS_FILE_C + 1) != null && r.getCell (Indices.APPRECIATIONS_FILE_C + 1).getCellType () == Cell.CELL_TYPE_NUMERIC ? r.getCell (Indices.APPRECIATIONS_FILE_C + 1).getNumericCellValue () : 20.;
						Double Iinf = r.getCell (Indices.APPRECIATIONS_FILE_I) != null && r.getCell (Indices.APPRECIATIONS_FILE_I).getCellType () == Cell.CELL_TYPE_NUMERIC ? r.getCell (Indices.APPRECIATIONS_FILE_I).getNumericCellValue () : 0.;
						Double Isup = r.getCell (Indices.APPRECIATIONS_FILE_I + 1) != null && r.getCell (Indices.APPRECIATIONS_FILE_I + 1).getCellType () == Cell.CELL_TYPE_NUMERIC ? r.getCell (Indices.APPRECIATIONS_FILE_I + 1).getNumericCellValue () : 20.;
						a.setConstraint (Indices.APPRECIATIONS_FILE_T[0], T1inf, T1sup);
						a.setConstraint (Indices.APPRECIATIONS_FILE_T[1], T2inf, T2sup);
						a.setConstraint (Indices.APPRECIATIONS_FILE_T[2], T3inf, T3sup);
						a.setConstraint (Indices.APPRECIATIONS_FILE_A, Ainf, Asup);
						a.setConstraint (Indices.APPRECIATIONS_FILE_C, Cinf, Csup);
						a.setConstraint (Indices.APPRECIATIONS_FILE_I, Iinf, Isup);
						list.add (a);
					}
				}
			}

		return list;
	}

	private int getSize ()
	{
		if (sheet == null)
			return 0;
		int size = 0;
		String s = "";

		for (int i = Indices.FIRST_ROW; i < 150 && size == 0; i++)
			if (rowExists (sheet, i) && (s = sheet.getRow (i).getCell (Indices.STUDENT_COLUMN).getStringCellValue ()).startsWith (Indices.SIZE_CLASS_TAG))
				size = Integer.valueOf (s.split (" ")[2]);

		return size;
	}

	public int getNbNotes ()
	{
		return nbNotes;
	}

	private void initIndices ()
	{
		if (sheet == null)
			return;

		nbNotes = 0;
		Indices.MEAN_COLUMN = 0;
		Indices.APPRECIATION_COLUMN = 0;
		if (rowExists (sheet, Indices.TITLE_ROW))
		{
			Row r = sheet.getRow (Indices.TITLE_ROW);
			int i;
			for (i = 0; i < 20; i++)
				if (r.getCell (i) != null && r.getCell (i).getCellType () == Cell.CELL_TYPE_STRING)
				{
					String s = r.getCell (i).getStringCellValue ();
					if (s.equals (Indices.T1_TAG))
					{
						nbNotes++;
						Indices.QUARTER_COLUMN[0] = i;
					}
					else if (s.equals (Indices.T2_TAG))
					{
						nbNotes++;
						Indices.QUARTER_COLUMN[1] = i;
					}
					else if (s.equals (Indices.T3_TAG))
					{
						nbNotes++;
						Indices.QUARTER_COLUMN[2] = i;
					}
					else if (s.equals (Indices.MEAN_COLUMN_TAG))
						Indices.MEAN_COLUMN = i;
					else if (s.equals (Indices.APPRECIATION_COLUMN_TAG))
						Indices.APPRECIATION_COLUMN = i;
				}
		}
	}

	public Student getStudent (int i)
	{
		return getStudent (i, sheet);
	}

	public ArrayList<Student> loadStudents ()
	{
		ArrayList<Student> list = new ArrayList<> ();
		int size = getSize ();

		for (int i = 0; i < size; i++)
			list.add (getStudent (i, sheet));

		return list;
	}

	private Student getStudent (int id, Sheet sheet)
	{
		if (sheet == null || !rowExists (sheet, id))
			return null;
		Row r = sheet.getRow (id + Indices.FIRST_ROW);
		Double[] notes = new Double[nbNotes];
		for (int i = 0; i < nbNotes; i++)
			notes[i] = r.getCell (Indices.QUARTER_COLUMN[i]).getNumericCellValue ();

		Student student = new Student (id, r.getCell (Indices.STUDENT_COLUMN).getStringCellValue (), notes, r.getCell (Indices.MEAN_COLUMN).getNumericCellValue (), r.getCell (Indices.APPRECIATION_COLUMN).getStringCellValue ());

		return student;
	}

	public void writeAppreciation (Student s)
	{
		if (sheet == null || !rowExists (sheet, s.getId () + Indices.FIRST_ROW))
			return;

		Row r = sheet.getRow (s.getId () + Indices.FIRST_ROW);
		r.getCell (Indices.APPRECIATION_COLUMN).setCellValue (s.getAppreciation ());
	}

//	public void writeStudent (Student s)
//	{
//		if (sheet == null || !rowExists (sheet, s.getId () + Indices.FIRST_ROW))
//			return;
//
//		Row r = sheet.getRow (s.getId () + Indices.FIRST_ROW);
//		r.getCell (Indices.STUDENT_COLUMN).setCellValue (s.getfullname ());
//		r.getCell (Indices.QUARTER_COLUMN[0]).setCellValue (s.getNotes ()[0]);
//		r.getCell (Indices.MEAN_COLUMN).setCellValue (s.getMean ());
//		r.getCell (Indices.APPRECIATION_COLUMN).setCellValue (s.getAppreciation ());
//	}
	public void writeBook ()
	{
		try (FileOutputStream f = new FileOutputStream (file))
		{
			book.write (f);
		}
		catch (IOException e)
		{
		}
	}

	private boolean sheetExists (String s)
	{
		return book.getSheet (s) != null;
	}

	private boolean rowExists (Sheet s, int r)
	{
		return s.getRow (r) != null;
	}

	public HSSFWorkbook getBook ()
	{
		return book;
	}

	public File getFile ()
	{
		return file;
	}

	public static class Indices
	{

		public final static String SHEET = "Appreciations";
		public final static String STUDENT_COLUMN_TAG = "Elèves";
		public final static String QUARTER_COLUMN_TAG = "T";
		public final static String T1_TAG = "T1";
		public final static String T2_TAG = "T2";
		public final static String T3_TAG = "T3";
		public final static String MEAN_COLUMN_TAG = "A";
		public final static String APPRECIATION_COLUMN_TAG = "Appréciations";

		public static int STUDENT_COLUMN = 1;
		public static int[] QUARTER_COLUMN =
		{
			3, 5, 7
		};

		public static int MEAN_COLUMN = 0;
		public static int APPRECIATION_COLUMN = 0;

		public static int APPRECIATIONS_FILE_COLUMN = 0;
		public static String APPRECIATIONS_FILE_SHEET = "Appréciations";
		public static int[] APPRECIATIONS_FILE_T =
		{
			2, 4, 6
		};
		public static int APPRECIATIONS_FILE_A = 8;
		public static int APPRECIATIONS_FILE_C = 10;
		public static int APPRECIATIONS_FILE_I = 12;

		public final static int TITLE_ROW = 1;
		public final static int FIRST_ROW = 3;
		public final static String SIZE_CLASS_TAG = "Effectif";
	}
}
