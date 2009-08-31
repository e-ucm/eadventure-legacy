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
package es.eucm.eadventure.common.data.adventure;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.Described;
import es.eucm.eadventure.common.data.Titled;
import es.eucm.eadventure.common.data.adaptation.AdaptationProfile;
import es.eucm.eadventure.common.data.assessment.AssessmentProfile;

/**
 * Basically this class represents a chapter entry in the descriptor file. It
 * may contain the following data: path, title, description, metadata
 * (optionally), adaptation-configuration (optionally), assessment-configuration
 * (optionally)
 * 
 * @author Javier
 * 
 */
public class ChapterSummary implements Cloneable, Titled, Described {

    /**
     * Chapter's title.
     */
    private String title;

    /**
     * Chapter's description.
     */
    private String description;

    /**
     * Adaptation profile's name, if there is any.
     */
    // this attribute isn't in descriptor, now is in chapter.xml, as "eAdventure" element attribute.
    // don't move to "Chapter" class for no get it dirty.
    private String adaptationName;

    /**
     * Assessment profile's name, if there is any.
     */
    // this attribute isn't in descriptor, now is in chapter.xml, as "eAdventure" element attribute.
    // don't move to "Chapter" class for no get it dirty.
    private String assessmentName;

    /**
     * List of assessment profiles in chapter.
     */
    protected List<AssessmentProfile> assessmentProfiles;

    /**
     * List of adaptation profiles in chapter.
     */
    protected List<AdaptationProfile> adaptationProfiles;

    /**
     * Relative path to the zip where it was contained. Used for replacing
     */
    private String path;

    /**
     * Empty constructor. Sets values to null or blank
     */
    public ChapterSummary( ) {

        title = null;
        description = "";
        adaptationName = "";
        assessmentName = "";
        assessmentProfiles = new ArrayList<AssessmentProfile>( );
        adaptationProfiles = new ArrayList<AdaptationProfile>( );
    }

    /**
     * Constructor with title for the chapter. Sets empty values..
     * 
     * @param title
     *            Title for the chapter
     */
    public ChapterSummary( String title ) {

        this( );
        this.title = title;
    }

    /**
     * Returns the title of the chapter
     * 
     * @return Chapter's title
     */
    public String getTitle( ) {

        return title;
    }

    /**
     * Returns the description of the chapter.
     * 
     * @return Chapter's description
     */
    public String getDescription( ) {

        return description;
    }

    /**
     * Returns the name of the adaptation file.
     * 
     * @return the name of the adaptation file
     */
    public String getAdaptationName( ) {

        return adaptationName;
    }

    /**
     * Returns the name of the assessment file.
     * 
     * @return the name of the assessment file
     */
    public String getAssessmentName( ) {

        return assessmentName;
    }

    /**
     * Sets the title of the chapter.
     * 
     * @param title
     *            New title for the chapter
     */
    public void setTitle( String title ) {

        this.title = title;
    }

    /**
     * Sets the description of the chapter.
     * 
     * @param description
     *            New description for the chapter
     */
    public void setDescription( String description ) {

        this.description = description;
    }

    /**
     * Changes the name of the adaptation file.
     * 
     * @param adaptationName
     *            the new name of the adaptation file
     */
    public void setAdaptationName( String adaptationName ) {

        this.adaptationName = adaptationName;
    }

    /**
     * Changes the name of the assessment file.
     * 
     * @param assessmentName
     *            the new name of the assessment file
     */
    public void setAssessmentName( String assessmentName ) {

        this.assessmentName = assessmentName;
    }

    /**
     * @return the path of the capt
     */
    public String getChapterPath( ) {

        return path;
    }

    /**
     * @param path
     *            the path to set
     */
    public void setChapterPath( String path ) {

        this.path = path;
    }

    /**
     * Returns true if an assessment profile has been defined for the chapter
     * 
     * @return
     */
    public boolean hasAssessmentProfile( ) {

        return this.assessmentName != null && !this.assessmentName.equals( "" );
    }

    /**
     * Returns true if an adaptation profile has been defined for the chapter
     * 
     * @return
     */
    public boolean hasAdaptationProfile( ) {

        return this.adaptationName != null && !this.adaptationName.equals( "" );
    }

    /**
     * Adds new assessment profile
     * 
     * @param assessProfile
     *            the new assessment profile to add
     */
    public void addAssessmentProfile( AssessmentProfile assessProfile ) {

        assessmentProfiles.add( assessProfile );
    }

    /**
     * Adds new adaptation profile
     * 
     * @param adaptProfile
     *            the new assessment profile to add
     */
    public void addAdaptationProfile( AdaptationProfile adaptProfile ) {

        adaptationProfiles.add( adaptProfile );
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        ChapterSummary cs = (ChapterSummary) super.clone( );
        cs.adaptationName = ( adaptationName != null ? new String( adaptationName ) : null );
        cs.assessmentName = ( assessmentName != null ? new String( assessmentName ) : null );
        cs.description = ( description != null ? new String( description ) : null );
        cs.path = ( path != null ? new String( path ) : null );
        cs.title = ( title != null ? new String( title ) : null );
        cs.adaptationProfiles = ( adaptationProfiles != null ? new ArrayList<AdaptationProfile>( ) : null );
        cs.assessmentProfiles = ( assessmentProfiles != null ? new ArrayList<AssessmentProfile>( ) : null );
        return cs;
    }

    /**
     * @return the assessmentProfiles
     */
    public List<AssessmentProfile> getAssessmentProfiles( ) {

        return assessmentProfiles;
    }

    /**
     * @return the adaptationProfiles
     */
    public List<AdaptationProfile> getAdaptationProfiles( ) {

        return adaptationProfiles;
    }

    /**
     * Return the selected assessment profile
     * 
     * @return the selected assessment profile
     */
    public AssessmentProfile getSelectedAssessmentProfile( ) {

        for( AssessmentProfile profile : assessmentProfiles ) {
            if( profile.getName( ).equals( assessmentName ) )
                return profile;
        }
        return null;
    }

    /**
     * Return the selected adaptation profile
     * 
     * @return the selected adaptation profile
     */
    public AdaptationProfile getSelectedAdaptationProfile( ) {

        for( AdaptationProfile profile : adaptationProfiles ) {
            if( profile.getName( ).equals( adaptationName ) )
                return profile;
        }
        return null;
    }

    /**
     * Return all adaptation profiles names
     * 
     * @return An array of all adaptation profiles names
     */
    public String[] getAdaptationProfilesNames( ) {

        String[] names = new String[ adaptationProfiles.size( ) ];
        for( int i = 0; i < adaptationProfiles.size( ); i++ )
            names[i] = adaptationProfiles.get( i ).getName( );
        return names;
    }

    /**
     * Return all assessment profiles names
     * 
     * @return An array of all assessment profiles names
     */
    public String[] getAssessmentProfilesNames( ) {

        String[] names = new String[ assessmentProfiles.size( ) ];
        for( int i = 0; i < assessmentProfiles.size( ); i++ )
            names[i] = assessmentProfiles.get( i ).getName( );
        return names;
    }
}
