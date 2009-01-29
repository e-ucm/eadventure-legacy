package es.eucm.eadventure.engine.core.control.functionaldata;

import java.awt.Color;
import java.awt.Image;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.CustomAction;
import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.InfluenceArea;
import es.eucm.eadventure.common.data.chapter.elements.Element;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Asset;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalAction;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalCustom;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalCustomInteract;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalExamine;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalGive;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalGoTo;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalGrab;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalLook;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalNullAction;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalSpeak;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalTalk;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalUse;
import es.eucm.eadventure.engine.core.control.functionaldata.functionalactions.FunctionalUseWith;
import es.eucm.eadventure.engine.core.data.GameText;

/**
 * The player
 */
public class FunctionalPlayer extends FunctionalElement implements TalkingElement {

    /**
     * Default speed of the player.
     */
    public static final float DEFAULT_SPEED = 120.0f;
    
    public static final float SPEED_TRANSPARENT_MODE=5000;
   
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
    
    public List<FunctionalAction> actionPool;
    
    public List<Animation[]> animationPool;
    
    
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
        speedX = 0;
        speedY = 0;
        layer = -1;
        currentDirection = AnimationState.EAST;
        // Select the resources block
        resources = createResourcesBlock( );

        actionPool = new ArrayList<FunctionalAction>();
        animationPool = new ArrayList<Animation[]>();

        MultimediaManager multimedia = MultimediaManager.getInstance( );


        Animation[] animations = new Animation[4];
        animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_UP ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
        
        animationPool.add(animations);

        // TODO the default animation should change with the orientation...
        
