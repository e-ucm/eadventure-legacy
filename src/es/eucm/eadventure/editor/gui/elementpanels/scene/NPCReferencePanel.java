package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.BarrierDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.otherpanels.PreviewPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class NPCReferencePanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Link to the element reference controller.
	 */
	private ElementReferenceDataControl elementReferenceDataControl;

	/**
	 * Combo box for the characters in the script.
	 */
	private JComboBox npcsComboBox;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;
	
	/**
	 * Panel with the editable element painted in it, along with the rest of the elements in the scene.
	 */
	private ScenePreviewEditionPanel scenePreviewEditionPanel;

	/**
	 * Constructor.
	 * 
	 * @param elementReferenceDataControl
	 *            Controller of the element reference
	 */
	public NPCReferencePanel( ElementReferenceDataControl elementReferenceDataControl ) {

		// Set the controller
		Controller controller = Controller.getInstance( );
		this.elementReferenceDataControl = elementReferenceDataControl;

		// Take the scene path and the element path
		String scenePath = controller.getSceneImagePath( elementReferenceDataControl.getParentSceneId( ) );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPCReference.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the combo box of characters
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JPanel npcIdPanel = new JPanel( );
		npcIdPanel.setLayout( new GridLayout( ) );
		npcsComboBox = new JComboBox( controller.getIdentifierSummary( ).getNPCIds( ) );
		npcsComboBox.setSelectedItem( elementReferenceDataControl.getElementId( ) );
		npcsComboBox.addActionListener( new NPCComboBoxListener( ) );
		npcIdPanel.add( npcsComboBox );
		npcIdPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPCReference.NPCId" ) ) );
		add( npcIdPanel, c );

		// Create the text area for the documentation
		c.gridy = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( elementReferenceDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPCReference.Documentation" ) ) );
		add( documentationPanel, c );

		// Create the button for the conditions
		c.gridy = 2;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPCReference.Conditions" ) ) );
		add( conditionsPanel, c );

		// Create image panel
		PreviewPanel previewPanel = new PreviewPanel(scenePath);
		scenePreviewEditionPanel = previewPanel.getScenePreviewEditionPanel();
		scenePreviewEditionPanel.setFixedSelectedElement(true);


		// Set the values for the categories and checkboxes
		scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_OBJECT, controller.getShowItemReferences( ));
		scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_CHARACTER, controller.getShowNPCReferences( ));
		scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_ATREZZO, controller.getShowAtrezzoReferences( ));

		// Create and add the resulting panel
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		JPanel completePositionPanel = new JPanel( );
		completePositionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPCReference.Position" ) ) );
		completePositionPanel.setLayout( new BorderLayout( ) );
		completePositionPanel.add( previewPanel, BorderLayout.CENTER );
		add( completePositionPanel, c );

		// Add the other elements of the scene if a background image was loaded
		if( scenePath != null ) {

			// Add the item references first
			for( ElementReferenceDataControl elementReference : elementReferenceDataControl.getParentSceneItemReferences( ) ) {
				scenePreviewEditionPanel.addElement(ScenePreviewEditionPanel.CATEGORY_OBJECT, elementReference);
			}

			// Add then the character references
			for( ElementReferenceDataControl elementReference : elementReferenceDataControl.getParentSceneNPCReferences( ) ) {
				scenePreviewEditionPanel.addElement(ScenePreviewEditionPanel.CATEGORY_CHARACTER, elementReference);
			}
			
			//Add atrezzo references
			for( ElementReferenceDataControl elementReference : elementReferenceDataControl.getParentSceneAtrezzoReferences( ) ) {
				scenePreviewEditionPanel.addElement(ScenePreviewEditionPanel.CATEGORY_ATREZZO, elementReference);
			}
			
			
			for ( ActiveAreaDataControl activeArea : elementReferenceDataControl.getParentSceneActiveAreaList()) {
				scenePreviewEditionPanel.addActiveArea(activeArea);
			}
			scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_ACTIVEAREA, false);

			for ( BarrierDataControl barrier : elementReferenceDataControl.getParentSceneBarrierList()) {
				scenePreviewEditionPanel.addBarrier(barrier);
			}
			scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_BARRIER, false);

			for ( ExitDataControl exit : elementReferenceDataControl.getParentSceneExitList()) {
				scenePreviewEditionPanel.addExit(exit);
			}
			scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_EXIT, false);

			scenePreviewEditionPanel.setMovableElement(elementReferenceDataControl);

			if (elementReferenceDataControl.getSceneDataControl().getTrajectory().hasTrajectory()) {
				scenePreviewEditionPanel.setTrajectory((Trajectory) elementReferenceDataControl.getSceneDataControl().getTrajectory().getContent());
				for (NodeDataControl nodeDataControl: elementReferenceDataControl.getSceneDataControl().getTrajectory().getNodes())
					scenePreviewEditionPanel.addNode(nodeDataControl);
				scenePreviewEditionPanel.addInfluenceArea(elementReferenceDataControl.getInfluenceArea());
			}

		
		}
		
		previewPanel.recreateCheckBoxPanel();
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
			elementReferenceDataControl.setDocumentation( documentationTextArea.getText( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate( DocumentEvent arg0 ) {
			// Set the new content
			elementReferenceDataControl.setDocumentation( documentationTextArea.getText( ) );
		}
	}

	/**
	 * Listener for the characters combo box.
	 */
	private class NPCComboBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			elementReferenceDataControl.setElementId( npcsComboBox.getSelectedItem( ).toString( ) );
			scenePreviewEditionPanel.recreateElement(elementReferenceDataControl);
		}
	}

	/**
	 * Listener for the edit conditions button.
	 */
	private class ConditionsButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new ConditionsDialog( elementReferenceDataControl.getConditions( ) );
		}
	}
}
