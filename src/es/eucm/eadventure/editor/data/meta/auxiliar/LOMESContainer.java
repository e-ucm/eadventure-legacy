package es.eucm.eadventure.editor.data.meta.auxiliar;

import java.util.ArrayList;

public abstract class LOMESContainer {

	
	
	protected int currentElement;
	
	protected ArrayList<LOMESComposeType> container;
	
	
	protected LOMESContainer(){
		container = new ArrayList<LOMESComposeType>();
	}
	
	/**
	 * The title for create element dialog
	 * @return
	 */
	public abstract String getTitle();
	
	/**
	 * Returns an String representation of each element in container
	 * @return
	 */
	public abstract String[] elements();
	
	/**
	 * Returns each attributes type. 
	 * @return
	 */
	public abstract String[] attributes();

	
	public void add(LOMESComposeType newComponent){
		container.add(newComponent);
	}
	
	public void add(LOMESComposeType newComponent,int index){
		container.add(index,newComponent);
	}
	public void delete(int index){
		container.remove(index);
	}
	
	public LOMESComposeType get(int index){
		return container.get(index);
	}
	
	public int getSize(){
		return container.size();
	}
	
	
	public LOMESComposeType getCurrentElement() {
		return container.get(currentElement);
	}

	public void setCurrentElement(int currentElement) {
		this.currentElement = currentElement;
	}

}
