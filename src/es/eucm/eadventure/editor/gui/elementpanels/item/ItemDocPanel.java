package es.eucm.eadventure.editor.gui.elementpanels.item;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Detailed;
import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.item.ItemDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DetailedDescriptionChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;

public class ItemDocPanel extends JPanel implements Updateable { 

	private static final long serialVersionUID = 4556146180537102976L;

	private ItemDataControl itemDataControl;
	
	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Text field for the name.
	 */
	private JTextField nameTextField;

	/**
	 * Text field for the description.
	 */
	private JTextField descriptionTextField;

	/**
	 * Text field for the detailed description.
	 */
	private JTextField detailedDescriptionTextField;

	public ItemDocPanel(ItemDataControl itemDataControl) {
		setLayout( new GridBagLayout( ) );
		this.itemDataControl = itemDataControl;
		GridBagConstraints cDoc = new GridBagConstraints( );

		cDoc.insets = new Insets( 5, 5, 5, 5 );

		cDoc.fill = GridBagConstraints.HORIZONTAL;
		cDoc.weightx = 1;
		cDoc.weighty = 0.3;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( itemDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) itemDataControl.getContent() ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Item.Documentation" ) ) );
		add( documentationPanel, cDoc );

		cDoc.gridy = 1;
		cDoc.weighty = 0;
		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridLayout( ) );
		nameTextField = new JTextField( itemDataControl.getName( ) );
		nameTextField.getDocument().addDocumentListener(new NameChangeListener(nameTextField, (Named) itemDataControl.getContent()));
		namePanel.add( nameTextField );
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Item.Name" ) ) );
		add( namePanel, cDoc );

		// Create the field for the brief description
		cDoc.gridy = 2;
		JPanel descriptionPanel = new JPanel( );
		descriptionPanel.setLayout( new GridLayout( ) );
		descriptionTextField = new JTextField( itemDataControl.getBriefDescription( ) );
		descriptionTextField.getDocument().addDocumentListener(new DescriptionChangeListener(descriptionTextField, (Described) itemDataControl.getContent()));
		descriptionPanel.add( descriptionTextField );
		descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Item.Description" ) ) );
		add( descriptionPanel, cDoc );

		// Create the field for the detailed description
		cDoc.gridy = 3;
		JPanel detailedDescriptionPanel = new JPanel( );
		detailedDescriptionPanel.setLayout( new GridLayout( ) );
		detailedDescriptionTextField = new JTextField( itemDataControl.getDetailedDescription( ) );
		detailedDescriptionTextField.getDocument().addDocumentListener( new DetailedDescriptionChangeListener( detailedDescriptionTextField, (Detailed) itemDataControl.getContent()));
		detailedDescriptionPanel.add( detailedDescriptionTextField );
		detailedDescriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Item.DetailedDescription" ) ) );
		add( detailedDescriptionPanel, cDoc );
		
		cDoc.gridy = 4;
		cDoc.fill = GridBagConstraints.BOTH;
		cDoc.weightx = 1;
		cDoc.weighty = 0.5;
		add( new JFiller(),cDoc );
	}
	
	public boolean updateFields() {
		this.descriptionTextField.setText(itemDataControl.getBriefDescription());
		this.detailedDescriptionTextField.setText(itemDataControl.getDetailedDescription());
		this.documentationTextArea.setText(itemDataControl.getDocumentation());
		this.nameTextField.setText(itemDataControl.getName());
		return true;
	}
}
