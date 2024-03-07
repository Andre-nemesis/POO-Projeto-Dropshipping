package ClassesBancos;

import java.util.Scanner;
import javax.swing.JOptionPane;

import FuncoesSistema.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;

public class TipoProduto {
    private String nome;
    private ArrayList<Produto> listProdutos = new ArrayList<Produto>();
    
    // Contrutor
    public TipoProduto(){}

    public TipoProduto(String Nome){
        this.setNome(Nome);
    }

    // set
    private void setNome(String nome) {this.nome = nome;}
    
    protected void setProdutos(ArrayList<Produto> produtos) {this.listProdutos = produtos;}

    // get
    public String getNome() {return nome;}

    public ArrayList<Produto> getListProdutos() {return listProdutos;}

    // metodos
    public void addProduto(Produto prod){
        this.listProdutos.add(prod);
    }

    private void cadastroTipoProduto (){
        Scanner leitura = new Scanner(System.in);
        try {
            System.out.println("Digite o nome do tipo do produto");
            this.setNome(leitura.nextLine());
            leitura.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void cadastrarTipoProduto(Connection conn) {
        try {
            this.cadastroTipoProduto();
            String query = "insert into TipoProduto (nome) values (?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, this.getNome());
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Tipo de Produto cadastrado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private String _editarTipoProduto(){
        Scanner leitura = new Scanner(System.in);
        System.out.println("Informe o novo nome do Tipo de Produto: ");
        try {
            this.setNome(leitura.nextLine());
            leitura.close();
            return this.getNome();    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
        

    }

    public void editarTipoProduto(Connection conn,String nomeDoTipoDeProdutoAntigo){
        try {
            Conexao con = new Conexao();
            String nNome = this._editarTipoProduto();
            int idtProd = Integer.parseInt(con.buscarDado(conn, "TipoProduto", "nome", nomeDoTipoDeProdutoAntigo, null, "id", "asc"));
            
            String queryUpdateNome = "update TipoProduto set nome = "+"\""+nNome+"\""+ " where id = "+idtProd;
            
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(queryUpdateNome);
            stmt.close();
            JOptionPane.showMessageDialog(null, "Tipo de Produto atualizado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+" linha 374");
        }
    }
    
    public static String[] tipoProduto(){
        Connection conn = Conexao.conectarBD();
        Conexao con = new Conexao();
        String[] dados = con.buscarDado(conn, "TipoProduto", "Nome", "*", "Nome", "Dados", "asc").split("/");
        String[] TprodNomes = new String[dados.length];
        int count = 0;
        for (int i = 0; i < dados.length; i++) {
            if (dados[i].equals("|")||dados[i].equals("||")) continue;
            else {
                TprodNomes[count] = dados[i];
                count++;
            }
        }
        return TprodNomes;
    }
}
