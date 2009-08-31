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

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.auxiliar.ReleaseFolders;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;

/**
 * This panel shows the info about an concrete effect
 * 
 */
public class EffectInfoPanel extends JPanel {

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

        String folder = "help/";
        if( Controller.getInstance( ).getLanguage( ) == ReleaseFolders.LANGUAGE_SPANISH )
            folder += "es_ES/";
        else if( Controller.getInstance( ).getLanguage( ) == ReleaseFolders.LANGUAGE_ENGLISH )
            folder += "en_EN/";
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

        add( new JLabel( TextConstants.getText( "HelpDialog.FileNotFound" ) + " " + path ) );
    }

}
