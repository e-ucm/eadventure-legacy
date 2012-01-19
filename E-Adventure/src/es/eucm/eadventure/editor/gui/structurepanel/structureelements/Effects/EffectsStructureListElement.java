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
package es.eucm.eadventure.editor.gui.structurepanel.structureelements.Effects;

import es.eucm.eadventure.editor.gui.structurepanel.EffectsStructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.EffectsStructurePanel;
import es.eucm.eadventure.editor.gui.structurepanel.StructureElement;
import es.eucm.eadventure.editor.gui.structurepanel.StructureListElement;

public abstract class EffectsStructureListElement extends StructureListElement {

    protected String[] groupEffects;

    /**
     * The path with the .html file with related help
     */
    protected String path;

    public EffectsStructureListElement( String name ) {

        super( name, null );
        icon = EffectsStructurePanel.getEffectIcon( name, EffectsStructurePanel.ICON_SIZE_MEDIUM );
    }

    @Override
    public StructureElement getChild( int i ) {

        return new EffectsStructureElement( this, groupEffects[i] );
    }

    @Override
    public int getChildCount( ) {

        return groupEffects.length;
    }

    public String getPath( ) {

        return path;
    }

}
