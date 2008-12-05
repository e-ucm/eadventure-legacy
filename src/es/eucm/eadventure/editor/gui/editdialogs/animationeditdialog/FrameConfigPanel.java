package es.eucm.eadventure.editor.gui.editdialogs.animationeditdialog;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.gui.TextConstants;

public class FrameConfigPanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Frame frame;
	
	private JList list;
	
	private JSpinner spinner;
	
	private JTextField textField;
	
	private AnimationEditDialog aed;
	
	private JCheckBox checkbox;
	
	public FrameConfigPanel(Frame frame, JList list, AnimationEditDialog aed) {
		this.frame = frame;
		this.list = list;
		this.aed = aed;
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
		c.weightx = 0.1;
		c.gridx = 0;
		c.gridy = 0;
		
		JPanel temp = new JPanel();
		temp.add(new JLabel(TextConstants.getText("Animation.Duration") + ": "));
	    SpinnerModel sm = new SpinnerNumberModel(frame.getTime(), 0, 10000, 100);
	    spinner = new JSpinner(sm);
	    spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				modifyFrame();					
			}});
	    temp.add(spinner);
	    

	    if (aed.getAnimationDataControl().getAnimation().isSlides()) {
			checkbox = new JCheckBox(TextConstants.getText("Animation.WaitForClick"));
			if (frame.isWaitforclick()) {
				checkbox.setSelected(true);
				spinner.setEnabled(false);
			} else
				checkbox.setSelected(false);
			checkbox.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent arg0) {
					changeWaitForClick();
				}
			
			});
			temp.add(checkbox);
	    }

	    this.add(temp, c);
	    
	    c.fill = GridBagConstraints.BOTH;
	    c.gridy = 1;
	    c.gridx = 0;
	    c.weightx = 1;
		JPanel assetPanel = new JPanel( );
		assetPanel.setLayout( new GridBagLayout( ) );
		assetPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), "Imagen" ) );
		GridBagConstraints c2 = new GridBagConstraints( );
		c2.insets = new Insets( 2, 2, 2, 2 );
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		c2.weighty = 0;

		// Create the text field and insert it
		textField = new JTextField(40);
		textField.setText(frame.getUri());
		textField.setEditable( false );
		c2.gridx = 0;
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.weightx = 1;
		assetPanel.add( textField, c2 );

		// Create the "Select" button and insert it
		JButton selectButton = new JButton( TextConstants.getText( "Resources.Select" ) );
		selectButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectImage();
			}} );
		c2.gridx = 1;
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		assetPanel.add( selectButton, c2 );
		
		this.add(assetPanel, c);
	}

	protected void changeWaitForClick() {
		frame.setWaitforclick(checkbox.isSelected());
		if (checkbox.isSelected())
			spinner.setEnabled(false);
		else
			spinner.setEnabled(true);
	}

	protected void selectImage() {
		String temp = aed.getAnimationDataControl().editAssetPath(aed);
		if (temp != null) {
			frame.setUri(temp);
			list.updateUI();
			textField.setText(temp);
			list.updateUI();
		}
	}

	protected void modifyFrame() {
		frame.setTime(((Double) spinner.getModel().getValue()).longValue());
		list.updateUI();
	}
	
	
}