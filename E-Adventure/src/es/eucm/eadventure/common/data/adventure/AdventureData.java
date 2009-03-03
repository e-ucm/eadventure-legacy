package es.eucm.eadventure.common.data.adventure;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;
import es.eucm.eadventure.common.data.chapter.Chapter;

/**
 * Represents the whole data of an adventure game. This includes:
 * Descriptor Information (adventure title, description, path of each chapter, adaptation & assessment configuration, gui configuration)
 * List of Chapters
 * List of AssessmentProfiles
 * List of AdaptationProfiles
 * @author Javier
 *
 */
public class AdventureData extends DescriptorData {

	/**
	 * List of chapters. Contains the main data of the adventures.
	 */
	protected List<Chapter> chapters;
	
	/**
	 * List of assessment profiles in file. Can be used to store all the different profiles in zip file or just to store those referenced
	 * in chapters
	 */
	protected List<AssessmentProfile> assessmentProfiles;

	/**
	 * List of adaptation profiles in file. Can be used to store all the different profiles in zip file or just to store those referenced
	 * in chapters
	 */
	protected List<AdaptationProfile> adaptationProfiles;
	
	/**
	 * Default constructor
	 */
	public AdventureData(){
		super();
		this.chapters = new ArrayList<Chapter>();
		this.assessmentProfiles = new ArrayList<AssessmentProfile>();
		this.adaptationProfiles = new ArrayList<AdaptationProfile>();
	}
	
	/**
	 * Adds a new chapter. 
	 * @param chapter
	 */
	public void addChapter ( Chapter chapter ){
		contents.add(chapter);
		chapters.add(chapter);
	}
	
	/**
	 * Adds a new assessment profile
	 * @param profile
	 */
	public void addAssessmentProfile ( AssessmentProfile profile ){
		assessmentProfiles.add(profile);
	}
	
	/**
	 * Adds a new adaptation profile
	 * @param profile
	 */
	public void addAssessmentProfile ( AdaptationProfile profile ){
		adaptationProfiles.add(profile);
	}

	/**
	 * @return the assessmentProfiles
	 */
	public List<AssessmentProfile> getAssessmentProfiles() {
		return assessmentProfiles;
	}

	/**
	 * @return the adaptationProfiles
	 */
	public List<AdaptationProfile> getAdaptationProfiles() {
		return adaptationProfiles;
	}

	/**
	 * @return the chapters
	 */
	public List<Chapter> getChapters() {
		return chapters;
	}

	/**
	 * @param chapters the chapters to set
	 */
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
		for (Chapter chapter: chapters){
			contents.add(chapter);
		}
	}
	
	public Object clone() throws CloneNotSupportedException {
		AdventureData ad = (AdventureData) super.clone();
		ad.adaptationProfiles = new ArrayList<AdaptationProfile>();
		for (AdaptationProfile ap : adaptationProfiles)
			ad.adaptationProfiles.add((AdaptationProfile) ap.clone());
		ad.assessmentProfiles = new ArrayList<AssessmentProfile>();
		for (AssessmentProfile ap : assessmentProfiles)
			ad.assessmentProfiles.add((AssessmentProfile) ap.clone());
		ad.buttons = new ArrayList<CustomButton>();
		for (CustomButton cb : buttons)
			ad.buttons.add((CustomButton) cb.clone());
		for (CustomArrow ca : arrows)
			ad.arrows.add((CustomArrow) ca.clone());
		ad.chapters = new ArrayList<Chapter>();
		for (Chapter c : chapters)
			ad.chapters.add((Chapter) c.clone());
		ad.commentaries = commentaries;
		ad.contents = new ArrayList<ChapterSummary>();
		for (ChapterSummary cs : contents)
			ad.contents.add((ChapterSummary) cs.clone());
		ad.cursors = new ArrayList<CustomCursor>();
		for (CustomCursor cc : cursors)
			ad.cursors.add((CustomCursor) cc.clone());
		ad.description = new String(description);
		ad.guiCustomized = guiCustomized;
		ad.guiType = guiType;
		ad.playerMode = playerMode;
		ad.playerName = (playerName != null ? new String(playerName) : null);
		ad.title = (title != null ? new String(title) : null);
		return ad;
	}

}
