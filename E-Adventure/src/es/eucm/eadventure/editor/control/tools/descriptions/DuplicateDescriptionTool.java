/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.control.tools.descriptions;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.elements.Description;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DescriptionController;
import es.eucm.eadventure.editor.control.controllers.DescriptionsController;
import es.eucm.eadventure.editor.control.tools.Tool;


public class DuplicateDescriptionTool extends Tool {

 private DescriptionsController descriptionsController;
    
    private DescriptionController descriptionController;
    
    private Description description;
    
    
    public DuplicateDescriptionTool(DescriptionsController descriptionsController){
    
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

        try {
            description = (Description)descriptionsController.getSelectedDescription( ).clone( );
            descriptionsController.addDescription( description );
            descriptionController = new DescriptionController(description);
            descriptionsController.addDescriptionController( descriptionController );
            descriptionsController.setSelectedDescription( descriptionsController.getDescriptionCount( )-1);
            return true;
        }
        catch( CloneNotSupportedException e ) {
            ReportDialog.GenerateErrorReport( e, true, "Could not clone descriptions" );
            return false;
        }
       
        
        
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
