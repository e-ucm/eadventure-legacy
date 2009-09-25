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
