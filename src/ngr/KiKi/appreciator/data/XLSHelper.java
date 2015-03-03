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
				student.setPrevious (appreciation);
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

		for (int i = 0; i < 1500; i++)
			if (rowExists (asheet, i))
			{
				Row r = asheet.getRow (i);
				Cell c = r.getCell (Indices.APPRECIATIONS_FILE_COLUMN);
				if (c != null && c.getCellType () == Cell.CELL_TYPE_STRING)
				{
					String s = c.getStringCellValue ();
					if (s != null && s.startsWith ("  * "))
						list.add (new Appreciation (StringUtils.removeLead (s)));
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
		if (rowExists (sheet, Indices.TITLE_ROW))
		{
			Row r = sheet.getRow (Indices.TITLE_ROW);
			int i;
			for (i = 0; i < Indices.QUARTER_COLUMN.length && r.getCell (Indices.QUARTER_COLUMN[i]).getStringCellValue ().startsWith (Indices.QUARTER_COLUMN_TAG); i++)
			{
			}
			nbNotes += i;
			Indices.MEAN_COLUMN += 2 * i - 2;
			Indices.APPRECIATION_COLUMN += 2 * i - 2;
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

	private static class Indices
	{

		public final static String SHEET = "Appreciations";
		public final static String STUDENT_COLUMN_TAG = "Elèves";
		public final static String QUARTER_COLUMN_TAG = "T";
		public final static String MEAN_COLUMN_TAG = "A";
		public final static String APPRECIATION_COLUMN_TAG = "Appréciations";

		public static int STUDENT_COLUMN = 1;
		public static int[] QUARTER_COLUMN =
		{
			3, 5, 7
		};
		public static int MEAN_COLUMN = 5;
		public static int APPRECIATION_COLUMN = 7;

		public static int APPRECIATIONS_FILE_COLUMN = 0;
		public static String APPRECIATIONS_FILE_SHEET = "Appréciations";

		public final static int TITLE_ROW = 1;
		public final static int FIRST_ROW = 3;
		public final static String SIZE_CLASS_TAG = "Effectif";
	}
}
