package es.eucm.eadventure.editor.control.writer.domwriters;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.conversation.GraphConversation;
import es.eucm.eadventure.common.data.chapter.conversation.TreeConversation;
import es.eucm.eadventure.common.data.chapter.conversation.line.ConversationLine;
import es.eucm.eadventure.common.data.chapter.conversation.node.ConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.DialogueConversationNode;
import es.eucm.eadventure.common.data.chapter.conversation.node.OptionConversationNode;

public class ConversationDOMWriter {

	/**
	 * Private constructor.
	 */
	private ConversationDOMWriter( ) {}

	public static Node buildDOM( Conversation conversation ) {
		Node conversationNode = null;

		if( conversation.getType( ) == Conversation.TREE )
			conversationNode = buildTreeConversationDOM( (TreeConversation) conversation );
		else if( conversation.getType( ) == Conversation.GRAPH )
			conversationNode = buildGraphConversationDOM( (GraphConversation) conversation );

		return conversationNode;
	}

	protected static Node buildTreeConversationDOM( TreeConversation treeConversation ) {
		Element rootNode = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			rootNode = doc.createElement( "tree-conversation" );

			// Set the identification attribute of the new conversation, and its type
			rootNode.setAttribute( "id", treeConversation.getId( ) );

			// Call the recursive function that will create the nodes. We pass the root of the tree, the DOM document,
			// the root DOM node that will be used to add the elements, and a depth level for indentation (2 by default)
			writeNodeInDOM( treeConversation.getRootNode( ), rootNode );

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return rootNode;
	}

