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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.HTMLEditController;

public class HTMLEditDialog extends JDialog implements WindowListener {

    //private BookPage bookPage;

    /**
     * Default generated serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private HTMLEditController htmlEditController;

    public HTMLEditController getHtmlEditController( ) {

        return htmlEditController;
    }

    private HTMLEditPanel kafenio;

    public HTMLEditDialog( String filename, JFrame frame ) {

        super( frame, true );
        this.setTitle( TextConstants.getText( "HTMLEditor.Title", filename ) );

        //this.bookPage = bookPage;
        htmlEditController = new HTMLEditController( );

        // Push the dialog into the stack
        Controller.getInstance( ).pushWindow( this );

        boolean newFile = false;
        if( filename == null ) {
            filename = AssetsController.TempFileGenerator.generateTempFileAbsolutePath( "html" );
            File file = new File( filename );
            try {
                file.createNewFile( );
            }
            catch( IOException e ) {
                ReportDialog.GenerateErrorReport( e, true, "UNKNOWERROR" );
            }
            newFile = true;
        }

        htmlEditController.setFilename( filename );
        htmlEditController.setNewFile( newFile );

        setLayout( new BorderLayout( ) );

        kafenio = HTMLEditPanel.getInstance( new File( filename ) );
        kafenio.setHtmlEditController( htmlEditController );

        add( kafenio, BorderLayout.CENTER );
        setJMenuBar( kafenio.getJMenuBar( ) );

        addWindowListener( this );
        setSize( 800, 600 );
        setResizable( true );
        //setAlwaysOnTop(true);
        setModalityType( ModalityType.APPLICATION_MODAL );
        setModal( true );

        setVisible( true );

        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );

    }

    public void windowActivated( WindowEvent arg0 ) {

    }

    public void windowClosed( WindowEvent arg0 ) {

    }

    public void windowClosing( WindowEvent arg0 ) {

        int option = JOptionPane.showConfirmDialog( this, TextConstants.getText( "HTMLEditor.QuitMessage" ), TextConstants.getText( "HTMLEditor.QuitMessageTitle" ), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
        if( option == 0 ) {
            kafenio.saveAll( );
        }
        Controller.getInstance( ).popWindow( );
    }

    public void windowDeactivated( WindowEvent arg0 ) {

    }

    public void windowDeiconified( WindowEvent arg0 ) {

    }

    public void windowIconified( WindowEvent arg0 ) {

    }

    public void windowOpened( WindowEvent arg0 ) {

    }

}
