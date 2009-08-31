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
package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class StringCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    private String value;

    private JTextField textField;

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( final JTable table, Object value2, boolean isSelected, final int row, final int col ) {

        this.value = (String) value2;
        return createPanel( isSelected, table );
    }

    private Component createPanel( boolean isSelected, JTable table ) {

        JPanel temp = new JPanel( );
        if( isSelected )
            temp.setBorder( BorderFactory.createMatteBorder( 2, 0, 2, 0, table.getSelectionBackground( ) ) );

        textField = new JTextField( this.value );
        temp.addFocusListener( new FocusListener( ) {

            public void focusGained( FocusEvent e ) {

                SwingUtilities.invokeLater( new Runnable( ) {

                    public void run( ) {

                        if( !textField.hasFocus( ) ) {
                            textField.selectAll( );
                            textField.requestFocusInWindow( );
                        }
                    }
                } );
            }

            public void focusLost( FocusEvent e ) {

            }
        } );
        textField.addFocusListener( new FocusListener( ) {

            public void focusGained( FocusEvent arg0 ) {

            }

            public void focusLost( FocusEvent arg0 ) {

                stopCellEditing( );
            }
        } );
        textField.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                stopCellEditing( );
            }
        } );
        textField.getDocument( ).addDocumentListener( new DocumentListener( ) {

            public void changedUpdate( DocumentEvent arg0 ) {

            }

            public void insertUpdate( DocumentEvent arg0 ) {

                value = textField.getText( );
            }

            public void removeUpdate( DocumentEvent arg0 ) {

                value = textField.getText( );
            }
        } );
        temp.setLayout( new BorderLayout( ) );
        temp.add( textField, BorderLayout.CENTER );
        return temp;
    }

    public Component getTableCellRendererComponent( JTable table, Object value2, boolean isSelected, boolean hasFocus, int row, int column ) {

        this.value = (String) value2;
        if( isSelected ) {
            return createPanel( isSelected, table );
        }
        else
            return new JLabel( value );
    }
}
