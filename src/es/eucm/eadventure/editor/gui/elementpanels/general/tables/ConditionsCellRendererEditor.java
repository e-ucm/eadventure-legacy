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

import es.eucm.eadventure.common.gui.TC;
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
            text = TC.get( "GeneralText.EditConditions" );
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
