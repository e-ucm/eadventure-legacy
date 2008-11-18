package es.eucm.eadventure.common.data.chapter.scenes;

/**
 * This class holds the data of a slidescene in eAdventure
 */
public class Slidescene extends Cutscene {

    /**
     * The tag for the slides
     */
    public static final String RESOURCE_TYPE_SLIDES = "slides";

	/**
	 * Creates a new Slidescene
	 * 
	 * @param id
	 *            the id of the slidescene
	 */
	public Slidescene( String id ) {
		super( GeneralScene.SLIDESCENE, id );
	}
}
