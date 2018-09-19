package hr.fer.zemris.java.tecaj_13.web.servlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogEntryForm;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Servlet processes updating/adding new entry.
 * 
 * @author Sven Njegač
 * @version 1.0
 */
@WebServlet(name="update-entry", urlPatterns={"/servleti/updateentry"})
public class UpdateEntryServlet extends HttpServlet {

	/** UID. */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("title");
		String text = req.getParameter("text");
		String idParam = req.getParameter("entryId");
		Long id = null;
		
		if (title == null || text == null) {
			System.out.println("Redirect");
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		try {
			id = Long.parseLong(idParam);
		} catch (Exception ignorable) {}
		
		BlogEntryForm entryForm = new BlogEntryForm(null, title, text);
		if (!entryForm.validate()) {
			req.setAttribute("entryForm", entryForm);
			req.setAttribute("errors", entryForm.getErrors());
			req.getRequestDispatcher("/WEB-INF/pages/edit-update-entry.jsp").forward(req, resp);;
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().getBlogUserByNick(req.getParameter("nick"));
		
		if (id == null) {
			BlogEntry entry = entryForm.createEntry(user);
			DAOProvider.getDAO().addEntry(entry);
			resp.sendRedirect(req.getContextPath() + "/servleti/author/" + user.getNick());
			return;
		}
		
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		if (entry == null) {
			resp.sendRedirect(req.getContextPath() + "/servleti/main");
			return;
		}
		
		entry.setTitle(entryForm.getTitle());
		entry.setText(entryForm.getText());
		entry.setLastModifiedAt(new Date());
		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + user.getNick());
	}
}
