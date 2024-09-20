
package model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

/**
 *
 * @author Gabriel
 */
public class Endereco {
    private int idEndereco;
    private int cep;
    private String estado;
    private  String cidade;
    private  String bairro; 
    private  String complemento;
    private  int numero;
    private Cliente cliente;
    
}
