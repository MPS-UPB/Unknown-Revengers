package elements_actions;

public enum ElementsActions {
	S_OCR("Analiza OCR"), S_GLUE("Uneste componente");

	/**
	 * @param text
	 */
	private ElementsActions(final String text) {
		this.text = text;
	}

	private final String text;

	@Override
	public String toString() {
		return text;
	}
}
