package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

import hr.fer.zemris.java.hw16.jvdraw.geometry.Changeable;
import hr.fer.zemris.java.hw16.jvdraw.geometry.ChangeablePanel;
import hr.fer.zemris.java.hw16.jvdraw.geometry.GeometricalObject;

/**
 * Implementation of list which can show {@link GeometricalObject} stored in
 * list model.
 * When user double clicks on a object in list he can change objects
 * properties such as location and color.
 * When object is selected in list and user enters DEL key, object is deleted.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
public class JVDrawList extends JList<GeometricalObject> {

	
	/** UID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor accepting model.
	 * 
	 * @param listModel list model
	 */
	public JVDrawList(ListModel<GeometricalObject> listModel) {
		this.setModel(listModel);
		initListeners();
	}

	/**
	 * Initialization of listeners.
	 */
	private void initListeners() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() < 2) {
					return;
				}
				
				@SuppressWarnings("unchecked")
				JList<GeometricalObject> list = (JList<GeometricalObject>) e.getSource();
				Changeable changeableObject = list.getSelectedValue();
				if (changeableObject == null) {
					return;
				}
				
				ChangeablePanel changeablePanel = changeableObject.createChangeablePanel();
				int result = JOptionPane.showConfirmDialog(list, changeablePanel);
				
				if (result == JOptionPane.NO_OPTION || result == JOptionPane.CANCEL_OPTION) {
					return;
				}
				
				if (!changeablePanel.validatePanelData()) {
					JOptionPane.showMessageDialog(list, "Provided data was invalid");
					return;
				}
				
				changeableObject.updateDataFromChangeablePanel(changeablePanel);
			}
		});
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if ((int) e.getKeyChar() != KeyEvent.VK_DELETE) {
					return;
				}
				
				@SuppressWarnings("unchecked")
				JList<GeometricalObject> list = (JList<GeometricalObject>) e.getSource();
				GeometricalObject geometricalObject = list.getSelectedValue();
				if (geometricalObject == null) {
					return;
				}
				
				JVDrawDM.getInstance().remove(geometricalObject);
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
	}
}
