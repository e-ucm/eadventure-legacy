package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import es.eucm.eadventure.common.data.chapter.elements.Element;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.resources.Asset;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.animations.npc.NPCIdle;
import es.eucm.eadventure.engine.core.control.animations.npc.NPCState;
import es.eucm.eadventure.engine.core.control.animations.npc.NPCTalking;
import es.eucm.eadventure.engine.core.control.animations.npc.NPCWalking;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * A non playing character (npc) in the game
 */
public class FunctionalNPC extends FunctionalElement implements TalkingElement {

    /**
     * npc's idle state
     */
    public static final int IDLE = 0;

    /**
     * npc's talking state
     */
    public static final int TALK = 1;
    
    /**
     * npc's walking state
     */
    public static final int WALK = 2;
    
    /**
     * Default velocity for the characters.
     */
    public static final float DEFAULT_SPEED = 120.0f;

    /**
     * Speed in the X coordinate.
     */
    protected float speedX;

    /**
     * Speed in the Y coordinate.
     */
    protected float speedY;

    /**
     * NPC containing the data
     */
    private NPC npc;
    
    /**
     * Front color of the character's text
     */
    private Color textFrontColor;
    
    /**
     * Border color of the character's text
     */
    private Color textBorderColor;
    
    /**
     * Resources being used by the character
     */
    private Resources resources;
    
    /**
     * Idle state
     */
    private NPCState idleAnimation;

    /**
     * Speaking state
     */
    private NPCTalking talkingAnimation;
    
    /**
     * Walking state
     */
    private NPCState walkingAnimation;

    /**
     * Current state
     */
    private NPCState currentState;
    
    /**
     * Destiny coordenates
     */
    private int destX, destY;
    
    /**
     * Current player's direction
     */
    private int currentDirection = -1;
    
    /**
     * Last player's direction set
     */
    //private int lastDirection = -1;
    
    /**
     * This is an Voice object of FreeTTS, that is used to synthesize the sound of a 
     * conversation line.
     */
    private Voice voice;
    
    /**
     * Creates a new FunctionalNCP
     * @param npc the npc's data
     * @param x the npc's horizontal position
     * @param y the npc's vertical position
     */
    public FunctionalNPC( NPC npc, int x, int y ) {
        super( x, y );
        this.npc = npc;
        
        textFrontColor = generateColor( npc.getTextFrontColor( ) );
        textBorderColor = generateColor( npc.getTextBorderColor( ) );

        // Select the resources
        resources = createResourcesBlock( );
        
        // Create the states of the character
        idleAnimation = new NPCIdle( this );
        talkingAnimation = new NPCTalking( this );
        walkingAnimation = new NPCWalking( this );
        
        destX = 0;
        destY = 0;

        currentState = idleAnimation;
        
        speedX = 0;
        speedY = 0;
    }

    /**
     * Returns this npc's data
     * @return this npc's data
     */
    public NPC getNPC( ) {
        return npc;
    }

    /**
     * Returns the front color of the character's text
     * @return Front color of the text
     */
    public Color getTextFrontColor( ) {
        return textFrontColor;
    }
    
    /**
     * Returns the border color of the character's text
     * @return Border color of the text
     */
    public Color getTextBorderColor( ) {
        return textBorderColor;
    }
    
    /**
     * Returns the resources of the npc
     * @return Resources of the npc
     */
    public Resources getResources( ) {
        return resources;
    }
    
    /**
     * Updates the resources of the npc (if the current resources and the new one are different)
     */
    public void updateResources( ) {
        // Get the new resources
        Resources newResources = createResourcesBlock( );
        
        // If the resources have changed, load the new one
        if( resources != newResources ) {
            resources = newResources;
            
            // Update the resources of the states
            idleAnimation.loadResources( );
            talkingAnimation.loadResources( );
            walkingAnimation.loadResources( );
        }
    }
    
    @Override
    public Element getElement( ) {
        return npc;
    }

    @Override
    public int getWidth( ) {
        return currentState.getImage( ).getWidth( null );
    }

