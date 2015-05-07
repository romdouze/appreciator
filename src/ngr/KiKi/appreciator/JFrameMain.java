/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.Painter;
import javax.swing.SwingConstants;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import ngr.KiKi.appreciator.data.Student;
import ngr.KiKi.appreciator.data.Utils;
import ngr.KiKi.appreciator.data.XLSHelper;
import ngr.KiKi.appreciator.view.Appreciation;
import ngr.KiKi.appreciator.view.JPanelAbout;
import ngr.KiKi.appreciator.view.JPanelClassView;
import ngr.KiKi.appreciator.view.JPanelSingleView;

/**
 *
 * @author KiKi
 */
public class JFrameMain extends javax.swing.JFrame
{

	private XLSHelper book;
	private static Properties properties;
	private JProgressBar progressBar;
	private ArrayList<Student> list;
	private Student current;
	private ArrayList<Appreciation> appreciations;

	private static final String PROPERTIES_FILENAME = "appreciator.properties";
	private static final String PROPERTIES_APPRECIATIONS_FILE = "appreciator.appreciationsFile";
	private static final String PROPERTIES_RECENT_PATH = "appreciator.recentPath";

	private JTabbedPane jTabbedPane;
	private JPanelSingleView singlePanel;
	private JPanelClassView classPanel;
	private static JLabel jLabelStatus;

	private boolean arrows;

	/**
	 * Creates new form JFrameMain
	 */
	public JFrameMain ()
	{
		initComponents ();

		init ();
	}

	private void init ()
	{
		properties = new Properties ();
		InputStream inputStream;
		try
		{
			inputStream = new FileInputStream (new File (PROPERTIES_FILENAME));
			properties.load (inputStream);
			inputStream.close ();
		}
		catch (FileNotFoundException ex)
		{
		}
		catch (IOException ex)
		{
			Logger.getLogger (XLSHelper.class.getName ()).log (Level.SEVERE, null, ex);
		}

		setVersion (JFrameMain.class);

		book = XLSHelper.getInstance ();
		list = new ArrayList<> ();
		appreciations = book.loadAppreciations (properties.getProperty (PROPERTIES_APPRECIATIONS_FILE));
		arrows = false;

		setTitle ("Appreciator");

		jMenuItemPrevious.setEnabled (false);

		this.setLayout (new BorderLayout ());

		jTabbedPane = new JTabbedPane ();
		jTabbedPane.addTab ("Elèves", new JPanel ());
		jTabbedPane.addTab ("Classe", new JPanel ());
		this.add (jTabbedPane, BorderLayout.CENTER);

		singlePanel = new JPanelSingleView (this, current = new Student ());

		addStatusBar ();

		classPanel = new JPanelClassView (this);
		jTabbedPane.setComponentAt (1, classPanel);
	}

	private void setVersion (Class classe)
	{
		String version;
		String shortClassName = classe.getName ().substring (classe.getName ().lastIndexOf (".") + 1);
		try
		{
			ClassLoader cl = this.getClass ().getClassLoader ();
			String threadContexteClass = classe.getName ().replace ('.', '/');
			URL url = cl.getResource (threadContexteClass + ".class");
			if (url == null)
				version = shortClassName + " $ (no manifest)";
			else
			{
				String path = url.getPath ();
				String jarExt = ".jar";
				int index = path.indexOf (jarExt);
				SimpleDateFormat sdf = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss");
				if (index != -1)
				{
					String jarPath = path.substring (0, index + jarExt.length ());
					File file = new File (jarPath);
					String jarVersion = file.getName ();
					JarFile jarFile = new JarFile (new File (new URI (jarPath)));
					JarEntry entry = jarFile.getJarEntry ("META-INF/MANIFEST.MF");
					version = shortClassName + " $ " + jarVersion.substring (0, jarVersion.length ()
							- jarExt.length ()) + " $ "
							+ sdf.format (new Date (entry.getTime ()));
					jarFile.close ();
				}
				else
				{
					File file = new File (path);
					version = shortClassName + " $ " + sdf.format (new Date (file.lastModified ()));
				}
			}
		}
		catch (URISyntaxException | IOException e)
		{
			version = shortClassName + " $ " + e.toString ();
		}

		Utils.builtDate = version.substring (version.lastIndexOf ("$") + 2);
	}

