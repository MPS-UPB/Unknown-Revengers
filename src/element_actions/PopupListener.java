package element_actions;

import gui.GPopup;

import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class PopupListener implements PopupMenuListener {

	@Override
	public void popupMenuCanceled(PopupMenuEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent arg0) {
		GPopup popup = (GPopup) arg0.getSource();
		popup.storeLocation();
	}

	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent arg0) {
		// TODO Auto-generated method stub
	}

}