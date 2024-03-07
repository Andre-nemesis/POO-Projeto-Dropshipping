package ClassesBancos;

import java.util.Scanner;
import javax.swing.JOptionPane;

import FuncoesSistema.Conexao;

import java.sql.*;
import java.util.ArrayList;

public class Cliente {
    private String nome;
    private String cpf;
    private String cep;
    private String endereco;
    private String uf;
    private String email;
    private String senha;
    private String telefone;
    private Long numeroCartao;
    private ArrayList<Produto> carrinhoDeCompras = new ArrayList<Produto>();
    

    // construtores

    public Cliente(){}

    public Cliente(String nome, String cpf, String endereco, String uf,String cep, String email, String senha, String telefone, Long numeroCartao){
        this.setCpf(cpf);
        this.setEmail(email);
        this.setEndereco(endereco);
        this.setNome(nome);
        this.setNumeroCartao(numeroCartao);
        this.setSenha(senha);
        this.setTelefone(telefone);
        this.setUf(uf);
        this.setCep(cep);
    }

    // seters
    public void setCep(String cep) {this.cep = cep;}

    private void setUf(String uf) {this.uf = uf;}

    private void setTelefone(String telefone) {this.telefone = telefone;}

    private void setSenha(String senha) {this.senha = senha;}

    private void setNumeroCartao(Long numeroCartao) {this.numeroCartao = numeroCartao;}

    private void setNome(String nome) {this.nome = nome;}

    private void setEndereco(String endereco) {this.endereco = endereco;}

    private void setEmail(String email) {this.email = email;}

    private void setCpf(String cpf) {this.cpf = cpf;}

    private void setCarrinho(Produto prod){this.carrinhoDeCompras.add(prod);}

    // geters

    public String getCep() {return cep;}

    public String getUf() {return uf;}

    public String getTelefone() {return telefone;}

    public String getSenha() {return senha;}

    public Long getNumeroCartao() {return numeroCartao;}

    public String getNome() {return nome;}

    public String getEndereco() {return endereco;}

    public String getEmail() {return email;}

    public String getCpf() {return cpf;}

    public ArrayList<Produto> getCarrinhoDeCompras() {return carrinhoDeCompras;}

