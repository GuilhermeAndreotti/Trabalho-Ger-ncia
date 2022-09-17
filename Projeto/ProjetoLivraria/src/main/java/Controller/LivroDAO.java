/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.Livro;
import java.sql.*;
import javax.swing.JOptionPane;



/**
 *
 * @author EDUARDO
 */
public class LivroDAO extends DAO{
    private PreparedStatement pstdados = null;
    private ResultSet rsdados = null;
    Livro livro = new Livro();
    private static final String inserirLivros = "INSERT INTO livro (titulo, nome_autor, sobrenome_autor, paginas, genero, editora) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String excluirTudo = "delete from livro";  
    private static final String consultarLivro = "SELECT * FROM livro ORDER BY id_livro";
    
    public boolean inserir() {
        if(verificarCampos()){
            try {
                int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
                int concorrencia = ResultSet.CONCUR_UPDATABLE;
                pstdados = connection.prepareStatement(inserirLivros, tipo, concorrencia);
                pstdados.setString(1, livro.getTitulo());
                pstdados.setString(2, livro.getNomeAutor());
                pstdados.setString(3, livro.getSobrenomeAutor());
                pstdados.setInt(4, livro.getQntPgns());
                pstdados.setString(5, livro.getGenero());
                pstdados.setString(6, livro.getEditora());
                int resposta = pstdados.executeUpdate();
                pstdados.close();
                if (resposta == 1) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } catch (SQLException erro) {
                System.out.println("Erro ao inserir: " + erro);
            }
             return false;
        }
        return false;
    }
    
    public boolean excluirTodos() {
        try {
            int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
            int concorrencia = ResultSet.CONCUR_UPDATABLE;
            pstdados = connection.prepareStatement(excluirTudo, tipo, concorrencia);
            int resposta = pstdados.executeUpdate();     
            pstdados.close();
            
            if (resposta >= 1) {
                connection.commit();
                return true;
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException erro) {
            System.out.println("Erro na execução da exclusão: " + erro);
        }
        return false;
    }
    
    public boolean consultarTodos() {
            try {
                int tipo = ResultSet.TYPE_SCROLL_SENSITIVE;
                int concorrencia = ResultSet.CONCUR_UPDATABLE;
                pstdados = connection.prepareStatement(consultarLivro, tipo, concorrencia);
                rsdados = pstdados.executeQuery();
                System.out.println(rsdados);
                System.out.println(consultarLivro);
                return true;
            } catch (SQLException erro) {
                System.out.println("Erro ao executar consulta: " + erro);
            }
            return false;
    }
    
    public Livro getLivro(){
        return livro;
    }
    public ResultSet getrsdados(){
        return rsdados;
    }
    
    public boolean verificarCampos(){
        if(getLivro().getEditora().isBlank() || getLivro().getEditora().length() > 50){
            JOptionPane.showMessageDialog(null, "Campo do editor está invalido!\nPor favor, preencha o campo editor", "FALHA AO SALVAR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(getLivro().getGenero().isBlank() || getLivro().getGenero().length() > 45){
            JOptionPane.showMessageDialog(null, "Campo do genero está inválido!\nPor favor, verifique o campo genero", "FALHA AO SALVAR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(getLivro().getNomeAutor().isBlank() || getLivro().getNomeAutor().length() > 45){
            JOptionPane.showMessageDialog(null, "Campo do nome do autor está inválido!\nPor favor, verifique o campo nome do autor", "FALHA AO SALVAR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(getLivro().getQntPgns() == 0){
            JOptionPane.showMessageDialog(null, "Campo de paginas está invalido!\nPor favor, verifique o campo pagina corretamente", "FALHA AO SALVAR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(getLivro().getSobrenomeAutor().isBlank() || getLivro().getSobrenomeAutor().length() > 60){
            JOptionPane.showMessageDialog(null, "Campo do sobrenome do autor está inválido!\nPor favor, verifique o campo sobrenome do autor", "FALHA AO SALVAR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(getLivro().getTitulo().isBlank() || getLivro().getTitulo().length() > 100){
            JOptionPane.showMessageDialog(null, "Campo do titulo está inválido!\nPor favor, verifique o campo titulo", "FALHA AO SALVAR", JOptionPane.ERROR_MESSAGE);
            return false;
        }
  
        return true;
    }
    
    public void zerarCampos(){
        getLivro().setEditora("");
        getLivro().setGenero("");
        getLivro().setIdLivro(0);
        getLivro().setNomeAutor("");
        getLivro().setQntPgns(0);
        getLivro().setSobrenomeAutor("");
        getLivro().setTitulo("");
    }

}