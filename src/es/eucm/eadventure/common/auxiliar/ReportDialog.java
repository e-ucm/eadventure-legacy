package es.eucm.eadventure.common.auxiliar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.IOException;
import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.FileNotFoundException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import java.util.InvalidPropertiesFormatException;
import java.text.ParseException;
import java.io.UnsupportedEncodingException;  
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.text.BadLocationException;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.engine.EAdventure;


public class ReportDialog extends JDialog {

	/**
	 * Default serial version UID
	 */
	private static final long serialVersionUID = 1L;

	private static int PANEL_WIDTH = 500;
	
	private static int PANEL_HEIGHT = 500;
	
	private JTextField nameTextField;

	private JTextField emailTextField;
	
	private JTextArea descriptionTextArea;
		
	private JButton sendButton;
	
	private JButton dontSendButton;
	
	private boolean askName;
	
	String exception = "";
	
	String message = "";
	
	public static void GenerateErrorReport(Exception e, boolean askName, String message) {
		if (isInterestingException(e))
			new ReportDialog(e, askName, message);
		else
			e.printStackTrace();
	}
	
	public static void GenerateCommentsReport() {
		new ReportDialog();
	}
	
	private static boolean isInterestingException(Exception e) {
		if (e instanceof NullPointerException)
			return true;
		if (e instanceof IndexOutOfBoundsException)
			return true;
		if (e instanceof IOException)
			return false;
		if (e instanceof AuthenticationFailedException)
			return false;
		if (e instanceof MessagingException)
			return true;
		if (e instanceof MalformedURLException)
			return true;
		if (e instanceof FileNotFoundException)
			return true;
		if (e instanceof ParserConfigurationException)
			return true;
		if (e instanceof SAXException)
			return true;
		if (e instanceof InvalidPropertiesFormatException)
			return true;
		if (e instanceof SAXParseException)
			return true;
		if (e instanceof ParseException)
			return true;
		if (e instanceof UnsupportedEncodingException)
			return true;
		if (e instanceof TransformerConfigurationException)
			return true;
		if (e instanceof TransformerException)
			return true;
		if (e instanceof InterruptedException)
			return true;
		if (e instanceof java.awt.HeadlessException)
			return true;
		if (e instanceof InvalidMidiDataException)
			return false;
		if (e instanceof MidiUnavailableException)
			return false;
		if (e instanceof LineUnavailableException)
			return false;
		if (e instanceof UnsupportedAudioFileException)
			return false;
		if (e instanceof BadLocationException)
			return false;
		if (e instanceof RuntimeException)
			return true;
		if (e instanceof NumberFormatException)
			return true;
		if (e instanceof java.util.MissingResourceException)
			return true;
		if (e instanceof javax.media.NoPlayerException)
			return true;
		if (e instanceof java.util.zip.ZipException)
			return true;
		if (e instanceof SecurityException)
			return true;
		if (e instanceof es.eucm.eadventure.engine.core.data.SaveGameException)
			return true;
		if (e instanceof java.awt.FontFormatException)
			return true;

//		if (e instanceof StackOverflowException)
//			return true;
		
		return true;
	}
	
	private ReportDialog(Exception e, boolean askName, String message) {
		super();
		
		this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		this.setResizable(false);
		
		this.setTitle(TextConstants.getText("ErrorReport.Title"));
		
		this.setLayout(new GridBagLayout());
		this.askName = askName;
		this.message = message;
		
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize( ).getWidth( );
		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize( ).getHeight( );
		int locX = Math.round(((int)screenWidth - PANEL_WIDTH)/2.0f);
		int locY = Math.round(((int)screenHeight - PANEL_HEIGHT)/2.0f);
		this.setLocation( locX, locY );

		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.9;
		c.weighty = 0.1;
		
		c .fill = GridBagConstraints.HORIZONTAL;
		JTextArea topMessage = new JTextArea(message); 
		topMessage.setLineWrap(true);
		topMessage.setEditable(false);
		this.add(topMessage, c);
		c.gridy++;
		

		c .fill = GridBagConstraints.NONE;
		if (askName) {
			this.add(createNamePanel(), c);
			c.gridy++;
		}
		
		
		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new BorderLayout());
		descriptionPanel.add(new JLabel(TextConstants.getText("ErrorReport.ShortDescription")), BorderLayout.NORTH);
		
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setLineWrap(true);
		JScrollPane scrollPaneDescription = new JScrollPane(descriptionTextArea);
		scrollPaneDescription.setSize(PANEL_WIDTH, PANEL_HEIGHT / 2 - 100);
		
		descriptionPanel.add(scrollPaneDescription, BorderLayout.CENTER);
		c.weighty = 1.0f;
		c.weightx = 1.0f;
		c.fill = GridBagConstraints.BOTH;
		this.add(descriptionPanel, c);
		c.gridy++;
		
		
		JPanel exceptionPanel = new JPanel();
		exceptionPanel.setLayout(new BorderLayout());
		exceptionPanel.add(new JLabel(TextConstants.getText("ErrorReport.FoundException")), BorderLayout.NORTH);
		
		JTextArea exceptionTextArea = new JTextArea();
		if (e != null) {
			StringWriter sw = new StringWriter();
	        PrintWriter pw = new PrintWriter(sw, true);
	        e.printStackTrace(pw);
	        pw.flush();
	        sw.flush();
	        exception = sw.toString();
		}
		exceptionTextArea.setText(exception);
		exceptionTextArea.setEditable(false);
		
