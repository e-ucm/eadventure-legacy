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
import es.eucm.eadventure.editor.gui.otherpanels.positionimagepanels.CategoryElementImagePanel;
import es.eucm.eadventure.editor.gui.otherpanels.positionpanel.PositionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.positionpanel.PositionPanelListener;

public class AtrezzoReferencePanel extends JPanel implements PositionPanelListener {
	
	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Link to the element reference controller.
	 */
	private ElementReferenceDataControl elementReferenceDataControl;

	/**
	 * Combo box for the atrezzo items in the script.
	 */
	private JComboBox atrezzoComboBox;

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
	private CategoryElementImagePanel categoryElementImagePanel;

	/**
	 * Constructor.
	 * 
	 * @param elementReferenceDataControl
	 *            Controller of the element reference
	 */
	public AtrezzoReferencePanel( ElementReferenceDataControl elementReferenceDataControl ) {

		// Set the controller
		Controller controller = Controller.getInstance( );
		this.elementReferenceDataControl = elementReferenceDataControl;

		// Take the scene path and the element path
		String scenePath = controller.getSceneImagePath( elementReferenceDataControl.getParentSceneId( ) );
		String elementPath = controller.getElementImagePath( elementReferenceDataControl.getElementId( ) );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoReference.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the combo box of items
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JPanel atrezzoIdPanel = new JPanel( );
		atrezzoIdPanel.setLayout( new GridLayout( ) );
		atrezzoComboBox = new JComboBox( controller.getIdentifierSummary( ).getItemIds( ) );
		atrezzoComboBox.setSelectedItem( elementReferenceDataControl.getElementId( ) );
		atrezzoComboBox.addActionListener( new ItemComboBoxListener( ) );
		atrezzoIdPanel.add( atrezzoComboBox );
		atrezzoIdPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoReference.AtrezzoId" ) ) );
		add( atrezzoIdPanel, c );

		// Create the text area for the documentation
		c.gridy = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( elementReferenceDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoReference.Documentation" ) ) );
		add( documentationPanel, c );

		// Create the button for the conditions
		c.gridy = 2;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoReference.Conditions" ) ) );
		add( conditionsPanel, c );

		// Create image panel
		categoryElementImagePanel = new CategoryElementImagePanel( 3, scenePath, elementPath );
		PositionPanel elementPositionPanel = new PositionPanel( this, categoryElementImagePanel );
		elementPositionPanel.setPosition( elementReferenceDataControl.getElementX( ), elementReferenceDataControl.getElementY( ) );

		// Create the checkboxes
		JPanel checkBoxesPanel = new JPanel( );
		checkBoxesPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints c2 = new GridBagConstraints( );
		c2.anchor = GridBagConstraints.CENTER;
		c2.weightx = 1;
		itemReferencesCheckBox = new JCheckBox( TextConstants.getText( "ElementReference.ItemReferences" ) );
		itemReferencesCheckBox.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				// Set the items visible or invisible
				categoryElementImagePanel.setCategoryVisible( 0, itemReferencesCheckBox.isSelected( ) );
				categoryElementImagePanel.repaint( );
			}
		} );
		checkBoxesPanel.add( itemReferencesCheckBox, c2 );
		c2.gridx = 1;
		npcReferencesCheckBox = new JCheckBox( TextConstants.getText( "ElementReference.NPCReferences" ) );
		npcReferencesCheckBox.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				// Set the items visible or invisible
				categoryElementImagePanel.setCategoryVisible( 1, npcReferencesCheckBox.isSelected( ) );
				categoryElementImagePanel.repaint( );
			}
		} );
		checkBoxesPanel.add( npcReferencesCheckBox, c2 );
		c2.gridx = 2;
		atrezzoReferencesCheckBox = new JCheckBox( TextConstants.getText( "ElementReference.AtrezzoReferences" ) );
		atrezzoReferencesCheckBox.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent e ) {
				// Set the items visible or invisible
				categoryElementImagePanel.setCategoryVisible( 2, atrezzoReferencesCheckBox.isSelected( ) );
				categoryElementImagePanel.repaint( );
			}
		} );
		checkBoxesPanel.add( atrezzoReferencesCheckBox, c2 );

		// Set the values for the categories and checkboxes
		categoryElementImagePanel.setCategoryVisible( 0, controller.getShowItemReferences( ) );
		categoryElementImagePanel.setCategoryVisible( 1, controller.getShowNPCReferences( ) );
		categoryElementImagePanel.setCategoryVisible( 2, controller.getShowAtrezzoReferences( ) );
		itemReferencesCheckBox.setSelected( controller.getShowItemReferences( ) );
		npcReferencesCheckBox.setSelected( controller.getShowNPCReferences( ) );
		atrezzoReferencesCheckBox.setSelected(controller.getShowAtrezzoReferences());

		// Create and add the resulting panel
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		JPanel completePositionPanel = new JPanel( );
		completePositionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoReference.Position" ) ) );
		completePositionPanel.setLayout( new BorderLayout( ) );
		completePositionPanel.add( checkBoxesPanel, BorderLayout.NORTH );
		completePositionPanel.add( elementPositionPanel, BorderLayout.CENTER );
		add( completePositionPanel, c );

		// Add the other elements of the scene if a background image was loaded
		if( scenePath != null ) {

			// Add the item references first
			for( ElementReferenceDataControl elementReference : elementReferenceDataControl.getParentSceneItemReferences( ) ) {
				// Check that the item is not added in the "Items" category
				if( !elementReferenceDataControl.getElementId( ).equals( elementReference.getElementId( ) ) ) {
					String itemPath = controller.getElementImagePath( elementReference.getElementId( ) );
					categoryElementImagePanel.addCategoryElement( 0, itemPath, elementReference.getElementX( ), elementReference.getElementY( ) );
				}
			}

			// Add then the character references
			for( ElementReferenceDataControl elementReference : elementReferenceDataControl.getParentSceneNPCReferences( ) ) {
				String itemPath = controller.getElementImagePath( elementReference.getElementId( ) );
				categoryElementImagePanel.addCategoryElement( 1, itemPath, elementReference.getElementX( ), elementReference.getElementY( ) );
			}
			
			//Add atrezzo references
			for( ElementReferenceDataControl elementReference : elementReferenceDataControl.getParentSceneAtrezzoReferences( ) ) {
				// Check that the atrezzo item is not added in the "Characters" category
				if( !elementReferenceDataControl.getElementId( ).equals( elementReference.getElementId( ) ) ) {
					String atrezzoPath = controller.getElementImagePath( elementReference.getElementId( ) );
					categoryElementImagePanel.addCategoryElement( 2, atrezzoPath, elementReference.getElementX( ), elementReference.getElementY( ) );
				}
			}
		}

		// Repaint the panel
		elementPositionPanel.repaint( );
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.editor.gui.editpanels.positionpanels.PositionPanelListener#updatePositionValues(int,
	 *      int)
	 */
	public void updatePositionValues( int x, int y ) {
		// Set the new data when changed
		elementReferenceDataControl.setElementPosition( x, y );
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
			elementReferenceDataControl.setElementId( atrezzoComboBox.getSelectedItem( ).toString( ) );

			// Get the new element, update it and paint the panel
			String elementPath = Controller.getInstance( ).getElementImagePath( elementReferenceDataControl.getElementId( ) );
			categoryElementImagePanel.loadElement( elementPath );
			categoryElementImagePanel.repaint( );
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
