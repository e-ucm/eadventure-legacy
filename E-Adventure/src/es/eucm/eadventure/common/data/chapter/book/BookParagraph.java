package es.eucm.eadventure.common.data.chapter.book;

/**
 * Main class of the paragraphs of one book
 */
public class BookParagraph implements Cloneable {

	/**
	 * Type for bullet paragraph.
	 */
	public static final int BULLET = 0;

	/**
	 * Type for image paragraph.
	 */
	public static final int IMAGE = 1;

	/**
	 * Type for text paragraph.
	 */
	public static final int TEXT = 2;

	/**
	 * Type for title paragraph.
	 */
	public static final int TITLE = 3;

	/**
	 * Type of the paragraph.
	 */
	private int type;

	/**
	 * Text content of the paragraph.
	 */
	private String content;

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The type of the paragraph
	 */
	public BookParagraph( int type ) {
		this.type = type;
		this.content = "";
	}

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            The type of the paragraph
	 * @param content
	 *            Content of the paragraph
	 */
	public BookParagraph( int type, String content ) {
		this.type = type;
		this.content = content;
	}

	/**
	 * Set the new content of the paragraph.
	 * 
	 * @param content
	 *            New content
	 */
	public void setContent( String content ) {
		this.content = content;
	}

	/**
	 * Returns the type of the paragraph
	 * 
	 * @return Paragraph's type
	 */
	public int getType( ) {
		return type;
	}

	/**
	 * Returns the content of the paragraph.
	 * 
	 * @return Content of the paragraph. It will be text if it is a text or bullet paragraph, or a path if it is an
	 *         image paragraph
	 */
	public String getContent( ) {
		return content;
	}

	public Object clone() throws CloneNotSupportedException {
		BookParagraph bp = (BookParagraph) super.clone();
		bp.content = (content != null ? new String(content) : null);
		bp.type = type;
		return bp;
	}
}
