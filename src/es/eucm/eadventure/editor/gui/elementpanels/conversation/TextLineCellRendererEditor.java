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

import java.awt.Color;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractCellEditor;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNodeView;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;

public class TextLineCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    private String value;

    private JTextPane textPane;

    private LinesPanel linesPanel;
    
    public TextLineCellRendererEditor( LinesPanel linesPanel ) {

        this.linesPanel = linesPanel;
    }

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( JTable table, Object value2, boolean isSelected, final int row, final int col ) {

        ConversationNodeView node = ( (ConversationNodeView) value2 );
        this.value = node.getLineText( row );
        Color color = getColor( node, row );
        textPane = new JTextPane( );
        if (this.value.contains( TC.get( "ConversationLine.DefaultText" )+ "\n" ))
           value = value.substring( 0, value.indexOf( "\n" ) );
        textPane.setText( this.value );
        textPane.setAutoscrolls( true );
        textPane.setForeground( color );
      
        textPane.addFocusListener(new TextLineFocus());
        
        
        
            textPane.getDocument( ).addDocumentListener( new DocumentListener( ) {

            public void changedUpdate( DocumentEvent arg0 ) {

                value = textPane.getText( );
                
            }

            public void insertUpdate( DocumentEvent arg0 ) {

                value = textPane.getText( );
                
            }

            public void removeUpdate( DocumentEvent arg0 ) {

                value = textPane.getText( );
            }
        } );
        
        textPane.addKeyListener( new KeyListener( ) {

            public void keyPressed( KeyEvent arg0 ) {

                if( arg0.getKeyCode( ) == KeyEvent.VK_ENTER ) {
                    value = textPane.getText( );
                    stopCellEditing( );
                    linesPanel.editNextLine( );
                    textPane.setText( TC.get( "ConversationLine.DefaultText" ) );
                    
                }
            }

            public void keyReleased( KeyEvent arg0 ) {

            }

            public void keyTyped( KeyEvent arg0 ) {

            }
        } );
        JScrollPane scrollPane = new JScrollPane( textPane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );

        scrollPane.addFocusListener( new FocusListener( ) {

            public void focusGained( FocusEvent arg0 ) {
 
                SwingUtilities.invokeLater( new Runnable( ) {

                    public void run( ) {
                        textPane.selectAll( );
                        textPane.requestFocusInWindow( );
                    }
                } );
            }

            public void focusLost( FocusEvent arg0 ) {
              
            }
        } );

        return scrollPane;

    }
    
   public void restartValue(){
       value=null;
   }

    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

        ConversationNodeView node = ( (ConversationNodeView) value );
        Color color = getColor( node, row );
        if( isSelected ) {
            JTextPane textPane = new JTextPane( );
            textPane.setText( node.getLineText( row ) );
            textPane.setForeground( color );
            textPane.setAutoscrolls( true );
            return textPane;
        }
        else {
            JLabel label = new JLabel( node.getLineText( row ) );
            label.setForeground( color );
            return label;
        }
    }

    private Color getColor( ConversationNodeView node, int row ) {

        Color color = Controller.generateColor( row );
        if( node.getType( ) == ConversationNodeView.DIALOGUE ) {
            String name = node.getLineName( row );
            color = Controller.generateColor( 0 );
            String[] charactersArray = Controller.getInstance( ).getIdentifierSummary( ).getNPCIds( );
            for( int i = 0; i < charactersArray.length; i++ ) {
                if( charactersArray[i].equals( name ) )
                    color = Controller.generateColor( i + 1 );
            }
        }
        return color;
    }
    
    
    private class TextLineFocus implements FocusListener{

        
        private int selectedLine;
        
        private ConversationNodeView node;
        
        public TextLineFocus(){
            this.selectedLine = linesPanel.getLineTable( ).getSelectedRow( );
            this.node = linesPanel.getSelectedNode( );
        }

        public void focusGained( FocusEvent e ) {

            // TODO Auto-generated method stub
            
        }

        public void focusLost( FocusEvent e ) {
           
            if ( selectedLine==linesPanel.getLineTable( ).getSelectedRow( ))
               linesPanel.getLineTable( ).modifyConversationLineOutTable(value);
            else if (linesPanel.getLineTable( ).getSelectedRow( )==-1)
                linesPanel.getLineTable( ).modifyConversationLineOutTable(value,node,selectedLine);
        }
       
        
        
    }
}
