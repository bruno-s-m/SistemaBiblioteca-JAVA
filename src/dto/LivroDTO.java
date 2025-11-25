package dto;

/**
 * Classe DTO (Data Transfer Object) Representa o "molde" de um Livro, servindo
 * para transportar os dados entre as camadas do sistema (View, DAO).
 */
public class LivroDTO {

    private int id;
    private String titulo;
    private String autor;
    private boolean disponivel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }
}
