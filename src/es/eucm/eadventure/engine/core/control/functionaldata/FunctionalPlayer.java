package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Color;

import es.eucm.eadventure.common.data.chapterdata.resources.Asset;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.animations.pc.PCExamining;
import es.eucm.eadventure.engine.core.control.animations.pc.PCGiving;
import es.eucm.eadventure.engine.core.control.animations.pc.PCGrabbing;
import es.eucm.eadventure.engine.core.control.animations.pc.PCIdle;
import es.eucm.eadventure.engine.core.control.animations.pc.PCLooking;
import es.eucm.eadventure.engine.core.control.animations.pc.PCState;
import es.eucm.eadventure.engine.core.control.animations.pc.PCTalking;
import es.eucm.eadventure.engine.core.control.animations.pc.PCUsing;
import es.eucm.eadventure.engine.core.control.animations.pc.PCUsingSingle;
import es.eucm.eadventure.engine.core.control.animations.pc.PCWalking;
import es.eucm.eadventure.engine.core.control.animations.pc.PCWalkingToExamine;
import es.eucm.eadventure.engine.core.control.animations.pc.PCWalkingToExit;
import es.eucm.eadventure.engine.core.control.animations.pc.PCWalkingToGive;
import es.eucm.eadventure.engine.core.control.animations.pc.PCWalkingToGrab;
import es.eucm.eadventure.engine.core.control.animations.pc.PCWalkingToTalk;
import es.eucm.eadventure.engine.core.control.animations.pc.PCWalkingToUse;
import es.eucm.eadventure.engine.core.control.animations.pc.PCWalkingToUseSingle;
import es.eucm.eadventure.common.data.chapterdata.Exit;
import es.eucm.eadventure.common.data.chapterdata.elements.Element;
import es.eucm.eadventure.common.data.chapterdata.elements.Player;
import es.eucm.eadventure.common.data.chapterdata.resources.Resources;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * The player
 */
public class FunctionalPlayer extends FunctionalElement implements TalkingElement {

    /**
     * Player's idle state
     */
    public static final int IDLE = 0;

    /**
     * Player's walking state
     */
    public static final int WALK = 1;

    /**
     * Player's examining state
     */
    public static final int EXAMINE = 2;

    /**
     * Player's looking state
     */
    public static final int LOOK = 3;

    /**
     * Player's grabbing state
     */
    public static final int GRAB = 4;

    /**
     * Player's walking to examine state
     */
    public static final int WALKING_EXAMINE = 5;

    /**
     * Player's walking to grab state
     */
    public static final int WALKING_GRAB = 6;

    /**
     * Player's walking to talk state
     */
    public static final int WALKING_TALK = 7;

    /**
     * Player's walking to give state
     */
    public static final int WALKING_GIVE = 8;

    /**
     * Player's using state
     */
    public static final int USE = 9;

    /**
     * Player's walking to use state
     */
    public static final int WALKING_USE = 10;

    /**
     * Player's giving state
     */
    public static final int GIVE = 11;

    /**
     * Player's talking state
     */
    public static final int TALK = 12;
    
    /**
     * Player's walking to exit state
     */
    public static final int WALKING_EXIT = 13;
    
    /**
     * Player's using single element state
     */
    public static final int USE_SINGLE = 14;
    
    /**
     * Player's walking to use a single element state
     */
    public static final int WALKING_USE_SINGLE = 15;
    
    /**
     * Default speed of the player.
     */
    public static final float DEFAULT_SPEED = 120.0f;
    
    public static final float SPEED_TRANSPARENT_MODE=5000;
    
    //public static final float X_TEXT_TRANSPARENT_MODE;
    
    //public static final float Y_TEXT_TRANSPARENT_MODE;

    /**
     * Speed in the X coordinate.
     */
    protected float speedX;

    /**
     * Speed in the Y coordinate.
     */
    protected float speedY;

    /**
     * Player containing the data
     */
    private Player player;
    
    /**
     * Front color of the player's text
     */
    private Color textFrontColor;
    
    /**
     * Border color of the player's text
     */
    private Color textBorderColor;
    
    /**
     * Resources being used by the character
     */
    private Resources resources;

    /**
     * Idle state 
     */
    private PCState idleAnimation;
    
