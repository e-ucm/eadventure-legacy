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

package es.eucm.eadventure.engine.gamelog;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Test {

    public static void main (String[]args){
        Map<String, List<String>> keys= new HashMap<String, List<String>>();
        
        Field[] fields = HighLevelEvents.class.getFields( );
        for (Field field:fields){
            String key;
            try {
                key = field.get( null ).toString( );
                if (keys.containsKey( key )){
                    keys.get( key ).add( field.getName( ) );
                } else {
                    List<String> newList = new ArrayList<String>();
                    newList.add( field.getName( ) );
                    keys.put( key, newList );
                }
            }
            catch( IllegalArgumentException e ) {
                e.printStackTrace();
            }
            catch( IllegalAccessException e ) {
                e.printStackTrace();
            }
            
        }
        
        // Print duplicates
        for (String key:keys.keySet( )){
            if (keys.get( key ).size( )>1){
                System.out.println(key);
                for (String fieldName: keys.get( key )){
                    System.out.print( "\t" );System.out.println( fieldName );
                }
            }
        }
    }
}
