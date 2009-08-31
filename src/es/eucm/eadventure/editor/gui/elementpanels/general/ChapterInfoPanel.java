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
package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Titled;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.general.ChapterDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.TitleChangeListener;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;

public class ChapterInfoPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the chapter.
	 */
	private ChapterDataControl chapterDataControl;

	/**
	 * Text field for the title.
	 */
	private JTextField titleTextField;

	/**
	 * Text area for the description.
	 */
	private JTextArea descriptionTextArea;

	/**
	 * Button to select an assessment file.
	 */
	private JButton selectAssessmentButton;

	/**
	 * Button to delete the current assessment file.
	 */
	private JButton deleteAssessmentButton;
	

	/**
	 * Text field containing the assessment path.
	 */
	private JTextField assessmentPathTextField;

	/**
	 * Button to select an adaptation file.
	 */
	private JButton selectAdaptationButton;

	/**
	 * Button to delete the current adaptation file.
	 */
	private JButton deleteAdaptationButton;
	
	/**
	 * Text field containing the adaptation path.
	 */
	private JTextField adaptationPathTextField;

	/**
	 * Combo box for the selected initial scene.
	 */
	private JComboBox initialSceneComboBox;

	/**
	 * Constructor.
	 * 
	 * @param chapterDataControl
	 *            Chapter controller
	 */
	public ChapterInfoPanel( ChapterDataControl chapterDataControl ) {

		// Set the controller
		this.chapterDataControl = chapterDataControl;

		// Load the image for the delete content button
		Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );

		// Load the array of general scenes to select the initial
		String[] initialScenesArray = Controller.getInstance( ).getIdentifierSummary( ).getGeneralSceneIds( );

		// Set the layout
		setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the field for the title
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;

		JPanel titlePanel = new JPanel( );
		titlePanel.setLayout( new GridLayout( ) );
		titleTextField = new JTextField( chapterDataControl.getTitle( ) );
		titleTextField.getDocument().addDocumentListener( new TitleChangeListener(titleTextField, (Titled) chapterDataControl.getContent()));
		titlePanel.add( titleTextField );
		titlePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Chapter.ChapterTitle" ) ) );
		add( titlePanel, c );

		// Create the text area for the description
		c.gridy = 1;
		JPanel descriptionPanel = new JPanel( );
		descriptionPanel.setLayout( new GridLayout( ) );
		descriptionTextArea = new JTextArea( chapterDataControl.getDescription( ), 4, 0 );
		descriptionTextArea.setLineWrap( true );
		descriptionTextArea.setWrapStyleWord( true );
		descriptionTextArea.getDocument( ).addDocumentListener( new DescriptionChangeListener( descriptionTextArea, (Described) chapterDataControl.getContent()) );
		descriptionPanel.add( new JScrollPane( descriptionTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Chapter.Description" ) ) );
		add( descriptionPanel, c );

		// Create the assessment panel and set the border
		JPanel assessmentPathPanel = new JPanel( );
		assessmentPathPanel.setLayout( new GridBagLayout( ) );
		assessmentPathPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Chapter.AssessmentPath" ) ) );
		GridBagConstraints c2 = new GridBagConstraints( );
		c2.insets = new Insets( 4, 4, 4, 4 );
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		c2.weighty = 0;

		// Create the delete content button
		deleteAssessmentButton = new JButton( deleteContentIcon );
		deleteAssessmentButton.addActionListener( new GeneralButtonListener( ) );
		deleteAssessmentButton.setPreferredSize( new Dimension( 20, 20 ) );
		deleteAssessmentButton.setToolTipText( TextConstants.getText( "Resources.DeleteAsset" ) );
		assessmentPathPanel.add( deleteAssessmentButton, c2 );

		// Create the text field and insert it
		c2.gridx = 1;
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.weightx = 1;
		String assessName=null;
		if (Controller.getInstance().getIdentifierSummary().isAssessmentProfileId(chapterDataControl.getAssessmentName( )))
		    assessName=chapterDataControl.getAssessmentName( );
		assessmentPathTextField = new JTextField( assessName );
		assessmentPathTextField.setEditable( false );
		assessmentPathPanel.add( assessmentPathTextField, c2 );

		// Create the "Select" button and insert it
		c2.gridx = 2;
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		selectAssessmentButton = new JButton( TextConstants.getText( "Resources.Select" ) );
		selectAssessmentButton.addActionListener( new GeneralButtonListener( ) );
		assessmentPathPanel.add( selectAssessmentButton, c2 );
		
		// Add the assessment panel to the principal panel
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		add( assessmentPathPanel, c );

		// Create the adaptation panel and set the border
		JPanel adaptationPathPanel = new JPanel( );
		adaptationPathPanel.setLayout( new GridBagLayout( ) );
		adaptationPathPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Chapter.AdaptationPath" ) ) );
		c2 = new GridBagConstraints( );
		c2.insets = new Insets( 4, 4, 4, 4 );
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		c2.weighty = 0;

		// Create the delete content button
		deleteAdaptationButton = new JButton( deleteContentIcon );
		deleteAdaptationButton.addActionListener( new GeneralButtonListener( ) );
		deleteAdaptationButton.setPreferredSize( new Dimension( 20, 20 ) );
		deleteAdaptationButton.setToolTipText( TextConstants.getText( "Resources.DeleteAsset" ) );
		adaptationPathPanel.add( deleteAdaptationButton, c2 );

		// Create the text field and insert it
		c2.gridx = 1;
		c2.fill = GridBagConstraints.HORIZONTAL;
		c2.weightx = 1;
		String adaptName=null;
		if (Controller.getInstance().getIdentifierSummary().isAdaptationProfileId(chapterDataControl.getAdaptationName( )))
		    adaptName=chapterDataControl.getAdaptationName( );
		adaptationPathTextField = new JTextField(adaptName  );
		adaptationPathTextField.setEditable( false );
		adaptationPathPanel.add( adaptationPathTextField, c2 );

		// Create the "Select" button and insert it
		c2.gridx = 2;
		c2.fill = GridBagConstraints.NONE;
		c2.weightx = 0;
		selectAdaptationButton = new JButton( TextConstants.getText( "Resources.Select" ) );
		selectAdaptationButton.addActionListener( new GeneralButtonListener( ) );
		adaptationPathPanel.add( selectAdaptationButton, c2 );

		// Add the adaptation panel to the principal panel
		c.gridy = 3;
		add( adaptationPathPanel, c );

		// Create and add the initial scene panel
		JPanel initialScenePanel = new JPanel( );
		c.gridy = 4;
		initialScenePanel.setLayout( new GridLayout( ) );
		initialSceneComboBox = new JComboBox( initialScenesArray );
		initialSceneComboBox.setSelectedItem( chapterDataControl.getInitialScene( ) );
		initialSceneComboBox.addActionListener( new InitialSceneComboBoxListener( ) );
		initialScenePanel.add( initialSceneComboBox );
		initialScenePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Chapter.InitialScene" ) ) );
		add( initialScenePanel, c );

		// Add a filler
		c.gridy = 5;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		add( new JFiller( ), c );
	}

	/**
	 * Listener for the select and delete content buttons.
	 */
	private class GeneralButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			// Select assessment file button
			if( e.getSource( ).equals( selectAssessmentButton ) ) {
				// Ask the user for an assessment file
				chapterDataControl.setAssessmentPath( );
				assessmentPathTextField.setText( chapterDataControl.getAssessmentName( ) );
			}

			// Select adaptation file button
			else if( e.getSource( ).equals( selectAdaptationButton ) ) {
				// Ask the user for an adaptation file
				chapterDataControl.setAdaptationPath( );
				adaptationPathTextField.setText( chapterDataControl.getAdaptationName( ) );
			}

			// Delete assessment file button
			else if( e.getSource( ).equals( deleteAssessmentButton ) ) {
				// Delete the current path
				assessmentPathTextField.setText( null );
				chapterDataControl.deleteAssessmentPath( );
			}

			// Delete adaptation file button
			else if( e.getSource( ).equals( deleteAdaptationButton ) ) {
				// Delete the current path
				adaptationPathTextField.setText( null );
				chapterDataControl.deleteAdaptationPath( );
			}
			
		}
	}

	/**
	 * Listener for initial scene combo box.
	 */
	private class InitialSceneComboBoxListener implements ActionListener {
		public void actionPerformed( ActionEvent e ) {
			chapterDataControl.setInitialScene( initialSceneComboBox.getSelectedItem( ).toString( ) );
		}
	}
}
