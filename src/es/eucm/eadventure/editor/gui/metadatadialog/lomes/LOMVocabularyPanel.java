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

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class LOMVocabularyPanel extends JPanel {

    private int selection;

    private JComboBox elements;

    public LOMVocabularyPanel( String[] values, int selection ) {

        //super( Controller.getInstance( ).peekWindow( ), TextConstants.getText("LOMES.Value"), Dialog.ModalityType.APPLICATION_MODAL );
        super( );
        elements = new JComboBox( values );
        elements.setPreferredSize( new Dimension( 130, 20 ) );
        this.selection = selection;

        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 2, 2, 2, 2 );
        c.weightx = 1;
        c.fill = GridBagConstraints.BOTH;
        JPanel textPanel = new JPanel( new GridBagLayout( ) );

        elements.addActionListener( new VocabularySelectionListener( ) );
        elements.setSelectedIndex( selection );
        textPanel.add( elements, c );

        this.add( textPanel );

    }

    private class VocabularySelectionListener implements ActionListener {

        public void actionPerformed( ActionEvent arg0 ) {

            selection = elements.getSelectedIndex( );
        }
    }

    /**
     * @return the selection
     */
    public int getSelection( ) {

        return selection;
    }

}
