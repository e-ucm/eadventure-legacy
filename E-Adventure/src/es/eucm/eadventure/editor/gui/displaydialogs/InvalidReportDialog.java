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

import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.gui.TextConstants;

/**
 * Dialog that shows a set of list of data, containing the inconsistencies found in an adventure. This dialog is not
 * modal, an can be resized without problems.
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
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "Operation.AdventureConsistencyTitle" ) );

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
		informationTextPane.setText( TextConstants.getText( "Operation.AdventureInconsistentReport" ) );
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
