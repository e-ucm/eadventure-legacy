/*******************************************************************************
 * eAdventure (formerly <e-Adventure> and <e-Game>) is a research project of the e-UCM
 *          research group.
 *   
 *    Copyright 2005-2012 e-UCM research group.
 *  
 *     e-UCM is a research group of the Department of Software Engineering
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
 * This file is part of eAdventure, version 1.5.
 * 
 *   You can access a list of all the contributors to eAdventure at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       eAdventure is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      eAdventure is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with Adventure.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.structurepanel.DuplicateElementTool;
import es.eucm.eadventure.editor.control.tools.structurepanel.RemoveElementTool;
import es.eucm.eadventure.editor.control.tools.structurepanel.RenameElementTool;

/**
 * This class is the one that represents an element in the list of the
 * StructurePanel.
 * <p>
 * 
 * @author Eugenio Marchiori
 * 
 */
public class StructureElementCell extends JPanel {

    private static final long serialVersionUID = -2096132139472242178L;

    /**
     * The value of the the element
     */
    private StructureElement value;

    /**
     * The table where it is shown
     */
    private JTable table;

    /**
     * Boolean indicating if the element is being renamed
     */
    private boolean renaming;

    /**
     * Boolean indicating if the element in selected
     */
    private boolean isSelected;

    /**
     * Filed where the name of the element is written
     */
    private JTextField name;

    /**
     * The parent element
     */
    private StructureListElement parent;

    /**
     * Constructur for the cell.
     * <p>
     * 
     * @param value
     *            The value of the cell
     * @param table
     *            The table where the cell is
     * @param isSelected
     *            Boolean indicating if it is selected
     * @param parent
     *            The parent element
     */
    public StructureElementCell( StructureElement value, JTable table, boolean isSelected, StructureListElement parent ) {

        setOpaque( true );
        setBackground( Color.white );
        this.value = value;
        this.table = table;
        this.isSelected = isSelected;
        this.parent = parent;
        setLayout( new GridLayout( 0, 1 ) );

        recreate( );

        this.addFocusListener( new FocusListener( ) {

            public void focusGained( FocusEvent arg0 ) {

                SwingUtilities.invokeLater( new Runnable( ) {

                    public void run( ) {

                        if( name != null && !name.hasFocus( ) ) {
                            name.selectAll( );
                            name.requestFocusInWindow( );
                        }
                    }
                } );
            }

            public void focusLost( FocusEvent arg0 ) {

            }
        } );
    }

