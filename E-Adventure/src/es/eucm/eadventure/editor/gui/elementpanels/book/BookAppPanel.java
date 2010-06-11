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
package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.elementpanels.general.ResourcesPanel;
import es.eucm.eadventure.editor.gui.otherpanels.bookpanels.BookArrowPositionPreviewPanel;

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
            resourcesArray[i] = TC.get( "ResourcesList.ResourcesBlockNumber" ) + ( i + 1 );

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
        private BookArrowPositionPreviewPanel imagePanel;

        public BookLooksPanel( DataControlWithResources control ) {

            super( control );

        }

        @Override
        protected void createPreview( ) {

            JPanel previewPanel = new JPanel( );
            previewPanel.setLayout( new BorderLayout( ) );
            GridBagConstraints c = new GridBagConstraints( );
            c.fill = GridBagConstraints.BOTH;

            imagePanel = new BookArrowPositionPreviewPanel( bookDataControl );
            ArrowsPositionPanel aPanel = new ArrowsPositionPanel( imagePanel );
            imagePanel.setArrowsPositionPanel( aPanel );

            previewPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TC.get( "Book.Preview" ) ) );
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 1;
            previewPanel.add( imagePanel, BorderLayout.CENTER );

            c.gridx = 1;
            previewPanel.add( aPanel, BorderLayout.EAST );
            lookPanel.add( previewPanel, cLook );

            // TODO Parche, arreglar
            //lookPanel.setPreferredSize( new Dimension( 0, 90 ) );
        }

        @Override
        public void updatePreview( ) {

            imagePanel.loadImages( bookDataControl );
            imagePanel.repaint( );
            if( getParent( ) != null && getParent( ).getParent( ) != null )
                getParent( ).getParent( ).repaint( );
        }

        @Override
        public void updateResources( ) {

            super.updateResources( );
            reorganizeResourcesPanel( resourcesPanel );

            if( getParent( ) != null && getParent( ).getParent( ) != null )
                getParent( ).getParent( ).repaint( );
        }

        /**
         * By default, ResourcesPanel has its components ordered in one column.
         * With this method, we remove all the components form ResourcePanel and
         * we put them again just like we want.
         * 
         */
        private void reorganizeResourcesPanel( ResourcesPanel r ) {

            Component components[] = r.getComponents( );
            r.removeAll( );
            GridBagConstraints c = new GridBagConstraints( );
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets( 2, 2, 2, 2 );
            c.weightx = 1;
            c.weighty = 0;
            c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 2;
            c.gridheight = 1;

            r.add( components[0], c );

            int k = 1;
            c.gridwidth = 1;
            c.weightx = 0.5;
            for( int i = 1; i < 3; i++ )
                for( int j = 0; j < 2; j++ ) {
                    c.gridx = j;
                    c.gridy = i;
                    r.add( components[k], c );
                    components[k].repaint( );
                    k++;
                }

        }
    }
}
