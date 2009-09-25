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
package es.eucm.eadventure.editor.data.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.adaptation.AdaptationRule;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.assessment.AssessmentRule;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalState;
import es.eucm.eadventure.common.data.chapter.conversation.Conversation;
import es.eucm.eadventure.common.data.chapter.effects.Macro;
import es.eucm.eadventure.common.data.chapter.elements.ActiveArea;
import es.eucm.eadventure.common.data.chapter.elements.Atrezzo;
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
     * List of all scene identifiers in the chapter (including playable scenes
     * and cutscenes).
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
     * List of all atrezzo item identifiers in the script.
     */
    private List<String> atrezzoIdentifiers;

    /**
     * List of all NPC identifiers in the script.
     */
    private List<String> npcIdentifiers;

    /**
     * List of all conversation identifiers in the script.
     */
    private List<String> conversationIdentifiers;

    /**
     * List of all assessment profile identifiers in the script and its associated assessment rules. 
     */
    private Map<String,List<String>> assessmentIdentifiers;

    /**
     * List of all adaptation profile identifiers in the script and its associated adaptation rules.
     */
    private Map<String,List<String>> adaptationIdentifiers;
    
    /**
     * List of all global states identifiers in the script.
     */
    private List<String> globalStateIdentifiers;

    /**
     * List of all macro identifiers in the script.
     */
    private List<String> macroIdentifiers;

    private List<String> activeAreaIdentifiers;

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
        atrezzoIdentifiers = new ArrayList<String>( );
        npcIdentifiers = new ArrayList<String>( );
        conversationIdentifiers = new ArrayList<String>( );
        assessmentIdentifiers = new HashMap<String,List<String>>( );
        adaptationIdentifiers = new HashMap<String,List<String>>( );
        globalStateIdentifiers = new ArrayList<String>( );
        macroIdentifiers = new ArrayList<String>( );
        activeAreaIdentifiers = new ArrayList<String>( );
        
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
        atrezzoIdentifiers.clear( );
        npcIdentifiers.clear( );
        conversationIdentifiers.clear( );
        globalStateIdentifiers.clear( );
        macroIdentifiers.clear( );
        activeAreaIdentifiers.clear( );
        assessmentIdentifiers.clear( );
        adaptationIdentifiers.clear( );
        
        // Add scene IDs
        for( Scene scene : chapter.getScenes( ) ) {
            addSceneId( scene.getId( ) );
            for( ActiveArea activeArea : scene.getActiveAreas( ) ) {
                if( activeArea.getId( ) != null && !activeArea.getId( ).equals( "" ) )
                    addActiveAreaId( activeArea.getId( ) );
            }
        }

        // Add cutscene IDs
        for( Cutscene cutscene : chapter.getCutscenes( ) )
            addCutsceneId( cutscene.getId( ) );

        // Add book IDs
        for( Book book : chapter.getBooks( ) )
            addBookId( book.getId( ) );

        // Add item IDs
        for( Item item : chapter.getItems( ) )
            addItemId( item.getId( ) );

        // Add atrezzo items IDs
        for( Atrezzo atrezzo : chapter.getAtrezzo( ) )
            addAtrezzoId( atrezzo.getId( ) );

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

        // Add assessment rules ids and asssessmnet profiles ids
        for( AssessmentProfile profile : chapter.getAssessmentProfiles( ) ) {
            String name = profile.getName();
            addAssessmentProfileId( name );
            for( AssessmentRule rule : profile.getRules( ) )
                this.addAssessmentRuleId( rule.getId( ), name );
        }

        // Add adaptation rules ids and asssessmnet profiles ids
        for( AdaptationProfile profile : chapter.getAdaptationProfiles( ) ) {
            String name = profile.getName( ) ;
            addAdaptationProfileId( name );
            for( AdaptationRule rule : profile.getRules( ) )
                this.addAdaptationRuleId( rule.getId( ), name );
        }

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
     * Returns all scenes ids (scenes and cutsecene)
     * 
     * @return Array of cutscene plus scene identifiers
     */
    public String[] getAllSceneIds( ) {

        ArrayList<String> allScenes = new ArrayList<String>( cutsceneIdentifiers );
        allScenes.addAll( new ArrayList<String>( sceneIdentifiers ) );
        return allScenes.toArray( new String[] {} );
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
     * Returns an array of atrezzo item identifiers.
     * 
     * @return Array of atrezzo item identifiers
     */
    public String[] getAtrezzoIds( ) {

        return atrezzoIdentifiers.toArray( new String[] {} );
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
    public String[] getGlobalStatesIds( String[] exceptions ) {

        List<String> globalStateIds = new ArrayList<String>( );
        for( String id : this.globalStateIdentifiers ) {
            for( String exception : exceptions ) {
                if( !id.equals( exception ) )
                    globalStateIds.add( id );
            }
        }
        return globalStateIds.toArray( new String[] {} );
    }

    /**
     * Returns an array of macro identifiers.
     * 
     * @return Array of macro identifiers
     */
    public String[] getMacroIds( String exception ) {

        List<String> macroIds = new ArrayList<String>( );
        for( String id : this.macroIdentifiers ) {
            if( !id.equals( exception ) )
                macroIds.add( id );
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
     * Adds a new atrezzo item id.
     * 
     * @param atrezzoId
     *            New atrezzo item id
     */
    public void addAtrezzoId( String atrezzoId ) {

        globalIdentifiers.add( atrezzoId );
        atrezzoIdentifiers.add( atrezzoId );
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

    /**
     * Adds a new assessment rule id. 
     * The id has the name of its profile and the name of the rule :  "profile.assRuleId"
     * 
     * @param assRuleId
     *            New assessment rule id
     * @param profile
     *            The name of profile which contains the rule
     */
    public void addAssessmentRuleId( String assRuleId, String profile ) {
	
        globalIdentifiers.add( profile+"."+assRuleId );
        this.assessmentIdentifiers.get(profile).add( assRuleId );
    }

    /**
     * Adds a new adaptation rule id. 
     * The id has the name of its profile and the name of the rule :  "profile.assRuleId"
     * 
     * @param assRuleId
     *            New adaptation rule id
     *            
     * @param profile
     *            The name of profile which contains the rule
     */
    public void addAdaptationRuleId( String adpRuleId, String profile  ) {

	 globalIdentifiers.add( profile+"."+adpRuleId );
	 this.adaptationIdentifiers.get(profile).add( adpRuleId );
    }

    /**
     * Adds a new assessment profile id.
     * 
     * @param assProfileId
     *            New assessment profile id
     */
    public void addAssessmentProfileId( String assProfileId ) {

        globalIdentifiers.add( assProfileId );
        this.assessmentIdentifiers.put(assProfileId, new ArrayList<String>());
    }

    /**
     * Adds a new adaptation profile id.
     * 
     * @param adaptProfileId
     *            New adaptation profile id
     */
    public void addAdaptationProfileId( String adaptProfileId ) {

        globalIdentifiers.add( adaptProfileId );
        this.adaptationIdentifiers.put(adaptProfileId, new ArrayList<String>() );
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
     * Deletes a new atrezzo item id.
     * 
     * @param atrezzoId
     *            atrezzo item id to be deleted
     */
    public void deleteAtrezzoId( String atrezzoId ) {

        globalIdentifiers.remove( atrezzoId );
        atrezzoIdentifiers.remove( atrezzoId );
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

    /**
     * Deleted an assessment rule id
     * 
     * @param id
     *            Assessment rule id to be deleted
     */
    public void deleteAssessmentRuleId( String id, String profile ) {

        globalIdentifiers.remove( profile + "." + id );
        assessmentIdentifiers.get(profile).remove(id);
    }

    /**
     * Deleted an adaptation rule id
     * 
     * @param id
     *            adaptation rule id to be deleted
     */
    public void deleteAdaptationRuleId( String id, String profile ) {

        globalIdentifiers.remove( profile + "." + id );
        adaptationIdentifiers.get(profile).remove(id);
    }

    /**
     * Deleted an assessment profile id
     * 
     * @param id
     *            Assessment profile id to be deleted
     */
    public void deleteAssessmentProfileId( String id ) {

        globalIdentifiers.remove( id );
        assessmentIdentifiers.remove( id );
    }

    /**
     * Deleted an adaptation profile id
     * 
     * @param id
     *            adaptation profile id to be deleted
     */
    public void deleteAdaptationProfileId( String id ) {

        globalIdentifiers.remove( id );
        adaptationIdentifiers.remove( id );

    }
    
    /**
     * Rename the adaptation profile and all of its associated rules
     * 
     * @param oldName
     * 		The old profile's name.
     * @param newName
     * 		The new profile's name.
     */
    public void renameAdaptationProfile( String oldName, String newName ){
	// delete all adaptation rules ids associated to deleted profile
	List<String> adp = adaptationIdentifiers.get(oldName);
	for (int i = 0; i<adp.size(); i++ ){
	    this.deleteAdaptationRuleId(adp.get(i), oldName );
	}
	// delete the profile ID
	this.deleteAdaptationProfileId(oldName);
	
	// Add the new adaptation ID and its rules ids
	this.addAdaptationProfileId(newName);
	for (int i = 0; i<adp.size(); i++ ){
	    this.addAdaptationRuleId(adp.get(i), newName );
	}
    }
    
    /**
     * Rename the asssessment profile and all of its associated rules
     * 
     * @param oldName
     * 		The old profile's name.
     * @param newName
     * 		The new profile's name.
     */
    public void renameAssessmentProfile( String oldName, String newName ){
	// delete all adaptation rules ids associated to deleted profile
	List<String> adp = assessmentIdentifiers.get(oldName);
	for (int i = 0; i<adp.size(); i++ ){
	    this.deleteAssessmentRuleId(adp.get(i), oldName );
	}
	// delete the profile ID
	this.deleteAssessmentProfileId(oldName);
	
	// Add the new assessment ID and its rules ids
	this.addAssessmentProfileId(newName);
	for (int i = 0; i<adp.size(); i++ ){
	    this.addAssessmentRuleId(adp.get(i), newName );
	}
    }

    public boolean isAdaptationProfileId( String id ) {

        return this.adaptationIdentifiers.keySet().contains( id );
    }
    
    public boolean isAdaptationRuleId( String id, String profile ) {

        return this.adaptationIdentifiers.get(profile).contains( id );
    }

    public boolean isAssessmentProfileId( String id ) {

        return this.assessmentIdentifiers.keySet().contains( id );
    }
    
    public boolean isAssessmentRuleId( String id, String profile ) {

        return this.assessmentIdentifiers.get(profile).contains( id );
    }

    public boolean isGlobalStateId( String id ) {

        return globalStateIdentifiers.contains( id );
    }

    public boolean isMacroId( String id ) {

        return macroIdentifiers.contains( id );
    }

    public void addActiveAreaId( String id ) {

        globalIdentifiers.add( id );
        activeAreaIdentifiers.add( id );
    }

    public void deleteActiveAreaId( String id ) {

        if( activeAreaIdentifiers.contains( id ) ) {
            globalIdentifiers.remove( id );
            activeAreaIdentifiers.remove( id );
        }
    }

    /**
     * Get a list of all ids of the items and active areas
     * 
     * @return ids of the items and activeAreas
     */
    public String[] getItemAndActiveAreaIds( ) {
        List<String> set = new ArrayList<String>( itemIdentifiers );
        set.addAll( activeAreaIdentifiers );
        return set.toArray( new String[] {} );
    }

    /**
     * Get a list of all ids of the items, active areas and npcs
     * 
     * @return ids of the items, activeAreas and npcs
     */
    public String[] getItemActiveAreaNPCIds( ) {
        List<String> set = new ArrayList<String>( itemIdentifiers );
        set.addAll( activeAreaIdentifiers );
        set.addAll( npcIdentifiers );
        return set.toArray( new String[] {} );
    }

}
