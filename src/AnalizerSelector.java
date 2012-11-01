import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Selecteaza analizatorul de layout dorit.
 * 
 * @author Unknown-Revengers
 */
public class AnalizerSelector extends JFrame {
	
	// Lista cu analizatoare disponibile
	List<Analizer> aList;
	
	private JButton nextBttn;
	
	private JComboBox analizerList;
	
	private Analizer selectedAnalizer = null; 
	
	JFrame frame = this;
	
	/**
	 * Constructor
	 * 
	 */
	public AnalizerSelector(){
		// Incarca analizatoare
		this.loadAnalizers();
		
		// Creaza content panel
		Container contentPanel = getContentPane();
		
		// New Panel
		JPanel panel = new JPanel();
		
		// Adauga buton next
		nextBttn = new JButton("Next");
		this.nextBttn.addActionListener(new NextListenter());
		panel.add(nextBttn);
		contentPanel.add(panel, BorderLayout.SOUTH);
		
		// TODO Ia lista cu analizatoare
		String[] aOptions = {"analiztor1", "analizator2"};
	
		// Adauga dropdown cu analizatoare
		analizerList = new JComboBox(aOptions);
		panel = new JPanel();
		panel.add(analizerList);
		contentPanel.add(panel, BorderLayout.NORTH);
		
		// Initializeaza fereastra curenta
		this.initFrame();
	}
	
	/**
	 * Initializeaza frame-ul curent
	 * 
	 * @return void
	 */
	private void initFrame(){		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(200, 100);
		this.setVisible(true);	
	}
	
	class NextListenter implements ActionListener {
	    public void actionPerformed(ActionEvent e) {
	    	
	    	// TODO incarca analizator selectat din dropdown
	    	selectedAnalizer = new Analizer("analizator selectat", "analiztor ce va fi folosit");
	    	
	    	synchronized (frame){
	    		frame.notifyAll();
	    	}
	    	
	    	frame.dispose();
	    }
	}
	
	/**
	 * TODO Cauta in toate fisierele din folderul cu XSD (Config.exec_schemas) si incarca analizatoarele de layout disponibile 
	 * 
	 * @return List<String>
	 */
	private void loadAnalizers(){
		
		// TODO Incarca analizatoarele disponibile
		aList = new ArrayList<Analizer>();
		aList.add(new Analizer("analizator1", "descriere analizator 1"));
		aList.add(new Analizer("analizator2", "descriere analizator 2"));
	}
	
	/**
	 * TODO Afiseaza o fereastra cu un dropdown si un buton OK pentru a selecta analizatorul de layout ce va fi folosit.
	 * 
	 * @return Analizer Analizatorul ce va fi folosit.
	 * @throws InterruptedException 
	 */
	public Analizer chooseAnalizer() throws InterruptedException{
		
		// Asteapta sa fie selectat un analizator.
		synchronized(this){
			while(selectedAnalizer == null){
				this.wait();
			}
		}
		
		return selectedAnalizer;
	}
}
