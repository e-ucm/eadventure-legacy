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
package es.eucm.eadventure.editor.gui.auxiliar;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ChapterToolManager;
import es.eucm.eadventure.editor.control.controllers.general.ChapterListDataControl;

public class ToolSystemDebugger extends JDialog {

    private ChapterListDataControl dataControl;

    private List<JList> chapterLists;

    private JPanel mainPanel;

    public ToolSystemDebugger( ChapterListDataControl dataControl ) {

        super( Controller.getInstance( ).peekWindow( ), "", Dialog.ModalityType.MODELESS );
        this.dataControl = dataControl;
        update( );
        setVisible( true );
    }

    public void update( ) {

        if( mainPanel != null )
            this.remove( mainPanel );
        mainPanel = new JPanel( );
        mainPanel.setLayout( new GridLayout( 1, dataControl.getChaptersCount( ) ) );
        String[] chapterTitles = dataControl.getChapterTitles( );
        chapterLists = new ArrayList<JList>( );
        for( int i = 0; i < dataControl.getChapterToolManagers( ).size( ); i++ ) {
            ChapterToolManager ctManager = dataControl.getChapterToolManagers( ).get( i );
            JPanel listPanel = new JPanel( );
            listPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), chapterTitles[i] ) );
            String[] listValues = new String[ ctManager.getGlobalToolManager( ).getRedoList( ).size( ) + ctManager.getGlobalToolManager( ).getUndoList( ).size( ) + 1 ];
            for( int j = 0; j < ctManager.getGlobalToolManager( ).getRedoList( ).size( ); j++ ) {
                listValues[j] = ctManager.getGlobalToolManager( ).getRedoList( ).get( j ).getToolName( );
            }
            listValues[ctManager.getGlobalToolManager( ).getRedoList( ).size( )] = "[[CURRENT]]]";
            for( int j = 0; j < ctManager.getGlobalToolManager( ).getUndoList( ).size( ); j++ ) {
                listValues[ctManager.getGlobalToolManager( ).getRedoList( ).size( ) + ctManager.getGlobalToolManager( ).getUndoList( ).size( ) - j] = ctManager.getGlobalToolManager( ).getUndoList( ).get( j ).getToolName( );
            }

            JList newList = new JList( listValues );
            listPanel.setLayout( new BorderLayout( ) );
            listPanel.add( newList, BorderLayout.CENTER );
            mainPanel.add( listPanel );
        }
        this.add( mainPanel );
        pack( );
        this.repaint( );
    }

}
