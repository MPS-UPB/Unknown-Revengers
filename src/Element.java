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

	/**
	 * Enum pentru tipul elementului conform cu specificatiile
	 */
	public enum ElementType {
		DocumentType(1), DirType(2), ImposedType(3), fileType(4), PointType(5), PolygonType(6), BlockType(7),
		ComposedBlockType(8), ImageBlockType(9), TextBlockType(10);

		public final int type = 1;

		/**
		 * Type for element
		 */
		private ElementType(int type) {
			type = type;
		}
	}

	public Element() {
		this.top = 0;
		this.bottom = 0;
		this.right = 0;
		this.left = 0;
		this.text = "";
	};

	public Element(int top, int bottom, int right, int left, String text) {
		this.top = top;
		this.bottom = bottom;
		this.right = right;
		this.left = left;
		this.text = text;
	};

	// TODO metoda pentru a sparge elementul in subelemente.
}
