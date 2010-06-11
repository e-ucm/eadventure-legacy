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
package es.eucm.eadventure.editor.gui.displaydialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.editor.control.Controller;

/**
 * Dialog that shows a set of list of data, containing the inconsistencies found
 * in an adventure. This dialog is not modal, an can be resized without
 * problems.
 * 
 * @author Bruno Torijano Bueno
 */
public class InvalidReportDialog extends JDialog {

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructor.
     * 
     * @param incidences
     *            List of strings containing the incidences
     */
    public InvalidReportDialog( List<String> incidences, String text) {

        super( Controller.getInstance( ).peekWindow( ), TC.get( "Operation.AdventureConsistencyTitle" ) );

        // Set the layout for the dialog
        setLayout( new GridBagLayout( ) );
        GridBagConstraints c = new GridBagConstraints( );
        c.insets = new Insets( 5, 5, 5, 5 );

        // Add the panel with the info
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        JTextPane informationTextPane = new JTextPane( );
        informationTextPane.setEditable( false );
        informationTextPane.setBackground( new JPanel( ).getBackground( ) );
        informationTextPane.setText( text );
        add( informationTextPane, c );

        // Add the list with the inconsistencies
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1;
        JList incidencesList = new JList( incidences.toArray( new String[] {} ) );
        add( new JScrollPane( incidencesList ), c );

        // Set the dialog properties
        setMinimumSize( new Dimension( 450, 500 ) );
        setSize( 450, 500 );
        Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
        setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );

        // Set the dialog visible
        setVisible( true );
    }
}
