package es.eucm.eadventure.engine.core.control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import es.eucm.eadventure.common.data.chapter.elements.Atrezzo;
import es.eucm.eadventure.common.data.chapter.elements.Item;


/**
 * Summary of the items in the adventure 
 */
public class AtrezzoSummary implements Serializable {

    /**
     * Required by Serializable
     */
    private static final long serialVersionUID = 1L;

    /**
     * List of normal atrezzo items
     */
    private ArrayList<String> normalAtrezzoItems;


    /**
     * Default constructor
     * @param items List of items, which will be stored as normal items
     */
    public AtrezzoSummary( List<Atrezzo> atrezzoItems ) {
        normalAtrezzoItems = new ArrayList<String>( );
        

        for( Atrezzo atrezzo : atrezzoItems ) {
            normalAtrezzoItems.add( atrezzo.getId( ) );
        }
    }
    

    

}

