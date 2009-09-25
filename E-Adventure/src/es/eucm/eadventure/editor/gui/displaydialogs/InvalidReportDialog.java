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
    public InvalidReportDialog( List<String> incidences ) {

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
        informationTextPane.setText( TC.get( "Operation.AdventureInconsistentReport" ) );
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
