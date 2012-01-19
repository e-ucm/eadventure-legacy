/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.engine.core.control.functionaldata.functionaleffects;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.effects.AbstractEffect;
import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.common.data.chapter.effects.MacroReferenceEffect;
import es.eucm.eadventure.engine.core.control.Game;

public class FunctionalMacroReferenceEffect extends FunctionalEffect {

    private MacroReferenceEffect macroRefEffect;
    
    private boolean triggered;

    public FunctionalMacroReferenceEffect( MacroReferenceEffect effect ) {

        super( effect );
        macroRefEffect = effect;
        triggered = false;
    }

    @Override
    public boolean isInstantaneous( ) {

        return true;
    }

    @Override
    public boolean isStillRunning( ) {

        return false;
    }

    @Override
    public void triggerEffect( ) {

        if (!triggered) {
            // Get the macro
            Macro macro = Game.getInstance( ).getCurrentChapterData( ).getMacro( macroRefEffect.getTargetId( ) );
    
            // Build list of functional effects
            List<FunctionalEffect> fEffects = new ArrayList<FunctionalEffect>( );
            for( AbstractEffect effect : macro.getEffects( ) ) {
                fEffects.add( FunctionalEffect.buildFunctionalEffect( effect ) );
            }
    
            // Add functional effects to the stack-queue. The GameStateEffects will trigger them normally
            //Game.getInstance().addToTheStack( fEffects );
            Game.getInstance( ).storeEffectsInQueue( fEffects, false );
            triggered = true;
        }
    }

}
