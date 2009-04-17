package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;

public class CutsceneTypesDialog extends JDialog{

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Stores the option selected for the style of the GUI;
	 */
	private int optionSelected;

	/**
	 * Radio button for the "Slidescene" option.
	 */
	private JRadioButton slidesRadioButton;

	/**
	 * Radio button for the "Videoscene" option.
	 */
	private JRadioButton videoRadioButton;

	/**
	 * Constructor.
	 * 
	 * @param optionSelected
	 *            GUI style active
	 */
	public CutsceneTypesDialog() {
		// Set the values
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "CutsceneTypes.Title" ), Dialog.ModalityType.APPLICATION_MODAL );
		this.optionSelected = Controller.CUTSCENE_SLIDES;

		// Panel with the report options
		JPanel cutsceneTypesPanel = new JPanel( );
		cutsceneTypesPanel.setLayout( new GridBagLayout( ) );

		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 10, 10, 3, 10 );
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.LINE_START;
		c.fill = GridBagConstraints.NONE;
		slidesRadioButton = new JRadioButton( TextConstants.getText( "CutsceneTypes.Slides" ) );
		cutsceneTypesPanel.add( slidesRadioButton, c );

		c.insets = new Insets( 10, 10, 3, 10 );
		c.gridy = 2;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.LINE_START;
		videoRadioButton = new JRadioButton( TextConstants.getText( "CutsceneTypes.Video" ) );
		cutsceneTypesPanel.add( videoRadioButton, c );

		JPanel buttonsPanel = new JPanel( );
		buttonsPanel.setLayout( new FlowLayout( ) );
		JButton btnLoad = new JButton( TextConstants.getText( "GeneralText.OK" ) );
		btnLoad.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				setVisible( false );
				dispose( );
			}
		} );
		buttonsPanel.add( btnLoad );
		JButton btnCancel = new JButton( TextConstants.getText( "GeneralText.Cancel" ) );
		btnCancel.addActionListener( new ActionListener( ) {
			public void actionPerformed( ActionEvent arg0 ) {
				invalidateOptionSelected( );
				setVisible( false );
				dispose( );
			}
		} );
		buttonsPanel.add( btnCancel );

		// Add the principal and the buttons panel
		add( cutsceneTypesPanel, BorderLayout.CENTER );
		add( buttonsPanel, BorderLayout.SOUTH );

		// Configure the radio buttons
		slidesRadioButton.addActionListener( new OptionChangedListener( ) );
		videoRadioButton.addActionListener( new OptionChangedListener( ) );
		ButtonGroup guiButtonGroup = new ButtonGroup( );
		guiButtonGroup.add( slidesRadioButton );
		guiButtonGroup.add( videoRadioButton );
		slidesRadioButton.setSelected( true );

		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				invalidateOptionSelected( );
				setVisible( false );
				dispose( );
			}
		} );

		setSize( 400, 200 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setResizable( false );
		setVisible( true );
	}

	/**
	 * Returns the option that has been selected in the dialog.
	 * 
	 * @return Option selected: 0 for traditional, 1 for contextual, -1 if the dialog was closed or the "Cancel" button
	 *         pressed
	 */
	public int getOptionSelected( ) {
		return optionSelected;
	}

	/**
	 * Invalidates the option selected (when the "Cancel" button is pressed or the dialog is closed).
	 */
	private void invalidateOptionSelected( ) {
		optionSelected = -1;
	}

	/**
	 * Listener for the radio buttons.
	 */
	private class OptionChangedListener implements ActionListener {

		public void actionPerformed( ActionEvent e ) {
			if( e.getSource( ).equals( slidesRadioButton ) )
				optionSelected = Controller.CUTSCENE_SLIDES;
			else if( e.getSource( ).equals( videoRadioButton ) )
				optionSelected = Controller.CUTSCENE_VIDEO;
		}
	}

}
