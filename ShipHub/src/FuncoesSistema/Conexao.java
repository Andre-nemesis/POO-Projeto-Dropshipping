package FuncoesSistema;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import ClassesBancos.*;

public class Conexao {
    // conexões
    public static Connection conectarBD() {
        final String conn = "mysql";
        final String host = "localhost";
        final String port = "3306";
        final String database = "dbshiphub2";
        final String url = "jdbc:" + conn + "://" + host + ":" + port + "/" + database;
        final String username = "root";
        final String password = "andre";
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return null;
    }

    public static void fecharBD(Connection conexao) {
        if (conexao != null) {
            try {
                conexao.close();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }

    }

    // cadastros
    public void cadastrarFornecedor(Connection conn,String nome,String cnpj,String telefone,String uf,String endereco,String email,String senha,String cep) {
        try {
            Fornecedor forn = new Fornecedor();
            forn.cadastrarFornecedor(conn,nome,cnpj,telefone,uf,endereco,email,senha,cep);    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage()+ "linha 49");
        }
        
        
    }

    public void cadastrarCliente(Connection conn,String nomeCliente,String cpf,String telefone,String uf,String endereco,String cep,String email,String senha,String nCartao) {
        try {
            Cliente cli = new Cliente();
            cli.cadastrarCliente(conn,nomeCliente,cpf,telefone,uf,endereco,cep,email,senha,nCartao);    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage()+ "linha 60");
        }
        
    }

    public void cadastrarProduto(Connection conn,String nomeProduto, String descProduto,double valorProduto,int quantProduto,String nomeTipoProduto,String nomeFornecedor) {
        try {
            Produto prod = new Produto();
            prod.cadastrarProduto(conn,nomeProduto,descProduto,valorProduto,quantProduto,nomeTipoProduto,nomeFornecedor);    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage()+ "linha 70");
        }
        
    }

    public void cadastrarTipoProduto(Connection conn) {
        try {
            TipoProduto tprod = new TipoProduto();
            tprod.cadastrarTipoProduto(conn);   
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.getMessage()+ "linha 80");
        }
    }

    public void realizarPedido(Connection conn,String dataSaída,String dataChegada,int qtdProd,String nomeCliente,String nomeProduto,String dataPagamento){
        try {
            Pedido ped = new Pedido();
            ped.addPedido(dataSaída,dataChegada,qtdProd,nomeCliente,nomeProduto,dataPagamento);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+" linha 89");
        }
    }
    
    // mostrar informações
    public void mostrarFornecedores(Connection conn) {
        try {
            Fornecedor fornecedor = new Fornecedor();
            fornecedor.mostrarFornecedores(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+" linha 99");
        }
    }

    public void mostrarClientes(Connection conn) {
        try {
            Cliente clientes = new Cliente();
            clientes.mostrarClientes(conn);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+" linha 108");
        }
    }

    // editar cadastros
    public void editarCliente(Connection conn, String cpfCliente){
        try {
            Cliente cli = new Cliente();
            cli.editarCliente(conn,cpfCliente);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+" linha244");
        }
    }

    public void editarFornecedor(Connection conn, String nome) {
        try {Fornecedor forn = new Fornecedor();
            forn.editarFornecedor(conn,nome);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void editarProduto(Connection conn, String nome,String opcao,String dado) {
        try {Produto prod = new Produto();
            prod.editarProduto(conn,nome,opcao,dado);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void editarTipoProduto(Connection conn,String nomeDoTipoDeProdutoAntigo){
        try {
            TipoProduto tProd = new TipoProduto();
            tProd.editarTipoProduto(conn,nomeDoTipoDeProdutoAntigo);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+" linha 374");
        }
    }
    
    public void editarPedido(Connection conn,String nomeCliente){
        try {
            Pedido ped = new Pedido();
            ped.editarPedido(conn, nomeCliente);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + " linha 397");
        }
    }

    // buscar dados
    // ajeitar os outros casos
    public String buscarDado(Connection conn, String tabela, String coluna, String dado, String colunasRetornadas,
            String optionReturn, String ordem) {
        // primeiro caso
        if ((colunasRetornadas == null || colunasRetornadas.equals("")) && optionReturn.equals("Dados")
                && (ordem.equals("asc") || ordem.equals("ASC"))) {
            if (!(dado.equals("*"))) {
                try {
                    String query = "select * from " + tabela + " where " + tabela + "." + coluna + " like " + "'" + dado
                            + "'" + " order by " + coluna + " " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        return this.infoCliente(conn, tabela, rs.getString("id"), null, ordem);
                    } else {
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            } else {
                try {
                    String query = "select * from " + tabela + " order by " + coluna + " " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        return this.infoCliente(conn, tabela, "*", null, ordem);
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            }
        }

        else if ((colunasRetornadas == null || colunasRetornadas.equals("")) && optionReturn.equals("Dados")
                && (ordem.equals("desc") || ordem.equals("DESC"))) {
            if (!(dado.equals("*"))) {
                try {
                    String query = "select * from " + tabela + " where " + tabela + "." + coluna + " like " + "'" + dado
                            + "'" + " order by " + coluna + " " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        return this.infoCliente(conn, tabela, rs.getString("id"), null, ordem);
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            } else {
                try {
                    String query = "select * from " + tabela + " order by " + coluna + " " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        return this.infoCliente(conn, tabela, "*", null, ordem);
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            }
        }

        else if ((colunasRetornadas == null || colunasRetornadas.equals("")) && optionReturn.equals("Dados")
                && ordem == null) {
            if (!(dado.equals("*"))) {
                try {
                    String query = "select * from " + tabela + " where " + tabela + "." + coluna + " like " + "'" + dado
                            + "'";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        return this.infoCliente(conn, tabela, rs.getString("id"), null, null);
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage() + " linha452");
                    return null;
                }
            } else {
                try {
                    String query = "select * from " + tabela;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        return this.infoCliente(conn, tabela, "*", null, null);
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage() + " linha471");
                    return null;
                }
            }
        }

        // segundo caso
        else if (colunasRetornadas != null && optionReturn.equals("Dados")
                && (ordem.equals("asc") || ordem.equals("ASC"))) {
            if (!(dado.equals("*"))&&colunasRetornadas.equals("*")) {
                try {
                    String query = "select " + colunasRetornadas + " from " + tabela + " where " + tabela + "." + coluna
                            + " like " + "\"" + dado + "\"" + " order by " + coluna + " "
                            + ordem;
                    
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        
                        String queryId = "select * from "+tabela + " where " + tabela +"."+coluna + " like " + "\"" + dado + "\"";
                        
                        Statement stmt2 = conn.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(queryId);
                        String id = "";
                        if (rs2.next()) {
                            id = rs2.getString("id");
                        }
                        
                        return this.infoCliente(conn, tabela, id, null, ordem);
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            } 
            
            else if (!(dado.equals("*"))&&!(colunasRetornadas.equals("*"))){
                try {
                    String query = "select " + colunasRetornadas + " from " + tabela + " where " + tabela + "." + coluna
                            + " like " + "\"" + dado + "\"" + " order by " + colunasRetornadas.split(",")[0] + " "
                            + ordem;
                    
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        String queryId = "select * from "+tabela + " where " + tabela +"."+coluna + " like " + "\"" + dado + "\"";
                        
                        Statement stmt2 = conn.createStatement();
                        ResultSet rs2 = stmt2.executeQuery(queryId);
                        String gg = "";
                        if (rs2.next()) {
                            gg = rs2.getString("id");
                        }
                        
                        return this.infoCliente(conn, tabela, gg, colunasRetornadas, ordem);
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            } 
            

            else {
                try {
                    String query = "select " + colunasRetornadas + " from " + tabela + " order by "
                            + colunasRetornadas.split(",")[0] + " " + ordem;
                    
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        
                        return this.infoCliente(conn, tabela, "*", colunasRetornadas, ordem);
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            }
        }

        else if (colunasRetornadas != null && optionReturn.equals("Dados")
                && (ordem.equals("desc") || ordem.equals("DESC"))) {
            if (!(dado.equals("*"))) {
                try {
                    String query = "select " + colunasRetornadas + " from " + tabela + " where " + tabela + "." + coluna
                            + " like " + "'" + dado + "'" + " order by " + colunasRetornadas.split(",")[0] + " "
                            + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        
                        return this.infoCliente(conn, tabela, rs.getString("id"), colunasRetornadas, ordem);
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            } else {
                try {
                    String query = "select " + colunasRetornadas + " from " + tabela + " order by "
                            + colunasRetornadas.split(",")[0] + " " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        return this.infoCliente(conn, tabela, "*", colunasRetornadas, ordem);
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            }
        }

        else if (colunasRetornadas != null && optionReturn.equals("Dados") && ordem == null) {
            if (!(dado.equals("*"))) {
                try {
                    String query = "select " + colunasRetornadas + " from " + tabela + " where " + tabela + "." + coluna
                            + " like " + "'" + dado + "'";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        return this.infoCliente(conn, tabela, rs.getString("id"), colunasRetornadas, null);
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage() + " linha576");
                    return null;
                }
            } else {
                try {
                    String query = "select " + colunasRetornadas + " from " + tabela;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        
                        return this.infoCliente(conn, tabela, "*", colunasRetornadas, null);
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage() + " linha595");
                    return null;
                }
            }
        }

        // terceiro caso
        else if ((colunasRetornadas == null || colunasRetornadas.equals(""))
                && (optionReturn.equals("id") || optionReturn.equals("ID"))
                && (ordem.equals("asc") || ordem.equals("ASC"))) {
            if (!(dado.equals("*"))) {
                try {
                    String query = "select * from " + tabela + " where " + tabela + "." + coluna + " like " + "'" + dado
                            + "'" + " order by " + coluna + " " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        
                        return rs.getString("id");
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            } else {
                try {
                    String query = "select * from " + tabela + " order by " + coluna + " " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        
                        return rs.getString("id");
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            }
        }

        else if ((colunasRetornadas == null || colunasRetornadas.equals(""))
                && (optionReturn.equals("id") || optionReturn.equals("ID"))
                && (ordem.equals("desc") || ordem.equals("DESC"))) {
            if (!(dado.equals("*"))) {
                try {
                    String query = "select * from " + tabela + " where " + tabela + "." + coluna + " like " + "'" + dado
                            + "'" + " order by " + coluna + " " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        
                        return rs.getString("id");
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            } else {
                try {
                    String query = "select * from " + tabela + " order by " + coluna + " " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        
                        return rs.getString("id");
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                    return null;
                }
            }
        }

        else if ((colunasRetornadas == null || colunasRetornadas.equals(""))
                && (optionReturn.equals("id") || optionReturn.equals("ID") && ordem == null)) {
            if (!(dado.equals("*"))) {
                try {
                    String query = "select * from " + tabela + " where " + tabela + "." + coluna + " like " + "'" + dado
                            + "'";
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        
                        return rs.getString("id");
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage() + " linha700");
                    return null;
                }
            } else {
                try {
                    String query = "select * from " + tabela;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        
                        return rs.getString("id");
                    } else {
                        
                        return null;
                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage() + " linha419");
                    return null;
                }
            }
        }

        // "caso padrão"
        else
            return null;
    }

    private String infoCliente(Connection conn, String tabela, String idDado, String colunasRetornadas, String ordem) {
        // caso 1
        if ((ordem.equals("asc")||ordem.equals("ASC"))||(ordem.equals("DESC")||ordem.equals("desc"))){
            if (idDado.equals("*") && (colunasRetornadas == null || colunasRetornadas.equals(""))&& (ordem.equals("asc") || ordem.equals("ASC"))) {
                try {
                    String query = "SELECT * FROM " + tabela + " ORDER BY id " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String resultado = "";
                    while (rs.next()) {
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            if (rs.getMetaData().getColumnName(i).equals("id")) {
                                resultado += "\n";
                            }
                            resultado += " " + rs.getString(i);
                        }
                    }
                    return resultado;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage()+" linha886");
                    return null;
                }
    
            }

            else if (idDado.equals("*") && (colunasRetornadas == null || colunasRetornadas.equals(""))&& (ordem.equals("desc") || ordem.equals("DESC"))) {
                try {
                    String query = "SELECT * FROM " + tabela + " ORDER BY id " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String resultado = "";
                    while (rs.next()) {
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            if (rs.getMetaData().getColumnName(i).equals("id")) {
                                resultado += "\n";
                            }
                            resultado += "/" + rs.getString(i);
                        }
                    }
                    return resultado;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage()+" linha908");
                    return null;
                }
    
            }

            else if (idDado.equals("*") && colunasRetornadas != null && (ordem.equals("asc") || ordem.equals("ASC"))) {
                try {
                    String query = "SELECT " + colunasRetornadas + " FROM " + tabela + " ORDER BY "+ colunasRetornadas.split(",")[0] + " " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String resultado = "";
                    while (rs.next()) {
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            if (rs.getMetaData().getColumnName(i).equals("id")) {
                                resultado += "\n";
                            }
                            resultado += "/" + rs.getString(i);
                        }
                    }
                    return resultado;    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage()+" linha930");
                    return null;
                }
                
            }
    
            else if (idDado.equals("*") && colunasRetornadas != null && (ordem.equals("desc") || ordem.equals("DESC"))) {
                try {
                    String query = "SELECT " + colunasRetornadas + " FROM " + tabela + " ORDER BY "
                        + colunasRetornadas.split(",")[0] + " " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String resultado = "";
                    while (rs.next()) {
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            if (rs.getMetaData().getColumnName(i).equals("id")) {
                                resultado += "\n";
                            }
                            resultado += "/" + rs.getString(i);
                        }
                    }
                    return resultado;    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage()+" linha953");
                    return null;
                }
                
            }
            
            else if (!(idDado.equals("*")) && (colunasRetornadas == null || colunasRetornadas.equals(""))
                && (ordem.equals("asc") || ordem.equals("ASC"))) {
            try {
                String query = "SELECT * FROM " + tabela + " WHERE " + tabela + ".id = " + idDado + " ORDER BY id " + ordem;
                
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                String resultado = "";
                if (rs.next()) {
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        resultado +="/" + rs.getString(i);
                    }
                }
                return resultado;    
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage()+" linha973");
                return null;
            }
            
        }

            else if (!(idDado.equals("*")) && (colunasRetornadas == null || colunasRetornadas.equals(""))
                    && (ordem.equals("desc") || ordem.equals("DESC"))) {
                try {
                    String query = "SELECT * FROM " + tabela + " WHERE " + tabela + ".id = " + idDado + " ORDER BY id " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String resultado = "";
                    if (rs.next()) {
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            resultado += "/" + rs.getString(i);
                        }
                    }
                    return resultado;    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage()+" linha993");
                    return null;
                }
                
            }
            
            else if (idDado.equals("*") && colunasRetornadas!=null
                    && (ordem.equals("asc") || ordem.equals("ASC"))) {
                try {
                    String query = "SELECT " + colunasRetornadas + " FROM " + tabela + " WHERE " + tabela + ".id = " + idDado
                        + " ORDER BY " + colunasRetornadas.split(",")[0] + " " + ordem;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String resultado = "";
                    if (rs.next()) {
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            resultado += "/" + rs.getString(i);
                        }
                    }
                    return resultado;    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage()+" linha1014");
                    return null;
                }
                
            }

            else {
                try {
                    String query = "SELECT " + colunasRetornadas + " FROM " + tabela + " WHERE " + tabela + ".id = " + idDado
                        + " ORDER BY " + colunasRetornadas.split(",")[0] + " " + ordem;
                    
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String resultado = "";
                    if (rs.next()) {
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            resultado += "/" + rs.getString(i);
                        }
                    }
                    return resultado;            
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage()+" linha1034");
                    return null;
                }
        
            }

    }

        // caso 2
        else {
            if (idDado.equals("*") && (colunasRetornadas == null || colunasRetornadas.equals(""))) {
                try {
                    String query = "SELECT * FROM " + tabela;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String resultado = "";
                    while (rs.next()) {
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            if (rs.getMetaData().getColumnName(i).equals("id")) {
                                resultado += "\n";
                            }
                            resultado += "/" + rs.getString(i);
                        }
                    }
                    return resultado;    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage()+" linha1060");
                    return null;
                }
                
            }
            
            else if (idDado.equals("*") && colunasRetornadas != null) {
                try {
                    String query = "SELECT " + colunasRetornadas + " FROM " + tabela;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String resultado = "";
                    while (rs.next()) {
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            if (rs.getMetaData().getColumnName(i).equals("id")) {
                                resultado += "\n";
                            }
                            resultado += "/" + rs.getString(i);
                        }
                    }
                    return resultado;     
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage()+" linha1082");
                    return null;
                }
                
            }
    
            else if (!(idDado.equals("*")) && (colunasRetornadas == null || colunasRetornadas.equals(""))) {
                try {
                    String query = "SELECT * FROM " + tabela + " WHERE " + tabela + ".id = " + idDado;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String resultado = "";
                    if (rs.next()) {
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            if (rs.getMetaData().getColumnName(i).equals("id")) {
                                resultado += "\n";
                            }
                            resultado += "/" + rs.getString(i);
                        }
                    }
                    return resultado;    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage()+" linha1101");
                    return null;
                }
            
        }

            else {
                try {
                    String query = "SELECT " + colunasRetornadas + " FROM " + tabela + " WHERE " + tabela + ".id = " + idDado;
                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    String resultado = "";
                    if (rs.next()) {
                        for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                            if (rs.getMetaData().getColumnName(i).equals("id")) {
                                resultado += "\n";
                            }
                            resultado += "/" + rs.getString(i);
                        }
                    }
                    return resultado;    
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage()+" linha1120");
                    return null;
                }
                
            }}

        }                

    // apagar dados
    public void removerCliente(Connection conn, int idCliente) {
        Cliente cliente = new Cliente();
        cliente.removerCliente(conn, idCliente);
    }

    public void removerFornecedor(Connection conn, int idFornecedor) {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.removerFornecedor(conn, idFornecedor);
    }

    public void cancelarPagamento(Connection conn,String nomeProduto){
        Pagamento pagamento = new Pagamento();
        pagamento.cancelarPagamento(conn, nomeProduto);
    }

    public void cancelarPedido(Connection conn,int idPedido){
        Pedido ped = new Pedido();
        ped.cancelarPedido(conn, idPedido);
    }
    
    // autenticacao
    public String autenticacao(Connection conn, String email, String senha) {
        try {
            String result1Cliente = this.buscarDado(conn, "Cliente", "email", email, null, "id", "asc");
            String result2Cliente = this.buscarDado(conn, "Cliente", "senha", senha, null, "id", "asc");
            if (!(result1Cliente == null && result2Cliente == null)) {
                JOptionPane.showMessageDialog(null, "Cliente Autenticado");
                return "Cliente";
            } else {
                String result1Fornecedor = this.buscarDado(conn, "Fornecedor", "email", email, null, "id", "asc");
                String result2Fornecedor = this.buscarDado(conn, "Fornecedor", "senha", senha, null, "id", "asc");
                if (!(result1Fornecedor == null && result2Fornecedor == null)) {
                    JOptionPane.showMessageDialog(null, "Fornecedor Autenticado");
                    return "Fornecedor";
                } else {
                    JOptionPane.showMessageDialog(null, "Email ou senha estão incorretas!");
                    return null;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = Conexao.conectarBD();
        Conexao con = new Conexao();
        //String dd = con.buscarDado(conn, "Fornecedor", "Nome", "*", "Nome", "Dados", "asc");
        String[] dados = con.buscarDado(conn, "Produto", "fk_TipoProduto_ID", "2", "Nome", "Dados", "asc").split("/");
        String[] TprodNomes = new String[dados.length];
        int count = 0;
        for (int i = 0; i < dados.length; i++) {
            System.out.println(dados[i]);
            TprodNomes[count] = dados[i];
            count++;
            
        }
        //System.out.println(dd);
        
        
        Conexao.fecharBD(conn);
    }
}
