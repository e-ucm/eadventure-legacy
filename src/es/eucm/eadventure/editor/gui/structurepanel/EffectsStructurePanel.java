package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects.ChangesInSceneStructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects.EffectsStructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects.FeedbackStructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects.GameStateStructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects.MainStructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects.MiscelaneousStructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects.MultimediaStructureListElement;
import es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects.TriggerStructureListElement;

/**
 * Extends structure panel to adapt to select effects dialog
 *
 */
public class EffectsStructurePanel extends StructurePanel{

   

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private static final String ACTIVATE_URL = "";
    
    private static final String DEACTIVATE_URL = "";
    private static final String INCR_URL = "";
    private static final String DECR_URL = "";
    private static final String SET_URL = "";
    private static final String LAUNCH_URL = "";
    private static final String CONSUME_URL = "";
    private static final String GENERATE_URL = "";
    private static final String CANCEL_URL = "";
    private static final String SP_PLAYER_URL = "";
    private static final String SP_NPC_URL = "";
    private static final String BOOK_URL = "";
    private static final String SOUND_URL = "";
    private static final String ANIMATION_URL = "";
    private static final String MV_PLAYER_URL = "";
    private static final String MV_NPC_URL = "";
    private static final String CONV_URL = "";
    private static final String CUTSCENE_URL = "";
    private static final String SCENE_URL = "";
    private static final String LAST_SCENE_URL = "";
    private static final String RAMDON_URL = "";
    private static final String TEXT_URL = "";
    private static final String TIME_URL = "";
    
    private EffectInfoPanel infoPanel;
    
    public EffectsStructurePanel() {
	
	super(null);
	infoPanel = new EffectInfoPanel();
	recreateElements();
	StructureControl.getInstance().setStructurePanel(this);
	
	changeEffectEditPanel(((EffectsStructureListElement)structureElements.get(0)).getPath());
    }
    
    
    public String getSelectedEffect(){
	if (selectedElement==0 || list.getSelectedRow()==-1)
	    return null;
	return structureElements.get(selectedElement).getChild(list.getSelectedRow()).getName();
    }
    
    public void recreateElements() {
	structureElements.clear();
	structureElements.add(new MainStructureListElement());
	structureElements.add(new GameStateStructureListElement());
	structureElements.add(new MultimediaStructureListElement());
	structureElements.add(new FeedbackStructureListElement());
	structureElements.add(new TriggerStructureListElement());
	structureElements.add(new ChangesInSceneStructureListElement());
	structureElements.add(new MiscelaneousStructureListElement());
	
	
	update();
    }
    public void update() {
	int i = 0;
	removeAll();
	
	for (StructureListElement element : structureElements) {
		if (i == selectedElement)
			add(createSelectedElementPanel(element, i), new Integer(element.getChildCount() != 0 ? -1 : 40));
		else {
			button = new JButton(element.getName(), element.getIcon());
			button.setHorizontalAlignment(SwingConstants.LEFT);
			Border b1 = BorderFactory.createRaisedBevelBorder();
	        Border b2 = BorderFactory.createEmptyBorder(3, 10, 3, 10);
	        button.setBorder(BorderFactory.createCompoundBorder(b1,b2));
	        button.setContentAreaFilled(false);
			button.addActionListener(new ListElementButtonActionListener(i));
			button.setFocusable(false);
			if (i < selectedElement)
				add(button, new Integer(25));
			else if (i > selectedElement)
				add(button, new Integer(25));
		} 
		i++;
	}
	this.updateUI();
    }
    
    
    
    protected JPanel createSelectedElementPanel(final StructureListElement element, final int index) {
	JPanel result = super.createSelectedElementPanel(element, index);
	button.addActionListener(new ListElementButtonActionListener(index));
	list.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
			if (list.getSelectedRow() >= 0) {
				list.setRowHeight(20);
				list.setRowHeight(list.getSelectedRow(), 50);
				list.editCellAt(list.getSelectedRow(), 0);
				changeEffectEditPanel(getSelectedEffect());
			} else {
			    changeEffectEditPanel(((EffectsStructureListElement)structureElements.get(index)).getPath());
			}
			
		}
	});
	return result;
    }
    
   
    /**
     * @return the infoPanel
     */
    public EffectInfoPanel getInfoPanel() {
        return infoPanel;
    }

    /**
     * @param infoPanel the infoPanel to set
     */
    public void setInfoPanel(EffectInfoPanel infoPanel) {
        this.infoPanel = infoPanel;
    }
    
   
    
    private void changeEffectEditPanel(String name){
	    
	    String text=null;
	    if (name.equals(TextConstants.getText("Effect.Activate"))){
		text = ACTIVATE_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.Deactivate" ))){
		text = DEACTIVATE_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.SetValue" ))){
		text = SET_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.IncrementVar" ))){
		text = INCR_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.DecrementVar" ))){
		text = DECR_URL;
	    }else if (name.equals( TextConstants.getText(  "Effect.MacroReference" ))){
		text = LAUNCH_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.ConsumeObject" ))){
		text = CONSUME_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.GenerateObject" ))){
		text = GENERATE_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.CancelAction"))){
		text = CANCEL_URL;
	    }else if (name.equals( TextConstants.getText("Effect.SpeakPlayer"))){
		text = SP_PLAYER_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.SpeakCharacter" ))){
		text = SP_NPC_URL;
	    }else if (name.equals( TextConstants.getText(  "Effect.TriggerBook" ))){
		text = BOOK_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.PlaySound" ))){
		text = SOUND_URL;
	    }else if (name.equals( TextConstants.getText("Effect.PlayAnimation" ))){
		text = ANIMATION_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.MovePlayer" ))){
		text = MV_PLAYER_URL;
	    }else if (name.equals( TextConstants.getText("Effect.MoveCharacter" ))){
		text = MV_NPC_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.TriggerConversation" ))){
		text = CONV_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.TriggerCutscene" ))){
		text = CUTSCENE_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.TriggerScene"))){
		text = SCENE_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.TriggerLastScene"))){
		text = LAST_SCENE_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.RandomEffect" ))){
		text = RAMDON_URL;
	    }else if (name.equals( TextConstants.getText(  "Effect.ShowText"  ))){
		text = TEXT_URL;
	    }else if (name.equals( TextConstants.getText( "Effect.WaitTime" ))){
		text = TIME_URL;
	    }// when this method is called for structure list effects
	    else 
		text = name;
	    
		infoPanel.setHTMLText(text);
	    
	}
    
    private class ListElementButtonActionListener implements ActionListener {
	private int index;
	
	public ListElementButtonActionListener(int index) {
		this.index = index;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		selectedElement = index;
		update();
		changeEffectEditPanel(((EffectsStructureListElement)structureElements.get(selectedElement)).getPath());
		list.requestFocusInWindow();
	}
}
    
    
}
