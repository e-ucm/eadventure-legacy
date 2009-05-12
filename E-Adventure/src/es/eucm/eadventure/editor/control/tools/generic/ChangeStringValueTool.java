package es.eucm.eadventure.editor.control.tools.generic;

import java.lang.reflect.Method;

import es.eucm.eadventure.common.auxiliar.ReportDialog;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.tools.Tool;

/**
 * Generic tool that uses introspection to change a string value
 * @author Javier
 *
 */
public class ChangeStringValueTool extends Tool{

	protected Method get;
	protected Method set;
	protected String getName;
	protected String setName;

	protected String oldValue;
	protected String newValue;
	
	protected Object data;
	
	protected boolean updateTree;
	protected boolean updatePanel;
	
	/**
	 * Default constructor. Will update panel but not tree
	 * @param data 			The object which data is to be modified
	 * @param newValue		The new Value (String)
	 * @param getMethodName The name of the get method. Must follow this pattern: public String getMethodName()
	 * @param setMethodName The name of the set method. Must follow this pattern: public * setMethodName( String )
	 */
	public ChangeStringValueTool (Object data, String newValue, String getMethodName, String setMethodName ) {
		this (data, newValue, getMethodName, setMethodName, false, true);
	}

	public ChangeStringValueTool (Object data, String newValue, String getMethodName, String setMethodName, boolean updateTree, boolean updatePanel) {
		this.data = data;
		this.newValue = newValue;
		this.updatePanel = updatePanel;
		this.updateTree = updateTree;

		try {
			set = data.getClass().getMethod(setMethodName, String.class);
			get = data.getClass().getMethod(getMethodName );
			this.getName = getMethodName;
			this.setName = setMethodName;
			if ( get.getReturnType() != String.class) {
				get = set = null;
				getName = setName = null;
				ReportDialog.GenerateErrorReport(new Exception ("Get method must return String value"), false, TextConstants.getText("Error.Title"));
			}
		} catch (SecurityException e) {
			get = set = null;
			getName = setName = null;
			ReportDialog.GenerateErrorReport(e, false, TextConstants.getText("Error.Title"));
		} catch (NoSuchMethodException e) {
			get = set = null;
			getName = setName = null;
			ReportDialog.GenerateErrorReport(e, false, TextConstants.getText("Error.Title"));			
		}
		
	}
	
	
	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public boolean combine(Tool other) {
		if (other instanceof ChangeStringValueTool) {
			ChangeStringValueTool cnt = (ChangeStringValueTool) other;
			if (cnt.getName.equals(getName) && cnt.setName.equals(setName) && data==cnt.data) {
				newValue = cnt.newValue;
				timeStamp = cnt.timeStamp;
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean doTool() {
		boolean done = false;
		if (get !=null && set !=null){
			// Get the old value
			try {
				oldValue = (String)get.invoke(data);
				if (newValue!=null && oldValue==null ||
						newValue==null && oldValue!=null ||
						(newValue!=null && oldValue!=null && !oldValue.equals(newValue))){
					set.invoke(data, newValue);
					done = true;
				}
			} catch (Exception e) {
				ReportDialog.GenerateErrorReport(e, false, TextConstants.getText("Error.Title"));	
			} 
			
		}
		return done;
	}

	@Override
	public boolean redoTool() {
		boolean  done = false;
		try {
			set.invoke(data, newValue);
			if (updateTree)
				Controller.getInstance().updateStructure();
			if (updatePanel)
				Controller.getInstance().updatePanel();
			done = true;
		} catch (Exception e) {
			ReportDialog.GenerateErrorReport(e, false, TextConstants.getText("Error.Title"));	
		} 
		return done;
	}

	@Override
	public boolean undoTool() {
		boolean  done = false;
		try {
			set.invoke(data, oldValue);
			if (updateTree)
				Controller.getInstance().updateStructure();
			if (updatePanel)
				Controller.getInstance().updatePanel();
			done = true;
		} catch (Exception e) {
			ReportDialog.GenerateErrorReport(e, false, TextConstants.getText("Error.Title"));	
		} 
		return done;
	}

}