    @Override
    public int getHeight( ) {
        return currentState.getImage( ).getHeight( null );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#draw(java.awt.Graphics2D)
     */
    public void draw( ) {
        currentState.draw( Math.round( x ), Math.round( y ) );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#update(long)
     */
    public void update( long elapsedTime ) {
        currentState.update( elapsedTime );
        currentState.updateAnimation( );
        currentState.getCurrentAnimation( ).update( elapsedTime );
    }
    
    @Override
    public boolean isPointInside( float x, float y ) {
//        return ( this.x - getWidth( ) / 2 < x ) && ( x < this.x + getWidth( ) / 2 ) && ( this.y - getHeight( ) < y ) && ( y < this.y );
        boolean isInside = false;
        
        int mousex = (int)( x - ( this.x - getWidth( ) / 2 ) );
        int mousey = (int)( y - ( this.y - getHeight( ) ) );
        
        if( ( mousex >= 0 ) && ( mousex < getWidth() ) && ( mousey >= 0 ) && ( mousey < getHeight() ) ) {
            BufferedImage bufferedImage = (BufferedImage) currentState.getImage();
            int alpha = bufferedImage.getRGB( mousex, mousey ) >>> 24;
            isInside = alpha > 128;
        }
        
        return isInside;
    }

    @Override
    public boolean canPerform( int action ) {
        boolean canPerform = false;

        switch( action ) {
            case ActionManager.ACTION_LOOK:
            case ActionManager.ACTION_EXAMINE:
            case ActionManager.ACTION_GOTO:
            case ActionManager.ACTION_GIVE_TO:
            case ActionManager.ACTION_TALK:
                canPerform = true;
                break;

            case ActionManager.ACTION_GRAB:
            case ActionManager.ACTION_USE:
            case ActionManager.ACTION_GIVE:
            case ActionManager.ACTION_USE_WITH:
                canPerform = false;
                break;
        }

        return canPerform;
    }

    /**
     * Changes this npc's state
     * @param animationState the new npc's state
     */
    public void setState( int animationState ) {
        switch( animationState ) {
            case IDLE:
                currentState = idleAnimation;
                break;
            case TALK:
                currentState = talkingAnimation;
                break;
            case WALK:
                currentState = walkingAnimation;
                break;
        }
        currentState.initialize( );
        //if( currentDirection != -1 )
        //    currentState.setCurrentDirection( currentDirection );
        //currentDirection = -1;
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.functionaldata.TalkingElement#speak(java.lang.String)
     */
    public void speak( String text ) {
        talkingAnimation.setText( text );
        setState( TALK );
    }
    
    public void speak ( String text, String audioPath ){
        talkingAnimation.setAudio(audioPath);
        /*try {
            Thread.sleep( 500 );
        } catch( InterruptedException e ) {
            e.printStackTrace();
        }*/
        talkingAnimation.setText( text );
        setState( TALK );

    }
    
    /**
     * TODO poner bien docu!!!
     * Funcion que va a leer del TTS!!!!!!
     */
    public void speakWithFreeTTS(String text, String voice){
    	// Start the voice
        VoiceManager voiceManager = VoiceManager.getInstance();
        
        // TODO ver que la voz exista!!!
        this.voice = voiceManager.getVoice(voice);
        this.voice.allocate();
        this.voice.speak(text);
        talkingAnimation.setText( text );
        setState( TALK );
    }
    
    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.functionaldata.TalkingElement#stopTalking()
     */
    public void stopTalking( ) {
        talkingAnimation.setAudio( null );
        setState( IDLE );
    }

    /*
     * (non-Javadoc)
     * @see es.eucm.eadventure.engine.engine.control.functionaldata.TalkingElement#isTalking()
     */
    public boolean isTalking( ) {
        return currentState == talkingAnimation;
    }
    
    /**
     * Changes the destiny position of the npc
     * @param x horizontal position of the destiny position
     * @param y vertical position of the destiny position
     */
    public void setDestiny( int x, int y ) {
        destX = x;
        destY = y;

    }

    /**
     * Returns the horizontal position of the npc's destiny position
     * @return the horizontal position of the npc's destiny position
     */
    public int getDestX( ) {
        return destX;
    }

    /**
     * Returns the vertical position of the npc's destiny position
     * @return the vertical position of the npc's destiny position
     */
    public int getDestY( ) {
        return destY;
    }
    
    /**
     * Returns if the NPC is walking
     * @return true if the NPC is walking
     */
    public boolean isWalking( ) {
        return currentState == walkingAnimation;
    }
    
    /**
     * Changes the npc's current direction
     * @param direction the new npc's direction
     */
    public void setDirection( int direction ) {
        currentDirection = direction;
        //lastDirection = direction;
    }
    
    /**
     * Return the las direction set
     */
    public int getDirection() {
        return currentDirection;
    }

    /**
     * Returns the X coordinate speed.
     * @return X coordinate speed
     */
    public float getSpeedX( ) {
        return speedX;
    }

    /**
     * Sets the new X coordinate speed.
     * @param speedX New X coordinate speed
     */
    public void setSpeedX( float speedX ) {
        this.speedX = speedX;
    }

    /**
     * Returns the Y coordinate speed.
     * @return Y coordinate speed
     */
    public float getSpeedY( ) {
        return speedY;
    }

    /**
     * Sets the new Y coordinate speed.
     * @param speedX New Y coordinate speed
     */
    public void setSpeedY( float speedY ) {
        this.speedY = speedY;
    }
    
    /**
     * Creates the current resource block to be used
     */
    public Resources createResourcesBlock( ) {
        
        // Get the active resources block
        Resources newResources = null;
        for( int i = 0; i < npc.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions( npc.getResources( ).get( i ).getConditions( ) ).allConditionsOk( ) )
                newResources = npc.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if (newResources == null){
            newResources = new Resources();
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_SPEAK_DOWN, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_SPEAK_UP, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_SPEAK_RIGHT, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_WALK_DOWN, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_WALK_UP, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_WALK_RIGHT, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_USE_RIGHT, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_STAND_UP, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_STAND_DOWN, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( NPC.RESOURCE_TYPE_STAND_RIGHT, ResourceHandler.DEFAULT_ANIMATION ) );
        }
        return newResources;
    }
}
