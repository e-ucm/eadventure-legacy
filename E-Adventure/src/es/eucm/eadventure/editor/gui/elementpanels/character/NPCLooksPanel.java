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
package es.eucm.eadventure.editor.gui.elementpanels.character;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import es.eucm.eadventure.common.data.chapter.elements.NPC;
import es.eucm.eadventure.common.gui.TC;
import es.eucm.eadventure.common.loader.Loader;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.DataControlWithResources;
import es.eucm.eadventure.editor.control.controllers.EditorImageLoader;
import es.eucm.eadventure.editor.control.controllers.character.NPCDataControl;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.elementpanels.general.LooksPanel;
import es.eucm.eadventure.editor.gui.otherpanels.AnimationPanel;

public class NPCLooksPanel extends LooksPanel implements Updateable {

    public static final int STANDING = 0;

    public static final int TALKING = 1;

    public static final int USING = 2;

    public static final int WALKING = 3;

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * Preview image panel.
     */
    private JPanel imagePanel;

    private NPCDataControl npcDataControl;

    public NPCLooksPanel( DataControlWithResources control ) {

        super( control );
    }

    @Override
    protected void createPreview( ) {

        npcDataControl = (NPCDataControl) this.dataControl;

        if( imagePanel != null )
            lookPanel.remove( imagePanel );

        imagePanel = createImagePanel( );

        lookPanel.add( imagePanel, cLook );

        lookPanel.setPreferredSize( new Dimension( 0, 800 ) );
    }

    @Override
    public void updatePreview( ) {

        if( imagePanel != null )
            lookPanel.remove( imagePanel );

        imagePanel = createImagePanel( );

        lookPanel.add( imagePanel, cLook );

        lookPanel.repaint( );

    }

    private JPanel createImagePanel( ) {

        imagePanel = new JPanel( );

        String animationPath1 = null, title1 = null;
        String animationPath2 = null, title2 = null;
        String animationPath3 = null, title3 = null;
        String animationPath4 = null, title4 = null;
        if( this.selectedResourceGroup == WALKING ) {
            imagePanel.setLayout( new GridLayout( 1, 4 ) );
            title2 = TC.get( "Resources.DescriptionCharacterAnimationWalkRight" );
            animationPath2 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_WALK_RIGHT );
            title1 = TC.get( "Resources.DescriptionCharacterAnimationWalkLeft" );
            animationPath1 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_WALK_LEFT );
            title3 = TC.get( "Resources.DescriptionCharacterAnimationWalkUp" );
            animationPath3 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_WALK_UP );
            title4 = TC.get( "Resources.DescriptionCharacterAnimationWalkDown" );
            animationPath4 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_WALK_DOWN );
        }
        else if( this.selectedResourceGroup == STANDING ) {
            imagePanel.setLayout( new GridLayout( 1, 4 ) );
            title2 = TC.get( "Resources.DescriptionCharacterAnimationStandRight" );
            animationPath2 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_STAND_RIGHT );
            title1 = TC.get( "Resources.DescriptionCharacterAnimationStandLeft" );
            animationPath1 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_STAND_LEFT );
            title3 = TC.get( "Resources.DescriptionCharacterAnimationStandUp" );
            animationPath3 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_STAND_UP );
            title4 = TC.get( "Resources.DescriptionCharacterAnimationStandDown" );
            animationPath4 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_STAND_DOWN );
        }
        else if( this.selectedResourceGroup == TALKING ) {
            imagePanel.setLayout( new GridLayout( 1, 4 ) );
            title2 = TC.get( "Resources.DescriptionCharacterAnimationSpeakRight" );
            animationPath2 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_SPEAK_RIGHT );
            title1 = TC.get( "Resources.DescriptionCharacterAnimationSpeakLeft" );
            animationPath1 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_SPEAK_LEFT );
            title3 = TC.get( "Resources.DescriptionCharacterAnimationSpeakUp" );
            animationPath3 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_SPEAK_UP );
            title4 = TC.get( "Resources.DescriptionCharacterAnimationSpeakDown" );
            animationPath4 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_SPEAK_DOWN );
        }
        else if( this.selectedResourceGroup == USING ) {
            imagePanel.setLayout( new GridLayout( 1, 2 ) );
            title2 = TC.get( "Resources.DescriptionCharacterAnimationUseRight" );
            animationPath2 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_USE_RIGHT );
            title1 = TC.get( "Resources.DescriptionCharacterAnimationUseLeft" );
            animationPath1 = npcDataControl.getAnimationPath( NPC.RESOURCE_TYPE_USE_LEFT );
        }

        imagePanel.add( createAnimationPanel( animationPath1, title1 ) );
        imagePanel.add( createAnimationPanel( animationPath2, title2 ) );
        if( this.selectedResourceGroup != USING ) {
            imagePanel.add( createAnimationPanel( animationPath3, title3 ) );
            imagePanel.add( createAnimationPanel( animationPath4, title4 ) );
        }

        return imagePanel;
    }

    private JPanel createAnimationPanel( String animationPath, String title ) {

        JPanel temp = new JPanel( );
        temp.setLayout( new BorderLayout( ) );
        temp.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), title ) );
        if( animationPath != null ) {
            if( animationPath.endsWith( ".eaa" ) )
                temp.add( new AnimationPanel( false, Loader.loadAnimation( AssetsController.getInputStreamCreator( ), animationPath, new EditorImageLoader()  ) ), BorderLayout.CENTER );
            else
                temp.add( new AnimationPanel( false, animationPath + "_01.png" ), BorderLayout.CENTER );
        }
        else
            temp.add( new AnimationPanel( false, "" ), BorderLayout.CENTER );

        return temp;
    }

    @Override
    public void updateResources( ) {

        super.updateResources( );
        if( getParent( ) != null && getParent( ).getParent( ) != null )
            getParent( ).getParent( ).repaint( );
    }

}
