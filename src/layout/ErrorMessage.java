package layout;

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

	/**
	 * Afiseaza un mesaj de eroare, dupa care incheie executia programului doar
	 * daca close e false.
	 * 
	 * @param message mesajul de eroare
	 * @param close Incheie sau nu programul.
	 */
	public static void show(String message, boolean close) {
		JOptionPane.showMessageDialog(null, message);
		if (close) {
			System.exit(0);
		}
	}
}
