package es.eucm.eadventure.common.loader.subparsers;

import org.xml.sax.Attributes;

import es.eucm.eadventure.common.data.chapter.Chapter;
import es.eucm.eadventure.common.data.chapter.Trajectory;
import es.eucm.eadventure.common.data.chapter.scenes.Scene;

/**
 * Class to subparse items.
 */
public class TrajectorySubParser extends SubParser {

	/* Attributes */

	/**
	 * Constant for subparsing nothing.
	 */
	private static final int SUBPARSING_NONE = 0;

	/**
	 * Stores the current element being subparsed.
	 */
	private int subParsing = SUBPARSING_NONE;

	/**
	 * Trajectory being parsed.
	 */
	private Trajectory trajectory;

	/**
	 * Subparser for effects and conditions.
	 */
	private SubParser subParser;

	/* Methods */

	/**
	 * Constructor.
	 * 
	 * @param chapter
	 *            Chapter data to store the read data
	 */
	public TrajectorySubParser( Chapter chapter, Scene scene ) {
		super( chapter );
		this.trajectory = new Trajectory();
		scene.setTrajectory(trajectory);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.cargador.subparsers.SubParser#startElement(java.lang.String, java.lang.String,
	 *      java.lang.String, org.xml.sax.Attributes)
	 */
	public void startElement( String namespaceURI, String sName, String qName, Attributes attrs ) {

		// If no element is being subparsed
		if( subParsing == SUBPARSING_NONE ) {
			// If it is a object tag, create the new object (with its id)
			if( qName.equals( "node" ) ) {

				int x = 0, y = 0;
				float scale = 1.0f;
				
				String id = "";

				for( int i = 0; i < attrs.getLength( ); i++ ) {
					if( attrs.getQName( i ).equals( "x" ) )
						x = Integer.parseInt( attrs.getValue( i ) );
					if( attrs.getQName( i ).equals( "y" ) )
						y = Integer.parseInt( attrs.getValue( i ) );
					if( attrs.getQName( i ).equals( "id" ) )
						id = attrs.getValue( i );
					if (attrs.getQName(i).equals("scale")) {
						scale = Float.parseFloat( attrs.getValue(i));
					}
				}

				trajectory.addNode(id, x, y, scale);
				
			}

			// If it is a condition tag, create new conditions and switch the state
			else if( qName.equals( "side" ) ) {
				String idStart = "";
				String idEnd = "";
				
				for( int i = 0; i < attrs.getLength( ); i++ ) {
					if( attrs.getQName(i).equals("idStart"))
						idStart = attrs.getValue(i);
					if( attrs.getQName( i ).equals( "idEnd" ) )
						idEnd = attrs.getValue( i );
				}
				
				trajectory.addSide(idStart, idEnd);
			}
			
			else if ( qName.equals( "initialnode")) {
				String id = "";
				
				for (int i = 0; i < attrs.getLength(); i++) {
					if (attrs.getQName(i).equals("id"))
						id = attrs.getValue(i);
				}
				
				trajectory.setInitial(id);
			}

		}

		// If it is reading an effect or a condition, spread the call
		if( subParsing != SUBPARSING_NONE ) {
			subParser.startElement( namespaceURI, sName, qName, attrs );
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.cargador.subparsers.SubParser#endElement(java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	public void endElement( String namespaceURI, String sName, String qName ) {

		// If no element is being subparsed
		if( subParsing == SUBPARSING_NONE ) {


			// Reset the current string
			currentString = new StringBuffer( );
		}


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see es.eucm.eadventure.engine.loader.subparsers.SubParser#characters(char[], int, int)
	 */
	public void characters( char[] buf, int offset, int len ) {
		// If no element is being subparsed
		if( subParsing == SUBPARSING_NONE )
			super.characters( buf, offset, len );

		// If it is reading an effect or a condition, spread the call
		else
			subParser.characters( buf, offset, len );
	}
}
