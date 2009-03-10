package es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;


public class LOMStringDialog extends JDialog{
	
	public static final int TYPE_AREA=0;
	public static final int TYPE_FIELD=1;
	
	private JTextField text;
	
	private String textValue;
	
	private JTextArea textArea;
	
	private int type;
	
	public LOMStringDialog(String value,int type){
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText("LOMES.Value"), Dialog.ModalityType.APPLICATION_MODAL );
		this.type = type;
		textValue = value;
		
		GridBagConstraints c = new GridBagConstraints(); 
		c.insets = new Insets(2,2,2,2);c.weightx=1;c.fill = GridBagConstraints.BOTH;
		JPanel textPanel = new JPanel(new GridBagLayout());
		if (type==TYPE_FIELD){
			text = new JTextField(textValue);
			text.getDocument().addDocumentListener(new TextFieldListener ());
			textPanel.add(text,c);
			textArea=null;
			this.setSize( new Dimension(200,150) );
			
		} else if (type==TYPE_AREA){
			textArea = new JTextArea(textValue,4, 0 );
			textArea.getDocument().addDocumentListener(new TextFieldListener ());
			textArea.setLineWrap( true );
			textArea.setWrapStyleWord( true );
			textArea.setEditable( true );
			c.ipady = 100;
			textPanel.add( new JScrollPane( textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER ) ,c);
			this.setSize( new Dimension(300,250) );
			//textPanel.add(textArea,c);
			text = null;
		}
		
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		c =  new GridBagConstraints(); 
		c.anchor = GridBagConstraints.CENTER;
		c.fill = GridBagConstraints.NONE;
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
			
		});
		buttonPanel.add(ok,c);
		
		
		this.getContentPane().setLayout(new GridLayout(2,0));
		this.getContentPane().add(textPanel);
		this.getContentPane().add(buttonPanel);
	
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setResizable( false );
		setVisible( true );
	
		
	}

	private class TextFieldListener implements DocumentListener {

		public void changedUpdate( DocumentEvent e ) {
			//Do nothing
		}

		public void insertUpdate( DocumentEvent e ) {
			if (type==TYPE_FIELD){
				textValue = text.getText( );
			} else if (type==TYPE_AREA){
				textValue = textArea.getText( );
			}
			
		}
		public void removeUpdate( DocumentEvent e ) {
			insertUpdate(e);
		}
		
	}

	/**
	 * @return the textValue
	 */
	public String getTextValue() {
		return textValue;
	}
	
}
