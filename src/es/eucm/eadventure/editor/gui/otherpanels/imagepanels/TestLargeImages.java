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
package es.eucm.eadventure.editor.gui.otherpanels.imagepanels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JFileChooser;

import es.eucm.eadventure.common.gui.TextConstants;

public class TestLargeImages extends JFrame {

    private ImagePanel imagePanel;

    public TestLargeImages( ) {

        super( "Prueba" );

        imagePanel = new ImagePanel( );
        this.getContentPane( ).setLayout( new BorderLayout( ) );
        this.getContentPane( ).add( imagePanel, BorderLayout.CENTER );

        JPanel buttonsPanel = new JPanel( );
        JButton load = new JButton( "Load" );
        load.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                JFileChooser chooser = new JFileChooser( );
                chooser.showOpenDialog( null );
                if( chooser.getSelectedFile( ) != null )
                    imagePanel.loadImage( chooser.getSelectedFile( ).getAbsolutePath( ) );
            }

        } );
        buttonsPanel.add( load );
        this.addWindowListener( new WindowListener( ) {

            public void windowActivated( WindowEvent e ) {

                // TODO Auto-generated method stub

            }

            public void windowClosed( WindowEvent e ) {

                // TODO Auto-generated method stub

            }

            public void windowClosing( WindowEvent e ) {

                setVisible( false );
                dispose( );
            }

            public void windowDeactivated( WindowEvent e ) {

                // TODO Auto-generated method stub

            }

            public void windowDeiconified( WindowEvent e ) {

                // TODO Auto-generated method stub

            }

            public void windowIconified( WindowEvent e ) {

                // TODO Auto-generated method stub

            }

            public void windowOpened( WindowEvent e ) {

                // TODO Auto-generated method stub

            }

        } );
        this.getContentPane( ).add( buttonsPanel, BorderLayout.SOUTH );
        this.setSize( 800, 600 );
        this.setVisible( true );

    }

    public static void main( String[] args ) {

        TextConstants.loadStrings( "english.xml" );
        new TestLargeImages( );
    }

}
