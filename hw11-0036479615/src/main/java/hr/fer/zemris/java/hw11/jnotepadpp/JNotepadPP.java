package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.ExitProgramAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * This is a GUI runnable class which is a simple notepad.
 * It can open existing and new files.
 * It can save them, it can copy, cut and paste text in files.
 * It offers icon for modification signalization.
 * It also has tools for manipulating wit text, such as sorting
 * or changing letter case.
 * It has implemented localization so people from many countries
 * can use it.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class JNotepadPP extends JFrame {
	
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;

	/** Program name. */
	private static final String PROGRAM_NAME = "JNotepad++";
	
	/** Actions provider. */
	private final ActionsProvider ACTIONS_PROVIDER;
	/** Localization provider. */
	private final FormLocalizationProvider flp;
	
	/** Tabbed pane. */
	private JTabbedPane tabbedPane;
	/** Status bar. */
	private StatusBar statusBar;
	
	/**
	 * Constructor which initializes application and its components.
	 */
	public JNotepadPP() {
		setLocation(200, 50);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		ACTIONS_PROVIDER = new ActionsProvider(this, flp);
		initGUI();
		setSize(getPreferredSize());
	}
	
	/**
	 * Initialization of components.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		
		JPanel contentPanel = new JPanel(new BorderLayout());
		tabbedPane = new JTabbedPane();
		contentPanel.add(tabbedPane, BorderLayout.CENTER);
		contentPanel.setPreferredSize(new Dimension(600, 750));
		
		cp.setLayout(new BorderLayout());
		cp.add(contentPanel, BorderLayout.CENTER);
		
		manageClosingOfProgram();
		updateProgramTitle();
		initTabChangedListener();
		enableOrDisableTabActions();
		enableOrDisableSelectionActions();
		createMenu();
		createToolbar();
		createStatusBar(contentPanel);
	}

	/**
	 * Method adds listener for window so that application can manage
	 * closing of program.
	 */
	private void manageClosingOfProgram() {
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (ExitProgramAction.tryClosing(JNotepadPP.this, flp)) {
					statusBar.terminate();
					dispose();
				}
			}
		});
	}
	
	/**
	 * Method for updating program title.
	 */
	protected void updateProgramTitle() {
		Tab tab = getSelectedTab();
		
		if (tab == null || tab.getFilePath() == null) {
			setTitle(PROGRAM_NAME);
		} else {
			setTitle(tab.getFilePath().toAbsolutePath().toString() + " - " + PROGRAM_NAME);
		}
	}
	
	/**
	 * Initialization of listener for tab
	 * changing.
	 */
	private void initTabChangedListener() {
		tabbedPane.addChangeListener(changeEvent -> {
			updateProgramTitle();
			updateStatusBar();
			enableOrDisableTabActions();
			enableOrDisableSelectionActions();
		});
	}

	/**
	 * Method enables or disables tab actions, depending on
	 * state of application.
	 */
	private void enableOrDisableTabActions() {
		if (getSelectedTab() == null) {
			ACTIONS_PROVIDER.setTabActionsEnabling(false);
		} else {
			ACTIONS_PROVIDER.setTabActionsEnabling(true);
		}
	}
	
	/**
	 * Method enables or disables selection actions, depending on
	 * state of application.
	 */
	protected void enableOrDisableSelectionActions() {
		if (getSelectedTab() == null || getSelectedTab().getTabEditor().getSelectedText() == null) {
			ACTIONS_PROVIDER.setSelectionActionsEnabling(false);
		} else {
			ACTIONS_PROVIDER.setSelectionActionsEnabling(true);
		}
	}

	/**
	 * Method which initializes menu of application.
	 */
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu(ACTIONS_PROVIDER.getFILE_MENU_ACTION());
		fileMenu.add(new JMenuItem(ACTIONS_PROVIDER.getNEW_FILE_ACTION()));
		fileMenu.add(new JMenuItem(ACTIONS_PROVIDER.getOPEN_FILE_ACTION()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(ACTIONS_PROVIDER.getSAVE_FILE_ACTION()));
		fileMenu.add(new JMenuItem(ACTIONS_PROVIDER.getSAVE_AS_FILE_ACTION()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(ACTIONS_PROVIDER.getCOPY_ACTION()));
		fileMenu.add(new JMenuItem(ACTIONS_PROVIDER.getPASTE_ACTION()));
		fileMenu.add(new JMenuItem(ACTIONS_PROVIDER.getCUT_ACTION()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(ACTIONS_PROVIDER.getSTATISTICAL_INFO_FILE_ACTION()));
		fileMenu.addSeparator();
		fileMenu.add(new JMenuItem(ACTIONS_PROVIDER.getCLOSE_SELECTED_TAB_ACTION()));
		fileMenu.add(new JMenuItem(ACTIONS_PROVIDER.getEXIT_PROGRAM_ACTION()));
		
		JMenu toolsMenu = new JMenu(ACTIONS_PROVIDER.getTOOLS_MENU_ACTION());
		JMenu changeCaseMenu = new JMenu(ACTIONS_PROVIDER.getCHANGE_CASE_MENU_ACTION());
		changeCaseMenu.add(new JMenuItem(ACTIONS_PROVIDER.getTO_UPPER_CASE_ACTION()));
		changeCaseMenu.add(new JMenuItem(ACTIONS_PROVIDER.getTO_LOWER_CASE_ACTION()));
		changeCaseMenu.add(new JMenuItem(ACTIONS_PROVIDER.getINVERT_CASE_ACTION()));
		JMenu sortMenu = new JMenu(ACTIONS_PROVIDER.getSORT_MENU_ACTION());
		sortMenu.add(new JMenuItem(ACTIONS_PROVIDER.getASCENDING_SORT_ACTION()));
		sortMenu.add(new JMenuItem(ACTIONS_PROVIDER.getDESCENDING_SORT_ACTION()));
		toolsMenu.add(changeCaseMenu);
		toolsMenu.add(sortMenu);
		toolsMenu.add(ACTIONS_PROVIDER.getUNIQUE_ACTION());
		
		JMenu languagesMenu = new JMenu(ACTIONS_PROVIDER.getLANGUAGES_MENU_ACTION());
		languagesMenu.add(ACTIONS_PROVIDER.getCROATIAN_LANGUAGE_ACTION());
		languagesMenu.add(ACTIONS_PROVIDER.getENGLISH_LANGUAGE_ACTION());
		languagesMenu.add(ACTIONS_PROVIDER.getGERMAN_LANGUAGE_ACTION());
		
		menuBar.add(fileMenu);
		menuBar.add(toolsMenu);
		menuBar.add(languagesMenu);
		setJMenuBar(menuBar);
	}
	
	/**
	 * Method initializes application tool bar.
	 */
	private void createToolbar() {
		JToolBar toolbar = new JToolBar();
		
		toolbar.add(new JButton(ACTIONS_PROVIDER.getNEW_FILE_ACTION()));
		toolbar.add(new JButton(ACTIONS_PROVIDER.getOPEN_FILE_ACTION()));
		toolbar.addSeparator();
		toolbar.add(new JButton(ACTIONS_PROVIDER.getSAVE_FILE_ACTION()));
		toolbar.add(new JButton(ACTIONS_PROVIDER.getSAVE_AS_FILE_ACTION()));
		toolbar.addSeparator();
		toolbar.add(new JButton(ACTIONS_PROVIDER.getCOPY_ACTION()));
		toolbar.add(new JButton(ACTIONS_PROVIDER.getPASTE_ACTION()));
		toolbar.add(new JButton(ACTIONS_PROVIDER.getCUT_ACTION()));
		toolbar.addSeparator();
		toolbar.add(new JButton(ACTIONS_PROVIDER.getSTATISTICAL_INFO_FILE_ACTION()));
		toolbar.addSeparator();
		toolbar.add(new JButton(ACTIONS_PROVIDER.getCLOSE_SELECTED_TAB_ACTION()));
		toolbar.add(new JButton(ACTIONS_PROVIDER.getEXIT_PROGRAM_ACTION()));
		
		getContentPane().add(toolbar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Method creates and sets status bar.
	 * 
	 * @param contentPanel panel in which status bar is added.
	 */
	private void createStatusBar(JPanel contentPanel) {
		statusBar = new StatusBar(this, flp);
		contentPanel.add(statusBar, BorderLayout.PAGE_END);
	}
	
	/**
	 * Method updates status bar with given tab information.
	 * 
	 * @param tab provided tab
	 */
	public void updateStatusBar(Tab tab) {
		statusBar.update(tab);
	}
	
	/**
	 * Method updates status bar with currently selected tab.
	 */
	public void updateStatusBar() {
		statusBar.update(getSelectedTab());
	}
	
	/**
	 * Method sets provided tab as selected.
	 * 
	 * @param tab provided tab
	 */
	public void setSelectedTab(Tab tab) {
		int position = tabbedPane.indexOfTabComponent(tab.getTabComponent());
		tabbedPane.setSelectedIndex(position);
	}
	
	/**
	 * Method sets tab on provided index as selected.
	 * 
	 * @param index index of new tab which will be selected
	 */
	public void setSelectedTab(int index) {
		tabbedPane.setSelectedIndex(index);
	}

	/**
	 * Method gets selected tab.
	 * 
	 * @return selected tab or <code>null</code> if no tabs exists
	 */
	public Tab getSelectedTab() {
		int selectedIndex = tabbedPane.getSelectedIndex();
		
		if (selectedIndex == -1) {
			return null;
		}
		
		return getTab(selectedIndex);
	}
	
	/**
	 * Method gets tab on provided index.
	 * 
	 * @param index index of wanted tab
	 * @return tab
	 */
	public Tab getTab(int index) {
		MyScrollPane myScrollPane = (MyScrollPane) tabbedPane.getComponentAt(index);
		return myScrollPane.getTab();
	}
	
	/**
	 * Method returns index of provided tab.
	 * 
	 * @param tab tab
	 * @return index of tab
	 */
	public int getIndexOfTab(Tab tab) {
		return tabbedPane.indexOfTabComponent(tab.getTabComponent());
	}
	
	/**
	 * Method returns number of tabs.
	 * 
	 * @return number of tabs
	 */
	public int getNumberOfTabs() {
		return tabbedPane.getTabCount();
	}
	
	/**
	 * Method closes tab.
	 * 
	 * @param tab tab which needs to be closed
	 */
	public void closeTab(Tab tab) {
		closeTab(getIndexOfTab(tab));
	}
	
	/**
	 * Method closes tab on provided index.
	 * 
	 * @param index index of tab that needs to be closed
	 */
	public void closeTab(int index) {
		tabbedPane.remove(index);
	}
	
	/**
	 * Method returns tabbed pane.
	 * 
	 * @return tabbed pane
	 */
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	/**
	 * Method returns localization provider.
	 * 
	 * @return localization provider
	 */
	public ILocalizationProvider getFlp() {
		return flp;
	}
	
	/**
	 * Method which is run when program starts.
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			LocalizationProvider.getInstance().setLanguage("en");
			new JNotepadPP().setVisible(true);
		});
	}
}