import javax.swing.JOptionPane;

/**
 * @author Unknown-Revengers
 */

/**
 * Clasa pentru afisarea mesajelor de eroare.
 *
 * @author Unknown-Revengers
 */
public class ErrorMessage {
	/**
	 * Afiseaza un mesaj de eroare, dupa care incheie executia programului.
	 *
	 * @param message Mesajul pe care trebuie sa il afiseze.
	 */
	public static void show(String message) {
		JOptionPane.showMessageDialog(null, message);
		System.exit(0);
	}
}
