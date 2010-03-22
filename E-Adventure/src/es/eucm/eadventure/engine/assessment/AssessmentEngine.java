/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.engine.assessment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import es.eucm.eadventure.common.auxiliar.ReleaseFolders;
import es.eucm.eadventure.common.auxiliar.SendMail;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.assessment.AssessmentProperty;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentRule;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.common.loader.incidences.Incidence;
import es.eucm.eadventure.engine.core.control.FlagSummary;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.TimerEventListener;
import es.eucm.eadventure.engine.core.control.TimerManager;
import es.eucm.eadventure.engine.core.control.VarSummary;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.core.gui.GUI;
import es.eucm.eadventure.engine.resourcehandler.ResourceHandler;

/**
 * This engine stores the rules to be processed when the flags change in the
 * game, creating events that tell the process of the player in the game
 */
public class AssessmentEngine implements TimerEventListener {

	public static int STATE_STARTED = 1;

	public static int STATE_NONE = 0;

	public static int STATE_DONE = 2;
	
	/**
	 * Constants for the colors of the HTML reports
	 */
	private static String HTML_REPORT_COLOR_0 = "794910"; //Old value =#4386CE 
	
	private static String HTML_REPORT_COLOR_1 = "F7D769"; //Old value =#C1D6EA
	
	private static String HTML_REPORT_COLOR_2 = "FFFFFF"; //Old value =#EEF6FE

	/**
	 * Current assessment profile
	 */
	private AssessmentProfile assessmentProfile;

	/**
	 * List of rules to be checked
	 */
	private List<AssessmentRule> assessmentRules;

	/**
	 * List of executed rules
	 */
	private List<ProcessedRule> processedRules;

	/**
	 * Structure of timed rules
	 */
	private HashMap<Integer, TimedAssessmentRule> timedRules;

	private String playerName;

	private int state;

	/**
	 * Constructor
	 */
	public AssessmentEngine() {
		processedRules = new ArrayList<ProcessedRule>();
		timedRules = new HashMap<Integer, TimedAssessmentRule>();
		state = STATE_NONE;
	}

	/**
	 * Loads a set of assessment rules
	 * 
	 * @param assessmentPath
	 *            Path of the file containing the assessment data
	 */
	public void loadAssessmentRules(AssessmentProfile profile) {
		//if (assessmentPath != null && !assessmentPath.equals("")) {
			//assessmentProfile = Loader.loadAssessmentProfile(ResourceHandler
			//		.getInstance(), assessmentPath, new ArrayList<Incidence>());
			if (profile!=null){
	    		assessmentProfile = profile;
			assessmentRules = new ArrayList<AssessmentRule>(assessmentProfile.getRules());

			FlagSummary flags = Game.getInstance().getFlags();
			VarSummary vars = Game.getInstance().getVars();
			for (String flag : assessmentProfile.getFlags()) {
				flags.addFlag(flag);
			}
			for (String var : assessmentProfile.getVars()) {
				vars.addVar(var);
			}
		//} else {
			//assessmentRules = new ArrayList<AssessmentRule>();
		//}

		// Iterate through the rules: those timed add them to the timer manager
		for (AssessmentRule assessmentRule : assessmentRules) {
			if (assessmentRule instanceof TimedAssessmentRule) {
				TimedAssessmentRule tRule = (TimedAssessmentRule) assessmentRule;
				int id = TimerManager.getInstance().addTimer(tRule.getInitConditions(), tRule.getEndConditions(), tRule.isUsesEndConditions(), this);
				timedRules.put(new Integer(id), tRule);
			}
		}
		processRules();
			}
	}

	public static AssessmentProfile loadAssessmentProfile(String assessmentPath) {
		if (assessmentPath != null && !assessmentPath.equals("")) {
			AssessmentProfile assessmentProfile = Loader.loadAssessmentProfile(
					ResourceHandler.getInstance(), assessmentPath,
					new ArrayList<Incidence>());
			return assessmentProfile;
		}
		return null;
	}