    /**
     * Walking state
     */
    private PCState walkAnimation;
    
    /**
     * Walking to examine state
     */
    private PCState walkingExamineAnimation;

    /**
     * Examining state
     */
    private PCState examineAnimation;
    
    /**
     * Looking state
     */
    private PCState lookAnimation;
    
    /**
     * Walking to grab state
     */
    private PCState walkingGrabAnimation;

    /**
     * Grabbing state
     */
    private PCState grabAnimation;

    /**
     * Walking to talk state
     */
    private PCState walkingTalkAnimation;
    
    /**
     * Talking animation
     */
    private PCTalking talkingAnimation;

    /**
     * Walking to give state
     */
    private PCState walkingGiveAnimation;
    
    /**
     * Giving animation
     */
    private PCState givingAnimation;

    /**
     * Walking to use state
     */
    private PCState walkingUseAnimation;

    /**
     * Using state
     */
    private PCState usingAnimation;
    
    /**
     * Walking to exit animation
     */
    private PCState walkingExitAnimation;
    
    /**
     * Using single item state
     */
    private PCState usingSingleAnimation;
    
    /**
     * Walking to use single item state
     */
    private PCState walkingUseSingleAnimation;

    /**
     * Current state
     */
    private PCState currentState;

    /**
     * Destiny coordenates
     */
    private int destX, destY;

    /**
     * Element the player will use to perform the action
     */
    private FunctionalElement finalElement;

    /**
     * Optional element the player will use to perform the action
     */
    private FunctionalElement optionalElement;
    
    /**
     * The exit where the player is heading
     */
    private Exit targetExit;
    
    /**
     * Current player's direction
     */
    private int currentDirection = -1;
    
    /**
     * Last player's direction set
     */
    //private int lastDirection = -1;
    
    
    private boolean isTransparent=false;

    /**
     * @return the isTransparent
     */
    public boolean isTransparent( ) {
        return isTransparent;
    }

    /**
     * @param isTransparent the isTransparent to set
     */
    public void setTransparent( boolean isTransparent ) {
        this.isTransparent = isTransparent;
    }

    /**
     * Creates a new FunctionalPlayer
     * @param player the player's data
     */
    public FunctionalPlayer( Player player ) {
        super( 0, 0 );
        this.player = player;
        destX = 0;
        destY = 0;
        
        textFrontColor = generateColor( player.getTextFrontColor( ) );
        textBorderColor = generateColor( player.getTextBorderColor( ) );
        
        // Select the resources block
        resources = createResourcesBlock( );

        // Create the states
        idleAnimation = new PCIdle( this );
        walkAnimation = new PCWalking( this );
        examineAnimation = new PCExamining( this );
        lookAnimation = new PCLooking( this );
        grabAnimation = new PCGrabbing( this );
        walkingExamineAnimation = new PCWalkingToExamine( this );
        walkingGrabAnimation = new PCWalkingToGrab( this );
        walkingTalkAnimation = new PCWalkingToTalk( this );
        walkingGiveAnimation = new PCWalkingToGive( this );
        usingAnimation = new PCUsing( this );
        walkingUseAnimation = new PCWalkingToUse( this );
        givingAnimation = new PCGiving( this );
        talkingAnimation = new PCTalking( this );
        walkingExitAnimation = new PCWalkingToExit( this );
        usingSingleAnimation = new PCUsingSingle( this );
        walkingUseSingleAnimation = new PCWalkingToUseSingle( this );

        currentState = idleAnimation;
        
        speedX = 0;
        speedY = 0;
    }

    /**
     * Returns the player's data
     * @return the player's data
     */
    public Player getPlayer( ) {
        return player;
    }
    
    /**
     * Returns the front color of the player's text
     * @return Front color of the text
     */
    public Color getTextFrontColor( ) {
        return textFrontColor;
    }
    
