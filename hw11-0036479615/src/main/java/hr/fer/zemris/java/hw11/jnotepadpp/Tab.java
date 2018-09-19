package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.nio.file.Path;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.CloseSelectedTabAction;

/**
 * Class representation of a single tab.
 * It has references to its editor and component for rendering.
 * Also it provides information if it is modified and knows file path
 * of a file opened in a tab.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class Tab {
	
	/** Default name for file in tab. */
	public static final String DEFAULT_FILE_NAME = "newFile";

	/** Reference to notepad. */
	private JNotepadPP jNotepadPP;
	/** Its text editor. */
	private JTextArea tabEditor;
	/** Fil epath of opened file. */
	private Path filePath;
	/** Flag if file is modified. */
	private boolean modified;
	/** Tab component reference. */
	TabComponent tabComponent;
	
	/**
	 * Constructor of tab which adds tab to notepad and initializes needed
	 * actions.
	 * 
	 * @param jNotepadPP notepad to which tab will be added
	 * @param text initialization text
	 * @param filePath path of file
	 * @param modified modified flag
	 */
	public Tab(JNotepadPP jNotepadPP, String text, Path filePath, boolean modified) {
		this.jNotepadPP = jNotepadPP;
		this.filePath = filePath;
		this.modified = modified;
		
		tabEditor = new JTextArea(text);
		
		initTab();
		initActions();
	}
	
	/**
	 * Method initializes tab components.
	 */
	private void initTab() {
		JTabbedPane tabbedPane = jNotepadPP.getTabbedPane();
		
		int position = tabbedPane.getTabCount();
		
		tabbedPane.add(new MyScrollPane(this, tabEditor), position);
		
		tabComponent = new TabComponent();
		
		tabbedPane.setTabComponentAt(position, tabComponent);
		
		if (filePath != null) {
			tabbedPane.setToolTipTextAt(position, filePath.toAbsolutePath().toString());
		}
	}

	/**
	 * Method initializes tab actions.
	 */
	private void initActions() {
		tabEditor.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				tabComponent.setModified();
				modified = true;
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				tabComponent.setModified();
				modified = true;
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				tabComponent.setModified();
				modified = true;
			}
		});
		
		tabEditor.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				jNotepadPP.updateStatusBar(Tab.this);
				jNotepadPP.enableOrDisableSelectionActions();
			}
		});
	}
	
	/**
	 * Getter for file name of file in tab.
	 * 
	 * @return file name of opened file
	 */
	public String getFileName() {
		if (filePath == null) {
			return DEFAULT_FILE_NAME;
		}
		
		return filePath.getFileName().toString();
	}
	
	/**
	 * Method pastes text form clip board to tab.
	 */
	public void pasteInThisTab() {
		tabEditor.paste();
	}
	
	/**
	 * Method copies from text from tab to clip board.
	 */
	public void copyFromThisTab() {
		tabEditor.copy();
	}
	
	/**
	 * Metod cut text from tab to clipboard.
	 */
	public void cutFromThisTab() {
		tabEditor.cut();
	}
	
	/**
	 * Method which performs actions when tab is saved.
	 * 
	 * @param filePath path of file saved in this tab
	 */
	public void tabSaved(Path filePath) {
		modified = false;
		this.filePath = filePath;
		tabComponent.setUnmodified();
		tabComponent.setFileNameLabel(getFileName());
		
		JTabbedPane tabbedPane = jNotepadPP.getTabbedPane();
		int position = tabbedPane.indexOfTabComponent(tabComponent);
		tabbedPane.setToolTipTextAt(position, filePath.toAbsolutePath().toString());
		
		jNotepadPP.updateProgramTitle();
	}
	
	/**
	 * Getter for tab component.
	 * 
	 * @return tab component
	 */
	public TabComponent getTabComponent() {
		return tabComponent;
	}
	
	/**
	 * Getter for file path.
	 * 
	 * @return file path
	 */
	public Path getFilePath() {
		return filePath;
	}
	
	/**
	 * Getter for tab editor.
	 * 
	 * @return tab editor
	 */
	public JTextArea getTabEditor() {
		return tabEditor;
	}
	
	/**
	 * Checks if tab is modified.
	 * 
	 * @return <code>true</code> if tab is modified, <code>false</code> otherwise
	 */
	public boolean isModified() {
		return modified;
	}
	
	/**
	 * Class which represents a tab component.
	 * Tab component is used for defining a look of tab card.
	 * 
	 * @author Sven Njegač
	 * @version 1.0
	 */
	private class TabComponent extends JPanel {

		/** Serial version UID. */
		private static final long serialVersionUID = 1L;
		/** Label which holds tab icon. */
		private JLabel iconLabel;
		/** Label for file name in tab. */
		private JLabel fileNameLabel;
		/** Button for tab closing. */
		private JButton closeButton;
		
		/**
		 * Method initializes tab component using fields defined in tab class.
		 */
		public TabComponent() {
			iconLabel = new JLabel(modified ? Util.getModifiedIcon() : Util.getUnmodifiedIcon());
			
			if (filePath != null) {
				fileNameLabel = new JLabel(filePath.getFileName().toString());
			} else {
				fileNameLabel = new JLabel("newFile");
			}
			
			closeButton = new JButton("x");
			closeButton.setBorder(null);
			closeButton.setSize(100, 10);
			closeButton.setBackground(Color.RED);
			
			this.setLayout(new GridBagLayout());
			this.setOpaque(false);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.weightx = 1;
			this.add(iconLabel);
			gbc.gridx++;
			gbc.weightx = 0;
			this.add(new JLabel("  "));
			gbc.gridx++;
			gbc.weightx = 0;
			this.add(fileNameLabel, gbc);
			gbc.gridx++;
			gbc.weightx = 0;
			this.add(new JLabel("  "));
			gbc.gridx++;
			gbc.weightx = 0;
			this.add(closeButton, gbc);
			
			initActions();
		}
		
		/**
		 * Method initializes action which is performed when user clicks on button
		 * which closes tab.
		 */
		private void initActions() {
			closeButton.addActionListener(e -> {
				jNotepadPP.setSelectedTab(Tab.this);
				CloseSelectedTabAction.closeSelectedTab(jNotepadPP, jNotepadPP.getFlp());
			});
		}
		
		/**
		 * Sets modified icon.
		 */
		private void setModified() {
			iconLabel.setIcon(Util.getModifiedIcon());
		}
		
		/**
		 * Sets unmodified icon.
		 */
		private void setUnmodified() {
			iconLabel.setIcon(Util.getUnmodifiedIcon());
		}
		
		/**
		 * Sets name of opened file in tab card.
		 * 
		 * @param text ned file name
		 */
		private void setFileNameLabel(String text) {
			fileNameLabel.setText(text);
		}
	}
}
