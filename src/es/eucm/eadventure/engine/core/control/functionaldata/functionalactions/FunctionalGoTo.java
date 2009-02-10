package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.DebugLog;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalTrajectory;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class FunctionalGoTo extends FunctionalAction {

	private int posX;
	
	private int posY;
	
	private int originalPosX;
	
	private int originalPosY;
	
	private float speedX;
	
	private boolean hasAnimation = false;
	
	private FunctionalTrajectory trajectory;
	
	private Resources resources;
	
    private MultimediaManager multimedia;
	
    private boolean trajectoryUpdated;
    
	public FunctionalGoTo(Action action, int posX, int posY) {
		super(action);
		this.originalPosX = posX;
		this.originalPosY = posY;
        int[] finalDest = Game.getInstance( ).getFunctionalScene( ).checkPlayerAgainstBarriers( posX, posY );
        this.trajectory = Game.getInstance().getFunctionalScene().getTrajectory();
        this.trajectory.setDestinationElement(null);
        this.posX = finalDest[0];
        this.posY = finalDest[1];
		type = ActionManager.ACTION_GOTO;
		trajectoryUpdated = false;
		keepDistance = 0;
	}
	
	public FunctionalGoTo(Action action, int posX, int posY, int keepDistance) {
		this(action, posX, posY);
		this.keepDistance = keepDistance;
	}

	public FunctionalGoTo(Action action, int x, int y, FunctionalPlayer functionalPlayer, FunctionalElement element) {
		this(action, x, y);
		if (trajectory.hasTrajectory()) {
	        trajectory.setDestinationElement(element);
	    	trajectory.updatePathToNearestPoint(functionalPlayer.getX(), functionalPlayer.getY(), originalPosX, originalPosY);
	    	trajectoryUpdated = true;
		}
	}

	@Override
	public void start(FunctionalPlayer functionalPlayer) {
		this.functionalPlayer = functionalPlayer;
		finished = false;
		this.needsGoTo = false;
		
		resources = functionalPlayer.getResources( );
        multimedia = MultimediaManager.getInstance( );
        if (!trajectory.hasTrajectory()) {
	        if( functionalPlayer.getX( ) < posX ) {
	        	functionalPlayer.setDirection(AnimationState.EAST);
	            speedX = FunctionalPlayer.DEFAULT_SPEED;
	        } else {
	        	functionalPlayer.setDirection(AnimationState.WEST);
	            speedX = -FunctionalPlayer.DEFAULT_SPEED;
	        }
            Animation[] animations = new Animation[4];
	        animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
            animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
	        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_UP ), false, MultimediaManager.IMAGE_PLAYER );
            animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_DOWN ), false, MultimediaManager.IMAGE_PLAYER );
            functionalPlayer.setAnimation(animations, -1);

        } else if (!trajectoryUpdated){
        	trajectory.updatePathToNearestPoint(functionalPlayer.getX(), functionalPlayer.getY(), posX, posY);
        }
        
        DebugLog.player("Player moves: hasTrajectory=" + trajectory.hasTrajectory() + " destination=" + posX + "," + posY);
    }

	@Override
	public void update(long elapsedTime) {
		if (!trajectory.hasTrajectory() && !finished) {
	       if( ( speedX > 0 
	    		   && functionalPlayer.getX( ) < posX - keepDistance) 
	    		   || ( speedX <= 0 
	    				   && functionalPlayer.getX( ) >= posX + keepDistance) ) {
	    	   float oldX = functionalPlayer.getX();
	    	   float newX = oldX + speedX*elapsedTime/1000;
	    	   functionalPlayer.setX( newX );
	       } else {
	    	   finished = true;
	    	   functionalPlayer.popAnimation();
	       }
		} else if (!finished){
			float oldSpeedX = trajectory.getSpeedX();
			float oldSpeedY = trajectory.getSpeedY();
			trajectory.updateSpeeds(elapsedTime, functionalPlayer.getX(), functionalPlayer.getY(), FunctionalPlayer.DEFAULT_SPEED);
			if (trajectory.getSpeedX() != 0 || trajectory.getSpeedY() != 0) {
				setAnimation(oldSpeedX, oldSpeedY, trajectory.getSpeedX(), trajectory.getSpeedY());
				functionalPlayer.setScale(trajectory.getScale());
				float oldX = functionalPlayer.getX();
		    	float newX = oldX + trajectory.getSpeedX()*elapsedTime/1000;
		    	functionalPlayer.setX( newX );
	
				float oldY = functionalPlayer.getY();
		    	float newY = oldY + trajectory.getSpeedY()*elapsedTime/1000;
		    	functionalPlayer.setY( newY );
			} else {
				finished = true;
				functionalPlayer.popAnimation();
			}
		}
	}
	
	private void setAnimation(float oldSpeedX, float oldSpeedY, float newSpeedX, float newSpeedY) {
        Animation[] animations = new Animation[4];
        animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_UP ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_DOWN ), false, MultimediaManager.IMAGE_PLAYER );

		if (!hasAnimation) {
			hasAnimation = true;
			if (muchGreater(newSpeedY, newSpeedX)) {
				if (newSpeedY > 0) {
					functionalPlayer.setDirection(AnimationState.SOUTH);
				} else {
					functionalPlayer.setDirection(AnimationState.NORTH);
				}
			} else {
				if (newSpeedX > 0) {
					functionalPlayer.setDirection(AnimationState.EAST);
				} else {
					functionalPlayer.setDirection(AnimationState.WEST);
				}
			}
            functionalPlayer.setAnimation(animations, -1);
		} else {
			if (muchGreater(newSpeedY, newSpeedX) && !muchGreater(oldSpeedY, oldSpeedX)) {
				functionalPlayer.popAnimation();
				if (newSpeedY > 0) {
		            functionalPlayer.setDirection(AnimationState.SOUTH);
				} else {
		            functionalPlayer.setDirection(AnimationState.NORTH);
				}
	            functionalPlayer.setAnimation(animations, -1);
			} else if (!muchGreater(newSpeedY, newSpeedX) && muchGreater(oldSpeedY, oldSpeedX)) {
				functionalPlayer.popAnimation();
				if (newSpeedX > 0) {
		            functionalPlayer.setDirection(AnimationState.EAST);
				} else {
		            functionalPlayer.setDirection(AnimationState.WEST);
				}
	            functionalPlayer.setAnimation(animations, -1);
			} else {
				if (oldSpeedX > 0 && newSpeedX < 0) {
		            functionalPlayer.setDirection(AnimationState.WEST);
		            functionalPlayer.popAnimation();
		            functionalPlayer.setAnimation(animations, -1);
				} else if (oldSpeedX < 0 && newSpeedX > 0) {
		            functionalPlayer.setDirection(AnimationState.EAST);
		            functionalPlayer.popAnimation();
		            functionalPlayer.setAnimation(animations, -1);
				}
			}
		}
	}
	
	private boolean muchGreater(float a, float b) {
		return (Math.abs(a) > Math.abs(b));
	}

	public boolean canGetTo() {
		if (!trajectory.hasTrajectory())
			return posX == originalPosX && posY == originalPosY;
		else {
			return trajectory.canGetTo();
		}
	}

	@Override
	public void drawAditionalElements() {
	}

	@Override
	public void stop() {
		finished = true;
	}

	@Override
	public void setAnotherElement(FunctionalElement element) {
	}

	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}
}
