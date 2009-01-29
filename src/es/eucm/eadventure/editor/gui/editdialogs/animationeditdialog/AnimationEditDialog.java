package es.eucm.eadventure.editor.gui.editdialogs.animationeditdialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import es.eucm.eadventure.common.data.animation.Animation;
import es.eucm.eadventure.common.data.animation.Frame;
import es.eucm.eadventure.common.gui.TextConstants;
import es.eucm.eadventure.editor.control.Controller;
import es.eucm.eadventure.editor.control.controllers.AssetsController;
import es.eucm.eadventure.editor.control.controllers.animation.AnimationDataControl;
import es.eucm.eadventure.editor.control.writer.AnimationWriter;
import es.eucm.eadventure.editor.gui.displaydialogs.AnimationDialog;

/**
 * This class shows an dialog to edit an animation
 * 
 * @author Eugenio Marchiori
 *
 */
public class AnimationEditDialog extends JDialog {
	
	/**
	 * Default generated serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The list that shows the frames in the animation
	 */
	private JList frameList;
	
	/**
	 * The panel where the frame list is displayed along with the buttons
	 * to order said frames and delete them
	 */
	private JPanel frameListPanel;
	
	/**
	 * The panel where the description and general information of the animation is displayed
	 */
	private JPanel descriptionPanel;
	
	/**
	 * The panel where the configuration of the current frame or transition is displayed
	 */
	private JPanel configurationPanel;
		
	/**
	 * The button to move a frame to the left
	 */
	private JButton moveLeftButton;
	
	/**
	 * The button to move a frame to the right
	 */
	private JButton moveRightButton;
	
	/**
	 * The button to delete a frame
	 */
	private JButton deleteButton;
	
	/**
	 * The button to add a frame 
	 */
	private JButton addButton;
	
	/**
	 * JCheckBox to set the useTransitions property of the animation
	 */
	private JCheckBox useTransitions;
	
	/**
	 * JCheckBox to set the slides property of the animation
	 */
	private JCheckBox slides;
	
	/**
	 * The text field that displays the documentation of the animation
	 */
	private JTextField documentationTextField;
	
	/**
	 * The data control for the animation
	 */
	private AnimationDataControl animationDataControl;

	/**
	 * FrameConfigPanel for the selected frame
	 */
	private FrameConfigPanel frameConfigPanel;
	
	/**
	 * Empty constructor. Creates a new animation.
	 */
	public AnimationEditDialog() {
		super();
		animationDataControl = new AnimationDataControl(new Animation("id", new Frame()));
		buildInterface();
	}
	
	/**
	 * Constructor with a filename and optional animation. If the animation
	 * is null, it tries to open it form the given file.
	 * 
	 * @param filename
	 * Path to the eaa(xml) with the animation
	 * @param animation
	 * The animation to edit (can be null)
	 */
	public AnimationEditDialog(String filename, Animation animation) {
		super();
		if (animation == null) {
			animationDataControl = new AnimationDataControl(Animation.loadAnimation(AssetsController.getInputStreamCreator(), filename));
		} else {
			animationDataControl = new AnimationDataControl(animation);
		}
		animationDataControl.setFilename(filename);

		// Push the dialog into the stack, and add the window listener to pop in when closing
		Controller.getInstance( ).pushWindow( this );
		addWindowListener( new WindowAdapter( ) {
			public void windowClosing( WindowEvent e ) {
				Controller.getInstance( ).popWindow( );
			}
		} );

		
		buildInterface();
	}

