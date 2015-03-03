/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import net.miginfocom.layout.CC;
import net.miginfocom.swing.MigLayout;
import ngr.KiKi.appreciator.JFrameMain;
import ngr.KiKi.appreciator.data.Student;

/**
 *
 * @author KiKi
 */
public class JPanelSingleView extends javax.swing.JPanel
{

	private final Student student;
//	private ArrayList<Appreciation> list;
	private DefaultListModel model;

	private JPanel jPanelList;
	private final JFrameMain parent;
	private JTextField[] jTFNotes;

	/**
	 * Creates new form JPanelSingleView
	 */
	public JPanelSingleView (JFrameMain p, Student s)
	{
		initComponents ();

		parent = p;
		student = s;
		init ();
	}

	private void init ()
	{
		jTFNotes = new JTextField[]
		{
			jTextFieldT1, jTextFieldT2, jTextFieldT3
		};

		jLabelName.setText (student.getfullname ());
		jTextFieldMean.setText (student.getMean ().toString ());

		for (int i = 0; i < student.getNotes ().length; i++)
			jTFNotes[i].setText (student.getNotes ()[i].toString ());

		jTextAreaPrevious.setText (student.getPrevious ());
		jTextAreaPrevious.addMouseListener (new MouseAdapter ()
		{
			@Override
			public void mouseEntered (MouseEvent evt)
			{
				jTextAreaPrevious.setBackground (Color.LIGHT_GRAY);
			}

			@Override
			public void mouseExited (MouseEvent evt)
			{
				jTextAreaPrevious.setBackground (Color.WHITE);
			}

			@Override
			public void mouseClicked (MouseEvent evt)
			{
				String text = jTextAreaPrevious.getText ();
				StringSelection ss = new StringSelection (text);
				Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
				clpbrd.setContents (ss, null);
				jTextAreaCustom.setText (text);
				JFrameMain.setStatus (text);
			}
		});
		if (student.getPrevious ().isEmpty ())
			jPanelPrevious.setVisible (false);

//		list = new ArrayList<> ();
//		list.add (new Appreciation ("Y manque de méthode, cela se voit dans ses contrôles, mais ce doit être le cas pour le travail à la maison. Il faut s'organiser pour un travail efficace."));
//		list.add (new Appreciation ("X a beaucoup de difficultés à suivre le rythme, elle semble perdue. Peut-être un problème d'organisation du travail ? Il faut essayer de se relancer avec les prochains chapitres et le début du deuxième trimestre."));
//		list.add (new Appreciation ("Y manque de méthode, cela se voit dans ses contrôles, mais ce doit être le cas pour le travail à la maison. Il faut s'organiser pour un travail efficace."));
//		list.add (new Appreciation ("Y manque de méthode, cela se voit dans ses contrôles, mais ce doit être le cas pour le travail à la maison. Il faut s'organiser pour un travail efficace."));
//		list.add (new Appreciation ("Y manque de méthode, cela se voit dans ses contrôles, mais ce doit être le cas pour le travail à la maison. Il faut s'organiser pour un travail efficace."));
//		list.add (new Appreciation ("Y manque de méthode, cela se voit dans ses contrôles, mais ce doit être le cas pour le travail à la maison. Il faut s'organiser pour un travail efficace."));
//		list.add (new Appreciation ("Y manque de méthode, cela se voit dans ses contrôles, mais ce doit être le cas pour le travail à la maison. Il faut s'organiser pour un travail efficace."));
//		list.add (new Appreciation ("Y manque de méthode, cela se voit dans ses contrôles, mais ce doit être le cas pour le travail à la maison. Il faut s'organiser pour un travail efficace."));
//		list.add (new Appreciation ("Y manque de méthode, cela se voit dans ses contrôles, mais ce doit être le cas pour le travail à la maison. Il faut s'organiser pour un travail efficace."));
//		list.add (new Appreciation ("Y manque de méthode, cela se voit dans ses contrôles, mais ce doit être le cas pour le travail à la maison. Il faut s'organiser pour un travail efficace."));
//		list.add (new Appreciation ("Y manque de méthode, cela se voit dans ses contrôles, mais ce doit être le cas pour le travail à la maison. Il faut s'organiser pour un travail efficace."));
//		list.add (new Appreciation ("Y manque de méthode, cela se voit dans ses contrôles, mais ce doit être le cas pour le travail à la maison. Il faut s'organiser pour un travail efficace."));
		jPanelList = new JPanel ();
		jPanelList.setLayout (new MigLayout ("fillx"));
//		jPanelList.setBorder (new LineBorder (Color.RED));

		class AsynchronousLoad implements Runnable
		{

			private final AppreciationRenderer stuff;

			public AsynchronousLoad (AppreciationRenderer ar)
			{
				stuff = ar;
			}

			@Override
			public void run ()
			{
				jPanelList.add (stuff, new CC ().wrap ().growX ());
				stuff.invalidate ();
				stuff.repaint ();

			}

		}

		parent.getAppreciations ().stream ().forEach ((a) ->
		{
			AppreciationRenderer ar = new AppreciationRenderer (this, a, student);
			SwingUtilities.invokeLater (new AsynchronousLoad (ar));
		});

		JPanel northOnlyPanel = new JPanel ();
		northOnlyPanel.setLayout (new BorderLayout ());
//		northOnlyPanel.setBorder (new LineBorder (Color.BLUE));
		northOnlyPanel.add (jPanelList, BorderLayout.NORTH);

		JScrollPane jScrollPaneList = new JScrollPane (jPanelList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPaneList.getVerticalScrollBar ().setValue (0);

		jPanelListHolder.setLayout (new BorderLayout ());
		jPanelListHolder.add (jScrollPaneList, BorderLayout.CENTER);

//		jListAppreciations.setModel (model = new DefaultListModel ());
//		model.addElement (new Appreciation ("Y manque de méthode, cela se voit dans ses contrôles, mais ce doit être le cas pour le travail à la maison. Il faut s'organiser pour un travail efficace.", student));
//		model.addElement (new Appreciation ("X a beaucoup de difficultés à suivre le rythme, elle semble perdue. Peut-être un problème d'organisation du travail ? Il faut essayer de se relancer avec les prochains chapitres et le début du deuxième trimestre.", student));
//
//		jListAppreciations.addMouseListener (new MouseAdapter ()
//		{
//			@Override
//			public void mouseClicked (MouseEvent evt)
//			{
//				if (evt.getButton () == MouseEvent.BUTTON1)
//				{
//					int index = jListAppreciations.locationToIndex (evt.getPoint ());
//					if (index >= 0)
//					{
//						String text = ((Appreciation) model.getElementAt (index)).getText ();
//						StringSelection ss = new StringSelection (text);
//						Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
//						clpbrd.setContents (ss, null);
//						jTextAreaCustom.setText (text);
//						JFrameMain.setStatus (text);
//					}
//				}
//			}
//		});
//
//		jListAppreciations.setCellRenderer (new AppreciationCellRenderer ());
	}

	public void setCustomAreaText (String s)
	{
		jTextAreaCustom.setText (s);
	}

	private void filterList ()
	{
		int behaviour = jSliderBehaviour.getValue ();
		int focus = jSliderFocus.getValue ();

	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jPanelPrevious = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaPrevious = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jSliderBehaviour = new javax.swing.JSlider();
        jLabelName = new javax.swing.JLabel();
        jSliderFocus = new javax.swing.JSlider();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldT3 = new javax.swing.JTextField();
        jTextFieldT1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldT2 = new javax.swing.JTextField();
        jTextFieldMean = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaCustom = new javax.swing.JTextArea();
        jButtonCopy = new javax.swing.JButton();
        jPanelListHolder = new javax.swing.JPanel();

        jPanelPrevious.setBorder(javax.swing.BorderFactory.createTitledBorder("Trimestre précédent"));

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTextAreaPrevious.setColumns(20);
        jTextAreaPrevious.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTextAreaPrevious.setLineWrap(true);
        jTextAreaPrevious.setRows(2);
        jTextAreaPrevious.setWrapStyleWord(true);
        jTextAreaPrevious.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jTextAreaPrevious.setOpaque(false);
        jScrollPane2.setViewportView(jTextAreaPrevious);

        javax.swing.GroupLayout jPanelPreviousLayout = new javax.swing.GroupLayout(jPanelPrevious);
        jPanelPrevious.setLayout(jPanelPreviousLayout);
        jPanelPreviousLayout.setHorizontalGroup(
            jPanelPreviousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2)
        );
        jPanelPreviousLayout.setVerticalGroup(
            jPanelPreviousLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPreviousLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jSliderBehaviour.setMajorTickSpacing(1);
        jSliderBehaviour.setMaximum(4);
        jSliderBehaviour.setPaintLabels(true);
        jSliderBehaviour.setValue(2);
        jSliderBehaviour.setBorder(javax.swing.BorderFactory.createTitledBorder("Comportement"));

        jLabelName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabelName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelName.setText("Nom");

        jSliderFocus.setMajorTickSpacing(1);
        jSliderFocus.setMaximum(4);
        jSliderFocus.setPaintLabels(true);
        jSliderFocus.setValue(2);
        jSliderFocus.setBorder(javax.swing.BorderFactory.createTitledBorder("Investissement"));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Notes"));

        jLabel1.setText("Moyenne générale");

        jLabel2.setText("Premier trimestre");

        jLabel4.setText("Troisième trimestre");

        jLabel3.setText("Second trimestre");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldT3)
                    .addComponent(jTextFieldT2)
                    .addComponent(jTextFieldT1)
                    .addComponent(jTextFieldMean, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMean, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextFieldT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSliderBehaviour, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
                    .addComponent(jSliderFocus, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jSliderBehaviour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSliderFocus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTextAreaCustom.setColumns(20);
        jTextAreaCustom.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jTextAreaCustom.setLineWrap(true);
        jTextAreaCustom.setRows(2);
        jTextAreaCustom.setWrapStyleWord(true);
        jScrollPane3.setViewportView(jTextAreaCustom);

        jButtonCopy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ngr/KiKi/appreciator/resources/ic_action_copy.png"))); // NOI18N
        jButtonCopy.setText("Copier");
        jButtonCopy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonCopy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonCopy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonCopy.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonCopyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCopy, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonCopy, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanelListHolder.setBorder(javax.swing.BorderFactory.createTitledBorder("Appréciations"));
        jPanelListHolder.setPreferredSize(new java.awt.Dimension(486, 315));

        javax.swing.GroupLayout jPanelListHolderLayout = new javax.swing.GroupLayout(jPanelListHolder);
        jPanelListHolder.setLayout(jPanelListHolderLayout);
        jPanelListHolderLayout.setHorizontalGroup(
            jPanelListHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanelListHolderLayout.setVerticalGroup(
            jPanelListHolderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanelPrevious, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanelListHolder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelListHolder, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanelPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonCopyActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonCopyActionPerformed
    {//GEN-HEADEREND:event_jButtonCopyActionPerformed
		String text = jTextAreaCustom.getText ();
		StringSelection ss = new StringSelection (text);
		Clipboard clpbrd = Toolkit.getDefaultToolkit ().getSystemClipboard ();
		clpbrd.setContents (ss, null);
		jTextAreaCustom.setText (text);
		JFrameMain.setStatus (text);
    }//GEN-LAST:event_jButtonCopyActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCopy;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelListHolder;
    private javax.swing.JPanel jPanelPrevious;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSlider jSliderBehaviour;
    private javax.swing.JSlider jSliderFocus;
    private javax.swing.JTextArea jTextAreaCustom;
    private javax.swing.JTextArea jTextAreaPrevious;
    private javax.swing.JTextField jTextFieldMean;
    private javax.swing.JTextField jTextFieldT1;
    private javax.swing.JTextField jTextFieldT2;
    private javax.swing.JTextField jTextFieldT3;
    // End of variables declaration//GEN-END:variables
}
