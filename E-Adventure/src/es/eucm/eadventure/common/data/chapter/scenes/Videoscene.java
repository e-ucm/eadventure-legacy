package es.eucm.eadventure.common.data.chapter.scenes;

/**
 * This class holds the data of a videoscene in eAdventure
 */
public class Videoscene extends Cutscene {

    /**
     * The tag for the video
     */
    public static final String RESOURCE_TYPE_VIDEO = "video";
	
	/**
	 * Creates a new Videoscene
	 * 
	 * @param id
	 *            the id of the videoscene
	 */
	public Videoscene( String id ) {
		super( GeneralScene.VIDEOSCENE, id );
	}
}
