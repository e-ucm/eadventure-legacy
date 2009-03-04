package es.eucm.eadventure.editor.control.controllers.general;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.adventure.AdventureData;
import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.editor.control.controllers.AdventureDataControl;
import es.eucm.eadventure.editor.control.controllers.ChapterToolManager;
import es.eucm.eadventure.editor.control.controllers.ToolManager;
import es.eucm.eadventure.editor.control.controllers.adaptation.AdaptationProfileDataControl;
import es.eucm.eadventure.editor.control.controllers.assessment.AssessmentProfileDataControl;
import es.eucm.eadventure.editor.control.tools.Tool;
import es.eucm.eadventure.editor.data.support.IdentifierSummary;
import es.eucm.eadventure.editor.data.support.VarFlagSummary;

public class ChapterListDataControl {

	/**
	 * Controller for the chapters of the adventure.
	 */
	private List<ChapterDataControl> chapterDataControlList;
	
	/**
	 * The list of toolManagers (for undo/redo)
	 */
	private List<ChapterToolManager> chapterToolManagers;
	
	/**
	 * Stores the current selected Chapter
	 */
	private int selectedChapter;
	
	/**
	 * Summary of identifiers.
	 */
	private IdentifierSummary identifierSummary;

	/**
	 * Summary of flags.
	 */
	private VarFlagSummary varFlagSummary;
	
	public ChapterListDataControl(){
		varFlagSummary = new VarFlagSummary( );
		chapterDataControlList = new ArrayList<ChapterDataControl>();
		chapterToolManagers = new ArrayList<ChapterToolManager>();
		setSelectedChapterInternal( -1 );
	}
	
	public ChapterListDataControl ( List<Chapter> chapters ){
		this();
		for ( Chapter chapter: chapters ){
			chapterDataControlList.add( new ChapterDataControl ( chapter ) );
			chapterToolManagers.add( new ChapterToolManager() );
		}
		if (chapters.size()>0)
			setSelectedChapterInternal( 0 );
	}
	
	public void setSelectedChapterInternal( int newSelectedChapter ){
		this.selectedChapter = newSelectedChapter;
		if (selectedChapter==-1){
			if (chapterDataControlList.size()>0){
				selectedChapter = 0;
				identifierSummary = new IdentifierSummary( getSelectedChapterData());
				if (varFlagSummary==null)
					varFlagSummary = new VarFlagSummary();
				getSelectedChapterDataControl().updateVarFlagSummary(varFlagSummary);
			}else {
				identifierSummary = null;
				varFlagSummary = new VarFlagSummary();
			}
		} else {
			identifierSummary = new IdentifierSummary( getSelectedChapterData());
			if (varFlagSummary==null)
				varFlagSummary = new VarFlagSummary();
			getSelectedChapterDataControl().updateVarFlagSummary(varFlagSummary);
		}
	}
	
	/**
	 * Returns the data of the selected chapter.
	 * 
	 * @return Selected chapter data
	 */
	public Chapter getSelectedChapterData( ) {
		return (Chapter)chapterDataControlList.get( selectedChapter ).getContent();
	}
	
	/**
	 * Adds a new data control with the new chapter. It also updates automatically the selectedChapter, pointing to this new one
	 * @param chapter
	 */
	public void addChapterDataControl ( Chapter newChapter ){
		chapterDataControlList.add( new ChapterDataControl(newChapter) );
		chapterToolManagers.add( new ChapterToolManager() );
		setSelectedChapterInternal( chapterDataControlList.size()-1 );
	}
	
	/**
	 * Deletes the selected chapter data control. 
	 * It also updates automatically the selectedChapter if necessary
	 * @param chapter
	 */
	public void removeChapterDataControl (  ){
		removeChapterDataControl ( selectedChapter );
	}
	
	/**
	 * Deletes the chapter data control in the given position. 
	 * It also updates automatically the selectedChapter if necessary
	 * @param chapter
	 */
	public void removeChapterDataControl ( int index ){
		chapterDataControlList.remove(index);
		chapterToolManagers.remove(index);
		setSelectedChapterInternal( selectedChapter-1 );
	}
	
