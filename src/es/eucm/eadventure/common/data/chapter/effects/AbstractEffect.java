package es.eucm.eadventure.common.data.chapter.effects;

import es.eucm.eadventure.common.data.chapter.conditions.Conditions;

public abstract class AbstractEffect implements Effect{

    private Conditions conditions;
    
    
    public AbstractEffect(){
	conditions = new Conditions();
    }
    
    /**
     * @return the conditions
     */
    public Conditions getConditions() {
        return conditions;
    }


    /**
     * @param conditions the conditions to set
     */
    public void setConditions(Conditions conditions) {
        this.conditions = conditions;
    }


    /**
    * Returns the type of the effect.
    * 
    * @return Type of the effect
    */
    public abstract int getType( );

    
    public Object clone() throws CloneNotSupportedException{
	return super.clone();
    }
    
}