		JScrollPane scrollPaneException = new JScrollPane(exceptionTextArea);
		exceptionPanel.add(scrollPaneException, BorderLayout.CENTER);

		this.add(exceptionPanel, c);
		c.gridy++;
		
		
		
		c.weighty = 0.1;
		c.fill = GridBagConstraints.NONE;
		this.add(createButtonPanel(true), c);
		
		
		this.setVisible(true);
	}
	
	private ReportDialog() {
		super();
		
		this.setSize(PANEL_WIDTH, PANEL_HEIGHT);
		this.setResizable(false);
		
		this.setTitle(TextConstants.getText("ErrorReport.TitleComments"));
		
		this.setLayout(new GridBagLayout());
		
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize( ).getWidth( );
		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize( ).getHeight( );
		int locX = Math.round(((int)screenWidth - PANEL_WIDTH)/2.0f);
		int locY = Math.round(((int)screenHeight - PANEL_HEIGHT)/2.0f);
		this.setLocation( locX, locY );

		
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.9;
		c.weighty = 0.1;
		
		

		c .fill = GridBagConstraints.NONE;
		this.add(createNamePanel(), c);
		c.gridy++;
		
		
		JPanel descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new BorderLayout());
		descriptionPanel.add(new JLabel(TextConstants.getText("ErrorReport.Comments")), BorderLayout.NORTH);
		
		descriptionTextArea = new JTextArea();
		descriptionTextArea.setLineWrap(true);
		JScrollPane scrollPaneDescription = new JScrollPane(descriptionTextArea);
		scrollPaneDescription.setSize(PANEL_WIDTH, PANEL_HEIGHT / 2 - 100);
		
		descriptionPanel.add(scrollPaneDescription, BorderLayout.CENTER);
		c.weighty = 1.0f;
		c.weightx = 1.0f;
		c.fill = GridBagConstraints.BOTH;
		this.add(descriptionPanel, c);
		c.gridy++;
				
		c.weighty = 0.1;
		c.fill = GridBagConstraints.NONE;
		this.add(createButtonPanel(false), c);
		
		
		this.setVisible(true);
	}
	
	private JPanel createButtonPanel(final boolean error) {
		JPanel buttonPanel = new JPanel();
		sendButton = new JButton(TextConstants.getText("ErrorReport.Send"));
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReportDialog.this.sendReport(error);
				ReportDialog.this.setVisible(false);
			}
		});
		buttonPanel.add(sendButton);
		
		dontSendButton = new JButton(TextConstants.getText("ErrorReport.Cancel"));
		dontSendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ReportDialog.this.setVisible(false);
			}
		});
		
		buttonPanel.add(dontSendButton);
		return buttonPanel;
	}
	
	protected void sendReport(boolean error) {
		String report;
		if (error) {
			report = "ERROR REPORT\n" + message + "\n\n";
			
			if (askName) {
				report += "USER: " + nameTextField.getText() + "\n";
				report += "EMAIL: " + emailTextField.getText() + "\n\n";
			}
			
			report += "DESCRIPTION:\n" + descriptionTextArea.getText() + "\n\n\n";
			report += "STACK TRACE:\n" + exception + "\n";
		}
		else {
			report = "USER COMMENTS\n\n";
			
			report += "USER: " + nameTextField.getText() + "\n";
			report += "EMAIL: " + emailTextField.getText() + "\n\n";
			
			report += "COMMENTS AND SUGGESTIONS:\n" + descriptionTextArea.getText() + "\n";
		}
		        
		try {
			URL url = new URL("http://147.96.112.160:88/pruebas/test3.php");
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod("POST");
			con.setDoInput (true);
			con.setDoOutput (true);

			con.setUseCaches (false);
			con.setAllowUserInteraction(true);
			HttpURLConnection.setFollowRedirects(true);
			con.setInstanceFollowRedirects(true);
			DataOutputStream out = new DataOutputStream(con.getOutputStream());
			String content = "type=" + (error ? "bug" : "comment" ) + "&version=" + EAdventure.VERSION + "&file=" + report;
			out.writeBytes (content);
			out.flush ();
			out.close ();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			in.readLine();
			//System.out.print(in.readLine());
		} catch (Exception e) {
			//e.printStackTrace();
		}
        
	}

	private JPanel createNamePanel() {
		JPanel temp = new JPanel();
		temp.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.fill = GridBagConstraints.NONE;
		temp.add(new JLabel(TextConstants.getText("ErrorReport.Name")), c);
		
		nameTextField = new JTextField(25) {
			private static final long serialVersionUID = 1L;

			public Dimension getMinimumSize() {
			    return getPreferredSize();
			}
		};
		
		c.gridx++;
		c.fill = GridBagConstraints.HORIZONTAL;
		temp.add(nameTextField, c);
		
		c.gridy++;
		c.gridx = 0;
		c.fill = GridBagConstraints.NONE;
		temp.add(new JLabel(TextConstants.getText("ErrorReport.Email")), c);
		
		emailTextField = new JTextField(200);
		c.gridx++;
		c.fill = GridBagConstraints.HORIZONTAL;
		temp.add(emailTextField, c);
		
		return temp;
	}
	

	public static void main(String[] args) {
		ReportDialog.GenerateErrorReport(new Exception(), false, "FATALE ERORR MESSAGEKA OH MY GODD!!!!!! THIS IS BROKEDN");
		//ReportDialog.GenerateCommentsReport();
	}
}
