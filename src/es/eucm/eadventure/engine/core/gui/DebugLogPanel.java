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

import es.eucm.eadventure.common.gui.TextConstants;
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
        pane.addTab( TextConstants.getText( "DebugFrameLog.All" ), null, all.getScrollPane( ), TextConstants.getText( "DebugFrameLog.AllTip" ) );

        user = new DebugTable( );
        pane.addTab( TextConstants.getText( "DebugFrameLog.User" ), null, user.getScrollPane( ), TextConstants.getText( "DebugFrameLog.UserTip" ) );

        player = new DebugTable( );
        pane.addTab( TextConstants.getText( "DebugFrameLog.Player" ), null, player.getScrollPane( ), TextConstants.getText( "DebugFrameLog.PlayerTip" ) );

        general = new DebugTable( );
        pane.addTab( TextConstants.getText( "DebugFrameLog.General" ), null, general.getScrollPane( ), TextConstants.getText( "DebugFrameLog.General" ) );

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
            String[] ids = { TextConstants.getText( "DebugFrameLog.Time" ), TextConstants.getText( "DebugFrameLog.Entry" ) };
            dtm.setColumnIdentifiers( ids );
            table.setModel( dtm );
            table.getColumnModel( ).getColumn( 0 ).setMaxWidth( 80 );
            scrollPane = new JScrollPane( table );
            table.setFillsViewportHeight( true );
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
