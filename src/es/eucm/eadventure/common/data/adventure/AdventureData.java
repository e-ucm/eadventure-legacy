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
public class AdventureData extends DescriptorData{

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
	}

}
