package es.eucm.eadventure.common.data.chapterdata.conversation.node;

public interface ConversationNodeView {

	/**
	 * Constant for dialogue node.
	 */
	public static final int DIALOGUE = 0;

	/**
	 * Constant for option node.
	 */
	public static final int OPTION = 1;

	/**
	 * Returns the type of the current node.
	 * 
	 * @return DIALOGUE if dialogue node, OPTION if option node
	 */
	public int getType( );

	/**
	 * Returns if the node is terminal (has no children).
	 * 
	 * @return True if the node is terminal, false otherwise
	 */
	public boolean isTerminal( );

	/**
	 * Returns the children's number of the node.
	 * 
	 * @return The number of children
	 */
	public int getChildCount( );

	/**
	 * Returns the view conversation node in the given position.
	 * 
	 * @param index
	 *            Index of the child
	 * @return Selected reduced child
	 */
	public ConversationNodeView getChildView( int index );

	/**
	 * Returns the lines' number of the node.
	 * 
	 * @return The number of lines
	 */
	public abstract int getLineCount( );

	/**
	 * Returns whether the given line belongs to the player or not.
	 * 
	 * @param index
	 *            Index of the line
	 * @return True if the line belongs to the player, false otherwise
	 */
	public abstract boolean isPlayerLine( int index );

	/**
	 * Returns the name of the line in the given index.
	 * 
	 * @param index
	 *            Index of the line
	 * @return Name of the line
	 */
	public abstract String getLineName( int index );

	/**
	 * Returns the text of the line in the given index.
	 * 
	 * @param index
	 *            Index of the line
	 * @return Text of the line
	 */
	public abstract String getLineText( int index );

	/**
	 * Returns the path of the audio for the given index.
	 * 
	 * @param index
	 *            Index of the line
	 * @return Text of the line
	 */
	public abstract String getAudioPath ( int index );
	
	/**
	 * Checks whether the line for the given index has a valid audio path
	 * @param index Index of the line
	 * 
	 * @return True if has audio path, false otherwise
	 */
	public abstract boolean hasAudioPath ( int index );

	/**
	 * Returns if the node has a valid effect set.
	 * 
	 * @return True if the node has a set of effects, false otherwise
	 */
	public boolean hasEffects( );
}
