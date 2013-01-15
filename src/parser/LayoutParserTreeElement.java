package parser;

/**
 * O clasa generica element pentru un element din fisier.
 * 
 * @author Unknown-Revengers
 */
public class LayoutParserTreeElement {
	/**
	 * Coordonata.
	 */
	public int top;

	/**
	 * Coordonata.
	 */
	public int bottom;

	/**
	 * Coordonata.
	 */
	public int left;

	/**
	 * Coordonata.
	 */
	public int right;

	/**
	 * Imaginea.
	 */
	String image;

	/**
	 * Textul.
	 */
	public String text;

	/**
	 * Este bloc pagina.
	 */
	public boolean hasPage = false;

	/**
	 * Coordonata.
	 */
	public String x;

	/**
	 * Coordonata.
	 */
	public String y;

	/**
	 * Enum pentru tipul elementului conform cu specificatiile
	 */
	public enum ElementType {
		/**
		 * Document.
		 */
		DOCUMENT("Document"),

		/**
		 * Director.
		 */
		DIR("Dir"),

		/**
		 * Imposed.
		 */
		IMPOSED("Imposed"),

		/**
		 * Fisier.
		 */
		FILE("File"),

		/**
		 * Point.
		 */
		POINT("Point"),

		/**
		 * Poligon.
		 */
		POLYGON("Polygon"),

		/**
		 * Bloc.
		 */
		BLOCK("Block"),

		/**
		 * Bloc compus.
		 */
		COMPOSEDBLOCK("ComposedBlock"),

		/**
		 * Bloc imagine.
		 */
		IMAGEBLOCK("ImageBlock"),

		/**
		 * Block text.
		 */
		TEXTBLOCK("TextBlock"),

		/**
		 * String.
		 */
		STRING("String"),

		/**
		 * Linie de text.
		 */
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

	/**
	 * Tipul elementului.
	 */
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
