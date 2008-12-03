package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.data.chapter.book.Book;
import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.data.chapter.book.BookParagraph;
import es.eucm.eadventure.common.data.chapter.resources.Resources;

public class BookDOMWriter {

	/**
	 * Private constructor.
	 */
	private BookDOMWriter( ) {}

	public static Node buildDOM( Book book ) {
		Element bookElement = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			bookElement = doc.createElement( "book" );
			bookElement.setAttribute( "id", book.getId( ) );

			// Append the documentation (if avalaible)
			if( book.getDocumentation( ) != null ) {
				Node bookDocumentationNode = doc.createElement( "documentation" );
				bookDocumentationNode.appendChild( doc.createTextNode( book.getDocumentation( ) ) );
				bookElement.appendChild( bookDocumentationNode );
			}

			// Append the resources
			for( Resources resources : book.getResources( ) ) {
				Node resourcesNode = ResourcesDOMWriter.buildDOM( resources );
				doc.adoptNode( resourcesNode );
				bookElement.appendChild( resourcesNode );
			}

			// Create the text/pages element
			
			Element textPagesElement = null;
			if (book.getType( ) == Book.TYPE_PARAGRAPHS){
				textPagesElement = doc.createElement( "text" );

				// Create and append the paragraphs
				for( BookParagraph bookParagraph : book.getParagraphs( ) ) {
					Node paragraphNode = null;
	
					// If it is a text paragraph
					if( bookParagraph.getType( ) == BookParagraph.TEXT ) {
						paragraphNode = doc.createTextNode( bookParagraph.getContent( ) );
					}
	
					// If it is a text paragraph
					if( bookParagraph.getType( ) == BookParagraph.TITLE ) {
						paragraphNode = doc.createElement( "title" );
						paragraphNode.appendChild( doc.createTextNode( bookParagraph.getContent( ) ) );
					}
	
					// If it is a bullet paragraph
					else if( bookParagraph.getType( ) == BookParagraph.BULLET ) {
						paragraphNode = doc.createElement( "bullet" );
						paragraphNode.appendChild( doc.createTextNode( bookParagraph.getContent( ) ) );
					}
	
					// If it is an image paragraph
					else if( bookParagraph.getType( ) == BookParagraph.IMAGE ) {
						Element imageParagraphElement = doc.createElement( "img" );
						imageParagraphElement.setAttribute( "src", bookParagraph.getContent( ) );
						paragraphNode = imageParagraphElement;
					}
	
					// Append the created paragraph
					textPagesElement.appendChild( paragraphNode );
				}
			}
			else if (book.getType( ) == Book.TYPE_PAGES){
				textPagesElement = doc.createElement( "pages" );
				for (BookPage page: book.getPageURLs( )){
					// Create the node for the page
					Element pageElement = doc.createElement( "page" );
					
					//Attributes: uri, type, margin, scrollable
					pageElement.setAttribute( "scrollable", ( page.getScrollable( )?"yes":"no"));
					pageElement.setAttribute( "margin", Integer.toString( page.getMargin( ) ) );
					if (page.getMarginEnd() != 0)
						pageElement.setAttribute("marginEnd", Integer.toString(page.getMarginEnd()));
					if (page.getMarginTop() != 0)
						pageElement.setAttribute("marginTop", Integer.toString(page.getMarginTop()));
					if (page.getMarginBottom() != 0)
						pageElement.setAttribute("marginBottom", Integer.toString(page.getMarginBottom()));
					pageElement.setAttribute( "uri", page.getUri( ) );
					pageElement.setAttribute( "type", ( page.getType( ) == BookPage.TYPE_RESOURCE)?"resource":"url" );
					
					
					textPagesElement.appendChild( pageElement );
				}
			}
			// Append the text element
			bookElement.appendChild( textPagesElement );

		} catch( ParserConfigurationException e ) {
			e.printStackTrace( );
		}

		return bookElement;
	}
}
