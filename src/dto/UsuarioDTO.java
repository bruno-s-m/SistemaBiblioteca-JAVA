package dto;

/**
 * Classe DTO (Data Transfer Object) Representa o "molde" de um Usuário,
 * servindo para transportar os dados entre as camadas do sistema (View, DAO).
 */
public class UsuarioDTO {

    private int id;
    private String nome;
    private String cpf;
    private String email;

    public UsuarioDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
