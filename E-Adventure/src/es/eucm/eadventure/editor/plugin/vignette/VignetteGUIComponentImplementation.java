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

package es.eucm.eadventure.editor.plugin.vignette;

import java.awt.GridBagConstraints;

import javax.swing.JPanel;

/**
 * PluginGUIComponentsFactor returns an object of this class to provide users with access to Vignette
 * @author Javier Torrente
 *
 */
public class VignetteGUIComponentImplementation implements VignetteGUIComponent{

    private VignetteButton exportToVignetteButton;
    private VignetteButton importFromVignetteButton;

    @Override
    public void buildAndAdd( VignetteConversationWrapper w, JPanel container, GridBagConstraints c ) {
        c.gridx++;
        exportToVignetteButton = new VignetteButton(w, true);
        container.add( exportToVignetteButton, c );
        c.gridx++;
        importFromVignetteButton = new VignetteButton(w, false);
        container.add( importFromVignetteButton, c );
    }

    @Override
    public void updateButtons( ) {
        this.importFromVignetteButton.updateButton( );
        this.exportToVignetteButton.updateButton( );        
    }

}
