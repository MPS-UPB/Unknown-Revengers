import static org.joox.JOOX.$;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.joox.Match;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

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
	 * Constructor.
	 *
	 */
	public AnalizerSelector() {
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
	 * Initializeaza frame-ul curent.
	 */
	private void initFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(200, 100);
		this.setVisible(true);
	}

	class NextListenter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			// TODO incarca analizator selectat din dropdown
			selectedAnalizer = new Analizer("analizator selectat", "analiztor ce va fi folosit");

			synchronized (frame) {
				frame.notifyAll();
			}

			frame.dispose();
		}
	}

	/**
	 * Cauta in toate fisierele din folderul cu XSD (Config.exec_schemas)
	 * si incarca analizatoarele de layout disponibile.
	 */
	private void loadAnalizers() {
		// Lista cu analizatoarele disponibile
		aList = new ArrayList<Analizer>();

		// Deschide directorul xml_schema.
		File directory = new File(Config.exec_schemas);

		// Ia fisierele din directorul xml_schema.
		File[] files = directory.listFiles();

		// Parseaza fiecare fisier.
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String file;
				file = files[i].getAbsolutePath();

				Analizer a = this.getAnalizer(file);
				if (a != null) {
					aList.add(a);
				}
			}
		}
	}

	/**
	 * Parseaza fisierul primit ca parametru.
	 *
	 * @param  file   Calea absoluta a fisierului de parsat.
	 *
	 * @return mixed  Daca in fisier a fost gasit un analizator atunci
	 * 				  returneaza Analizer, altfel returneaza null.
	 */
	private Analizer getAnalizer(String file) {
		Document dom = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.
		newInstance();
		dbf.setNamespaceAware(true);

		try {
			// Ia din factory o noua instanta de document builder.
			DocumentBuilder db = dbf.newDocumentBuilder();

			// Parseaza pentru a obtine o reprezentare DOM a fisierului.
			dom = db.parse(file);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		Map<String, String> dt = new TreeMap<String, String>();

		// Ia toate tagurile simpleType.
		Match simpleType = $(dom).namespace("xs",
		"http://www.w3.org/2001/XMLSchema").xpath("//xs:simpleType");
		for (int i = 0; i < simpleType.size(); i++) {
			// Ia nume si valoare.
			dt.put(simpleType.get(i).getAttribute("name"),
					$(simpleType.content(i)).namespace("xs",
					"http://www.w3.org/2001/XMLSchema").xpath("//xs:pattern").attr("value"));
		}

		// Avem nevoie de analizator de layout.
		if (dt.get("execType").compareTo("layout") == 0) {
			return new Analizer(dt.get("execName"),
					dt.get("execDescription"));
		}

		return null;
	}

	/**
	 * TODO Afiseaza o fereastra cu un dropdown, un textbox cu descrierea analizatorului
	 * si un buton OK pentru a selecta analizatorul de layout ce va fi folosit.
	 * In momentul in care se seleteaza alt analizator se schimba si descrierea.
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
