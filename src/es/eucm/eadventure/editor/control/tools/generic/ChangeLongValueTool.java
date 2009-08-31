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
package es.eucm.eadventure.editor.control.tools.generic;

import java.lang.reflect.Method;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Generic tool that uses introspection to change an integer value
 * 
 * @author Javier
 * 
 */
public class ChangeLongValueTool extends Tool {

    protected Method get;

    protected Method set;

    protected String getName;

    protected String setName;

    protected Long oldValue;

    protected Long newValue;

    protected Object data;

    protected boolean updateTree;

    protected boolean updatePanel;

    /**
     * Default constructor. Will update panel but not tree
     * 
     * @param data
     *            The object which data is to be modified
     * @param newValue
     *            The new Value (Long)
     * @param getMethodName
     *            The name of the get method. Must follow this pattern: public
     *            Long getMethodName()
     * @param setMethodName
     *            The name of the set method. Must follow this pattern: public *
     *            setMethodName( Long )
     */
    public ChangeLongValueTool( Object data, Long newValue, String getMethodName, String setMethodName ) {

        this( data, newValue, getMethodName, setMethodName, false, true );
    }

    public ChangeLongValueTool( Object data, Long newValue, String getMethodName, String setMethodName, boolean updateTree, boolean updatePanel ) {

        this.data = data;
        this.newValue = newValue;
        this.updatePanel = updatePanel;
        this.updateTree = updateTree;
        try {
            set = data.getClass( ).getMethod( setMethodName, Long.class );
            get = data.getClass( ).getMethod( getMethodName );
            this.getName = getMethodName;
            this.setName = setMethodName;
            if( get.getReturnType( ) != Long.class ) {
                get = set = null;
                getName = setName = null;
                ReportDialog.GenerateErrorReport( new Exception( "Get method must return Long value" ), false, TextConstants.getText( "Error.Title" ) );
            }
        }
        catch( SecurityException e ) {
            get = set = null;
            getName = setName = null;
            ReportDialog.GenerateErrorReport( e, false, TextConstants.getText( "Error.Title" ) );
        }
        catch( NoSuchMethodException e ) {
            get = set = null;
            getName = setName = null;
            ReportDialog.GenerateErrorReport( e, false, TextConstants.getText( "Error.Title" ) );
        }

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

        boolean done = false;
        if( get != null && set != null ) {
            // Get the old value
            try {
                oldValue = (Long) get.invoke( data );
                if( newValue != null && oldValue == null || newValue == null && oldValue != null || ( newValue != null && oldValue != null && !oldValue.equals( newValue ) ) ) {
                    set.invoke( data, newValue );
                    done = true;
                }
            }
            catch( Exception e ) {
                ReportDialog.GenerateErrorReport( e, false, TextConstants.getText( "Error.Title" ) );
            }

        }
        return done;

    }

    @Override
    public boolean redoTool( ) {

        boolean done = false;
        try {
            set.invoke( data, newValue );
            if( updateTree )
                Controller.getInstance( ).updateStructure( );
            if( updatePanel )
                Controller.getInstance( ).updatePanel( );
            done = true;
        }
        catch( Exception e ) {
            ReportDialog.GenerateErrorReport( e, false, TextConstants.getText( "Error.Title" ) );
        }
        return done;
    }

    @Override
    public boolean undoTool( ) {

        boolean done = false;
        try {
            set.invoke( data, oldValue );
            if( updateTree )
                Controller.getInstance( ).updateStructure( );
            if( updatePanel )
                Controller.getInstance( ).updatePanel( );
            done = true;
        }
        catch( Exception e ) {
            ReportDialog.GenerateErrorReport( e, false, TextConstants.getText( "Error.Title" ) );
        }
        return done;
    }

}
