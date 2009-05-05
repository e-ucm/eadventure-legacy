package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.control.controllers.general.CustomActionDataControl;
import es.eucm.eadventure.editor.gui.DataControlsPanel;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ActionsTable;

public class ActionsListPanel extends JPanel implements DataControlsPanel,Updateable{

	private static final long serialVersionUID = 1L;

	protected ActionsListDataControl dataControl;
	
	private JPanel actionPropertiesPanel;
	
	protected JButton deleteButton;
	
	protected JButton duplicateButton;
	
	protected JButton moveUpButton;

	protected JButton moveDownButton;
	
	protected JTable table;
	
	private static final int HORIZONTAL_SPLIT_POSITION = 140;

	/**
	 * Constructor.
	 * 
	 * @param actionsListDataControl
	 *            Actions list controller
	 */
	public ActionsListPanel( ActionsListDataControl actionsListDataControl ) {
		this.dataControl = actionsListDataControl;

		setLayout(new BorderLayout());
		
		actionPropertiesPanel = new JPanel();
		actionPropertiesPanel.setLayout(new BorderLayout());
		JPanel tablePanel = createTablePanel();
		
		JSplitPane tableWithSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tablePanel, actionPropertiesPanel);
		tableWithSplit.setOneTouchExpandable(true);
		tableWithSplit.setDividerLocation(HORIZONTAL_SPLIT_POSITION);
		tableWithSplit.setContinuousLayout(true);
		tableWithSplit.setResizeWeight(0.5);
		tableWithSplit.setDividerSize(10);
		
