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
package es.eucm.eadventure.editor.gui.elementpanels.book;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.gui.otherpanels.bookpanels.BookArrowPositionPreviewPanel;

/**
 * This panel contains the spinners and buttons for set the arrows position
 * 
 * @author Ángel S.
 * 
 */
public class ArrowsPositionPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private BookArrowPositionPreviewPanel bookPreview;

    private JSpinner xPreviousPageSpinner = new JSpinner( ),
            yPreviousPageSpinner = new JSpinner( ),
            xNextPageSpinner = new JSpinner( ),
            yNextPageSpinner = new JSpinner( );

    private final static int SPINNER_LEFT = 0,
            SPINNER_RIGHT = 1, SPINNER_X = 2,
            SPINNER_Y = 3;

    public ArrowsPositionPanel( BookArrowPositionPreviewPanel bPreview ) {

        bookPreview = bPreview;
        //this.setPreferredSize( new Dimension ( 300, 200 ) );
        xPreviousPageSpinner.setMinimumSize( new Dimension( 200, 20 ) );
        yPreviousPageSpinner.setMinimumSize( new Dimension( 200, 20 ) );
        xNextPageSpinner.setMinimumSize( new Dimension( 200, 20 ) );
        yNextPageSpinner.setMinimumSize( new Dimension( 200, 20 ) );

        this.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );

        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 1;

        c.gridy = 0;
        c.weighty = 0.01;
        this.add( getButtonPanel( ), c );

        c.gridy = 1;
        c.weighty = 1;

    }

    private JPanel getButtonPanel( ) {

        JPanel buttonPanel = new JPanel( new GridBagLayout( ) );

        JButton bDefaultPosition = new JButton( TC.get( "Arrows.DefaultPosition" ) );
        bDefaultPosition.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                bookPreview.setDefaultArrowsPosition( );
                updateSpinners( false );
                setAddTool( true );
                bookPreview.repaint( );
            }
        } );

        GridBagConstraints c = new GridBagConstraints( );
        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;

        buttonPanel.add( bDefaultPosition, c );

        // Previous page panel
        JPanel previousPagePanel = new JPanel( new GridBagLayout( ) );
        previousPagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), "Previous page" ) );
        GridBagConstraints c2 = new GridBagConstraints( );

        c2.gridx = 0;
        c2.gridy = 0;
        previousPagePanel.add( new JLabel( TC.get( "SPEP.XCoordinate" ) ), c2 );

        c2.gridx = 1;
        c2.gridy = 0;
        previousPagePanel.add( xPreviousPageSpinner, c2 );

        c2.gridx = 0;
        c2.gridy = 1;
        previousPagePanel.add( new JLabel( TC.get( "SPEP.YCoordinate" ) ), c2 );

        c2.gridx = 1;
        c2.gridy = 1;
        previousPagePanel.add( yPreviousPageSpinner, c2 );

        // Next page panel
        JPanel nextPagePanel = new JPanel( new GridBagLayout( ) );
        nextPagePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), "Next page" ) );
        GridBagConstraints c3 = new GridBagConstraints( );

        c3.gridx = 0;
        c3.gridy = 0;
        nextPagePanel.add( new JLabel( TC.get( "SPEP.XCoordinate" ) ), c3 );

        c3.gridx = 1;
        c3.gridy = 0;
        nextPagePanel.add( xNextPageSpinner, c3 );

        c3.gridx = 0;
        c3.gridy = 1;
        nextPagePanel.add( new JLabel( TC.get( "SPEP.YCoordinate" ) ), c3 );

        c3.gridx = 1;
        c3.gridy = 1;
        nextPagePanel.add( yNextPageSpinner, c3 );

        // Add panels to main panel
        c.gridx = 0;
        c.gridy = 1;
        buttonPanel.add( previousPagePanel, c );

        c.gridx = 0;
        c.gridy = 2;
        buttonPanel.add( nextPagePanel, c );

        updateSpinners( false );
        setAddTool( true );

        yPreviousPageSpinner.addChangeListener( new CoordinateChange( SPINNER_Y, SPINNER_LEFT ) );
        xNextPageSpinner.addChangeListener( new CoordinateChange( SPINNER_X, SPINNER_RIGHT ) );
        yNextPageSpinner.addChangeListener( new CoordinateChange( SPINNER_Y, SPINNER_RIGHT ) );
        xPreviousPageSpinner.addChangeListener( new CoordinateChange( SPINNER_X, SPINNER_LEFT ) );

        return buttonPanel;
    }

    public void updateSpinners( boolean addTool ) {

        setAddTool( addTool );
        xPreviousPageSpinner.setValue( bookPreview.getPreviousPagePosition( ).x );
        yPreviousPageSpinner.setValue( bookPreview.getPreviousPagePosition( ).y );
        xNextPageSpinner.setValue( bookPreview.getNextPagePosition( ).x );
        yNextPageSpinner.setValue( bookPreview.getNextPagePosition( ).y );
    }

    public void setAddTool( boolean addTool ) {

        ArrowsPositionPanel.addTool = addTool;
    }

    private static boolean addTool = false;

    public class CoordinateChange implements ChangeListener {

        private int coordinate;

        private int side;

        public CoordinateChange( int coordinate, int side ) {

            this.coordinate = coordinate;
            this.side = side;
        }

        public void stateChanged( ChangeEvent e ) {

            int value = (Integer) ( (JSpinner) e.getSource( ) ).getValue( );
            update( value );

        }

        private void update( int value ) {

            if( side == SPINNER_LEFT ) {
                Point p = bookPreview.getPreviousPagePosition( );
                Point oldP = (Point) p.clone( );
                if( coordinate == SPINNER_X ) {
                    p.x = value;
                }
                else if( coordinate == SPINNER_Y ) {
                    p.y = value;
                }
                if( addTool ) {
                    bookPreview.setPreviousPagePosition( (Point) p.clone( ), oldP );
                }
                else
                    bookPreview.setPreviousPagePosition( p.x, p.y );
            }
            else if( side == SPINNER_RIGHT ) {
                Point p = bookPreview.getNextPagePosition( );
                Point oldP = (Point) p.clone( );
                if( coordinate == SPINNER_X ) {
                    p.x = value;
                }
                else if( coordinate == SPINNER_Y ) {
                    p.y = value;
                }
                if( addTool ) {
                    bookPreview.setNextPagePosition( (Point) p.clone( ), oldP );
                }
                else
                    bookPreview.setNextPagePosition( p.x, p.y );
            }

            updateSpinners( false );
            setAddTool( true );
            bookPreview.repaint( );
        }

    }

}
