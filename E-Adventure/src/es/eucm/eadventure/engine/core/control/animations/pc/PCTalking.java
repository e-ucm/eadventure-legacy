package es.eucm.eadventure.engine.core.control.animations.pc;


import java.util.Timer;
import java.util.TimerTask;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.Options;
import es.eucm.eadventure.engine.core.control.animations.npc.NPCTalking.TTask;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The talking animation for the player
 */
public class PCTalking extends PCState {

    /**
     * The text the player will speak
     */
    private String[] text;
    
    private long audioId=-1;

    /**
     * Time spent in this state
     */
    private long totalTime;
    
    /**
     * The time the character will be talking
     */
    private int timeTalking;

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
     * Creates a new PCTalking
     * @param player the reference to the player
     */
    public PCTalking( FunctionalPlayer player ) {
        super( player );
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
                    e.printStackTrace();
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
                e.printStackTrace();
            }}
        }
        
        //Gap between audios: 0.5s
        try {
            Thread.sleep( 500 );
        } catch( InterruptedException e ) {
            e.printStackTrace();
        }

        
        audioId = MultimediaManager.getInstance( ).loadSound( audioPath, false );
        MultimediaManager.getInstance( ).startPlaying( audioId );
        while(!MultimediaManager.getInstance( ).isPlaying( audioId )){
            try {
                Thread.sleep( 1 );
            } catch( InterruptedException e ) {
                e.printStackTrace();
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
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	}
    	int wordsPerSecond = (int)task.getDuration()/60;
    	String[] words= text.split(" ");
    	timeTalking = (words.length/wordsPerSecond) *1000;
    	
   }
    
    public void stopTTSTalking(){
    	if (task!=null)
    	task.deallocate();
    }


    @Override
    public void update( long elapsedTime ) {
        totalTime += elapsedTime;
        
        if( totalTime > timeTalking &&(audioId==-1 || !MultimediaManager.getInstance( ).isPlaying( audioId )))
            player.setState( FunctionalPlayer.IDLE );
    }

    @Override
    public void draw( int x, int y ) {
        super.draw( x, y );

        // If there is a line to speak, draw it
        if( !text.equals( "" ) )
            GUI.getInstance().addTextToDraw( text, x - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), y - player.getHeight( ), player.getTextFrontColor( ), player.getTextBorderColor( ) );
    }

    @Override
    public void initialize( ) {
        totalTime = 0;
    }
    
    @Override
    public void loadResources() {
        Resources resources = player.getResources( );
        
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_UP ), false, MultimediaManager.IMAGE_PLAYER );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_SPEAK_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
    }
    
    public class TTask extends TimerTask{

    	private String voiceText;
    	private String text;
    	private float duration;
    	private boolean dealocate;
    	
    	public TTask ( String voiceText, String text ){
    		this.voiceText = voiceText;
    		this.text = text;
    		this.dealocate=false;
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
				if (!dealocate){
					voice.deallocate();
					dealocate=true;
				}
				
			}
			
			public float getDuration(){
				return duration;
			}
    }

}
