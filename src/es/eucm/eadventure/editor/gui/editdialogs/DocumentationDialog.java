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
package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Detailed;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.DataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DetailedDescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;

/**
 * This class is the editing dialog for the effects. Here the user can add effects to the events of the script.
 * 
 * @author Bruno Torijano Bueno
 */
public class DocumentationDialog extends ToolManagableDialog {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
	private JTextField nameTextField;
	
	private NameChangeListener nameChangeListener; 
	
	/**
	 * Text field for the description.
	 */
	private JTextField descriptionTextField;

	/**
	 * Text field for the detailed description.
	 */
	private JTextField detailedDescriptionTextField;
	
	private DataControl dataControl;

	private DescriptionChangeListener descriptionChangeListener;

	private DocumentListener detailedDescriptionListener;

	/**
	 * Constructor.
	 * 
	 * @param effectsController
	 *            Controller for the conditions
	 */
	public DocumentationDialog( DataControl dataControl ) {
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "ActiveAreasList.Documentation" ), false );//, Dialog.ModalityType.APPLICATION_MODAL );
		this.dataControl = dataControl;
		setLayout( new GridBagLayout( ) );
		GridBagConstraints cDoc = new GridBagConstraints( );

		cDoc.fill = GridBagConstraints.HORIZONTAL;
		cDoc.weightx = 1;
		cDoc.weighty = 0;
		cDoc.gridx = 0;
		cDoc.gridy = 0;
		
		if (dataControl.getContent() instanceof Named) {
			JPanel descriptionPanel = new JPanel( );
			descriptionPanel.setLayout( new GridLayout( ) );
			nameTextField = new JTextField( ((Named) dataControl.getContent()).getName() );
			nameChangeListener= new NameChangeListener(nameTextField, (Named) dataControl.getContent());
			nameTextField.getDocument().addDocumentListener(nameChangeListener);
			descriptionPanel.add( nameTextField );
			descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Item.Name" ) ) );
			add( descriptionPanel, cDoc );
			cDoc.gridy++;
		}

		if (dataControl.getContent() instanceof Described) {
			JPanel descriptionPanel = new JPanel( );
			descriptionPanel.setLayout( new GridLayout( ) );
			descriptionTextField = new JTextField( ((Described) dataControl.getContent()).getDescription() );
			descriptionChangeListener = new DescriptionChangeListener(descriptionTextField, (Described) dataControl.getContent());
			descriptionTextField.getDocument().addDocumentListener(descriptionChangeListener);
			descriptionPanel.add( descriptionTextField );
			descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Item.Description" ) ) );
			add( descriptionPanel, cDoc );
			cDoc.gridy++;
		}
		
		if (dataControl.getContent() instanceof Detailed) {
			JPanel detailedDescriptionPanel = new JPanel( );
			detailedDescriptionPanel.setLayout( new GridLayout( ) );
			detailedDescriptionTextField = new JTextField( ((Detailed) dataControl.getContent()).getDetailedDescription( ) );
			detailedDescriptionListener = new DetailedDescriptionChangeListener( detailedDescriptionTextField, (Detailed) dataControl.getContent());
			detailedDescriptionTextField.getDocument().addDocumentListener(detailedDescriptionListener );
			detailedDescriptionPanel.add( detailedDescriptionTextField );
			detailedDescriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Item.DetailedDescription" ) ) );
			add( detailedDescriptionPanel, cDoc );
		}		
		
		setResizable( false );
		setSize( 600, 200 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}
	
	public boolean updateFields(){
		if (descriptionTextField != null){
			descriptionTextField.getDocument().removeDocumentListener(descriptionChangeListener);
			this.descriptionTextField.setText(((Described) dataControl.getContent()).getDescription());
			descriptionTextField.getDocument().addDocumentListener(descriptionChangeListener);
		}
		if (detailedDescriptionTextField != null) {
			detailedDescriptionTextField.getDocument().removeDocumentListener(detailedDescriptionListener);
			this.detailedDescriptionTextField.setText(((Detailed) dataControl.getContent()).getDetailedDescription());
			detailedDescriptionTextField.getDocument().addDocumentListener(detailedDescriptionListener);
		}
		if (nameTextField != null) {
			nameTextField.getDocument().removeDocumentListener(nameChangeListener);
			this.nameTextField.setText(((Named) dataControl.getContent()).getName());
			nameTextField.getDocument().addDocumentListener(nameChangeListener);
		}
		return true;
	}
}
