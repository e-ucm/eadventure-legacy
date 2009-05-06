package es.eucm.eadventure.editor.gui.editdialogs.effectdialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.controllers.EffectsController;


/**
 * This class represents a dialog used to add and edit the time in WaitTimeEffect.
 * 
 */
public class WaitTimeEffectDialog extends EffectDialog{

    /**
     * Required.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The spinner to store the time (in seconds) to wait without do nothing
     */
    private JSpinner time;
    
    /**
     * Constructor.
     * 
     * @param currentProperties
     *     Set of initial values
     */
    public WaitTimeEffectDialog(HashMap<Integer, Object> currentProperties){
	// Call the super method
	super( TextConstants.getText( "WaitTimeEffect.Title" ) , true);
	
	// Create the main panel
	JPanel mainPanel = new JPanel( );
	mainPanel.setLayout( new GridBagLayout( ) );
	GridBagConstraints c = new GridBagConstraints( );
	
	// Set the border of the panel with the description
	mainPanel.setBorder( BorderFactory.createCompoundBorder( BorderFactory.createEmptyBorder( 5, 5, 0, 5 ), BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "WaitTimeEffect.Border" ) ) ) );

	JLabel label = new JLabel(TextConstants.getText("WaitTimeEffect.Label"));
	
	
	// Set the defualt values (if present)
	if( currentProperties != null ) {
	    int timeValue=0;
	    if ( currentProperties.containsKey( EffectsController.EFFECT_PROPERTY_TIME  ) )
		timeValue = Integer.parseInt( (String)currentProperties.get( EffectsController.EFFECT_PROPERTY_TIME ) );
	    time = new JSpinner(new SpinnerNumberModel(timeValue,0,Integer.MAX_VALUE,1));
	}else {
	    time = new JSpinner(new SpinnerNumberModel(0,0,Integer.MAX_VALUE,1));
	}
	c.gridx=0;
	c.gridy=0;
	mainPanel.add(label,c);
	c.gridy++;
	mainPanel.add(time,c);
	
	add(mainPanel,BorderLayout.CENTER);
	// Set the dialog
	setResizable( false );
	setSize( 250, 150);
	Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
	setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
	setVisible( true );
    }
    
   
    
    protected void pressedOKButton() {
	// Create a set of properties, and put the selected value
	properties = new HashMap<Integer, Object>( );
	properties.put( EffectsController.EFFECT_PROPERTY_TIME, Integer.toString((Integer)time.getModel().getValue() ));
	
    }

}
