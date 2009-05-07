package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;

public class ConditionsCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	public static final int NO_ICON = 0;
	public static final int ICON_SMALL = 1;
	public static final int ICON_MEDIUM = 2;
	public static final int ICON_LARGE = 3;
	
	private boolean useText;
	
	private int iconSize;
	
	private ConditionsController value;
	
	public ConditionsCellRendererEditor(){
		this.useText = false;
		this.iconSize = ICON_SMALL;
	}
	
	public ConditionsCellRendererEditor(boolean useText, int iconSize){
		this.useText = useText;
		this.iconSize=iconSize;
	}
	
	public Object getCellEditorValue() {
		return value;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
		if (value == null)
			return null;
		this.value = (ConditionsController) value;
		return createButton(isSelected, table);
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		if (value == null)
			return null;
		this.value = (ConditionsController) value;
		return createButton(isSelected, table);
	}

	private Icon createIcon(){
		// Create icon (if applicable)
		Icon icon = null;
		boolean hasConditions = false;
		if (value!=null){
			ConditionsController controller = (ConditionsController)value;
			hasConditions = !controller.isEmpty();
		}
		if (iconSize==ICON_SMALL){
			if (hasConditions){
				icon = new ImageIcon("img/icons/conditions-16x16.png");
			}else{
				icon = new ImageIcon("img/icons/no-conditions-16x16.png");
			}
		}
		if (iconSize==ICON_MEDIUM){
			if (hasConditions){
				icon = new ImageIcon("img/icons/conditions-24x24.png");
			}else{
				icon = new ImageIcon("img/icons/no-conditions-24x24.png");
			}
		}
		if (iconSize==ICON_LARGE){
			if (hasConditions){
				icon = new ImageIcon("img/icons/conditions-32x32.png");
			}else{
				icon = new ImageIcon("img/icons/no-conditions-32x32.png");
			}
		}
		return icon;
	}
	
	private Component createButton(boolean isSelected, JTable table) {
		JPanel temp = new JPanel();
		if (isSelected)
			temp.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, table.getSelectionBackground()));
		JButton button = null;
		
		// Create text (if applicable)
		String text = null;
		if (useText){
			text = TextConstants.getText( "GeneralText.EditConditions" );
		}
		
		// Create icon (if applicable)
		Icon icon = createIcon();
		
		// Create button
		if (text!=null && icon!=null){
			button =new JButton(text, icon); 
		} else if (text!=null){
			button =new JButton(text);
		} else if (icon!=null){
			button = new JButton(icon);
			button.setContentAreaFilled(false);
			button.setOpaque(false);
		}
		
		button.setFocusable(false);
		button.setEnabled(isSelected);
		//button.setOpaque(false);
		//button.setContentAreaFilled(false);
		//button.setRolloverIcon(new ImageIcon("img/icons/conditions-hot-16x16.png"));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ConditionsDialog( (ConditionsController) ConditionsCellRendererEditor.this.value );
				//Update icon
				((JButton)(arg0.getSource())).setIcon(createIcon());
			}
		});
		temp.setLayout(new BorderLayout());
		temp.add(button, BorderLayout.CENTER);
		//button.requestFocus();
		return temp;
		
	}
	
}