        textFrontColor = generateColor( player.getTextFrontColor( ) );
        textBorderColor = generateColor( player.getTextBorderColor( ) );
        
    }

    
    /**
     * Check if all player conversation lines must be read by synthesizer
     * @return
     * 		true, if all player conversation lines must be read by synthesizer
     */
    public boolean isAlwaysSynthesizer(){
    	return player.isAlwaysSynthesizer();
    }
    
    /**
     * Takes the player voice for synthesizer
     * 
     * @return
     * 		A string representing associates voice
     */
    public String getPlayerVoice(){
    	return player.getVoice();
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

            MultimediaManager multimedia = MultimediaManager.getInstance();
            Animation[] animations = new Animation[4];
            animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
            animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_UP ), false, MultimediaManager.IMAGE_PLAYER );
            animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_STAND_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
            
            this.animationPool.clear();
            this.animationPool.add(animations);
        }
    }

    @Override
    public Element getElement( ) {
        return player;
    }

    @Override
    public int getWidth( ) {
    	return getCurrentAnimation().getImage().getWidth(null);
    }

    @Override
    public int getHeight( ) {
    	return getCurrentAnimation().getImage().getHeight(null);
    }

    
    /**
     * Adds a new action to the pool.
     * The walking action will be added as needed.
     * 
     * @param action The action to add to the pool
     * @param element The element on with the action is performed
     */
    public void addAction(FunctionalAction action) {
    	actionPool.add(action);
    }

    /**
     * Cancel all actions currently being performed
     */
    public void cancelActions() {
    	getCurrentAction().stop();
    	actionPool.clear();
    	cancelAnimations();
    }
    
    public FunctionalAction getCurrentAction() {
    	if (actionPool.size() > 0)
    		return actionPool.get(actionPool.size() - 1);
    	else
    		return new FunctionalNullAction(null);
    }
    
    public void popAction() {
    	if (actionPool.size() > 0)
    		actionPool.remove(actionPool.size() - 1);
    }
    
    /**
     * Adds a new animation to the animation pool, the exact result
     * depends on whether the animation is to be repeated indefinitely
     * or a limited number of times.
     * 
     * @param animation
     * @param repeat
     */
    public void setAnimation(Animation[] animations, int repeat) {
    	//TODO check behavior
    	if (repeat != -1) {
    		animationPool.add(animations);
    	} else {
    		if (animationPool.size() > 1)
    			animationPool.remove(animationPool.size() - 1);
    		animationPool.add(animations);
    	}
    }
    
    /**
     * When an action is completed, it is likely it would like to
     * remove the animation it set
     */
    public void popAnimation() {
    	if (animationPool.size() > 1) {
    		animationPool.remove(animationPool.size() - 1);
    	}
    }
    
    public Animation getCurrentAnimation() {
    	if (currentDirection >= 0 && currentDirection < 4)
    		return animationPool.get(animationPool.size() - 1)[currentDirection];
    	else
    		return animationPool.get(animationPool.size() - 1)[0];
    }

    public void cancelAnimations() {
    	if (animationPool.size() > 0) {
	    	Animation[] temp = animationPool.get(0);
	    	animationPool.clear();
	    	animationPool.add(temp);
    	}
    }
    
    /**
     * Performs the given action with the given element
     * @param element the element that will receive the action
     * @param actionSelected the action to be performed
     */
    public void performActionInElement( FunctionalElement element ) {
    	Game game = Game.getInstance( );
    	int actionSelected = Game.getInstance( ).getActionManager( ).getActionSelected( );
    	if (actionSelected == ActionManager.ACTION_LOOK) {
    		addAction(new FunctionalLook(element));
    		return;
    	}
    	FunctionalAction nextAction = new FunctionalNullAction(null);
    	switch( actionSelected ) {
    	case ActionManager.ACTION_EXAMINE:
        	cancelActions();
    		nextAction = new FunctionalExamine(null, element);
    		break;
    	case ActionManager.ACTION_GIVE:
    		if( element.canPerform( actionSelected ) ) {
    			if( element.isInInventory( ) ) {
    		    	cancelActions();
    				nextAction = new FunctionalGive(null, element);
    				game.getActionManager( ).setActionSelected( ActionManager.ACTION_GIVE_TO );
    			} else
    				speak( GameText.getTextGiveObjectNotInventory( ) );
    		} else
    			speak( GameText.getTextGiveNPC( ) );
    		break;
    	case ActionManager.ACTION_GIVE_TO:
    		if( element.canPerform( actionSelected ) ) {
    			if (getCurrentAction().getType() == ActionManager.ACTION_GIVE) {
    				nextAction = getCurrentAction();
    				popAction();
    				((FunctionalGive) nextAction).setAnotherElement(element);
    			}
    		} else {
				popAction();
    			speak( GameText.getTextGiveCannot( ) );
    		}
    		break;
    	case ActionManager.ACTION_GRAB:
        	cancelActions();
    		if( element.canPerform( actionSelected ) ) {
    			if( !element.isInInventory( ) ) {
    				nextAction = new FunctionalGrab(null, element);
    			} else
    				speak( GameText.getTextGrabObjectInventory( ) );
    		} else
    			speak( GameText.getTextGrabNPC( ) );
    		break;
    	case ActionManager.ACTION_TALK:
        	cancelActions();
    		if( element.canPerform( actionSelected ) ) {
    			nextAction = new FunctionalTalk(null, element);
    		} else
    			speak( GameText.getTextTalkObject( ) );
    		break;
    	case ActionManager.ACTION_USE:
    		if( element.canPerform( actionSelected ) ) {
    			if( element.canBeUsedAlone( ) ) {
    	        	cancelActions();
    				nextAction = new FunctionalUse(element);
    			}
    			else {
    	        	cancelActions();
    				nextAction = new FunctionalUseWith(null, element);
    				game.getActionManager( ).setActionSelected( ActionManager.ACTION_USE_WITH );
    			}
    		} else {
    			popAction();
    			speak( GameText.getTextUseNPC( ) );
    		}
    		break;
    	case ActionManager.ACTION_CUSTOM:
    		nextAction = new FunctionalCustom(element, Game.getInstance( ).getActionManager( ).getCustomActionName());
    		break;
    	case ActionManager.ACTION_CUSTOM_INTERACT:
    		if (getCurrentAction().getType() == Action.CUSTOM_INTERACT) {
    			nextAction = getCurrentAction();
    			popAction();
    			if (getCurrentAction() != null && element != null && getCurrentAction() instanceof FunctionalCustomInteract) {
    				((FunctionalCustomInteract) nextAction).setAnotherElement(element);
    			}
    		} else {
    			nextAction = new FunctionalCustomInteract(element, Game.getInstance().getActionManager().getCustomActionName());
    		}
    		break;
    	case ActionManager.ACTION_USE_WITH:
    		if( element.canPerform( actionSelected ) ) {
    			if (getCurrentAction().getType() == ActionManager.ACTION_USE_WITH) {
    				nextAction = getCurrentAction();
    				popAction();
    				((FunctionalUseWith) nextAction).setAnotherElement(element);
    			} 
    		} else {
    			popAction();
    			speak( GameText.getTextUseNPC( ) );
    		}
    		break;
    	}
    	

    	if (nextAction.isNeedsGoTo() &&  !this.isTransparent) {
    		FunctionalGoTo functionalGoTo = new FunctionalGoTo(null, (int)element.getX(), (int)element.getY(), this, element);
    		if (functionalGoTo.canGetTo()) {
    			addAction(nextAction);
    			functionalGoTo.setKeepDistance(nextAction.getKeepDistance());
    			addAction(functionalGoTo);
    		} else {
    			addAction(functionalGoTo);
    		}
    		return;
    	} else
    		addAction(nextAction);
    	return;

    }
    
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#draw(java.awt.Graphics2D)
     */
    public void draw( ) {
        if (!isTransparent) {
        	Image image = getCurrentAnimation().getImage();
            int realX = (int) (x - ( image.getWidth( null ) * scale / 2 ) - Game.getInstance( ).getFunctionalScene( ).getOffsetX( ));
            int realY = (int) (y - ( image.getHeight( null ) * scale )); 
            if (scale != 1) {
        		Image temp = image.getScaledInstance(Math.round(image.getWidth(null) * scale), Math.round(image.getHeight(null) * scale), Image.SCALE_SMOOTH);
        		
        		if (layer==Scene.PLAYER_WITHOUT_LAYER||layer==Scene.PLAYER_NO_ALLOWED)
        			GUI.getInstance().addPlayerToDraw(temp, realX, realY, Math.round(y),Math.round(y));
        		else 
        			GUI.getInstance().addElementToDraw(temp, realX, realY, layer, Math.round(y));
        		
            } else {
            	
	            if (layer==Scene.PLAYER_WITHOUT_LAYER||layer==Scene.PLAYER_NO_ALLOWED)
	            	GUI.getInstance( ).addPlayerToDraw( image, realX, realY, Math.round( y ), Math.round(y));
	            else 
	            	GUI.getInstance( ).addElementToDraw( image, realX, realY, layer, Math.round(y) );
            
            
        }
        }
        if (getCurrentAction().isStarted() && !getCurrentAction().isFinished())
        	getCurrentAction().drawAditionalElements();
        
        
    }
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.Renderable#update(long)
     */
    public void update( long elapsedTime ) {
    	if (getCurrentAction().isFinished()) {
    		this.popAction();
    	}
    	if (!getCurrentAction().isStarted()) {
    		getCurrentAction().start(this);
    	} else {
    		getCurrentAction().update(elapsedTime);
    	}
    	//TODO check if the animation must be pop because it repeated as indicated
    	
    	getCurrentAnimation( ).update( elapsedTime );
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
            case ActionManager.ACTION_CUSTOM:
                canPerform = true;
                break;

            case ActionManager.ACTION_GRAB:
            case ActionManager.ACTION_USE:
            case ActionManager.ACTION_GIVE:
            case ActionManager.ACTION_GOTO:
            case ActionManager.ACTION_USE_WITH:
            case ActionManager.ACTION_GIVE_TO:
            case ActionManager.ACTION_TALK:
            case ActionManager.ACTION_CUSTOM_INTERACT:
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
        	FunctionalSpeak functionalSpeak = new FunctionalSpeak(null, text);
        	addAction(functionalSpeak);
        }
    }
    
    public void speak( String text, String audioPath) {
        FunctionalSpeak functionalSpeak = new FunctionalSpeak(null, text, audioPath);
        addAction(functionalSpeak);
    }
    
    /**
     * TODO poner bien docu!!!
     * Funcion que va a leer del TTS!!!!!!
     */
    public void speakWithFreeTTS(String text, String voice){
        if (text!=null){
        	FunctionalSpeak functionalSpeak = new FunctionalSpeak(null, text);
        	functionalSpeak.setSpeakFreeTTS(text, voice);
        	addAction(functionalSpeak);
        }
        //TODO old code, left because of the "draw()" call, maybe the new code
        //   causes problems
//    	speak(text);
 //   	draw();
  //  	talkingAnimation.setSpeakFreeTTS(text, voice);  
    }
    
    public void dealocateTTS(){
    	if (getCurrentAction().getType() == ActionManager.ACTION_TALK) {
    		((FunctionalSpeak) getCurrentAction()).stopTTSTalking();
    	}
    }
        
    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.TalkingElement#stopTalking()
     */
    public void stopTalking( ) {
    	if (getCurrentAction().getType() == ActionManager.ACTION_TALK) {
    		getCurrentAction().stop();
    		this.cancelActions();
    	}
    }

    /*
     *  (non-Javadoc)
     * @see es.eucm.eadventure.engine.core.control.functionaldata.TalkingElement#isTalking()
     */
    public boolean isTalking( ) {
        return getCurrentAction().getType() == ActionManager.ACTION_TALK;
    }
    
    /**
     * Returns if the player is walking
     * @return true if the player is walking, false otherwise
     */
    public boolean isWalking( ) {
        return getCurrentAction().getType() == ActionManager.ACTION_GOTO;
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

	@Override
	public CustomAction getFirstValidCustomAction(String actionName) {
		//TODO Player should be modified to support custom actions
		/*
        for( Action action : player.getActions() ) {
            if( action.getType( ) == Action.CUSTOM && ((CustomAction) action).getID() == actionID ) {
                if( new FunctionalConditions(action.getConditions( ) ).allConditionsOk( ) ) {
                	return (CustomAction) action;
                } 
            }
        } */
        return null;
        
	}

	@Override
	public InfluenceArea getInfluenceArea() {
		return null;
	}

}
