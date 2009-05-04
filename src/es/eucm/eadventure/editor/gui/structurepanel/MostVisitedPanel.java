package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.config.ProjectConfigData;
import es.eucm.eadventure.editor.control.controllers.SelectedEffectsController;
import es.eucm.eadventure.editor.control.controllers.SelectedEffectsController.ListElements;
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
    
    
    public MostVisitedPanel(SelectEffectsDialog dialog){
	this.dialog=dialog;
	this.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	c.fill =GridBagConstraints.BOTH;
	this.isPressed = false;
	selectEffectControl = new SelectedEffectsController();
	ListElements[] values = selectEffectControl.getMostVisiteEffects();
	int addedButtons=0;
	for (int i=0;i<4;i++){
	if (values[i].getValue()>0){
	    JButton button = new JButton(SelectedEffectsController.reconvertNames(values[i].getName()));
	    button.addActionListener(new ButtonListener(SelectedEffectsController.reconvertNames(values[i].getName())));
	    this.add(button,c);
	    c.gridy++;
	    addedButtons++;
	}
	}
	if (addedButtons==0)
	    this.add(new JLabel(TextConstants.getText("MoreVisitedPanel.Empty")));

    }
    
    
    private class ButtonListener implements ActionListener{

	private String storedName;
	
	public ButtonListener(String name){
	    this.storedName = name;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	   isPressed=true;
	    selectedName=storedName;
	    dialog.setOk(true);
	    dialog.dispose();
	    
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
