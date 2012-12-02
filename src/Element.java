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
	String image;
	String text;
	int type = 1;
	
	/**
	 * Enum pentru tipul elementului conform cu specificatiile
	 */
	public enum ElementType {
		DocumentType(1), DirType(2), ImposedType(3), FileType(4), PointType(5), PolygonType(6), BlockType(7),
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
	
	public Element(String tag, String text, int top, int bottom, int right, int left, String image){
		this.top    = top;
		this.bottom = bottom;
		this.right  = right;
		this.left   = left;
		this.text   = text;
		this.image  = image;
		
		// Parseaza tipul elementului din text in ElementType
		if(tag == "String"){
			this.elementType = ElementType.StringType;
		} else if(tag == "Document") {
			this.elementType = ElementType.DocumentType;
		} else if(tag == "TextBlock") {
			this.elementType = ElementType.TextBlockType;
		} else if(tag == "TextLine") {
			this.elementType = ElementType.TextLineType;
		} else if(tag == "Dir") {
			this.elementType = ElementType.DirType;
		} else if(tag == "Imposed") {
			this.elementType = ElementType.ImposedType;
		} else if(tag == "File") {
			this.elementType = ElementType.FileType;
		} else if(tag == "Point") {
			this.elementType = ElementType.PointType;
		} else if(tag == "Polygon") {
			this.elementType = ElementType.PolygonType;
		} else if(tag == "Block") {
			this.elementType = ElementType.BlockType;
		} else if(tag == "ComposedBlock") {
			this.elementType = ElementType.ComposedBlockType;
		} else if(tag == "ImageBlock") {
			this.elementType = ElementType.ImageBlockType;
		} else if(tag == "ComposedBlock") {
			this.elementType = ElementType.ComposedBlockType;
		}
	}
	
	public Element(ElementType type, String text){
		this.elementType = type;
		this.text = text;
	}

	public String toString(){
		return (this.text + "\n");
	}
	
	// TODO metoda pentru a sparge elementul in subelemente.
}
