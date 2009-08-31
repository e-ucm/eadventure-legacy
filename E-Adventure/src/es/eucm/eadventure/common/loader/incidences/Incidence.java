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
package es.eucm.eadventure.common.loader.incidences;

import java.util.List;

public class Incidence {

    /**
     * Type of the affected resource: XML or Asset file.
     */
    public static final int XML_INCIDENCE = 0;

    public static final int ASSET_INCIDENCE = 1;

    /**
     * Affected resource "sub-type". For XML: Assessment, Adaptation, Chapter or
     * Descriptor can fail.
     */
    public static final int ASSESSMENT_INCIDENCE = 0;

    public static final int ADAPTATION_INCIDENCE = 1;

    public static final int CHAPTER_INCIDENCE = 2;

    public static final int DESCRIPTOR_INCIDENCE = 3;

    /**
     * Importance: Low, Medium, High or Critical
     */
    public static final int IMPORTANCE_LOW = 0;

    public static final int IMPORTANCE_MEDIUM = 1;

    public static final int IMPORTANCE_HIGH = 2;

    public static final int IMPORTANCE_CRITICAL = 3;

    /**
     * Type of the affected resource: {@link #XML_INCIDENCE},
     * {@link #ASSET_INCIDENCE}
     */
    private int type;

    /**
     * Affected area (sub-type). For XML: {@link #ASSESSMENT_INCIDENCE},
     * {@link #ADAPTATION_INCIDENCE}, {@link #CHAPTER_INCIDENCE},
     * {@link #DESCRIPTOR_INCIDENCE} For Asset: Category of the asset (see
     * AssetsController)
     */
    private int affectedArea;

    /**
     * Path of the affected File
     */
    private String affectedResource;

    /**
     * Importance of the incidence. Low-important incidences will not affect
     * directly the game. Medium-important incidences will vary slightly the
     * behaviour of the game, but not significantly. High important incidences
     * will make some parts of the games unplayable and Critical ones will make
     * them completely unreadable.
     */
    private int importance;

    /**
     * String containing a message which describes the problem found reading the
     * damaged file
     */
    private String message;

    /**
     * Indicates if the damaged file was referenced inside the XML or just was
     * contained in the zip / folder
     */
    private boolean referenced;

    /**
     * the exception
     */
    private Exception exception;

    /**
     * @return the exception
     */
    public Exception getException( ) {

        return exception;
    }

    /**
     * @param exception
     *            the exception to set
     */
    public void setException( Exception exception ) {

        this.exception = exception;
    }

    /**
     * @return the type
     */
    public int getType( ) {

        return type;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType( int type ) {

        this.type = type;
    }

    /**
     * @return the affectedArea
     */
    public int getAffectedArea( ) {

        return affectedArea;
    }

    /**
     * @param affectedArea
     *            the affectedArea to set
     */
    public void setAffectedArea( int affectedArea ) {

        this.affectedArea = affectedArea;
    }

    /**
     * @return the affectedResource
     */
    public String getAffectedResource( ) {

        return affectedResource;
    }

    /**
     * @param affectedResource
     *            the affectedResource to set
     */
    public void setAffectedResource( String affectedResource ) {

        this.affectedResource = affectedResource;
    }

    /**
     * @return the importance
     */
    public int getImportance( ) {

        return importance;
    }

    /**
     * @param importance
     *            the importance to set
     */
    public void setImportance( int importance ) {

        this.importance = importance;
    }

    /**
     * @return the message
     */
    public String getMessage( ) {

        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage( String message ) {

        this.message = message;
    }

    /**
     * @return the referenced
     */
    public boolean isReferenced( ) {

        return referenced;
    }

    /**
     * @param referenced
     *            the referenced to set
     */
    public void setReferenced( boolean referenced ) {

        this.referenced = referenced;
    }

    /**
     * @param type
     * @param affectedArea
     * @param affectedResource
     * @param importance
     * @param message
     * @param referenced
     */
    public Incidence( int type, int affectedArea, String affectedResource, int importance, String message, boolean referenced, Exception exception ) {

        this.type = type;
        this.affectedArea = affectedArea;
        this.affectedResource = affectedResource;
        this.importance = importance;
        this.message = message;
        this.referenced = referenced;
        this.exception = exception;
    }

    public static Incidence createDescriptorIncidence( String message, Exception exception ) {

        int type = XML_INCIDENCE;
        int affectedArea = DESCRIPTOR_INCIDENCE;
        String affectedResource = "descriptor.xml";
        int importance = IMPORTANCE_CRITICAL;
        boolean referenced = true;
        return new Incidence( type, affectedArea, affectedResource, importance, message, referenced, exception );
    }

    public static Incidence createAssessmentIncidence( boolean referenced, String message, String profilePath, Exception exception ) {

        int type = XML_INCIDENCE;
        int affectedArea = ASSESSMENT_INCIDENCE;
        String affectedResource = profilePath;
        int importance = IMPORTANCE_LOW;
        if( referenced )
            importance = IMPORTANCE_MEDIUM;
        return new Incidence( type, affectedArea, affectedResource, importance, message, referenced, exception );

    }

    public static Incidence createAdaptationIncidence( boolean referenced, String message, String profilePath, Exception exception ) {

        int type = XML_INCIDENCE;
        int affectedArea = ADAPTATION_INCIDENCE;
        String affectedResource = profilePath;
        int importance = IMPORTANCE_LOW;
        if( referenced )
            importance = IMPORTANCE_MEDIUM;
        return new Incidence( type, affectedArea, affectedResource, importance, message, referenced, exception );
    }

    public static Incidence createChapterIncidence( String message, String chapterPath, Exception exception ) {

        int type = XML_INCIDENCE;
        int affectedArea = CHAPTER_INCIDENCE;
        String affectedResource = chapterPath;
        int importance = IMPORTANCE_HIGH;
        boolean referenced = true;
        return new Incidence( type, affectedArea, affectedResource, importance, message, referenced, exception );
    }

    public static Incidence createAssetIncidence( boolean notPresent, int assetType, String message, String assetPath, Exception exception ) {

        int type = ASSET_INCIDENCE;
        int affectedArea = assetType;
        int importance = IMPORTANCE_MEDIUM;
        return new Incidence( type, affectedArea, assetPath, importance, message, notPresent, exception );
    }

    public static void sortIncidences( List<Incidence> incidences ) {

        if( incidences != null && incidences.size( ) > 0 )
            sortIncidences( incidences, 0 );
    }

    private static void sortIncidences( List<Incidence> incidences, int j ) {

        if( incidences != null ) {
            int max = incidences.get( j ).importance;
            for( int i = j + 1; i < incidences.size( ) && max < IMPORTANCE_CRITICAL; i++ ) {
                if( incidences.get( i ).importance > max ) {
                    max = incidences.get( i ).importance;
                    Incidence temp = incidences.remove( j );
                    incidences.add( j, incidences.get( i ) );
                    incidences.add( i, temp );
                }
            }
        }
    }
}
