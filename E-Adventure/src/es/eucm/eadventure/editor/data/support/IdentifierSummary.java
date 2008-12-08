package es.eucm.eadventure.editor.data.support;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.common.data.chapter.elements.Item;
import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.data.chapter.scenes.Cutscene;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;

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
	 * List of all global states identifiers in the script.
	 */
	private List<String> globalStateIdentifiers;
	
	/**
	 * List of all macro identifiers in the script.
	 */
	private List<String> macroIdentifiers;


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
		globalStateIdentifiers = new ArrayList<String>( );
		macroIdentifiers = new ArrayList<String>( );

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
		globalStateIdentifiers.clear( );
		macroIdentifiers.clear( );

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
		
		// Add global state IDs
		for( GlobalState globalState : chapter.getGlobalStates( ) )
			addGlobalStateId( globalState.getId( ) );
		
		// Add macro IDs
		for( Macro macro : chapter.getMacros( ) )
			addMacroId( macro.getId( ) );


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
	 * Returns an array of global state identifiers.
	 * 
	 * @return Array of global state identifiers
	 */
	public String[] getGlobalStatesIds( ) {
		return globalStateIdentifiers.toArray( new String[] {} );
	}
	
	/**
	 * Returns an array of macro identifiers.
	 * 
	 * @return Array of macro identifiers
	 */
	public String[] getMacroIds( ) {
		return macroIdentifiers.toArray( new String[] {} );
	}

	/**
	 * Returns an array of global state identifiers.
	 * 
	 * @return Array of global state identifiers
	 */
	public String[] getGlobalStatesIds( String exception ) {
		List<String> globalStateIds = new ArrayList<String>();
		for (String id: this.globalStateIdentifiers){
			if (!id.equals(exception))
				globalStateIds.add(id);
		}
		return globalStateIds.toArray( new String[] {} );
	}
	
	/**
	 * Returns an array of macro identifiers.
	 * 
	 * @return Array of macro identifiers
	 */
	public String[] getMacroIds( String exception ) {
		List<String> macroIds = new ArrayList<String>();
		for (String id: this.macroIdentifiers){
			if (!id.equals(exception))
				macroIds.add(id);
		}
		return macroIds.toArray( new String[] {} );
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
	
	/**
	 * Adds a new global state id.
	 * 
	 * @param globalStateId
	 *            New conversation id
	 */
	public void addGlobalStateId( String globalStateId ) {
		globalIdentifiers.add( globalStateId );
		globalStateIdentifiers.add( globalStateId );
	}

	/**
	 * Adds a new macro id.
	 * 
	 * @param macroId
	 *            New macro id
	 */
	public void addMacroId( String macroId ) {
		globalIdentifiers.add( macroId );
		macroIdentifiers.add( macroId );
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
	
	/**
	 * Deletes a new conversation id.
	 * 
	 * @param globalStateId
	 *            Conversation id to be deleted
	 */
	public void deleteGlobalStateId( String globalStateId ) {
		globalIdentifiers.remove( globalStateId );
		globalStateIdentifiers.remove( globalStateId );
	}
	
	/**
	 * Deletes a macro id.
	 * 
	 * @param macroId
	 *            Macro id to be deleted
	 */
	public void deleteMacroId( String macroId ) {
		globalIdentifiers.remove( macroId );
		macroIdentifiers.remove( macroId );
	}


	public void deleteAssessmentRuleId( String id ) {
		globalIdentifiers.remove( id );
		assessmentRuleIdentifiers.remove( id );
	}

	public void deleteAdaptationRuleId( String id ) {
		globalIdentifiers.remove( id );
		adaptationRuleIdentifiers.remove( id );
		
	}
	
	public boolean isGlobalStateId ( String id ){
		return globalStateIdentifiers.contains(id);
	}

	public boolean isMacroId ( String id ){
		return macroIdentifiers.contains(id);
	}

}
