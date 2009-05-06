package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.config.ProjectConfigData;
import es.eucm.eadventure.editor.control.controllers.SelectedEffectsController;
import es.eucm.eadventure.editor.control.controllers.SelectedEffectsController.ListElements;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.SelectEffectsDialog;

public class MostVisitedPanel extends JPanel{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
   
    
    private boolean isPressed;
    
    private String selectedName;
    
    private SelectEffectsDialog dialog;
    
    private SelectedEffectsController selectEffectControl;
    
    public static final int N_EFFECTS_TO_DISPLAY = 10;
    public static final int N_COLS = 2;
    
    public MostVisitedPanel(SelectEffectsDialog dialog){
		this.dialog=dialog;
		int nRows = N_EFFECTS_TO_DISPLAY/N_COLS;
		if (N_EFFECTS_TO_DISPLAY % N_COLS>0)
			nRows++;
		this.setLayout(new GridLayout(nRows,N_COLS));
		//GridBagConstraints c = new GridBagConstraints();
		//c.fill =GridBagConstraints.BOTH;
		this.isPressed = false;
		selectEffectControl = new SelectedEffectsController();
		ListElements[] values = selectEffectControl.getMostVisiteEffects(N_EFFECTS_TO_DISPLAY);
		int addedButtons=0;
		for (int i=0;i<N_EFFECTS_TO_DISPLAY;i++){
			if (values[i].getValue()>0){
				String name = SelectedEffectsController.reconvertNames(values[i].getName());
				JButton button = new JButton(EffectsStructurePanel.getEffectIcon(name, EffectsStructurePanel.ICON_SIZE_LARGE));
				button.setRolloverIcon(EffectsStructurePanel.getEffectIcon(name, EffectsStructurePanel.ICON_SIZE_LARGE_HOT));
				button.setPressedIcon(EffectsStructurePanel.getEffectIcon(name, EffectsStructurePanel.ICON_SIZE_LARGE_HOT));
				button.setToolTipText(name);
			    button.setContentAreaFilled(false);
			    
			    button.addActionListener(new ButtonListener(SelectedEffectsController.reconvertNames(values[i].getName())));
			    add(button);
			    addedButtons++;
			}
		}
		if (addedButtons==0)
		    this.add(new JLabel(TextConstants.getText("MoreVisitedPanel.Empty")));
		else if (addedButtons<N_EFFECTS_TO_DISPLAY){
			while (addedButtons<N_EFFECTS_TO_DISPLAY){
				add(new JFiller());
				addedButtons++;
			}
		}

    }
    
    
    private class ButtonListener implements ActionListener{

	private String storedName;
	
	public ButtonListener(String name){
	    this.storedName = name;
	}

	public void actionPerformed(ActionEvent e) {
	   isPressed=true;
	    selectedName=storedName;
	    dialog.setOk(true);
	    
	}
	
    }
    
    

    /**
     * @return the isPressed
     */
    public boolean isPressed() {
        return isPressed;
    }

    /**
     * @return the selectedName
     */
    public String getSelectedName() {
        return selectedName;
    }
    
}
