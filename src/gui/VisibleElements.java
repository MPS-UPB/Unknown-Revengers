package gui;

import parser.LayoutParserTreeElement.ElementType;

public enum VisibleElements {
	S_BLOCK("Blocuri", ElementType.TEXTBLOCK), S_LINE("Linii",
			ElementType.TEXTLINE);

	/**
	 * @param text
	 * @param type
	 */
	private VisibleElements(String text, ElementType type) {
		this.text = text;
		this.type = type;
	}

	private String text;
	private ElementType type;

	@Override
	public String toString() {
		return text;
	}

	public ElementType toType() {
		return type;
	}
}
