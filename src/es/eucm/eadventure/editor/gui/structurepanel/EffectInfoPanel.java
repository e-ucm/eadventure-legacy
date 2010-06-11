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
package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;

/**
 * This panel shows the info about an concrete effect
 * 
 */
public class EffectInfoPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -5172698031996946042L;
    
    /**
     * Pane to show HTML formatted text
     */
    private JEditorPane editorPane;

    public EffectInfoPanel( ) {

        editorPane = new JEditorPane( );
        this.setLayout( new BorderLayout( ) );
        add( new JScrollPane( editorPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER ), BorderLayout.CENTER );

    }

    public void setHTMLText( String helpPath ) {

        //this.removeAll();

        String folder = "help/" + Controller.getInstance( ).getLanguage( ) + "/";
        File file = new File( folder + helpPath );
        if( file.exists( ) ) {
            try {
                editorPane.setPage( file.toURI( ).toURL( ) );
                editorPane.setEditable( false );
                editorPane.setHighlighter( null );
            }
            catch( MalformedURLException e1 ) {
                writeFileNotFound( folder + helpPath );
            }
            catch( IOException e1 ) {
                writeFileNotFound( folder + helpPath );
            }
        }
        else {
            writeFileNotFound( folder + helpPath );
        }

    }

    public void writeFileNotFound( String path ) {

        add( new JLabel( TC.get( "HelpDialog.FileNotFound" ) + " " + path ) );
    }

}
