package ClassesBancos;

import javax.swing.JOptionPane;

import FuncoesSistema.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Produto {
    private String nome;
    private String descricao;
    private int quantidade;
    private double valor;
    private int tipoProduto;
    private int fornecedor;

    // contrutores

    public Produto() {
    }

    public Produto(String nome, String descricao, int quantidade, double valor, int tprod, int fornecedor) {
        this.setDescricao(descricao);
        this.setNome(nome);
        this.setQuantidade(quantidade);
        this.setValor(valor);
        this.setTipoProduto(tprod);
        this.setFornecedor(fornecedor);
    }

    // seters
    private void setValor(double valor) {
        this.valor = valor;
    }

    private void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    private void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    private void setTipoProduto(int tipoProduto) {
        this.tipoProduto = tipoProduto;
    }

    private void setFornecedor(int fornecedor) {
        this.fornecedor = fornecedor;
    }

    // geters
    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getTipoProduto() {
        return tipoProduto;
    }

    public int getFornecedor() {
        return fornecedor;
    }

    // metodos
    private void cadastroProduto(String nomeProduto, String descProduto, double valorProduto, int quantProduto,
            String nomeTipoProduto, String nomeFornecedor) {

        Connection conn = Conexao.conectarBD();
        Conexao con = new Conexao();
        String tiposProdutos = con.buscarDado(conn, "TipoProduto", "nome", nomeTipoProduto, null, "id", "asc");
        String fornecedores = con.buscarDado(conn, "Fornecedor", "nome", nomeFornecedor, null, "id", "asc");
        try {
            this.setNome(nomeProduto);
            this.setFornecedor(Integer.parseInt(fornecedores));
            this.setQuantidade(quantProduto);
            this.setTipoProduto(Integer.parseInt(tiposProdutos));
            this.setValor(valorProduto);
            this.setDescricao(descProduto);
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");

        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao inserir os dados!\nTente novamente.");
        }

    }

    public void cadastrarProduto(Connection conn, String nomeProduto, String descProduto, double valorProduto,
            int quantProduto, String nomeTipoProduto, String nomeFornecedor) {
        this.cadastroProduto(nomeProduto, descProduto, valorProduto, quantProduto, nomeTipoProduto, nomeFornecedor);
        try {
            String query = "insert into Produto (nome,descricao,quantidade,valor,fk_TipoProduto_ID,fk_Fornecedor_ID) values(?,?,?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.getNome());
            pstmt.setString(2, this.getDescricao());
            pstmt.setInt(3, this.getQuantidade());
            pstmt.setFloat(4, (float) this.getValor());
            pstmt.setInt(5, this.getTipoProduto());
            pstmt.setInt(6, this.getFornecedor());
            pstmt.executeUpdate();
            pstmt.close();
            JOptionPane.showMessageDialog(null, "Produto Cadastrado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void editarProduto(Connection conn, String nome, String result, String informacao) {
        try {
            Conexao con = new Conexao();
            int idProduto = Integer.parseInt(con.buscarDado(conn, "Produto", "nome", nome, null, "id", "asc"));
            switch (result) {
                case "nome":
                    String queryNome = "update Produto set nome =" + "\"" + informacao + "\"" + " where id ="
                            + idProduto;
                    PreparedStatement stmtCase1 = conn.prepareStatement(queryNome);
                    stmtCase1.execute();
                    stmtCase1.close();
                    break;
                case "descricao":
                    String queryDesc = "update Produto set descricao =" + "\"" + informacao + "\""
                            + " where id =" + idProduto;
                    PreparedStatement stmtCase2 = conn.prepareStatement(queryDesc);
                    stmtCase2.execute();
                    stmtCase2.close();
                    break;
                case "valor":
                    String queryValor = "update Produto set valor =" + Double.parseDouble(informacao) + " where id = "
                            + idProduto;
                    PreparedStatement stmtCase4 = conn.prepareStatement(queryValor);
                    stmtCase4.execute();
                    stmtCase4.close();
                    break;
                case "quantidade":
                    String queryQuant = "update Produto set quantidade =" + Integer.parseInt(informacao) + " where id ="
                            + idProduto;
                    PreparedStatement stmtCase5 = conn.prepareStatement(queryQuant);
                    stmtCase5.execute();
                    stmtCase5.close();
                    break;
                case "fornecedor":
                    String resultado = con.buscarDado(conn, "Fornecedor", "nome", informacao, "id", "Dados", "asc");
                    int id = Integer.parseInt(resultado.split(" ")[1]);
                    String queryForn = "update Produto set fk_Fornecedor_ID =" + id + " where id ="
                            + idProduto;
                    PreparedStatement stmtCase6 = conn.prepareStatement(queryForn);
                    stmtCase6.execute();
                    stmtCase6.close();
                    break;
                case "TipoProduto":
                    String queryTprod = "update Produto set fk_TipoProduto_ID =" + this.getTipoProduto() + " where id ="
                            + idProduto;
                    PreparedStatement stmtCase7 = conn.prepareStatement(queryTprod);
                    stmtCase7.execute();
                    stmtCase7.close();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Não foi possivel encontrar os dados a serem alterados");
            }
            JOptionPane.showMessageDialog(null, "Dados do Produto alterado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static String[] getProdutos(String tipoProduto) {
        Connection conn = Conexao.conectarBD();
        Conexao con = new Conexao();
        int idTprod = Integer
                .parseInt(con.buscarDado(conn, "TipoProduto", "Nome", tipoProduto, "id", "Dados", "asc").split("/")[1]);
        try {
            String query = "SELECT p.nome FROM dbshiphub2.produto as p join tipoproduto as  tp on p.fk_TipoProduto_ID=tp.id where fk_TipoProduto_ID like "
                    + idTprod;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            String[] produtos = new Produto().obterValoresDoResultSet(rs);
            return produtos;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return null;

    }

    public String[] obterValoresDoResultSet(ResultSet rs) {
        try {
            // Descobrir o número de colunas no ResultSet
            ResultSetMetaData metaData = rs.getMetaData();
            int numColunas = metaData.getColumnCount();

            // Criar uma lista para armazenar os valores
            List<String> listaDeValores = new ArrayList<>();

            // Iterar sobre as linhas do ResultSet
            while (rs.next()) {
                for (int i = 0; i < numColunas; i++) {
                    listaDeValores.add(rs.getString(i + 1)); // Use i + 1 para obter o valor da coluna correta
                }
            }

            // Converter a lista para um array de única dimensão
            String[] resultado = new String[listaDeValores.size()];
            for (int i = 0; i < listaDeValores.size(); i++) {
                resultado[i] = listaDeValores.get(i);
            }

            return resultado;
        } catch (Exception e) {
            // Lidar com exceções, se necessário
            return null;
        }
    }

}
