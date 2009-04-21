package es.eucm.eadventure.editor.gui.elementpanels.timer;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.editor.control.controllers.timer.TimerDataControl;

public class TimerTimeCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

	private static final long serialVersionUID = 8128260157985286632L;
	
	private TimerDataControl value;
	
	@Override
	public Object getCellEditorValue() {
		return value;
	}
	
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int col) {
		this.value = (TimerDataControl) value;
		return createSpinner();
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		this.value = (TimerDataControl) value;
		if (!isSelected)
			return new JLabel("" + this.value.getTime() + " seg");
		return createSpinner();
	}
	
	private JPanel createSpinner() {
		long current = value.getTime( );
		long min = 1;
		long max = 99 * 3600;
		long increment = 1;
		JLabel totalTime = new JLabel("seg");
		JSpinner timeSpinner = new JSpinner(new SpinnerNumberModel(current, min, max, increment));
		timeSpinner.addChangeListener(new TimeSpinnerListener());
		JPanel temp = new JPanel();
		temp.add(timeSpinner);
		temp.add(totalTime);

		return temp;
	}
	
	/**
	 * Listener for the time spinner
	 */
	private class TimeSpinnerListener implements ChangeListener {
			public void stateChanged(ChangeEvent e) {
				JSpinner timeSpinner = (JSpinner)e.getSource( );
				SpinnerNumberModel model = (SpinnerNumberModel) timeSpinner.getModel();
				value.setTime( model.getNumber( ).longValue( ) );
			}
	}
}
