package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.gui.structurepanel.EffectsStructurePanel;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

/**
 * This is a dialog to show all existing groups of effects in a more visual way 
 *
 */
public class SelectEffectsDialog extends ToolManagableDialog {

   

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private EffectsStructurePanel effectsStructurePanel;
    
    private boolean isOk;
    
    
    public SelectEffectsDialog() {
	super(Controller.getInstance( ).peekWindow( ),TextConstants.getText("SelectEffectDialog.Title") );
	
	effectsStructurePanel = new EffectsStructurePanel();
	StructureControl.getInstance().setStructurePanel(effectsStructurePanel);
	isOk=false;
	JPanel leftPanel = new JPanel(new GridLayout(2,0));
	
	JPanel lastUsedElements = new JPanel();
	JLabel last = new JLabel("Este es el panel de los ultimos elementos introducidos");
	lastUsedElements.add(last);
	
	GridBagConstraints c = new GridBagConstraints();
	c.fill = GridBagConstraints.BOTH;
	c.weightx=1;
	c.weighty=1;
	leftPanel.add(lastUsedElements);
	c.gridy++;
	leftPanel.add(effectsStructurePanel);
	
	
	
	GridBagConstraints c2 = new GridBagConstraints();
	c2.gridy=0;
	c2.gridx=0;
	c2.fill = GridBagConstraints.BOTH;
	c2.weightx=0;
	c2.weighty=1;
	c2.insets = new Insets( 5, 5, 5, 5 );
	//this.add(leftPanel,c2);
	//this.add(effectsStructurePanel);
	
	JPanel infoPlusButtons = new JPanel(new BorderLayout());
	infoPlusButtons.add(effectsStructurePanel.getInfoPanel(),BorderLayout.CENTER);
	
	JPanel buttonPane = new JPanel(new GridLayout(0,2));
	JButton ok = new JButton("OK");
	ok.addActionListener(new ActionListener(){

	    public void actionPerformed(ActionEvent e) {
		isOk = true;
		
		dispose();
	    }
	    
	});
	
	JButton cancel = new JButton("Cancel");
	cancel.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		
		dispose();
	    }
	    
	});
	buttonPane.add(ok);
	buttonPane.add(cancel);
	
	infoPlusButtons.add(buttonPane,BorderLayout.SOUTH);
	
	c2.gridx=1;
	//this.add(infoPlusButtons,c2);
	
	
	JSplitPane container = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,infoPlusButtons);
	leftPanel.setMaximumSize(new Dimension(225,0));
	leftPanel.setMinimumSize(new Dimension(200,0));
	container.setDividerLocation(200);
	
	add(container);
	
	setResizable( false );
	pack( );
	this.setSize(new Dimension(917,700));
	Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
	setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
	this.setVisible( true );
	
    }

   
    
    public static String getSelectedEffect(){
	SelectEffectsDialog dialog = new SelectEffectsDialog();
	StructureControl.getInstance().resetStructurePanel();
	if (dialog.isOk)
	    return dialog.effectsStructurePanel.getSelectedEffect();
	else 
	    return null;
	
    }
    
}
