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
        textPane.setText( this.value );
        textPane.setAutoscrolls( true );
        textPane.setForeground( color );
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

}
