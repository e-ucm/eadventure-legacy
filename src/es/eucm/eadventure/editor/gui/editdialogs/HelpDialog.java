package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import es.eucm.eadventure.common.auxiliar.File;
import es.eucm.eadventure.common.auxiliar.ReleaseFolders;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;

public class HelpDialog extends JDialog implements HyperlinkListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1705431401552161788L;

	private static final int HELP_WIDTH = 720;
	
	private static final int HELP_HEIGHT = 600;
	
	private JEditorPane pane;
	
	private List<URL> backList;
	
	private List<URL> forwardList;
	
	private JButton goBack;
	
	private JButton goForward;
	
	public HelpDialog(String helpPath) {
		super( Controller.getInstance( ).peekWindow( ), TextConstants.getText( "HelpDialog.Title" ), Dialog.ModalityType.TOOLKIT_MODAL );
		backList = new ArrayList<URL>();
		forwardList = new ArrayList<URL>();
		setSize(HELP_WIDTH, HELP_HEIGHT);
		setLayout(new BorderLayout());
		addButtonPanel();
		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((int) (size.getWidth() - HELP_WIDTH) / 2, (int) (size.getHeight() - HELP_HEIGHT) / 2);
		String folder = "help/";
		if (Controller.getInstance().getLanguage() == ReleaseFolders.LANGUAGE_SPANISH)
			folder += "es_ES/";
		else if (Controller.getInstance().getLanguage() == ReleaseFolders.LANGUAGE_ENGLISH)
			folder += "en_EN/";
		File file = new File(folder + helpPath);
		if (file.exists( )){
			pane =new JEditorPane();
			try {
				pane.setPage( file.toURI().toURL( ) );
				backList.add(file.toURI().toURL());
				pane.setEditable( false );
				pane.addHyperlinkListener(this);
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
	
	public void hyperlinkUpdate(HyperlinkEvent event) {
	    if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
	      try {
	        pane.setPage(event.getURL());
	        backList.add(event.getURL());
	        forwardList.clear();
	        updateButtons();
	      } catch(IOException ioe) {
	      }
	    }
	}
	
	private void goBack() {
		if (backList.size() > 1) {
			try {
				pane.setPage(backList.get(backList.size() - 2));
				forwardList.add(backList.get(backList.size() - 1));
				backList.remove(backList.size() - 1);
		        updateButtons();
			} catch (IOException e) {
			}
		}
	}
	
	private void goForward() {
		if (forwardList.size() > 0) {
			try {
				pane.setPage(forwardList.get(forwardList.size() - 1));
				backList.add(forwardList.get(forwardList.size() - 1));
				forwardList.remove(forwardList.size() - 1);
		        updateButtons();
			} catch (IOException e) {
			}
		}
	}
	
	private void addButtonPanel() {
		JPanel buttonPanel = new JPanel();
		goBack = new JButton("Back");
		goBack.setEnabled(false);
		goBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				goBack();
			}
		});
		buttonPanel.add(goBack);
		
		goForward = new JButton("Forward");
		goForward.setEnabled(false);
		goForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				goForward();
			}
		});
		buttonPanel.add(goForward);
		add(buttonPanel, BorderLayout.NORTH);
	}

	private void updateButtons() {
		if (backList.size() > 1)
			goBack.setEnabled(true);
		else
			goBack.setEnabled(false);
		if (forwardList.size() > 0)
			goForward.setEnabled(true);
		else
			goForward.setEnabled(false);
	}
	
	public void writeFileNotFound(String path) {
		add(new JLabel(TextConstants.getText("HelpDialog.FileNotFound") + " " + path));
	}
}
