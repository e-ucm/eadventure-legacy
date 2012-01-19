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
package es.eucm.eadventure.editor.control.controllers.imageedition;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.imageedition.filter.TransparentColorFilter;
import es.eucm.eadventure.editor.gui.displaydialogs.GraphicDialog;
import es.eucm.eadventure.editor.gui.displaydialogs.GraphicDialog.ImagePanel;

public class ImageToolBar extends JPanel {

    private static final long serialVersionUID = -87717263659816496L;

    private TransparentColorFilter filter;

    private EditImageController controller;

    private ArrayList<JComponent> tools;

    private GraphicDialog.ImagePanel panel;

    public ImageToolBar( boolean admitTransparency, TransparentColorFilter filter, EditImageController cont, ImagePanel p ) {

        this.panel = p;
        this.filter = filter;
        this.controller = cont;
        this.setLayout( new GridBagLayout( ) );

        tools = new ArrayList<JComponent>( );

        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;

        JToggleButton editButton = new JToggleButton( TC.get( "ImageEdition.SelectColorTransparent" ) );
        editButton.setEnabled( admitTransparency );
        this.add( editButton, c );

        if( admitTransparency ) {
            editButton.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent e ) {

                    boolean selected = ( (JToggleButton) ( e.getSource( ) ) ).isSelected( );
                    
                    ImageToolBar.this.filter.setActive( selected );

                    for( JComponent c : tools ) {
                        c.setEnabled( selected );
                    }

                    if( selected ) {
                        panel.setCursor( Cursor.getPredefinedCursor( Cursor.HAND_CURSOR ) );
                    }
                    else
                        panel.setCursor( Cursor.getPredefinedCursor( Cursor.DEFAULT_CURSOR ) );

                }

            } );

            c.gridx++;
            this.add( new JSeparator( JSeparator.VERTICAL ), c );

            JButton undoButton = new JButton( new ImageIcon( "img/icons/undo.png" ) );
            undoButton.setToolTipText( TC.get( "Tools.Undo" ) );
            undoButton.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent e ) {

                    controller.undo( );

                }

            } );
            tools.add( undoButton );

            c.gridx++;
            this.add( undoButton, c );

            JButton redoButton = new JButton( new ImageIcon( "img/icons/redo.png" ) );
            redoButton.setToolTipText( TC.get( "Tools.Redo" ) );
            redoButton.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent e ) {

                    controller.redo( );

                }

            } );

            c.gridx++;
            this.add( redoButton, c );
            tools.add( redoButton );

            c.gridx++;
            this.add( new JSeparator( JSeparator.VERTICAL ), c );

            JCheckBox contiguous = new JCheckBox( TC.get( "ImageEdition.Contiguous" ) );
            contiguous.setSelected( filter.isContiguous( ) );
            contiguous.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent arg ) {

                    ImageToolBar.this.filter.setContiguous( !ImageToolBar.this.filter.isContiguous( ) );
                }

            } );
            c.gridx++;
            this.add( contiguous, c );
            tools.add( contiguous );

            c.gridx++;
            this.add( new JSeparator( JSeparator.VERTICAL ), c );

            c.gridx++;
            this.add( new JLabel( TC.get( "ImageEdition.Threshold" ) ), c );

            JSpinner s = new JSpinner( new SpinnerNumberModel( filter.getThreshold( ), 10, 255, 1 ) );
            s.addChangeListener( new ChangeListener( ) {

                public void stateChanged( ChangeEvent arg0 ) {

                    ImageToolBar.this.filter.setThreshold( (Integer) ( (JSpinner) arg0.getSource( ) ).getValue( ) );

                }

            } );

            c.gridx++;
            this.add( s, c );
            tools.add( s );

            editButton.setSelected( false );

            for( JComponent com : tools ) {
                com.setEnabled( false );
            }
        }
        else {
            c.gridx++;
            this.add( new JLabel( TC.get( "ImageEdition.NoTransparencyAllowed" ) ), c );
        }

    }

}
