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
package es.eucm.eadventure.common.loader;

import java.io.InputStream;
import java.net.URL;

/**
 * Constructs the InputStream of a file located in a parent structure, which is
 * abstracted by this entity Entities aiming to use Loader must implement this
 * interface
 * 
 * @author Javier
 * 
 */
public interface InputStreamCreator {

    /**
     * Builds the inputStream Object of a filePath which is stored in "parent",
     * where the implementation of parent is undefined
     * 
     * @param filePath
     *            Path of the file
     */
    public InputStream buildInputStream( String filePath );

    /**
     * If filePath is a directory in parentPath, the list of its children is
     * given
     * 
     * @param filePath
     * @return
     */
    public String[] listNames( String filePath );

    public URL buildURL( String path );
}