	/**
	 * Build the edition interface with all of it's components
	 */
	private void buildInterface() {
		//this.setLayout(new BorderLayout());
		this.setLayout(new GridBagLayout());
		this.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setModal(true);
		this.setResizable(false);
		this.setTitle(TextConstants.getText("Animation.DialogTitle", animationDataControl.getFilename()));
		
		createDescriptionPanel();
		

		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.weighty = 0.5;
		this.add(descriptionPanel, gbc);//, BorderLayout.NORTH);
		
		AnimationListModel listModel = new AnimationListModel(animationDataControl.getAnimation());
		
		frameList = new JList(listModel);
		//frameList.add(new JLabel("hola"));
		frameList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		frameList.getSelectionModel().addListSelectionListener(new AnimationListSelectionListener(this));
		frameList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		frameList.setVisibleRowCount(-1);
		frameList.setCellRenderer(new AnimationListRenderer());
		frameList.setVisibleRowCount(1);
		JScrollPane listScroller = new JScrollPane(frameList);
		listScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		listScroller.setPreferredSize(new Dimension(520, 160));
		
		frameListPanel = new JPanel();
		frameListPanel.add(listScroller, BorderLayout.CENTER);
		
		frameListPanel.add(createButtonPanel(), BorderLayout.SOUTH);
		
		frameListPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Animation.Timeline" ) ) );
		frameListPanel.setMinimumSize(new Dimension(600, 240));
		gbc.gridy = 1;
		gbc.weighty = 1;
		this.add(frameListPanel, gbc);//, BorderLayout.CENTER);
		
		
		configurationPanel = new JPanel();
		configurationPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Animation.Details" ) ));
		configurationPanel.setMinimumSize(new Dimension(600, 180));
		gbc.gridy = 2;
		gbc.weighty = 0.7;
		this.add(configurationPanel, gbc);//, BorderLayout.SOUTH);

		JPanel acceptCancelPanel = new JPanel();		
		
		JButton preview = new JButton(TextConstants.getText( "Animation.Preview" ));
		preview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new AnimationDialog(animationDataControl.getAnimation());
			}
		});
		acceptCancelPanel.add(preview);

		acceptCancelPanel.setMinimumSize(new Dimension(600, 50));
		JButton accept = new JButton(TextConstants.getText("GeneralText.OK"));
		accept.addActionListener( new ActionListener() {
			public void actionPerformed( ActionEvent e) {
				saveAndClose();
			}
		});
		acceptCancelPanel.add(accept);

		JButton cancel = new JButton(TextConstants.getText("GeneralText.Cancel"));
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed( ActionEvent e ) {
				AnimationEditDialog.this.setVisible(false);
			}
		});
		acceptCancelPanel.add(cancel);
		
		gbc.gridy = 3;
		gbc.weighty = 0.3;
		this.add(acceptCancelPanel, gbc);
		
		this.setSize(600, 650);		

		Dimension screenSize = Toolkit.getDefaultToolkit( ).getScreenSize( );
		setLocation( ( screenSize.width - getWidth( ) ) / 2, ( screenSize.height - getHeight( ) ) / 2 );
		setVisible(true);

	}
		
	/**
	 * Creates the description panel, where all the elements that describe
	 * the animation are placed
	 */
	private void createDescriptionPanel() {
		descriptionPanel = new JPanel();
		descriptionPanel.setLayout(new GridLayout(2,1));
		
				
		JPanel temp = new JPanel();
		temp.setLayout(new GridBagLayout());
		
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.weightx = 0.1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		temp.add(new JLabel(TextConstants.getText( "Animation.Documentation" )), gbc);
	
		gbc.gridx = 1;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		documentationTextField = new JTextField(animationDataControl.getAnimation().getDocumentation());
		temp.add(documentationTextField, gbc);

		descriptionPanel.add(temp);

		temp = new JPanel();
		temp.setLayout(new GridBagLayout());
		
		gbc = new GridBagConstraints();

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.NONE;
		useTransitions = new JCheckBox(TextConstants.getText("Animation.UseTransitions"));
		if (animationDataControl.getAnimation().isUseTransitions())
			useTransitions.setSelected(true);
		else
			useTransitions.setSelected(false);
		useTransitions.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				changeUseTransitions();
			}
		});
		temp.add(useTransitions, gbc);
		
		gbc.gridx = 1;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.NONE;
		slides = new JCheckBox(TextConstants.getText("Animation.Slides"));
		if (animationDataControl.getAnimation().isSlides())
			slides.setSelected(true);
		else
			slides.setSelected(false);
		slides.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				changeSlides();
			}
		});		
		temp.add(slides, gbc);
		
		
		descriptionPanel.add(temp);
		descriptionPanel.setBorder( BorderFactory.createTitledBorder( BorderFactory.createEtchedBorder( ), TextConstants.getText( "Animation.GeneralInfo" ) ) );
		descriptionPanel.setMinimumSize(new Dimension(600, 100));

	}

	protected void changeSlides() {
		if (slides.isSelected() != animationDataControl.getAnimation().isSlides()) {
			animationDataControl.getAnimation().setSlides(slides.isSelected());
			frameList.getSelectionModel().clearSelection();
			configurationPanel.removeAll();
			this.validate();
			this.repaint();
		}
	}

	protected void changeUseTransitions() {
		if (useTransitions.isSelected() != animationDataControl.getAnimation().isUseTransitions()) {
			animationDataControl.getAnimation().setUseTransitions(useTransitions.isSelected());
			frameList.getSelectionModel().clearSelection();
			frameList.updateUI();
			configurationPanel.removeAll();
			this.validate();
			this.repaint();
		}
	}

	/**
	 * Creates the button panel, where the buttons used for the manipulation
	 * of the timeline are placed
	 * 
	 * @return A JPanel with the needed elements
	 */
	private JPanel createButtonPanel() {
		JPanel buttons = new JPanel();
		moveLeftButton = new JButton(new ImageIcon("img/icons/moveNodeLeft.png"));
		moveLeftButton.setContentAreaFilled( false );
		moveLeftButton.setMargin( new Insets(0,0,0,0) );
		moveLeftButton.setToolTipText( TextConstants.getText( "Animation.MoveFrameLeft" ) );
		moveLeftButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				moveFrameLeft();
			}
		});
		moveLeftButton.setEnabled(false);
		buttons.add(moveLeftButton);
		
		deleteButton = new JButton(new ImageIcon("img/icons/deleteNode.png"));
		deleteButton.setContentAreaFilled( false );
		deleteButton.setMargin( new Insets(0,0,0,0) );
		deleteButton.setToolTipText( TextConstants.getText( "Animation.DeleteFrame" ) );
		deleteButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				deleteFrame();
			}
		});
		deleteButton.setEnabled(false);
		buttons.add(deleteButton);

		addButton = new JButton(new ImageIcon("img/icons/addNode.png"));
		addButton.setContentAreaFilled( false );
		addButton.setMargin( new Insets(0,0,0,0) );
		addButton.setToolTipText( TextConstants.getText( "Animation.AddFrame" ) );
		addButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				addFrame();
			}
		});
		buttons.add(addButton);

		
		moveRightButton = new JButton(new ImageIcon("img/icons/moveNodeRight.png"));
		moveRightButton.setContentAreaFilled( false );
		moveRightButton.setMargin( new Insets(0,0,0,0) );
		moveRightButton.setToolTipText( TextConstants.getText( "Animation.MoveFrameRight" ) );
		moveRightButton.addActionListener( new ActionListener(){
			public void actionPerformed( ActionEvent e ) {
				moveFrameRight();
			}
		});
		moveRightButton.setEnabled(false);
		buttons.add(moveRightButton);

		return buttons;
	}
	
	/**
	 * Save changes to the eaa file and close the edition panel
	 */
	protected void saveAndClose() {
		if (animationDataControl.getFilename() != null) {
			animationDataControl.getAnimation().setDocumentation(documentationTextField.getText());
			AnimationWriter.writeAnimation(animationDataControl.getFilename(), animationDataControl.getAnimation());
		}
		this.setVisible(false);
	}

	/**
	 * Method to add a new frame to the animation. If a frame is selected it adds it
	 * right after it.
	 */
	protected void addFrame() {
		Frame newFrame = new Frame();

		int index = frameList.getSelectedIndex() / 2;
		if (!animationDataControl.getAnimation().isUseTransitions()) {
			index = frameList.getSelectedIndex();
		}
		if (frameList.getSelectedIndex() == -1)
			index = animationDataControl.getAnimation().getFrames().size() - 1;
		
		
		animationDataControl.getAnimation().addFrame(index, newFrame);
		frameList.updateUI();
		int newFrameIndex = animationDataControl.getAnimation().getFrames().indexOf(newFrame);
		frameList.setSelectedIndex(newFrameIndex);
		if (frameConfigPanel!=null)
			frameConfigPanel.selectImage();
	}

	/**
	 * Delete the selected frame from the list
	 */
	protected void deleteFrame() {
		if (frameList.getSelectedValue() instanceof Frame) {
			int index = animationDataControl.getAnimation().getFrames().indexOf(frameList.getSelectedValue());
			animationDataControl.getAnimation().removeFrame(index);
			frameList.getSelectionModel().clearSelection();
			configurationPanel.removeAll();
			this.validate();
			this.repaint();
//			frameList.updateUI();
		}
	}

	/**
	 * Move the selected frame in the list to the left
	 */
	protected void moveFrameLeft() {
	/*	int index = frameList.getSelectedIndex() / 2;
		Frame temp = animationDataControl.getAnimation().getFrame(index);
		animationDataControl.getAnimation().getFrames().remove(index);
		animationDataControl.getAnimation().getFrames().add(index - 1, temp);
		frameList.setSelectedValue(temp, true);
	*/
		Frame temp = (Frame) frameList.getSelectedValue();
		int index = animationDataControl.getAnimation().getFrames().indexOf(temp);
		animationDataControl.getAnimation().getFrames().remove(temp);
		animationDataControl.getAnimation().getFrames().add(index - 1, temp);
		frameList.setSelectedValue(temp, true);
		frameList.updateUI();
	}

	/**
	 * Move the selected frame in the list to the right
	 */
	protected void moveFrameRight() {
		Frame temp2 = (Frame) frameList.getSelectedValue();
		int index = animationDataControl.getAnimation().getFrames().indexOf(temp2);
		Frame temp = animationDataControl.getAnimation().getFrame(index + 1);
		animationDataControl.getAnimation().getFrames().remove(index + 1);
		animationDataControl.getAnimation().getFrames().add(index, temp);
		frameList.setSelectedValue(temp2, true);
		frameList.updateUI();
	}

	
	/**
	 * Class that holds the internal representation of the list
	 * of frames and transitions
	 *
	 */
	private class AnimationListModel extends AbstractListModel {

		/**
		 * 
		 * 
		 */
		private static final long serialVersionUID = -2832912217451105062L;

		private Animation animation;
		
		public AnimationListModel(Animation animation) {
			super();
			this.animation = animation;
		}
				
		public Object getElementAt(int index) {
			if (animation.isUseTransitions()) {
				if (index % 2 == 0) {
					return animation.getFrame(index / 2);
				} else {
					return animation.getTransitions().get((index - 1) / 2 + 1);
				}
			} else {
				return animation.getFrame(index);
			}
		}

		public int getSize() {
			if (animation.isUseTransitions())
				return (animation.getFrames().size() * 2) - 1;
			else
				return animation.getFrames().size();
		}
		
		
	}
	
	/**
	 *	This class responds to the changes of selection in the list
	 *  of frames
	 *
	 */
	private class AnimationListSelectionListener implements ListSelectionListener {

		private AnimationEditDialog aed;
		
		public AnimationListSelectionListener(AnimationEditDialog aed) {
			this.aed = aed;
		}
		
		public void valueChanged(ListSelectionEvent e) {
	        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
	        if (lsm.isSelectionEmpty()) {
	        } else {
	            int index = lsm.getMinSelectionIndex();
	            aed.selectionChanged(index);
	        }
		}
		
	}

	/**
	 * Method called when the selection changes with the index of the selected item
	 * 
	 * @param index the index of the selection
	 */
	public void selectionChanged(int index) {
		if (animationDataControl.getAnimation().isUseTransitions()) {
	        if (index < 0 || index >= (animationDataControl.getAnimation().getFrames().size() * 2) + 1) {
	        	selectedNothing();
	        	return;
	        }
	        if (index % 2 == 0) {
	        	selectedFrame(index / 2);
	        }
	        else {
	        	selectedTransition((index - 1) / 2 + 1);
	        }
		} else {
	        if (index < 0 || index >= animationDataControl.getAnimation().getFrames().size()) {
	        	selectedNothing();
	        	return;
	        }
	        selectedFrame(index);
		}
    }

	/**
	 * Method called when a frame is selected
	 * 
	 * @param i
	 * 		index of the selected frame
	 */
	public void selectedFrame(int i) {
		if (i < animationDataControl.getAnimation().getFrames().size() - 1)
			moveRightButton.setEnabled(true);
		else
			moveRightButton.setEnabled(false);
		if (i > 0)
			moveLeftButton.setEnabled(true);
		else
			moveLeftButton.setEnabled(false);
		deleteButton.setEnabled(true);
		
		configurationPanel.removeAll();
		frameConfigPanel = new FrameConfigPanel(animationDataControl.getAnimation().getFrame(i), frameList, this);
		configurationPanel.add(frameConfigPanel);
		this.validate();
		this.repaint();
	}

	/**
	 * Method called when the selection is changed to nothing
	 */
	public void selectedNothing() {
		moveRightButton.setEnabled(false);
		moveLeftButton.setEnabled(false);
		deleteButton.setEnabled(false);	
		configurationPanel.removeAll();
		this.validate();
		this.repaint();
		
	}

	/**
	 * Method called when a transition is selected
	 * @param i
	 * 		index of the selected transition
	 */
	public void selectedTransition(int i) {
		moveRightButton.setEnabled(false);
		moveLeftButton.setEnabled(false);
		deleteButton.setEnabled(false);

		configurationPanel.removeAll();
		configurationPanel.add(new TransitionConfigPanel(animationDataControl.getAnimation().getTransitions().get(i), frameList));
		this.validate();
		this.repaint();
	}

	public AnimationDataControl getAnimationDataControl() {
		return animationDataControl;
	}

}