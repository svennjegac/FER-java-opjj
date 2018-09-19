package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.CloseSelectedTabAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.CopyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.CutAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.ExitProgramAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.FileMenuAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.NewFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.OpenFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.PasteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.SaveAsFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.SaveFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.file.StatisticalInfoAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.languages.CroatianLanguageAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.languages.EnglishLanguageAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.languages.GermanLanguageAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.languages.LanguagesMenuAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.tools.AscendingSortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.tools.ChangeCaseMenuAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.tools.DescendingSortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.tools.InvertCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.tools.SortMenuAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.tools.ToLowerCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.tools.ToUpperCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.tools.ToolsMenuAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.tools.UniqueAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Class which has duty to initialize all actions and give them
 * to who asks for them.
 * It also group actions in tab actions and selected text actions.
 * It offers methods to enable or disable each of these groups.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class ActionsProvider {
	
	/** Tab actions. */
	private List<AbstractJNotepadPPLocalizableAction> tabActions = new ArrayList<>();
	/** Selected text actions. */
	private List<AbstractJNotepadPPLocalizableAction> selectionActions = new ArrayList<>();
	
	/** File menu action. */
	private final FileMenuAction FILE_MENU_ACTION;
	/** New file action. */
	private final NewFileAction NEW_FILE_ACTION;
	/** Open file action. */
	private final OpenFileAction OPEN_FILE_ACTION;
	/** Save file action. */
	private final SaveFileAction SAVE_FILE_ACTION;
	/** Save As file action. */
	private final SaveAsFileAction SAVE_AS_FILE_ACTION;
	/** Copy action. */
	private final CopyAction COPY_ACTION;
	/** Paste action. */
	private final PasteAction PASTE_ACTION;
	/** Cut action. */
	private final CutAction CUT_ACTION;
	/** Statistical info file action. */
	private final StatisticalInfoAction STATISTICAL_INFO_FILE_ACTION;
	/** Close selected tab action. */
	private final CloseSelectedTabAction CLOSE_SELECTED_TAB_ACTION;
	/** Exit program action. */
	private final ExitProgramAction EXIT_PROGRAM_ACTION;
	
	/** Tools menu action. */
	private final ToolsMenuAction TOOLS_MENU_ACTION;
	/** Change case menu action. */
	private final ChangeCaseMenuAction CHANGE_CASE_MENU_ACTION;
	/** To upper case action. */
	private final ToUpperCaseAction TO_UPPER_CASE_ACTION;
	/** To lower case action. */
	private final ToLowerCaseAction TO_LOWER_CASE_ACTION;
	/** Invert case action. */
	private final InvertCaseAction INVERT_CASE_ACTION;
	
	/** Languages menu action. */
	private final LanguagesMenuAction LANGUAGES_MENU_ACTION;
	/** Croatian language action. */
	private final CroatianLanguageAction CROATIAN_LANGUAGE_ACTION;
	/** English language action. */
	private final EnglishLanguageAction ENGLISH_LANGUAGE_ACTION;
	/** German language action. */
	private final GermanLanguageAction GERMAN_LANGUAGE_ACTION;
	
	/** Sort menu action. */
	private final SortMenuAction SORT_MENU_ACTION;
	/** Ascending sort action. */
	private final AscendingSortAction ASCENDING_SORT_ACTION;
	/** Descending sort action. */
	private final DescendingSortAction DESCENDING_SORT_ACTION;
	/** Unique action. */
	private final UniqueAction UNIQUE_ACTION;
	
	/**
	 * Constructor which initializes all actions.
	 * It also fills previously mentioned groups of actions.
	 * 
	 * @param jNotepadPP reference to notepad
	 * @param flp reference to localization provider
	 */
	public ActionsProvider(JNotepadPP jNotepadPP, ILocalizationProvider flp) {
		FILE_MENU_ACTION = new FileMenuAction("file", jNotepadPP, flp);
		NEW_FILE_ACTION = new NewFileAction("new", KeyStroke.getKeyStroke("control N"), KeyEvent.VK_N, jNotepadPP, flp);
		OPEN_FILE_ACTION = new OpenFileAction("open", KeyStroke.getKeyStroke("control O"), KeyEvent.VK_O, jNotepadPP, flp);
		SAVE_FILE_ACTION = new SaveFileAction("save", KeyStroke.getKeyStroke("control S"), KeyEvent.VK_S, jNotepadPP, flp);
		SAVE_AS_FILE_ACTION = new SaveAsFileAction("saveAs", KeyStroke.getKeyStroke("control W"), KeyEvent.VK_W, jNotepadPP, flp);
		COPY_ACTION = new CopyAction("copy", jNotepadPP, flp);
		PASTE_ACTION = new PasteAction("paste", jNotepadPP, flp);
		CUT_ACTION = new CutAction("cut", jNotepadPP, flp);
		STATISTICAL_INFO_FILE_ACTION = new StatisticalInfoAction("documentStatistics", KeyStroke.getKeyStroke("control D"), KeyEvent.VK_D, jNotepadPP, flp);
		CLOSE_SELECTED_TAB_ACTION = new CloseSelectedTabAction("closeTab", KeyStroke.getKeyStroke("control Q"), KeyEvent.VK_Q, jNotepadPP, flp);
		EXIT_PROGRAM_ACTION = new ExitProgramAction("exit", KeyStroke.getKeyStroke("control E"), KeyEvent.VK_E, jNotepadPP, flp);
		
		TOOLS_MENU_ACTION = new ToolsMenuAction("tools", jNotepadPP, flp);
		CHANGE_CASE_MENU_ACTION = new ChangeCaseMenuAction("changeCase", jNotepadPP, flp);
		TO_UPPER_CASE_ACTION = new ToUpperCaseAction("toUpperCase", jNotepadPP, flp);
		TO_LOWER_CASE_ACTION = new ToLowerCaseAction("toLowerCase", jNotepadPP, flp);
		INVERT_CASE_ACTION = new InvertCaseAction("invertCase", jNotepadPP, flp);
		SORT_MENU_ACTION = new SortMenuAction("sort", jNotepadPP, flp);
		ASCENDING_SORT_ACTION = new AscendingSortAction("sortAscending", jNotepadPP, flp);
		DESCENDING_SORT_ACTION = new DescendingSortAction("sortDescending", jNotepadPP, flp);
		UNIQUE_ACTION = new UniqueAction("unique", jNotepadPP, flp);
		
		LANGUAGES_MENU_ACTION = new LanguagesMenuAction("languages", jNotepadPP, flp);
		CROATIAN_LANGUAGE_ACTION = new CroatianLanguageAction("croatian", jNotepadPP, flp);
		ENGLISH_LANGUAGE_ACTION = new EnglishLanguageAction("english", jNotepadPP, flp);
		GERMAN_LANGUAGE_ACTION = new GermanLanguageAction("german", jNotepadPP, flp);
		
		tabActions.addAll(Arrays.asList(
				SAVE_FILE_ACTION,
				SAVE_AS_FILE_ACTION,
				COPY_ACTION, PASTE_ACTION,
				CUT_ACTION,
				STATISTICAL_INFO_FILE_ACTION,
				CLOSE_SELECTED_TAB_ACTION
		));
		
		selectionActions.addAll(Arrays.asList(
				TO_UPPER_CASE_ACTION,
				TO_LOWER_CASE_ACTION,
				INVERT_CASE_ACTION,
				ASCENDING_SORT_ACTION,
				DESCENDING_SORT_ACTION,
				UNIQUE_ACTION
		));
	}
	
	/**
	 * Sets tab closing actions to enabled or disabled.
	 * 
	 * @param enabled value for enabling
	 */
	public void setTabActionsEnabling(boolean enabled) {
		tabActions.forEach(action -> {
			action.setEnabled(enabled);
		});
	}
	
	/**
	 * Sets selection actions to enabled or disabled.
	 * 
	 * @param enabled value for enabling
	 */
	public void setSelectionActionsEnabling(boolean enabled) {
		selectionActions.forEach(action -> {
			action.setEnabled(enabled);
		});
	}

	/**
	 * Getter for file menu action.
	 * 
	 * @return file menu action
	 */
	public FileMenuAction getFILE_MENU_ACTION() {
		return FILE_MENU_ACTION;
	}

	/**
	 * Getter for new file action.
	 * 
	 * @return new file action
	 */
	public NewFileAction getNEW_FILE_ACTION() {
		return NEW_FILE_ACTION;
	}

	/**
	 * Getter for open file action.
	 * 
	 * @return open file action
	 */
	public OpenFileAction getOPEN_FILE_ACTION() {
		return OPEN_FILE_ACTION;
	}

	/**
	 * Getter for save file action.
	 * 
	 * @return save file action
	 */
	public SaveFileAction getSAVE_FILE_ACTION() {
		return SAVE_FILE_ACTION;
	}

	/**
	 * Getter for save as file action.
	 * 
	 * @return save as file action
	 */
	public SaveAsFileAction getSAVE_AS_FILE_ACTION() {
		return SAVE_AS_FILE_ACTION;
	}

	/**
	 * Getter for copy action.
	 * 
	 * @return copy action
	 */
	public CopyAction getCOPY_ACTION() {
		return COPY_ACTION;
	}

	/**
	 * Getter for paste action.
	 * 
	 * @return paste action
	 */
	public PasteAction getPASTE_ACTION() {
		return PASTE_ACTION;
	}

	/**
	 * Getter for cut action.
	 * 
	 * @return cut action
	 */
	public CutAction getCUT_ACTION() {
		return CUT_ACTION;
	}

	/**
	 * Getter for statistical info file action.
	 * 
	 * @return statistical info file action
	 */
	public StatisticalInfoAction getSTATISTICAL_INFO_FILE_ACTION() {
		return STATISTICAL_INFO_FILE_ACTION;
	}

	/**
	 * Getter for close selected tab action.
	 * 
	 * @return close selected tab action
	 */
	public CloseSelectedTabAction getCLOSE_SELECTED_TAB_ACTION() {
		return CLOSE_SELECTED_TAB_ACTION;
	}

	/**
	 * Getter for exit program action.
	 * 
	 * @return exit program action
	 */
	public ExitProgramAction getEXIT_PROGRAM_ACTION() {
		return EXIT_PROGRAM_ACTION;
	}
	
	/**
	 * Getter for tools menu action.
	 * 
	 * @return tools menu action
	 */
	public ToolsMenuAction getTOOLS_MENU_ACTION() {
		return TOOLS_MENU_ACTION;
	}
	
	/**
	 * Getter for change case menu action.
	 * 
	 * @return change case menu action
	 */
	public ChangeCaseMenuAction getCHANGE_CASE_MENU_ACTION() {
		return CHANGE_CASE_MENU_ACTION;
	}
	
	/**
	 * Getter for to upper case action.
	 * 
	 * @return to upper case action
	 */
	public ToUpperCaseAction getTO_UPPER_CASE_ACTION() {
		return TO_UPPER_CASE_ACTION;
	}
	
	/**
	 * Getter for to lower case action.
	 * 
	 * @return to lower case action
	 */
	public ToLowerCaseAction getTO_LOWER_CASE_ACTION() {
		return TO_LOWER_CASE_ACTION;
	}
	
	/**
	 * Getter for invert case action.
	 * 
	 * @return invert case action
	 */
	public InvertCaseAction getINVERT_CASE_ACTION() {
		return INVERT_CASE_ACTION;
	}
	
	/**
	 * Getter for languages menu action.
	 * 
	 * @return languages menu action
	 */
	public LanguagesMenuAction getLANGUAGES_MENU_ACTION() {
		return LANGUAGES_MENU_ACTION;
	}
	
	/**
	 * Getter for croatian language action.
	 * 
	 * @return croatian language action
	 */
	public CroatianLanguageAction getCROATIAN_LANGUAGE_ACTION() {
		return CROATIAN_LANGUAGE_ACTION;
	}
	
	/**
	 * Getter for english language action.
	 * 
	 * @return english language action
	 */
	public EnglishLanguageAction getENGLISH_LANGUAGE_ACTION() {
		return ENGLISH_LANGUAGE_ACTION;
	}
	
	/**
	 * Getter for german language action.
	 * 
	 * @return german language action
	 */
	public GermanLanguageAction getGERMAN_LANGUAGE_ACTION() {
		return GERMAN_LANGUAGE_ACTION;
	}
	
	/**
	 * Getter for sort menu action.
	 * 
	 * @return sort menu action
	 */
	public SortMenuAction getSORT_MENU_ACTION() {
		return SORT_MENU_ACTION;
	}
	
	/**
	 * Getter for ascending sort action.
	 * 
	 * @return ascending sort action
	 */
	public AscendingSortAction getASCENDING_SORT_ACTION() {
		return ASCENDING_SORT_ACTION;
	}
	
	/**
	 * Getter for descending sort action.
	 * 
	 * @return descending sort action
	 */
	public DescendingSortAction getDESCENDING_SORT_ACTION() {
		return DESCENDING_SORT_ACTION;
	}
	
	/**
	 * Getter for unique action.
	 * 
	 * @return unique action
	 */
	public UniqueAction getUNIQUE_ACTION() {
		return UNIQUE_ACTION;
	}
}
