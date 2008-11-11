package es.eucm.eadventure.editor.gui.elementpanels.general;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import es.eucm.eadventure.editor.control.controllers.general.ExitLookDataControl;
import es.eucm.eadventure.editor.gui.TextConstants;
import es.eucm.eadventure.editor.gui.auxiliar.components.JFiller;
import es.eucm.eadventure.editor.gui.otherpanels.imagepanels.ImagePanel;

public class ExitLookPanel extends JPanel{

	/**
	 * Required
	 */
	private static final long serialVersionUID = 1L;

	private JCheckBox customizeText;
	private JCheckBox customizeCursor;
	private ImagePanel cursorPreviewPanel;
	private JTextField customizedText;
	private JTextField customizedCursorPath;
	private JButton editCursor;
	private JButton deleteCursor;
	
	private ExitLookDataControl dataControl;
	
	public ExitLookPanel (ExitLookDataControl dControl){
		this.dataControl = dControl;
		/*----------------------INFO TEXT AREA------------------------*/
		JTextPane helpText = new JTextPane( );
		helpText.setEditable( false );
		helpText.setBackground( getBackground( ) );
		helpText.setText( TextConstants.getText( "ExitLook.HelpText" ) );
		JPanel informationPanel = new JPanel( );
		informationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "GeneralText.Information" ) ) );
		informationPanel.setLayout( new BorderLayout( ) );
		informationPanel.add( helpText, BorderLayout.CENTER );
		
		/*---------------- TEXT PANEL --------------------------*/
		JPanel textPanel = new JPanel();
		textPanel.setLayout( new GridBagLayout() );
		GridBagConstraints c = new GridBagConstraints(); 
		c.insets=new Insets(5,5,5,5);
		c.fill=GridBagConstraints.BOTH;
		c.weightx=1;
		c.weighty=0.2;
		c.gridwidth=4;
		c.gridheight=1;
		c.gridx=0;
		c.gridy=0;
		
		//CheckBox
		this.customizeText = new JCheckBox(TextConstants.getText( "ExitLook.CustomizeText" ));
		customizeText.setSelected( dataControl.isTextCustomized( ) );
		customizeText.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent e ) {
				customizedText.setEditable( customizeText.isSelected( ) );
				if (!customizeText.isSelected( )){
					dataControl.setExitText( null );
					customizedText.setText( "" );
				} else {
					dataControl.setExitText( customizedText.getText( ) );
				}
			}
			
		});
		c.gridy=0; c.gridx=0; c.gridwidth=1;c.weightx=0.25;
		c.fill = GridBagConstraints.HORIZONTAL;
		textPanel.add( customizeText, c );
		
		//Edit text field
		if (dataControl.isTextCustomized( )){
			customizedText = new JTextField(dataControl.getCustomizedText( ));
			customizedText.setEditable( true );
		}else{
			customizedText = new JTextField("");
			customizedText.setEditable( false );
		}
		customizedText.setBorder( BorderFactory.createEtchedBorder( ) );

		customizedText.addFocusListener( new FocusListener(){

			public void focusGained( FocusEvent e ) {}

			public void focusLost( FocusEvent e ) {
				if (customizedText.isEditable( )){
					dataControl.setExitText( customizedText.getText( ) );
				}
			}
			
		});
		c.gridx=1; c.gridwidth=3; c.weightx=0.75; 
		textPanel.add( customizedText, c );
		textPanel.setBorder( BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder( ), TextConstants.getText( "ExitLook.TextPanelTitle" )) );
		
		
		/*---------------- Cursor PANEL --------------------------*/
		JPanel cursorPanel = new JPanel();
		cursorPanel.setLayout( new GridBagLayout() );
		
		c.fill=GridBagConstraints.BOTH;
		c.weightx=1;
		c.weighty=0.2;
		c.gridwidth=5;
		c.gridheight=1;
		c.gridx=0;
		c.gridy=0;
		
		this.customizeCursor = new JCheckBox(TextConstants.getText( "ExitLook.CustomizeCursor" ));
		customizeCursor.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent e ) {
				
				if (!customizeCursor.isSelected( )){
					dataControl.invalidCursor( );
					cursorPreviewPanel.removeImage( );
					editCursor.setEnabled( false );
					deleteCursor.setEnabled( false );
					customizedCursorPath.setText( "" );
				} else {
					editCursor.setEnabled( true );
					if (dataControl.isCursorCustomized( )){
						cursorPreviewPanel.loadImage( dataControl.getCustomizedCursor( ) );
						deleteCursor.setEnabled( true );
						customizedCursorPath.setText( dataControl.getCustomizedCursor( ) );
					} else{
						deleteCursor.setEnabled( false );
					}
				}
			}
			
		});
		
		this.editCursor = new JButton(TextConstants.getText( "Resources.Select" )  );
		editCursor.addActionListener( new ActionListener(){

			public void actionPerformed( ActionEvent e ) {
				dataControl.editCursorPath( );
				if (dataControl.isCursorCustomized( ) && !dataControl.getCustomizedCursor( ).equals( customizedCursorPath.getText( ) )){
					customizedCursorPath.setText( dataControl.getCustomizedCursor( ) );
					cursorPreviewPanel.loadImage( dataControl.getCustomizedCursor( ) );
					deleteCursor.setEnabled( true );
				}
			}
			
		});
		
		Icon deleteContentIcon = new ImageIcon( "img/icons/deleteContent.png" );
		this.deleteCursor = new JButton( deleteContentIcon );
		deleteCursor.setPreferredSize( new Dimension( 20, 20 ) );
		deleteCursor.addActionListener( new ActionListener() {

			public void actionPerformed( ActionEvent e ) {
					dataControl.invalidCursor( );
					cursorPreviewPanel.removeImage( );
					deleteCursor.setEnabled( false );
					customizedCursorPath.setText( "" );

			}
			
		});
		
		if (dataControl.isCursorCustomized( )){
			this.cursorPreviewPanel = new ImagePanel(dataControl.getCustomizedCursor( ));
			this.customizedCursorPath = new JTextField(dataControl.getCustomizedCursor( ));
			customizeCursor.setSelected( true );
			editCursor.setEnabled( true );
			deleteCursor.setEnabled( true );
		}else{
			this.cursorPreviewPanel = new ImagePanel();
			this.customizedCursorPath = new JTextField("");
			customizeCursor.setSelected( false );
			editCursor.setEnabled( false );
			deleteCursor.setEnabled( false );
		}
		customizedCursorPath.setEditable( false );
		
		c.gridy = 0; c.gridx=0; c.fill= GridBagConstraints.BOTH;c.weightx=0.5;c.gridwidth=2;c.weighty=1;
		cursorPreviewPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ),TextConstants.getText( "Cursor.PreviewTitle" ) ) );
		cursorPanel.add( cursorPreviewPanel, c );
		c.gridx=2;c.weightx=0;
		cursorPanel.add( customizeCursor, c );
		
		c.fill=GridBagConstraints.NONE;
		c.gridy=1; c.gridx=0;c.weightx=0;c.gridwidth=1;c.weighty=0;
		cursorPanel.add( deleteCursor, c );
		c.gridx=1;c.weightx=1;c.gridwidth=2;
		c.fill=GridBagConstraints.HORIZONTAL;
		cursorPanel.add( customizedCursorPath, c );
		c.gridx=3;c.weightx=0;
		c.fill=GridBagConstraints.NONE;
		cursorPanel.add( editCursor, c );
		
		cursorPanel.setBorder( BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder( ), TextConstants.getText( "ExitLook.CursorPanelTitle" )) );
		
		// ADD BOTH PANELS
		this.setLayout( new GridBagLayout() );
		c = new GridBagConstraints(); c.fill=GridBagConstraints.BOTH;c.anchor=GridBagConstraints.NORTH;
		c.gridy=0;c.weightx=1;c.weighty=0.5;
		this.add( informationPanel,c );
		c.anchor=GridBagConstraints.NORTH;
		c.gridy=1;c.weighty=0;
		this.add(textPanel,c);
		c.gridy=2;c.weighty=0.4;
		this.add(cursorPanel,c);
		c.gridy=3;c.weighty=0.3;
		this.add( new JFiller( ),c );
	}
	
}
