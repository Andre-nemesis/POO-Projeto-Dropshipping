package ClassesBancos;

import java.util.Scanner;
import javax.swing.JOptionPane;

import FuncoesSistema.Conexao;

import java.sql.*;

public class Pedido {
    private String dataSaida;
    private String dataChegada;
    private int quantidade;
    private int cliente;
    private int vendaPagamento;
    private String produto;

    // construtores

    public Pedido() {}

    public Pedido(String dataSaida, String dataChegada, int quant, int cliente, int vendapag, String produtoNome) {
        this.setCliente(cliente);
        this.setDataChegada(dataChegada);
        this.setDataSaida(dataSaida);
        this.setQuantidade(quant);
        this.setVendaPagamento(vendapag);
        this.setProduto(produtoNome);
    }

    // seters
    private void setVendaPagamento(int vendaPagamento) {this.vendaPagamento = vendaPagamento;}

    private void setQuantidade(int quantidade) {this.quantidade = quantidade;}

    private void setDataSaida(String dataSaida) {this.dataSaida = dataSaida;}

    private void setDataChegada(String dataChegada) {this.dataChegada = dataChegada;}

    private void setCliente(int cliente) {this.cliente = cliente;}

    private void setProduto(String produto) {this.produto = produto;}

    // geters
    public int getVendaPagamento() {return vendaPagamento;}

    public int getQuantidade() {return quantidade;}

    public String getDataSaida() {return dataSaida;}

    public String getDataChegada() {return dataChegada;}

    public int getCliente() {return cliente;}

    public String getProduto() {return produto;}

    // metodos

    public void addPedido(String dataSaída,String dataChegada,int qtdProd,String nomeCliente,String nomeProduto,String dataPagamento) {
        try {
            this.setDataSaida(dataSaída);

            this.setDataChegada(dataChegada);

            this.setQuantidade(qtdProd);

            Connection con = Conexao.conectarBD();
            Conexao conn = new Conexao();

            // pegando o id do cliente
            this.setCliente(Integer.parseInt(conn.buscarDado(con, "Cliente", "nome", nomeCliente, null, "id", "asc")));

            this.setProduto(nomeProduto);

            // pegando o id do produto para atualiza-lo após o pedido for feito no banco
            int idProd = Integer
                    .parseInt(conn.buscarDado(con, "Produto", "nome", this.getProduto(), null, "id", "asc"));

            
            // realizando o pagamento
            Pagamento pag = new Pagamento();
            Pedido ped = new Pedido(this.getDataSaida(), this.getDataChegada(), this.getQuantidade(), this.getCliente(),
                    0, this.getProduto());
            this.setVendaPagamento(pag.realizarPagamento(ped, con, idProd,dataPagamento));
            
            // criando o pedido
            String createPed = "insert into Pedido (dataSaida,dataChegada,quantidade,fk_Cliente_ID,fk_Venda_Pagamento_ID) values (?,?,?,?,?)";
            PreparedStatement pstmt = con.prepareStatement(createPed);
            pstmt.setString(1, this.getDataSaida());
            pstmt.setString(2, this.getDataChegada());
            pstmt.setInt(3, this.getQuantidade());
            pstmt.setInt(4, this.getCliente());
            pstmt.setInt(5, this.getVendaPagamento());

            pstmt.executeUpdate();
            pstmt.close();

            String queryIdPedido = "select id from Pedido where dataSaida = " + "\"" + this.getDataSaida() + "\""
                    + " and quantidade = " + this.getQuantidade();

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(queryIdPedido);
            
            if (rs.next()) {
                int idPedido = rs.getInt("id");

                // atualizando o produto
                String updateFkPedido = "update Produto as p set p.fk_Pedido_ID =" + idPedido + " where p.id = " + idProd;

                stmt.executeUpdate(updateFkPedido);
                
                int oldquanti = Integer.parseInt(conn.buscarDado(con, "Produto", "fk_Pedido_ID",
                        Integer.toString(idPedido), "quantidade", "Dados", "asc").split("/")[1]);

                String updateQuantidadeProd = "update Produto as p set p.quantidade = "
                        + (oldquanti - this.getQuantidade()) + " where p.id = " + idProd;
                
                stmt.executeUpdate(updateQuantidadeProd);
                stmt.close();

                // adicionando dados na tabela realizada
                int fkFornecedor = Integer.parseInt(conn.buscarDado(con, "Produto", "fk_Pedido_ID",
                        Integer.toString(idPedido), "fk_Fornecedor_ID", "Dados", "asc").split("/")[1]);

                String addRealizda = "insert into relizada (fk_Fornecedor_ID,fk_Venda_Pagamento_ID) values (?,?)";
                PreparedStatement pstmt2 = con.prepareStatement(addRealizda);

                pstmt2.setInt(1, fkFornecedor);
                pstmt2.setInt(2, this.getVendaPagamento());

                pstmt2.execute();
                pstmt2.close();
                
                JOptionPane.showMessageDialog(null, "Produto vendido com sucesso!");

            } else {
                JOptionPane.showMessageDialog(null, "Ocorreu um erro ao procurar o id do Pedido");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " linha 141 Pedido");
        }
    }

