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
package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;
import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.bookpanels.BookPagePreviewPanel;

public class StyledBookDialog extends JDialog {

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
