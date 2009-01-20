package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.ExitLookPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ExitPanel extends JTabbedPane {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the exit.
	 */
	private ExitDataControl exitDataControl;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Panel in which the exit area is painted.
	 */
	private ScenePreviewEditionPanel spep;

	/**
	 * Constructor.
	 * 
	 * @param exitDataControl
	 *            Exit controller
	 */
	public ExitPanel( ExitDataControl exitDataControl ) {

		JPanel mainPanel = new JPanel();
		// Set the controller
		this.exitDataControl = exitDataControl;


		// Take the path of the background
		String scenePath = Controller.getInstance( ).getSceneImagePath( exitDataControl.getParentSceneId( ) );
		spep = new ScenePreviewEditionPanel(scenePath);
		spep.setShowTextEdition(true);
		spep.setSelectedElement(exitDataControl);
		spep.setFixedSelectedElement(true);
		
		// Set the layout of the principal panel
		mainPanel.setLayout( new GridBagLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Exit.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create and add te documenation panel
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( exitDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Exit.Documentation" ) ) );
		mainPanel.add( documentationPanel, c );

		// Add the created rectangle position panel
		c.gridy = 1;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		mainPanel.add( spep, c );
		
		this.insertTab(  TextConstants.getText( "Exit.Title" ), null, mainPanel,  TextConstants.getText( "Exit.Title" ), 0 );
		this.insertTab( TextConstants.getText( "Exit.AdvancedOptions" ), null, new ExitLookPanel(this.exitDataControl.getExitLookDataControl( )), TextConstants.getText( "Exit.AdvancedOptions" ), 1 );
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
			exitDataControl.setDocumentation( documentationTextArea.getText( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate( DocumentEvent arg0 ) {
			// Set the new content
			exitDataControl.setDocumentation( documentationTextArea.getText( ) );
		}
	}

}
