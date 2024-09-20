
package dao;
import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import java.util.ArrayList;
import model.Endereco;
import model.Cliente;
public class EnderecoDao {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql = "";
    
    ArrayList<Endereco> enderecos = new ArrayList<>(); 
       
    public ArrayList<Endereco> getLista()throws SQLException{
        sql = "SELECT idEndereco, cep, estado, cidade, "
                + "bairro, complemento, numero, idCliente" +
                "FROM endereco";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            Endereco E = new Endereco();
            
            E.setIdEndereco(rs.getInt("idEndereco"));
            E.setCep(rs.getInt("cep"));
            E.setEstado(rs.getString("estado"));
            E.setCidade(rs.getString("cidade"));
            E.setBairro(rs.getString("bairro"));
            E.setComplemento(rs.getString("complemento"));
            E.setNumero(rs.getInt("numero"));
            
            Cliente c = new Cliente();
            c.setIdCliente(rs.getInt("idCliente"));
            E.setCliente(c);
            
            
            enderecos.add(E);
                      
            }
        ConexaoFactory.close(con);
        return enderecos;
                      
    }
    
    public boolean gravar(Endereco e) throws SQLException {
        con = ConexaoFactory.conectar();
        
        if(e.getIdEndereco() == 0){
            sql = "INSERT INTO endereco (cep, estado, cidade, bairro, complemento, numero) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            
            ps.setInt(1,e.getCep());
            ps.setString(2, e.getEstado());
            ps.setString(3, e.getCidade());
            ps.setString(4, e.getBairro());
            ps.setString(5, e.getComplemento());
            ps.setInt(6, e.getNumero());
            
            
        }else{
            sql = "UPDADE endereco SET cep = ?, estado = ?, cidade = ?, bairro = ?, complemento = ?, numero = ? " +
                    "WHERE idEndereco = ?";
            ps = con.prepareStatement(sql);
            
            ps.setInt(1, e.getCep());
            ps.setString(2, e.getEstado());
            ps.setString(3, e.getCidade());
            ps.setString(4, e.getBairro());
            ps.setString(5, e.getComplemento());
            ps.setInt(6, e.getNumero());
            ps.setInt(7, e.getIdEndereco());
            
            
        }
        ps.executeUpdate();
        ConexaoFactory.close(con);
        
        return true;
    }
    
}
