package es.eucm.eadventure.editor.gui.ims;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.editor.control.controllers.ims.IMSTextDataControl;
import es.eucm.eadventure.editor.control.controllers.lom.LOMTextDataControl;

public class IMSTextPanel extends JPanel{

	public static final int TYPE_AREA=0;
	public static final int TYPE_FIELD=1;
	
	protected IMSTextDataControl dataControl;
	
	/**
	 * Text area for the documentation.
	 */
	private JTextArea textArea;
	
	protected JTextField textField;

	public IMSTextPanel ( IMSTextDataControl dataControl, String title , int type){
		this(dataControl, title, type, true);
	}
	
	public IMSTextPanel ( IMSTextDataControl dataControl, String title , int type, boolean editable){
		this.dataControl = dataControl;
		
		setLayout( new GridLayout( ) );
	
		if (type==TYPE_AREA){
			textArea = new JTextArea( dataControl.getText( ), 4, 0 );
			textArea.setLineWrap( true );
			textArea.setWrapStyleWord( true );
			textArea.getDocument( ).addDocumentListener( new TextAreaChangesListener( ) );
			textArea.setEditable( editable );
			add( new JScrollPane( textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) );
			textField=null;
		}
		else if (type==TYPE_FIELD){
			textField = new JTextField( dataControl.getText( ) );
			textField.addActionListener( new TextFieldChangesListener( ) );
			textField.addFocusListener( new TextFieldChangesListener( ) );
			textField.setEditable( editable );
			add( textField );
			textArea=null;
		}
		setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), title ) );

	}
	
	/**
	 * Called when a text field has changed, so that we can set the new values.
	 * 
	 * @param source
	 *            Source of the event
	 */
	private void valueChanged( Object source ) {
		// Check the name field
		if (textField!=null){
			dataControl.setText( textField.getText( ) );
		}
		else if (textArea!=null){
			dataControl.setText( textArea.getText( ) );
		}
	}

	
	/**
	 * Listener for the text area. It checks the value of the area and updates the documentation.
	 */
	private class TextAreaChangesListener implements DocumentListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#changedUpdate(javax.swing.event.DocumentEvent)
		 */
		public void changedUpdate( DocumentEvent arg0 ) {
		// Do nothing
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#insertUpdate(javax.swing.event.DocumentEvent)
		 */
		public void insertUpdate( DocumentEvent arg0 ) {
			// Set the new content
			dataControl.setText( textArea.getText( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.swing.event.DocumentListener#removeUpdate(javax.swing.event.DocumentEvent)
		 */
		public void removeUpdate( DocumentEvent arg0 ) {
			// Set the new content
			dataControl.setText( textArea.getText( ) );
		}
	}

	/**
	 * Listener for the text fields. It checks the values from the fields and updates the data.
	 */
	private class TextFieldChangesListener extends FocusAdapter implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.FocusAdapter#focusLost(java.awt.event.FocusEvent)
		 */
		public void focusLost( FocusEvent e ) {
			valueChanged( e.getSource( ) );
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed( ActionEvent e ) {
			valueChanged( e.getSource( ) );
		}
	}

	public void updateText(){
		// Check the name field
		if (textField!=null){
			textField.setText( dataControl.getText( ) );
		}
		else if (textArea!=null){
			textArea.setText( dataControl.getText( ) );
		}
		updateUI();
		
	}
}
