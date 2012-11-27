/**
 * O clasa generica element pentru un element din fisier.
 * 
 * @author Unknown-Revengers
 */
public class Element {
	int top;
	int bottom;
	int left;
	int right;
	String text;
	int type = 1;
	
	/**
	 * Enum pentru tipul elementului conform cu specificatiile
	 */
	public enum ElementType {
		DocumentType(1), DirType(2), ImposedType(3), fileType(4), PointType(5), PolygonType(6), BlockType(7),
		ComposedBlockType(8), ImageBlockType(9), TextBlockType(10), StringType(11), TextLineType(12);



		/**
		 * Type for element
		 */
		private ElementType(int type) {
			type = type;
		}
	}
	
	ElementType elementType;

	public Element() {
		this.top = 0;
		this.bottom = 0;
		this.right = 0;
		this.left = 0;
		this.text = "";
		this.type = 0;
	};

	public Element(int top, int bottom, int right, int left, String text, int type) {
		this.top = top;
		this.bottom = bottom;
		this.right = right;
		this.left = left;
		this.text = text;
		this.type = 0;
	};

	public Element(ElementType type) {
		this.elementType = type;
	};
	
	public Element(String tag, String text){
		this.text = text;
		
		if(tag == "String"){
			this.elementType = ElementType.StringType;
		} else if(tag == "Document") {
			this.elementType = ElementType.DocumentType;
		} else if(tag == "TextBlock") {
			this.elementType = ElementType.TextBlockType;
		} else if(tag == "TextLine") {
			this.elementType = ElementType.TextLineType;
		}
	}
	
	public Element(ElementType type, String text){
		this.elementType = type;
		this.text = text;
	}

	// TODO metoda pentru a sparge elementul in subelemente.
}
