package controller;

import dao.EnderecoDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Endereco;
import java.sql.SQLException;
import model.Cliente;
import dao.ClienteDAO;

/**
 *
 * @author Gabriel
 */
@WebServlet(name = "GerenciarEndereco", urlPatterns = {"/gerenciarEndereco"})  
public class GerenciarEndereco extends HttpServlet {
   

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String acao = request.getParameter("acao");
       
        String idEndereco = request.getParameter("idEndereco");
        
        String mensagem = "";

        Endereco E = new Endereco();

        EnderecoDao edao = new EnderecoDao();
        
        try {
            if (acao.equals("listar")) {

                ArrayList<Endereco> Endereco = new ArrayList<>();
                Endereco = edao.getLista();
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/listarEndereco.jsp");
                request.setAttribute("Endereco", Endereco);
                dispatcher.forward(request, response);

            } else if (acao.equals("alterar")) {

            } else {
                response.sendRedirect("index.jsp");

            }

        } catch (SQLException e) {
            mensagem = "Erro: " + e.getMessage();
            e.printStackTrace();
        }

        out.println(
                "<script type='text/javascript'>"
                + "alert('"+mensagem+"');"
                + "location.href='gerenciarEndereco?acao=listar';" 
                + "</script>"
        );
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
