
package dao;
import factory.ConexaoFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



import java.util.ArrayList;
import model.Endereco;
public class EnderecoDao {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql = "";
    
    ArrayList<Endereco> enderecos = new ArrayList<>(); 
       
    public ArrayList<Endereco> getLista()throws SQLException{
        sql = "SELECT idEndereco, cep, estado, cidade, "
                + "bairro, complemento, numero" +
                "FROM endereco";
        con = ConexaoFactory.conectar();
        ps = con.prepareStatement(sql);
        rs = ps.executeQuery();
        while(rs.next()){
            Endereco e = new Endereco();
            e.setIdEndereco(rs.getInt("idEndereco"));
            e.setCep(rs.getInt("cep"));
            e.setEstado(rs.getString("estado"));
            e.setCidade(rs.getString("cidade"));
            e.setBairro(rs.getString("bairro"));
            e.setComplemento(rs.getString("complemento"));
            e.setNumero(rs.getInt("numero"));
            
            
            enderecos.add(e);
                      
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
