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
package es.eucm.eadventure.editor.gui.otherpanels.bookpanels;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import es.eucm.eadventure.common.data.chapter.book.BookPage;
import es.eucm.eadventure.common.gui.BookEditorPane;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.AssetsController.TempFileGenerator;
import es.eucm.eadventure.engine.core.gui.GUI;


public class BookEditorPaneEditor extends BookEditorPane {

    private static final long serialVersionUID = 1L;
    
    /**
     * Current book page represented.
     */
    protected BookPage currentBookPage;
    
    protected boolean export = false;
    
    public void setExport( boolean export ){
        this.export = export;
    }

    public BookEditorPaneEditor( BookPage currentBookP ) {
        super( );
        this.currentBookPage = currentBookP;
    }
    
    private void exportImage( Image im ) {

        String filePath = TempFileGenerator.generateTempFileOverwriteExisting( currentBookPage.getImageName( false ), "png" );

        File f = new File( filePath );
        try {
            BufferedImage ex = new BufferedImage( GUI.WINDOW_WIDTH, GUI.WINDOW_HEIGHT, BufferedImage.TYPE_INT_ARGB );
            ex.getGraphics( ).drawImage( im, currentBookPage.getMargin( ), currentBookPage.getMarginTop( ), null );
            ImageIO.write( ex, "png", f );
        }
        catch( IOException e ) {
            e.printStackTrace( );
        }

        AssetsController.addSingleAsset( AssetsController.CATEGORY_IMAGE, filePath, false );
    }

    @Override
    public boolean imageUpdate( Image img, int infoflags, int x, int y, int width, int height ) {
        //System.out.println( infoflags );
        if( export && infoflags == ImageObserver.FRAMEBITS && currentBookPage.getType( ) == BookPage.TYPE_RESOURCE ) {       
            exportImage( img );
        }
        return super.imageUpdate( img, infoflags, x, y, width, height );

    }

}
