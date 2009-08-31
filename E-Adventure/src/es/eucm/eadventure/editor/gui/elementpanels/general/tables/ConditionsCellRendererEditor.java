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

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;

public class ConditionsCellRendererEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private static final long serialVersionUID = 8128260157985286632L;

    public static final int NO_ICON = 0;

    public static final int ICON_SMALL = 1;

    public static final int ICON_MEDIUM = 2;

    public static final int ICON_LARGE = 3;

    private boolean useText;

    private int iconSize;

    private ConditionsController value;

    public ConditionsCellRendererEditor( ) {

        this.useText = false;
        this.iconSize = ICON_SMALL;
    }

    public ConditionsCellRendererEditor( boolean useText, int iconSize ) {

        this.useText = useText;
        this.iconSize = iconSize;
    }

    public Object getCellEditorValue( ) {

        return value;
    }

    public Component getTableCellEditorComponent( JTable table, Object value, boolean isSelected, int row, int col ) {

        if( value == null )
            return null;
        this.value = (ConditionsController) value;
        return createButton( isSelected, table );
    }

    public Component getTableCellRendererComponent( JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column ) {

        if( value == null )
            return null;
        this.value = (ConditionsController) value;
        return createButton( isSelected, table );
    }

    private Icon createIcon( ) {

        // Create icon (if applicable)
        Icon icon = null;
        boolean hasConditions = false;
        if( value != null ) {
            ConditionsController controller = value;
            hasConditions = !controller.isEmpty( );
        }
        if( iconSize == ICON_SMALL ) {
            if( hasConditions ) {
                icon = new ImageIcon( "img/icons/conditions-16x16.png" );
            }
            else {
                icon = new ImageIcon( "img/icons/no-conditions-16x16.png" );
            }
        }
        if( iconSize == ICON_MEDIUM ) {
            if( hasConditions ) {
                icon = new ImageIcon( "img/icons/conditions-24x24.png" );
            }
            else {
                icon = new ImageIcon( "img/icons/no-conditions-24x24.png" );
            }
        }
        if( iconSize == ICON_LARGE ) {
            if( hasConditions ) {
                icon = new ImageIcon( "img/icons/conditions-32x32.png" );
            }
            else {
                icon = new ImageIcon( "img/icons/no-conditions-32x32.png" );
            }
        }
        return icon;
    }

    private Component createButton( boolean isSelected, JTable table ) {

        JPanel temp = new JPanel( );
        temp.setOpaque( false );
        if( isSelected )
            temp.setBorder( BorderFactory.createMatteBorder( 2, 0, 2, 0, table.getSelectionBackground( ) ) );
        JButton button = null;

        // Create text (if applicable)
        String text = null;
        if( useText ) {
            text = TextConstants.getText( "GeneralText.EditConditions" );
        }

        // Create icon (if applicable)
        Icon icon = createIcon( );

        // Create button
        if( text != null && icon != null ) {
            button = new JButton( text, icon );
            button.setToolTipText( text );
        }
        else if( text != null ) {
            button = new JButton( text );
            button.setToolTipText( text );
        }
        else if( icon != null ) {
            button = new JButton( icon );
            button.setContentAreaFilled( false );
            button.setOpaque( false );
        }

        button.setFocusable( false );
        button.setEnabled( isSelected );

        //button.setOpaque(false);
        //button.setContentAreaFilled(false);
        //button.setRolloverIcon(new ImageIcon("img/icons/conditions-hot-16x16.png"));
        button.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                new ConditionsDialog( ConditionsCellRendererEditor.this.value );
                //Update icon
                ( (JButton) ( arg0.getSource( ) ) ).setIcon( createIcon( ) );
            }
        } );
        temp.setLayout( new BorderLayout( ) );
        temp.add( button, BorderLayout.CENTER );
        //button.requestFocus();
        return temp;

    }

}
