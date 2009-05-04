package es.eucm.eadventure.editor.gui.elementpanels.globalstate;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import es.eucm.eadventure.common.data.Documented;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.globalstate.GlobalStateDataControl;
import es.eucm.eadventure.editor.control.tools.listeners.DocumentationChangeListener;
import es.eucm.eadventure.editor.gui.elementpanels.condition.ConditionsPanel2;

/**
 * Panel for Global State
 * @author Javier
 *
 */
public class GlobalStatePanel extends JPanel{

	/**
	 * Required
	 */
	private static final long serialVersionUID = 1356438513519568096L;
	
	
	/**
	 * Conditions panel
	 */
	private ConditionsPanel2 conditionsPanel;
	
	
	private JTextArea documentationTextArea;
	/**
	 * Constructor
	 * @param dataControl
	 */
	public GlobalStatePanel ( GlobalStateDataControl dataControl ){		
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		setLayout ( new GridBagLayout( ) );

		// Create the text area for the documentation
		c.fill = GridBagConstraints.BOTH;
		c.weighty = 0.3;
		c.weightx = 1;
		
		JPanel documentationPanel = new JPanel( );
		documentationPanel.setLayout( new GridLayout( ) );
		documentationTextArea = new JTextArea( dataControl.getDocumentation( ), 4, 0 );
		documentationTextArea.setLineWrap( true );
		documentationTextArea.setWrapStyleWord( true );
		documentationTextArea.getDocument( ).addDocumentListener( new DocumentationChangeListener( documentationTextArea, (Documented) dataControl.getContent() ) );
		documentationPanel.add( new JScrollPane( documentationTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
		documentationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GlobalState.Documentation" ) ) );
		
		add ( documentationPanel, c );
		
		c.weighty = 0.7;
		c.gridy = 1;
		conditionsPanel = new ConditionsPanel2 ( dataControl.getController( ));
		conditionsPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GlobalState.Conditions" ) ) );
		add ( conditionsPanel, c );
	}

}
