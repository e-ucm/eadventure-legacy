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
package es.eucm.eadventure.editor.control.controllers.macro;

import java.util.List;

import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.controllers.EffectsController;
import es.eucm.eadventure.editor.control.controllers.Searchable;
import es.eucm.eadventure.editor.control.tools.general.commontext.ChangeDocumentationTool;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class MacroDataControl extends DataControl {

    private EffectsController controller;

    private Macro macro;

    public MacroDataControl( Macro conditions ) {

        macro = conditions;
        controller = new EffectsController( macro );
    }

    public void setDocumentation( String doc ) {

        Controller.getInstance( ).addTool( new ChangeDocumentationTool( macro, doc ) );
    }

    public String getDocumentation( ) {

        return macro.getDocumentation( );
    }

    public String getId( ) {

        return macro.getId( );
    }

    /**
     * @return the controller
     */
    public EffectsController getController( ) {

        return controller;
    }

    @Override
    public boolean addElement( int type, String id ) {

        return false;
    }

    @Override
    public boolean canAddElement( int type ) {

        return false;
    }

    @Override
    public boolean canBeDeleted( ) {

        // Check if no references are made to this global state
        int references = Controller.getInstance( ).countIdentifierReferences( getId( ) );
        return ( references == 0 );
    }

    @Override
    public boolean canBeDuplicated( ) {

        return true;
    }

    @Override
    public boolean canBeMoved( ) {

        return true;
    }

    @Override
    public boolean canBeRenamed( ) {

        return true;
    }

    @Override
    public int countAssetReferences( String assetPath ) {

        return EffectsController.countAssetReferences( assetPath, macro );
    }

    @Override
    public int countIdentifierReferences( String id ) {

        return EffectsController.countIdentifierReferences( id, macro );
    }

    @Override
    public void deleteAssetReferences( String assetPath ) {

        EffectsController.deleteAssetReferences( assetPath, macro );
    }

    @Override
    public boolean deleteElement( DataControl dataControl, boolean askConfirmation ) {

        return false;
    }

    @Override
    public void deleteIdentifierReferences( String id ) {

        EffectsController.deleteIdentifierReferences( id, macro );
    }

    @Override
    public int[] getAddableElements( ) {

        return new int[] {};
    }

    @Override
    public void getAssetReferences( List<String> assetPaths, List<Integer> assetTypes ) {

        EffectsController.getAssetReferences( assetPaths, assetTypes, macro );
    }

    @Override
    public Object getContent( ) {

        return macro;
    }

    @Override
    public boolean isValid( String currentPath, List<String> incidences ) {

        return EffectsController.isValid( currentPath, incidences, macro );
    }

    @Override
    public boolean moveElementDown( DataControl dataControl ) {

        return false;
    }

    @Override
    public boolean moveElementUp( DataControl dataControl ) {

        return false;
    }

    @Override
    public String renameElement( String name ) {

        boolean elementRenamed = false;
        String oldItemId = getId( );
        String references = String.valueOf( Controller.getInstance( ).countIdentifierReferences( oldItemId ) );

        // Ask for confirmation
        if( name != null || Controller.getInstance( ).showStrictConfirmDialog( TC.get( "Operation.RenameMacroTitle" ), TC.get( "Operation.RenameElementWarning", new String[] { oldItemId, references } ) ) ) {

            // Show a dialog asking for the new item id
            String newItemId = name;
            if( name == null )
                newItemId = Controller.getInstance( ).showInputDialog( TC.get( "Operation.RenameMacroTitle" ), TC.get( "Operation.RenameMacroMessage" ), oldItemId );

            // If some value was typed and the identifiers are different
            if( newItemId != null && !newItemId.equals( oldItemId ) && Controller.getInstance( ).isElementIdValid( newItemId ) ) {
                macro.setId( newItemId );
                Controller.getInstance( ).replaceIdentifierReferences( oldItemId, newItemId );
                Controller.getInstance( ).getIdentifierSummary( ).deleteMacroId( oldItemId );
                Controller.getInstance( ).getIdentifierSummary( ).addMacroId( newItemId );
                //Controller.getInstance().dataModified( );
                elementRenamed = true;
            }
        }

        if( elementRenamed )
            return oldItemId;
        return null;
    }

    @Override
    public void replaceIdentifierReferences( String oldId, String newId ) {

        EffectsController.replaceIdentifierReferences( oldId, newId, macro );
    }

    @Override
    public void updateVarFlagSummary( VarFlagSummary varFlagSummary ) {

        EffectsController.updateVarFlagSummary( varFlagSummary, macro );
    }

    @Override
    public void recursiveSearch( ) {

        check( this.getDocumentation( ), TC.get( "Search.Documentation" ) );
        check( this.getId( ), "ID" );

        for( int i = 0; i < this.getController( ).getEffectCount( ); i++ ) {
            check( this.getController( ).getEffectInfo( i ), TC.get( "Search.Effect" ) );
        }
    }

    @Override
    public List<Searchable> getPathToDataControl( Searchable dataControl ) {

        return null;
    }

}
