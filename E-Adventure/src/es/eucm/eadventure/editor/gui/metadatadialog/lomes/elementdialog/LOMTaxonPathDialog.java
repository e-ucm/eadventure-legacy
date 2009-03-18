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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.data.meta.LangString;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMClassificationTaxonPath;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMESContainer;
import es.eucm.eadventure.editor.data.meta.auxiliar.LOMTaxon;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.LOMESCreateContainerPanel;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.LOMLangStringPanel;
import es.eucm.eadventure.editor.gui.metadatadialog.lomes.elementdialog.LOMContributeDialog;;


public class LOMTaxonPathDialog extends JDialog{

	
	private LOMLangStringPanel source;

	private LOMESCreateContainerPanel taxon;
	
	private LangString sourceValue;

	private LOMTaxon taxonValue;
	
	private LOMESContainer container;
	

	
	public LOMTaxonPathDialog(LOMESContainer container, int selectedItem){
		super( Controller.getInstance( ).peekWindow( ), container.getTitle(), Dialog.ModalityType.APPLICATION_MODAL );
		
		Controller.getInstance().pushWindow(this);
		
		this.container = container;
		
		if (selectedItem ==0){
			
		    sourceValue=new LangString("");
		    taxonValue=new LOMTaxon();
		}else {
		    
		    sourceValue=((LOMClassificationTaxonPath)container.get(selectedItem-1)).getSource();
		    taxonValue=((LOMClassificationTaxonPath)container.get(selectedItem-1)).getTaxon();
		   
		}
		
		GridBagConstraints c = new GridBagConstraints(); 
		c.insets = new Insets(2,2,2,2);c.weightx=1;c.fill = GridBagConstraints.BOTH;
		
		source = new LOMLangStringPanel(sourceValue,TextConstants.getText("LOMES.Classification.TaxonPathSorce"));
	
		taxon = new LOMESCreateContainerPanel(taxonValue,TextConstants.getText("LOMES.Classification.TaxonPathTaxon"),LOMContributeDialog.NONE);
		
		
		
		
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
		
		JPanel mainPanel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints(); 
		c.insets = new Insets(5,5,5,5);c.gridy = 0;
		c.fill = GridBagConstraints.BOTH;c.weightx=1;
		mainPanel.add(source,c);
		c.gridy=1;
		mainPanel.add(taxon,c);
		c.gridy=2;
		mainPanel.add(buttonPanel,c);
		
		this.getContentPane().setLayout(new GridBagLayout());
		this.getContentPane().add(mainPanel);
		//this.getContentPane().add(buttonPanel);
	
		this.setSize( new Dimension(270,230) );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setResizable( false );
		setVisible( true );
		
		addWindowListener( new WindowAdapter (){
			@Override
			public void windowClosed(WindowEvent e) {
				Controller.getInstance().popWindow();
				
			}
			
		});
	
	}



	/**
	 * @return the sourceValue
	 */
	public LangString getSourceValue() {
	    return sourceValue;
	}



	/**
	 * @return the taxonValue
	 */
	public LOMTaxon getTaxonValue() {
	    return taxonValue;
	}



	

}
