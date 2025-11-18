# 📚 Sistema de Biblioteca Senac

Sistema de gestão para bibliotecas desenvolvido em Java (Swing) com persistência de dados em MySQL. O projeto utiliza a arquitetura em camadas (MVC/DAO) para organizar o código de forma profissional.

## 🚀 Funcionalidades

* **Painel de Controle (Dashboard):** Visão geral e operações rápidas.
* **Gestão de Usuários:** Cadastro, listagem e alteração de dados.
* **Gestão de Livros:** Cadastro e controlo de acervo.
* **Empréstimos:** Registo de saída de livros com validação de disponibilidade.
* **Devoluções:** Baixa de livros e libertação imediata para novos empréstimos.
* **Histórico:** Visualização completa de quem pegou qual livro e quando.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java (JDK 21)
* **Interface:** Java Swing (JFrame, JPanel)
* **Banco de Dados:** MySQL
* **Conexão:** JDBC (mysql-connector-j)
* **Arquitetura:** DAO (Data Access Object) e DTO (Data Transfer Object)
* **IDE:** NetBeans

## ⚙️ Como Executar o Projeto

### 1. Configuração do Banco de Dados
Antes de iniciar a aplicação, é necessário criar o banco de dados. Execute o seguinte script no seu MySQL (Workbench ou DBeaver):

```sql
CREATE DATABASE IF NOT EXISTS sistemabiblioteca;
USE sistemabiblioteca;

-- Tabela de Usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(20) NOT NULL UNIQUE,
    email VARCHAR(100)
);

-- Tabela de Livros
CREATE TABLE IF NOT EXISTS livros (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(100),
    disponivel BOOLEAN DEFAULT TRUE
);

-- Tabela de Empréstimos
CREATE TABLE IF NOT EXISTS emprestimos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_livro INT NOT NULL,
    data_emprestimo DATE DEFAULT (CURRENT_DATE),
    data_devolucao DATE NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_livro) REFERENCES livros(id)
);
