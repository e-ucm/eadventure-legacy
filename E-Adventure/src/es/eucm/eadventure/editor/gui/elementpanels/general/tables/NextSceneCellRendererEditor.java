package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.gui.editdialogs.PlayerPositionDialog;

public class NextSceneCellRendererEditor  extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 4870491701696223525L;

	private Object[] items;
	
	private Object selectedItem;

	private JComboBox combo;
	
	private ExitDataControl exit;
	
	private JButton editPosition;
	
	private JCheckBox hasPosition;
	
	public NextSceneCellRendererEditor(Object[] items) {
		this.items = items;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
		this.exit = (ExitDataControl) value;
		selectedItem = exit.getNextSceneId();
		return comboPanel();
	}

	@Override
	public Object getCellEditorValue() {
		return exit;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		this.exit = (ExitDataControl) value;
		selectedItem = exit.getNextSceneId();
		if (!isSelected) {
			return new JLabel((String) selectedItem);
		} else {
			return comboPanel();
		}
	}
	
	private JPanel comboPanel() {
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		combo = new JComboBox(items);
		combo.setSelectedItem(selectedItem);
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectedItem = combo.getSelectedItem();
				exit.setNextSceneId((String) selectedItem);
				hasPosition.setEnabled( Controller.getInstance( ).getIdentifierSummary( ).isScene( exit.getNextSceneId( ) ) );
				editPosition.setEnabled(hasPosition.isEnabled() && exit.hasDestinyPosition());
				hasPosition.setSelected(exit.hasDestinyPosition());
			}
		});
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 2.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		tempPanel.add(combo, c);
		
		if (!Controller.getInstance().isPlayTransparent()) {
			
			hasPosition = new JCheckBox(TextConstants.getText("NextSceneCell.UsePosition"));
			hasPosition.addActionListener(new DestinyPositionCheckBoxListener( ));
			hasPosition.setEnabled( Controller.getInstance( ).getIdentifierSummary( ).isScene( exit.getNextSceneId( ) ) );
			hasPosition.setSelected(exit.hasDestinyPosition());
			
			editPosition = new JButton(TextConstants.getText( "NextSceneCell.EditDestinyPosition" ));
			editPosition.addActionListener(new DestinyPositionButtonListener());
			editPosition.setEnabled(hasPosition.isEnabled() && exit.hasDestinyPosition());
			c.gridy++;
			tempPanel.add(hasPosition, c);
			c.gridy++;
			tempPanel.add(editPosition, c);
		}
		
		tempPanel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, Color.BLUE));
		return tempPanel;
	}

	
	private class DestinyPositionButtonListener implements ActionListener {
		public void actionPerformed( ActionEvent arg0 ) {
			PlayerPositionDialog destinyPositionDialog = new PlayerPositionDialog( selectedItem.toString( ), exit.getDestinyPositionX( ), exit.getDestinyPositionY( ) );
			exit.setDestinyPosition( destinyPositionDialog.getPositionX( ), destinyPositionDialog.getPositionY( ) );
		}
	}

	/**
	 * Listener for the "Use destiny position in this next scene" check box.
	 */
	private class DestinyPositionCheckBoxListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			exit.toggleDestinyPosition( );
			editPosition.setEnabled( exit.hasDestinyPosition( ) );
		}
	}

}
