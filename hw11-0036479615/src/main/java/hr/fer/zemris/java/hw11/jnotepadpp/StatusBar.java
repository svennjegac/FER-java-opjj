package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.text.BadLocationException;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class is a representation of a status bar of a {@link JNotepadPP}.
 * It shows length of document, current line and column which is caret,
 * how much text is selected and current time.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class StatusBar extends JPanel {
	
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	/** Request for stopping clock. */
	private volatile boolean stopRequested = false;
	/** Localization provider. */
	private ILocalizationProvider flp;
	
	/** Length. */
	private String length;
	/** Line. */
	private String line;
	/** Column. */
	private String column;
	/** Selected. */
	private String selected;
	/** Default constant. */
	private String defaultConstant;
	
	/** Panel which shows editor information. */
	JPanel editorPanel;
	/** Label for text length. */
	JLabel lengthLabel;
	/** Panel for cursor information. */
	JPanel cursorPanel;
	/** Label shows line of caret. */
	JLabel lineLabel;
	/** Label shows column of caret. */
	JLabel columnLabel;
	/** Label shows how much text is selected. */
	JLabel selectionLabel;
	
	/** Panel for clock. */
	JPanel clockPanel;
	/** Label for clock text. */
	JLabel clockLabel;
	
	/**
	 * Constructor of status bar.
	 * It initializes all of its components and adds a listener
	 * to localization provider.
	 * 
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public StatusBar(JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		this.setSize(100, 25);
		this.setLayout(new GridLayout(1, 2));
		this.setOpaque(true);
		this.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.DARK_GRAY));
		
		editorPanel = new JPanel(new GridLayout(1, 2));
		lengthLabel = new JLabel();
		cursorPanel = new JPanel(new GridLayout(1, 3));
		lineLabel = new JLabel();
		columnLabel = new JLabel();
		selectionLabel = new JLabel();
		
		clockPanel = new JPanel(new GridLayout(1, 1));
		clockLabel = new JLabel();
		
		this.flp = flp;
		updateTextForLabels();
		
		flp.addLocalizationListener(new ILocalizationListener() {
			@Override
			public void localizationChanged() {
				updateTextForLabels();
				jNotepadPP.updateStatusBar();
			}
		});
		
		initGUI();
	}

	/**
	 * Initialization of status bar components.
	 */
	private void initGUI() {
		//editor panel init
		cursorPanel.add(lineLabel);
		cursorPanel.add(columnLabel);
		cursorPanel.add(selectionLabel);
		editorPanel.add(lengthLabel);
		editorPanel.add(cursorPanel);
	
		//clock init
		clockLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		Thread clock = new Thread(() -> {
			while (true) {
				clockLabel.setText(getCurrentLocalDateTimeStamp());
				
				try {
					Thread.sleep(500L);
				} catch (InterruptedException ignorable) {}
				
				if (stopRequested) {
					return;
				}
			}
		});
		clock.setDaemon(true);
		clock.start();
		
		clockPanel.add(clockLabel);
		
		//status bar init
		this.add(editorPanel);
		this.add(clockPanel);
		
		//set default values
		setDefaultStatusBarValues();
	}
	
	/**
	 * Request for program termination.
	 */
	public void terminate() {
		stopRequested = true;
	}
	
	/**
	 * Gets current date and time.
	 * 
	 * @return current date and time
	 */
	private String getCurrentLocalDateTimeStamp() {
	    return LocalDateTime.now()
	       .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}
	
	/**
	 * Sets status bar values to default values.
	 */
	private void setDefaultStatusBarValues() {
		lengthLabel.setText(length + defaultConstant);
		lineLabel.setText(line + defaultConstant);
		columnLabel.setText(column + defaultConstant);
		selectionLabel.setText(selected + defaultConstant);
	}
	
	/**
	 * Updates status bar information.
	 * 
	 * @param tab tab with information
	 */
	public void update(Tab tab) {
		if (tab == null) {
			setDefaultStatusBarValues();
			return;
		}
		
		JTextArea tabEditor = tab.getTabEditor();
		String text = tabEditor.getText();
		
		lengthLabel.setText(length + text.length());
		
		try {
			int line = tabEditor.getLineOfOffset(tabEditor.getCaretPosition());
			lineLabel.setText(this.line + line);
			
			int startOfCaretLine = 0;
			
			if (line > 0) {
				startOfCaretLine = tabEditor.getLineEndOffset(line - 1);
			}
			
			columnLabel.setText(column + (tabEditor.getCaretPosition() - startOfCaretLine));
		} catch (BadLocationException ignorable) {}
		
		String selectedText = tabEditor.getSelectedText();
		int selectedTextLength = 0;
		if (selectedText != null) {
			selectedTextLength = selectedText.length();
		}
		
		selectionLabel.setText(selected + selectedTextLength);
	}
	
	/**
	 * Updates text which is used in labels.
	 */
	private void updateTextForLabels() {
		length = flp.getString("statusBarLength");
		line = flp.getString("statusBarLine");
		column = flp.getString("statusBarColumn");
		selected = flp.getString("statusBarSelected");
		defaultConstant = flp.getString("statusBarDefaultConstant");
	}
}
