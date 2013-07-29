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
