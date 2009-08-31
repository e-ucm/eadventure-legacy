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

import javax.swing.Icon;

import de.schlichtherle.io.archive.zip.Zip32Driver;

/**
 * This class extends class de.schlichtherle.io.archive.zip.Zip32Driver, from
 * library truezip-6, in order to specify a charset different to IMB437 (default
 * charset for Zip32 driver), which is not supported by early versions of the
 * Java Platform. This is intended to extend the use of <e-Adventure> games in
 * systems with Java 1.4.x or 1.5.x where file lib/charsets.jar is not present.
 * 
 * @author Javier
 * 
 */
public class EADDriver extends Zip32Driver {

    /**
     * Generated UID. Required.
     */
    private static final long serialVersionUID = 143788939440380318L;

    /**
     * The default character set to use for entry names and comments, which is
     * {@value}.
     */
    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * Default constructor. Will be used by
     * ArchiveDriverRegistry.createArchiveDriver when invoking instruction:
     * driver = ((Class) driver).newInstance(); That is the last call in the
     * stack trace when the default archive driver is set
     */
    public EADDriver( ) {

        super( DEFAULT_CHARSET );
    }

    /**
     * Equivalent to
     * {@link #Zip32Driver(String, Icon, Icon, boolean, boolean, int)
     * this(DEFAULT_CHARSET, null, null, false, false, level)}.
     */
    public EADDriver( int level ) {

        super( DEFAULT_CHARSET, null, null, false, false, level );
    }
}
