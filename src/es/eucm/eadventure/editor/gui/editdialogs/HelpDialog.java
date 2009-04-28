package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.auxiliar.ReleaseFolders;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;

public class HelpDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1705431401552161788L;

	private static final int HELP_WIDTH = 720;
	
	private static final int HELP_HEIGHT = 600;
	
	public HelpDialog(String helpPath) {
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "HelpDialog.Title" ), Dialog.ModalityType.TOOLKIT_MODAL );
		setSize(HELP_WIDTH, HELP_HEIGHT);
		setLayout(new BorderLayout());
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (size.getWidth() - HELP_WIDTH) / 2, (int) (size.getHeight() - HELP_HEIGHT) / 2);
		String folder = "help/";
		if (Controller.getInstance().getLanguage() == ReleaseFolders.LANGUAGE_SPANISH)
			folder += "es_ES/";
		else if (Controller.getInstance().getLanguage() == ReleaseFolders.LANGUAGE_ENGLISH)
			folder += "en_EN/";
		File file = new File(folder + helpPath);
		if (file.exists( )){
			JEditorPane pane =new JEditorPane();
			try {
				pane.setPage( file.toURI().toURL( ) );
				pane.setEditable( false );
				add(new JScrollPane(pane, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
			} catch (MalformedURLException e1) {
				writeFileNotFound(folder + helpPath);
			} catch (IOException e1) {
				writeFileNotFound(folder + helpPath);
			}
		} else {
			writeFileNotFound(folder + helpPath);
		}
		
		setVisible(true);
		
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				dispose( );
			}
		} );
	}
	
	public void writeFileNotFound(String path) {
		add(new JLabel(TextConstants.getText("HelpDialog.FileNotFound") + " " + path));
	}
}
