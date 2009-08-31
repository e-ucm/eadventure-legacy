/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.engine.core.control.functionaldata.functionalactions;

import es.eucm.eadventure.common.data.chapter.elements.Player;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.engine.core.control.ActionManager;
import es.eucm.eadventure.engine.core.control.DebugLog;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.animations.Animation;
import es.eucm.eadventure.engine.core.control.animations.AnimationState;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.data.GameText;
import es.eucm.eadventure.engine.multimedia.MultimediaManager;

/**
 * The action to use an element
 * 
 * @author Eugenio Marchiori
 */
public class FunctionalUse extends FunctionalAction {

	/**
	 * The element to use
	 */
	private FunctionalElement element;
	
	/**
	 * The total elapsed time of the action
	 */
	private long totalTime;
	
	/**
	 * True if the element can be used
	 */
	private boolean canUse = false;
	
	/**
	 * Constructor with the element to use
	 * 
	 * @param element The element to be used
	 */
	public FunctionalUse(FunctionalElement element) {
		super(null);
		this.type = ActionManager.ACTION_USE;
		this.element = element;
        originalAction = element.getFirstValidAction(ActionManager.ACTION_USE);
		if (element.isInInventory() || originalAction == null) {
			this.needsGoTo = false;
		} else {
			this.needsGoTo = originalAction.isNeedsGoTo();
			this.keepDistance = originalAction.getKeepDistance();
		}
	}

	@Override
	public void drawAditionalElements() {
	}

	@Override
	public void setAnotherElement(FunctionalElement element) {
	}

	@Override
	public void start(FunctionalPlayer functionalPlayer) {
		this.functionalPlayer = functionalPlayer;
		this.needsGoTo = true;
		this.finished = false;
		totalTime = 0;
		
        Resources resources = functionalPlayer.getResources( );
        MultimediaManager multimedia = MultimediaManager.getInstance( );
        
        Animation[] animations = new Animation[4];
        animations[AnimationState.EAST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
		if (resources.getAssetPath(Player.RESOURCE_TYPE_USE_LEFT) != null && !resources.getAssetPath(Player.RESOURCE_TYPE_USE_LEFT).equals(Game.ASSET_EMPTY_ANIMATION))
			animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath(Player.RESOURCE_TYPE_USE_LEFT), false, MultimediaManager.IMAGE_PLAYER);
		else
			animations[AnimationState.WEST] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.NORTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), false, MultimediaManager.IMAGE_PLAYER );
        animations[AnimationState.SOUTH] = multimedia.loadAnimation( resources.getAssetPath( Player.RESOURCE_TYPE_USE_RIGHT ), true, MultimediaManager.IMAGE_PLAYER );
        
        
        if( functionalPlayer.getX( ) < element.getX() ) {
        	functionalPlayer.setDirection(AnimationState.EAST);
        } else {
        	functionalPlayer.setDirection(AnimationState.WEST);
        }
        functionalPlayer.setAnimation(animations, -1);

        
	}

	@Override
	public void stop() {
		finished = true;
	}

	@Override
	public void update(long elapsedTime) {
        totalTime += elapsedTime;
        if (!finished && !canUse) {
        	canUse = element.use();
        	if (!canUse) {
        		DebugLog.player("Can't use " + element.getElement().getId());
        		if (functionalPlayer.isAlwaysSynthesizer())
        			functionalPlayer.speakWithFreeTTS( GameText.getTextUseCannot(), functionalPlayer.getPlayerVoice());
        		else
        			functionalPlayer.speak( GameText.getTextUseCannot( ) );
                functionalPlayer.popAnimation();
                finished = true;       		
        	}
        } else if(!finished && totalTime > 1000 ) {
        	DebugLog.player("Used " + element.getElement().getId());
            finished = true;
            functionalPlayer.popAnimation();
        }
	}

}
