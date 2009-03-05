package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Detailed;
import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.scene.ActiveAreaDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DetailedDescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.editdialogs.ConditionsDialog;
import es.eucm.eadventure.editor.gui.otherpanels.IrregularAreaEditionPanel;
import es.eucm.eadventure.editor.gui.otherpanels.ScenePreviewEditionPanel;

public class ActiveAreaPanel extends JPanel {

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Controller of the activeArea.
	 */
	private ActiveAreaDataControl activeAreaDataControl;

	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	private ScenePreviewEditionPanel spep;
	
	private JTabbedPane tabPanel;

	private JPanel docPanel;

	private JTextField nameTextField;

	private JTextField descriptionTextField;

	private JTextField detailedDescriptionTextField;
	
	private JCheckBox rectangular;

	/**
	 * Constructor.
	 * 
	 * @param activeAreaDataControl
	 *            ActiveArea controller
	 */
	public ActiveAreaPanel( ActiveAreaDataControl activeAreaDataControl ) {
		// Set the controller
		this.activeAreaDataControl = activeAreaDataControl;
		
		// Create the panels and layouts
		tabPanel = new JTabbedPane( );
		docPanel = new JPanel( );
		docPanel.setLayout( new GridBagLayout( ) );
		GridBagConstraints cDoc = new GridBagConstraints( );

		// Set the layout
		setLayout( new BorderLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Title" ) ) );
		//GridBagConstraints c = new GridBagConstraints( );
		cDoc.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		cDoc.fill = GridBagConstraints.HORIZONTAL;
		cDoc.weightx = 1;
		cDoc.weighty = 0.3;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( activeAreaDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) activeAreaDataControl.getContent() ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Documentation" ) ) );
		docPanel.add( documentationPanel, cDoc );

		// Create the field for the name
		cDoc.gridy = 1;
		cDoc.weighty = 0;
		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridLayout( ) );
		nameTextField = new JTextField( this.activeAreaDataControl.getName( ) );
		nameTextField.getDocument().addDocumentListener( new NameChangeListener(nameTextField, (Named) activeAreaDataControl.getContent()));
		namePanel.add( nameTextField );
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Name" ) ) );
		docPanel.add( namePanel, cDoc );

		// Create the field for the brief description
		cDoc.gridy = 2;
		JPanel descriptionPanel = new JPanel( );
		descriptionPanel.setLayout( new GridLayout( ) );
		descriptionTextField = new JTextField( activeAreaDataControl.getBriefDescription( ) );
		descriptionTextField.getDocument().addDocumentListener( new DescriptionChangeListener(descriptionTextField, (Described) activeAreaDataControl.getContent()));
		descriptionPanel.add( descriptionTextField );
		descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Description" ) ) );
		docPanel.add( descriptionPanel, cDoc );

		// Create the field for the detailed description
		cDoc.gridy = 3;
		JPanel detailedDescriptionPanel = new JPanel( );
		detailedDescriptionPanel.setLayout( new GridLayout( ) );
		detailedDescriptionTextField = new JTextField( activeAreaDataControl.getDetailedDescription( ) );
		detailedDescriptionTextField.getDocument().addDocumentListener( new DetailedDescriptionChangeListener(detailedDescriptionTextField, (Detailed) activeAreaDataControl.getContent()));
		detailedDescriptionPanel.add( detailedDescriptionTextField );
		detailedDescriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.DetailedDescription" ) ) );
		docPanel.add( detailedDescriptionPanel, cDoc );
		
		
		cDoc.gridy = 4;
		cDoc.fill = GridBagConstraints.BOTH;
		cDoc.weightx = 1;
		cDoc.weighty = 0.5;
		docPanel.add( new JFiller(),cDoc );

		// Add the tabs
		//Finally, add lookPanel to its scrollPane container, and insert it as a tab along with docPanel

		tabPanel.insertTab( TextConstants.getText( "ActiveArea.MainPanelTitle" ), null, createMainPanel(), TextConstants.getText( "ActiveArea.MainPanelTip" ), 0 );
		tabPanel.insertTab( TextConstants.getText( "ActiveArea.DocPanelTitle" ), null, docPanel, TextConstants.getText( "ActiveArea.DocPanelTip" ), 1 );
		setLayout( new BorderLayout( ) );
		add( tabPanel, BorderLayout.CENTER );
	}

	private JPanel createMainPanel (){
		JPanel mainPanel = new JPanel();
		// Take the path of the background
		String scenePath = Controller.getInstance( ).getSceneImagePath( activeAreaDataControl.getParentSceneId( ) );
		
		JPanel looksPanel;
		
		if (activeAreaDataControl.isRectangular()) {
			spep = new ScenePreviewEditionPanel(false, scenePath);
			spep.setShowTextEdition(true);
			spep.setFixedSelectedElement(true);
			spep.setSelectedElement(activeAreaDataControl);
			looksPanel = spep;
		} else {
			looksPanel = new IrregularAreaEditionPanel(scenePath, activeAreaDataControl, Color.GREEN);
			spep = ((IrregularAreaEditionPanel) looksPanel).getScenePreviewEditionPanel();
			spep.setShowTextEdition(false);		
		}
		
		// Set the layout of the principal panel
		mainPanel.setLayout( new GridBagLayout( ) );
		//setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Title" ) ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );

		// Create the button for the conditions
		c.gridy = 0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1;
		JPanel conditionsPanel = new JPanel( );
		conditionsPanel.setLayout( new GridLayout( ) );
		JButton conditionsButton = new JButton( TextConstants.getText( "GeneralText.EditConditions" ) );
		conditionsButton.addActionListener( new ConditionsButtonListener( ) );
		conditionsPanel.add( conditionsButton );
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "ActiveArea.Conditions" ) ) );
		mainPanel.add( conditionsPanel, c );
		
		c.gridy = 1;
		rectangular = new JCheckBox(TextConstants.getText("ActiveArea.IsRectangular"));
		rectangular.setSelected(activeAreaDataControl.isRectangular());
		rectangular.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (activeAreaDataControl.isRectangular() != rectangular.isSelected()) {
					activeAreaDataControl.setRectangular(rectangular.isSelected());
					Controller.getInstance().reloadPanel();
				}
			}
		});
		mainPanel.add( rectangular, c);

		
		// Add the created rectangle position panel
		c.gridy = 2;
		c.fill = GridBagConstraints.BOTH;		
		c.weighty = 1;
		mainPanel.add( looksPanel , c );
		
		return mainPanel;
	}

	/**
	 * Listener for the edit conditions button.
	 */
	private class ConditionsButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			new ConditionsDialog( activeAreaDataControl.getConditions( ) );
		}
	}
}