	/**
	 * Returns the index of the chapter currently selected.
	 * 
	 * @return Index of the selected chapter
	 */
	public int getSelectedChapter( ) {
		return selectedChapter;
	}
	
	/**
	 * Returns the selected chapter data controller.
	 * 
	 * @return The selected chapter data controller
	 */
	public ChapterDataControl getSelectedChapterDataControl( ) {
		if (chapterDataControlList.size()!=0)
			return chapterDataControlList.get( selectedChapter );
		else 
			return null;
	}
	
	public void addPlayerToAllScenesChapters(){
		for ( ChapterDataControl dataControl: chapterDataControlList ){
			dataControl.getScenesList().addPlayerToAllScenes();
		}
	}
	
	public void addPlayerToAllScenesSelectedChapter(){
		
	}
	
	public void deletePlayerToAllScenesChapters(){
		for ( ChapterDataControl dataControl: chapterDataControlList ){
			dataControl.getScenesList().deletePlayerToAllScenes();
		}
	}
	
	public void clear (){
		chapterDataControlList.clear();
		chapterToolManagers.clear();
		setSelectedChapterInternal( -1 );
	}
	
	public void deletePlayerToAllScenesSelectedChapter(){
		
	}

	public boolean isValid ( String currentPath, List<String> incidences ){
		boolean valid = true;
		for ( ChapterDataControl dataControl: chapterDataControlList){
			valid &= dataControl.isValid(currentPath, incidences);
		}
		
		return valid;
	}
	
	public boolean isAnyChapterSelected(){
		return selectedChapter !=-1;
	}

	/**
	 * @return the identifierSummary
	 */
	public IdentifierSummary getIdentifierSummary() {
		return identifierSummary;
	}

	/**
	 * @return the varFlagSummary
	 */
	public VarFlagSummary getVarFlagSummary() {
		return varFlagSummary;
	}

	public boolean replaceSelectedChapter(Chapter newChapter, AdventureDataControl adventureData) {
		int chapter = this.getSelectedChapter();
		adventureData.getChapters().set( getSelectedChapter(), newChapter);
		this.chapterDataControlList.remove( chapter );
		chapterDataControlList.add( chapter, new ChapterDataControl(newChapter) );
		
		identifierSummary = new IdentifierSummary(newChapter);
		identifierSummary.loadIdentifiers( getSelectedChapterData( ) );

		return true;
	}
	
	public boolean hasScorm12Profiles( AdventureDataControl adventureData ){
		boolean hasProfiles = true;
		for ( ChapterDataControl dataControl: chapterDataControlList ){
			Chapter chapter = (Chapter)dataControl.getContent();
			if (chapter.hasAdaptationProfile()){
				AdaptationProfileDataControl adpProfile = adventureData.getAdaptationRulesListDataControl().getProfileByPath( chapter.getAdaptationPath() );
				hasProfiles &=adpProfile.isScorm12();
			}
			if (chapter.hasAssessmentProfile()){
				AssessmentProfileDataControl assessmentProfile = adventureData.getAssessmentRulesListDataControl().getProfileByPath( chapter.getAssessmentPath() );
				hasProfiles &=assessmentProfile.isScorm12();
			}
		}
		return hasProfiles;
	}
	
	public boolean hasScorm2004Profiles( AdventureDataControl adventureData ){
		boolean hasProfiles = true;
		for ( ChapterDataControl dataControl: chapterDataControlList ){
			Chapter chapter = (Chapter)dataControl.getContent();
			if (chapter.hasAdaptationProfile()){
				AdaptationProfileDataControl adpProfile = adventureData.getAdaptationRulesListDataControl().getProfileByPath( chapter.getAdaptationPath() );
				hasProfiles &=adpProfile.isScorm2004();
			}
			if (chapter.hasAssessmentProfile()){
				AssessmentProfileDataControl assessmentProfile = adventureData.getAssessmentRulesListDataControl().getProfileByPath( chapter.getAssessmentPath() );
				hasProfiles &=assessmentProfile.isScorm2004();
			}
		}
		return hasProfiles;
	}