		add(tableWithSplit,BorderLayout.CENTER);
	}
	
	private JPanel createTablePanel(){
		JPanel tablePanel = new JPanel(new BorderLayout());
		
		table = new ActionsTable(dataControl);

		tablePanel.add( new TableScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) ,BorderLayout.CENTER);
		
		table.getSelectionModel( ).addListSelectionListener( new ListSelectionListener(){
			public void valueChanged( ListSelectionEvent e ) {
				updateSelectedAction();
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
				JPopupMenu menu = getAddChildPopupMenu();
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

		duplicateButton = new JButton(new ImageIcon("img/icons/duplicateNode.png"));
		duplicateButton.setContentAreaFilled( false );
		duplicateButton.setMargin( new Insets(0,0,0,0) );
		duplicateButton.setToolTipText( TextConstants.getText( "ActionsList.Duplicate" ) );
		duplicateButton.addActionListener(new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				duplicate();
			}
		});	
		duplicateButton.setEnabled(false);

		moveUpButton = new JButton(new ImageIcon("img/icons/moveNodeUp.png"));
		moveUpButton.setContentAreaFilled( false );
		moveUpButton.setMargin( new Insets(0,0,0,0) );
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
		c.gridy = 1;
		buttonsPanel.add(duplicateButton, c);
		c.gridy = 2;
		buttonsPanel.add( moveUpButton , c );
		c.gridy = 3;
		buttonsPanel.add( moveDownButton , c );
		c.gridy= 5;
		buttonsPanel.add( deleteButton , c );
		c.gridy = 4;
		c.weighty = 2.0;
		c.fill = GridBagConstraints.VERTICAL;
		buttonsPanel.add(new JFiller(), c);
		
		
		tablePanel.add( buttonsPanel,BorderLayout.EAST);
		return tablePanel;
	}

	private void updateSelectedAction() {
		int selectedAction = table.getSelectedRow( );
		actionPropertiesPanel.removeAll();

		if (selectedAction != -1&&dataControl.getActions().size()>0) {
			ActionDataControl action = dataControl.getActions().get(selectedAction);
			if (action instanceof CustomActionDataControl){
			    JPanel actionPanel = new CustomActionPropertiesPanel((CustomActionDataControl)action);
			    actionPropertiesPanel.add(actionPanel,BorderLayout.CENTER);
			}else if (action instanceof ActionDataControl){
			    JPanel actionPanel = new ActionPropertiesPanel(action);
			    actionPropertiesPanel.add(actionPanel,BorderLayout.CENTER);
			}
			actionPropertiesPanel.updateUI();
			deleteButton.setEnabled(true);
			duplicateButton.setEnabled(true);
			//Enable moveUp and moveDown buttons when there is more than one element
			moveUpButton.setEnabled( dataControl.getActions().size()>1 && selectedAction > 0);
			moveDownButton.setEnabled( dataControl.getActions().size()>1 && selectedAction < table.getRowCount( )-1 );
		} else {
		    	actionPropertiesPanel.removeAll();
		    	actionPropertiesPanel.updateUI();
			deleteButton.setEnabled(false);
			duplicateButton.setEnabled(false);
			moveUpButton.setEnabled(false);
			moveDownButton.setEnabled(false);
		}
	}
	
    /**
	 * Returns a popup menu with the add operations.
	 * 
	 * @return Popup menu with child adding operations
	 */
	public JPopupMenu getAddChildPopupMenu( ) {
		JPopupMenu addChildPopupMenu = new JPopupMenu( );

		for( final int type : dataControl.getAddableElements( ) ) {
			JMenuItem addChildMenuItem = new JMenuItem( TextConstants.getText( "TreeNode.AddElement" + type ) );
			addChildMenuItem.setEnabled( true );
			addChildMenuItem.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (dataControl.addElement(type, null)) {
						((AbstractTableModel) table.getModel()).fireTableDataChanged();
						table.changeSelection(table.getRowCount() - 1, table.getRowCount() - 1, false, false);
					}
				}
			});
			addChildPopupMenu.add( addChildMenuItem );
		}

		return addChildPopupMenu;
	}
	
	protected void duplicate() {
		if (dataControl.duplicateElement(dataControl.getActions().get(table.getSelectedRow()))) {
			((AbstractTableModel) table.getModel()).fireTableDataChanged();
			table.changeSelection(dataControl.getActions().size() - 1, 0, false, false);
		}
	}

	protected void delete() {
		if (dataControl.deleteElement(dataControl.getActions().get(table.getSelectedRow()), false)) {
			table.clearSelection();
			table.changeSelection(0, 1, false, false);
			table.updateUI();
		}
	}
	
	protected void moveUp() {
		int selectedRow = table.getSelectedRow();
		table.clearSelection();
		dataControl.moveElementUp(dataControl.getActions().get(selectedRow));
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		table.changeSelection(selectedRow - 1, selectedRow - 1, false, false);
		table.editCellAt(selectedRow + 1, 0);
	}
	
	protected void moveDown() {
		int selectedRow = table.getSelectedRow();
		table.clearSelection();
		dataControl.moveElementDown(dataControl.getActions().get(selectedRow));
		((AbstractTableModel) table.getModel()).fireTableDataChanged();
		table.changeSelection(selectedRow + 1, selectedRow + 1, false, false);
		table.editCellAt(selectedRow + 1, 0);
	}

	@Override
	public void setSelectedItem(List<DataControl> path) {
		if (path.size() > 0) {
			for (int i = 0 ; i < dataControl.getActions().size(); i++) {
				if (dataControl.getActions().get(i) == path.get(path.size() -1))
					table.changeSelection(i, i, false, false);
			}
		}
	}

	@Override
	public boolean updateFields() {
	  /*boolean update = false;
	   if (actionPropertiesPanel.getComponents()[0] instanceof CustomActionPropertiesPanel){
		    update=((CustomActionPropertiesPanel)actionPropertiesPanel.getComponents()[0]).updateFields();
		}else if (actionPropertiesPanel.getComponents()[0] instanceof ActionPropertiesPanel){
		    update=((ActionPropertiesPanel)actionPropertiesPanel.getComponents()[0]).updateFields();
		}
		
	
	    return update;*/
	    return true;
	}
}
