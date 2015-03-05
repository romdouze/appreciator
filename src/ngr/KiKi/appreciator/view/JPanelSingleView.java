/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import ngr.KiKi.appreciator.JFrameMain;
import ngr.KiKi.appreciator.data.Student;
import ngr.KiKi.appreciator.data.Utils;
import ngr.KiKi.appreciator.data.XLSHelper;

/**
 *
 * @author KiKi
 */
public class JPanelSingleView extends javax.swing.JPanel
{

	private final Student student;
//	private ArrayList<Appreciation> list;
	private DefaultListModel model;
	private ArrayList<AppreciationRenderer> arList;

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
		arList = new ArrayList<> ();

		jTFNotes = new JTextField[]
		{
			jTextFieldT1, jTextFieldT2, jTextFieldT3
		};

		jLabelName.setText (student.getfullname ());
		jTextFieldMean.setText (student.getMean ().toString ());

		for (int i = 0; i < student.getNotes ().length; i++)
			jTFNotes[i].setText (student.getNotes ()[i].toString ());

		DocumentListener docListener = new DocumentListener ()
		{

			@Override
			public void insertUpdate (DocumentEvent de)
			{
				updateList ();
			}

			@Override
			public void removeUpdate (DocumentEvent de)
			{
				updateList ();
			}

			@Override
			public void changedUpdate (DocumentEvent de)
			{
				updateList ();
			}
		};

		jTextFieldT1.getDocument ().addDocumentListener (docListener);
		jTextFieldT2.getDocument ().addDocumentListener (docListener);
		jTextFieldT3.getDocument ().addDocumentListener (docListener);
		jTextFieldMean.getDocument ().addDocumentListener (docListener);

		ChangeListener sliderListener = (ChangeEvent ce) ->
		{
			if (!((JSlider) ce.getSource ()).getValueIsAdjusting ())
				updateList ();
		};

		jSliderBehaviour.addChangeListener (sliderListener);
		jSliderFocus.addChangeListener (sliderListener);

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
				Utils.sendToClipboard (text);
				jTextAreaCustom.setText (text);
				parent.setStatus (text);
			}
		});
		if (student.getPrevious ().isEmpty ())
			jPanelPrevious.setVisible (false);

		jPanelList = new JPanel ();
		jPanelList.setLayout (new MigLayout (new LC ().fillX ().hideMode (3)));
//		jPanelList.setBorder (new LineBorder (Color.RED));

		initList ();

		JPanel northOnlyPanel = new JPanel ();
		northOnlyPanel.setLayout (new BorderLayout ());
//		northOnlyPanel.setBorder (new LineBorder (Color.BLUE));
		northOnlyPanel.add (jPanelList, BorderLayout.NORTH);

		JScrollPane jScrollPaneList = new JScrollPane (jPanelList, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jScrollPaneList.getVerticalScrollBar ().setValue (0);

		jPanelListHolder.setLayout (new BorderLayout ());
		jPanelListHolder.add (jScrollPaneList, BorderLayout.CENTER);
	}

	public void setCustomAreaText (String s)
	{
		jTextAreaCustom.setText (s);
	}

	private void initList ()
	{
		jPanelList.removeAll ();
		arList.clear ();
		parent.getAppreciations ().stream ().forEach ((a) ->
		{
			AppreciationRenderer ar = new AppreciationRenderer (this, a, student);
			arList.add (ar);
			SwingUtilities.invokeLater (new AsynchronousLoad (ar));
		});
		updateList ();
	}

	private void updateList ()
	{
		arList.stream ().forEach ((ar) ->
		{
			ar.setVisible (isWithin (ar.getAppreciation ()));
		});
	}

	private boolean isWithin (Appreciation a)
	{
		boolean within = true;

		for (int i = 0; i < student.getNotes ().length && within == true; i++)
			if (!a.isWithin (XLSHelper.Indices.APPRECIATIONS_FILE_T[i], Double.valueOf (jTFNotes[i].getText ().isEmpty () ? "0" : jTFNotes[i].getText ())))
				within = false;
		if (!a.isWithin (XLSHelper.Indices.APPRECIATIONS_FILE_A, Double.valueOf (jTextFieldMean.getText ())))
			within = false;
		if (!a.isWithin (XLSHelper.Indices.APPRECIATIONS_FILE_C, Double.valueOf (jSliderBehaviour.getValue ())))
			within = false;
		if (!a.isWithin (XLSHelper.Indices.APPRECIATIONS_FILE_I, Double.valueOf (jSliderFocus.getValue ())))
			within = false;

		return within;
	}

	void setStatus (String text)
	{
		parent.setStatus (text);
	}

	private class AsynchronousLoad implements Runnable
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
		}

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
        jButtonOK = new javax.swing.JButton();
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

        jButtonOK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ngr/KiKi/appreciator/resources/ic_action_accept.png"))); // NOI18N
        jButtonOK.setText("OK");
        jButtonOK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonOK.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButtonOK.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButtonOK.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonOKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 689, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonOK, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonOK, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonOKActionPerformed
    {//GEN-HEADEREND:event_jButtonOKActionPerformed
		String text = jTextAreaCustom.getText ();
		Utils.sendToClipboard (text);
		parent.setStatus (text);
		parent.sendToTable (student, text);
    }//GEN-LAST:event_jButtonOKActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonOK;
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
