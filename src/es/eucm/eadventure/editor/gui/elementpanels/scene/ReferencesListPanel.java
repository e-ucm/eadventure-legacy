package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.book.BookParagraphDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementContainer;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ReferencesListDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.elementpanels.book.ImageBookParagraphPanel;
import es.eucm.eadventure.editor.gui.elementpanels.book.ParagraphsTable;
import es.eucm.eadventure.editor.gui.elementpanels.book.TextBookParagraphPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.ElementReferencesTable;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ReferencesListPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel tablePanel;
	
	private ElementReferencesTable table;
	
	private JButton deleteButton;
	
	private JButton moveUpButton;
	
	private JButton moveDownButton;

	private ScenePreviewEditionPanel spep;
	
	private JSplitPane tableWithSplit;
	
	private JSplitPane infoWithSpep;
	
	private JPanel infoPanel;
	
	private ReferencesListDataControl referencesListDataControl;
	
	private JComboBox itemsComboBox;
	
	private JTextArea documentationTextArea;
	
	
	/**
	 * Constructor.
	 * 
	 * @param itemReferencesListDataControl
	 *            Item references list controller
	 */
	public ReferencesListPanel( ReferencesListDataControl referencesListDataControl ) {

		this.referencesListDataControl = referencesListDataControl;
		
		// Take the path of the background
		String scenePath = Controller.getInstance( ).getSceneImagePath( referencesListDataControl.getParentSceneId( ) );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		
		//Create the infoPanel
		
		infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "ElementList.Empty" ));
		infoPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText("ElementList.Info" ) ) );
		infoPanel.add( informationTextPane,BorderLayout.CENTER);
		
		// Create the scene preview edition panel
		spep = new ScenePreviewEditionPanel(scenePath);
		spep.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReferencesList.PreviewTitle" ) ) );

		// Add the item references if an image was loaded
		if( scenePath != null ) {
			// Add the item references
			for( ElementReferenceDataControl elementReference : referencesListDataControl.getItemReferences( ) ) {
				spep.addElement(ScenePreviewEditionPanel.CATEGORY_OBJECT, elementReference);
			}
			for( ElementReferenceDataControl elementReference : referencesListDataControl.getAtrezzoReferences( ) ) {
				spep.addElement(ScenePreviewEditionPanel.CATEGORY_ATREZZO, elementReference);
			}
			for( ElementReferenceDataControl elementReference : referencesListDataControl.getNPCReferences( ) ) {
				spep.addElement(ScenePreviewEditionPanel.CATEGORY_CHARACTER, elementReference);
			}
			spep.addPlayer(referencesListDataControl.getSceneDataControl(), referencesListDataControl.getPlayerImage());
		}
		//Create a split pane with the two panels: info panel and preview panel
		infoWithSpep = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                infoPanel, spep);
		infoWithSpep.setOneTouchExpandable(true);
		infoWithSpep.setResizeWeight(0.5);
		infoWithSpep.setContinuousLayout(true);
		infoWithSpep.setDividerLocation(280);


		//Provide minimum sizes for the two components in the split pane
		Dimension minimumSize = new Dimension(300, 280);
		//infoPanel.setMinimumSize(minimumSize);
		infoPanel.setPreferredSize(minimumSize);
		spep.setMinimumSize(minimumSize);
		
	
		// Add the table which contains the elements in the scene (items, atrezzo and npc) with its layer position
		createReferencesTablePanel();
		
		// set the listener to get the events in the preview panel that implies changes in the table
		spep.setElementReferenceSelectionListener(table);
		
		//Create a split pane with the two scroll panes in it
		tableWithSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                infoWithSpep, tablePanel);
		tableWithSplit.setOneTouchExpandable(true);
		tableWithSplit.setDividerLocation(535);
		//tableWithSplit.setContinuousLayout(true);

		//Provide minimum sizes for the two components in the split pane
		minimumSize = new Dimension(200, 100);
	
		tableWithSplit.setResizeWeight(0.5);
	
		
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReferencesList.Title" ) ) );

		
		
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.BOTH;
		
		setLayout( new BorderLayout( ) );
		add(tableWithSplit,BorderLayout.CENTER);
	}
	
	private void prepareInformationPanel(){
		// Set the controller
		Controller controller = Controller.getInstance( );
		
		infoPanel.removeAll();
		// Set the layout
		infoPanel.setLayout( new GridBagLayout( ) );
		infoPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the combo box of items
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JPanel itemIdPanel = new JPanel( );
		itemIdPanel.setLayout( new GridLayout( ) );
		itemsComboBox = new JComboBox( referencesListDataControl.getAllReferencesId() );
		if (referencesListDataControl.getLastElementContainer().isPlayer())
			itemsComboBox.setSelectedItem( TextConstants.getText("ElementList.Player") );
		else 
			itemsComboBox.setSelectedItem( referencesListDataControl.getLastElementContainer().getErdc().getElementId( ) );
		itemsComboBox.addActionListener( new ItemComboBoxListener( ) );
		itemIdPanel.add( itemsComboBox );
		itemIdPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.ItemId" ) ) );
		infoPanel.add( itemIdPanel, c );

		// Create the text area for the documentation
		c.gridy = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		if (referencesListDataControl.getLastElementContainer().isPlayer())
			documentationTextArea = new JTextArea(TextConstants.getText("ElementList.PlayerDoc"),4,0);
		else
			documentationTextArea = new JTextArea( referencesListDataControl.getLastElementContainer().getErdc().getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationTextAreaChangesListener( ) );
		documentationTextArea.setMinimumSize(new Dimension(0,100));
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.Documentation" ) ) );
		infoPanel.add( documentationPanel, c );

		// Create the button for the conditions
		c.gridy = 2;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReference.Conditions" ) ) );
		infoPanel.add( conditionsPanel, c );

		
	}
	
	
	private void updateSelectedElementReference(){
		// No valid row is selected
		if (table.getSelectedRow( )<0 || table.getSelectedRow( )>=referencesListDataControl.getAllReferencesDataControl().size( )){
			
			// set information pane as no element selected
			JTextPane informationTextPane = new JTextPane( );
			informationTextPane.setEditable( false );
			informationTextPane.setBackground( getBackground( ) );
			informationTextPane.setText( TextConstants.getText( "ElementList.Empty" ));
			infoPanel.setLayout(new BorderLayout());
			infoPanel.add( informationTextPane, BorderLayout.CENTER);
			itemsComboBox.setSelectedIndex(table.getSelectedRow());
			//Disable delete button
			deleteButton.setEnabled( false );
			//Disable moveUp and moveDown buttons
			moveUpButton.setEnabled( false );
			moveDownButton.setEnabled( false );
		}
		
		//When a element has been selected
		else {
			int selectedReference = table.getSelectedRow( );
			ElementContainer elementContainer = referencesListDataControl.getAllReferencesDataControl().get( selectedReference);
			referencesListDataControl.setLastElementContainer(elementContainer);
			spep.setSelectedElement(elementContainer.getErdc(),elementContainer.getImage(),referencesListDataControl.getSceneDataControl());

			prepareInformationPanel();
			
			// Enable delete button
			deleteButton.setEnabled( true );
			//Enable moveUp and moveDown buttons when there is more than one element
			moveUpButton.setEnabled( referencesListDataControl.getAllReferencesDataControl().size( )>1 && selectedReference>0);
			moveDownButton.setEnabled( referencesListDataControl.getAllReferencesDataControl().size( )>1 && selectedReference<table.getRowCount( )-1 );

		}
		updateUI( );
		
		spep.repaint();
	}
	
	private void createReferencesTablePanel(){
		// Create the main panel
		tablePanel = new JPanel( new BorderLayout());
		
		// Create the info panel (NORTH)
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "ItemReferenceTable.ListDescription" , Integer.toString( referencesListDataControl.getAllReferencesDataControl().size()) ));
		tablePanel.add( informationTextPane, BorderLayout.NORTH );
		
		// Create the table (CENTER)
		table = new ElementReferencesTable(referencesListDataControl);
		table.addMouseListener( new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				// By default the JTable only selects the nodes with the left click of the mouse
				// With this code, we spread a new call everytime the right mouse button is pressed
				if( e.getButton( ) == MouseEvent.BUTTON3 ) {
					// Create new event (with the left mouse button)
					MouseEvent newEvent = new MouseEvent( e.getComponent( ), e.getID( ), e.getWhen( ), MouseEvent.BUTTON1_MASK, e.getX( ), e.getY( ), e.getClickCount( ), e.isPopupTrigger( ) );

					// Take the listeners and make the calls
					for( MouseListener mouseListener : e.getComponent( ).getMouseListeners( ) )
						mouseListener.mousePressed( newEvent );
					
				}
			}
			
			public void mouseClicked(MouseEvent evt){
				if (evt.getButton( ) == MouseEvent.BUTTON3){
					JPopupMenu menu = getCompletePopupMenu();
					menu.show( evt.getComponent( ), evt.getX( ), evt.getY( ) );
				}
			}
		});
		tablePanel.add( new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER );
		
		table.getSelectionModel( ).addListSelectionListener( new ListSelectionListener(){
			public void valueChanged( ListSelectionEvent e ) {
				updateSelectedElementReference();
			}
		});
		
		//Create the buttons panel (SOUTH)
		JPanel buttonsPanel = new JPanel();
		JButton newButton = new JButton(new ImageIcon("img/icons/addNode.png"));
		newButton.setContentAreaFilled( false );
		newButton.setMargin( new Insets(0,0,0,0) );
		newButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.AddParagraph" ) );
		newButton.addMouseListener( new MouseAdapter(){
			public void mouseClicked (MouseEvent evt){
				JPopupMenu menu= getAddChildPopupMenu();
				menu.show( evt.getComponent( ), evt.getX( ), evt.getY( ) );
			}
		});
		deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteButton.setContentAreaFilled( false );
		deleteButton.setMargin( new Insets(0,0,0,0) );
		deleteButton.setToolTipText( TextConstants.getText( "BookParagraphs.DeleteParagraph" ) );
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				delete();
			}
		});
		deleteButton.setEnabled(false);
		moveUpButton = new JButton(new ImageIcon("img/icons/moveNodeUp.png"));
		moveUpButton.setContentAreaFilled( false );
		moveUpButton.setMargin( new Insets(0,0,0,0) );
		moveUpButton.setToolTipText( TextConstants.getText( "BookParagraphs.MoveParagraphUp" ) );
		moveUpButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				moveUp();
			}
		});
		moveUpButton.setEnabled(false);
		moveDownButton = new JButton(new ImageIcon("img/icons/moveNodeDown.png"));
		moveDownButton.setContentAreaFilled( false );
		moveDownButton.setMargin( new Insets(0,0,0,0) );
		moveDownButton.setToolTipText( TextConstants.getText( "BookParagraphs.MoveParagraphDown" ) );
		moveDownButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				moveDown();
			}
		});
		moveDownButton.setEnabled(false);

		buttonsPanel.add( newButton );
		buttonsPanel.add( deleteButton );
		buttonsPanel.add( moveUpButton );
		buttonsPanel.add( moveDownButton );
		
		//tablePanel.add(table, BorderLayout.NORTH);
		tablePanel.add( buttonsPanel, BorderLayout.SOUTH );
		tablePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReferenceTable.TableBorder" ) ) );
	
	
	}
	
	private void delete( ) {
		ElementContainer element = referencesListDataControl.getAllReferencesDataControl().get( table.getSelectedRow( ) );
		if (referencesListDataControl.deleteElement( element.getErdc() )){
			if (element.isPlayer()){
				spep.removeElement(transformType(element.getErdc().getType()), element.getErdc());
				table.clearSelection( );
				table.updateUI( );
			}
		}
	}
	
	private void moveUp(){
		int selectedRow = table.getSelectedRow( );
		ElementContainer element = referencesListDataControl.getAllReferencesDataControl().get( selectedRow );
		if (referencesListDataControl.moveElementUp( element.getErdc() )){
			table.getSelectionModel( ).setSelectionInterval( selectedRow-1, selectedRow-1 );
			table.updateUI( );
		}
	}
	
	private void moveDown(){
		int selectedRow = table.getSelectedRow( );
		ElementContainer element = referencesListDataControl.getAllReferencesDataControl().get( selectedRow );
		if (referencesListDataControl.moveElementDown( element.getErdc() )){
			table.getSelectionModel( ).setSelectionInterval( selectedRow+1, selectedRow+1 );
			table.updateUI( );
		}
	}
	
	
	/**
	 * Returns a popup menu with the add operations.
	 * 
	 * @return Popup menu with child adding operations
	 */
	public JPopupMenu getAddChildPopupMenu( ) {
		JPopupMenu addChildPopupMenu = new JPopupMenu( );

		// If the element accepts children
		if( referencesListDataControl.getAddableElements().length > 0 ) {
			// Add an entry in the popup menu for each type of possible child
			for( int type : referencesListDataControl.getAddableElements( ) ) {
				JMenuItem addChildMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.AddElement" + type ) );
				addChildMenuItem.setEnabled( true );
				addChildMenuItem.addActionListener( new AddElementReferenceActionListener( type ) );
				addChildPopupMenu.add( addChildMenuItem );
			}
		}

		// If no element can be added, insert a disabled general option
		else {
			JMenuItem addChildMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.AddElement" ) );
			addChildMenuItem.setEnabled( false );
			addChildPopupMenu.add( addChildMenuItem );
		}

		return addChildPopupMenu;
	}
	
	
	/**
	 * Returns a popup menu with all the operations.
	 * 
	 * @return Popup menu with all operations
	 */
	public JPopupMenu getCompletePopupMenu( ) {
		JPopupMenu completePopupMenu = getAddChildPopupMenu( );

		// Separator
		completePopupMenu.addSeparator( );

		// Create and add the delete item
		JMenuItem deleteMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.DeleteElement" ) );
		deleteMenuItem.setEnabled( deleteButton.isEnabled( ));
		deleteMenuItem.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				delete( );
			}
		} );
		completePopupMenu.add( deleteMenuItem );

		// Separator
		completePopupMenu.addSeparator( );

		// Create and add the move up and down item
		JMenuItem moveUpMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.MoveElementUp" ) );
		JMenuItem moveDownMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.MoveElementDown" ) );
		moveUpMenuItem.setEnabled( moveUpButton.isEnabled( ) );
		moveDownMenuItem.setEnabled( moveDownButton.isEnabled( ) );
		moveUpMenuItem.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				moveUp( );
			}
		} );
		moveDownMenuItem.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				moveDown( );
			}
		} );
		completePopupMenu.add( moveUpMenuItem );
		completePopupMenu.add( moveDownMenuItem );

		return completePopupMenu;
	}
	
	/**
	 * Catch the type of the element reference control data and return the associated scene preview category
	 * 
	 * @param type
	 * @return
	 * 			the scene preview category
	 */
	private int transformType(int type){
		int category = 0;
		if( type == Controller.ITEM_REFERENCE ) 
			category = ScenePreviewEditionPanel.CATEGORY_OBJECT;
		else if( type == Controller.ATREZZO_REFERENCE )
			 category = ScenePreviewEditionPanel.CATEGORY_ATREZZO;
		else if( type == Controller.NPC_REFERENCE )
			 category = ScenePreviewEditionPanel.CATEGORY_CHARACTER;
		else if (type == -1)
			category = ScenePreviewEditionPanel.CATEGORY_PLAYER;
		return category;
	}
	
	/**
	 * This class is the action listener for the add buttons of the popup menus.
	 */
	private class AddElementReferenceActionListener implements ActionListener {

		/**
		 * Type of element to be created.
		 */
		int type;

		/**
		 * Constructor
		 * 
		 * @param type
		 *            Type of element the listener must call
		 */
		public AddElementReferenceActionListener( int type ) {
			this.type = type;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			
			// var for keep the scene preview type of the new element reference
			int category;
			
			if (referencesListDataControl.addElement( type )){
				
				category = transformType(type);
				
				/*int selectedRow = table.getSelectedRow( ); 
				//If a row is selected, move the new element up until it is under the selected one
				if (selectedRow>=0 && selectedRow<table.getRowCount( )){
					
					//We need size-2-selectedRow movements
					ElementReferenceDataControl element = referencesListDataControl.getAllReferencesDataControl().get(referencesListDataControl.getAllReferencesDataControl().size()-1);
					for (int i=0; i<table.getRowCount( )-2-table.getSelectedRow( ); i++){
						referencesListDataControl.moveElementUp( element );
					}
					
					//Set the new element reference selected
					table.clearSelection( );
					table.getSelectionModel( ).setSelectionInterval( selectedRow+1, selectedRow+1 );
				}*/ 
				if (category!=0&&referencesListDataControl.getLastElementContainer()!=null){
				// it is not necessary to check if it is an player element container because never a player will be added
				spep.addElement(category, referencesListDataControl.getLastElementContainer().getErdc());
				table.updateUI( );
				}
			}
		}
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
			referencesListDataControl.getLastElementContainer().getErdc().setDocumentation( documentationTextArea.getText( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate( DocumentEvent arg0 ) {
			// Set the new content
			referencesListDataControl.getLastElementContainer().getErdc().setDocumentation( documentationTextArea.getText( ) );
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
			//TODO check player compatibility
			referencesListDataControl.getLastElementContainer().getErdc().setElementId( itemsComboBox.getSelectedItem( ).toString( ) );

			// Get the new element, update it and paint the panel
			//String elementPath = Controller.getInstance( ).getElementImagePath( elementReferenceDataControl.getElementId( ) );
			//categoryElementImagePanel.loadElement( elementPath );
			//categoryElementImagePanel.repaint( );
			spep.recreateElement(referencesListDataControl.getLastElementContainer().getErdc());
			spep.repaint();
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
			//TODO check player compatibility
			new ConditionsDialog( referencesListDataControl.getLastElementContainer().getErdc().getConditions( ) );
		}
	}



	
}
