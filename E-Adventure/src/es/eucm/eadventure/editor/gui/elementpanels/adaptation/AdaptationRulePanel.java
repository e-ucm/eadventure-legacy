package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;

public class AdaptationRulePanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Link to the element reference controller.
	 */
	private AdaptationRuleDataControl adaptationRuleDataControl;

	/**
	 * Text area for the description.
	 */
	private JTextArea descriptionTextArea;

	/**
	 * Constructor.
	 * 
	 * @param adpRuleDataControl
	 *            Controller of the element reference
	 */
	public AdaptationRulePanel( AdaptationRuleDataControl adpRuleDataControl ) {

		// Set the controller
		this.adaptationRuleDataControl = adpRuleDataControl;

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRule.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the combo box of importance
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "AdaptationRule.Information" ) );
		JPanel informationPanel = new JPanel( );
		informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GeneralText.Information" ) ) );
		informationPanel.setLayout( new BorderLayout( ) );
		informationPanel.add( informationTextPane, BorderLayout.CENTER );
		add( informationPanel, c );

		// Create the text area for the description
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;c.weightx=1;
		c.weighty = 0.3;

		JPanel conceptPanel = new JPanel( );
		conceptPanel.setLayout( new GridLayout( ) );
		descriptionTextArea = new JTextArea( adpRuleDataControl.getDescription( ), 4, 0 );
		descriptionTextArea.setLineWrap( true );
		descriptionTextArea.setWrapStyleWord( true );
		descriptionTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		conceptPanel.add( new JScrollPane( descriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		conceptPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AdaptationRule.Description" ) ) );
		add( conceptPanel, c );

		// Create the uol-state panel
		UOLPropertiesPanel uolPanel = new UOLPropertiesPanel( this.adaptationRuleDataControl ); 
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		add( uolPanel, c );

		// Create the game-state panel
		GridBagConstraints c3 = new GridBagConstraints( );
		
		c3.gridx=0;c3.gridy=0;
		c3.insets = new Insets( 5, 5, 5, 5 );
		c3.weightx=1; 
		c3.weighty=0.8; c3.fill=GridBagConstraints.BOTH;
		GameStatePanel gsPanel = new GameStatePanel( this.adaptationRuleDataControl ); 
		
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		add( gsPanel, c );

	}


	/**
	 * Listener for the text area. It checks the value of the area and updates the documentation.
	 */
	private class DocumentationTextAreaChangesListener implements DocumentListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
		 */
		public void changedUpdate( DocumentEvent arg0 ) {
		// Do nothing
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
		 */
		public void insertUpdate( DocumentEvent arg0 ) {
			// Set the new content
			if (arg0.getDocument( ) == descriptionTextArea.getDocument( )){
				adaptationRuleDataControl.setDescription( descriptionTextArea.getText( ) );	
			}
			
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate( DocumentEvent arg0 ) {
			// Set the new content
			
			if (arg0.getDocument( ) == descriptionTextArea.getDocument( )){
				adaptationRuleDataControl.setDescription( descriptionTextArea.getText( ) );
			}
			
		}
	}

}