    // metodos
    public void cadastrarCliente(Connection conn,String nomeCliente,String cpf,String telefone,String uf,String endereco,String cep,String email,String senha,String nCartao){
        try {
            String query = "insert into Cliente (cpf,nome,telefone,uf,endereco,cep,email,senha,numeroCartao) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, cpf);
            stmt.setString(2, nomeCliente);
            stmt.setString(3, telefone);
            stmt.setString(4, uf);
            stmt.setString(5, endereco);
            stmt.setString(6, cep);
            stmt.setString(7, email);
            stmt.setString(8, senha);
            stmt.setLong(9, Long.parseLong(nCartao));

            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void mostrarClientes(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Cliente");
            if (rs.next()) {
                while (rs.next()) {
                    System.out.print("| ID: " + rs.getString("id"));
                    System.out.print(" | CPF: " + rs.getString("cpf"));
                    System.out.print(" | Nome: " + rs.getString("nome"));
                    System.out.print(" | Telefone: " + rs.getString("telefone"));
                    System.out.print(" | CEP: " + rs.getString("cep"));
                    System.out.print(" | UF: " + rs.getString("uf"));
                    System.out.print(" | Endereço: " + rs.getString("endereco"));
                    System.out.print(" | E-mail: " + rs.getString("email"));
                    System.out.print(" | Senha: " + rs.getString("senha"));
                    System.out.print(" | Número do Cartão: " + rs.getString("numeroCartao") + " |");
                    System.out.println();
                }
            } else
                JOptionPane.showMessageDialog(null, "Não foi encontrado dados na tabela Cliente");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private String _editarCliente(){
        Scanner leitura = new Scanner(System.in);
        boolean controle = false;
        do{
            try {
                System.out.println("\nDigite qual informação deseja alterar:");
                System.out.println("1 - Nome\n2 - CPF\n3 - E-mail\n4 - Senha\n5 - Telefone\n6 - Endereço\n7 - UF\n8 - Número do Cartão");
                String info = leitura.nextLine();
                int opcao = Integer.parseInt(info);  

                switch (opcao) {
                    case 1:
                        System.out.println("Digite o Novo nome:");
                        this.setNome(leitura.nextLine());
                        controle = true;
                        leitura.close();
                        return "nome";
                    case 2:
                        System.out.println("Digite o Novo CPF:");
                        this.setCpf(leitura.nextLine());
                        controle = true;
                        leitura.close();
                        return "cpf";
                    case 3:
                        System.out.println("Digite o Novo Email:");
                        this.setEmail(leitura.nextLine());
                        controle = true;
                        leitura.close();
                        return "email";
                    case 4:
                        System.out.println("Digite a Nova Senha:");
                        this.setSenha(leitura.nextLine());
                        controle = true;
                        leitura.close();
                        return "senha";
                    case 5:
                        System.out.println("Digite o Novo Telefone:");
                        this.setTelefone(leitura.nextLine());
                        controle = true;
                        leitura.close();
                        return "telefone";
                    case 6:
                        System.out.println("Digite o Novo Endereço:");
                        this.setEndereco(leitura.nextLine());
                        controle = true;
                        leitura.close();
                        return "endereco";
                    case 7:
                        System.out.println("Digite o Novo UF:");
                        this.setUf(leitura.nextLine());
                        controle = true;
                        leitura.close();
                        return "uf";
                    case 8:
                        System.out.println("Digite o Novo Número do Cartão:");
                        this.setNumeroCartao(leitura.nextLong());
                        controle = true;
                        leitura.close();
                        return "numeroCartao";
                    default:
                        System.out.println("Valor Inválido!\nDigite novamente.");
                        return null;
                }
                  
            } catch (Exception e) {
                System.out.println("Ocorreu um erro ao inserir os dados!\nDigite novamente.");
                return null;
            }
        }while(controle!=true);
    }

    public void editarCliente(Connection conn, String cpfCliente){ 
        String result = this._editarCliente();
        try {
            Conexao con = new Conexao();
            int idCliente = Integer.parseInt(con.buscarDado(conn, "Cliente", "cpf", cpfCliente, null, "id", "asc"));
            switch (result) {
                case "nome":
                    String queryNome = "update cliente set nome =" + "\"" + this.getNome() + "\"" + " where id ="
                            + idCliente;
                    PreparedStatement stmtCase1 = conn.prepareStatement(queryNome);
                    stmtCase1.execute();
                    stmtCase1.close();
                    break;
                case "cpf":
                    String queryCpf = "update cliente set cpf =" + "\"" + this.getCpf() + "\"" + " where id ="
                            + idCliente;
                    PreparedStatement stmtCase2 = conn.prepareStatement(queryCpf);
                    stmtCase2.execute();
                    stmtCase2.close();
                    break;
                case "numeroCartao":
                    String queryNCartao = "update cliente set numeroCartao =" + this.getNumeroCartao() + " where id ="
                            + idCliente;
                    PreparedStatement stmtCase3 = conn.prepareStatement(queryNCartao);
                    stmtCase3.execute();
                    stmtCase3.close();
                    break;
                case "uf":
                    String queryUf = "update cliente set uf =" + "\"" + this.getUf() + "\"" + "where id =" + idCliente;
                    PreparedStatement stmtCase4 = conn.prepareStatement(queryUf);
                    stmtCase4.execute();
                    stmtCase4.close();
                    break;
                case "endereco":
                    String queryEnd = "update cliente set endereco =" + "\"" + this.getEndereco() + "\"" + "where id ="
                            + idCliente;
                    PreparedStatement stmtCase5 = conn.prepareStatement(queryEnd);
                    stmtCase5.execute();
                    stmtCase5.close();
                    break;
                case "telefone":
                    String queryTell = "update cliente set telefone =" + "\"" + this.getTelefone() + "\"" + "where id ="
                            + idCliente;
                    PreparedStatement stmtCase6 = conn.prepareStatement(queryTell);
                    stmtCase6.execute();
                    stmtCase6.close();
                    break;
                case "senha":
                    String queryPass = "update cliente set senha =" + "\"" + this.getSenha() + "\"" + "where id ="
                            + idCliente;
                    PreparedStatement stmtCase7 = conn.prepareStatement(queryPass);
                    stmtCase7.execute();
                    stmtCase7.close();
                    break;
                case "email":
                    String queryEmail = "update cliente set email =" + "\"" + this.getSenha() + "\"" + "where id ="
                            + idCliente;
                    PreparedStatement stmtCase8 = conn.prepareStatement(queryEmail);
                    stmtCase8.execute();
                    stmtCase8.close();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Não foi possivel encontrar os dados a serem alterados");
            }
            JOptionPane.showMessageDialog(null, "Dados do Cliente alterado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage()+" linha244");
        }
    }

    public void adicionarProduto(Produto prod){
        this.setCarrinho(prod);
    }

    public void removerProduto(Produto prod){this.carrinhoDeCompras.remove(prod);}

    public void removerCliente(Connection conn, int idCliente){
        try {
            String query = "delete from cliente where id = " + idCliente;
            PreparedStatement ps = conn.prepareCall(query);
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "Cliente removido com Sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
