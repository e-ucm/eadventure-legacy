/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
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
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.metadatadialog.lomdialog;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.editor.control.controllers.metadata.lom.LOMOptionsDataControl;

public class LOMOptionsPanel extends JPanel {

    private LOMOptionsDataControl dataControl;

    private JComboBox comboBox;

    public LOMOptionsPanel( LOMOptionsDataControl dControl, String title ) {

        this.dataControl = dControl;
        setLayout( new GridLayout( ) );

        comboBox = new JComboBox( dataControl.getOptions( ) );
        if( dataControl.getSelectedOption( ) != -1 )
            comboBox.setSelectedIndex( dataControl.getSelectedOption( ) );
        comboBox.addActionListener( new ActionListener( ) {

            public void actionPerformed( ActionEvent e ) {

                dataControl.setOption( comboBox.getSelectedIndex( ) );
            }

        } );
        add( comboBox );
        setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), title ) );
    }

    public void setSelectedOption( String op ) {

        dataControl.setOption( Integer.parseInt( op ) );
    }

}
