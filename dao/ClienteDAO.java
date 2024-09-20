
package dao;
    import factory.ConexaoFactory;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;
    import java.util.ArrayList;
    import model.Cliente;
    import model.Endereco;

public class ClienteDAO {    
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql = "";
          
    public ArrayList<Cliente> getListCliente() throws SQLException{
        ArrayList<Cliente> clientes = new ArrayList<>();
        sql = "SELECT cliente.*,E.cep,E.estado,E.cidade,E.bairro,E.complemento,E.numero FROM cliente INNER JOIN endereco E on cliente.idCliente = E.idCliente;";
        
        con  = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        
        while(rs.next()){        
            Cliente cliente = new Cliente();
            Endereco E = new Endereco();
            cliente.setIdCliente(rs.getInt("idCliente"));
            cliente.setNome(rs.getString("nome"));
            cliente.setEmail(rs.getString("email"));
            cliente.setTelefone(rs.getString("telefone"));
            E.setCep(rs.getInt("cep"));
            E.setBairro(rs.getString("bairro"));
            E.setCidade(rs.getString("cidade"));
            E.setEstado(rs.getString("estado"));
            E.setNumero(rs.getInt("numero"));
            E.setComplemento(rs.getString("complemento"));
            
            cliente.setEndereco(E);
            clientes.add(cliente);
            
        }
        ConexaoFactory.close(con);
        return clientes;    
    }

//------------------------------------------------------------------------------
    public Cliente getLoadCliente(int idCliente)throws SQLException{
        Cliente client = new Cliente();        
        sql = "SELECT idCliente, nome, email, telefone FROM cliente WHERE idCliente = ?";
        
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        ps.setInt(1, idCliente);
        rs = ps.executeQuery();
        
        if(rs.next()){
            client.setIdCliente(rs.getInt("idCliente"));
            client.setNome(rs.getString("nome"));
            client.setEmail(rs.getString("email"));
            client.setTelefone(rs.getString("telefone"));            
        }
        ConexaoFactory.close(con);
        return client;        
    }
//------------------------------------------------------------------------------    
      
    public boolean registerCliente(Cliente cliente)throws SQLException{    
        con = ConexaoFactory.conectar();
        
        if(cliente.getIdCliente() == 0){
            sql = "INSERT INTO cliente(nome, email, telefone) VALUES (?,?,?)";
            
            ps = con.prepareStatement(sql);            
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getTelefone());           
        }
        else{
            sql = "UPDATE cliente SET nome = ?, email = ?, telefone = ? WHERE idCliente = ?";
            
            ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getTelefone());
            ps.setInt(4, cliente.getIdCliente());
        }
        ps.executeUpdate();
        ConexaoFactory.close(con);
        return true;        
    }
        
}
