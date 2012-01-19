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
package es.eucm.eadventure.editor.control.tools.general.commontext;

import es.eucm.eadventure.common.auxiliar.AssetsConstants;
import es.eucm.eadventure.common.data.HasDescriptionSound;
import es.eucm.eadventure.common.data.chapter.resources.Resources;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.tools.general.assets.SelectResourceTool;
import es.eucm.eadventure.editor.data.AssetInformation;


public class ChangeDescriptionSoundTool  extends SelectResourceTool {

    
    
    protected static final String AUDIO_STR = "audio";
    
    private HasDescriptionSound descriptionSound;

  //  private String oldName;

    private Controller controller = Controller.getInstance( );
    
    private int type;
    
    protected static AssetInformation[] createAssetInfoArray( ) {

        AssetInformation[] array = new AssetInformation[ 1 ];
        array[0] = new AssetInformation( "", AUDIO_STR, true, AssetsConstants.CATEGORY_AUDIO, AssetsController.FILTER_NONE );
        return array;
    }

    protected static Resources createResources( HasDescriptionSound descriptionSound, int type ) {

        Resources resources = new Resources( );
        
        String selectedName;
        
        switch (type){
            case HasDescriptionSound.NAME_PATH:
                selectedName = descriptionSound.getNameSoundPath( );
                if( selectedName!=null  ) {
                    resources.addAsset( AUDIO_STR, selectedName );
                }
                return resources;
            case HasDescriptionSound.DESCRIPTION_PATH:
                selectedName = descriptionSound.getDescriptionSoundPath( );
                if( selectedName!=null  ) {
                    resources.addAsset( AUDIO_STR, selectedName );
                }
                return resources;
            case HasDescriptionSound.DETAILED_DESCRIPTION_PATH:
                selectedName = descriptionSound.getDetailedDescriptionSoundPath( );
                if( selectedName!=null  ) {
                    resources.addAsset( AUDIO_STR, selectedName );
                }
                return resources;
           default:
                return resources;
        }
        
    }
    
    public ChangeDescriptionSoundTool(HasDescriptionSound descrSound,  int type) throws CloneNotSupportedException{
       
        super( createResources( descrSound, type ), createAssetInfoArray( ), 0 , 0 );
        
        this.descriptionSound = descrSound;
        this.type = type;
        
    }

    
    
    @Override
    public boolean doTool( ) {
        
        //String selectedName;
        
        boolean done = super.doTool( );
        if( !done )
            return false;
        else {
            
        switch (type){
            case HasDescriptionSound.NAME_PATH:
                descriptionSound.setNameSoundPath( resources.getAssetPath( AUDIO_STR ));
                controller.updatePanel( );
                return true;
                
            case HasDescriptionSound.DESCRIPTION_PATH:
                descriptionSound.setDescriptionSoundPath( resources.getAssetPath( AUDIO_STR ) );
                controller.updatePanel( );
                return true;
                
            case HasDescriptionSound.DETAILED_DESCRIPTION_PATH:
                descriptionSound.setDetailedDescriptionSoundPath( resources.getAssetPath( AUDIO_STR ) );
                controller.updatePanel( );
                return true;
                
            default:
                return false;
            }
        
        }// end else
        
          
    }

    @Override
    public boolean redoTool( ) {

        boolean done = super.redoTool( );
        if( !done )
            return false;
        else {
            
        switch (type){
            case HasDescriptionSound.NAME_PATH:    
                descriptionSound.setNameSoundPath( resources.getAssetPath( AUDIO_STR ));
                controller.updatePanel( );
                return true;
            
            case HasDescriptionSound.DESCRIPTION_PATH:
                descriptionSound.setDescriptionSoundPath( resources.getAssetPath( AUDIO_STR ) );
                controller.updatePanel( );
                return true;
            case HasDescriptionSound.DETAILED_DESCRIPTION_PATH:
                descriptionSound.setDetailedDescriptionSoundPath( resources.getAssetPath( AUDIO_STR ) );
                controller.updatePanel( );
                return true;
                
            default:
                return false;
            }
        
        }// end else
        
    }

    @Override
    public boolean undoTool( ) {
       
        boolean done = super.undoTool( );
        if( !done )
            return false;
        else {
            
        switch (type){
            case HasDescriptionSound.NAME_PATH:
                descriptionSound.setNameSoundPath( resources.getAssetPath( AUDIO_STR ));
                controller.updatePanel( );
                return true;
                    
            case HasDescriptionSound.DESCRIPTION_PATH:
                descriptionSound.setDescriptionSoundPath( resources.getAssetPath( AUDIO_STR ) );
                controller.updatePanel( );
                return true;
                
            case HasDescriptionSound.DETAILED_DESCRIPTION_PATH:
                descriptionSound.setDetailedDescriptionSoundPath( resources.getAssetPath( AUDIO_STR ) );
                controller.updatePanel( );
                return true;
                
            default:
                return false;
            }
        
        }// end else
        
    }

}
