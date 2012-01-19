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
package es.eucm.eadventure.editor.control.controllers;

import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Description;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.descriptions.AddDescriptionTool;
import es.eucm.eadventure.editor.control.tools.descriptions.DuplicateDescriptionTool;
import es.eucm.eadventure.editor.control.tools.descriptions.RemoveDescriptionTool;

public class DescriptionsController {
    
    private List<Description> descriptions;
    
    private List<DescriptionController> descriptionsController;
    
    private int selectedDescription=0;
    
    public DescriptionsController(List<Description> descriptions ){
        this.descriptions = descriptions;
        
        descriptionsController = new ArrayList<DescriptionController>();
        this.descriptions = descriptions;
        
        if (this.descriptions==null||this.descriptions.size( )==0){
            
            Description d = new Description();
            this.descriptions.add( d );
            descriptionsController.add( new DescriptionController( d ) );
            
        } else {
            for (Description d: descriptions){
                descriptionsController.add( new DescriptionController(d) );
            }
        }
        
    }
    
   
   public int getDescriptionCount(){
       return descriptionsController.size( );
   }
    
    public String getName(int index){
       return descriptionsController.get( index ).getName();
       
   }
    
    public int getNumberOfDescriptions(){
        return descriptionsController.size( );
    }
    
    public Description getSelectedDescription(){
        return descriptions.get( selectedDescription );
    }
    
    public DescriptionController getSelectedDescriptionController(){
        return descriptionsController.get( selectedDescription );
    }
    
    public int getSelectedDescriptionNumber(){
        return selectedDescription;
    }
    
    public String getSelectedName(){
        return descriptions.get( selectedDescription ).getName( );
    }

    
    public void setSelectedDescription( int selectedDescription ) {
    
        this.selectedDescription = selectedDescription;
    }
    
    public void addDescription(Description description){
        this.descriptions.add( description );
    }
    
    public void addDescriptionController(DescriptionController desController){
        this.descriptionsController.add( desController );
    }
    
    public void addDescription(Description description, int index){
        this.descriptions.add( index, description );
    }
    
    public void addDescriptionController(DescriptionController desController, int index){
        this.descriptionsController.add( index, desController );
    }
    
    public boolean removeDescription(Description description){
        return this.descriptions.remove( description );
    }
    
    public boolean removeDescriptionController(DescriptionController desController){
        return this.descriptionsController.remove( desController );
    }
    
    /**
     * Remove the selected description and its controller
     * 
     * @return
     *      The deleted description controller
     */
    public DescriptionController removeSelectedDescription(){
        
        descriptions.remove( this.selectedDescription );
        return descriptionsController.remove( this.selectedDescription );
    }
    
    
    
    public DescriptionController getDescriptionController( int index ){
        return descriptionsController.get( index );
    }
    
    public boolean deleteElement( ) {

        return Controller.getInstance().addTool( new RemoveDescriptionTool(this)); 
    }

    public boolean duplicateElement( ) {

        return Controller.getInstance().addTool( new DuplicateDescriptionTool(this));
    }
    
    public boolean addElement(  ) {

        return Controller.getInstance().addTool( new AddDescriptionTool(this));
    }

}
