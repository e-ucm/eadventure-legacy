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
package es.eucm.eadventure.editor.data;

/**
 * This class holds the information about an asset. It stores the description of
 * the asset, the identifier (name) of the asset, and its type.
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
