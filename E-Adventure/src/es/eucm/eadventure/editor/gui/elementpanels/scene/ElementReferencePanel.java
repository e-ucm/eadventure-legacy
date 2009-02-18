package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;
import es.eucm.eadventure.editor.gui.treepanel.TreeNodeControl;

public class ElementReferencePanel extends JPanel {

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
	private JComboBox elementsComboBox;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Panel with the editable element painted in it, along with the rest of the elements in the scene.
	 */
	private ScenePreviewEditionPanel scenePreviewEditionPanel;
	
	/**
	 * Split panel that combine the preview and information panel.
	 */
	private JSplitPane infoWithPreview;
	

	/**
	 * Constructor.
	 * 
	 * @param elementReferenceDataControl
	 *            Controller of the element reference
	 */
	public ElementReferencePanel( ElementReferenceDataControl elementReferenceDataControl ) {

		// Set the controller
		Controller controller = Controller.getInstance( );
		this.elementReferenceDataControl = elementReferenceDataControl;

		// Take the scene path and the element path
		String scenePath = controller.getSceneImagePath( elementReferenceDataControl.getParentSceneId( ) );

		

		if (elementReferenceDataControl.getType() == Controller.ITEM_REFERENCE)
			setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.Title" ) ) );
		else if (elementReferenceDataControl.getType() == Controller.NPC_REFERENCE)
			setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPCReference.Title" ) ) );
		else if (elementReferenceDataControl.getType() == Controller.ATREZZO_REFERENCE)
			setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoReference.Title" ) ) );
		
			
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the combo box of elements
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;

		
		JPanel elementIdPanel = new JPanel( );
		elementIdPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints c2 = new GridBagConstraints();
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.gridx = 0;
		c2.gridy = 0;
		c2.weightx = 1.0;

		if (elementReferenceDataControl.getType() == Controller.ITEM_REFERENCE){
			elementsComboBox = new JComboBox( controller.getIdentifierSummary( ).getItemIds( ) );
			elementIdPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.ItemId" ) ) );
		}else if (elementReferenceDataControl.getType() == Controller.NPC_REFERENCE){
			elementsComboBox = new JComboBox( controller.getIdentifierSummary( ).getNPCIds( ) );
			elementIdPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPCReference.NPCId" ) ) );
		}else if (elementReferenceDataControl.getType() == Controller.ATREZZO_REFERENCE){
			elementsComboBox = new JComboBox( controller.getIdentifierSummary( ).getAtrezzoIds() );		
			elementIdPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoReference.AtrezzoId" ) ) );
		}
		elementsComboBox.setSelectedItem( elementReferenceDataControl.getElementId( ) );
		elementsComboBox.addActionListener( new ElementsComboBoxListener( ) );
		elementIdPanel.add( elementsComboBox, c2 );
		
		Icon goToIcon = new ImageIcon( "img/icons/moveNodeRight.png" );
		JButton goToButton = new JButton (goToIcon);
		goToButton.setPreferredSize(new Dimension(20,20));
		goToButton.setToolTipText(TextConstants.getText("ElementReference.GoToReferencedElement"));
		goToButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TreeNodeControl.getInstance().changeTreeNode(ElementReferencePanel.this.elementReferenceDataControl.getReferencedElementDataControl());
			}
		});
		c2.gridx = 1;
		c2.weightx = 0.1;
		elementIdPanel.add( goToButton, c2);

		
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new GridBagLayout());
		infoPanel.add( elementIdPanel, c );

		// Create the text area for the documentation
		c.gridy = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( elementReferenceDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) elementReferenceDataControl.getContent()) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		if (elementReferenceDataControl.getType() == Controller.ITEM_REFERENCE)
			documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.Documentation" ) ) );
		else if (elementReferenceDataControl.getType() == Controller.NPC_REFERENCE)
			documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPCReference.Documentation" ) ) );
		else if (elementReferenceDataControl.getType() == Controller.ATREZZO_REFERENCE)
			documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoReference.Documentation" ) ) );
		infoPanel.add( documentationPanel, c );

		// Create the button for the conditions
		c.gridy = 2;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		if (elementReferenceDataControl.getType() == Controller.ITEM_REFERENCE)
			conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.Conditions" ) ) );
		else if (elementReferenceDataControl.getType() == Controller.NPC_REFERENCE)
			conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "NPCReference.Conditions" ) ) );
		else if (elementReferenceDataControl.getType() == Controller.ATREZZO_REFERENCE)
			conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "AtrezzoReference.Conditions" ) ) );
		infoPanel.add( conditionsPanel, c );

		// Create image panel
		scenePreviewEditionPanel = new ScenePreviewEditionPanel(scenePath);
		scenePreviewEditionPanel.setShowTextEdition(true);


		// Set the values for the categories and checkboxes
		
		scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_OBJECT, controller.getShowItemReferences( ) );
		scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_CHARACTER, controller.getShowNPCReferences( ) );
		scenePreviewEditionPanel.setDisplayCategory(ScenePreviewEditionPanel.CATEGORY_ATREZZO, controller.getShowAtrezzoReferences( ) );

		// Create and add the resulting panel
		c.gridy = 3;
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 1;
		JPanel completePositionPanel = new JPanel( );
		completePositionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.Position" ) ) );
		completePositionPanel.setLayout( new BorderLayout( ) );
		completePositionPanel.add( scenePreviewEditionPanel, BorderLayout.CENTER );
		
		infoWithPreview = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                infoPanel, completePositionPanel);
		infoWithPreview.setOneTouchExpandable(true);
		infoWithPreview.setResizeWeight(0.5);
		infoWithPreview.setContinuousLayout(true);
		infoWithPreview.setDividerLocation(280);
		infoPanel.setMinimumSize(new Dimension(0,200));
		completePositionPanel.setMinimumSize(new Dimension(0,300));
		
		// Set the layout
		setLayout( new BorderLayout( ) );
		add(infoWithPreview,BorderLayout.CENTER);
		
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
		if (elementReferenceDataControl.getSceneDataControl().getTrajectory().hasTrajectory()) {
			scenePreviewEditionPanel.setTrajectory((Trajectory) elementReferenceDataControl.getSceneDataControl().getTrajectory().getContent());
			for (NodeDataControl nodeDataControl: elementReferenceDataControl.getSceneDataControl().getTrajectory().getNodes())
				scenePreviewEditionPanel.addNode(nodeDataControl);
			if (elementReferenceDataControl.getInfluenceArea() != null) 
				scenePreviewEditionPanel.addInfluenceArea(elementReferenceDataControl.getInfluenceArea());
		}

		scenePreviewEditionPanel.setFixedSelectedElement(true);

		// Repaint the panel
		scenePreviewEditionPanel.repaint( );
		

	}

	/**
	 * Listener for the items combo box.
	 */
	private class ElementsComboBoxListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			elementReferenceDataControl.setElementId( elementsComboBox.getSelectedItem( ).toString( ) );

			// Get the new element, update it and paint the panel
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
