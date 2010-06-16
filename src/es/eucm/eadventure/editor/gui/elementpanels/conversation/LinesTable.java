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
package es.eucm.eadventure.editor.gui.elementpanels.conversation;

import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

    /**
     * Constant to reference the column where the text of the line is editing
     */
    public static final int TEXT_COLUMN=1;
    
    private ConversationDataControl conversationDataControl;
    
    private int lastSelectedConversationLine;
    
    private ConversationNodeView lastSelectedNode;

    public LinesTable( ConversationDataControl conversationDataControl, LinesPanel linesPanel ) {

        super( );
        this.conversationDataControl = conversationDataControl;
        lastSelectedNode= null;
        putClientProperty( "terminateEditOnFocusLost", Boolean.TRUE );
        
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
                if( getSelectedRow( ) != -1 ){
                    setRowHeight( getSelectedRow( ), 48 );
                    lastSelectedConversationLine = getSelectedRow( );
                }
            }
        } );
        
        this.addFocusListener( new FocusListener(){

            public void focusGained( FocusEvent e ) {

               
                
            }

            public void focusLost( FocusEvent e ) {

                System.out.println( "Pierdo el foco en la tabla" );
                
                
            }
            
        });
        
    }

    public void newSelectedNode( ConversationNodeView selectedNode ) {

        setModel( new NodeTableModel( selectedNode ) );
        if (selectedNode != null)
            lastSelectedNode = selectedNode;
        if( isEditing( ) )
            getCellEditor( ).cancelCellEditing( );
    }
    
    public void modifyConversationLineOutTable(String value){
      conversationDataControl.setNodeLineText( lastSelectedNode, lastSelectedConversationLine, value);


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
                if( columnIndex == 1 ){
                    conversationDataControl.setNodeLineText( node, rowIndex, value.toString( ) );
                  ((TextLineCellRendererEditor) (getColumnModel( ).getColumn( 1 ).getCellEditor( ))).restartValue();
                }
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
