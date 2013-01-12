package elements_actions;

/**
 * @author Unknown-Revengers
 * 
 */
public enum ElementsVisibility {
	/**
	 * Vezi imagine.
	 */
	S_IMAGE("Imagine"),

	/**
	 * Vezi text.
	 */
	S_TEXT("Text");

	/**
	 * @param text
	 */
	private ElementsVisibility(String text) {
		this.text = text;
	}

	private String text;

	@Override
	public String toString() {
		return text;
	}
}
