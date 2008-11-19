package es.eucm.eadventure.engine.core.control.animations.npc;


import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.Options;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalNPC;
import es.eucm.eadventure.common.data.adventure.DescriptorData;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * A state for a talking npc
 */
public class NPCTalking extends NPCState {

    /**
     * The text the character will speak
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
     * Creates a new NPCTalking
     * @param npc the reference to the npc
     */
    public NPCTalking( FunctionalNPC npc ) {
        super( npc );
    }

    /**
     * Set the text to be displayed. This is what the character is saying
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
    
    public void setAudio( String audioPath){
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
                    }
                }
            }
            
            audioId=MultimediaManager.getInstance( ).loadSound( audioPath, false );
     
            MultimediaManager.getInstance( ).startPlaying( audioId );
                while(!MultimediaManager.getInstance( ).isPlaying( audioId )){
                    try {
                        Thread.sleep( 1 );
                    } catch( InterruptedException e ) {
                        e.printStackTrace();
                    }
                }
            } 
    }

    @Override
    public void update( long elapsedTime ) {
        totalTime += elapsedTime;
        if( totalTime > timeTalking && (audioId==-1 || !MultimediaManager.getInstance( ).isPlaying( audioId ))) {
            npc.setState( FunctionalNPC.IDLE );
        }
    }

    @Override
    public void draw( int x, int y ) {
        super.draw( x, y );

		// If there is a line to speak, draw it
        if( !text.equals( "" ) )
            GUI.getInstance( ).addTextToDraw( text, x - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ), y - npc.getHeight( ), npc.getTextFrontColor( ), npc.getTextBorderColor( ) );
    
    }

    @Override
    public void initialize( ) {
        totalTime = 0;
        // Check the player mode
        if (Game.getInstance( ).getGameDescriptor( ).getPlayerMode( ) == DescriptorData.MODE_PLAYER_1STPERSON
                || Game.getInstance( ).getFunctionalPlayer( ).getX( ) >= npc.getX( )){
            setCurrentDirection( EAST );
        } else 
            setCurrentDirection( WEST );
            
    }
    
    @Override
    public void loadResources() {
        Resources resources = npc.getResources( );

        MultimediaManager multimedia = MultimediaManager.getInstance( );
        animations[EAST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ), false, MultimediaManager.IMAGE_SCENE );
        animations[WEST] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT ), true, MultimediaManager.IMAGE_SCENE );
        animations[NORTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_UP ), false, MultimediaManager.IMAGE_SCENE );
        animations[SOUTH] = multimedia.loadAnimation( resources.getAssetPath( NPC.RESOURCE_TYPE_SPEAK_DOWN ), false, MultimediaManager.IMAGE_SCENE );
    }
}
