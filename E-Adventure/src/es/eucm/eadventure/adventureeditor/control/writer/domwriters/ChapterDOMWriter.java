package es.eucm.eadventure.adventureeditor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import es.eucm.eadventure.adventureeditor.data.chapterdata.Chapter;
import es.eucm.eadventure.adventureeditor.data.chapterdata.Timer;
import es.eucm.eadventure.adventureeditor.data.chapterdata.book.Book;
import es.eucm.eadventure.adventureeditor.data.chapterdata.conversation.Conversation;
import es.eucm.eadventure.adventureeditor.data.chapterdata.elements.Item;
import es.eucm.eadventure.adventureeditor.data.chapterdata.elements.NPC;
import es.eucm.eadventure.adventureeditor.data.chapterdata.scenes.Cutscene;
import es.eucm.eadventure.adventureeditor.data.chapterdata.scenes.Scene;

public class ChapterDOMWriter {

	/**
	 * Private constructor.
	 */
	private ChapterDOMWriter( ) {}

	/**
	 * Returns the DOM element for the chapter
	 * 
	 * @param chapter
	 *            Chapter data to be written
	 * @return DOM element with the chapter data
	 */
	public static Node buildDOM( Chapter chapter, String zipFile ) {
		Node chapterNode = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			chapterNode = doc.createElement( "eAdventure" );

			// Append the scene elements
			for( Scene scene : chapter.getScenes( ) ) {
				boolean initialScene = chapter.getInitialScene( ).equals( scene.getId( ) );
				Node sceneNode = SceneDOMWriter.buildDOM( scene, initialScene );
				doc.adoptNode( sceneNode );
				chapterNode.appendChild( sceneNode );
			}

			// Append the cutscene elements
			for( Cutscene cutscene : chapter.getCutscenes( ) ) {
				boolean initialScene = chapter.getInitialScene( ).equals( cutscene.getId( ) );
				Node cutsceneNode = CutsceneDOMWriter.buildDOM( cutscene, initialScene );
				doc.adoptNode( cutsceneNode );
				chapterNode.appendChild( cutsceneNode );
			}

			// Append the book elements
			for( Book book : chapter.getBooks( ) ) {
				Node bookNode = BookDOMWriter.buildDOM( book );
				doc.adoptNode( bookNode );
				chapterNode.appendChild( bookNode );
			}

			// Append the item elements
			for( Item item : chapter.getItems( ) ) {
				Node itemNode = ItemDOMWriter.buildDOM( item );
				doc.adoptNode( itemNode );
				chapterNode.appendChild( itemNode );
			}

			// Append the player element
			Node playerNode = PlayerDOMWriter.buildDOM( chapter.getPlayer( ) );
			doc.adoptNode( playerNode );
			chapterNode.appendChild( playerNode );

			// Append the character element
			for( NPC character : chapter.getCharacters( ) ) {
				Node characterNode = CharacterDOMWriter.buildDOM( character );
				doc.adoptNode( characterNode );
				chapterNode.appendChild( characterNode );
			}

			// Append the conversation element
			for( Conversation conversation : chapter.getConversations( ) ) {
				Node conversationNode = ConversationDOMWriter.buildDOM( conversation );
				doc.adoptNode( conversationNode );
				chapterNode.appendChild( conversationNode );
			}
			
			// Append the timers
			for( Timer timer : chapter.getTimers( ) ) {
				Node timerNode = TimerDOMWriter.buildDOM( timer );
				doc.adoptNode( timerNode );
				chapterNode.appendChild( timerNode );
			}

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return chapterNode;
	}
}
