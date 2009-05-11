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
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.chapter.effects.Effect;
import es.eucm.eadventure.common.data.chapter.effects.RandomEffect;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.config.ConfigData;
import es.eucm.eadventure.editor.control.config.ProjectConfigData;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.SelectedEffectsController;
import es.eucm.eadventure.editor.control.controllers.SingleEffectController;
import es.eucm.eadventure.editor.control.tools.general.effects.AddEffectTool;
import es.eucm.eadventure.editor.gui.editdialogs.effectdialogs.EffectDialog;
import es.eucm.eadventure.editor.gui.structurepanel.EffectInfoPanel;
import es.eucm.eadventure.editor.gui.structurepanel.EffectsStructurePanel;
import es.eucm.eadventure.editor.gui.structurepanel.MostVisitedPanel;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

/**
 * This is a dialog to show all existing groups of effects in a more visual way 
 *
 */
public class SelectEffectsDialog extends ToolManagableDialog{

   

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private static SelectEffectsDialog instance = null;
    

    private EffectsStructurePanel effectsStructurePanel;
    
    private EffectsStructurePanel allEffectsStructurePanel;
    
    private boolean isOk;
    
    private MostVisitedPanel visitPanel;
    
    private JTabbedPane tabPane;
    
    private JPanel infoPlusButtons ;
    
    private EffectsController controller;
    
    private JButton ok;
    
    private HashMap<Integer, Object> effectProperties;
    
    public SelectEffectsDialog(EffectsController controller) {
	super(Controller.getInstance( ).peekWindow( ),TextConstants.getText("SelectEffectDialog.Title") , false);
	this.controller = controller;
	effectsStructurePanel = new EffectsStructurePanel(false,this);
	allEffectsStructurePanel = new EffectsStructurePanel(true,this);
	//StructureControl.getInstance().setStructurePanel(effectsStructurePanel);
	isOk=false;
	//JPanel leftPanel = new JPanel(new GridLayout(2,0));
	
	visitPanel = new MostVisitedPanel(this);
	
	tabPane = new JTabbedPane(JTabbedPane.TOP);
	tabPane.insertTab(TextConstants.getText("SelectEffectDialog.ByCategory"), null, effectsStructurePanel, TextConstants.getText("SelectEffectDialog.ByCategory.ToolTipText"), 0);
	tabPane.insertTab(TextConstants.getText("SelectEffectDialog.Recent"), null, visitPanel, TextConstants.getText("SelectEffectDialog.Recent.ToolTipText"), 1);
	tabPane.insertTab(TextConstants.getText("SelectEffectDialog.AllEffects"), null, allEffectsStructurePanel, TextConstants.getText("SelectEffectDialog.AllEffects.ToolTipText"), 2);
	if (ConfigData.getEffectSelectorTab()<=2 && ConfigData.getEffectSelectorTab()>=0){
		tabPane.setSelectedIndex(ConfigData.getEffectSelectorTab());
	}
	
	tabPane.addChangeListener(new ChangeListener(){

		@Override
		public void stateChanged(ChangeEvent e) {
			if (infoPlusButtons!=null){
				if (tabPane.getSelectedComponent() == effectsStructurePanel){
					//infoPlusButtons.add( effectsStructurePanel.getInfoPanel(), BorderLayout.CENTER);
				    	createInfoPlusButtons( effectsStructurePanel.getInfoPanel());
				    	ok.setEnabled(true);
				} else if (tabPane.getSelectedComponent() == allEffectsStructurePanel){
					//infoPlusButtons.add( allEffectsStructurePanel.getInfoPanel(), BorderLayout.CENTER);
				    	createInfoPlusButtons( allEffectsStructurePanel.getInfoPanel());
				    	ok.setEnabled(true);
				} 
			} 
			if (ok!=null){
				if (tabPane.getSelectedComponent() == visitPanel){
					ok.setEnabled(false);
				}
			}
			ConfigData.setEffectSelectorTab(tabPane.getSelectedIndex());
		}
		
	});
	JPanel leftPanel = new JPanel(new GridBagLayout());
	//JPanel leftPanel = new JPanel();
	//leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.PAGE_AXIS));

	
	
	GridBagConstraints c = new GridBagConstraints();
	c.fill = GridBagConstraints.HORIZONTAL;
	c.weightx=1;
	c.weighty=0;
	c.gridheight=1;
	c.gridy=0;
	//leftPanel.add(visitPanel, c);
	c.fill = GridBagConstraints.BOTH;
	c.weighty=1;
	//c.gridy=1;
	c.gridheight=2;
	//leftPanel.add(effectsStructurePanel,c);
	leftPanel.setLayout(new BorderLayout());
	leftPanel.add(tabPane, BorderLayout.CENTER);
	
	
	
	GridBagConstraints c2 = new GridBagConstraints();
	c2.gridy=0;
	c2.gridx=0;
	c2.fill = GridBagConstraints.BOTH;
	c2.weightx=0;
	c2.weighty=1;
	c2.insets = new Insets( 5, 5, 5, 5 );
	//this.add(leftPanel,c2);
	//this.add(effectsStructurePanel);
	
	
	c2.gridx=1;
	//this.add(infoPlusButtons,c2);
	
	infoPlusButtons = new JPanel(new BorderLayout());
	createInfoPlusButtons(effectsStructurePanel.getInfoPanel());
	
	JSplitPane container = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,leftPanel,infoPlusButtons);
	leftPanel.setMaximumSize(new Dimension(225,0));
	leftPanel.setMinimumSize(new Dimension(200,0));
	container.setDividerLocation(200);
	