	private void addArrows ()
	{
		JPanel arrowsPanel = new JPanel ();
		add (arrowsPanel, BorderLayout.NORTH);
		arrowsPanel.setPreferredSize (new Dimension (getWidth (), 25));
		arrowsPanel.setLayout (new BorderLayout ());

		JButton left = new JButton ("<");
		left.addActionListener ((ae) ->
		{
			int i = list.indexOf (current);
			switchStudent (list.get (i - 1 < 0 ? list.size () - 1 : i - 1));
		});

		JButton right = new JButton (">");
		right.addActionListener ((ae) ->
		{
			int i = list.indexOf (current);
			switchStudent (list.get ((i + 1) % list.size ()));
		});

		arrowsPanel.add (left, BorderLayout.WEST);
		arrowsPanel.add (right, BorderLayout.EAST);

		progressBar = new JProgressBar ();
		progressBar.setStringPainted (true);
		progressBar.setValue (getProgress ());
		arrowsPanel.add (progressBar, BorderLayout.CENTER);

		arrows = true;
	}

	private void addStatusBar ()
	{
		JPanel statusPanel = new JPanel ();
		statusPanel.setBorder (new BevelBorder (BevelBorder.LOWERED));
		this.add (statusPanel, BorderLayout.SOUTH);
		statusPanel.setPreferredSize (new Dimension (this.getWidth (), 20));
		statusPanel.setLayout (new BoxLayout (statusPanel, BoxLayout.X_AXIS));
		jLabelStatus = new JLabel ("Pas de sélection");
		jLabelStatus.setHorizontalAlignment (SwingConstants.LEFT);
		statusPanel.add (jLabelStatus);
	}

	private void switchStudent (Student s)
	{
		singlePanel.updateCurrentValues ();
		singlePanel = new JPanelSingleView (this, s);
		jTabbedPane.setComponentAt (0, singlePanel);
		current = s;

		pack ();
	}

	public void switchStudent (int index)
	{
		switchStudent (list.get (index));
	}

	private void quit ()
	{
		System.exit (0);
	}

	public void setStatus (String s)
	{
		jLabelStatus.setText ("Copié : " + s);
	}

	public void sendToTable (Student st, String text)
	{
		classPanel.set (st, text);
		progressBar.setValue (getProgress ());
	}

	private int getProgress ()
	{
		long count = list.stream ().filter (student -> !(student.getAppreciation ().isEmpty ())).count ();

		return (int) ((count * 100) / list.size ());
	}

	public ArrayList<Appreciation> getAppreciations ()
	{
		return appreciations;
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemClose = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItemPrevious = new javax.swing.JMenuItem();
        jCheckBoxMenuItemCopy = new javax.swing.JCheckBoxMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setMnemonic('F');
        jMenu1.setText("Fichier");

        jMenuItemOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemOpen.setMnemonic('O');
        jMenuItemOpen.setText("Ouvrir");
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItemOpenActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemOpen);