	/**
	 * Process the rules, triggering them if necessary
	 */
	public void processRules() {
		int i = 0;

		if (assessmentRules!=null){
		// For every rule
		while (i < assessmentRules.size()) {

			// If it was activated, execute the rule
			if (isActive(assessmentRules.get(i))) {
				AssessmentRule oldRule = assessmentRules.remove(i);
				ProcessedRule rule = new ProcessedRule(oldRule, Game
						.getInstance().getTime());

				 //System.out.println("Se cumple la regla "+ oldRule.getId());
				// Signal the LMS about the change
				if (Game.getInstance().isConnected()) {
				    // check if it is necessary to send in-game value to the property
				    List<AssessmentProperty> properties = checkProperties(oldRule.getAssessmentProperties());
					Game.getInstance().getComm().notifyRelevantState(properties);
				//	System.out.println("Mandamos regla de adaptacion");
				}
				processedRules.add(rule);
			}

			// Else, check the next rule
			else
				i++;
		}
		}
	}

	private static boolean isActive(AssessmentRule rule) {
		return new FunctionalConditions(rule.getConditions()).allConditionsOk();
	}

	/**
	 * Assign a in-game var/flag to an assessment property
	 */
	private List<AssessmentProperty> checkProperties(List<AssessmentProperty> properties){
	    if (properties!= null){
	        for (AssessmentProperty property:properties){
	            if (property.getVarName( )!=null){
	                if (Game.getInstance().getFlags( ).existFlag( property.getVarName( ))){
	                    if (Game.getInstance().getFlags( ).getFlagValue(property.getVarName( )))
	                        property.setValue( "true" );
	                    else 
	                        property.setValue( "false" );
	                } else if (Game.getInstance().getVars( ).existVar(property.getVarName( ))){
	                        property.setValue( Integer.toString( Game.getInstance().getVars( ).getValue( property.getVarName( ) )) );
	                
	                }
	                
	    
	            }
	        }
	    }
	    return properties;
	}
	
	/**
	 * Returns the timed rule indexed by key "i".
	 * 
	 * @param i
	 *            the key in the hash map
	 * @return the correct TimedAssessmentRule of timedRules
	 */
	public TimedAssessmentRule getTimedAssessmentRule(int i) {
		return timedRules.get(new Integer(i));
	}

