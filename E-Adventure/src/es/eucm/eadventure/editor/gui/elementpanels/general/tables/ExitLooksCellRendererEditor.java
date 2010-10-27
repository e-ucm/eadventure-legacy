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
package es.eucm.eadventure.editor.gui.elementpanels.general.tables;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.general.ExitLookDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.control.tools.generic.ChangeStringValueTool;

public class ExitLooksCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    private ExitLookDataControl value;

    private JLabel label = null;

    private String tooltip;

    private JPanel panel;

    private GridBagConstraints c;

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int col ) {

        if( value != null )
            this.value = (ExitLookDataControl) value;
        else
            this.value = null;
        return newPanel( isSelected, table );
    }

    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

        if( !isSelected ) {
            if( value != null ) {
                this.value = (ExitLookDataControl) value;
                String text = ( this.value.getCustomizedText( ) != null ? this.value.getCustomizedText( ) : "" );
                ImageIcon icon = new ImageIcon( );
                if( this.value.isCursorCustomized( ) ) {
                    Image image = AssetsController.getImage( this.value.getCustomizedCursor( ) );
                    icon.setImage( image );
                }
                else {
                    Image image = AssetsController.getImage( Controller.getInstance( ).getDefaultExitCursorPath( ) );
                    icon.setImage( image );
                }
                return new JLabel( text, icon, SwingConstants.LEFT );
            }
            else {
                this.value = null;
                return new JLabel( "" );
            }
        }
        else {
            if( value != null )
                this.value = (ExitLookDataControl) value;
            else
                this.value = null;
            return newPanel( isSelected, table );
        }
    }

    private JPanel newPanel( final boolean isSelected, JTable table ) {

        panel = new JPanel( );
        panel.setLayout( new GridBagLayout( ) );
        c = new GridBagConstraints( );

        final JTextField text = new JTextField( );
        text.setText( value.getCustomizedText( ) != null ? value.getCustomizedText( ) : "" );
        text.setEnabled( isSelected );
        text.getDocument( ).addDocumentListener( new DocumentListener( ) {

            public void changedUpdate( DocumentEvent arg0 ) {

            }

            public void insertUpdate( DocumentEvent arg0 ) {

                Tool tool = new ChangeStringValueTool( value, text.getText( ), "getCustomizedText", "setExitText" );
                Controller.getInstance( ).addTool( tool );
            }

            public void removeUpdate( DocumentEvent arg0 ) {

                Tool tool = new ChangeStringValueTool( value, text.getText( ), "getCustomizedText", "setExitText" );
                Controller.getInstance( ).addTool( tool );
            }
        } );

        Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );
        JButton deleteContentButton = new JButton( deleteContentIcon );
        deleteContentButton.setPreferredSize( new Dimension( 20, 20 ) );
        deleteContentButton.setMaximumSize( new Dimension( 20, 20 ) );
        deleteContentButton.setToolTipText( TC.get( "Resources.DeleteAsset" ) );

        ImageIcon cursorPreview = new ImageIcon( );
        if( value.isCursorCustomized( ) ) {
            Image image = AssetsController.getImage( value.getCustomizedCursor( ) );
            tooltip = value.getCustomizedCursor( );
            cursorPreview.setImage( image );
        }
        else {
            Image image = AssetsController.getImage( Controller.getInstance( ).getDefaultExitCursorPath( ) );
            tooltip = Controller.getInstance( ).getDefaultExitCursorPath( );
            cursorPreview.setImage( image );
        }

        final JButton button = new JButton( TC.get( "Buttons.Select" ) );
        button.setFocusable( false );
        button.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                value.editCursorPath( );
                ImageIcon cursorPreview = new ImageIcon( );
                Image image = AssetsController.getImage( value.getCustomizedCursor( ) );
                tooltip = value.getCustomizedCursor( );
                cursorPreview.setImage( image );
                panel.remove( label );
                label = new JLabel( cursorPreview );
                panel.add( label, c );
                panel.validate( );
            }
        } );

        deleteContentButton.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                if( value.isCursorCustomized( ) ) {
                    Controller.getInstance( ).addTool( new ChangeStringValueTool( value, null, "getCustomizedCursor", "setCursorPath" ) );

                    //				value.setCursorPath(null);
                    Image image = AssetsController.getImage( Controller.getInstance( ).getDefaultExitCursorPath( ) );
                    tooltip = Controller.getInstance( ).getDefaultExitCursorPath( );
                    ImageIcon cursorPreview = new ImageIcon( image );
                    panel.remove( label );
                    label = new JLabel( cursorPreview );
                    panel.add( label, c );
                    panel.validate( );
                }
            }
        } );

        c.gridx = 0;
        c.gridy = 0;
        panel.add( button, c );
        c.gridx = 2;
        panel.add( deleteContentButton, c );
        c.gridx = 1;
        c.weightx = 2.0;
        c.weighty = 2.0;
        c.fill = GridBagConstraints.BOTH;
        label = new JLabel( cursorPreview );
        label.setToolTipText( tooltip );
        panel.add( label, c );
        panel.validate( );

        JPanel temp = new JPanel( );
        temp.setLayout( new BorderLayout( ) );
        temp.add( text, BorderLayout.NORTH );
        temp.add( panel, BorderLayout.CENTER );

        temp.setBorder( BorderFactory.createMatteBorder( 2, 0, 2, 0, table.getSelectionBackground( ) ) );

        return temp;
    }

}
