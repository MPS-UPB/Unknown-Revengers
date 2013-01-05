package gui;

import parser.LayoutParserTreeElement.ElementType;

public enum VisibleElements {
	S_BLOCK("Blocuri", ElementType.TEXTBLOCK),
	S_LINE("Linii", ElementType.TEXTLINE);
	
	/**
     * @param text
     * @param type
     */
    private VisibleElements(final String text, final ElementType type) {
        this.text = text;
        this.type = type;
    }
    
    private final String text;
    private final ElementType type;
    
    @Override
    public String toString() {
        return text;
    }
    
    public ElementType toType() {
        return type;
    }
}
