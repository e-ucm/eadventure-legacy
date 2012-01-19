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
package es.eucm.eadventure.editor.control.tools.general.commontext;

import es.eucm.eadventure.common.data.HasDescriptionSound;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;


public class DeleteDescriptionSoundTool extends Tool {

    private HasDescriptionSound hds;
    
    private int type; 
    
    private String oldPath;
    
    public DeleteDescriptionSoundTool(HasDescriptionSound hds, int type){
        this.hds = hds;
        this.type = type;
        
        switch (type){
            case HasDescriptionSound.NAME_PATH:
                oldPath = hds.getNameSoundPath( );
                break;
                
            case HasDescriptionSound.DESCRIPTION_PATH:
                oldPath = hds.getDescriptionSoundPath( );
                break;
                
            case HasDescriptionSound.DETAILED_DESCRIPTION_PATH:
                oldPath = hds.getDetailedDescriptionSoundPath( );
                break;
                
        }
    }
    
    
   

    @Override
    public boolean doTool( ) {

        switch (type){
            case HasDescriptionSound.NAME_PATH:
                hds.setNameSoundPath( null );
                break;
                
            case HasDescriptionSound.DESCRIPTION_PATH:
                hds.setDescriptionSoundPath( null );
                break;
                
            case HasDescriptionSound.DETAILED_DESCRIPTION_PATH:
                hds.setDetailedDescriptionSoundPath( null );
                break;
                
        }
        
        Controller.getInstance( ).updatePanel( );
        
        return true;
    }

    @Override
    public boolean redoTool( ) {

        switch (type){
            case HasDescriptionSound.NAME_PATH:
                hds.setNameSoundPath( null );
                break;
                
            case HasDescriptionSound.DESCRIPTION_PATH:
                hds.setDescriptionSoundPath( null );
                break;
                
            case HasDescriptionSound.DETAILED_DESCRIPTION_PATH:
                hds.setDetailedDescriptionSoundPath( null );
                break;
                
        }
        
        Controller.getInstance( ).updatePanel( );
        
        
        return true;
    }

    @Override
    public boolean undoTool( ) {

        switch (type){
            case HasDescriptionSound.NAME_PATH:
                hds.setNameSoundPath( oldPath );
                break;
                
            case HasDescriptionSound.DESCRIPTION_PATH:
                hds.setDescriptionSoundPath( oldPath );
                break;
                
            case HasDescriptionSound.DETAILED_DESCRIPTION_PATH:
                hds.setDetailedDescriptionSoundPath( oldPath );
                break;
                
        }
        
        Controller.getInstance( ).updatePanel( );
        
        return true;
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

        return true;
    }

}
