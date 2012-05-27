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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.structurepanel.StructureControl;

public class AuxTabPanel extends JTabbedPane implements Updateable {

    private static final long serialVersionUID = 1546563540388226634L;

    /**
     * The list of PanelTabs
     */
    private List<PanelTab> tabs;

    /**
     * The JComponent of the tab being shown
     */
    private JComponent component = null;

    /**
     * The index of the selected tab
     */
    private int selected = -1;

    /**
     * Constructor
     */
    public AuxTabPanel( ) {

        super( );
        tabs = new ArrayList<PanelTab>( );
        this.addChangeListener( new ElementPanelTabChangeListener( ) );
        this.setFocusable( false );
    }

    /**
     * Add a new PanelTab to the panel
     * 
     * @param tab
     *            The new PanelTab element
     */
    public void addTab( PanelTab tab ) {

        tabs.add( tab );
        JPanel panel = new JPanel( );
        panel.setLayout( new BorderLayout( ) );
        if( tab.getToolTipText( ) != null )
            this.addTab( tab.getTitle( ), tab.getIcon( ), panel, tab.getToolTipText( ) );
        else
            this.addTab( tab.getTitle( ), tab.getIcon( ), panel );
    }

    public boolean updateFields( ) {

        boolean update = false;
        update = tabs.get( this.getSelectedIndex( ) ).updateFields( );
        if( !update ) {
            ( (JPanel) getSelectedComponent( ) ).removeAll( );
            PanelTab tab = tabs.get( getSelectedIndex( ) );
            ( (JPanel) getSelectedComponent( ) ).add( tab.getComponent( ), BorderLayout.CENTER );
            ( (JPanel) getSelectedComponent( ) ).updateUI( );
        }
        return true;
    }

    @Override
    public void addTab( String title, Component component ) {

        super.addTab( title, component );
        this.component = (JComponent) component;
    }

    @Override
    public void setSelectedIndex( int index ) {

        super.setSelectedIndex( index );
        selected = index;
    }

    /**
     * Private class representing the change listener for the changes in the
     * selected tab of the pane.
     */
    private class ElementPanelTabChangeListener implements ChangeListener {

        public void stateChanged( final ChangeEvent arg0 ) {

            if( tabs.size( ) != 0 && getSelectedComponent( ) != null ) {
                //int s = getSelectedIndex();
                if( selected != getSelectedIndex( ) ) {
                    setCursor( new Cursor( Cursor.WAIT_CURSOR ) );
                    //if (selected >= 0)
                    //  AuxTabPanel.this.setTabComponentAt(selected, new JLabel(tabs.get(selected).getTitle(), tabs.get(selected).getIcon(), SwingConstants.LEFT));
                    selected = getSelectedIndex( );
                    ( (JPanel) getSelectedComponent( ) ).removeAll( );
                    PanelTab tab = tabs.get( getSelectedIndex( ) );
                    AuxTabPanel.this.setTabComponentAt( selected, tab.getTab( ) );
                    StructureControl.getInstance( ).visitDataControl( tab.getDataControl( ) );
                    component = tab.getComponent( );
                    ( (JPanel) getSelectedComponent( ) ).add( component, BorderLayout.CENTER );
                    ( (JPanel) getSelectedComponent( ) ).updateUI( );
                    if( selected >= 0 )
                        AuxTabPanel.this.setTabComponentAt( selected, new JLabel( tabs.get( selected ).getTitle( ), tabs.get( selected ).getIcon( ), SwingConstants.LEFT ) );

                    updateUI( );

                    SwingUtilities.invokeLater( new Runnable( ) {

                        public void run( ) {

                            setCursor( new Cursor( Cursor.DEFAULT_CURSOR ) );
                        }
                    } );

                }
            }
        }
    }
}
