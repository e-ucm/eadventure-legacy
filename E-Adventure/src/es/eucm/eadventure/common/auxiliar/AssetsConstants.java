/**
 * <e-Adventure> is an <e-UCM> research project. <e-UCM>, Department of Software
 * Engineering and Artificial Intelligence. Faculty of Informatics, Complutense
 * University of Madrid (Spain).
 * 
 * @author Del Blanco, A., Marchiori, E., Torrente, F.J. (alphabetical order) *
 * @author López Mañas, E., Pérez Padilla, F., Sollet, E., Torijano, B. (former
 *         developers by alphabetical order)
 * @author Moreno-Ger, P. & Fernández-Manjón, B. (directors)
 * @year 2009 Web-site: http://e-adventure.e-ucm.es
 */

/*
 * Copyright (C) 2004-2009 <e-UCM> research group
 * 
 * This file is part of <e-Adventure> project, an educational game & game-like
 * simulation authoring tool, available at http://e-adventure.e-ucm.es.
 * 
 * <e-Adventure> is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option) any
 * later version.
 * 
 * <e-Adventure> is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * <e-Adventure>; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 * 
 */
package es.eucm.eadventure.common.auxiliar;

/**
 * Set of constants for identifying types of Asssets. AssetsController (editor)
 * must implement it.
 * 
 * @author Javier
 * 
 */
public interface AssetsConstants {

    /**
     * Background category.
     */
    public static final int CATEGORY_BACKGROUND = 0;

    /**
     * Animation category.
     */
    public static final int CATEGORY_ANIMATION = 1;

    /**
     * Image category.
     */
    public static final int CATEGORY_IMAGE = 2;

    /**
     * Icon category.
     */
    public static final int CATEGORY_ICON = 3;

    /**
     * Audio category.
     */
    public static final int CATEGORY_AUDIO = 4;

    /**
     * Video category.
     */
    public static final int CATEGORY_VIDEO = 5;

    /**
     * Cursor category.
     */
    public static final int CATEGORY_CURSOR = 6;

    /**
     * Cursor category.
     */
    public static final int CATEGORY_STYLED_TEXT = 7;

    /**
     * Animation Image category
     */
    public static final int CATEGORY_ANIMATION_IMAGE = 8;

    /**
     * Customized button category
     */
    public static final int CATEGORY_BUTTON = 9;

    /**
     * Animation sound category
     */
    public static final int CATEGORY_ANIMATION_AUDIO = 10;
    
    /**
     * Arrows for books
     */
    public static final int CATEGORY_ARROW_BOOK = 11;

    /**
     * Number of categories.
     */
    public static final int CATEGORIES_COUNT = 12;
    
    
}
