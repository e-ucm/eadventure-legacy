package es.eucm.eadventure.editor.control.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.config.ProjectConfigData;




public class SelectedEffectsController {

    
    private static final int NUMBER_OF_EFFECTS=23;
    /**
     * Constants for each type of effects.
     */
    private static final String ACTIVATE = "ACTIVATE";
    private static final String DEACTIVATE = "DEACTIVATE";
    private static final String INCREMENT = "INCREMENT";
    private static final String DECREMENT = "DECREMENT";
    private static final String SETVALUE = "SETVALUE";
    private static final String MACRO = "MACRO";
    private static final String CONSUME = "CONSUME";
    private static final String GENERATE = "GENERATE";
    private static final String CANCEL = "CANCEL";
    private static final String SPPLAYER = "SPPLAYER";
    private static final String SPNPC = "SPNPC";
    private static final String BOOK= "BOOK";
    private static final String SOUND= "SOUND";
    private static final String ANIMATION = "ANIMATION";
    private static final String MVPLAYER = "MVPLAYER";
    private static final String MVNPC = "MVNPC";
    private static final String CONVERSATION = "CONVERSATION";
    private static final String CUTSCENE = "CUTSCENE";
    private static final String SCENE = "SCENE";
    private static final String LASTSCENE = "LASTSCENE";
    private static final String RANDOM = "RANDOM";
    private static final String SHOWTEXT = "SHOWTEXT";
    private static final String WAITTIME = "WAITTIME";
  
    
    
    	  /**
	   * Store all effects selection. Connects the type of effect with the number of times that has been used
	   */
	  private HashMap<String,ListElements> selectedEffects;
    
	  public SelectedEffectsController(){
	      init();
	  }
	  
	  public ListElements[] getMostVisiteEffects(){
	      ArrayList<ListElements> list = new ArrayList<ListElements>(selectedEffects.values());
	      Collections.sort(list);
	      ListElements[] values=new ListElements[4];
	      for (int i=0;i<4;i++){
		  values[i]= list.get(NUMBER_OF_EFFECTS-i-1);
	      }
	      return values;
	  }
	  
	  /**
	     * Restore the past effect selection
	     */
	    private void init(){
		final String[] effectNames = { ACTIVATE,DEACTIVATE,INCREMENT,DECREMENT ,SETVALUE ,MACRO ,CONSUME,GENERATE ,
			CANCEL ,SPPLAYER ,SPNPC,BOOK,SOUND,ANIMATION ,MVPLAYER ,MVNPC ,CONVERSATION ,CUTSCENE,SCENE ,LASTSCENE ,RANDOM ,
			SHOWTEXT,WAITTIME };
		selectedEffects = new HashMap<String,ListElements>();
		for (int i=0; i< effectNames.length; i++){
		    String result=ProjectConfigData.getProperty(effectNames[i]);
		    if (result!=null)
			selectedEffects.put(effectNames[i],new ListElements(effectNames[i],Integer.parseInt(result)));
		    else 
			selectedEffects.put(effectNames[i],new ListElements(effectNames[i],new Integer(0)));
		}

	    }
	    
	    public void addSelectedEffect(String name){
		int value = selectedEffects.get(name).getValue();
		    selectedEffects.put(name, new ListElements(name,value+1));
	    }
	    