	/**
	 * Generates a report file, in XML format
	 * 
	 * @param filename
	 *            File name of the report file
	 */
	public void generateXMLReport(String filename) {
		try {
			// Create the necessary elements for building the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.newDocument();

			// Create the root element, "report"
			Element report = doc.createElement("report");

			// For each processed rule
			for (ProcessedRule rule : processedRules) {
				// Create a new "processed-rule" node (DOM element), and link it
				// to the document
				Element processedRule = rule.getDOMStructure();
				doc.adoptNode(processedRule);

				// Add the node
				report.appendChild(processedRule);
			}

			// Add the report structure to the XML file
			doc.appendChild(report);

			// Indent the DOM
			indentDOM(report, 0);

			// Create the necessary elements for export the DOM into a XML file
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();

			// Create the output buffer, write the DOM and close it
			OutputStream fout = new FileOutputStream(filename);
			OutputStreamWriter writeFile = new OutputStreamWriter(fout, "UTF-8");
			transformer.transform(new DOMSource(doc), new StreamResult(
					writeFile));
			writeFile.close();

		} catch (IOException exception) {
			exception.printStackTrace();
		} catch (ParserConfigurationException exception) {
			exception.printStackTrace();
		} catch (TransformerConfigurationException exception) {
			exception.printStackTrace();
		} catch (TransformerException exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * Generates a report file, in HTML format
	 * 
	 * @param filename
	 *            File name of the report file
	 * @param minImportance
	 *            Importance value for filtering
	 */
	public void generateHTMLReport(String filename, int minImportance) {
		try {
			// Create the file to write
			PrintStream file = new PrintStream(new FileOutputStream(filename));

			// HTML tag
			file.println("<html>");

			// Header
			file.print("<title>");
			file.print(Game.getInstance( ).processText( Game.getInstance().getGameDescriptor().getTitle()) );
			file.println("</title>");

			// Body and content table
			file.println("<body style=\"background: #"+HTML_REPORT_COLOR_0+";\">");
			file.println("<br/><br/>");
			file
					.println("<table width=\"80%\" align=\"center\" style=\"background : #"+HTML_REPORT_COLOR_1+"; border : 1px solid #000000;\">");
			file.println("<tr><td>");

			// Title
			file.print("<center><h1>");
			file.print(Game.getInstance( ).processText(Game.getInstance().getGameDescriptor().getTitle()));
			file.print(" report");
			file.println("</h1></center>");

			// Clear table
			file.println("<br/><br/>");
			file
					.println("<table width=\"95%\" align=\"center\" style=\"background : #"+HTML_REPORT_COLOR_2+"; border : 1px solid #000000\">");
			file.println("<tr><td>");

			// For each processed rule
			for (ProcessedRule rule : processedRules) {
				// First check the importance
				if (rule.getImportance() >= minImportance) {
					file.println(Game.getInstance( ).processText(rule.getHTMLCode()));
					file.println("<br/><br/>");
				}
			}

			// Close clear table
			file.println("</td></tr>");
			file.println("</table>");

			// Close table and body
			file.println("<br/><br/>");
			file.println("</td></tr>");
			file.println("</table>");
			file.println("</body>");

			// Close HTML
			file.println("</html>");

			// Close the file
			file.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Indent the given DOM node recursively with the given depth
	 * 
	 * @param nodeDOM
	 *            DOM node to be indented
	 * @param depth
	 *            Depth of the current node
	 */
	private void indentDOM(Node nodeDOM, int depth) {
		// First of all, extract the document of the node, and the list of
		// children
		Document document = nodeDOM.getOwnerDocument();
		NodeList children = nodeDOM.getChildNodes();

		// Flag for knowing if the current node is empty of element nodes
		boolean isEmptyOfElements = true;

		int i = 0;
		// For each children node
		while (i < children.getLength()) {
			Node currentChild = children.item(i);

			// If the current child is an element node
			if (currentChild.getNodeType() == Node.ELEMENT_NODE) {
				// Insert a indention before it, and call the recursive function
				// with the child (and a higher depth)
				nodeDOM.insertBefore(document.createTextNode("\n"
						+ getTab(depth + 1)), currentChild);
				indentDOM(currentChild, depth + 1);

				// Set empty of elements to false, and increase i (the new child
				// moves all children)
				isEmptyOfElements = false;
				i++;
			}

			// Go to next child
			i++;
		}

		// If this node has some element, add the indention for the closing tag
		if (!isEmptyOfElements)
			nodeDOM.appendChild(document.createTextNode("\n" + getTab(depth)));
	}

	/**
	 * Returns a set of tabulations, equivalent to the given numer
	 * 
	 * @param tabulations
	 *            Number of tabulations
	 */
	private String getTab(int tabulations) {
		String tab = "";
		for (int i = 0; i < tabulations; i++)
			tab += "\t";
		return tab;
	}

	public void cycleCompleted(int timerId, long elapsedTime) {
		// Do nothing
	}

	public void timerStarted(int timerId, long currentTime) {
		// Save the currentTime
		TimedAssessmentRule tRule = this.timedRules.get(new Integer(timerId));
		tRule.ruleStarted(currentTime);
		// System.out.println( "[TIMER STARTED] " + timerId + " - time:
		// "+currentTime );
	}

	public void timerStopped(int timerId, long currentTime) {
		// Get the rule
		TimedAssessmentRule tRule = this.timedRules.get(new Integer(timerId));
		tRule.ruleDone(currentTime);
		// Once the rule has been processed, remove it from the timermanager
		TimerManager.getInstance().deleteTimer(timerId);
		// System.out.println( "[TIMER DONE] " + timerId + " - time:
		// "+currentTime );
	}

	public boolean isEndOfChapterFeedbackDone() {
		// if(gameDescriptor.getChapterSummaries().get(currentChapter-1).hasAssessmentProfile(
		// )) {
		if (assessmentProfile!=null){
		if (state == STATE_NONE && assessmentProfile.isShowReportAtEnd()) {
			try {
				state = STATE_STARTED;
				int i = 0;
				File reportFile = null;
				String fileName = null;
				do {
					i++;
					fileName = "Report_" + i + ".html";
					reportFile = new File(ReleaseFolders.reportsFolder(), fileName);
				} while (reportFile.exists());
				if (!ReleaseFolders.reportsFolder().exists()) {
					ReleaseFolders.reportsFolder().mkdirs();
				}
				reportFile.createNewFile();
				final String reportAbsoluteFile = reportFile.getAbsolutePath();
				generateHTMLReport(reportFile.getAbsolutePath(), -5);

				JEditorPane reportPanel = new JEditorPane();
				reportPanel.setContentType("text/html");

				reportPanel.setPage(reportFile.toURI().toURL());
				reportPanel.setEditable(false);

				JScrollPane contentPanel = new JScrollPane(reportPanel,
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				JPanel panel = new JPanel();
				panel.setLayout(new BorderLayout());
				panel.add(contentPanel, BorderLayout.CENTER);

				JPanel buttonPanel = new JPanel();
				if (!assessmentProfile.isSendByEmail()) {
					JButton ok = new JButton("OK");
					ok.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent e) {
							GUI.getInstance().restoreFrame();
							state = STATE_DONE;
						}

					});
					buttonPanel.add(ok);
				} else {
					final JButton ok_send = new JButton(TC
							.get("Report.OKSend"));
					if (playerName == null || playerName.equals("")) {
					    ok_send.setEnabled( false );
					    JLabel label =new JLabel(TC.get("Report.Name"));
					    label.setForeground( Color.white );
					    buttonPanel.add(label);
					    JTextField nameTextField = new JTextField(30);
					    nameTextField.getDocument( ).addDocumentListener( new DocumentListener() {
                            public void changedUpdate( DocumentEvent e ) {
                                update(e);
                            }
                            public void insertUpdate( DocumentEvent e ) {
                                update(e);
                            }
                            public void removeUpdate( DocumentEvent e ) {
                                update(e);
                            }
                            private void update(DocumentEvent e) {
                                try {
                                    playerName = e.getDocument( ).getText( 0, e.getDocument( ).getLength( ));
                                }
                                catch( BadLocationException e1 ) {
                                    e1.printStackTrace();
                                }
                                if (playerName == null || playerName.equals(""))
                                    ok_send.setEnabled( false );
                                else
                                    ok_send.setEnabled( true );
                            }
					    });
					    buttonPanel.add(nameTextField);
					}
					
					
					ok_send.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							String[] to = new String[1];
							to[0] = assessmentProfile.getEmail();
							String subject = "Report eAdventure";
							String message = "Report from: " + playerName;
							
							//TODO: eliminar esto, se a–adi— para las pruebas en medicina
							File report = new File(reportAbsoluteFile);
							try {
							    FileReader fir = new FileReader(report);
	                            BufferedReader br = new BufferedReader(fir);
	                            String line = br.readLine( );
	                            String text = "";
	                            while (line != null) {
	                                text += line + "\n\r";
	                                line = br.readLine( );
	                            }
	                            es.eucm.eadventure.common.auxiliar.ReportDialog.sendReport(subject + "\n\r" + message + "\n\r" + text);
                            }
                            catch( FileNotFoundException e1 ) {
                                e1.printStackTrace();
                            }
                            catch( IOException e1 ) {
                                e1.printStackTrace();
                            }
							
							SendMail sm = new SendMail(assessmentProfile.getSmtpServer(), assessmentProfile.getSmtpUser(), assessmentProfile.getSmtpPwd());
							sm.setPort(Integer.parseInt(assessmentProfile.getSmtpPort()));
							sm.setRequiersSSL(assessmentProfile.isSmtpSSL());
							sm.postMailAttachements(to, subject, message, null,reportAbsoluteFile);
							GUI.getInstance().restoreFrame();
							state = STATE_DONE;
						}
					});
					buttonPanel.add(ok_send);
					JButton ok_dont_send = new JButton(TC
							.get("Report.OKDontSend"));
					ok_dont_send.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							GUI.getInstance().restoreFrame();
							state = STATE_DONE;
						}
					});
					buttonPanel.add(ok_dont_send);
					// buttonPanel.getRootPane().setDefaultButton(ok_send);
				}

				panel.add(buttonPanel, BorderLayout.SOUTH);

				GUI.getInstance().showComponent(panel);

				// new ReportDialog( GUI.getInstance( ).getFrame( ),
					// assessmentEngine, adventureName );
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return false;
		} else if (state == STATE_STARTED) {
		    	GUI.getInstance().componentRepaint();
			return false;
		} else if (state == STATE_DONE) {
			return true;
		} else
			return true;
		}
		return true;
	}

	public AssessmentProfile getAssessmentProfile() {
		return assessmentProfile;
	}

	/**
	 * @param playerName
	 *            the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}
