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
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;

public class LOMVocabularyDialog extends JDialog{
	
	private int selection;
	
	private JComboBox elements;
	
	public LOMVocabularyDialog(String[] values,int selection){
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText("LOMES.Value"), Dialog.ModalityType.APPLICATION_MODAL );
		elements = new JComboBox(values);
		this.selection=selection;
		
		GridBagConstraints c = new GridBagConstraints(); 
		c.insets = new Insets(2,2,2,2);c.weightx=1;c.fill = GridBagConstraints.BOTH;
		JPanel textPanel = new JPanel(new GridBagLayout());
		
		elements.addActionListener(new VocabularySelectionListener ());
		elements.setSelectedIndex(selection);
		textPanel.add(elements,c);
		
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		GridBagConstraints c2 =  new GridBagConstraints(); 
		c2.anchor = GridBagConstraints.CENTER;
		c2.fill = GridBagConstraints.NONE;
		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
			
		});
		buttonPanel.add(ok,c2);
		
		c.gridy=1;
		textPanel.add(buttonPanel,c);
		
		this.getContentPane().setLayout(new GridBagLayout());
		this.getContentPane().add(textPanel);
		//this.getContentPane().add(buttonPanel);
	
		this.setSize( new Dimension(285,100) );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setResizable( false );
		setVisible( true );
	
		
	}

	
	
	private class VocabularySelectionListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			
			selection = elements.getSelectedIndex();
		}      
    }

	/**
	 * @return the selection
	 */
	public int getSelection() {
		return selection;
	}
	
}
