package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.basic.BasicSplitPaneDivider;

import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementContainer;
import es.eucm.eadventure.editor.control.controllers.scene.ElementReferenceDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.NodeDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ReferencesListDataControl;
import es.eucm.eadventure.editor.control.tools.general.MovePlayerLayerInTableTool;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.elementpanels.general.TableScrollPane;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ReferencesListPanel extends JPanel implements DataControlsPanel,Updateable {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int HORIZONTAL_SPLIT_POSITION = 140;
	
	private JPanel tablePanel;
	
	private ElementReferencesTable table;
	
	private JButton deleteButton;
	
	private JButton moveUpButton;
	
	private JButton moveDownButton;

	private ScenePreviewEditionPanel spep;
	
	private JSplitPane tableWithSplit;
	
	BasicSplitPaneDivider horizontalDivider;
	
	BasicSplitPaneDivider verticalDivider;
	
	private ReferencesListDataControl referencesListDataControl;
		
	/**
	 * Constructor.
	 * 
	 * @param itemReferencesListDataControl
	 *            Item references list controller
	 */
	public ReferencesListPanel( ReferencesListDataControl referencesListDataControl ) {
		this.referencesListDataControl = referencesListDataControl;
		String scenePath = Controller.getInstance( ).getSceneImagePath( referencesListDataControl.getParentSceneId( ) );
				
		spep = new ScenePreviewEditionPanel(true, scenePath);
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
			if (!Controller.getInstance().isPlayTransparent()&& referencesListDataControl.getSceneDataControl().isAllowPlayer())
				spep.addPlayer(referencesListDataControl.getSceneDataControl(), referencesListDataControl.getPlayerImage());
		}
		
		// Add the table which contains the elements in the scene (items, atrezzo and npc) with its layer position
		createReferencesTablePanel();
		
		// set the listener to get the events in the preview panel that implies changes in the table
		spep.setElementReferenceSelectionListener(table);
		spep.setShowTextEdition(true);
		
		if (referencesListDataControl.getSceneDataControl().getTrajectory().hasTrajectory()) {
			spep.setTrajectory((Trajectory) referencesListDataControl.getSceneDataControl().getTrajectory().getContent());
			for (NodeDataControl nodeDataControl: referencesListDataControl.getSceneDataControl().getTrajectory().getNodes())
				spep.addNode(nodeDataControl);
			spep.setShowInfluenceArea(true);
		}
		

		tableWithSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, spep);
		tableWithSplit.setOneTouchExpandable(true);
		tableWithSplit.setDividerLocation(HORIZONTAL_SPLIT_POSITION);
		tableWithSplit.setContinuousLayout(true);
		tableWithSplit.setResizeWeight(0.5);
		tableWithSplit.setDividerSize(10);
	
		setLayout( new BorderLayout( ) );
		add(tableWithSplit,BorderLayout.CENTER);
	}
		
	private void updateSelectedElementReference(){
		// No valid row is selected
		if (table.getSelectedRow( )<0 || table.getSelectedRow( )>=referencesListDataControl.getAllReferencesDataControl().size( )){
			
			// set information pane as no element selected
			JTextPane informationTextPane = new JTextPane( );
			informationTextPane.setEditable( false );
			informationTextPane.setBackground( getBackground( ) );
			informationTextPane.setText( TextConstants.getText( "ElementList.Empty" ));
			
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
			//spep.repaint();
			// Enable delete button
			if (elementContainer.isPlayer())
				deleteButton.setEnabled( false );
			else 
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
		tablePanel = new JPanel(new BorderLayout());
		
		
		JPanel temp = new JPanel();
		temp.setLayout(new GridLayout(2,1));
		JTextPane layerTextPane = new JTextPane( );
		layerTextPane.setEditable( false );
		layerTextPane.setBackground( getBackground( ) );
		layerTextPane.setText( TextConstants.getText( "ItemReferenceTable.LayerExplanation" ));
		
		temp.add(layerTextPane);
		
		JCheckBox isAllowPlayerLayer = new JCheckBox(TextConstants.getText("Scene.AllowPlayer"),referencesListDataControl.getSceneDataControl().isAllowPlayer());
		isAllowPlayerLayer.setSelected( referencesListDataControl.getSceneDataControl().isAllowPlayer() );
		isAllowPlayerLayer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				referencesListDataControl.getSceneDataControl().changeAllowPlayerLayer(((JCheckBox) arg0.getSource()).isSelected(), null);
			}
		});
		temp.add(isAllowPlayerLayer);
		
		if (!Controller.getInstance().isPlayTransparent())
			tablePanel.add(temp, BorderLayout.SOUTH);
		else
			tablePanel.add(layerTextPane,BorderLayout.SOUTH);
		
		// Create the table (CENTER)
		table = new ElementReferencesTable(referencesListDataControl, spep);

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
		

		tablePanel.add( new TableScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) ,BorderLayout.CENTER);
		
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
		newButton.setBorder(BorderFactory.createEmptyBorder());
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
		deleteButton.setBorder(BorderFactory.createEmptyBorder());
		deleteButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.Delete" ) );
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				delete();
			}
		});
		deleteButton.setEnabled(false);
		moveUpButton = new JButton(new ImageIcon("img/icons/moveNodeUp.png"));
		moveUpButton.setContentAreaFilled( false );
		moveUpButton.setMargin( new Insets(0,0,0,0) );
		moveUpButton.setBorder(BorderFactory.createEmptyBorder());
		moveUpButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.MoveUp" ) );
		moveUpButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				moveUp();
			}
		});
		moveUpButton.setEnabled(false);
		moveDownButton = new JButton(new ImageIcon("img/icons/moveNodeDown.png"));
		moveDownButton.setContentAreaFilled( false );
		moveDownButton.setMargin( new Insets(0,0,0,0) );
		moveDownButton.setBorder(BorderFactory.createEmptyBorder());
		moveDownButton.setToolTipText( TextConstants.getText( "ItemReferenceTable.MoveDown" ) );
		moveDownButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				moveDown();
			}
		});
		moveDownButton.setEnabled(false);

		buttonsPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		buttonsPanel.add( newButton , c );
		c.gridy++;
		buttonsPanel.add( deleteButton , c );
		c.anchor = GridBagConstraints.SOUTH;
		c.gridy++;
		buttonsPanel.add( moveUpButton , c );
		c.gridy++;
		buttonsPanel.add( moveDownButton , c );
		
		
		tablePanel.add( buttonsPanel,BorderLayout.EAST);
