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
package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class BookAppPanel extends JPanel {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Controller of the book.
     */
    private BookDataControl bookDataControl;

    private LooksPanel bookLooks;

    /**
     * Constructor.
     * 
     * @param bookDataControl
     *            Book controller
     */
    public BookAppPanel( BookDataControl bookDataControl ) {

        // Set the controller
        this.bookDataControl = bookDataControl;

        // Create the list of resources
        String[] resourcesArray = new String[ bookDataControl.getResourcesCount( ) ];
        for( int i = 0; i < bookDataControl.getResourcesCount( ); i++ )
            resourcesArray[i] = TextConstants.getText( "ResourcesList.ResourcesBlockNumber" ) + ( i + 1 );

        // Set the layout
        setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );

        c.gridy = 0;
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 3;

        this.bookLooks = new BookLooksPanel( this.bookDataControl );
        this.add( bookLooks, c );
    }

    private class BookLooksPanel extends LooksPanel {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * Preview image panel.
         */
        private ImagePanel imagePanel;

        public BookLooksPanel( DataControlWithResources control ) {

            super( control );
        }

        @Override
        protected void createPreview( ) {

            String bookImagePath = bookDataControl.getPreviewImage( );
            JPanel previewPanel = new JPanel( );
            previewPanel.setLayout( new BorderLayout( ) );
            imagePanel = new ImagePanel( bookImagePath );
            previewPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Book.Preview" ) ) );
            previewPanel.add( imagePanel, BorderLayout.CENTER );
            lookPanel.add( previewPanel, cLook );
            // TODO Parche, arreglar
            lookPanel.setPreferredSize( new Dimension( 0, 90 ) );
        }

        @Override
        public void updatePreview( ) {

            imagePanel.loadImage( bookDataControl.getPreviewImage( ) );
            imagePanel.repaint( );
            if( getParent( ) != null && getParent( ).getParent( ) != null )
                getParent( ).getParent( ).repaint( );
        }

        @Override
        public void updateResources( ) {

            super.updateResources( );
            if( getParent( ) != null && getParent( ).getParent( ) != null )
                getParent( ).getParent( ).repaint( );
        }

    }

}
