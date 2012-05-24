/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.auxiliar.JPositionedDialog;
import es.eucm.eadventure.editor.gui.otherpanels.bookpanels.BookPagePreviewPanel;

public class StyledBookDialog extends JPositionedDialog {

    private static final long serialVersionUID = 1L; 

    public StyledBookDialog( BookDataControl book ) {

        super( );
        if( book.getType( ) == Book.TYPE_PAGES ) {
            
            BookPagePreviewPanel previewPanel = new BookPagePreviewPanel( book, true, 0 );
            JPanel contentPane = new JPanel( null );
            contentPane.setPreferredSize( new Dimension( 800, 600 ) );
            previewPanel.setPreferredSize( new Dimension( 800, 600 ) );
            previewPanel.setBounds( 0, 0, 800, 600 );
            contentPane.add( previewPanel );
            this.setContentPane( previewPanel );
            //this.setSize( 800, 600 );
            this.pack();
            setResizable( false );
            
            Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
            setLocation( ( screenSize.width - 800 ) / 2, ( screenSize.height - 600 ) / 2 );
            setEnabled( true );
            setVisible( true );
            setFocusable( true );
            requestFocus( );

        }
    }

}
