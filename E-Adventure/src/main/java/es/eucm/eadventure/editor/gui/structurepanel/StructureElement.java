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
package es.eucm.eadventure.editor.gui.structurepanel;

import javax.swing.Icon;
import javax.swing.JComponent;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.common.data.HasId;

public class StructureElement {

    protected Icon icon;

    protected String name;

    private DataControl dataControl;

    private StructureListElement parent;

    public StructureElement( DataControl dataControl, StructureListElement parent ) {

        this.dataControl = dataControl;
        this.parent = parent;
        name = null;
    }

    public StructureElement( String name, DataControl dataControl, StructureListElement parent ) {

        this( dataControl, parent );
        this.name = name;
    }

    public String getName( ) {

        if( name != null )
            return name;
        return ( (HasId) dataControl.getContent( ) ).getId( );
    }

    public Icon getIcon( ) {

        return icon;
    }

    public JComponent getEditPanel( ) {

        return EditPanelFactory.getEditPanel( dataControl );
    }

    public void setName( String name ) {

        this.name = name;
    }

    public void setIcon( Icon icon ) {

        this.icon = icon;
    }

    public boolean isCanRename( ) {

        return dataControl.canBeRenamed( );
    }

    public boolean canBeRemoved( ) {

        return dataControl.canBeDeleted( );
    }

    public DataControl getDataControl( ) {

        return dataControl;
    }

    public boolean delete( boolean askConfirmation ) {

        if( getDataControl( ).canBeDeleted( ) && parent.getDataControl( ).deleteElement( getDataControl( ), askConfirmation ) ) {
            Controller.getInstance( ).updateVarFlagSummary( );
            return true;
        }
        return false;
    }

    public void setJustCreated( boolean justCreated ) {

        dataControl.setJustCreated( justCreated );
    }

    public boolean isJustCreated( ) {

        return dataControl.isJustCreated( );
    }

    public boolean canBeDuplicated( ) {

        return dataControl.canBeDuplicated( );
    }
}