        jMenuItemClose.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemClose.setMnemonic('Q');
        jMenuItemClose.setText("Quitter");
        jMenuItemClose.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItemCloseActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemClose);

        jMenuBar1.add(jMenu1);

        jMenu2.setMnemonic('O');
        jMenu2.setText("Options");

        jMenuItemPrevious.setText("Appréciations précédentes");
        jMenuItemPrevious.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItemPreviousActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemPrevious);

        jCheckBoxMenuItemCopy.setText("Boutons 'copier' visibles");
        jCheckBoxMenuItemCopy.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jCheckBoxMenuItemCopyActionPerformed(evt);
            }
        });
        jMenu2.add(jCheckBoxMenuItemCopy);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("?");

        jMenuItem1.setText("A propos");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 817, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 505, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemCloseActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemCloseActionPerformed
    {//GEN-HEADEREND:event_jMenuItemCloseActionPerformed
		quit ();
    }//GEN-LAST:event_jMenuItemCloseActionPerformed

    private void jMenuItemOpenActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemOpenActionPerformed
    {//GEN-HEADEREND:event_jMenuItemOpenActionPerformed
		JFileChooser chooser = new JFileChooser ();

		chooser.setFileFilter (new FileNameExtensionFilter ("Fichier Excel", "xls", "XLS"));
		chooser.setCurrentDirectory (new File (properties.getProperty (PROPERTIES_RECENT_PATH) == null ? "" : properties.getProperty (PROPERTIES_RECENT_PATH)));
		if (chooser.showOpenDialog (this) != JFileChooser.OPEN_DIALOG)
			return;

		File file = chooser.getSelectedFile ();
		if (!book.openBook (file))
			return;

		list = book.loadStudents ();

		if (!arrows)
			addArrows ();

		switchStudent (list.get (0));

		jMenuItemPrevious.setEnabled (true);
		properties.setProperty (PROPERTIES_RECENT_PATH, file.getParent ());

		classPanel.load (list);
//		mainPanel.load (list);
    }//GEN-LAST:event_jMenuItemOpenActionPerformed

    private void jMenuItemPreviousActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItemPreviousActionPerformed
    {//GEN-HEADEREND:event_jMenuItemPreviousActionPerformed
		JFileChooser chooser = new JFileChooser ();

		chooser.setFileFilter (new FileNameExtensionFilter ("Fichier Excel", "xls", "XLS"));
		chooser.setCurrentDirectory (new File (properties.getProperty (PROPERTIES_RECENT_PATH) == null ? "" : properties.getProperty (PROPERTIES_RECENT_PATH)));
		if (chooser.showOpenDialog (this) != JFileChooser.OPEN_DIALOG)
			return;

		File file = chooser.getSelectedFile ();
		book.loadSecondaryAppreciations (file, list);

		switchStudent (current);

		properties.setProperty (PROPERTIES_RECENT_PATH, file.getParent ());
    }//GEN-LAST:event_jMenuItemPreviousActionPerformed

    private void jCheckBoxMenuItemCopyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jCheckBoxMenuItemCopyActionPerformed
    {//GEN-HEADEREND:event_jCheckBoxMenuItemCopyActionPerformed
		classPanel.setButtonsVisible (jCheckBoxMenuItemCopy.isSelected ());
    }//GEN-LAST:event_jCheckBoxMenuItemCopyActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jMenuItem1ActionPerformed
    {//GEN-HEADEREND:event_jMenuItem1ActionPerformed
		JFrame about = new JFrame ("A propos...");
		about.add (new JPanelAbout ());
		about.setResizable (false);
		about.pack ();
		about.setVisible (true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

	/**
	 * @param args the command line arguments
	 */
	public static void main (String args[])
	{
		/* Set the Nimbus look and feel */
		//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
		 * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
		 */
		try
		{
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels ())
				if ("Nimbus".equals (info.getName ()))
				{
					javax.swing.UIManager.setLookAndFeel (info.getClassName ());
					break;
				}
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
		{
			java.util.logging.Logger.getLogger (JFrameMain.class.getName ()).log (java.util.logging.Level.SEVERE, null, ex);
		}
		//</editor-fold>

		//</editor-fold>

		/* Create and display the form */
		java.awt.EventQueue.invokeLater (() ->
		{
			Runtime.getRuntime ().addShutdownHook (new Thread ()
			{
				@Override
				public void run ()
				{
					try
					{
						FileOutputStream fos = new FileOutputStream (new File (PROPERTIES_FILENAME));
						properties.store (fos, "");
					}
					catch (IOException ex)
					{
						Logger.getLogger (JFrameMain.class.getName ()).log (Level.SEVERE, null, ex);
					}
				}
			});
			new JFrameMain ().setVisible (true);
		});
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItemCopy;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItemClose;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemPrevious;
    // End of variables declaration//GEN-END:variables
}
