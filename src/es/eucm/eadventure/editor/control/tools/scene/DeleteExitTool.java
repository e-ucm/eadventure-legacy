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
package es.eucm.eadventure.editor.control.tools.scene;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.Exit;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ExitDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ExitsListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;

public class DeleteExitTool extends Tool {

    private ExitsListDataControl dataControl;

    private IrregularAreaEditionPanel iaep;

    private JTable table;

    private ExitDataControl element;

    private int position;

    public DeleteExitTool( ExitsListDataControl dataControl2, JTable table2, IrregularAreaEditionPanel iaep ) {

        this.dataControl = dataControl2;
        this.table = table2;
        this.iaep = iaep;
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

        int position = table.getSelectedRow( );
        element = dataControl.getExits( ).get( position );
        iaep.getScenePreviewEditionPanel( ).removeElement( element );
        iaep.setRectangular( null );
        iaep.getScenePreviewEditionPanel( ).setSelectedElement( (ImageElement) null );
        dataControl.deleteElement( element, true );
        ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
        return true;
    }

    @Override
    public boolean redoTool( ) {

        iaep.getScenePreviewEditionPanel( ).removeElement( element );
        dataControl.deleteElement( element, true );
        Controller.getInstance( ).updatePanel( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        dataControl.getExitsList( ).add( position, (Exit) element.getContent( ) );
        dataControl.getExits( ).add( position, element );
        iaep.getScenePreviewEditionPanel( ).addExit( element );
        Controller.getInstance( ).updatePanel( );
        return true;
    }
}
