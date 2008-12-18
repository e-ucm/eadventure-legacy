package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.general.CustomActionDataControl;

/**
 * The panel for the edition of a custom action
 * 
 * @author Eugenio Marchiori
 *
 */
public class CustomActionPanel extends JPanel {

	/**
	 * Default generated serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The data control for the custom action
	 */
	private CustomActionDataControl customActionDataControl;
	
	/**
	 * The panel where the general action configuration is displayed
	 */
	private ActionPanel actionPanel;
	
	/**
	 * The panel used to configure the resources
	 */
	private CustomActionLooksPanel looksPanel;
	
	/**
	 * The tab panel that allows to switch between the ActionPanel
	 * and the one with personalization elements
	 */
	private JTabbedPane tabPanel;
	
	/**
	 * The text field with the name of the action
	 */
	private JTextField nameTextField;
	
	/**
	 * The checkbox with the value of needsGoTo
	 */
	private JCheckBox needsGoToCheck;
	
	/**
	 * The spinner to set the value of keepDistance
	 */
	private JSpinner keepDistanceSpinner;
	
	/**
	 * Defaul constructor
	 * 
	 * @param customActionDataControl the dataControl for the customaction
	 */
	public CustomActionPanel(CustomActionDataControl customActionDataControl) {
		this.customActionDataControl = customActionDataControl;
		
		tabPanel = new JTabbedPane( );
		
		actionPanel = new ActionPanel(customActionDataControl);
		
		JPanel personalizationPanel = createPersonalizationPanel();
		
		tabPanel.insertTab( TextConstants.getText( "CustomAction.PersonalizationTitle" ), null, personalizationPanel, TextConstants.getText( "CustomAction.PersonalizationTip" ), 0 );
		tabPanel.insertTab( TextConstants.getText( "CustomAction.ConfigurationTitle" ), null, actionPanel, TextConstants.getText( "CustomAction.ConfigurationTip" ), 1 );

		setLayout( new BorderLayout( ) );
		add( tabPanel, BorderLayout.CENTER );

	}
	
	/**
	 * Creates the panel where the personalization elements of the action
	 * are placed.
	 * 
	 * @return the panel with the necessary elements
	 */
	private JPanel createPersonalizationPanel() {
		JPanel personalizationPanel = new JPanel();
		personalizationPanel.setLayout(new GridBagLayout());
		
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0.1;
		c.weightx = 1.0;
		c.insets = new Insets( 5, 5, 5, 5 );
		c.fill = GridBagConstraints.BOTH;

		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridLayout( ) );
		nameTextField = new JTextField( customActionDataControl.getName( ) );
		nameTextField.addActionListener( new TextFieldChangesListener( ) );
		nameTextField.addFocusListener( new TextFieldChangesListener( ) );
		namePanel.add( nameTextField );
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "CustomAction.Name" ) ) );
		personalizationPanel.add(namePanel, c);
		
		c.gridy++;
		JPanel otherPanel = new JPanel();
		otherPanel.setLayout( new GridLayout(3,1));
		otherPanel.add(new JLabel("CustomAction.OtherConfigurationDetails"));
		
		needsGoToCheck = new JCheckBox(TextConstants.getText("CustomAction.NeedsGoTo"));
		needsGoToCheck.setSelected(customActionDataControl.getNeedsGoTo());
		needsGoToCheck.addChangeListener( new NeedsGoToCheckListener());
		otherPanel.add( needsGoToCheck);
		
	    JPanel temp = new JPanel();
		SpinnerModel sm = new SpinnerNumberModel(customActionDataControl.getKeepDistance(), 0, 100, 5);
		keepDistanceSpinner = new JSpinner(sm);
		keepDistanceSpinner.setEnabled(customActionDataControl.getNeedsGoTo());
		keepDistanceSpinner.addChangeListener(new KeepDistanceSpinnerListener());
		temp.setLayout(new BorderLayout());
		temp.add(new JLabel(TextConstants.getText("CustomAction.DistanceToObjective")), BorderLayout.CENTER);
		temp.add(keepDistanceSpinner, BorderLayout.WEST);
		otherPanel.add( temp);
		
		otherPanel.setBorder(BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder(), TextConstants.getText("CustomAction.OtherConfiguration")));
		personalizationPanel.add(otherPanel, c);
		
		c.gridy++;
		c.weighty = 1.0;
		looksPanel = new CustomActionLooksPanel( customActionDataControl );
		looksPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "CustomAction.Appearance" ) ) );
		personalizationPanel.add(looksPanel, c);
		
		
		return personalizationPanel;
	}

	private class CustomActionLooksPanel extends LooksPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public CustomActionLooksPanel( DataControlWithResources control ) {
			super( control );
			// TODO Parche, arreglar
			lookPanel.setPreferredSize( new Dimension( 0, 90 ) );

		}

		@Override
		protected void createPreview( ) {

		}

		@Override
		public void updatePreview( ) {
			getParent( ).getParent( ).repaint( );
		}

		public void updateResources( ) {
			super.updateResources( );
			getParent( ).getParent( ).repaint( );
		}

	}
	
	/**
	 * Listener for the changes in the needsGoTo checkbox
	 */
	private class NeedsGoToCheckListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent arg0) {
			customActionDataControl.setNeedsGoTo(needsGoToCheck.isSelected());
			keepDistanceSpinner.setEnabled(needsGoToCheck.isSelected());
		}
	}
	
	/**
	 * Listener for the changes in the keepDistances spinner
	 */
	private class KeepDistanceSpinnerListener implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent arg0) {
			customActionDataControl.setKeepDistance(((Integer) keepDistanceSpinner.getModel().getValue()).intValue());
		}
	}
	
	/**
	 * Listener for the text fields. It checks the values from the fields and updates the data.
	 */
	private class TextFieldChangesListener extends FocusAdapter implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
		 */
		public void focusLost( FocusEvent e ) {
			valueChanged( e.getSource( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			valueChanged( e.getSource( ) );
		}
	}

	/**
	 * Called when a text field has changed, so that we can set the new values.
	 * 
	 * @param source
	 *            Source of the event
	 */
	private void valueChanged( Object source ) {
		// Check the name field
		if( source == nameTextField )
			customActionDataControl.setName( nameTextField.getText( ) );
	}

}
