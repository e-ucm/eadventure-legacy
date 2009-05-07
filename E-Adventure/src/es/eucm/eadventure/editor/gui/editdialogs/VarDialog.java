package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;


public class VarDialog extends JDialog{

    
    private WholeNumberField value;
    
    private boolean err;
    
    
    public VarDialog(int var){
	
	// Call to the JDialog constructor
	super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "VarDialog.Title" ), Dialog.ModalityType.TOOLKIT_MODAL );

	err=false;
	
	// Push the dialog into the stack, and add the window listener to pop in when closing
	Controller.getInstance( ).pushWindow( this );
	addWindowListener( new WindowAdapter( ) {
		public void windowClosing( WindowEvent e ) {
			Controller.getInstance( ).popWindow( );
		}
	} );
	
	
	
	value = new WholeNumberField(var,10);
	//value.setPreferredSize(new Dimension (150,20));
	JPanel cont = new JPanel();
	cont.add(value);
	cont.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "VarDialog.Value" ) ) );
	
	//setLayout(new BorderLayout());
	add(cont);
	// Create Ok button to close the dialog
	JButton ok = new JButton("OK");
	ok.addActionListener(new ActionListener(){

	    @Override
	    public void actionPerformed(ActionEvent e) {
		dispose();
		
	    }
	    
	});
	JPanel container= new JPanel();
	container.add(ok);
	add(ok,BorderLayout.SOUTH);
	// Set the size, position and properties of the dialog
	setResizable( false );
	setSize( 250, 100);
	Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
	setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
	setVisible( true );
	ok.requestFocus();
	
    }


	
	public String getValue(){
	    if (err)
		return "error";
	    else
		return value.getText();
	}

    	/**
	 * Called when a text field has changed, so that we can check the new values.
	 * 
	 * @param String
	 * 		
	 */
	private void valueChanged( String source ) {
		// Check the if the field is a number
		try{
		    Integer.parseInt(source);
		    err=true;
		}catch (NumberFormatException er){
		   
		}
		
	}
	
	
	public class WholeNumberField extends JTextField {

	    private Toolkit toolkit;
	    private NumberFormat integerFormatter;

	    public WholeNumberField(int value, int columns) {
	        super(columns);
	        toolkit = Toolkit.getDefaultToolkit();
	        integerFormatter = NumberFormat.getNumberInstance(Locale.US);
	        integerFormatter.setParseIntegerOnly(true);
	        setValue(value);
	    }

	    public int getValue() {
	        int retVal = 0;
	        try {
	            retVal = integerFormatter.parse(getText()).intValue();
	        } catch (ParseException e) {
	            // This should never happen because insertString allows
	            // only properly formatted data to get in the field.
	            toolkit.beep();
	        }
	        return retVal;
	    }

	    public void setValue(int value) {
	        setText(integerFormatter.format(value));
	    }

	    protected Document createDefaultModel() {
	        return new WholeNumberDocument();
	    }

	    protected class WholeNumberDocument extends PlainDocument {

	        public void insertString(int offs, String str, AttributeSet a) 
	            throws BadLocationException {

	            char[] source = str.toCharArray();
	            char[] result = new char[source.length];
	            int j = 0;

	            for (int i = 0; i < result.length; i++) {
	                if (Character.isDigit(source[i]))
	                    result[j++] = source[i];
	                else {
	                    toolkit.beep();
	                    Controller.getInstance().showErrorDialog(TextConstants.getText("VarDialog.Err.Title"), TextConstants.getText("VarDialog.Err.Message"));
			    err = false;
	                }
	            }
	            super.insertString(offs, new String(result, 0, j), a);
	        }
	    }

	}

	    
}
