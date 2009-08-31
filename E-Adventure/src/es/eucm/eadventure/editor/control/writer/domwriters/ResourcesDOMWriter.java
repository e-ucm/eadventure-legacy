/**
 * <e-Adventure> is an <e-UCM> research project.
 * <e-UCM>, Department of Software Engineering and Artificial Intelligence.
 * Faculty of Informatics, Complutense University of Madrid (Spain).
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J.
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009
 * Web-site: http://e-adventure.e-ucm.es
 */

/*
    Copyright (C) 2004-2009 <e-UCM> research group

    This file is part of <e-Adventure> project, an educational game & game-like 
    simulation authoring tool, availabe at http://e-adventure.e-ucm.es. 

    <e-Adventure> is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    <e-Adventure> is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with <e-Adventure>; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

*/
package es.eucm.eadventure.editor.control.writer.domwriters;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.controllers.AssetsController;

public class ResourcesDOMWriter {

	public static final int RESOURCES_NONE = 0;

	public static final int RESOURCES_ITEM = 1;
	
	public static final int RESOURCES_SCENE = 2;
	
	public static final int RESOURCES_CUTSCENE = 3;

	public static final int RESOURCES_CHARACTER = 4;
	
	public static final int RESOURCES_ANIMATION = 5;
	
	public static final int RESOURCES_CUSTOM_ACTION = 6;

	/**
	 * Private constructor.
	 */
	private ResourcesDOMWriter( ) {}

	public static Node buildDOM( Resources resources ) {
		return buildDOM( resources, RESOURCES_NONE );
	}

	public static Node buildDOM( Resources resources, int resourcesType ) {
		Node resourcesNode = null;

		try {
			// Create the necessary elements to create the DOM
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance( );
			DocumentBuilder db = dbf.newDocumentBuilder( );
			Document doc = db.newDocument( );

			// Create the root node
			resourcesNode = doc.createElement( "resources" );

			// Append the conditions block (if there is one)
			if( !resources.getConditions( ).isEmpty( ) ) {
				Node conditionsNode = ConditionsDOMWriter.buildDOM( resources.getConditions( ) );
				doc.adoptNode( conditionsNode );
				resourcesNode.appendChild( conditionsNode );
			}

			// Take the array of types and values of the assets
			String[] assetTypes = resources.getAssetTypes( );
			String[] assetValues = resources.getAssetValues( );
			for( int i = 0; i < resources.getAssetCount( ); i++ ) {
				Element assetElement = doc.createElement( "asset" );
				assetElement.setAttribute( "type", assetTypes[i] );
				assetElement.setAttribute( "uri", assetValues[i] );
				resourcesNode.appendChild( assetElement );
			}

			// If the owner is an item
			if( resourcesType == RESOURCES_ITEM ) {
				// If the item has no image, add the default one
				if( resources.getAssetPath( "image" ) == null ) {
					Element assetElement = doc.createElement( "asset" );
					assetElement.setAttribute( "type", "image" );
					assetElement.setAttribute( "uri", AssetsController.ASSET_EMPTY_IMAGE );
					resourcesNode.appendChild( assetElement );
				}

				// If the item has no icon, add the default one
				if( resources.getAssetPath( "icon" ) == null ) {
					Element assetElement = doc.createElement( "asset" );
					assetElement.setAttribute( "type", "icon" );
					assetElement.setAttribute( "uri", AssetsController.ASSET_EMPTY_ICON );
					resourcesNode.appendChild( assetElement );
				}
			}
			
			// If the owner is a scene
			if( resourcesType == RESOURCES_SCENE ) {
				// If the item has no image, add the default one
				if( resources.getAssetPath( "background" ) == null ) {
					Element assetElement = doc.createElement( "asset" );
					assetElement.setAttribute( "type", "background" );
					assetElement.setAttribute( "uri", AssetsController.ASSET_EMPTY_IMAGE );
					resourcesNode.appendChild( assetElement );
				}
			}
			
			// If the owner is a scene
			if( resourcesType == RESOURCES_CUTSCENE ) {
				// If the item has no image, add the default one
				if( resources.getAssetPath( "slides" ) == null ) {
					Element assetElement = doc.createElement( "asset" );
					assetElement.setAttribute( "type", "slides" );
					assetElement.setAttribute( "uri", AssetsController.ASSET_EMPTY_ANIMATION );
					resourcesNode.appendChild( assetElement );
				}
			}

			// If the owner is a character
			else if( resourcesType == RESOURCES_CHARACTER ) {
				// For each asset, if it has not been declared attach the empty animation
				String[] assets = new String[] { "standup", "standdown", "standright", "standleft", "speakup", "speakdown", "speakright", "speakleft", "useright", "useleft", "walkup", "walkdown", "walkright", "walkleft" };
				for( String asset : assets ) {
					if( resources.getAssetPath( asset ) == null ) {
						Element assetElement = doc.createElement( "asset" );
						assetElement.setAttribute( "type", asset );
						assetElement.setAttribute( "uri", AssetsController.ASSET_EMPTY_ANIMATION );
						resourcesNode.appendChild( assetElement );
					}
				}
			}

			// If the owner is a character
			else if( resourcesType == RESOURCES_CUSTOM_ACTION ) {
				// For each asset, if it has not been declared attach the empty animation
				String[] assets = new String[] { "buttonNormal", "buttonOver", "buttonPressed" };
				for( String asset : assets ) {
					if( resources.getAssetPath( asset ) == null ) {
						Element assetElement = doc.createElement( "asset" );
						assetElement.setAttribute( "type", asset );
						assetElement.setAttribute( "uri", AssetsController.ASSET_EMPTY_ICON );
						resourcesNode.appendChild( assetElement );
					}
				}
			}

		} catch( ParserConfigurationException e ) {
        	ReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}

		return resourcesNode;
	}

}
