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

package es.eucm.eadventure.editor.control.tools.descriptions;

import es.eucm.eadventure.common.data.chapter.elements.Description;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DescriptionController;
import es.eucm.eadventure.editor.control.controllers.DescriptionsController;
import es.eucm.eadventure.editor.control.tools.Tool;


public class AddDescriptionTool extends Tool {
    
    private DescriptionsController descriptionsController;
    
    private DescriptionController descriptionController;
    
    private Description description;
    
    
    public AddDescriptionTool(DescriptionsController descriptionsController){
    
        this.descriptionsController = descriptionsController;
    
    }
    
    
    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return true;
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        description = new Description();
        descriptionsController.addDescription( description );
        descriptionController = new DescriptionController(description);
        descriptionsController.addDescriptionController( descriptionController );
        
        return true;
    }

    @Override
    public boolean redoTool( ) {

        descriptionsController.addDescription( description );
        descriptionsController.addDescriptionController( descriptionController );
        descriptionsController.setSelectedDescription( descriptionsController.getDescriptionCount( )-1 );
        Controller.getInstance( ).updatePanel( );
        return false;
    }

    @Override
    public boolean undoTool( ) {

        boolean undone = descriptionsController.removeDescription( description ) && descriptionsController.removeDescriptionController( descriptionController );
        if (undone){
            descriptionsController.setSelectedDescription( descriptionsController.getDescriptionCount( )-1);
            Controller.getInstance( ).updatePanel( );
            return true;
        }
        return false;
    }

}