    public void editarPedido(Connection conn, String nomeCliente) {
        Conexao con = new Conexao();
        try {
            int idCliente = Integer
                    .parseInt(con.buscarDado(conn, "Cliente", "nome", nomeCliente, "id", "Dados", "asc").split(" ")[1]);
            Scanner leitura = new Scanner(System.in);
            System.out.println("Digite o que deseja editar: "
                    + "\n1 - Data de Saída\n2 - Data de Chegada\n3 - Quantidade de Produtos\n");
            int op = Integer.parseInt(leitura.nextLine());
            switch (op) {
                case 1:
                    System.out.println("Informe a nova Data de Saída no formato Ano/Mês/dia: ");
                    this.setDataSaida(leitura.nextLine());
                    String updateDataS = "update Pedido set dataSaida = " + "\"" + this.getDataSaida() + "\""
                            + " where fk_Cliente_ID =" + idCliente;
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(updateDataS);
                    stmt.close();
                    leitura.close();
                    JOptionPane.showMessageDialog(null, "Pedido alterado com sucesso!");
                case 2:
                    System.out.println("Informe a nova Data de Chegada no formato Ano/Mês/dia: ");
                    this.setDataChegada(leitura.nextLine());
                    String updateDataC = "update Pedido set dataChegada = " + "\"" + this.getDataChegada() + "\""
                            + " where fk_Cliente_ID =" + idCliente;
                    Statement stmt2 = conn.createStatement();
                    stmt2.executeUpdate(updateDataC);
                    stmt2.close();
                    leitura.close();
                    JOptionPane.showMessageDialog(null, "Pedido alterado com sucesso!");
                case 3:
                    System.out.println("Informe o nova Quantidade de Produtos: ");
                    this.setQuantidade(Integer.parseInt(leitura.nextLine()));
                    String updateQuant = "update Pedido set quantidade = "+ this.getQuantidade()+ " where fk_Cliente_ID =" + idCliente;
                    Statement stmt3 = conn.createStatement();
                    stmt3.executeUpdate(updateQuant);
                    stmt3.close();
                    leitura.close();
                    JOptionPane.showMessageDialog(null, "Pedido alterado com sucesso!");
                default:
                    leitura.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "Pedido");
        }
    }

    public void cancelarPedido(Connection conn, int idPedido){
        try {
            String[] opcoes = {"Sim","Não"};
            int op;
            do{
                int op2 = JOptionPane.showOptionDialog(null, "Deseja cancelar o pedido?", "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes,opcoes[1]);
                if (op2 == JOptionPane.YES_OPTION){
                    op=0;
                    Statement stmt = conn.createStatement();    
                    String updateProd = "update Produto set fk_Pedido_ID = null where fk_Pedido_ID = "+idPedido;
                    stmt.executeUpdate(updateProd);
                    String deletePedido = "delete from Pedido where id = "+idPedido;
                    stmt.executeUpdate(deletePedido);
                    stmt.close();
                    JOptionPane.showMessageDialog(null, "Pedido cancelado com sucesso!");
                }
                else if (op2 == JOptionPane.NO_OPTION) {
                    op=1;
                }
                else op=-1;
            }while(op==-1);

            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }
}
