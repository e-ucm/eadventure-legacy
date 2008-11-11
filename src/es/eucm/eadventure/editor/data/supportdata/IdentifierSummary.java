package es.eucm.eadventure.editor.data.supportdata;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapterdata.Chapter;
import es.eucm.eadventure.common.data.chapterdata.book.Book;
import es.eucm.eadventure.common.data.chapterdata.conversation.Conversation;
import es.eucm.eadventure.common.data.chapterdata.elements.Item;
import es.eucm.eadventure.common.data.chapterdata.elements.NPC;
import es.eucm.eadventure.common.data.chapterdata.scenes.Cutscene;
import es.eucm.eadventure.common.data.chapterdata.scenes.Scene;

/**
 * This class holds the summary of all the identifiers present on the script.
 */
public class IdentifierSummary {

	/**
	 * List of all identifiers in the script.
	 */
	private List<String> globalIdentifiers;

	/**
	 * List of all scene identifiers in the chapter (including playable scenes and cutscenes).
	 */
	private List<String> generalSceneIdentifiers;

	/**
	 * List of all ccutscene identifiers in the script.
	 */
	private List<String> sceneIdentifiers;

	/**
	 * List of all identifiers of cutscenes in the script.
	 */
	private List<String> cutsceneIdentifiers;

	/**
	 * List of all book identifiers in the script.
	 */
	private List<String> bookIdentifiers;

	/**
	 * List of all item identifiers in the script.
	 */
	private List<String> itemIdentifiers;

	/**
	 * List of all NPC identifiers in the script.
	 */
	private List<String> npcIdentifiers;

	/**
	 * List of all conversation identifiers in the script.
	 */
	private List<String> conversationIdentifiers;
	
	/**
	 * List of all assessment rule identifiers in the script.
	 */
	private List<String> assessmentRuleIdentifiers;
	
	/**
	 * List of all adaptation rule identifiers in the script.
	 */
	private List<String> adaptationRuleIdentifiers;

	

	/**
	 * Constructor.
	 * 
	 * @param chapter
	 *            Chapter data which will provide the identifiers
	 */
	public IdentifierSummary( Chapter chapter ) {

		// Create the lists
		globalIdentifiers = new ArrayList<String>( );
		generalSceneIdentifiers = new ArrayList<String>( );
		sceneIdentifiers = new ArrayList<String>( );
		cutsceneIdentifiers = new ArrayList<String>( );
		bookIdentifiers = new ArrayList<String>( );
		itemIdentifiers = new ArrayList<String>( );
		npcIdentifiers = new ArrayList<String>( );
		conversationIdentifiers = new ArrayList<String>( );
		assessmentRuleIdentifiers = new ArrayList<String>( );
		adaptationRuleIdentifiers = new ArrayList<String>( );

		// Fill all the lists
		loadIdentifiers( chapter );
	}

	/**
	 * Reloads the identifiers with the given chapter data.
	 * 
	 * @param chapter
	 *            Chapter data which will provide the identifiers
	 */
	public void loadIdentifiers( Chapter chapter ) {

		// Clear the lists
		globalIdentifiers.clear( );
		generalSceneIdentifiers.clear( );
		sceneIdentifiers.clear( );
		cutsceneIdentifiers.clear( );
		bookIdentifiers.clear( );
		itemIdentifiers.clear( );
		npcIdentifiers.clear( );
		conversationIdentifiers.clear( );

		// Add scene IDs
		for( Scene scene : chapter.getScenes( ) )
			addSceneId( scene.getId( ) );

		// Add cutscene IDs
		for( Cutscene cutscene : chapter.getCutscenes( ) )
			addCutsceneId( cutscene.getId( ) );

		// Add book IDs
		for( Book book : chapter.getBooks( ) )
			addBookId( book.getId( ) );

		// Add item IDs
		for( Item item : chapter.getItems( ) )
			addItemId( item.getId( ) );

		// Add NPC IDs
		for( NPC npc : chapter.getCharacters( ) )
			addNPCId( npc.getId( ) );

		// Add conversation IDs
		for( Conversation conversation : chapter.getConversations( ) )
			addConversationId( conversation.getId( ) );
	}

	/**
	 * Returns if the given id exists or not.
	 * 
	 * @param id
	 *            Id to be checked
	 * @return True if the id exists, false otherwise
	 */
	public boolean existsId( String id ) {
		return globalIdentifiers.contains( id );
	}

	/**
	 * Returns whether the given identifier is a scene or not.
	 * 
	 * @param sceneId
	 *            Scene identifier
	 * @return True if the identifier belongs to a scene, false otherwise
	 */
	public boolean isScene( String sceneId ) {
		return sceneIdentifiers.contains( sceneId );
	}

	/**
	 * Returns whether the given identifier is a conversation or not.
	 * 
	 * @param sceneId
	 *            Scene identifier
	 * @return True if the identifier belongs to a scene, false otherwise
	 */
	public boolean isConversation( String convId ) {
		return conversationIdentifiers.contains( convId );
	}


	/**
	 * Returns an array of general scene identifiers.
	 * 
	 * @return Array of general scene identifiers
	 */
	public String[] getGeneralSceneIds( ) {
		return generalSceneIdentifiers.toArray( new String[] {} );
	}

	/**
	 * Returns an array of scene identifiers.
	 * 
	 * @return Array of scene identifiers
	 */
	public String[] getSceneIds( ) {
		return sceneIdentifiers.toArray( new String[] {} );
	}