	    public static String convertNames(String effectName){

			
		if (effectName.equals(TextConstants.getText( "Effect.Activate" ))){
		    return ACTIVATE;
		}else if (effectName.equals(TextConstants.getText( "Effect.Deactivate" ))){
		    return DEACTIVATE;
		}else if (effectName.equals(TextConstants.getText( "Effect.SetValue" ))){
		    return SETVALUE;
		}else if (effectName.equals(TextConstants.getText( "Effect.IncrementVar" ))){
		    return INCREMENT;
		}else if (effectName.equals(TextConstants.getText( "Effect.DecrementVar" ))){
		    return DECREMENT;
		}else if (effectName.equals(TextConstants.getText( "Effect.MacroReference" ))){
		    return MACRO;
		}else if (effectName.equals(TextConstants.getText( "Effect.ConsumeObject" ))){
		    return CONSUME;
		}else if (effectName.equals(TextConstants.getText( "Effect.GenerateObject" ))){
		    return GENERATE;
		}else if (effectName.equals(TextConstants.getText( "Effect.CancelAction" ))){
		    return CANCEL;
		}else if (effectName.equals(TextConstants.getText( "Effect.SpeakPlayer" ))){
		    return SPPLAYER;
		}else if (effectName.equals(TextConstants.getText( "Effect.SpeakCharacter" ))){
		    return SPNPC;
		}else if (effectName.equals(TextConstants.getText( "Effect.TriggerBook" ))){
		    return BOOK;
		}else if (effectName.equals(TextConstants.getText( "Effect.PlaySound" ))){
		    return SOUND;
		}else if (effectName.equals(TextConstants.getText( "Effect.TriggerConversation" ))){
		    return CONVERSATION;
		}else if (effectName.equals(TextConstants.getText( "Effect.TriggerCutscene" ))){
		    return CUTSCENE;
		}else if (effectName.equals(TextConstants.getText( "Effect.TriggerScene" ))){
		    return SCENE;
		}else  if (effectName.equals(TextConstants.getText( "Effect.PlayAnimation" ))){
		    return ANIMATION;
		}else if (effectName.equals(TextConstants.getText( "Effect.MovePlayer" ))){
		    return MVPLAYER;
		}else if (effectName.equals(TextConstants.getText( "Effect.MoveCharacter" ))){
		    return MVNPC;
		}else if (effectName.equals(TextConstants.getText( "Effect.TriggerLastScene" ))){
		    return LASTSCENE;
		}else if (effectName.equals(TextConstants.getText( "Effect.RandomEffect" ))){
		    return RANDOM;
		}else if (effectName.equals(TextConstants.getText( "Effect.ShowText" ))){
		    return SHOWTEXT;
		}else if (effectName.equals(TextConstants.getText( "Effect.WaitTime" )))
		    return WAITTIME;
		
		return null;
	    }
	    
	    public static String reconvertNames(String effectName){
		if (effectName.equals(ACTIVATE)){
		    return TextConstants.getText( "Effect.Activate" );
		}else if (effectName.equals(DEACTIVATE)){
		    return TextConstants.getText( "Effect.Deactivate" );
		}else if (effectName.equals(SETVALUE)){
		    return TextConstants.getText( "Effect.SetValue" );
		}else if (effectName.equals(INCREMENT)){
		    return TextConstants.getText( "Effect.IncrementVar" );
		}else if (effectName.equals(DECREMENT)){
		    return TextConstants.getText( "Effect.DecrementVar" );
		}else if (effectName.equals(MACRO)){
		    return TextConstants.getText( "Effect.MacroReference" );
		}else if (effectName.equals(CONSUME)){
		    return TextConstants.getText( "Effect.ConsumeObject" );
		}else if (effectName.equals(GENERATE)){
		    return TextConstants.getText( "Effect.GenerateObject" );
		}else if (effectName.equals(CANCEL)){
		    return TextConstants.getText( "Effect.CancelAction" );
		}else if (effectName.equals(SPPLAYER)){
		    return TextConstants.getText( "Effect.SpeakPlayer" );
		}else if (effectName.equals(SPNPC)){
		    return TextConstants.getText( "Effect.SpeakCharacter" );
		}else if (effectName.equals(BOOK)){
		    return TextConstants.getText( "Effect.TriggerBook" );
		}else if (effectName.equals(SOUND)){
		    return TextConstants.getText( "Effect.PlaySound" );
		}else if (effectName.equals(CONVERSATION)){
		    return TextConstants.getText( "Effect.TriggerConversation" );
		}else if (effectName.equals(CUTSCENE)){
		    return TextConstants.getText( "Effect.TriggerCutscene" );
		}else if (effectName.equals(SCENE)){
		    return TextConstants.getText( "Effect.TriggerScene" );
		}else if (effectName.equals(ANIMATION)){
		    return TextConstants.getText( "Effect.PlayAnimation" );
		}else if (effectName.equals(MVPLAYER)){
		    return TextConstants.getText( "Effect.MovePlayer" );
		}else if (effectName.equals(MVNPC)){
		    return TextConstants.getText( "Effect.MoveCharacter" );
		}else if (effectName.equals(LASTSCENE)){
		    return TextConstants.getText( "Effect.TriggerLastScene" );
		}else if (effectName.equals(RANDOM)){
		    return TextConstants.getText( "Effect.RandomEffect" );
		}else if (effectName.equals(SHOWTEXT)){
		    return TextConstants.getText( "Effect.ShowText" );
		}else if (effectName.equals(WAITTIME))
		    return TextConstants.getText( "Effect.WaitTime" );
		else 
		    return null;
	    }
	    
	  
	  public class ListElements implements Comparable{

		private String name;
		
		private Integer value;
		
		public ListElements(String name,Integer value){
		    this.name=name;
		    this.value=value;
		}
		
		public String getName(){
		    return name;
		}
		
		
		public Integer getValue(){
		    return value;
		}
		
		@Override
		public int compareTo(Object o) {
		    ListElements listElement= (ListElements)o;
		    return this.value.compareTo(listElement.value);
		}
		
	    }
}