    /**
     * Returns the border color of the player's text
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
            
            // Flush the past resources from the images cache
            MultimediaManager.getInstance( ).flushImagePool( MultimediaManager.IMAGE_PLAYER );
            
            // Update the assets
            idleAnimation.loadResources( );
            walkAnimation.loadResources( );
            examineAnimation.loadResources( );
            lookAnimation.loadResources( );
            grabAnimation.loadResources( );
            walkingExamineAnimation.loadResources( );
            walkingGrabAnimation.loadResources( );
            walkingTalkAnimation.loadResources( );
            walkingGiveAnimation.loadResources( );
            usingAnimation.loadResources( );
            walkingUseAnimation.loadResources( );
            givingAnimation.loadResources( );
            talkingAnimation.loadResources( );
            walkingExitAnimation.loadResources( );
            usingSingleAnimation.loadResources( );
            walkingUseSingleAnimation.loadResources( );
        }
    }

    @Override
    public Element getElement( ) {
        return player;
    }

    @Override
    public int getWidth( ) {
        return currentState.getImage( ).getWidth( null );
    }

    @Override
    public int getHeight( ) {
        return currentState.getImage( ).getHeight( null );
    }

    /* Own methods */

    /**
     * Changes the player's state
     * @param animationState new player's state
     */
    public void setState( int animationState ) {

        switch( animationState ) {
            case WALK:
                currentState = walkAnimation;
                break;
            case IDLE:
                currentState = idleAnimation;
                break;
            case EXAMINE:
                currentState = examineAnimation;
                break;
            case LOOK:
                currentState = lookAnimation;
                break;
            case GRAB:
                currentState = grabAnimation;
                break;
            case WALKING_EXAMINE:
                currentState = walkingExamineAnimation;
                break;
            case WALKING_GRAB:
                currentState = walkingGrabAnimation;
                break;
            case WALKING_TALK:
                currentState = walkingTalkAnimation;
                break;
            case WALKING_GIVE:
                currentState = walkingGiveAnimation;
                break;
            case USE:
                currentState = usingAnimation;
                break;
            case WALKING_USE:
                currentState = walkingUseAnimation;
                break;
            case GIVE:
                currentState = givingAnimation;
                break;
            case TALK:
                currentState = talkingAnimation;
                break;
            case WALKING_EXIT:
                currentState = walkingExitAnimation;
                break;
            case USE_SINGLE:
                currentState = usingSingleAnimation;
                break;
            case WALKING_USE_SINGLE:
                currentState = walkingUseSingleAnimation;
                break;
        }
        currentState.initialize( );
        //System.out.println("FP:"+animationState);
        //if( currentDirection != -1 )
        //    currentState.setCurrentDirection( currentDirection );
        //currentDirection = -1;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#draw(java.awt.Graphics2D)
     */
    public void draw( ) {
        if (!isTransparent)
            currentState.draw( Math.round( x ), Math.round( y ) );
        else
            currentState.draw( Math.round( GUI.WINDOW_WIDTH/2.0f+Game.getInstance().getFunctionalScene().getOffsetX( ) ), Math.round( GUI.WINDOW_HEIGHT*1.0f/6.0f+getHeight() ) );
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
        return ( this.x - getWidth( )/2 < x ) && ( x < this.x + getWidth( )/2 ) && ( this.y - getHeight( ) < y ) && ( y < this.y );
    }

    @Override
    public boolean canPerform( int action ) {
        boolean canPerform = false;

        switch( action ) {
            case ActionManager.ACTION_LOOK:
            case ActionManager.ACTION_EXAMINE:
                canPerform = true;
                break;

            case ActionManager.ACTION_GRAB:
            case ActionManager.ACTION_USE:
            case ActionManager.ACTION_GIVE:
            case ActionManager.ACTION_GOTO:
            case ActionManager.ACTION_USE_WITH:
            case ActionManager.ACTION_GIVE_TO:
            case ActionManager.ACTION_TALK:
                canPerform = false;
                break;
        }

        return canPerform;
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.TalkingElement#speak(java.lang.String)
     */
    public void speak( String text ) {
        if (text!=null){
            talkingAnimation.setText( text );
            setState( TALK );
        } else {
            setState( IDLE );
        }
    }
    
    public void speak( String text, String audioPath ) {
        
        talkingAnimation.setAudio(audioPath);
        /*try {
            Thread.sleep( 500 );
        } catch( InterruptedException e ) {
            e.printStackTrace();
        }*/
        talkingAnimation.setText( text );
        setState( TALK );
    }

    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.TalkingElement#stopTalking()
     */
    public void stopTalking( ) {
        talkingAnimation.setAudio( null );
        setState( IDLE );
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.TalkingElement#isTalking()
     */
    public boolean isTalking( ) {
        return currentState == talkingAnimation;
    }

    /**
     * Changes the destiny position of the player
     * @param x horizontal position of the destiny position
     * @param y vertical position of the destiny position
     */
    public void setDestiny( int x, int y ) {
        destX = x;
        destY = y;
    }

    /**
     * Returns the horizontal position of the player's destiny position
     * @return the horizontal position of the player's destiny position
     */
    public int getDestX( ) {
        return destX;
    }

    /**
     * Returns the vertical position of the player's destiny position
     * @return the vertical position of the player's destiny position
     */
    public int getDestY( ) {
        return destY;
    }
    
    /**
     * Returns if the player is walking
     * @return true if the player is walking, false otherwise
     */
    public boolean isWalking( ) {
        return currentState == walkAnimation;
    }

    /**
     * Changes the player's destiny element.
     * That is, the element to be examined, grabbed, talked to...
     * @param destinyElement the new player's destiny element
     */
    public void setFinalElement( FunctionalElement destinyElement ) {
        this.finalElement = destinyElement;
        if( destinyElement != null ) {
            this.destX = Math.round( finalElement.getX( ) );
            this.destY = Math.round( finalElement.getY( ) );
            
            int[] finalDest = Game.getInstance( ).getFunctionalScene( ).checkPlayerAgainstBarriers( destX, destY );
            this.destX = finalDest[0];
            this.destY = finalDest[1];
        }
    }

    /**
     * Returns the player's destiny element
     * That is, the element to be examined, grabbed, talked to...
     * @return the player's destiny element
     */
    public FunctionalElement getFinalElement( ) {
        return finalElement;
    }

    /**
     * Set the player's optional element.
     * That is, the element to be used with, given to, ...
     * @param element the new player's optional element
     */
    public void setOptionalElement( FunctionalElement element ) {
        optionalElement = element;
    }

    /**
     * Returns the player's optional element.
     * That is, the element to be used with, given to, ...
     * @return the player's optional element
     */
    public FunctionalElement getOptionalElement( ) {
        return optionalElement;
    }
    
    /**
     * Changes the player's target exit
     * @param exit the new target exit
     */
    public void setTargetExit( Exit exit ) {
        targetExit = exit;
    }
    
    /**
     * Returns the player's target exit
     * @return the player's target exit
     */
    public Exit getTargetExit( ) {
        return targetExit;
    }
    
    /**
     * Changes the player's current direction
     * @param direction the new player's direction
     */
    public void setDirection( int direction ) {
        currentDirection = direction;
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
        if (!isTransparent)
            this.speedX = speedX;
        else if (speedX<0){
            this.speedX=-SPEED_TRANSPARENT_MODE;
        }
        else if (speedX>0){
            this.speedX=SPEED_TRANSPARENT_MODE;
        }
        else{
            this.speedX=0;
        }

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
     * @param speedY New Y coordinate speed
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
        for( int i = 0; i < player.getResources( ).size( ) && newResources == null; i++ )
            if( new FunctionalConditions(player.getResources( ).get( i ).getConditions( )).allConditionsOk( ) )
                newResources = player.getResources( ).get( i );

        // If no resource block is available, create a default one 
        if (newResources == null){
            newResources = new Resources();
            newResources.addAsset( new Asset( Player.RESOURCE_TYPE_SPEAK_DOWN, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( Player.RESOURCE_TYPE_SPEAK_UP, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( Player.RESOURCE_TYPE_SPEAK_RIGHT, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( Player.RESOURCE_TYPE_WALK_DOWN, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( Player.RESOURCE_TYPE_WALK_UP, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( Player.RESOURCE_TYPE_WALK_RIGHT, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( Player.RESOURCE_TYPE_USE_RIGHT, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( Player.RESOURCE_TYPE_STAND_UP, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( Player.RESOURCE_TYPE_STAND_DOWN, ResourceHandler.DEFAULT_ANIMATION ) );
            newResources.addAsset( new Asset( Player.RESOURCE_TYPE_STAND_RIGHT, ResourceHandler.DEFAULT_ANIMATION ) );
        }
        return newResources;
    }
}
