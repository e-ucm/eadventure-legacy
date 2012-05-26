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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Dialog.ModalityType;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.HTMLEditController;
import es.eucm.eadventure.editor.gui.auxiliar.JPositionedDialog;

public class HTMLEditDialog extends JPositionedDialog implements WindowListener {

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
        this.setTitle( TC.get( "HTMLEditor.Title", filename ) );

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
        //setResizable( true );
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

        int option = JOptionPane.showConfirmDialog( this, TC.get( "HTMLEditor.QuitMessage" ), TC.get( "HTMLEditor.QuitMessageTitle" ), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE );
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
