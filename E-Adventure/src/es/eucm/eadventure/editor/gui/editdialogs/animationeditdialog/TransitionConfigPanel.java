package es.eucm.eadventure.editor.gui.editdialogs.animationeditdialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.animation.Transition;

public class TransitionConfigPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Transition transition;

	private JList list;

	private JComboBox comboBox;

	private JSpinner spinner;
	
	public TransitionConfigPanel(Transition transition, JList list) {
		this.transition = transition;
		this.list = list;
		this.setLayout(new GridLayout(1,1));

		JPanel temp2 = new JPanel();
		temp2.add(new JLabel("Duration" + ": "));
	    SpinnerModel sm = new SpinnerNumberModel(transition.getTime(), 0, 10000, 100);
	    spinner = new JSpinner(sm);
	    spinner.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				modifyTransitionTime();					
			}});
	    temp2.add(spinner);
	    this.add(temp2);

		
		JPanel temp = new JPanel();
		comboBox = new JComboBox();
		comboBox.addItem("NONE");
		comboBox.addItem("FADEIN");
		comboBox.addItem("HORIZONTAL");
		comboBox.addItem("VERTICAL");

		switch(transition.getType()) {
		case Transition.TYPE_NONE:
			comboBox.setSelectedIndex(0);
			break;
		case Transition.TYPE_FADEIN:
			comboBox.setSelectedIndex(1);
			break;
		case Transition.TYPE_HORIZONTAL:
			comboBox.setSelectedIndex(2);
			break;
		case Transition.TYPE_VERTICAL:
			comboBox.setSelectedIndex(3);
			break;
		}

		comboBox.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				modifyTransition();
			}});
		temp.add(comboBox);
		this.add(temp);
	}

	protected void modifyTransitionTime() {
		transition.setTime(((Double) spinner.getModel().getValue()).longValue());
		list.updateUI();
	}

	protected void modifyTransition() {
		switch(comboBox.getSelectedIndex()) {
		case 0:
			transition.setType(Transition.TYPE_NONE);
			break;
		case 1:
			transition.setType(Transition.TYPE_FADEIN);
			break;
		case 2:
			transition.setType(Transition.TYPE_HORIZONTAL);
			break;
		case 3:
			transition.setType(Transition.TYPE_VERTICAL);
			break;
		}
		list.updateUI();
	}


}