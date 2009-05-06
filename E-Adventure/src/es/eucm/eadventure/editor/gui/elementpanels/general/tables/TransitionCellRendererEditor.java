package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;

public class TransitionCellRendererEditor  extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 4870491701696223525L;

	private JComboBox combo;
	
	private ExitDataControl exit;
	
	private JSpinner spinner;
			
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
		this.exit = (ExitDataControl) value;
		return comboPanel(table);
	}

	public Object getCellEditorValue() {
		return exit;
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		this.exit = (ExitDataControl) value;
		if (!isSelected) {
			switch (exit.getTransitionType()) {
			case 0:
				return new JLabel(TextConstants.getText("Exit.NoTransition"));
			case 1:
				return new JLabel(TextConstants.getText("Exit.TopToBottom"));
			case 2:
				return new JLabel(TextConstants.getText("Exit.BottomToTop"));
			case 3:
				return new JLabel(TextConstants.getText("Exit.LeftToRight"));
			case 4:
				return new JLabel(TextConstants.getText("Exit.RightToLeft"));
			case 5:
				return new JLabel(TextConstants.getText("Exit.FadeIn"));
			default:
			}
			return new JLabel("" + exit.getTransitionType());
		} else {
			return comboPanel(table);
		}
	}
	
	private JPanel comboPanel(JTable table) {
		JPanel tempPanel = new JPanel();
		tempPanel.setLayout(new GridBagLayout());
		tempPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, table.getSelectionBackground()));

		GridBagConstraints c = new GridBagConstraints();
		
		combo = new JComboBox();
		combo.addItem(makeObj(TextConstants.getText("Exit.NoTransition")));
		combo.addItem(makeObj(TextConstants.getText("Exit.TopToBottom")));
		combo.addItem(makeObj(TextConstants.getText("Exit.BottomToTop")));
		combo.addItem(makeObj(TextConstants.getText("Exit.LeftToRight")));
		combo.addItem(makeObj(TextConstants.getText("Exit.RightToLeft")));
		combo.addItem(makeObj(TextConstants.getText("Exit.FadeIn")));
		combo.setSelectedIndex(exit.getTransitionType());

		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				exit.setTransitionType(combo.getSelectedIndex());
				if (combo.getSelectedIndex() == 0)
					spinner.setEnabled(false);
				else
					spinner.setEnabled(true);
			}
		});
		
	    SpinnerModel sm = new SpinnerNumberModel(exit.getTransitionTime(), 0, 5000, 100);
	    spinner = new JSpinner(sm);
	    spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				exit.setTransitionTime((Integer) spinner.getValue());
			}
		});
	    spinner.setEnabled(combo.getSelectedIndex() != 0);

		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 2.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		tempPanel.add(combo, c);
		
		c.gridy++;
		tempPanel.add(spinner, c);
		
		return tempPanel;
	}

	private Object makeObj(final String item)  {
	    return new Object() { public String toString() { return item; } };
	 }


}
