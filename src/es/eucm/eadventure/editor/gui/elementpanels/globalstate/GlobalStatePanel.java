package es.eucm.eadventure.editor.gui.elementpanels.globalstate;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.ConditionsPanel;

/**
 * Panel for Global State
 * @author Javier
 *
 */
public class GlobalStatePanel extends JPanel{

	/**
	 * Required
	 */
	private static final long serialVersionUID = 1356438513519568096L;
	
	/**
	 * Controller
	 */
	private GlobalStateDataControl dataControl;
	
	/**
	 * Conditions panel
	 */
	private ConditionsPanel conditionsPanel;
	
	
	private JTextArea documentationTextArea;
	/**
	 * Constructor
	 * @param dataControl
	 */
	public GlobalStatePanel ( GlobalStateDataControl dataControl ){
		this.dataControl = dataControl;
		
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		setLayout ( new GridBagLayout( ) );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.3;
		c.weightx = 1;
		
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( dataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GlobalState.Documentation" ) ) );
		
		add ( documentationPanel, c );
		
		c.weighty = 0.7;
		c.gridy = 1;
		
		conditionsPanel = new ConditionsPanel ( dataControl.getController( ) );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GlobalState.Conditions" ) ) );
		add ( conditionsPanel, c );
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
			dataControl.setDocumentation( documentationTextArea.getText( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate( DocumentEvent arg0 ) {
			// Set the new content
			dataControl.setDocumentation( documentationTextArea.getText( ) );
		}
	}

}
