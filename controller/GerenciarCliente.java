package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

import model.Cliente;
import dao.ClienteDAO;
import model.Endereco;
import dao.EnderecoDao;


@WebServlet(name = "GerenciarCliente", urlPatterns = {"/gerenciarCliente"})
public class GerenciarCliente extends HttpServlet {
    RequestDispatcher dispatcher = null;
    Cliente cliente = null;
    ClienteDAO cdao = null;
    
    
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response .getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");        
        String acao = request.getParameter("acao");
        String idCliente = request.getParameter("idCliente");
        String message = "";        
        cliente = new Cliente();  
        cdao = new ClienteDAO();
        
        try {
            if(acao.equals("listar")){
                ArrayList<Cliente>clientes = new ArrayList<>();
                clientes = cdao.getListCliente();                
                dispatcher = getServletContext().getRequestDispatcher("/listCliente.jsp");
                request.setAttribute("clientes",clientes);    
                dispatcher.forward(request, response);     
                
            }else if(acao.equals("alterar")){
                cliente = cdao.getLoadCliente(Integer.parseInt(idCliente));
                if(cliente.getIdCliente() > 0){
                    dispatcher = getServletContext().getRequestDispatcher("/registerCliente.jsp");
                    request.setAttribute("cliente",cliente);
                    dispatcher.forward(request, response);                    
                }else{
                    message = "Não foi possivel localizar os dados!";
                }
            }else{
                response.sendRedirect("/home.jsp");
            }
            
        } catch (SQLException erro) {
            message = "Erro! "+erro.getMessage();
            erro.printStackTrace();
        }
        
        out.println(
            "<script type='text/javascript'>"+
                    "alert('"+message+"');"+
                    "location.href='gerenciarCliente?acao=listar';"+                
            "</script>"                
        );        
    }
 
    @Override
    protected void doPost(HttpServletRequest request, 
            HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        
        String idCliente = request.getParameter("idCliente");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String telefone = request.getParameter("telefone");
        String message = "";
        String msg = "";
        
        
        cliente = new Cliente();   
        cdao = new ClienteDAO();
        
        if(!idCliente.isEmpty()){
            cliente.setIdCliente(Integer.parseInt(idCliente));            
        }
        
        if(nome.isEmpty() || nome.equals("")){
            request.setAttribute("msg", "Nome não informado!");
            notification(request, response);            
        }else{
            cliente.setNome(nome);
        }
        
        if(email.isEmpty() || email.equals("")){
            request.setAttribute("msg","E-mail não innformado!");
            notification(request, response);                
        }else{
            cliente.setEmail(email);
        }
        
        if(telefone.isEmpty() || telefone.equals("")){
            request.setAttribute("msg", "Telefone não informado!");
            notification(request, response);                
        }else{
            cliente.setTelefone(telefone);
        }
    
    //--------------------------------------------------------------------------          
        try {
            if(cdao.registerCliente(cliente)){
                message = "Cadastro Efetuado com Sucesso!";
            }else{
                message = "Falha ao efetuar o registro! tente novamente!";               
            }
        } catch (SQLException erro) {
            message = "Erro!" + erro.getMessage();
            erro.printStackTrace();
        }
        
        out.println(
            "<script type='text/javascript'> "+
                "alert('"+ message +"');"+
                "location.href='gerenciarCliente?acao=listar';"+
            "</script>"
        );
    } 
    
    private void notification(HttpServletRequest request, 
            HttpServletResponse response) 
            throws IOException, ServletException{
        dispatcher = getServletContext().getRequestDispatcher("/registerCliente.jsp");
        dispatcher.forward(request, response);
        }
    
}
