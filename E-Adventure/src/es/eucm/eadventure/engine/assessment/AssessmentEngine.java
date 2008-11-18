package es.eucm.eadventure.engine.assessment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.assessment.TimedAssessmentRule;
import es.eucm.eadventure.engine.core.control.Game;
import es.eucm.eadventure.engine.core.control.TimerEventListener;
import es.eucm.eadventure.engine.core.control.TimerManager;
import es.eucm.eadventure.engine.core.control.functionaldata.FunctionalConditions;
import es.eucm.eadventure.engine.loader.Loader;

/**
 * This engine stores the rules to be processed when the flags change in the game, creating
 * events that tell the process of the player in the game 
 */
public class AssessmentEngine implements TimerEventListener{

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
    
    /**
     * Constructor
     */
    public AssessmentEngine( ) {
        processedRules = new ArrayList<ProcessedRule>( );
        timedRules = new HashMap<Integer, TimedAssessmentRule>();
    }

    /**
     * Loads a set of assessment rules
     * @param assessmentPath Path of the file containing the assessment data
     */
    public void loadAssessmentRules( String assessmentPath ) {
        assessmentRules = Loader.loadAssessmentRules( assessmentPath );
        
        // Iterate through the rules: those timed add them to the timer manager 
        for (AssessmentRule assessmentRule: assessmentRules){
            if (assessmentRule instanceof TimedAssessmentRule){
                TimedAssessmentRule tRule = (TimedAssessmentRule)assessmentRule;
                int id = TimerManager.getInstance().addTimer( tRule.getInitConditions( ), tRule.getEndConditions( ), this );
                timedRules.put( new Integer(id), tRule );
            }
        }
        processRules( );
    }
    
    /**
     * Process the rules, triggering them if necessary
     */
    public void processRules( ) {
        int i = 0;
        
        // For every rule
        while( i < assessmentRules.size( ) ) {
            
            // If it was activated, execute the rule
            if( isActive( assessmentRules.get( i ) ) ) {
                AssessmentRule oldRule = assessmentRules.remove( i );
                ProcessedRule rule = new ProcessedRule( oldRule , Game.getInstance( ).getTime( ) );
                
                //Signal the LMS about the change
                if(Game.getInstance( ).isConnected( )) {
                    Game.getInstance( ).getComm( ).notifyRelevantState( oldRule.getAssessmentProperties( ) );
                }
                processedRules.add( rule );
            }
            
            // Else, check the next rule
            else
                i++;
        }
    }
    
    private static boolean isActive ( AssessmentRule rule ){
    	return new FunctionalConditions(rule.getConditions()).allConditionsOk( );
    }
    
    /**
     * Generates a report file, in XML format
     * @param filename File name of the report file
     */
    public void generateXMLReport( String filename ) {
        try {
            // Create the necessary elements for building the DOM
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
            DocumentBuilder db = dbf.newDocumentBuilder( );
            Document doc = db.newDocument( );

            // Create the root element, "report"
            Element report = doc.createElement( "report" );
            
            // For each processed rule
            for( ProcessedRule rule : processedRules ) {
                // Create a new "processed-rule" node (DOM element), and link it to the document
                Element processedRule = rule.getDOMStructure( );
                doc.adoptNode( processedRule );

                // Add the node
                report.appendChild( processedRule );
            }
            
            // Add the report structure to the XML file
            doc.appendChild( report );

            // Indent the DOM
            indentDOM( report, 0 );

            // Create the necessary elements for export the DOM into a XML file
            TransformerFactory tFactory = TransformerFactory.newInstance( );
            Transformer transformer = tFactory.newTransformer( );

            // Create the output buffer, write the DOM and close it
            OutputStream fout = new FileOutputStream( filename );
            OutputStreamWriter writeFile = new OutputStreamWriter( fout, "UTF-8" );
            transformer.transform( new DOMSource( doc ), new StreamResult( writeFile ) );
            writeFile.close( );
            
        } catch( IOException exception ) {
            exception.printStackTrace( );
        } catch( ParserConfigurationException exception ) {
            exception.printStackTrace( );
        } catch( TransformerConfigurationException exception ) {
            exception.printStackTrace( );
        } catch( TransformerException exception ) {
            exception.printStackTrace( );
        }
    }
    
