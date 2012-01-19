/*******************************************************************************
 * <e-Adventure> (formerly <e-Game>) is a research project of the <e-UCM>
 *          research group.
 *   
 *    Copyright 2005-2012 <e-UCM> research group.
 *  
 *     <e-UCM> is a research group of the Department of Software Engineering
 *          and Artificial Intelligence at the Complutense University of Madrid
 *          (School of Computer Science).
 *  
 *          C Profesor Jose Garcia Santesmases sn,
 *          28040 Madrid (Madrid), Spain.
 *  
 *          For more info please visit:  <http://e-adventure.e-ucm.es> or
 *          <http://www.e-ucm.es>
 *  
 *  ****************************************************************************
 * This file is part of <e-Adventure>, version 1.4.
 * 
 *   You can access a list of all the contributors to <e-Adventure> at:
 *          http://e-adventure.e-ucm.es/contributors
 *  
 *  ****************************************************************************
 *       <e-Adventure> is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU Lesser General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *  
 *      <e-Adventure> is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU Lesser General Public License for more details.
 *  
 *      You should have received a copy of the GNU Lesser General Public License
 *      along with <e-Adventure>.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/
package es.eucm.eadventure.editor.gui.otherpanels.bookpanels;

import java.awt.Graphics;
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

        String filePath = TempFileGenerator.generateTempFileOverwriteExisting( currentBookPage.getImageName( ), "png" );

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
    
    @Override
    public void paint( Graphics g ) {

    }


}
