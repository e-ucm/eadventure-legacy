package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.control.controllers.general.CustomActionDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ActionsTable;

public class ActionPanel extends JPanel{

    /**
     * 
     */
    private static final long serialVersionUID = 4806429175992295071L;

    
    private static final int HORIZONTAL_SPLIT_POSITION = 140;
    
    private ActionsListDataControl dataControl;
    
    private ActionTypePanel actionPanel;
    
    private JPanel actionPanelContainer;
    
    private ActionsTable actionsTable;
    
    private JButton deleteButton;
    
    private JButton moveUpButton;
	
    private JButton moveDownButton;
    
    private JSplitPane tableWithSplit;
    
    private JPanel tablePanel;

    
    public ActionPanel(ActionsListDataControl dataControl, ActionTypePanel actionPanel){
	super();
	this.dataControl = dataControl;
	this.actionPanel = actionPanel; 
	//this.actionPanel = new ActionPropertiesPanel(dataControl.getActions().get(0));
	setLayout( new GridBagLayout( ) );
	setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActionsList.Title" ) ) );
	GridBagConstraints c = new GridBagConstraints( );
	c.insets = new Insets( 5, 5, 5, 5 );

	// Create the text area for the documentation
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx = 1;
	tablePanel = new JPanel();
	actionsTable =  new ActionsTable(dataControl);
	actionsTable.addMouseListener( new MouseAdapter(){
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
	

	
	JPanel buttonsPanel = new JPanel();
	JButton newButton = new JButton(new ImageIcon("img/icons/addNode.png"));
	newButton.setContentAreaFilled( false );
	newButton.setMargin( new Insets(0,0,0,0) );
	newButton.setToolTipText( TextConstants.getText( "ActionsList.Add" ) );
	newButton.addMouseListener( new MouseAdapter(){
		public void mouseClicked (MouseEvent evt){
			JPopupMenu menu= getAddChildPopupMenu();
			menu.show( evt.getComponent( ), evt.getX( ), evt.getY( ) );
		}
	});
	deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
	deleteButton.setContentAreaFilled( false );
	deleteButton.setMargin( new Insets(0,0,0,0) );
	deleteButton.setToolTipText( TextConstants.getText( "ActionsList.Delete" ) );
	deleteButton.addActionListener(new ActionListener(){
		public void actionPerformed( ActionEvent e ) {
			delete();
		}
	});
	deleteButton.setEnabled(false);
	moveUpButton = new JButton(new ImageIcon("img/icons/moveNodeUp.png"));
	moveUpButton.setContentAreaFilled( false );
	moveUpButton.setMargin( new Insets(0,0,0,0) );
	moveUpButton.setToolTipText( TextConstants.getText( "ActionsList.MoveUp" ) );
	moveUpButton.addActionListener( new ActionListener(){
		public void actionPerformed( ActionEvent e ) {
			moveUp();
		}
	});
	moveUpButton.setEnabled(false);
	moveDownButton = new JButton(new ImageIcon("img/icons/moveNodeDown.png"));
	moveDownButton.setContentAreaFilled( false );
	moveDownButton.setMargin( new Insets(0,0,0,0) );
	moveDownButton.setToolTipText( TextConstants.getText( "ActionsList.MoveDown" ) );
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
	
	tablePanel.setLayout(new BorderLayout());
	tablePanel.add(buttonsPanel,BorderLayout.SOUTH);
	
	
	tablePanel.add( new JScrollPane(actionsTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS) ,BorderLayout.CENTER);
	
	
	actionsTable.getSelectionModel( ).addListSelectionListener( new ListSelectionListener(){
		public void valueChanged( ListSelectionEvent e ) {
			updateSelectedElementReference();
		}
	});
	JTextPane informationTextPane = new JTextPane( );
	informationTextPane.setEditable( false );
	informationTextPane.setBackground( getBackground( ) );
	informationTextPane.setText( TextConstants.getText( "ActionList.Empty" ));
	actionPanelContainer = new JPanel(new BorderLayout());
	actionPanelContainer.add(informationTextPane,BorderLayout.CENTER);
	tableWithSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel,actionPanelContainer);
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
	if (actionsTable.getSelectedRow( )<0 || actionsTable.getSelectedRow( )>=dataControl.getActions().size()){
		
		// set information pane as no element selected
		JTextPane informationTextPane = new JTextPane( );
		informationTextPane.setEditable( false );
		informationTextPane.setBackground( getBackground( ) );
		informationTextPane.setText( TextConstants.getText( "ActionList.Empty" ));
		actionPanelContainer.removeAll();
		actionPanelContainer.add(informationTextPane);
		//Disable delete button
		deleteButton.setEnabled( false );
		//Disable moveUp and moveDown buttons
		moveUpButton.setEnabled( false );
		moveDownButton.setEnabled( false );
	}
	
	//When a element has been selected
	else {
		int selectedAction = actionsTable.getSelectedRow( );
		ActionDataControl action = dataControl.getActions().get(selectedAction);
		actionPanelContainer.removeAll();
		// Update the panel with action's info
		if (action instanceof CustomActionDataControl){
		    actionPanel = new CustomActionPropertiesPanel((CustomActionDataControl)action);
		    actionPanelContainer.add((CustomActionPropertiesPanel)actionPanel,BorderLayout.CENTER);
		}else if (action instanceof ActionDataControl){
		    actionPanel = new ActionPropertiesPanel(action);
		    actionPanelContainer.add((ActionPropertiesPanel)actionPanel,BorderLayout.CENTER);
		}
		actionPanelContainer.updateUI();
		deleteButton.setEnabled(true);
		//Enable moveUp and moveDown buttons when there is more than one element
		moveUpButton.setEnabled( dataControl.getActions().size()>1 && selectedAction>0);
		moveDownButton.setEnabled( dataControl.getActions().size()>1 && selectedAction<actionsTable.getRowCount( )-1 );

	}
	updateUI( );
	
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
	 * Returns a popup menu with the add operations.
	 * 
	 * @return Popup menu with child adding operations
	 */
	public JPopupMenu getAddChildPopupMenu( ) {
		JPopupMenu addChildPopupMenu = new JPopupMenu( );

		// If the element accepts children
		if( dataControl.getAddableElements().length > 0 ) {
			// Add an entry in the popup menu for each type of possible child
			for( int type : dataControl.getAddableElements( ) ) {
				JMenuItem addChildMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.AddElement" + type ) );
				addChildMenuItem.setEnabled( true );
				addChildMenuItem.addActionListener( new AddActionListener( type ) );
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
	
	private void delete( ) {
		ActionDataControl action = dataControl.getActions().get(actionsTable.getSelectedRow( ) );
		if (dataControl.deleteElement( action, true )){
		    	((JPanel)actionPanel).setVisible(false);
			actionsTable.clearSelection( );
			actionsTable.changeSelection(0, 1, false, false);
			actionsTable.updateUI( );
			
		}
	}
	
	private void moveUp(){
	    //falta tool
	    int pos = actionsTable.getSelectedRow();
	    if (dataControl.moveElementUp(dataControl.getActions().get(pos))){
		actionsTable.clearSelection( );
		actionsTable.changeSelection(pos-1,0,false,false);
	    }
	    //Controller.getInstance().addTool(new MovePlayerLayerInTableTool(referencesListDataControl,table,true));
		
	}
	

	
	private void moveDown(){
	    int pos = actionsTable.getSelectedRow();
	    if (dataControl.moveElementDown(dataControl.getActions().get(pos))){
		actionsTable.clearSelection( );
		actionsTable.changeSelection(pos+1,0,false,false);
	    }
		//Controller.getInstance().addTool(new MovePlayerLayerInTableTool(referencesListDataControl,table,false));
		
	}
	
	
	/**
	 * This class is the action listener for the add buttons of the popup menus.
	 */
	private class AddActionListener implements ActionListener {

		/**
		 * Type of action to be created.
		 */
		int type;

		/**
		 * Constructor
		 * 
		 * @param type
		 *            Type of element the listener must call
		 */
		public AddActionListener( int type ) {
			this.type = type;
		}

		public void actionPerformed( ActionEvent e ) {	
			if (dataControl.addElement( type, null )){
			    if (type==Action.CUSTOM_INTERACT||type==Action.CUSTOM){
				actionPanel = new CustomActionPropertiesPanel( (CustomActionDataControl)dataControl.getLastAction());
			    }else {
				actionPanel = new ActionPropertiesPanel( dataControl.getLastAction());
			    }
			    actionsTable.getSelectionModel().setSelectionInterval(actionsTable.getRowCount()-1, actionsTable.getRowCount()-1);
			    actionsTable.updateUI( );
				}
			}
		}
	
}
