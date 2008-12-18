package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.Action;
import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

public class FunctionalGoTo extends FunctionalAction {

	private int posX;
	
	private int posY;
	
	private int originalPosX;
	
	private int originalPosY;
	
	private float speedX;
	
	public FunctionalGoTo(Action action, int posX, int posY) {
		super(action);
		this.originalPosX = posX;
		this.originalPosY = posY;
        int[] finalDest = Game.getInstance( ).getFunctionalScene( ).checkPlayerAgainstBarriers( posX, posY );
        this.posX = finalDest[0];
        this.posY = finalDest[1];
		type = ActionManager.ACTION_GOTO;
		// TODO more initializations?
	}
	
	public FunctionalGoTo(Action action, int posX, int posY, int keepDistance) {
		this(action, posX, posY);
		this.keepDistance = keepDistance;
	}

	@Override
	public void start(FunctionalPlayer functionalPlayer) {
		this.functionalPlayer = functionalPlayer;
		finished = false;
		this.needsGoTo = false;
		
		// TODO always loads the default animation and walks with the defualt speed
        Resources resources = functionalPlayer.getResources( );
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        if( functionalPlayer.getX( ) < posX ) {
            Animation animation = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
            functionalPlayer.setAnimation(animation, -1);
            speedX = FunctionalPlayer.DEFAULT_SPEED;
        } else {
            Animation animation = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_WALK_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
            functionalPlayer.setAnimation(animation, -1);
            speedX = -FunctionalPlayer.DEFAULT_SPEED;
        }
    }

	@Override
	public void update(long elapsedTime) {
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
	}
	
	public boolean canGetTo() {
		return posX == originalPosX && posY == originalPosY;
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
