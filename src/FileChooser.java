import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import java.awt.Frame;
import java.io.File;

/**
 * Selecteaza un fisier de input.
 * 
 * @author Unknown-Revengers
 */
public class FileChooser extends Frame{
	
	JFileChooser fileDialog;
	
	public FileChooser() {
		// Creaza un file dialog.
		fileDialog = new JFileChooser();
		
		// Seteaza titlu.
		fileDialog.setDialogTitle("Select the file you want to analyze.");
		
		// Seteaza filtru XML.
		FileFilter xmlFilter = new ExtensionFileFilter("XML (*.xml)", new String[] { "XML" });
		fileDialog.setFileFilter(xmlFilter);
		
		// Seteaza filtru imagine.
		FileFilter imageFilter = new ExtensionFileFilter("Image (*.jpg, *.jpeg)", new String[] { "JPG", "JPEG" });
		fileDialog.setFileFilter(imageFilter);
		
		// Accepta numai filtrele adaugate.
		fileDialog.setAcceptAllFileFilterUsed(false);
	}
	
	/**
	 * Returneaza fisierul selectat sau null daca nu a fost selectat niciun fisier.
	 * @return String
	 */
	public String chooseFile(){
		int rVal = fileDialog.showOpenDialog(FileChooser.this);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			return fileDialog.getCurrentDirectory().toString() + "\\" + fileDialog.getSelectedFile().getName();
		}
      
		return null;
	}
	
	class ExtensionFileFilter extends FileFilter {
		  String description;

		  String extensions[];

		  public ExtensionFileFilter(String description, String extension) {
		    this(description, new String[] { extension });
		  }

		  public ExtensionFileFilter(String description, String extensions[]) {
		    if (description == null) {
		      this.description = extensions[0];
		    } else {
		      this.description = description;
		    }
		    this.extensions = (String[]) extensions.clone();
		    toLower(this.extensions);
		  }

		  private void toLower(String array[]) {
		    for (int i = 0, n = array.length; i < n; i++) {
		      array[i] = array[i].toLowerCase();
		    }
		  }

		  public String getDescription() {
		    return description;
		  }

		  public boolean accept(File file) {
		    if (file.isDirectory()) {
		      return true;
		    } else {
		      String path = file.getAbsolutePath().toLowerCase();
		      for (int i = 0, n = extensions.length; i < n; i++) {
		        String extension = extensions[i];
		        if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
		          return true;
		        }
		      }
		    }
		    return false;
		  }
		}
}