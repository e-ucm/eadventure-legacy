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
package es.eucm.eadventure.editor.control.tools.descriptions;

import es.eucm.eadventure.common.data.chapter.elements.Description;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DescriptionController;
import es.eucm.eadventure.editor.control.controllers.DescriptionsController;
import es.eucm.eadventure.editor.control.tools.Tool;


public class RemoveDescriptionTool extends Tool {

    private DescriptionsController descriptionsController;
    
    private DescriptionController deletedDescriptionController;
    
    private Description description;
    
    /*
     * Elements for UNDO REDO
     */
    private int lastSelectedDescription;
    
    
    public RemoveDescriptionTool(DescriptionsController descriptionsController){
    
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

        boolean elementDeleted = false;
        lastSelectedDescription = descriptionsController.getSelectedDescriptionNumber( );
        if (descriptionsController.getSelectedDescriptionNumber( ) > 0){
            deletedDescriptionController =  descriptionsController.removeSelectedDescription( );
            descriptionsController.setSelectedDescription( descriptionsController.getDescriptionCount( ) - 1 );
            elementDeleted = true;
        }
        
        
        
     // If it was the last one, show an error message
        else
            //TODO cambiar cadenas
            Controller.getInstance( ).showErrorDialog( TC.get( "Operation.DeleteResourcesTitle" ), TC.get( "Operation.DeleteResourcesErrorLastResources" ) );

        return elementDeleted;
        
    }

    @Override
    public boolean redoTool( ) {

        boolean redone = doTool( );
        if( redone )
            Controller.getInstance( ).updatePanel( );
        return redone;
    }

    @Override
    public boolean undoTool( ) {

        descriptionsController.addDescriptionController(  deletedDescriptionController, lastSelectedDescription);
        descriptionsController.addDescription( deletedDescriptionController.getDescriptionData( ), lastSelectedDescription );
        descriptionsController.setSelectedDescription( lastSelectedDescription  );
        Controller.getInstance( ).updatePanel( );
        return true;
    }


}
