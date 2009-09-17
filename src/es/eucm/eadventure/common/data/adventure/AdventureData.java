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

import es.eucm.eadventure.common.data.chapter.Chapter;

/**
 * Represents the whole data of an adventure game. This includes: Descriptor
 * Information (adventure title, description, path of each chapter, adaptation &
 * assessment configuration, gui configuration) List of Chapters List of
 * AssessmentProfiles List of AdaptationProfiles
 * 
 * @author Javier
 * 
 */
public class AdventureData extends DescriptorData {

    /**
     * List of chapters. Contains the main data of the adventures.
     */
    protected List<Chapter> chapters;

    /**
     * Default constructor
     */
    public AdventureData( ) {

        super( );
        this.chapters = new ArrayList<Chapter>( );
        contents = null;
    }

    /**
     * Adds a new chapter.
     * 
     * @param chapter
     */
    public void addChapter( Chapter chapter ) {

        //contents.add(chapter);
        chapters.add( chapter );
    }

    /**
     * @return the chapters
     */
    public List<Chapter> getChapters( ) {

        /*List<Chapter> chapters = new ArrayList<Chapter>();
        for (ChapterSummary summary: contents){
        	chapters.add((Chapter)summary);
        }*/
        return chapters;
    }

    /**
     * @param chapters
     *            the chapters to set
     */
    public void setChapters( List<Chapter> chapters ) {

        /*this.contents = new ArrayList<ChapterSummary>();
        for (Chapter chapter: chapters){
        	contents.add(chapter);
        }*/
        this.chapters = chapters;
    }

    /*
     * Redefine ChapterSummaries handling so no data is duplicated
     *  
     */
    @Override
    public void addChapterSummary( ChapterSummary chapter ) {

        if( chapter instanceof Chapter ) {
            chapters.add( (Chapter) chapter );
        }
    }
    
    /**
     * Returns if the chapter.xml has adaptation and/or assessment data
     * 
     * @return
     */
    public boolean hasAdapOrAssesData(){
	
	for (int i=0;i<chapters.size();i++){
	    String[] apn = chapters.get(i).getAdaptationProfilesNames();
	   if (apn.length>0)
	       return true;
	   String[] aspn = chapters.get(i).getAssessmentProfilesNames();
	   if (aspn.length>0)
	       return true;
	}
	
	return false;
    }

    /**
     * Returns the list of chapters of the game
     * 
     * @return List of chapters of the game
     */
    @Override
    public List<ChapterSummary> getChapterSummaries( ) {

        List<ChapterSummary> summary = new ArrayList<ChapterSummary>( );
        for( Chapter chapter : chapters ) {
            summary.add( chapter );
        }
        return summary;
    }

    @Override
    public Object clone( ) throws CloneNotSupportedException {

        AdventureData ad = (AdventureData) super.clone( );
        ad.buttons = new ArrayList<CustomButton>( );
        for( CustomButton cb : buttons )
            ad.buttons.add( (CustomButton) cb.clone( ) );
        for( CustomArrow ca : arrows )
            ad.arrows.add( (CustomArrow) ca.clone( ) );
        //ad.chapters = new ArrayList<Chapter>();
        //for (Chapter c : chapters)
        //	ad.chapters.add((Chapter) c.clone());
        ad.commentaries = commentaries;
        ad.contents = new ArrayList<ChapterSummary>( );
        for( ChapterSummary cs : contents )
            ad.contents.add( (ChapterSummary) cs.clone( ) );
        ad.cursors = new ArrayList<CustomCursor>( );
        for( CustomCursor cc : cursors )
            ad.cursors.add( (CustomCursor) cc.clone( ) );
        ad.description = new String( description );
        ad.guiCustomized = guiCustomized;
        ad.guiType = guiType;
        ad.playerMode = playerMode;
        ad.playerName = ( playerName != null ? new String( playerName ) : null );
        ad.title = ( title != null ? new String( title ) : null );
        return ad;
    }

}
