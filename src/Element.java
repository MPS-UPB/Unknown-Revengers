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
	// TODO structura pentru a tine tatal elementului.
	// TODO structura pentru a tine fii elementului.

	public Element(){};

	// TODO metoda pentru a sparge elementul in subelemente.
}
