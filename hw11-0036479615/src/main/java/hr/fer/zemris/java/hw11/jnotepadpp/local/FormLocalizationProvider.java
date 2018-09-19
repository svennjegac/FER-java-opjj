package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * Localization provider which automatically registers and deregisters itself
 * on window opening or closing of provided frame.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
	
	/**
	 * Constructor which has reference to localization provided which can be asked to get useful informations.
	 * It registers listeners for frame closing or opening so it can register and deregister itself.
	 * 
	 * @param lp localization provider
	 * @param jFrame frame
	 */
	public FormLocalizationProvider(ILocalizationProvider lp, JFrame jFrame) {
		super(lp);
		
		jFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}
		});
	}
}