//		tablePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ItemReferenceTable.TableBorder" ) ) );
	
	
	}
	
	private void delete( ) {
		ElementContainer element = referencesListDataControl.getAllReferencesDataControl().get( table.getSelectedRow( ) );
		if (referencesListDataControl.deleteElement( element.getErdc(), true )){
			if (!element.isPlayer()){
				spep.removeElement(transformType(element.getErdc().getType()), element.getErdc());
				table.clearSelection( );
				table.changeSelection(0, 1, false, false);
				table.updateUI( );
			}
		}
	}
	
	private void moveUp(){
		Controller.getInstance().addTool(new MovePlayerLayerInTableTool(referencesListDataControl,table,true));
		
	}
	

	
	private void moveDown(){
		Controller.getInstance().addTool(new MovePlayerLayerInTableTool(referencesListDataControl,table,false));
		
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

		public void actionPerformed( ActionEvent e ) {
			
			int category;
			if (referencesListDataControl.addElement( type, null )){
				category = transformType(type);
	
				if (category!=0&&referencesListDataControl.getLastElementContainer()!=null){
					// it is not necessary to check if it is an player element container because never a player will be added
					spep.addElement(category, referencesListDataControl.getLastElementContainer().getErdc());
					spep.setSelectedElement(referencesListDataControl.getLastElementContainer().getErdc());
					spep.repaint();
					int layer = referencesListDataControl.getLastElementContainer().getErdc().getElementReference().getLayer();
					table.getSelectionModel().setSelectionInterval(layer, layer);
					table.updateUI( );
				}
			}
		}
	}
	
	public void setSelectedItem(List<DataControl> path) {
		if (path.size() > 0) {
			for (int i = 0 ; i < referencesListDataControl.getAllReferencesDataControl().size(); i++) {
				if (referencesListDataControl.getAllReferencesDataControl().get(i).getErdc() == path.get(path.size() -1))
					table.changeSelection(i, i, false, false);
			}
		}
	}

	public boolean updateFields() {
	   // updateSelectedElementReference();
	    return true;
	}
}