/**
 * @author Unknown-Revengers
 */

import static org.joox.JOOX.$;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
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
@SuppressWarnings("serial")
public class AnalizerSelector extends JFrame {

	/**
	 *  Lista cu analizatoare disponibile.
	 */
	private List<Analizer> aList;

	/**
	 * Next button.
	 */
	private JButton nextBttn;

	/**
	 * Dropdown pentru analizatoare.
	 */
	@SuppressWarnings("rawtypes")
	private JComboBox analizerList;

	/**
	 * Text Area pentru descrierea analizatorului.
	 */
	private JTextArea descriptionArea;

	/**
	 * Analizatorul selectat.
	 */
	private Analizer selectedAnalizer = null;

	/**
	 * Fereastra curenta.
	 */
	private JFrame frame = this;

	/**
	 * Constructor.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public AnalizerSelector() {
		// Incarca analizatoare.
		this.loadAnalizers();

		// Creaza content panel.
		Container contentPanel = getContentPane();

		// New Panel.
		JPanel panel = new JPanel();

		// Adauga buton de next.
		nextBttn = new JButton("Next");
		this.nextBttn.addActionListener(new NextListenter());
		panel.add(nextBttn);
		contentPanel.add(panel, BorderLayout.SOUTH);

		// Formeaza lista cu analizatoare pentru dropdown.
		String[] aOptions = new String[aList.size()];
		for (int i = 0; i < aList.size(); i++) {
			aOptions[i] = aList.get(i).name;
		}

		// Creeaza dropdown cu analizatoarele.
		analizerList = new JComboBox(aOptions);
		analizerList.addActionListener(new ComboListener());

		// Adauga dropdown la content panel.
		panel = new JPanel();
		panel.add(analizerList);
		contentPanel.add(panel, BorderLayout.NORTH);

		// Creaza Text Area pentru descrierea analizatorului.
		descriptionArea = new JTextArea(aList.get(0).description);
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setEditable(false);

		// Adauga Text Area la un scroll panel.
		JScrollPane spanel = new JScrollPane(descriptionArea);
		spanel.setPreferredSize(new Dimension(200, 100));

		// Adauga scroll panel la content panel.
		panel = new JPanel();
		panel.setSize(200, 100);
		panel.add(spanel);
		contentPanel.add(panel, BorderLayout.CENTER);

		// Initializeaza fereastra curenta.
		this.initFrame();
	}

	/**
	 * Initializeaza frame-ul curent.
	 */
	private void initFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 220);
		this.setVisible(true);

		setLocationRelativeTo(null);
	}

	/**
	 * Ia descrierea analizatorului specificat ca parametru.
	 *
	 * @param  name   Numele analizatorului.
	 *
	 * @return String Returneaza descrierea analizatorului.
	 */
	private String getDescription(final String name) {
		String description = "";
		for (int i = 0; i < aList.size(); i++) {
			if (aList.get(i).name.compareTo(name) == 0) {
				description = aList.get(i).description;
			}
		}
		return description;
	}

	/**
	 * Listener pentru dropdown.
	 *
	 * @author Unknown-Revengers
	 */
	class ComboListener implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			@SuppressWarnings("rawtypes")
			JComboBox cb = (JComboBox) e.getSource();
			String execName = (String) cb.getSelectedItem();

			descriptionArea.setText(AnalizerSelector.this.
					getDescription(execName));
		}

	}

	/**
	 * Listener pentru butonul de next.
	 *
	 * @author Unknown-Revengers
	 */
	class NextListenter implements ActionListener {
		@Override
		public void actionPerformed(final ActionEvent e) {
			String execName = (String) analizerList.getSelectedItem();
			String description = AnalizerSelector.this.
			getDescription(execName);

			// Incarca analizator selectat din dropdown.
			selectedAnalizer = new Analizer(execName, description);

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
	private Analizer getAnalizer(final String file) {
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
		if (dt.get("execType") != null
				&& dt.get("execType").compareTo("layout") == 0) {
			return new Analizer(dt.get("execName"),
					dt.get("execDescription"));
		}

		return null;
	}

	/**
	 * Afiseaza o fereastra cu un dropdown, un textbox cu descrierea
	 * analizatorului si un buton OK pentru a selecta analizatorul
	 * de layout ce va fi folosit. In momentul in care se seleteaza
	 * alt analizator se schimba si descrierea.
	 *
	 * @return Analizer Analizatorul ce va fi folosit.
	 * @throws InterruptedException
	 */
	public Analizer chooseAnalizer() throws InterruptedException {
		// Asteapta sa fie selectat un analizator.
		synchronized (this) {
			while (selectedAnalizer == null) {
				this.wait();
			}
		}

		return selectedAnalizer;
	}
}
