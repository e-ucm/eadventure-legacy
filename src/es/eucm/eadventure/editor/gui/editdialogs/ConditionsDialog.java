package es.eucm.eadventure.editor.gui.editdialogs;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import es.eucm.eadventure.common.data.chapter.conditions.Condition;
import es.eucm.eadventure.common.data.chapter.conditions.Conditions;
import es.eucm.eadventure.common.data.chapter.conditions.FlagCondition;
import es.eucm.eadventure.common.data.chapter.conditions.GlobalStateCondition;
import es.eucm.eadventure.common.data.chapter.conditions.VarCondition;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.ConditionsController;
import es.eucm.eadventure.editor.gui.Updateable;
import es.eucm.eadventure.editor.gui.elementpanels.condition.ConditionsPanel2;
import es.eucm.eadventure.editor.gui.elementpanels.general.ConditionsPanel;

/**
 * This class is the editing dialog for the conditions. Here the user can add conditions to the events of the script,
 * using the flags defined in the Flags dialog.
 * 
 * @author Bruno Torijano Bueno
 */
public class ConditionsDialog extends JDialog/*ToolManagableDialog*/{

	/**
	 * Required.
	 */
	private static final long serialVersionUID = 1L;
	
	private ConditionsPanel conditionsPanel;
	
	private ConditionsPanel2 conditionsPanel2;


	/**
	 * Constructor.
	 * 
	 * @param conditionsController
	 *            Controller for the conditions
	 */
	public ConditionsDialog( ConditionsController conditionsController ) {

		// Call to the JDialog constructor
		super( new JFrame()/*Controller.getInstance( ).peekWindow( )*/, TextConstants.getText( "Conditions.Title" ), false );

		// Create the main panel and add it
		setLayout( new GridBagLayout( ) );
		GridBagConstraints c = new GridBagConstraints( );
		c.insets = new Insets( 5, 5, 5, 5 );
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		c.weighty = 1;
		conditionsPanel2 = new ConditionsPanel2( conditionsController);
		add( conditionsPanel2, c );

		// Set the size, position and properties of the dialog
		//setResizable( false );
		setSize( 600, 400 );
		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible( true );
	}
	
	//@Override
	public boolean updateFields() {
		//this.removeAll();
		if (conditionsPanel!=null)
			return conditionsPanel.updateFields();
		else 
			return conditionsPanel2.updateFields();
	}
	
	public static void main (String[]args){
		// Set the look and feel
		try {
			//UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName( ) );
		} catch( Exception e ) {
        	//ErrorReportDialog.GenerateErrorReport(e, true, "UNKNOWERROR");
		}
		
		
		try {
			TextConstants.loadStrings("laneditor/en_EN.xml");
			
			Conditions conditions = new Conditions();
			FlagCondition condition = new FlagCondition("Flag1");
			VarCondition condition2 = new VarCondition("Var1", VarCondition.VAR_EQUALS, 2);
			GlobalStateCondition condition3 = new GlobalStateCondition( "GS1" );

			FlagCondition condition4= (FlagCondition)condition.clone();
			VarCondition condition5= (VarCondition)condition2.clone();
			GlobalStateCondition condition6= (GlobalStateCondition)condition3.clone();
			
			Conditions either = new Conditions();
			either.add(condition4);
			either.add(condition5);
			either.add(condition6);
			
			Conditions either2 = (Conditions)either.clone();
			conditions.add(condition);
			conditions.add(either);
			conditions.add(condition2);
			conditions.add(either2);
			conditions.add(condition3);
			
			ConditionsController controller = new ConditionsController(conditions);
			ConditionsDialog dialog = new ConditionsDialog(controller);
			
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		
		
	}
}
