package elements_actions;

/**
 * @author Unknown-Revengers
 * 
 */
public enum ElementsActions {
	/**
	 * Analiza OCR
	 */
	S_OCR("Analiza OCR"),

	/**
	 * Uneste componente
	 */
	S_GLUE("Uneste componente");

	/**
	 * @param text
	 */
	private ElementsActions(String text) {
		this.text = text;
	}

	private String text;

	@Override
	public String toString() {
		return text;
	}
}
