package element_actions;

/**
 * Possible actions on an element.
 * 
 * @author Unknown-Revengers
 */
public enum ElementActions {
	/**
	 * Analiza OCR pe un element.
	 */
	S_OCR("Analiza OCR"),

	/**
	 * Spargere element pe verticala.
	 */
	S_BREAK_V("Sparge vertical"),

	/**
	 * Spargere element pe orizontala.
	 */
	S_BREAK_H("Sparge orizontal"),

	/**
	 * Editeaza textul din element.
	 */
	S_TEXT("Editeaza text"),

	/**
	 * Marcheaza blocul ca fiind numar pagina.
	 */
	S_PAGE("Este numar pagina"),

	/**
	 * Sterge elementul.
	 */
	S_DELETE("Sterge element"),

	/**
	 * Bring element in front.
	 */
	S_FRONT("Bring to Front"),

	/**
	 * Send element to back.
	 */
	S_BACK("Send to Back"),

	/**
	 * Resize element.
	 */
	S_RESIZE("Redimensioneaza");

	/**
	 * @param text
	 */
	private ElementActions(String text) {
		this.text = text;
	}

	private String text;

	@Override
	public String toString() {
		return text;
	}
}
