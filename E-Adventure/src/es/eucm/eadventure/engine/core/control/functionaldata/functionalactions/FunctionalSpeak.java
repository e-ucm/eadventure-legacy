package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import java.util.Timer;
import java.util.TimerTask;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.Options;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class FunctionalSpeak extends FunctionalAction {

	private String[] text;

    private long audioId=-1;
    
    /**
     * This is an Voice object of FreeTTS, that is used to synthesize the sound of a 
     * conversation line.
     */
    private Voice voice;
    
    /**
     * The speech must be launched in another thread
     */
    private TTask task;


    /**
     * Time spent in this state
     */
    private long totalTime;
    
    /**
     * The time the character will be talking
     */
    private int timeTalking;

	public FunctionalSpeak(Action action, String text) {
		super(action);
		type = ActionManager.ACTION_TALK;
		setText(text);
	}

	public FunctionalSpeak(Action action, String text, String audioPath) {
		super(action);
		type = ActionManager.ACTION_TALK;
		setText(text);
		setAudio(audioPath);
	}

	@Override
	public void start(FunctionalPlayer functionalPlayer) {
		this.functionalPlayer = functionalPlayer;
		totalTime = 0;
		
        Resources resources = functionalPlayer.getResources( );
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        Animation[] animations = new Animation[4];
        
        animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        if (resources.getAssetPath(Player.RESOURCE_TYPE_SPEAK_LEFT) != null && resources.getAssetPath(Player.RESOURCE_TYPE_SPEAK_LEFT) != AssetsController.ASSET_EMPTY_ANIMATION)
        	animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_LEFT), false, MultimediaManager.IMAGE_PLAYER);
        else
        	animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_UP ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
        
        functionalPlayer.setAnimation(animations, -1);
   	}

	@Override
	public void update(long elapsedTime) {
		totalTime += elapsedTime;
		
        if( totalTime > timeTalking &&(audioId==-1 || !MultimediaManager.getInstance( ).isPlaying( audioId ))) {
        	finished = true;
        	functionalPlayer.popAnimation();
        	stopTTSTalking();
        }
	}

    /**
     * Set the text to be displayed. This is what the player is saying
     * @param text the text to be displayed
     */
    public void setText( String text ) {
        this.text = GUI.getInstance( ).splitText( text );
        
        float multiplier = 1;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_SLOW ) multiplier = 1.5f;
        if( Game.getInstance( ).getOptions( ).getTextSpeed( ) == Options.TEXT_FAST ) multiplier = 0.8f;
        
        timeTalking = (int)( 300 * text.split( " " ).length * multiplier );
        if( timeTalking < (int)( 1400 * multiplier ) ) timeTalking = (int)( 1400 * multiplier );
    }
    
    public void setAudio( String audioPath ) {
        if (audioPath==null){
            if (audioId!=-1){
                MultimediaManager.getInstance( ).stopPlayingInmediately( audioId );
                while(MultimediaManager.getInstance( ).isPlaying( audioId )){
                try {
                    Thread.sleep( 1 );
                } catch( InterruptedException e ) {
                }}
                audioId = -1;
            }
        }
        else{
        if (audioId!=-1){
            MultimediaManager.getInstance( ).stopPlayingInmediately( audioId );
            while(MultimediaManager.getInstance( ).isPlaying( audioId )){
            try {
                Thread.sleep( 1 );
            } catch( InterruptedException e ) {
            }}
        }
        
        //Gap between audios: 0.5s
        try {
            Thread.sleep( 500 );
        } catch( InterruptedException e ) {
        }

        
        audioId = MultimediaManager.getInstance( ).loadSound( audioPath, false );
        MultimediaManager.getInstance( ).startPlaying( audioId );
        while(!MultimediaManager.getInstance( ).isPlaying( audioId )){
            try {
                Thread.sleep( 1 );
            } catch( InterruptedException e ) {
            }}
        }
    }

    public void setSpeakFreeTTS(String text, String voice){
   	 
    	task = new TTask(voice, text);
    	Timer timer = new Timer () ;
    	timer.schedule(task, 0);
    	while (task.getDuration()==0){
        	try {
    			Thread.sleep( 1 );
    		} catch (InterruptedException e) {
    		}
        	}
    	int wordsPerSecond = (int)task.getDuration()/60;
    	String[] words= text.split(" ");
    	timeTalking = (words.length/wordsPerSecond) *1000;
    	
   }
    
    public void stopTTSTalking(){
    	if (task != null)
    		task.deallocate();
    }

    public class TTask extends TimerTask{

    	private String voiceText;
    	private String text;
    	private float duration;
    	private boolean deallocate;
    	
    	public TTask ( String voiceText, String text ){
    		this.voiceText = voiceText;
    		this.text = text;
    		this.deallocate = false;
    	}
    	
			@Override
			public void run() {
		    	 VoiceManager voiceManager = VoiceManager.getInstance();
		         // TODO ver que la voz exista!!!
		         voice = voiceManager.getVoice(voiceText);
		         voice.allocate();
		         duration =voice.getRate();
		         voice.speak(text);
		         deallocate();
		         
			}
		
			public void deallocate(){
				if (!deallocate) {
					voice.deallocate();
					deallocate = true;
				}
			}
			
			public float getDuration(){
				return duration;
			}
    }

	@Override
	public void drawAditionalElements() {
        if( !text.equals( "" ) ) {
        	int posX;
        	int posY;
        	if (functionalPlayer != null && !functionalPlayer.isTransparent()) {
	        	posX = (int) functionalPlayer.getX() - Game.getInstance( ).getFunctionalScene( ).getOffsetX( );
	            posY = (int) functionalPlayer.getY() - functionalPlayer.getHeight( );
        	} else {
        		posX = Math.round( GUI.WINDOW_WIDTH/2.0f+Game.getInstance().getFunctionalScene().getOffsetX( ) );
        		posY = Math.round( GUI.WINDOW_HEIGHT*1.0f/6.0f+ (functionalPlayer!=null?functionalPlayer.getHeight():0) );
        	}
        	GUI.getInstance().addTextToDraw( text, posX , posY, functionalPlayer.getTextFrontColor( ), functionalPlayer.getTextBorderColor( ) );
        }
	}

	@Override
	public void stop() {
		if (this.isStarted())
			stopTTSTalking();
	}

	@Override
	public void setAnotherElement(FunctionalElement element) {
	}

}
