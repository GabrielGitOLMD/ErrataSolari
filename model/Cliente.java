
package model;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class Cliente {
    private int idCliente;
    private String nome;
    private String email; 
    private String telefone;
    private Endereco endereco;
    
}
