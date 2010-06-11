/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *         research group.
 *  
 *   Copyright 2005-2010 <e-UCM> research group.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *         http://e-adventure.e-ucm.es/contributors
 * 
 *   <e-UCM> is a research group of the Department of Software Engineering
 *         and Artificial Intelligence at the Complutense University of Madrid
 *         (School of Computer Science).
 * 
 *         C Profesor Jose Garcia Santesmases sn,
 *         28040 Madrid (Madrid), Spain.
 * 
 *         For more info please visit:  <http://e-adventure.e-ucm.es> or
 *         <http://www.e-ucm.es>
 * 
 * ****************************************************************************
 * 
 * This file is part of <e-Adventure>, version 1.2.
 * 
 *     <e-Adventure> is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     <e-Adventure> is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 * 
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
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
     * @return Content of the paragraph. It will be text if it is a text or
     *         bullet paragraph, or a path if it is an image paragraph
     */
    public String getContent( ) {

        return content;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        BookParagraph bp = (BookParagraph) super.clone( );
        bp.content = ( content != null ? new String( content ) : null );
        bp.type = type;
        return bp;
    }
}
