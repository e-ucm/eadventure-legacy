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
package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.controllers.SelectedEffectsController;
import es.eucm.eadventure.editor.control.controllers.SelectedEffectsController.ListElement;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.SelectEffectsDialog;

public class MostVisitedPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private boolean isPressed;

    private String selectedName;

    private SelectEffectsDialog dialog;

    private SelectedEffectsController selectEffectControl;

    public static final int N_EFFECTS_TO_DISPLAY = 10;

    public static final int N_COLS = 2;

    public MostVisitedPanel( SelectEffectsDialog dialog ) {

        this.dialog = dialog;
        int nRows = N_EFFECTS_TO_DISPLAY / N_COLS;
        if( N_EFFECTS_TO_DISPLAY % N_COLS > 0 )
            nRows++;
        this.setLayout( new GridLayout( nRows, N_COLS ) );
        //GridBagConstraints c = new GridBagConstraints();
        //c.fill =GridBagConstraints.BOTH;
        this.isPressed = false;
        selectEffectControl = new SelectedEffectsController( );
        ListElement[] values = selectEffectControl.getMostVisiteEffects( N_EFFECTS_TO_DISPLAY );
        int addedButtons = 0;
        for( int i = 0; i < N_EFFECTS_TO_DISPLAY; i++ ) {
            if( values[i].getValue( ) > 0 ) {
                String name = SelectedEffectsController.reconvertNames( values[i].getName( ) );
                JButton button = new JButton( EffectsStructurePanel.getEffectIcon( name, EffectsStructurePanel.ICON_SIZE_LARGE ) );
                button.setRolloverIcon( EffectsStructurePanel.getEffectIcon( name, EffectsStructurePanel.ICON_SIZE_LARGE_HOT ) );
                button.setPressedIcon( EffectsStructurePanel.getEffectIcon( name, EffectsStructurePanel.ICON_SIZE_LARGE_HOT ) );
                button.setToolTipText( name );
                button.setContentAreaFilled( false );

                button.addActionListener( new ButtonListener( SelectedEffectsController.reconvertNames( values[i].getName( ) ) ) );
                add( button );
                addedButtons++;
            }
        }
        if( addedButtons == 0 )
            this.add( new JLabel( TC.get( "MoreVisitedPanel.Empty" ) ) );
        else if( addedButtons < N_EFFECTS_TO_DISPLAY ) {
            while( addedButtons < N_EFFECTS_TO_DISPLAY ) {
                add( new JFiller( ) );
                addedButtons++;
            }
        }

    }

    private class ButtonListener implements ActionListener {

        private String storedName;

        public ButtonListener( String name ) {

            this.storedName = name;
        }

        public void actionPerformed( ActionEvent e ) {

            isPressed = true;
            selectedName = storedName;
            dialog.setOk( true );

        }

    }

    /**
     * @return the isPressed
     */
    public boolean isPressed( ) {

        return isPressed;
    }

    /**
     * @return the selectedName
     */
    public String getSelectedName( ) {

        return selectedName;
    }

}
