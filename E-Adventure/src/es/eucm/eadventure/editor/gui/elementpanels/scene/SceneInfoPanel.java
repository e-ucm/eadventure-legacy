package es.eucm.eadventure.editor.gui.elementpanels.scene;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.data.Named;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.scene.SceneDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.control.tools.listeners.NameChangeListener;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;

public class SceneInfoPanel extends JPanel implements Updateable{

	private static final long serialVersionUID = -6436346206278699690L;

	private SceneDataControl sceneDataControl;
	
	/**
	 * Text area for the documentation.
	 */
	private JTextArea documentationTextArea;

	/**
	 * Text field for the name.
	 */
	private JTextField nameTextField;

		
	public SceneInfoPanel(SceneDataControl sDataControl) {
		super();

		this.sceneDataControl = sDataControl;

		setLayout( new GridBagLayout( ) );
		GridBagConstraints cDoc = new GridBagConstraints( );


		cDoc.insets = new Insets( 5, 5, 5, 5 );

		// Create the text area for the documentation
		cDoc.fill = GridBagConstraints.BOTH;
		cDoc.weighty = 0.5;
		cDoc.weightx = 1;
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( sceneDataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) sceneDataControl.getContent()) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.Documentation" ) ) );
		add( documentationPanel, cDoc );

		// Create the text field for the name
		cDoc.gridy = 1;
		cDoc.weighty = 0;
		cDoc.fill = GridBagConstraints.HORIZONTAL;
		JPanel namePanel = new JPanel( );
		namePanel.setLayout( new GridLayout( ) );
		nameTextField = new JTextField( sceneDataControl.getName( ) );
		nameTextField.getDocument().addDocumentListener( new NameChangeListener(nameTextField, (Named) sceneDataControl.getContent()));
		namePanel.add( nameTextField );
		namePanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Scene.Name" ) ) );
		add( namePanel, cDoc );

		cDoc.gridy++;
		cDoc.weightx = 1;
		cDoc.weighty = 0.5;
		add( new JFiller( ), cDoc );
	}
	



	public boolean updateFields() {
		documentationTextArea.setText( sceneDataControl.getDocumentation( ) );
		nameTextField.setText( sceneDataControl.getName( ) );
		return true;
	}

}
