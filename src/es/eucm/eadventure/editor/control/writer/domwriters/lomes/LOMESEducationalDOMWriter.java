package es.eucm.eadventure.editor.control.writer.domwriters.lomes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.editor.data.meta.ims.IMSEducational;
import es.eucm.eadventure.editor.data.meta.lomes.LOMESEducational;


public class LOMESEducationalDOMWriter extends LOMESSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private LOMESEducationalDOMWriter( ) {}

	public static Node buildDOM( LOMESEducational educational ) {
		Element educationalElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			educationalElement = doc.createElement( "lomes:educational" );
			
			//Create the interactivity type node
			Node intType = buildVocabularyNode(doc, "lomes:interactivityType", educational.getInteractivityType( ));
			educationalElement.appendChild( intType );

			//Create the learning resource type
			Node lrType = buildVocabularyNode(doc, "lomes:learningResourceType", educational.getLearningResourceType( ));
			educationalElement.appendChild( lrType );

			
			//Create the interactivity level node
			Node intLevel = buildVocabularyNode(doc, "lomes:interactivityLevel", educational.getInteractivityLevel( ));
			educationalElement.appendChild( intLevel );
			
			//Create the semantic density node
			Node semanticDensity = buildVocabularyNode(doc, "lomes:semanticDensity", educational.getSemanticDensity( ));
			educationalElement.appendChild( semanticDensity );
			
			//Create the intended end user role node
			Node ieuRole = buildVocabularyNode(doc, "lomes:intendededEndUserRole", educational.getIntendedEndUserRole( ));
			educationalElement.appendChild( ieuRole );
			
			//Create the context node
			Node context = buildVocabularyNode(doc, "lomes:context", educational.getContext( ));
			educationalElement.appendChild( context );
			
			//Create the typical age range node
			if (isStringSet(educational.getTypicalAgeRange( ))){
				Element taRange = doc.createElement( "lomes:typicalAgeRange" );
				taRange.appendChild( buildLangStringNode(doc, educational.getTypicalAgeRange( )) );
				educationalElement.appendChild( taRange );
			}

			//Create the difficulty node
			Node difficulty = buildVocabularyNode(doc, "lomes:difficulty", educational.getDifficulty( ));
			educationalElement.appendChild( difficulty );

			//Create the typical learning time node
			Node tlTime = doc.createElement( "lomes:typicalLearningTime" );
			Node dateTime = doc.createElement( "lomes:dateTime" );
			dateTime.setTextContent( educational.getTypicalLearningTime( ) );
			tlTime.appendChild( dateTime );
			educationalElement.appendChild( tlTime );
			
			//Create the description node
			if (isStringSet(educational.getDescription( ))){
				Element description = doc.createElement( "lomes:description" );
				description.appendChild( buildLangStringNode(doc, educational.getDescription( )));
				educationalElement.appendChild( description );
			}
			
			
			//Create the language node
			if (isStringSet(educational.getLanguage( ))){
				Element language = doc.createElement( "lomes:language" );
				language.setTextContent(educational.getLanguage( ));
				educationalElement.appendChild( language );
			}
			
			//create cognitive process node
			Node cognitive = buildVocabularyNode(doc, "cognitiveProcess", educational.getCognitiveProcess());
			educationalElement.appendChild( cognitive );


		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return educationalElement;
	}
}