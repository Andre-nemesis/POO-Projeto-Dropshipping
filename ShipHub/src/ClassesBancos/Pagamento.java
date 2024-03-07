package ClassesBancos;

import javax.swing.JOptionPane;

import FuncoesSistema.Conexao;
import FuncoesSistema.GerarData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class Pagamento {
    private String dataPagamento;
    private float valorFinal;
    private String prazo;

    // construtores
    public Pagamento() {
    }

    public Pagamento(String dataPagamento, float valorFinal, String prazo) {
        this.setDataPagamento(dataPagamento);
        this.setPrazo(prazo);
        this.setValorFinal(valorFinal);
    }

    // seters
    private void setValorFinal(float valorFinal) {
        this.valorFinal = valorFinal;
    }

    private void setPrazo(String prazo) {
        this.prazo = prazo;
    }

    private void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    // geters
    public float getValorFinal() {
        return valorFinal;
    }

    public String getPrazo() {
        return prazo;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    // metodos

    public int realizarPagamento(Pedido ped, Connection connection, int idProduto, String dataPagamento) {
        try {

            this.setDataPagamento(dataPagamento);

            Conexao con = new Conexao();

            this.setValorFinal(Float.parseFloat(
                    con.buscarDado(connection, "Produto", "id", Integer.toString(idProduto), "valor", "Dados", "asc")
                            .split("/")[1])
                    * ped.getQuantidade());

            // inserindo valores no banco
            String realizandoPagamento = "insert into Venda_Pagamento (DataPagamento,prazo,ValorFinal) values (?,?,?)";
            PreparedStatement pstmt = connection.prepareStatement(realizandoPagamento);
            pstmt.setString(1, this.getDataPagamento());
            pstmt.setString(2, GerarData.gerarDataPrazo(GerarData.getDataSaida()));
            pstmt.setFloat(3, this.valorFinal);

            pstmt.execute();
            pstmt.close();

            // retornando o id da venda realizada
            Statement stmt = connection.createStatement();
            String queryIdVenda = "select id from Venda_Pagamento where DataPagamento = " + "\""
                    + this.getDataPagamento() + "\"";

            ResultSet rs = stmt.executeQuery(queryIdVenda);
            if (rs.next()) {
                int idVenda = rs.getInt("id");

                return idVenda;
            } else {
                return -1;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "linha 53 Pagamento");
            return -1;
        }

    }

    public void cancelarPagamento(Connection conn, String nomeProduto) {
        try {
            Conexao con = new Conexao();
            String[] opcoes = { "Sim", "Não" };
            int op;
            do {
                int op2 = JOptionPane.showOptionDialog(null, "Deseja remover o Produto?", "Aviso",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, opcoes[1]);
                if (op2 == JOptionPane.YES_OPTION) {
                    op = 0;
                } else if (op2 == JOptionPane.NO_OPTION) {
                    op = 1;
                } else
                    op = -1;
            } while (op == -1);

            if (op == 0) {
                int idProd = Integer.parseInt(
                        con.buscarDado(conn, "Produto", "nome", nomeProduto, "id", "Dados", "asc").split("/")[1]);

                String queryIdPedido = "select fk_Pedido_ID from Produto where id = " + idProd;
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(queryIdPedido);
                int fkPedidoId = 0;
                
                if (rs.next()) {
                    if (rs.getString("fk_Pedido_ID")==null)
                        fkPedidoId = -1;    
                    else 
                        fkPedidoId = Integer.parseInt(rs.getString("fk_Pedido_ID"));
                    System.out.println(queryIdPedido+" "+fkPedidoId);
                } else {
                    fkPedidoId = -1;
                }
                if (fkPedidoId != -1) {
                    stmt.close();
                    int idVenda = Integer.parseInt(con.buscarDado(conn, "Pedido", "id", Integer.toString(fkPedidoId),
                            "fk_Venda_Pagamento_ID", "Dados", "asc").split("/")[1]);

                    System.out.println(idVenda);
                    Statement stmt2 = conn.createStatement();

                    String deletePagamento = "delete from Venda_Pagamento where id = " + idVenda;
                    stmt2.executeUpdate(deletePagamento);

                    String deletePagamentoRealizado = "delete from relizada where fk_Venda_Pagamento_ID = " + idVenda;
                    stmt2.executeUpdate(deletePagamentoRealizado);

                    String deletePed = "delete from Pedido as p where p.id=" + fkPedidoId;
                    stmt2.executeUpdate(deletePed);

                    String deleteProduto = "delete from Produto as p where p.id =" + idProd;
                    stmt2.executeUpdate(deleteProduto);

                    stmt2.close();
                    JOptionPane.showMessageDialog(null, "Produto e pagamentos removidos com sucesso!");
                } else {
                    Statement stmt2 = conn.createStatement();
                    String deleteProduto = "delete from Produto as p where p.id =" + idProd;
                    stmt2.executeUpdate(deleteProduto);
                    stmt2.close();
                    JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
                }

            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
