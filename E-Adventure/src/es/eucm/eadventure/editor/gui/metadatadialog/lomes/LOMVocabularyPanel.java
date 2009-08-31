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
