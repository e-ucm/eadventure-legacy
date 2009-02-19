package es.eucm.eadventure.common.gui;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Very useful to avoid that a spinner could be edited by typing text. 
 * This class has been designed to avoid JSpinners to get The Key Focus in the window 
 * @author Javier
 *
 */
public class NoEditableNumberSpinner extends JSpinner{

	/**
	 * Required
	 */
	private static final long serialVersionUID = 4827835884428487802L;
	/**
	 * Component used as editor
	 */
	private JLabel editor;
	
	/**
	 * Constructor. Must be argumented with the selected value, min valid value, max valid value and the step
	 * @param value
	 * @param min
	 * @param max
	 * @param step
	 */
	public NoEditableNumberSpinner(Number value, int min, int max, Number step){
		super (new SpinnerNumberModel (value, min,max,step));
		editor = new JLabel(value.toString());
		this.setEditor(editor);
		this.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				editor.setText(getValue().toString());
			}
			
		});
	}
}
