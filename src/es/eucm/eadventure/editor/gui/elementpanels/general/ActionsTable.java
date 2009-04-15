package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.control.controllers.general.ActionDataControl;
import es.eucm.eadventure.editor.control.controllers.general.ActionsListDataControl;
import es.eucm.eadventure.editor.control.controllers.general.CustomActionDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ElementContainer;
import es.eucm.eadventure.editor.gui.elementpanels.book.IconTextPanel;


public class ActionsTable extends JTable{
	
	
	/**
	 * 
	 */
    	private static final long serialVersionUID = -777111416961485368L;

	private ActionsListDataControl dataControl;
	
	
	public ActionsTable (ActionsListDataControl dControl){
		super();
		
		this.setModel( new ActionsTableModel() );
		this.getColumnModel( ).setColumnSelectionAllowed( false );
		this.setDragEnabled( false );
		this.getColumnModel().getColumn(0).setMaxWidth( 55 );
		this.getColumnModel().getColumn(0).setPreferredWidth(50);
		
		TableColumn tc = this.getColumnModel().getColumn(1); 
		//tc.setMaxWidth(30);
		tc.setCellEditor(this.getDefaultEditor(Boolean.class));
		//tc.setCellRenderer(this.getDefaultRenderer(Boolean.class));
		

		
		
		//this.getColumnModel().getColumn(2).setCellRenderer(new ActionsTableCellRenderer());
		//this.getColumnModel().getColumn(3).setCellRenderer(new ActionsTableCellRenderer());
		this.getSelectionModel( ).setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.dataControl = dControl;
		this.setSize(200, 150);
	}
	
	
	private class ActionsTableModel extends AbstractTableModel{

		
		/**
	     * 
	     */
	    private static final long serialVersionUID = -243535410363608581L;
		
		public int getColumnCount( ) {
			return 3;
		}

		public int getRowCount( ) {
			return dataControl.getActions().size();
		}

		public Object getValueAt( int rowIndex, int columnIndex ) {
		    List<ActionDataControl> actions = dataControl.getActions();
		    if (columnIndex == 0)
			return Integer.toString( rowIndex );
		    if (columnIndex == 1)
			return getType(((Action)actions.get(rowIndex).getContent()).getType());
		    if (columnIndex == 2){
			int type = ((Action)actions.get(rowIndex).getContent()).getType();
			if (type==Action.GIVE_TO||type==Action.USE_WITH)
			    return ((Action)actions.get(rowIndex).getContent()).getTargetId();
			else if (type == Action.CUSTOM_INTERACT)
			    return ((CustomAction)actions.get(rowIndex).getContent()).getTargetId();
			else 
			    return null;
		    }
		    
		    return null;
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			if (columnIndex==0)
				return TextConstants.getText( "ActionsList.Pos" );
			if (columnIndex==1)
				return TextConstants.getText( "ActionsList.ActionName" );
			if (columnIndex==2)
				return TextConstants.getText( "ActionsList.With" );
			
			return "";
		}
		
		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
		    	
		}
		
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
		
		private String getType(int type){
		    switch(type) {
			case Action.EXAMINE:
				return TextConstants.getText("Element.Name21");
			case Action.GRAB:
				return TextConstants.getText("Element.Name22");
			case Action.GIVE_TO:
			    return TextConstants.getText("Element.Name25");
			case Action.USE_WITH:
			    return TextConstants.getText("Element.Name24");
			case Action.USE:
			    return TextConstants.getText("Element.Name23");
			case Action.CUSTOM:
			    return TextConstants.getText("Element.Name230");
			case Action.CUSTOM_INTERACT:
			    return TextConstants.getText("Element.Name250");
			default:
			    return "";
			  
			}
		}
	}

	

	
	private class ActionsTableCellRenderer extends DefaultTableCellRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable,
		 *      java.lang.Object, boolean, boolean, int, int)
		 */
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			
			if (value instanceof ElementContainer){
				
				String elementName;
				if (((ElementContainer)value).isPlayer()){
					elementName = "player";
					return new IconTextPanel("img/icons/npc.png", elementName,isSelected);
				}
				else {
					elementName = ((ElementContainer)value).getErdc().getElementId();
					if (((ElementContainer)value).getErdc().getType( ) == Controller.ITEM_REFERENCE){
						return new IconTextPanel("img/icons/item.png", elementName, isSelected);				
					} else if (((ElementContainer)value).getErdc().getType( )  == Controller.ATREZZO_REFERENCE){
						return new IconTextPanel("img/icons/Atrezzo-1.png", elementName,isSelected);
					} else if (((ElementContainer)value).getErdc().getType( )  == Controller.NPC_REFERENCE){
						return new IconTextPanel("img/icons/npc.png", elementName,isSelected);
					} else 
					return null;
				}
			} else if (value instanceof ConditionsController) { 
				return new JButton(TextConstants.getText( "GeneralText.EditConditions" ));
		    } else if (value != null){
				return new JLabel(value.toString( ));
			} else {
				return new JLabel();
			}
		}

	}
}
