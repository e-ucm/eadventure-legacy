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

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ItemReferencePanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Link to the element reference controller.
	 */
	private ElementReferenceDataControl elementReferenceDataControl;

	/**
	 * Combo box for the items in the script.
	 */
	private JComboBox itemsComboBox;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Check box for the item references.
	 */
	private JCheckBox itemReferencesCheckBox;

	/**
	 * Check box for the NPC references.
	 */
	private JCheckBox npcReferencesCheckBox;

	/**
	 * Check box for the atrezzo references
	 */
	private JCheckBox atrezzoReferencesCheckBox;
	
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
	public ItemReferencePanel( ElementReferenceDataControl elementReferenceDataControl ) {

		// Set the controller
		Controller controller = Controller.getInstance( );
		this.elementReferenceDataControl = elementReferenceDataControl;

		// Take the scene path and the element path
		String scenePath = controller.getSceneImagePath( elementReferenceDataControl.getParentSceneId( ) );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the combo box of items
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JPanel itemIdPanel = new JPanel( );
		itemIdPanel.setLayout( new GridLayout( ) );
		itemsComboBox = new JComboBox( controller.getIdentifierSummary( ).getItemIds( ) );
		itemsComboBox.setSelectedItem( elementReferenceDataControl.getElementId( ) );
		itemsComboBox.addActionListener( new ItemComboBoxListener( ) );
		itemIdPanel.add( itemsComboBox );
		itemIdPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.ItemId" ) ) );
		add( itemIdPanel, c );

		// Create the text area for the documentation
		c.gridy = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( elementReferenceDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.Documentation" ) ) );
		add( documentationPanel, c );

		// Create the button for the conditions
		c.gridy = 2;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.Conditions" ) ) );
		add( conditionsPanel, c );

		// Create image panel
		scenePreviewEditionPanel = new ScenePreviewEditionPanel(scenePath);

		// Create the checkboxes
		JPanel checkBoxesPanel = new JPanel( );
		checkBoxesPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints c2 = new GridBagConstraints( );
		c2.anchor = GridBagConstraints.CENTER;
		c2.weightx = 1;
		itemReferencesCheckBox = new JCheckBox( TextConstants.getText( "ElementReference.ItemReferences" ) );
		itemReferencesCheckBox.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_OBJECT, itemReferencesCheckBox.isSelected( ) );
			}
		} );
		checkBoxesPanel.add( itemReferencesCheckBox, c2 );
		c2.gridx = 1;
		npcReferencesCheckBox = new JCheckBox( TextConstants.getText( "ElementReference.NPCReferences" ) );
		npcReferencesCheckBox.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_CHARACTER, npcReferencesCheckBox.isSelected( ) );
			}
		} );
		checkBoxesPanel.add( npcReferencesCheckBox, c2 );
		c2.gridx = 2;
		atrezzoReferencesCheckBox = new JCheckBox( TextConstants.getText( "ElementReference.AtrezzoReferences" ) );
		atrezzoReferencesCheckBox.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_ATREZZO, atrezzoReferencesCheckBox.isSelected( ) );
			}
		} );
		checkBoxesPanel.add( atrezzoReferencesCheckBox, c2 );

		// Set the values for the categories and checkboxes
		
		scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_OBJECT, controller.getShowItemReferences( ) );
		scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_CHARACTER, controller.getShowNPCReferences( ) );
		scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_ATREZZO, controller.getShowAtrezzoReferences( ) );
		itemReferencesCheckBox.setSelected( controller.getShowItemReferences( ) );
		npcReferencesCheckBox.setSelected( controller.getShowNPCReferences( ) );
		atrezzoReferencesCheckBox.setSelected(controller.getShowAtrezzoReferences());

		// Create and add the resulting panel
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		JPanel completePositionPanel = new JPanel( );
		completePositionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.Position" ) ) );
		completePositionPanel.setLayout( new BorderLayout( ) );
		completePositionPanel.add( checkBoxesPanel, BorderLayout.NORTH );
		completePositionPanel.add( scenePreviewEditionPanel, BorderLayout.CENTER );
		add( completePositionPanel, c );

		// Add the other elements of the scene if a background image was loaded
		if( scenePath != null ) {

			// Add the item references first
			for( ElementReferenceDataControl elementReference : elementReferenceDataControl.getParentSceneItemReferences( ) ) {
				// Check that the item is not added in the "Items" category
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
		}

		scenePreviewEditionPanel.setMovableElement(elementReferenceDataControl);
		// Repaint the panel
		scenePreviewEditionPanel.repaint( );
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
	 * Listener for the items combo box.
	 */
	private class ItemComboBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			elementReferenceDataControl.setElementId( itemsComboBox.getSelectedItem( ).toString( ) );

			// Get the new element, update it and paint the panel
			//String elementPath = Controller.getInstance( ).getElementImagePath( elementReferenceDataControl.getElementId( ) );
			//categoryElementImagePanel.loadElement( elementPath );
			//categoryElementImagePanel.repaint( );
			scenePreviewEditionPanel.recreateElement(elementReferenceDataControl);
			scenePreviewEditionPanel.repaint();
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
