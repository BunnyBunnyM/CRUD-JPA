package com.prueba.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prueba.dao.UserDAO;
import com.prueba.entities.User;


/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO UserDAO;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		this.UserDAO =  new UserDAO();
		}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();
		
		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertarUsuario(request, response);
				break;
			case "/delete":
				eliminarUsuario(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/update":
				actualizarUsuario(request, response);
				break;
			default:
				listUsuarios(request, response);
					break;
			}
		} catch (SQLException e) {
			throw new ServletException(e);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("formuser.jsp");
		dispatcher.forward(request, response);

	}
	
	private void insertarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
		String nombre = request.getParameter("nombre");
		String email = request.getParameter("email");
		String pais = request.getParameter("pais");
		
		User usuario =  new User(nombre, email, pais);
		
		UserDAO.insert(usuario);
		
		response.sendRedirect("usuariolist.jsp");
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
			throws IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		User usuarioActual = UserDAO.find(id);
		
		request.setAttribute("user", usuarioActual);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("Usuario.jsp");
		dispatcher.forward(request, response);
		
	}
	
	private void actualizarUsuario(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String nombre = request.getParameter("nombre");
		String email = request.getParameter("email");
		String pais = request.getParameter("pais");
		
		User s = new User(id, nombre, email, pais);
		UserDAO.delete(s);
		
		response.sendRedirect("list");
	}
	
	private void eliminarUsuario(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		UserDAO.delete(new User(id));
		
		response.sendRedirect("list");
	}
	
	private void listUsuarios(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, SQLException, IOException {
		
		List <User> lista = UserDAO.list();
		request.setAttribute("lista", lista);
		RequestDispatcher dispatcher = request.getRequestDispatcher("usuariolist.jsp");
		dispatcher.forward(request, response);
		
	}

}
