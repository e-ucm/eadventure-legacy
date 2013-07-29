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
package es.eucm.eadventure.editor.gui.metadatadialog.lomes;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESComposeType;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESLifeCycleDate;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMLifeCycleDateDialog;
import es.eucm.eadventure.editor.data.meta.LangString;

public class LOMESCreateComposeTypePanel extends JPanel {

    private JButton edit;

    private JLabel value;

    private LOMESComposeType compose;

    public LOMESCreateComposeTypePanel( LOMESComposeType compose ) {

        super( );
        this.compose = compose;
        this.setLayout( new GridBagLayout( ) );

        edit = new JButton( TC.get( "LOMES.Edit" ) );
        edit.addActionListener( new EditButtonListener( ) );
        GridBagConstraints c = new GridBagConstraints( );

        c.gridy = 0;

        if( compose instanceof LOMESLifeCycleDate ) {
            value = new JLabel( ( (LOMESLifeCycleDate) compose ).getDateTime( ) );
            this.add( value, c );
            c.gridy = 1;
        }

        this.add( edit, c );

    }

    /**
     * Listener for the "Add" button
     */
    private class EditButtonListener implements ActionListener {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed( ActionEvent arg0 ) {

            if( compose instanceof LOMESLifeCycleDate ) {
                LOMLifeCycleDateDialog idDialog = new LOMLifeCycleDateDialog( (LOMESLifeCycleDate) compose );
                ( (LOMESLifeCycleDate) compose ).setDateTime( idDialog.getDateTimeValue( ) );
                ( (LOMESLifeCycleDate) compose ).setDescription( new LangString( idDialog.getDescriptionValue( ) ) );
                value.setText( idDialog.getDateTimeValue( ) );

            }
        }
    }
}
