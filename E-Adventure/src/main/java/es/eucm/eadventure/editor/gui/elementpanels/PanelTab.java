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
package es.eucm.eadventure.editor.gui.elementpanels;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.editdialogs.HelpDialog;

/**
 * This class represents the content of a Tab in an ElementPanel.<br>
 * 
 */
public abstract class PanelTab implements Updateable {

    private static final long serialVersionUID = -7666569367118952601L;

    /**
     * The title of the tab
     */
    private String title;

    /**
     * The tool tip text of the tab
     */
    private String toolTipText;

    /**
     * The JComponent inside the tab that represents the tabs content
     */
    private JComponent component;

    /**
     * The icon of the tab
     */
    private Icon icon;

    /**
     * The datacontrol associated with the tab
     */
    private DataControl dataControl;

    private String helpPath;

    /**
     * Constructor.
     * 
     * @param title
     *            The title of the tab
     * @param dataControl
     *            The data control associated with the tab
     */
    public PanelTab( String title, DataControl dataControl ) {

        super( );
        this.title = title;
        this.dataControl = dataControl;
    }

    /**
     * Returns the title of the tab
     * 
     * @return The title of the tab
     */
    public String getTitle( ) {

        return title;
    }

    /**
     * Set the toolTipText of the tab
     * 
     * @param toolTipText
     *            The new toolTipText
     */
    public void setToolTipText( String toolTipText ) {

        this.toolTipText = toolTipText;
    }

    public void setHelpPath( String helpPath ) {

        this.helpPath = helpPath;
    }

    /**
     * Returns the toolTipText of the tab
     * 
     * @return The toolTipText of the tab
     */
    public String getToolTipText( ) {

        return toolTipText;
    }

    /**
     * Returns the JComponent inside the tab
     * 
     * @return The JComponent inside the tab
     */
    public JComponent getComponent( ) {

        component = getTabComponent( );
        return component;
    }

    /**
     * Abstract method the returns the JComponent for a specific tab type.
     * 
     * @return The JComponent of the tab
     */
    protected abstract JComponent getTabComponent( );

    public boolean updateFields( ) {

        if( component instanceof Updateable ) {
            return ( (Updateable) component ).updateFields( );
        }
        return false;
    }

    /**
     * Returns the data control associated with the tab
     * 
     * @return The data control associated with the tab
     */
    public DataControl getDataControl( ) {

        return dataControl;
    }

    /**
     * Sets the icon for the tab
     * 
     * @param icon
     *            The new icon for the tab
     */
    public void setIcon( Icon icon ) {

        this.icon = icon;
    }

    /**
     * Returns the icon of the tab
     * 
     * @return The icon of the tab
     */
    public Icon getIcon( ) {

        return icon;
    }

    public JComponent getTab( ) {

        JPanel panel = new JPanel( );
        panel.setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.gridx = 0;
        c.gridy = 0;
        JLabel label = new JLabel( title, icon, SwingConstants.LEFT );
        label.setFont( label.getFont( ).deriveFont( Font.BOLD ) );
        panel.add( label, c );
        if( helpPath != null ) {
            JButton infoButton = new JButton( new ImageIcon( "img/icons/information.png" ) );
            infoButton.setContentAreaFilled( false );
            infoButton.setMargin( new Insets( 0, 0, 0, 0 ) );
            infoButton.setFocusable( false );
            infoButton.setBorder( BorderFactory.createEmptyBorder( ) );
            c.gridx = 1;
            panel.add( infoButton, c );
            infoButton.addActionListener( new ActionListener( ) {

                public void actionPerformed( ActionEvent arg0 ) {

                    new HelpDialog( helpPath );
                }
            } );
        }
        panel.setFocusable( false );
        panel.setOpaque( false );
        return panel;
    }
}
