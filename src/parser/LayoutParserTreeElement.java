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
		DOCUMENT("Document"),
		DIR("Dir"),
		IMPOSED("Imposed"),
		FILE("File"),
		POINT("Point"),
		POLYGON("Polygon"),
		BLOCK("Block"),
		COMPOSEDBLOCK("ComposedBlock"),
		IMAGEBLOCK("ImageBlock"),
		TEXTBLOCK("TextBlock"),
		STRING("String"),
		TEXTLINE("TextLine");

		private String name;

		/**
		 * Type for element
		 */
		private ElementType(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
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
	 * @param type Type.
	 * @param hasPage Block is page number.
	 */
	public LayoutParserTreeElement(ElementType type, boolean hasPage) {
		this.hasPage = hasPage;
		this.text = "";
		this.elementType = type;
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
	 * @param type Type.
	 * @param text Element text.
	 */
	public LayoutParserTreeElement(ElementType type, String text) {
		this.text = text;
		this.elementType = type;
	}

	/**
	 * Constructor.
	 * 
	 * @param type Element type.
	 * @param text Element text.
	 * @param top Element top coordinate.
	 * @param bottom Element bottom coordinate.
	 * @param right Element right coordinate.
	 * @param left Element left coordinate.
	 * @param image Element image.
	 */
	public LayoutParserTreeElement(ElementType type, String text, int top,
			int bottom, int right, int left, String image) {
		this(top, bottom, right, left, text);
		this.image = image;
		this.elementType = type;
	}

	/**
	 * Constructor.
	 * 
	 * @param type Element type.
	 * @param x Coordinate.
	 * @param y Coordinate.
	 */
	public LayoutParserTreeElement(ElementType type, String x, String y) {
		this.elementType = type;
		this.text = "";
		this.x = x;
		this.y = y;
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
