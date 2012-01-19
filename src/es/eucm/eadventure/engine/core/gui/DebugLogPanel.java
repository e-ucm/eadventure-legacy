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
package es.eucm.eadventure.engine.core.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.engine.core.control.DebugLog;

public class DebugLogPanel extends JPanel {

    /**
     * Default serial version UID
     */
    private static final long serialVersionUID = 1L;

    private DebugTable all;

    private DebugTable general;

    private DebugTable user;

    private DebugTable player;

    public DebugLogPanel( ) {

        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        this.setSize( screenSize.width, screenSize.height - GUI.WINDOW_HEIGHT - 25 );
        this.setLocation( 0, GUI.WINDOW_HEIGHT );
        this.setLayout( new BorderLayout( ) );

        JTabbedPane pane = new JTabbedPane( );

        all = new DebugTable( );
        pane.addTab( TC.get( "DebugFrameLog.All" ), null, all.getScrollPane( ), TC.get( "DebugFrameLog.AllTip" ) );

        user = new DebugTable( );
        pane.addTab( TC.get( "DebugFrameLog.User" ), null, user.getScrollPane( ), TC.get( "DebugFrameLog.UserTip" ) );

        player = new DebugTable( );
        pane.addTab( TC.get( "DebugFrameLog.Player" ), null, player.getScrollPane( ), TC.get( "DebugFrameLog.PlayerTip" ) );

        general = new DebugTable( );
        pane.addTab( TC.get( "DebugFrameLog.General" ), null, general.getScrollPane( ), TC.get( "DebugFrameLog.General" ) );

        this.add( pane, BorderLayout.CENTER );

        DebugLog.getInstance( ).setDebugFrameLog( this );
        this.setVisible( true );
    }

    public void addLine( int type, String time, String text ) {

        try {
            all.addLine( time, text );
            if( type == DebugLog.PLAYER )
                player.addLine( time, text );
            if( type == DebugLog.GENERAL )
                general.addLine( time, text );
            if( type == DebugLog.USER )
                user.addLine( time, text );
        }
        catch( Exception e ) {

        }
    }

    public void close( ) {

        this.setVisible( false );
        DebugLog.getInstance( ).clear( );
    }

    private class DebugTable {

        private JTable table;

        private DefaultTableModel dtm;

        private JScrollPane scrollPane;

        public DebugTable( ) {

            table = new JTable( );
            dtm = new DefaultTableModel( );
            dtm.setColumnCount( 2 );
            String[] ids = { TC.get( "DebugFrameLog.Time" ), TC.get( "DebugFrameLog.Entry" ) };
            dtm.setColumnIdentifiers( ids );
            table.setModel( dtm );
            table.getColumnModel( ).getColumn( 0 ).setMaxWidth( 80 );
            scrollPane = new JScrollPane( table );
            //#JAVA6#
            table.setFillsViewportHeight( true );
            //@JAVA6@
        }

        public JScrollPane getScrollPane( ) {

            return scrollPane;
        }

        public void addLine( String time, String text ) {

            String[] line = new String[ 2 ];
            line[0] = time;
            line[1] = text;
            dtm.addRow( line );
            dtm.fireTableDataChanged( );
            SwingUtilities.invokeLater( new Runnable( ) {

                public void run( ) {

                    Rectangle r = table.getCellRect( table.getRowCount( ) - 1, 0, true );
                    table.scrollRectToVisible( r );
                }
            } );
        }
    }

}
