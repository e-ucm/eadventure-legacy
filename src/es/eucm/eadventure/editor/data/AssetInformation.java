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
package es.eucm.eadventure.editor.data;

/**
 * This class holds the information about an asset. It stores the description of the asset, the identifier (name) of
 * the asset, and its type.
 */
public class AssetInformation {

	/**
	 * Textual description of the asset.
	 */
	public String description;

	/**
	 * Name of the asset.
	 */
	public String name;

	/**
	 * True if the asset is necessary for the resources block to be valid.
	 */
	public boolean assetNecessary;

	/**
	 * Category of the asset.
	 */
	public int category;

	/**
	 * Specific filter for the category
	 */
	public int filter;

	/**
	 * Constructor.
	 * 
	 * @param description
	 *            Description of the asset
	 * @param name
	 *            Name of the asset
	 * @param assetNecessary
	 *            True if the asset is necessary for the resources to be valid
	 * @param category
	 *            Category of the asset
	 * @param filter
	 *            Specific filter for the category
	 */
	public AssetInformation( String description, String name, boolean assetNecessary, int category, int filter ) {
		this.description = description;
		this.name = name;
		this.assetNecessary = assetNecessary;
		this.category = category;
		this.filter = filter;
	}
}
