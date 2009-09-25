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
package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.conversation.ConversationDataControl;
import es.eucm.eadventure.editor.control.controllers.conversation.GraphConversationDataControl;
import es.eucm.eadventure.editor.gui.elementpanels.general.tables.ConditionsCellRendererEditor;

public class LinesTable extends JTable {

    private static final long serialVersionUID = -6962666312669657936L;

    private ConversationDataControl conversationDataControl;

    public LinesTable( ConversationDataControl conversationDataControl, LinesPanel linesPanel ) {

        super( );
        this.conversationDataControl = conversationDataControl;

        setModel( new NodeTableModel( null ) );
        setAutoCreateColumnsFromModel( false );
        getColumnModel( ).getColumn( 0 ).setMaxWidth( 90 );

        getColumnModel( ).getColumn( 1 ).setCellEditor( new TextLineCellRendererEditor( linesPanel ) );
        getColumnModel( ).getColumn( 1 ).setCellRenderer( new TextLineCellRendererEditor( linesPanel ) );

        getColumnModel( ).getColumn( 2 ).setMaxWidth( 90 );
        getColumnModel( ).getColumn( 2 ).setMinWidth( 90 );
        getColumnModel( ).getColumn( 2 ).setWidth( 90 );
        getColumnModel( ).getColumn( 2 ).setCellEditor( new AudioCellRendererEditor( ) );
        getColumnModel( ).getColumn( 2 ).setCellRenderer( new AudioCellRendererEditor( ) );

        getColumnModel( ).getColumn( 3 ).setMaxWidth( 90 );
        getColumnModel( ).getColumn( 3 ).setMinWidth( 90 );
        getColumnModel( ).getColumn( 3 ).setWidth( 90 );
        getColumnModel( ).getColumn( 3 ).setCellEditor( new SynthesizeCellRendererEditor( ) );
        getColumnModel( ).getColumn( 3 ).setCellRenderer( new SynthesizeCellRendererEditor( ) );

        getColumnModel( ).getColumn( 4 ).setMaxWidth( 40 );
        getColumnModel( ).getColumn( 4 ).setMinWidth( 40 );
        getColumnModel( ).getColumn( 4 ).setWidth( 120 );
        getColumnModel( ).getColumn( 4 ).setCellRenderer( new ConditionsCellRendererEditor( ) );
        getColumnModel( ).getColumn( 4 ).setCellEditor( new ConditionsCellRendererEditor( ) );

        List<String> charactersList = new ArrayList<String>( );
        charactersList.add( TC.get( "ConversationLine.PlayerName" ) );
        String[] charactersArray = Controller.getInstance( ).getIdentifierSummary( ).getNPCIds( );
        for( String npc : charactersArray )
            charactersList.add( npc );
        charactersArray = charactersList.toArray( new String[] {} );
        JComboBox charactersComboBox = new JComboBox( charactersArray );
        getColumnModel( ).getColumn( 0 ).setCellEditor( new DefaultCellEditor( charactersComboBox ) );

        setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        setCellSelectionEnabled( false );
        setColumnSelectionAllowed( false );
        setRowSelectionAllowed( true );

        setTableHeader( null );
        setIntercellSpacing( new Dimension( 1, 1 ) );

        setRowHeight( 20 );
        this.getSelectionModel( ).addListSelectionListener( new ListSelectionListener( ) {

            public void valueChanged( ListSelectionEvent arg0 ) {

                setRowHeight( 20 );
                if( getSelectedRow( ) != -1 )
                    setRowHeight( getSelectedRow( ), 48 );
            }
        } );

        putClientProperty( "terminateEditOnFocusLost", Boolean.TRUE );

    }

    public void newSelectedNode( ConversationNodeView selectedNode ) {

        setModel( new NodeTableModel( selectedNode ) );
        if( isEditing( ) )
            getCellEditor( ).cancelCellEditing( );
    }

    /**
     * Private class containing the model for the line table
     */
    private class NodeTableModel extends AbstractTableModel {

        /**
         * Required
         */
        private static final long serialVersionUID = 1L;

        /**
         * Link to the representated conversational node
         */
        private ConversationNodeView node;

        /**
         * Constructor
         * 
         * @param node
         *            Node which lines will be in the table
         */
        public NodeTableModel( ConversationNodeView node ) {

            this.node = node;
        }

        public int getRowCount( ) {

            int rowCount = 0;

            if( node != null )
                rowCount = node.getLineCount( );

            return rowCount;
        }

        public int getColumnCount( ) {

            return 5;
        }

        @Override
        public Class<?> getColumnClass( int c ) {

            return getValueAt( 0, c ).getClass( );
        }

        @Override
        public boolean isCellEditable( int rowIndex, int columnIndex ) {

            boolean isEditable = false;
            ;

            if( node.getType( ) == ConversationNodeView.DIALOGUE )
                isEditable = true;

            else if( node.getType( ) == ConversationNodeView.OPTION )
                isEditable = columnIndex != 0;

            return isEditable && rowIndex == getSelectedRow( );
        }

        @Override
        public void setValueAt( Object value, int rowIndex, int columnIndex ) {

            if( !value.toString( ).trim( ).equals( "" ) ) {
                if( columnIndex == 0 )
                    if( value.toString( ).equals( TC.get( "ConversationLine.PlayerName" ) ) )
                        conversationDataControl.setNodeLineName( node, rowIndex, ConversationLine.PLAYER );
                    else
                        conversationDataControl.setNodeLineName( node, rowIndex, value.toString( ) );
                if( columnIndex == 1 )
                    conversationDataControl.setNodeLineText( node, rowIndex, value.toString( ) );
                fireTableCellUpdated( rowIndex, columnIndex );
            }
        }

        public Object getValueAt( int rowIndex, int columnIndex ) {

            Object value = null;
            if( node != null ) {
                switch( columnIndex ) {
                    case 0:
                        if( node.isPlayerLine( rowIndex ) )
                            value = TC.get( "ConversationLine.PlayerName" );
                        else
                            value = node.getLineName( rowIndex );
                        break;
                    case 1:
                        value = node;//.getLineText( rowIndex );
                        break;
                    case 2:
                        value = node;
                        break;
                    case 3:
                        value = node;
                        break;
                    case 4:
                        value = ( (GraphConversationDataControl) conversationDataControl ).getLineConditionController( node, rowIndex );
                        break;
                }
            }
            return value;
        }
    }

}
