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

import java.util.List;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.common.data.chapter.NextScene;
import es.eucm.eadventure.common.data.chapter.effects.Effects;
import es.eucm.eadventure.engine.core.control.DebugLog;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalElement;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalPlayer;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffect;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalEffects;
import es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects.FunctionalNextSceneEffect;

/**
 * The action to exit a scene
 * 
 * @author Eugenio Marchiori
 *
 */
public class FunctionalExit extends FunctionalAction {

	/**
	 * The exit through which the player leaves the scene
	 */
	private Exit exit;
	
	/**
	 * Default constructor, with the exit to use.
	 * 
	 * @param exit The exit through which the player leaves the scene.
	 */
	public FunctionalExit(Exit exit) {
		super(null);
		this.exit = exit;
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
		if( exit != null ) {
            if( new FunctionalConditions ( exit.getConditions( ) ).allConditionsOk( ) ) {
           		FunctionalEffects effects =  new FunctionalEffects(exit.getEffects( ));
           		effects.addEffect( new FunctionalNextSceneEffect( exit ));
           		FunctionalEffects.storeAllEffects(effects);
            } else if (exit.isHasNotEffects()) {
            	FunctionalEffects.storeAllEffects(exit.getNotEffects());
            }
        }
		finished = true;
		
		DebugLog.player("Exit scene to: " + (exit != null ? exit.getNextSceneId() : "none") );
	}

	@Override
	public void stop() {
		finished = true;
	}

	@Override
	public void update(long elapsedTime) {
	}

}
