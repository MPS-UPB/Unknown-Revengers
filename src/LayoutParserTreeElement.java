/**
 * O clasa generica element pentru un element din fisier.
 * 
 * @author Unknown-Revengers
 */
public class LayoutParserTreeElement {
	int top;
	int bottom;
	int left;
	int right;
	String image;
	String text;
	int type = 1;

	/**
	 * Enum pentru tipul elementului conform cu specificatiile
	 */
	public enum ElementType {
		Document(1), Dir(2), Imposed(3), File(4), Point(5), Polygon(6), Block(7),
		ComposedBlock(8), ImageBlock(9), TextBlock(10), String(11), TextLine(12);

		/**
		 * Type for element
		 */
		private ElementType(int type) {
		}
	}

	ElementType elementType;

	public LayoutParserTreeElement() {
		this.top = 0;
		this.bottom = 0;
		this.right = 0;
		this.left = 0;
		this.text = "";
		this.type = 0;
	};

	public LayoutParserTreeElement(int top, int bottom, int right, int left, String text, int type) {
		this.top = top;
		this.bottom = bottom;
		this.right = right;
		this.left = left;
		this.text = text;
		this.type = 0;
	};

	public LayoutParserTreeElement(ElementType type) {
		this.elementType = type;
	};

	public LayoutParserTreeElement(String tag, String text, int top, int bottom, int right, int left, String image) {
		this.top    = top;
		this.bottom = bottom;
		this.right  = right;
		this.left   = left;
		this.text   = text;
		this.image  = image;

		// Parseaza tipul elementului din text in ElementType
		if(tag == "String") {
			this.elementType = ElementType.String;
		} else if(tag == "Document") {
			this.elementType = ElementType.Document;
		} else if(tag == "TextBlock") {
			this.elementType = ElementType.TextBlock;
		} else if(tag == "TextLine") {
			this.elementType = ElementType.TextLine;
		} else if(tag == "Dir") {
			this.elementType = ElementType.Dir;
		} else if(tag == "Imposed") {
			this.elementType = ElementType.Imposed;
		} else if(tag == "File") {
			this.elementType = ElementType.File;
		} else if(tag == "Point") {
			this.elementType = ElementType.Point;
		} else if(tag == "Polygon") {
			this.elementType = ElementType.Polygon;
		} else if(tag == "Block") {
			this.elementType = ElementType.Block;
		} else if(tag == "ComposedBlock") {
			this.elementType = ElementType.ComposedBlock;
		} else if(tag == "ImageBlock") {
			this.elementType = ElementType.ImageBlock;
		} else if(tag == "ComposedBlock") {
			this.elementType = ElementType.ComposedBlock;
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
