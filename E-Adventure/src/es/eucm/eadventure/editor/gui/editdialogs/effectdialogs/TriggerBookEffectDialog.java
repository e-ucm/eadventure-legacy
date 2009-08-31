/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.gui.editdialogs.effectdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.EffectsController;

public class TriggerBookEffectDialog extends EffectDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Combo box with the books.
	 */
	private JComboBox booksComboBox;

	/**
	 * Constructor.
	 * 
	 * @param currentProperties
	 *            Set of initial values
	 */
	public TriggerBookEffectDialog( HashMap<Integer, Object> currentProperties ) {

		// Call the super method
		super( TextConstants.getText( "TriggerBookEffect.Title" ), false );

		// Take the list of books
		String[] booksArray = controller.getIdentifierSummary( ).getBookIds( );

		// If there is at least one element
		if( booksArray.length > 0 ) {
			// Create the main panel
			JPanel mainPanel = new JPanel( );
			mainPanel.setLayout( new GridBagLayout( ) );
			GridBagConstraints c = new GridBagConstraints( );

			// Set the border of the panel with the description
			mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "TriggerBookEffect.Description" ) ) ) );

			// Create and add the list of flags
			c.insets = new Insets( 2, 4, 4, 4 );
			c.fill = GridBagConstraints.HORIZONTAL;
			c.weightx = 1;
			booksComboBox = new JComboBox( booksArray );
			mainPanel.add( booksComboBox, c );

			// Add the panel to the center
			add( mainPanel, BorderLayout.CENTER );

			// Set the defualt values (if present)
			if( currentProperties != null ) {
				if( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TARGET ) )
					booksComboBox.setSelectedItem( currentProperties.get( EffectsController.EFFECT_PROPERTY_TARGET ) );
			}

			// Set the dialog
			setResizable( false );
			setSize( 240, 140 );
			Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
			setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
			setVisible( true );
		}

		// If the list had no elements, show an error message
		else
			controller.showErrorDialog( getTitle( ), TextConstants.getText( "TriggerBookEffect.ErrorNoBooks" ) );
	}

	@Override
	protected void pressedOKButton( ) {
		// Create a set of properties, and put the selected value
		properties = new HashMap<Integer, Object>( );
		properties.put( EffectsController.EFFECT_PROPERTY_TARGET, booksComboBox.getSelectedItem( ).toString( ) );
	}

}