	/**
	 * Recursive function responsible for transforming a node (and its children) into a DOM structure
	 * 
	 * @param currentNode
	 *            Node to be transformed
	 * @param rootDOMNode
	 *            DOM node in which the elements must be attached
	 */
	private static void writeNodeInDOM( ConversationNode currentNode, Node rootDOMNode ) {

		// Extract the document
		Document document = rootDOMNode.getOwnerDocument( );

		// If the node is a DialogueNode write the lines one after another, and then the child (or the mark if it is no
		// child)
		if( currentNode.getType( ) == ConversationNode.DIALOGUE ) {

			// For each line of the node
			for( int i = 0; i < currentNode.getLineCount( ); i++ ) {
				// Create a phrase element, and extract the actual text line
				Element phrase;
				Node conditionsNode = null;
				ConversationLine line = currentNode.getLine( i );

				// If the line belongs to the player, create a "speak-player" element. Otherwise, if it belongs to a
				// NPC,
				// create a "speak-char" element, which will have an attribute "idTarget" with the name of the
				// non-playable character,
				// if there is no name the attribute won't be written
				if( line.isPlayerLine( ) ) {
					phrase = document.createElement( "speak-player" );
				} else {
					phrase = document.createElement( "speak-char" );
					if( !line.getName( ).equals( "NPC" ) )
						phrase.setAttribute( "idTarget", line.getName( ) );
				}
				
				// Add the line text into the element
				phrase.setTextContent( line.getText( ) );
				
				//If there is audio track, store it as attribute
				if( line.isValidAudio( ) )
					phrase.setAttribute( "uri", line.getAudioPath( ) );
				//If there is a synthesizer valid voice, store it as attribute
				if (line.getSynthesizerVoice())
					phrase.setAttribute( "synthesize", "yes");
				
				
				// Add the element to the DOM root
				rootDOMNode.appendChild( phrase );
				
				// Create conditions for current effect
				conditionsNode = ConditionsDOMWriter.buildDOM(line.getConditions());
				document.adoptNode( conditionsNode );
				
				// Add conditions associated to that effect
				rootDOMNode.appendChild(conditionsNode);
			}

			
			//TODO MODIFIED
			if( currentNode.hasEffects( ) ) {
				// Extract the root node
				Node effect = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, currentNode.getEffects( ) );

				// Insert it into the DOM
				document.adoptNode( effect );
				rootDOMNode.appendChild( effect );
			}
 
			
			// Check if the node is terminal
			if( currentNode.isTerminal( ) ) {
				// If it is terminal add a "end-conversation" element
				Element endConversation = document.createElement( "end-conversation" );

				// If the terminal node has an effect, include it into the DOM
				if( currentNode.hasEffects( ) ) {
					// Extract the root node
					Node effect = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, currentNode.getEffects( ) );

					// Insert it into the DOM
					document.adoptNode( effect );
					endConversation.appendChild( effect );
				}

				// Add the "end-conversation" tag into the root
				rootDOMNode.appendChild( endConversation );
			} else {
				// If the node isn't terminal, check if it performing a "go-back" (going back to the inmediatly upper
				// OptionNode)
				if( TreeConversation.thereIsGoBackTag( currentNode ) ) {
					// If it is the case, add a "go-back" element
					rootDOMNode.appendChild( document.createElement( "go-back" ) );
				} else {
					// Otherwise, if the node has a child, call the recursive function with the child node, and the same
					// DOM root node
					writeNodeInDOM( currentNode.getChild( 0 ), rootDOMNode );
				}
			}
		}

		// If the node is a OptionNode write a "response" element, and inside it a "option" element with its content
		else if( currentNode.getType( ) == ConversationNode.OPTION ) {
			// Create the "response" element
			Element response = document.createElement( "response" );
			// Adds a random attribute if "random" is activate in conversation node data
			if (((OptionConversationNode)currentNode).isRandom())
				response.setAttribute("random", "yes");
			// For each line of the node (we suppose the number of line equals the number of links, or children nodes)
			for( int i = 0; i < currentNode.getLineCount( ); i++ ) {
				// Create the "option" element
				Element optionElement = document.createElement( "option" );
				ConversationLine line = currentNode.getLine( i );
				// Create the actual option (a "speak-player" element) and add its respective text
				Element lineElement = document.createElement( "speak-player" );
				lineElement.setTextContent( currentNode.getLine( i ).getText( ) );

				//If there is audio track, store it as attribute
				if( line.isValidAudio( ) )
					lineElement.setAttribute( "uri", line.getAudioPath( ) );
				//If there is a synthesizer valid voice, store it as attribute
				if (line.getSynthesizerVoice())
					lineElement.setAttribute( "synthesize", "yes");
				
				
				// Insert the text line in the option node
				optionElement.appendChild( lineElement );

				// Call the recursive function, to write in the "option" node the appropiate elements
				// Note that the root DOM node is the "option" element
				writeNodeInDOM( currentNode.getChild( i ), optionElement );

				// Add the "option" element
				response.appendChild( optionElement );
			}
			// If the terminal node has an effect, include it into the DOM
			if( currentNode.hasEffects( ) ) {
				// Extract the root node
				Node effect = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, currentNode.getEffects( ) );

				// Insert it into the DOM
				document.adoptNode( effect );
				response.appendChild( effect );
			}

			// Add the element
			rootDOMNode.appendChild( response );
		}
	}

	private static Node buildGraphConversationDOM( GraphConversation graphConversation ) {
		Element conversationElement = null;

		try {
			// Get the complete node list
			List<ConversationNode> nodes = graphConversation.getAllNodes( );

			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			conversationElement = doc.createElement( "graph-conversation" );
			conversationElement.setAttribute( "id", graphConversation.getId( ) );

			// For each node
			for( int i = 0; i < nodes.size( ); i++ ) {
				ConversationNode node = nodes.get( i );

				Element nodeElement = null;

				// If the node is a dialogue node
				if( node instanceof DialogueConversationNode ) {

					// Create the node element and set the nodeindex
					nodeElement = doc.createElement( "dialogue-node" );
					nodeElement.setAttribute( "nodeindex", String.valueOf( i ) );

					// For each line of the node
					for( int j = 0; j < node.getLineCount( ); j++ ) {
						// Create a phrase element, and extract the actual text line
						Element phrase;
						ConversationLine line = node.getLine( j );

						// If the line belongs to the player, create a "speak-player" element. Otherwise, if it belongs
						// to a NPC,
						// create a "speak-char" element, which will have an attribute "idTarget" with the name of the
						// non-playable character,
						// if there is no name the attribute won't be written
						if( line.isPlayerLine( ) ) {
							phrase = doc.createElement( "speak-player" );
						} else {
							phrase = doc.createElement( "speak-char" );
							if( !line.getName( ).equals( "NPC" ) )
								phrase.setAttribute( "idTarget", line.getName( ) );
						}
						
						//If there is audio track, store it as attribute
						if( line.isValidAudio( ) )
							phrase.setAttribute( "uri", line.getAudioPath( ) );
						//If there is a synthesizer valid voice, store it as attribute
						if (line.getSynthesizerVoice())
							phrase.setAttribute( "synthesize", "yes");
						
						// Add the line text into the element
						phrase.setTextContent( line.getText( ) );

						// Add the element to the node
						nodeElement.appendChild( phrase );
					}

					// Check if the node is terminal
					if( node.isTerminal( ) ) {
						// If it is terminal add a "end-conversation" element
						Element endConversation = doc.createElement( "end-conversation" );

						// If the terminal node has an effect, include it into the DOM
						if( node.hasEffects( ) ) {
							// Extract the root node
							Node effect = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, node.getEffects( ) );

							// Insert it into the DOM
							doc.adoptNode( effect );
							endConversation.appendChild( effect );
						}

						// Add the "end-conversation" tag into the node
						nodeElement.appendChild( endConversation );
					} else {
						// Otherwise, if the node has a child, add the element
						Element childElement = doc.createElement( "child" );

						// Add the number of the child node (index of the node in the structure)
						childElement.setAttribute( "nodeindex", String.valueOf( nodes.indexOf( node.getChild( 0 ) ) ) );

						// Insert the tag into the node
						nodeElement.appendChild( childElement );

						// TODO MODIFIED
						// If the terminal node has an effect, include it into the DOM
						if( node.hasEffects( ) ) {
							// Extract the root node
							Node effect = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, node.getEffects( ) );

							// Insert it into the DOM
							doc.adoptNode( effect );
							nodeElement.appendChild( effect );
						}

					}
				}

				// If the node is a option node
				if( node instanceof OptionConversationNode ) {

					// Create the node element and set the nodeindex
					nodeElement = doc.createElement( "option-node" );
					nodeElement.setAttribute( "nodeindex", String.valueOf( i ) );
					// Adds a random attribute if "random" is activate in conversation node data
					if (((OptionConversationNode)node).isRandom())
						nodeElement.setAttribute("random", "yes");

					// For each line of the node
					for( int j = 0; j < node.getLineCount( ); j++ ) {
						// Take the current conversation line
						ConversationLine line = node.getLine( j );
						
						// Create the actual option (a "speak-player" element) and add its respective text
						Element lineElement = doc.createElement( "speak-player" );
						lineElement.setTextContent( node.getLine( j ).getText( ) );
						
						//If there is audio track, store it as attribute
						if( line.isValidAudio( ) )
							lineElement.setAttribute( "uri", line.getAudioPath( ) );
						//If there is a synthesizer valid voice, store it as attribute
						if (line.getSynthesizerVoice())
							lineElement.setAttribute( "synthesize", "yes");

						// Create a child tag, and set it the index of the child
						Element childElement = doc.createElement( "child" );
						childElement.setAttribute( "nodeindex", String.valueOf( nodes.indexOf( node.getChild( j ) ) ) );

						// Insert the text line in the option node
						nodeElement.appendChild( lineElement );
						nodeElement.appendChild( childElement );
					}
					// If node has an effect, include it into the DOM
					if( node.hasEffects( ) ) {
						// Extract the root node
						Node effect = EffectsDOMWriter.buildDOM( EffectsDOMWriter.EFFECTS, node.getEffects( ) );

						// Insert it into the DOM
						doc.adoptNode( effect );
						nodeElement.appendChild( effect );
					}
				}

				// Add the node to the conversation
				conversationElement.appendChild( nodeElement );
			}
		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return conversationElement;
	}
}