    /**
     * Generates a report file, in HTML format
     * @param filename File name of the report file
     * @param minImportance Importance value for filtering
     */
    public void generateHTMLReport( String filename, int minImportance ) {
        try {
            // Create the file to write
            PrintStream file = new PrintStream( new FileOutputStream( filename ) );
            
            // HTML tag
            file.println( "<html>" );
            
            // Header
            file.print( "<title>" );
            file.print( Game.getInstance( ).getGameDescriptor( ).getTitle( ) );
            file.println( "</title>" );
            
            // Body and content table
            file.println( "<body style=\"background: #4386CE;\">" );
            file.println( "<br/><br/>" );
            file.println( "<table width=\"80%\" align=\"center\" style=\"background : #C1D6EA; border : 1px solid #000000;\">" );
            file.println( "<tr><td>" );
            
            // Title
            file.print( "<center><h1>" );
            file.print( Game.getInstance( ).getGameDescriptor( ).getTitle( ) );
            file.print( " report" );
            file.println( "</h1></center>" );

            // Clear table
            file.println( "<br/><br/>" );
            file.println( "<table width=\"95%\" align=\"center\" style=\"background : #EEF6FE; border : 1px solid #000000\">" );
            file.println( "<tr><td>" );
            
            // For each processed rule
            for( ProcessedRule rule : processedRules ) {
                // First check the importance
                if( rule.getImportance( ) >= minImportance ) {
                    file.println( rule.getHTMLCode( ) );
                    file.println( "<br/><br/>" );
                }
            }
            
            // Close clear table
            file.println( "</td></tr>" );
            file.println( "</table>" );
            
            // Close table and body
            file.println( "<br/><br/>" );            
            file.println( "</td></tr>" );
            file.println( "</table>" );
            file.println( "</body>" );
            
            // Close HTML
            file.println( "</html>" );
 
            // Close the file
            file.close( );
            
        } catch( FileNotFoundException e ) {
            e.printStackTrace();
        }
    }
    
    /**
     * Indent the given DOM node recursively with the given depth
     * @param nodeDOM DOM node to be indented
     * @param depth Depth of the current node
     */
    private void indentDOM( Node nodeDOM, int depth ) {
        // First of all, extract the document of the node, and the list of children
        Document document = nodeDOM.getOwnerDocument( );
        NodeList children = nodeDOM.getChildNodes( );
        
        // Flag for knowing if the current node is empty of element nodes
        boolean isEmptyOfElements = true;
        
        int i = 0;
        // For each children node
        while( i < children.getLength( ) ) {
            Node currentChild = children.item( i );
            
            // If the current child is an element node
            if( currentChild.getNodeType( ) == Node.ELEMENT_NODE ) {
                // Insert a indention before it, and call the recursive function with the child (and a higher depth)
                nodeDOM.insertBefore( document.createTextNode( "\n" + getTab( depth + 1 ) ), currentChild );
                indentDOM( currentChild, depth + 1 );
                
                // Set empty of elements to false, and increase i (the new child moves all children) 
                isEmptyOfElements = false;
                i++;
            }
            
            // Go to next child
            i++;
        }
        
        // If this node has some element, add the indention for the closing tag
        if( !isEmptyOfElements )
            nodeDOM.appendChild( document.createTextNode( "\n" + getTab( depth ) ) );
    }
    
    /**
     * Returns a set of tabulations, equivalent to the given numer
     * @param tabulations Number of tabulations
     */
    private String getTab( int tabulations ) {
        String tab = "";        
        for( int i = 0; i < tabulations; i++ )
            tab += "\t";        
        return tab;
    }

    public void cycleCompleted( int timerId, long elapsedTime ) {
        // Do nothing
    }

    public void timerStarted( int timerId, long currentTime ) {
        // Save the currentTime
        TimedAssessmentRule tRule = this.timedRules.get( new Integer(timerId) );
        tRule.ruleStarted( currentTime );
        //System.out.println( "[TIMER STARTED] " + timerId + " - time: "+currentTime );
    }

    public void timerStopped( int timerId, long currentTime ) {
        // Get the rule
        TimedAssessmentRule tRule = this.timedRules.get( new Integer(timerId) );
        tRule.ruleDone( currentTime );
        // Once the rule has been processed, remove it from the timermanager
        TimerManager.getInstance( ).deleteTimer( timerId );
        //System.out.println( "[TIMER DONE] " + timerId + " - time: "+currentTime );
    }
}
