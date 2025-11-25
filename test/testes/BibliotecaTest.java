package testes;

import dao.LivroDAO;
import dto.LivroDTO;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*; 

public class BibliotecaTest {

    @Test
    public void testarMoldeLivroDTO() {
        System.out.println("Teste do DTO.");
        
        LivroDTO livro = new LivroDTO();
        livro.setId(10);
        livro.setTitulo("A Arte da Guerra");
        livro.setAutor("Sun Tzu");
        livro.setDisponivel(true);

        assertEquals(10, livro.getId());
        assertEquals("A Arte da Guerra", livro.getTitulo());
        assertTrue(livro.isDisponivel());
    }

    @Test
    public void testarCadastroEListagemDAO() {
        System.out.println("Teste de Cadastro no DAO.");

        LivroDAO dao = new LivroDAO();
        dao.criarTabelaSeNaoExistir();

        LivroDTO novoLivro = new LivroDTO();
        novoLivro.setTitulo("Livro Teste");
        novoLivro.setAutor("Autor Teste");
        novoLivro.setDisponivel(true);

        dao.cadastrarLivro(novoLivro);

        ArrayList<LivroDTO> lista = dao.listarLivros();
        
        assertFalse("A lista não deveria estar vazia.", lista.isEmpty());
        
        boolean encontrou = false;
        for(LivroDTO livro : lista) {
            if(livro.getTitulo().equals("Livro Teste")) {
                encontrou = true;
                break;
            }
        }
        assertTrue("O livro nao foi encontrado.", encontrou);
    }

    @Test
    public void testarBuscaPorId() {
        System.out.println("Executando teste TDD de Busca por ID...");
        
        LivroDAO dao = new LivroDAO();
        dao.criarTabelaSeNaoExistir(); 
        
        dao.limparTabela();

        LivroDTO livroParaCadastrar = new LivroDTO();
        livroParaCadastrar.setTitulo("O Senhor dos Anéis");
        livroParaCadastrar.setAutor("J.R.R. Tolkien");
        dao.cadastrarLivro(livroParaCadastrar);

        LivroDTO livroEncontrado = dao.buscarPorId(1); 

        assertNotNull("Deveria ter encontrado algum livro com ID 1", livroEncontrado);
        
        if (livroEncontrado != null) {
             System.out.println("Livro encontrado: " + livroEncontrado.getTitulo());
        }
    }
}