package es.eucm.eadventure.editor.gui.elementpanels.adaptation;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationRuleDataControl;

public class InitialSceneCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private AdaptationRuleDataControl value;
	
	public Object getCellEditorValue() {
		return value;
	}
	
	public Component getTableCellEditorComponent(JTable table, Object value2, boolean isSelected, int row, int col) {
		this.value = (AdaptationRuleDataControl) value2;
		return createComponent(isSelected, table.getSelectionBackground());
	}

	public Component getTableCellRendererComponent(JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column) {
		this.value = (AdaptationRuleDataControl) value2;
		if (table.getSelectedRow() == row) {
			return createComponent(isSelected, table.getSelectionBackground());
		}
		return new JLabel(value.getInitialScene() != null ? value.getInitialScene() : TextConstants.getText("GeneralText.NotSelected"));
	}

	private Component createComponent(boolean isSelected, Color background) {
		JPanel temp = new JPanel();
		if (isSelected)
			temp.setBackground(background);

		String[] scenes = Controller.getInstance( ).getIdentifierSummary( ).getSceneIds( );
		String[] isValues = new String[scenes.length+1];
		isValues[0] = TextConstants.getText("GeneralText.NotSelected");
		for (int i=0; i<scenes.length; i++){
			isValues[i+1]=scenes[i];
		}
		final JComboBox comboBox = new JComboBox(isValues);
		if (value.getInitialScene() == null) {
			comboBox.setSelectedIndex(0);
		} else {
			comboBox.setSelectedItem(value.getInitialScene());
		}
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox.getSelectedIndex() == 0) {
					value.setInitialScene(null);
				} else {
					value.setInitialScene((String) comboBox.getSelectedItem());
				}
			}
		});
		
		temp.add(comboBox);
		return temp;
	}
}
