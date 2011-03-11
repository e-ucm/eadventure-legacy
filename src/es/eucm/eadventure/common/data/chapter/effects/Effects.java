/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.common.data.chapter.effects;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.auxiliar.AllElementsWithAssets;

/**
 * A list of effects that can be triggered by an unique player's action during
 * the game.
 */
public class Effects implements Cloneable {

    /**
     * List of effects to be triggered
     */
    private List<AbstractEffect> effects;

    /**
     * Creates a new list of Effects.
     */
    public Effects( ) {

        effects = new ArrayList<AbstractEffect>( );
    }

    /**
     * Returns whether the effects block is empty or not.
     * 
     * @return True if the block has no effects, false otherwise
     */
    public boolean isEmpty( ) {

        return effects.isEmpty( );
    }

    /**
     * Clear the list of effects.
     */
    public void clear( ) {

        effects.clear( );
    }

    /**
     * Adds a new effect to the list.
     * 
     * @param effect
     *            the effect to be added
     */
    public void add( AbstractEffect effect ) {

        effects.add( effect );
        
        //Check if the effect has resources, to add it in the AllAssetsPaths
        if (effect.getType( ) == Effect.PLAY_ANIMATION || effect.getType( ) == Effect.PLAY_SOUND ){
            AllElementsWithAssets.addAsset( effect );
        } else if (effect.getType( ) == Effect.RANDOM_EFFECT ){
            if (((RandomEffect)effect).getPositiveEffect( ).getType( ) == Effect.PLAY_ANIMATION){
                AllElementsWithAssets.addAsset( ((RandomEffect)effect).getPositiveEffect( ) );
            }
            if (((RandomEffect)effect).getNegativeEffect( ).getType( ) == Effect.PLAY_SOUND){
                AllElementsWithAssets.addAsset( ((RandomEffect)effect).getNegativeEffect( ) );
            }
        }
        
    }

    /**
     * Returns the contained list of effects
     * 
     * @return List of effects
     */
    public List<AbstractEffect> getEffects( ) {

        return effects;
    }

    /**
     * Checks if there is any cancel action effect in the list
     */
    public boolean hasCancelAction( ) {

        boolean hasCancelAction = false;
        for( Effect effect : effects ) {
            if( effect.getType( ) == Effect.CANCEL_ACTION ) {
                hasCancelAction = true;
                break;
            }
        }
        return hasCancelAction;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        Effects e = (Effects) super.clone( );
        if( effects != null ) {
            e.effects = new ArrayList<AbstractEffect>( );
            for( Effect ef : effects )
                e.effects.add( (AbstractEffect) ef.clone( ) );
        }
        return e;
    }

}
