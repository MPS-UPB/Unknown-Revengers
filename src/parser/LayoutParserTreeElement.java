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
	public boolean hasPage = false;
	public String x;
	public String y;

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

		private int typeNumber;

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
	 * 
	 * @param top Top coordinate.
	 * @param bottom Bottom coordinate.
	 * @param right Right coordinate.
	 * @param left Left coordinate.
	 * @param text Text.
	 */
	public LayoutParserTreeElement(int top, int bottom, int right, int left,
			String text) {
		this.top = top;
		this.bottom = bottom;
		this.right = right;
		this.left = left;
		this.text = text;
	};

	/**
	 * Constructor.
	 * 
	 * @param tag Type.
	 * @param hasPage Block is page number.
	 */
	public LayoutParserTreeElement(String tag, boolean hasPage) {
		this.hasPage = hasPage;
		this.text = "";
		setTagType(tag);
	}

	/**
	 * Constructor.
	 * 
	 * @param type Element type.
	 */
	public LayoutParserTreeElement(ElementType type) {
		this.elementType = type;
		this.text = "";
	};

	/**
	 * Constructor.
	 * 
	 * @param tag Type.
	 * @param text Element text.
	 */
	public LayoutParserTreeElement(String tag, String text) {
		this.text = text;
		setTagType(tag);
	}

	/**
	 * 
	 * Sets the tag type
	 * 
	 * @param tag
	 */
	private void setTagType(String tag) {
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

	/**
	 * Constructor.
	 * 
	 * @param tag Element type.
	 * @param text Element text.
	 * @param top Element top coordinate.
	 * @param bottom Element bottom coordinate.
	 * @param right Element right coordinate.
	 * @param left Element left coordinate.
	 * @param image Element image.
	 */
	public LayoutParserTreeElement(String tag, String text, int top,
			int bottom, int right, int left, String image) {
		this(top, bottom, right, left, text);
		this.image = image;

		setTagType(tag);
	}

	/**
	 * Constructor.
	 * 
	 * @param tag Element type.
	 * @param x Coordinate.
	 * @param y Coordinate.
	 */
	public LayoutParserTreeElement(String tag, String x, String y) {
		setTagType(tag);
		this.text = "";
		this.x = x;
		this.y = y;
	}

	/**
	 * Constructor.
	 * 
	 * @param type Element type.
	 * @param text Element text.
	 */
	public LayoutParserTreeElement(ElementType type, String text) {
		this.elementType = type;
		this.text = text;
	}

	// Cum va fi tiparit un nod cand vrem sa-l afisam
	@Override
	public String toString() {
		return (this.elementType.toString());
	}

	/**
	 * 
	 * Verifica daca tag-ul curent ar trebui sa aiba atribute de tipul left,
	 * right, top, below
	 * 
	 * @return Intoarce daca ar trebui sa contina sau nu
	 */
	public boolean shouldOutputCoords() {
		if (this.elementType == ElementType.BLOCK
				|| this.elementType == ElementType.IMAGEBLOCK
				|| this.elementType == ElementType.TEXTBLOCK
				|| this.elementType == ElementType.TEXTLINE) {
			return true;
		} else {
			return false;
		}
	}
}
