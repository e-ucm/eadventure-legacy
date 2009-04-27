package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.HeadlessException;


import javax.swing.JDialog;
import javax.swing.JFileChooser;


public class GenericFileChooserDialog extends JFileChooser{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private JFileChooser fileChooser;
    
    public GenericFileChooserDialog( String path) {
	
	super(path);		
    }
    
    protected JDialog createDialog(Component parent) throws HeadlessException {
	JDialog dialog = super.createDialog(parent);
	dialog.setModalityType(Dialog.ModalityType.TOOLKIT_MODAL);
	return dialog;
    }
    
    
}
