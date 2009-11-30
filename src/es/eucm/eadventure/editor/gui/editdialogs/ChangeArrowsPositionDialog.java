/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.book.BookDataControl;
import es.eucm.eadventure.editor.gui.otherpanels.bookpanels.BookArrowPositionPreview;

/**
 * This class represents the dialog which is used to define
 * the arrows position in the books.
 * 
 * @author Ángel S.
 *
 */
public class ChangeArrowsPositionDialog extends ToolManagableDialog {

    private static final long serialVersionUID = 1L;
    
    private BookArrowPositionPreview bookPreview;
    
    private JSpinner xPreviousPageSpinner = new JSpinner( ), 
                     yPreviousPageSpinner = new JSpinner( ), 
                     xNextPageSpinner = new JSpinner( ), 
                     yNextPageSpinner = new JSpinner( );
    
    private final static int SPINNER_LEFT = 0, SPINNER_RIGHT = 1, SPINNER_X = 2, SPINNER_Y = 3;

    public ChangeArrowsPositionDialog( Window window, BookDataControl dControl ) {

        super( window, TC.get( "Arrows.PositionDialog" ) );
        bookPreview = new BookArrowPositionPreview( dControl, this );
        
        JPanel p = new JPanel( new GridBagLayout( ));
        GridBagConstraints c = new GridBagConstraints( );
        
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.weightx = 1;
        
        
        c.gridy = 0;
        c.weighty = 0.01;
        p.add( getTopPanel( ), c);
        
        c.gridy = 1;
        c.weighty = 1;
        p.add(  bookPreview, c );


        this.setContentPane( p );
        
        //this.setSize( 830, 630 );
        this.pack( );
        this.setResizable( false );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
        this.setVisible( true );
    }
    
    private JPanel getTopPanel( ){
        JPanel bottomPanel = new JPanel( new GridBagLayout( ) );
        
        JButton bDefaultPosition = new JButton ( TC.get( "Arrows.DefaultPosition" ) );
        bDefaultPosition.addActionListener( new ActionListener( ){
            public void actionPerformed( ActionEvent e ) {
                bookPreview.setDefaultArrowsPosition( );
                updateSpinners( );
                bookPreview.repaint( );
            }
        });
        
        GridBagConstraints c = new GridBagConstraints( );
        c.gridheight = 1;
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 0;
        
        bottomPanel.add(  bDefaultPosition, c );
        // TODO Labels strings
        c.gridx = 1;
        c.gridy = 0;
        bottomPanel.add( new JLabel( TC.get( "SPEP.XCoordinate" ) ), c );
        
        xPreviousPageSpinner.addChangeListener( new CoordinateChange( SPINNER_X, SPINNER_LEFT ) );
        c.gridx = 2;
        c.gridy = 0;
        bottomPanel.add( xPreviousPageSpinner, c );
        
        c.gridx = 3;
        c.gridy = 0;
        bottomPanel.add( new JLabel(TC.get( "SPEP.YCoordinate" )  ), c );
        
        yPreviousPageSpinner.addChangeListener( new CoordinateChange( SPINNER_Y, SPINNER_LEFT ) );
        
        c.gridx = 4;
        c.gridy = 0;
        bottomPanel.add( yPreviousPageSpinner, c );
        
        c.gridx = 1;
        c.gridy = 1;
        bottomPanel.add(  new JLabel( TC.get( "SPEP.XCoordinate" )), c );
        
        xNextPageSpinner.addChangeListener( new CoordinateChange( SPINNER_X, SPINNER_RIGHT ) );
        
        c.gridx = 2;
        c.gridy = 1;
        bottomPanel.add( xNextPageSpinner, c );
        
        c.gridx = 3;
        c.gridy = 1;
        bottomPanel.add(  new JLabel( TC.get( "SPEP.YCoordinate" )  ), c );
        
        yNextPageSpinner.addChangeListener( new CoordinateChange( SPINNER_Y, SPINNER_RIGHT ) );
        
        c.gridx = 4;
        c.gridy = 1;
        bottomPanel.add( yNextPageSpinner, c );
        
        updateSpinners( );
        
        return bottomPanel;
    }
    
    public void updateSpinners( ){
        xPreviousPageSpinner.setValue( bookPreview.getPreviousPagePosition( ).x );
        yPreviousPageSpinner.setValue( bookPreview.getPreviousPagePosition( ).y );
        xNextPageSpinner.setValue( bookPreview.getNextPagePosition( ).x );
        yNextPageSpinner.setValue( bookPreview.getNextPagePosition( ).y );
    }
    
    private class CoordinateChange implements ChangeListener {
        
        private int coordinate;
        private int side;
        
        public CoordinateChange( int coordinate, int side ){
            this.coordinate = coordinate;
            this.side = side;
        }

        public void stateChanged( ChangeEvent e ) {
            int value = (Integer) ((JSpinner) e.getSource( )).getValue( );
            update( value );
            
        }
        
        private void update( int value ){
            if ( side == SPINNER_LEFT ){
                Point p = bookPreview.getPreviousPagePosition( );
                if ( coordinate == SPINNER_X ){
                    bookPreview.setPreviousPagePosition( value, p.y );
                    updateSpinners( );
                }
                else if ( coordinate == SPINNER_Y ){
                    bookPreview.setPreviousPagePosition( p.x, value );
                    updateSpinners( );
                }
            }
            else if ( side == SPINNER_RIGHT ){
                Point p = bookPreview.getNextPagePosition( );
                if ( coordinate == SPINNER_X ){
                    bookPreview.setNextPagePosition( value, p.y );
                    updateSpinners( );
                }
                else if ( coordinate == SPINNER_Y ){
                    bookPreview.setNextPagePosition( p.x, value );
                    updateSpinners( );
                }
            }
            bookPreview.repaint( );
        }
        
    }

}
