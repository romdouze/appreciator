/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ngr.KiKi.appreciator.view;

import ngr.KiKi.appreciator.data.Utils;

/**
 *
 * @author KiKi
 */
public class JPanelAbout extends javax.swing.JPanel
{

	private static int count = 0;

	/**
	 * Creates new form JPanelAbout
	 */
	public JPanelAbout ()
	{
		initComponents ();

		init ();
	}

	private void init ()
	{
		if (count++ < 6)
			jLabelK2K.setVisible (false);
		else
			jLabelK2K.setVisible (true);

		jLabelName.setText (Utils.implementationTitle);
		jLabelVersion.setText (Utils.builtDate);
		jLabelBy.setText (Utils.builtBy);
	}

	/**
	 * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabelName = new javax.swing.JLabel();
        jLabelVersion = new javax.swing.JLabel();
        jLabelK2K = new javax.swing.JLabel();
        jLabelBy = new javax.swing.JLabel();

        jLabelName.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabelName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelName.setText("APPRECIATOR");

        jLabelVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelVersion.setText("Version");

        jLabelK2K.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        jLabelK2K.setForeground(new java.awt.Color(204, 0, 0));
        jLabelK2K.setText("KRA20KRA");

        jLabelBy.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelBy.setText("Romain Mouchel-Vallon");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelName, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelVersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabelK2K))
            .addComponent(jLabelBy, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabelName)
                .addGap(18, 18, 18)
                .addComponent(jLabelVersion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelBy)
                .addGap(123, 123, 123)
                .addComponent(jLabelK2K))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabelBy;
    private javax.swing.JLabel jLabelK2K;
    private javax.swing.JLabel jLabelName;
    private javax.swing.JLabel jLabelVersion;
    // End of variables declaration//GEN-END:variables
}