	/**
	 * Returns an array of cutscene identifiers.
	 * 
	 * @return Array of cutscene identifiers
	 */
	public String[] getCutsceneIds( ) {
		return cutsceneIdentifiers.toArray( new String[] {} );
	}

	/**
	 * Returns an array of book identifiers.
	 * 
	 * @return Array of book identifiers
	 */
	public String[] getBookIds( ) {
		return bookIdentifiers.toArray( new String[] {} );
	}

	/**
	 * Returns an array of item identifiers.
	 * 
	 * @return Array of item identifiers
	 */
	public String[] getItemIds( ) {
		return itemIdentifiers.toArray( new String[] {} );
	}

	/**
	 * Returns an array of NPC identifiers.
	 * 
	 * @return Array of NPC identifiers
	 */
	public String[] getNPCIds( ) {
		return npcIdentifiers.toArray( new String[] {} );
	}

	/**
	 * Returns an array of conversation identifiers.
	 * 
	 * @return Array of conversation identifiers
	 */
	public String[] getConversationsIds( ) {
		return conversationIdentifiers.toArray( new String[] {} );
	}

	/**
	 * Adds a new scene id.
	 * 
	 * @param sceneId
	 *            New scene id
	 */
	public void addSceneId( String sceneId ) {
		globalIdentifiers.add( sceneId );
		generalSceneIdentifiers.add( sceneId );
		sceneIdentifiers.add( sceneId );
	}

	/**
	 * Adds a new cutscene id.
	 * 
	 * @param cutsceneId
	 *            New cutscene id
	 */
	public void addCutsceneId( String cutsceneId ) {
		globalIdentifiers.add( cutsceneId );
		generalSceneIdentifiers.add( cutsceneId );
		cutsceneIdentifiers.add( cutsceneId );
	}

	/**
	 * Adds a new book id.
	 * 
	 * @param bookId
	 *            New book id
	 */
	public void addBookId( String bookId ) {
		globalIdentifiers.add( bookId );
		bookIdentifiers.add( bookId );
	}

	/**
	 * Adds a new item id.
	 * 
	 * @param itemId
	 *            New item id
	 */
	public void addItemId( String itemId ) {
		globalIdentifiers.add( itemId );
		itemIdentifiers.add( itemId );
	}

	/**
	 * Adds a new NPC id.
	 * 
	 * @param npcId
	 *            New NPC id
	 */
	public void addNPCId( String npcId ) {
		globalIdentifiers.add( npcId );
		npcIdentifiers.add( npcId );
	}

	/**
	 * Adds a new conversation id.
	 * 
	 * @param conversationId
	 *            New conversation id
	 */
	public void addConversationId( String conversationId ) {
		globalIdentifiers.add( conversationId );
		conversationIdentifiers.add( conversationId );
	}
	
	public void addAssessmentRuleId( String assRuleId ) {
		globalIdentifiers.add( assRuleId );
		this.assessmentRuleIdentifiers.add( assRuleId );
	}
	
	public void addAdaptationRuleId( String adpRuleId ) {
		globalIdentifiers.add( adpRuleId );
		this.adaptationRuleIdentifiers.add( adpRuleId );
	}


	/**
	 * Deletes a new scene id.
	 * 
	 * @param sceneId
	 *            Scene id to be deleted
	 */
	public void deleteSceneId( String sceneId ) {
		globalIdentifiers.remove( sceneId );
		generalSceneIdentifiers.remove( sceneId );
		sceneIdentifiers.remove( sceneId );
	}

	/**
	 * Deletes a new cutscene id.
	 * 
	 * @param cutsceneId
	 *            Cutscene id to be deleted
	 */
	public void deleteCutsceneId( String cutsceneId ) {
		globalIdentifiers.remove( cutsceneId );
		generalSceneIdentifiers.remove( cutsceneId );
		cutsceneIdentifiers.remove( cutsceneId );
	}

	/**
	 * Deletes a new book id.
	 * 
	 * @param bookId
	 *            Book id to be deleted
	 */
	public void deleteBookId( String bookId ) {
		globalIdentifiers.remove( bookId );
		bookIdentifiers.remove( bookId );
	}

	/**
	 * Deletes a new item id.
	 * 
	 * @param itemId
	 *            Item id to be deleted
	 */
	public void deleteItemId( String itemId ) {
		globalIdentifiers.remove( itemId );
		itemIdentifiers.remove( itemId );
	}

	/**
	 * Deletes a new NPC id.
	 * 
	 * @param npcId
	 *            NPC id to be deleted
	 */
	public void deleteNPCId( String npcId ) {
		globalIdentifiers.remove( npcId );
		npcIdentifiers.remove( npcId );
	}

	/**
	 * Deletes a new conversation id.
	 * 
	 * @param conversationId
	 *            Conversation id to be deleted
	 */
	public void deleteConversationId( String conversationId ) {
		globalIdentifiers.remove( conversationId );
		conversationIdentifiers.remove( conversationId );
	}

	public void deleteAssessmentRuleId( String id ) {
		globalIdentifiers.remove( id );
		assessmentRuleIdentifiers.remove( id );
	}

	public void deleteAdaptationRuleId( String id ) {
		globalIdentifiers.remove( id );
		adaptationRuleIdentifiers.remove( id );
		
	}

}