    /**
     * Method to recreate the element
     */
    public void recreate( ) {

        removeAll( );

        if( isSelected && !renaming && !( value ).isJustCreated( ) ) {
            JLabel label;
            if( ( value ).getIcon( ) == null )
                label = new JLabel( " " + ( value ).getName( ), SwingConstants.CENTER );
            else
                label = new JLabel( ( value ).getName( ), ( value ).getIcon( ), SwingConstants.CENTER );

            label.setFont( label.getFont( ).deriveFont( Font.BOLD ) );
            add( label );
            setBorder( BorderFactory.createLineBorder( new Color (26, 23, 27) , 2 ) );

            boolean hasOptions = false;
            JPanel optionsPanel = new JPanel( );
            optionsPanel.setBackground( Color.WHITE );
            optionsPanel.setLayout( new GridBagLayout( ) );
            GridBagConstraints c = new GridBagConstraints( );
            c.gridx = 0;
            c.gridy = 0;
            if( ( value ).isCanRename( ) ) {
                addRenameButton( c, optionsPanel );
                hasOptions = true;
            }
            if( ( value ).canBeDuplicated( ) ) {
                addDuplicateButton( c, optionsPanel );
                hasOptions = true;
            }
            if( ( value ).canBeRemoved( ) ) {
                addRemoveButton( c, optionsPanel );
                hasOptions = true;
            }
            if( hasOptions )
                add( optionsPanel );
            this.setMinimumSize( new Dimension( this.getWidth( ), 60 ) );
        }
        else if( isSelected && ( renaming || ( value ).isJustCreated( ) ) ) {
            name = new JTextField( ( value ).getName( ) );
            name.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent arg0 ) {

                    acceptRename( );
                }
            } );
            name.addFocusListener( new FocusListener( ) {

                public void focusGained( FocusEvent arg0 ) {

                    // Do nothing
                }

                public void focusLost( FocusEvent arg0 ) {

                    cancelRename( );
                }
            } );
            add( name );
            name.requestFocusInWindow( );
            setBorder( BorderFactory.createLineBorder(  new Color (26, 23, 27) , 2 ) );

            JPanel optionsPanel = createOKCancelButtons( );
            add( optionsPanel );

            this.setMinimumSize( new Dimension( this.getWidth( ), 60 ) );
        }
        else {
            renaming = false;

            if( value != null ) {
                JLabel label;
                if( ( value ).getIcon( ) == null )
                    label = new JLabel( " " + ( value ).getName( ), SwingConstants.LEFT );
                else
                    label = new JLabel( ( value ).getName( ), ( value ).getIcon( ), SwingConstants.LEFT );
                add( label );
            }
            setBorder( BorderFactory.createEmptyBorder( ) );
            this.setMinimumSize( new Dimension( this.getWidth( ), 20 ) );
        }

    }

    /**
     * Create a panel with the OK and Cancel buttons for the changes in the name
     * of the element.
     * <p>
     * 
     * @return
     */
    private JPanel createOKCancelButtons( ) {

        JPanel optionsPanel = new JPanel( );
        optionsPanel.setLayout( new GridLayout( 1, 0 ) );
        optionsPanel.setBackground( Color.WHITE );

        JButton rename = new JButton( TC.get( "GeneralText.OK" ) );
        rename.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                acceptRename( );
            }
        } );
        rename.setFocusable( false );
        optionsPanel.add( rename );

        JButton cancel = new JButton( TC.get( "GeneralText.Cancel" ) );
        cancel.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                cancelRename( );
            }
        } );

        cancel.setFocusable( false );
        optionsPanel.add( cancel );
        return optionsPanel;
    }

    /**
     * Add the rename button to the cell
     * 
     * @param c
     *            The constraints for the elemetns in the cell
     * @param optionsPanel
     *            The panel where the button goes
     */
    private void addRemoveButton( GridBagConstraints c, JPanel optionsPanel ) {

        JButton remove = new JButton( new ImageIcon( "img/icons/deleteNode.png" ) );
        remove.setContentAreaFilled( false );
        remove.setMargin( new Insets( 0, 0, 0, 0 ) );
        remove.setBorder( BorderFactory.createEmptyBorder( ) );
        remove.setToolTipText( TC.get( "GeneralText.Delete" ) );
        remove.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                Controller.getInstance( ).addTool( new RemoveElementTool( table, value ) );
            }
        } );

        remove.setFocusable( false );
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0f;
        optionsPanel.add( remove, c );
    }

    /**
     * Add the duplicate button to the cell
     * 
     * @param c
     *            The constraints for the elements in the cell
     * @param optionsPanel
     *            The panel where the button goes
     */
    private void addDuplicateButton( GridBagConstraints c, JPanel optionsPanel ) {

        JButton remove = new JButton( new ImageIcon( "img/icons/duplicateNode.png" ) );
        remove.setContentAreaFilled( false );
        remove.setMargin( new Insets( 0, 0, 0, 0 ) );
        remove.setBorder( BorderFactory.createEmptyBorder( ) );
        remove.setToolTipText( TC.get( "GeneralText.Duplicate" ) );
        remove.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                Controller.getInstance( ).addTool( new DuplicateElementTool( value, table, parent ) );
            }
        } );

        remove.setFocusable( false );
        c.fill = GridBagConstraints.NONE;
        c.weightx = 0.0f;
        optionsPanel.add( remove, c );
        c.gridx++;
    }

    /**
     * Add the rename button to the cell
     * 
     * @param c
     *            The constraints for the elements in the cell
     * @param optionsPanel
     *            The panel where the button goes
     */
    private void addRenameButton( GridBagConstraints c, JPanel optionsPanel ) {

        JButton rename = new JButton( TC.get( "GeneralText.Rename" ) );

        rename.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent arg0 ) {

                renaming = true;
                recreate( );
                repaint( );
                updateUI( );
            }
        } );

        rename.setFocusable( false );
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 2.0f;
        optionsPanel.add( rename, c );
        c.gridx++;
    }

    /**
     * Accept the changes to the name and rename the element
     */
    private void acceptRename( ) {

        Controller.getInstance( ).addTool( new RenameElementTool( StructureElementCell.this.value.getDataControl( ), name.getText( ) ) );
        ( value ).setJustCreated( false );
        renaming = false;
        recreate( );
        repaint( );
        updateUI( );
    }

    /**
     * Cancel the changes to the name and keep the old element name
     */
    private void cancelRename( ) {

        renaming = false;
        recreate( );
        repaint( );
        updateUI( );
        if( ( value ).isJustCreated( ) ) {
            ( value ).setJustCreated( false );
            ( value ).delete( false );
            table.clearSelection( );
            Controller.getInstance( ).updateStructure( );
        }
    }

    /**
     * @return The value of the element
     */
    public Object getValue( ) {

        return value;
    }
}
