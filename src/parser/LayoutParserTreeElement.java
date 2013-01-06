package parser;
/**
 * O clasa generica element pentru un element din fisier.
 * 
 * @author Unknown-Revengers
 */
public class LayoutParserTreeElement {
	public int top;
	public int bottom;
    public int left;
    public int right;
    String image;
    public String text;

    /**
     * Enum pentru tipul elementului conform cu specificatiile
     */
    public enum ElementType {
	DOCUMENT(1),
	DIR(2),
	IMPOSED(3),
	FILE(4),
	POINT(5),
	POLYGON(6),
	BLOCK(7),
	COMPOSEDBLOCK(8),
	IMAGEBLOCK(9),
	TEXTBLOCK(10),
	STRING(11),
	TEXTLINE(12);

	private final int typeNumber;

	/**
	 * Type for element
	 */
	private ElementType(int type) {
	    this.typeNumber = type;
	}

	/**
	 * @return int Numarul care corespunde tipului de element.
	 */
	public int getNumber() {
	    return this.typeNumber;
	}

	@Override
	public String toString() {
	    switch (this) {
	    case DOCUMENT:
		return "Document";
	    case DIR:
		return "Dir";
	    case IMPOSED:
		return "Imposed";
	    case FILE:
		return "File";
	    case POINT:
		return "Point";
	    case POLYGON:
		return "Polygon";
	    case BLOCK:
		return "Block";
	    case COMPOSEDBLOCK:
		return "ComposedBlock";
	    case IMAGEBLOCK:
		return "ImageBlock";
	    case TEXTBLOCK:
		return "TextBlock";
	    case STRING:
		return "String";
	    case TEXTLINE:
		return "TextLine";
	    }
	    return super.toString();
	}
    }

    public ElementType elementType;

    /**
     * Constructor
     */
    public LayoutParserTreeElement() {
	this(0, 0, 0, 0, "");
    };

    /**
     * Constructor
     */
    public LayoutParserTreeElement(int top, int bottom, int right, int left,
	    String text) {
	this.top = top;
	this.bottom = bottom;
	this.right = right;
	this.left = left;
	this.text = text;
    };

    public LayoutParserTreeElement(ElementType type) {
	this.elementType = type;
    };

    public LayoutParserTreeElement(String tag, String text, int top,
	    int bottom, int right, int left, String image) {
	this(top, bottom, right, left, text);
	this.image = image;

	// Parseaza tipul elementului din text in ElementType
	switch (tag) {
	case "String":
	    this.elementType = ElementType.STRING;
	    break;
	case "Document":
	    this.elementType = ElementType.DOCUMENT;
	    break;
	case "TextBlock":
	    this.elementType = ElementType.TEXTBLOCK;
	    break;
	case "TextLine":
	    this.elementType = ElementType.TEXTLINE;
	    break;
	case "Dir":
	    this.elementType = ElementType.DIR;
	    break;
	case "Imposed":
	    this.elementType = ElementType.IMPOSED;
	    break;
	case "File":
	    this.elementType = ElementType.FILE;
	    break;
	case "Point":
	    this.elementType = ElementType.POINT;
	    break;
	case "Polygon":
	    this.elementType = ElementType.POLYGON;
	    break;
	case "Block":
	    this.elementType = ElementType.BLOCK;
	    break;
	case "ComposedBlock":
	    this.elementType = ElementType.COMPOSEDBLOCK;
	    break;
	case "ImageBlock":
	    this.elementType = ElementType.IMAGEBLOCK;
	    break;
	}
    }

    public LayoutParserTreeElement(ElementType type, String text) {
	this.elementType = type;
	this.text = text;
    }

    // Cum va fi tiparit un nod cand vrem sa-l afisam
    @Override
    public String toString() {
	return (this.elementType.toString());
    }

    // TODO metoda pentru a sparge elementul in subelemente.
}
