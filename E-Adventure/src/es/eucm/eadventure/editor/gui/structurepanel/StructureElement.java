/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
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
