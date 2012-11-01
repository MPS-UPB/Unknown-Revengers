import java.awt.Frame;

import javax.swing.JFrame;

/**
 * Interfata grafica (ar fi recomandat ca pentru fiecare TODO sa existe o metoda / mai multe care fac acel lucru. Astfel,
 * constructorul nu va avea sute de linii). Probabil e bine ca listenerele sa fie declarate in alte fisiere, din acelasi motiv.
 * 
 * @author Unknown-Revengers
 */
public class LayoutGUI extends JFrame{
	
	LayoutParser layoutParser;
	
	public LayoutGUI(LayoutParser layoutParser){
		
		this.layoutParser = layoutParser;
		
		/**
		 * TODO
		 * Imaginea se incarca in zona principala a GUI-ului
		 */
		
		/**
		 * TODO
		 * Se incarca peste imagine elementele din layout analysis 
		 */
		this.loadElements();
		
		/**
		 * TODO 
		 * Adauga in partea de sus checkboxuri pentru selectie litere / randuri / blocuri de text
		 * Trebuie atasate si listenere care sa modifice continutul fisierului atunci cand este schimbata selectia
		 * Cred ca ar fi bine ca fiecare element sa fie marcat cu o culoare diferita
		 */
		this.addElementFilters();
		
		/** 
		 * TODO (sper sa fie posibil)
		 * Adauga in partea de sus in button pentru analiza OCR. 
		 * 
		 * Atunci cand este apasat, in jurul fiecarui element (doar randuri) va aparea un border punctat iar cand se selecteaza, 
		 * borderul va deveni solid si cu o culoare highlighted.
		 * 
		 * Dupa se se selecteaza elementele dorite, se va apasa un button in partea de jos "Analiza OCR". La apasarea butonului, se
		 * realizeaza analiza OCR pe elementele selectate anterior.
		 */
		this.addOCRActions();
		
		/** 
		 * TODO
		 * Trebuie sa existe si un buton (in partea de dreapta sus) pentru editarea layoutului:
		 *    - unirea/spargerea blocurilor de text
		 *    - unirea/spargerea liniilor de text
		 *    
		 * Similar cu cel pentru analiza OCR, se vor selecta mai mult elemente (doar ca trebuie sa fie de 
		 * acelasi fel si din acelasi bloc) si apoi vor fi in partea de jos 2 butoane:
		 *   - uneste blocuri
		 *   - sparge blocuri
		 *   
		 * Modificarile facute se reflecta in this.layoutParser
		 */
		this.addEditLayoutActions();
		
		/**
		 * TODO
		 * Un buton in partea de jos "SAVE" care scrie in fisierul de output rezultatele. In listener trebuie:
		 *    - sa se scrie in fisier datele din this.layoutParser
		 *    - sa se faca dispose la fereastra curenta
		 */
		this.addSaveActions();
		
		this.initFrame();
	}
	
	/**
	 * Initializeaza fereastra
	 * 
	 * @return void
	 */
	private void initFrame(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Se va seta la dimensiunea imaginii - cred
		this.setSize(400, 600);
		
		this.setVisible(true);
	}
	
	/**
	 * TODO
	 * Incarca elementele in fereastra parsand fisierul de layout (se folosesc functii din this.layoutParser)
	 * 
	 * @return void
	 */
	private void loadElements(){
		/**
		 * TODO
		 * Incarca elemente
		 */
		
		/** 
		 * TODO
		 * Atunci cand se face click pe un element, va aparea un popup cu textul rezultat in urma analizei OCR (daca nu s-a facut
		 * analiza OCR se poate cere sa se faca pentru blocul curent).Acest text trebuie sa poata fi editat. 
		 * Schimbarile facute se reflecta in this.layoutParser.
		 */
	}
	
	/**
	 * TODO
	 * Adauga filtre pentru selectia elementelor din imagine.
	 */
	private void addElementFilters(){
		
	}
	
	/**
	 * TODO
	 * Adauga button pentru analiza OCR si actiunile pentru el.
	 */
	private void addOCRActions(){
		
	}
	
	/**
	 * TODO 
	 * Button pentru editat layout si cu actiunile posibile.
	 */
	private void addEditLayoutActions(){
		
	}
	
	/**
	 * TODO
	 * Buton pentru salvat schimbarile facute intr-un fisier de output.
	 */
	private void addSaveActions(){
		
	}
}
