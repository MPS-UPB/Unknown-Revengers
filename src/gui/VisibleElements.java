package gui;

import parser.LayoutParserTreeElement.ElementType;

/**
 * @author Unknown-Revengers
 * 
 */
public enum VisibleElements {
	/**
	 * Vezi blocuri.
	 */
	S_BLOCK("Blocuri", ElementType.TEXTBLOCK),

	/**
	 * Vezi linii.
	 */
	S_LINE("Linii", ElementType.TEXTLINE);

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

	/**
	 * Get elemtn type.
	 * 
	 * @return Element type.
	 */
	public ElementType toType() {
		return type;
	}
}
