package es.eucm.eadventure.editor.control.writer.domwriters.lom;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.editor.data.lom.LOMEducational;
import es.eucm.eadventure.editor.data.lom.LOMGeneral;


public class LOMEducationalDOMWriter extends LOMSimpleDataWriter{

	/**
	 * Private constructor.
	 */
	private LOMEducationalDOMWriter( ) {}

	public static Node buildDOM( LOMEducational educational ) {
		Element educationalElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			educationalElement = doc.createElement( "imsmd:educational" );
			
			//Create the interactivity type node
			Node intType = buildVocabularyNode(doc, "interactivitytype", educational.getInteractivityType( ));
			educationalElement.appendChild( intType );

			//Create the learning resource type
			Node lrType = buildVocabularyNode(doc, "learningresourcetype", educational.getLearningResourceType( ));
			educationalElement.appendChild( lrType );

			
			//Create the interactivity level node
			Node intLevel = buildVocabularyNode(doc, "interactivitylevel", educational.getInteractivityLevel( ));
			educationalElement.appendChild( intLevel );
			
			//Create the semantic density node
			Node semanticDensity = buildVocabularyNode(doc, "semanticdensity", educational.getSemanticDensity( ));
			educationalElement.appendChild( semanticDensity );
			
			//Create the intended end user role node
			Node ieuRole = buildVocabularyNode(doc, "intendedenduserrole", educational.getIntendedEndUserRole( ));
			educationalElement.appendChild( ieuRole );
			
			//Create the context node
			Node context = buildVocabularyNode(doc, "context", educational.getContext( ));
			educationalElement.appendChild( context );
			
			//Create the typical age range node
			if (isStringSet(educational.getTypicalAgeRange( ))){
				Element taRange = doc.createElement( "imsmd:typicalagerange" );
				taRange.appendChild( buildLangStringNode(doc, educational.getTypicalAgeRange( )) );
				educationalElement.appendChild( taRange );
			}

			//Create the difficulty node
			Node difficulty = buildVocabularyNode(doc, "difficulty", educational.getDifficulty( ));
			educationalElement.appendChild( difficulty );

			//Create the typical learning time node
			Node tlTime = doc.createElement( "imsmd:typicallearningtime" );
			Node dateTime = doc.createElement( "datetime" );
			dateTime.setTextContent( educational.getTypicalLearningTime( ) );
			tlTime.appendChild( dateTime );
			educationalElement.appendChild( tlTime );

			
			//Create the language node
			if (isStringSet(educational.getLanguage( ))){
				Element language = doc.createElement( "imsmd:language" );
				language.setTextContent(educational.getLanguage( ));
				educationalElement.appendChild( language );
			}
			
			//Create the description node
			if (isStringSet(educational.getDescription( ))){
				Element description = doc.createElement( "imsmd:description" );
				description.appendChild( buildLangStringNode(doc, educational.getDescription( )));
				educationalElement.appendChild( description );
			}
			
			
			//Create the language node
			if (isStringSet(educational.getLanguage( ))){
				Element language = doc.createElement( "imsmd:language" );
				language.setTextContent(educational.getLanguage( ));
				educationalElement.appendChild( language );
			}

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return educationalElement;
	}
}
