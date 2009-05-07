package es.eucm.eadventure.editor.gui.structurepanel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.auxiliar.ReleaseFolders;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;

/**
 * This panel shows the info about an concrete effect 
 *
 */
public class EffectInfoPanel extends JPanel{
    
    
    /**
     * Pane to show HTML formatted text
     */
    private JEditorPane editorPane;
    
    
    
    public EffectInfoPanel(){
	
	editorPane = new JEditorPane();
	this.setLayout(new BorderLayout());
	add(new JScrollPane(editorPane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
	
    }
    
    public void setHTMLText(String helpPath){
	//this.removeAll();
	
	String folder = "help/";
	if (Controller.getInstance().getLanguage() == ReleaseFolders.LANGUAGE_SPANISH)
		folder += "es_ES/";
	else if (Controller.getInstance().getLanguage() == ReleaseFolders.LANGUAGE_ENGLISH)
		folder += "en_EN/";
	File file = new File(folder + helpPath);
	if (file.exists( )){
		try {
		    editorPane.setPage( file.toURI().toURL( ) );
		    editorPane.setEditable( false );
		    editorPane.setHighlighter(null);
		} catch (MalformedURLException e1) {
			writeFileNotFound(folder + helpPath);
		} catch (IOException e1) {
			writeFileNotFound(folder + helpPath);
		}
	} else {
		writeFileNotFound(folder + helpPath);
	}

	
	
    }
    
    public void writeFileNotFound(String path) {
	add(new JLabel(TextConstants.getText("HelpDialog.FileNotFound") + " " + path));
    }
    
   

}