	add(container);
	
	setResizable( false );
	//pack( );
	this.setSize(new Dimension(600,400));
	Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
	setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
	this.setVisible( true );
	
    }

   private void createInfoPlusButtons(EffectInfoPanel infoPanel){

	infoPlusButtons.removeAll();
	infoPlusButtons.add(infoPanel,BorderLayout.CENTER);
	
	JPanel buttonPane = new JPanel(new GridLayout(0,2));
	ok = new JButton(TextConstants.getText("GeneralText.OK"));
	ok.addActionListener(new ActionListener(){

	    public void actionPerformed(ActionEvent e) {
		//isOk = true;
		setOk(true);
	    }
	    
	});
	
	JButton cancel = new JButton(TextConstants.getText("GeneralText.Cancel"));
	cancel.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e) {
	    	setOk(false);
	    }
	    
	});
	buttonPane.add(ok);
	buttonPane.add(cancel);
	
	infoPlusButtons.add(buttonPane,BorderLayout.SOUTH);
   }
    
    /**
     * @param isOk the isOk to set
     */
    public void setOk(boolean isOk) {
        this.isOk = isOk;
        effectProperties = buildEffectProperties();
        dispose();
    }
    
    /**
     * @param isOk the isOk to set
     */
    public boolean isOk() {
    	return isOk;
    }
    
    protected String getSelection(){
    	if ( !visitPanel.isPressed() && tabPane.getSelectedComponent() == effectsStructurePanel ){
    		return effectsStructurePanel.getSelectedEffect();
    	} else if ( !visitPanel.isPressed() && tabPane.getSelectedComponent() == allEffectsStructurePanel ){
    		return allEffectsStructurePanel.getSelectedEffect();
    	}  else if ( visitPanel.isPressed() && tabPane.getSelectedComponent() == visitPanel ){
    		return visitPanel.getSelectedName();
    	} 
    	return null;
    }

    public HashMap<Integer, Object> buildEffectProperties(){
		// Create a list with the names of the effects (in the same order as the next)
		final String[] effectNames = { TextConstants.getText( "Effect.Activate" ), TextConstants.getText( "Effect.Deactivate" ),
				TextConstants.getText( "Effect.SetValue" ), TextConstants.getText( "Effect.IncrementVar" ), TextConstants.getText( "Effect.DecrementVar" ),
				TextConstants.getText( "Effect.MacroReference" ),
				TextConstants.getText( "Effect.ConsumeObject" ), TextConstants.getText( "Effect.GenerateObject" ), 
				TextConstants.getText( "Effect.CancelAction" ), TextConstants.getText( "Effect.SpeakPlayer" ), 
				TextConstants.getText( "Effect.SpeakCharacter" ), TextConstants.getText( "Effect.TriggerBook" ), 
				TextConstants.getText( "Effect.PlaySound" ), TextConstants.getText( "Effect.PlayAnimation" ), 
				TextConstants.getText( "Effect.MovePlayer" ), TextConstants.getText( "Effect.MoveCharacter" ), 
				TextConstants.getText( "Effect.TriggerConversation" ), TextConstants.getText( "Effect.TriggerCutscene" ), 
				TextConstants.getText( "Effect.TriggerScene" ), TextConstants.getText( "Effect.TriggerLastScene" ) , 
				TextConstants.getText( "Effect.RandomEffect" ),TextConstants.getText( "Effect.ShowText" ),
				TextConstants.getText( "Effect.WaitTime" )};

		// Create a list with the types of the effects (in the same order as the previous)
		final int[] effectTypes = { Effect.ACTIVATE, Effect.DEACTIVATE, Effect.SET_VALUE, Effect.INCREMENT_VAR, Effect.DECREMENT_VAR, 
				Effect.MACRO_REF, Effect.CONSUME_OBJECT, Effect.GENERATE_OBJECT, Effect.CANCEL_ACTION, Effect.SPEAK_PLAYER, 
				Effect.SPEAK_CHAR, Effect.TRIGGER_BOOK, Effect.PLAY_SOUND, Effect.PLAY_ANIMATION, Effect.MOVE_PLAYER, Effect.MOVE_NPC, 
				Effect.TRIGGER_CONVERSATION, Effect.TRIGGER_CUTSCENE, Effect.TRIGGER_SCENE, Effect.TRIGGER_LAST_SCENE, Effect.RANDOM_EFFECT,
				Effect.SHOW_TEXT,Effect.WAIT_TIME};
    	
    	String selectedValue = getSelectedEffect();
    	
    	HashMap<Integer, Object> effectProperties = null;
    	// Is not random effect
    	if( selectedValue != null && 
				!selectedValue.equals( TextConstants.getText( "Effect.RandomEffect" ) )) {
    		// Store the type of the effect selected
			int selectedType = 0;
			for( int i = 0; i < effectNames.length; i++ )
				if( effectNames[i].equals( selectedValue ) )
					selectedType = effectTypes[i];

			if (selectedType==Effect.MOVE_PLAYER && Controller.getInstance( ).isPlayTransparent( )){
				Controller.getInstance( ).showErrorDialog( TextConstants.getText( "Error.EffectMovePlayerNotAllowed.Title" ), TextConstants.getText( "Error.EffectMovePlayerNotAllowed.Message" ) );
			}else{
				effectProperties = EffectDialog.showAddEffectDialog( controller, selectedType );	
				if (effectProperties!=null)
					effectProperties.put(EffectsController.EFFECT_PROPERTY_TYPE, Integer.toString(selectedType));
			}
    	}
    	
    	// Is random effect
    	else if (selectedValue != null){
			SingleEffectController posController = new SingleEffectController();
			SingleEffectController negController = new SingleEffectController();
			effectProperties = EffectDialog.showEditRandomEffectDialog( 50, posController, negController );
			if (effectProperties!=null){
			effectProperties.put(EffectsController.EFFECT_PROPERTY_TYPE, Integer.toString(Effect.RANDOM_EFFECT));
				if (posController.getEffect()!=null)
					effectProperties.put(EffectsController.EFFECT_PROPERTY_FIRST_EFFECT, posController.getEffect());
				if (negController.getEffect()!=null)
					effectProperties.put(EffectsController.EFFECT_PROPERTY_SECOND_EFFECT, negController.getEffect());
			}
		}
    	
    	return effectProperties;   	
    }


    private String getSelectedEffect(){
		//instance = new SelectEffectsDialog(controller);
		if (isOk()){
		    String selection=getSelection();
		    
		    if (selection!=null){
			    // store the number of times that selected effect has been used
			    int value=0;
			    String realName=SelectedEffectsController.convertNames(selection);
			    String numberOfUses=ProjectConfigData.getProperty(realName);
			    if (numberOfUses!=null)
				value = Integer.parseInt(numberOfUses);
			    ProjectConfigData.setProperty(realName,String.valueOf( value+1));
		    }
		    return selection;
		    
		}
		else{ 
		    return null;
		}
    }
    
    public static HashMap<Integer, Object> getNewEffectProperties(EffectsController controller){
    	instance = new SelectEffectsDialog(controller);
    	return instance.effectProperties;
    }
    
}