	public void updateFlagSummary( ) {
		getSelectedChapterDataControl( ).updateVarFlagSummary( varFlagSummary );
	}
	
	/**
	 * Moves the selected chapter to the previous position of the chapter's list.
	 */
	public void moveChapterUp(  ) {
		// If the chapter can be moved
		if( selectedChapter > 0 ) {
			// Move the chapter and update the selected chapter
			chapterDataControlList.add( selectedChapter - 1, chapterDataControlList.remove( selectedChapter ) );
			chapterToolManagers.add( selectedChapter - 1, chapterToolManagers.remove( selectedChapter ) );

			setSelectedChapterInternal ( getSelectedChapter()-1);

		}
	}

	/**
	 * Moves the selected chapter to the next position of the chapter's list.
	 * 
	 */
	public void moveChapterDown( ) {
		// If the chapter can be moved
		if( selectedChapter < chapterDataControlList.size( ) - 1 ) {
			// Move the chapter and update the selected chapter
			chapterDataControlList.add( selectedChapter + 1, chapterDataControlList.remove( selectedChapter ) );
			setSelectedChapterInternal ( getSelectedChapter()+1);

		}
	}
	
	/**
	 * Counts all the references to a given asset in the entire script.
	 * 
	 * @param assetPath
	 *            Path of the asset (relative to the ZIP), without suffix in case of an animation or set of slides
	 * @return Number of references to the given asset
	 */
	public int countAssetReferences( String assetPath ) {
		int count = 0;

		// Search in all the chapters
		for( ChapterDataControl chapterDataControl : chapterDataControlList ){
			count += chapterDataControl.countAssetReferences( assetPath );
		}return count;
	}
	
	/**
	 * Gets a list with all the assets referenced in the chapter along with the types of those assets
	 * @param assetPaths
	 * @param assetTypes
	 */
	public void getAssetReferences(List<String> assetPaths, List<Integer> assetTypes){
		for( ChapterDataControl chapterDataControl : chapterDataControlList ){
			chapterDataControl.getAssetReferences( assetPaths, assetTypes );
		}
	}
	
	/**
	 * Deletes a given asset from the script, removing all occurrences.
	 * 
	 * @param assetPath
	 *            Path of the asset (relative to the ZIP), without suffix in case of an animation or set of slides
	 */
	public void deleteAssetReferences( String assetPath ) {
		// Delete the asset in all the chapters
		for( ChapterDataControl chapterDataControl : chapterDataControlList )
			chapterDataControl.deleteAssetReferences( assetPath );
	}
	
	/**
	 * Deletes a given identifier from the script, removing all occurrences.
	 * 
	 * @param id
	 *            Identifier to be deleted
	 */
	public void deleteIdentifierReferences( String id ) {
		if (getSelectedChapterDataControl() != null)
			getSelectedChapterDataControl( ).deleteIdentifierReferences( id );
		else
			this.identifierSummary.deleteAssessmentRuleId( id );
	}
	
	public boolean addTool(Tool tool) {
		return chapterToolManagers.get( getSelectedChapter() ).addTool(tool);
	}

	public void undoTool() {
		chapterToolManagers.get( getSelectedChapter() ).undoTool();
	}

	public void redoTool() {
		chapterToolManagers.get( getSelectedChapter() ).redoTool();
	}
	
	public void pushLocalToolManager(){
		chapterToolManagers.get( getSelectedChapter() ).pushLocalToolManager();
	}
	
	public void popLocalToolManager(){
		chapterToolManagers.get( getSelectedChapter() ).popLocalToolManager();
	}
	
	public void update ( List<Chapter> chapters ){
		varFlagSummary = new VarFlagSummary( );
		chapterDataControlList = new ArrayList<ChapterDataControl>();
		chapterToolManagers = new ArrayList<ChapterToolManager>();
		
		for ( Chapter chapter: chapters ){
			chapterDataControlList.add( new ChapterDataControl ( chapter ) );
			chapterToolManagers.add( new ChapterToolManager() );
		}
		if (chapters.size()>0)
			setSelectedChapterInternal( 0 );		
	}

}
