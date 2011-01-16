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

import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreasListDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.gui.elementpanels.scene.ActiveAreasTable;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imageelements.ImageElement;

public class DeleteActiveAreaTool extends Tool {

    private ActiveAreasListDataControl dataControl;

    private IrregularAreaEditionPanel iaep;

    private ActiveAreasTable table;

    private ActiveAreaDataControl element;

    private int position;

    private ChapterDataControl chapterDataControl;

    private Chapter chapter;

    public DeleteActiveAreaTool( ActiveAreasListDataControl dataControl2, IrregularAreaEditionPanel iaep, ActiveAreasTable table2 ) {

        this.dataControl = dataControl2;
        this.table = table2;
        this.iaep = iaep;

        chapterDataControl = Controller.getInstance( ).getSelectedChapterDataControl( );
        try {
            chapter = (Chapter) ( ( (Chapter) chapterDataControl.getContent( ) ).clone( ) );
        }
        catch( Exception e ) {
            ReportDialog.GenerateErrorReport( e, true, "Could not clone chapter" );
        }

    }

    @Override
    public boolean canRedo( ) {

        return true;
    }

    @Override
    public boolean canUndo( ) {

        return ( chapter != null );
    }

    @Override
    public boolean combine( Tool other ) {

        return false;
    }

    @Override
    public boolean doTool( ) {

        position = table.getSelectedRow( );
        element = dataControl.getActiveAreas( ).get( position );
        if( dataControl.deleteElement( element, true ) ) {
            iaep.getScenePreviewEditionPanel( ).removeElement( element );
            iaep.setRectangular( null );
            iaep.getScenePreviewEditionPanel( ).setSelectedElement( (ImageElement) null );
            ( (AbstractTableModel) table.getModel( ) ).fireTableDataChanged( );
            return true;
        }
        return false;

    }

    @Override
    public boolean redoTool( ) {

        Controller.getInstance( ).replaceSelectedChapter( (Chapter) chapterDataControl.getContent( ) );
        Controller.getInstance( ).reloadData( );
        return true;
    }

    @Override
    public boolean undoTool( ) {

        Controller.getInstance( ).replaceSelectedChapter( chapter );
        Controller.getInstance( ).reloadData( );
        return true;
    }
}
