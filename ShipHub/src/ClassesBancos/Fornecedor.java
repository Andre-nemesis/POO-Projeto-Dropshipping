package ClassesBancos;
import java.sql.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

import FuncoesSistema.Conexao;

public class Fornecedor {
    private String nome;
    private String cnpj;
    private String telefone;
    private String endereco;
    private String uf;
    private String email;
    private String senha;

    // construtores
    public Fornecedor(){}

    public Fornecedor(String nome, String cpnj, String telefone, String endereco, String uf, String email,String senha){
        this.setEmail(email);
        this.setNome(nome);
        this.setCnpj(cpnj);
        this.setTelefone(telefone);
        this.setEndereco(endereco);
        this.setUf(uf);
        this.setSenha(senha);
    }

    // seters
    private void setUf(String uf) {this.uf = uf;}

    private void setTelefone(String telefone) {this.telefone = telefone;}

    private void setNome(String nome) {this.nome = nome;}

    private void setEndereco(String endereco) {this.endereco = endereco;}

    private void setEmail(String email) {this.email = email;}

    private void setCnpj(String cnpj) {this.cnpj = cnpj;}

    private void setSenha(String senha) {this.senha = senha;}

    //geters
    public String getUf() {return this.uf;}
    
    public String getTelefone() {return this.telefone;}
    
    public String getNome() {return this.nome;}
    
    public String getEndereco() {return this.endereco;}
    
    public String getEmail() {return this.email;}
    
    public String getCnpj() {return this.cnpj;}

    public String getSenha() {return this.senha;}

    // metodos
    public void cadastrarFornecedor(Connection conn,String nome,String cnpj,String telefone,String uf,String endereco,String email,String senha,String cep){
        try {
            String query = "insert into Fornecedor (cnpj,nome,telefone,uf,endereco,email,senha,cep) values (?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, cnpj);
            stmt.setString(2, nome);
            stmt.setString(3, telefone);
            stmt.setString(4, uf);
            stmt.setString(5, endereco);
            stmt.setString(6, email);
            stmt.setString(7, senha);
            stmt.setString(8, query);

            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    private String _editarFornecedor(){
        Scanner leitura = new Scanner(System.in);
        boolean controle = false;
        do{
            try {
                System.out.println("\nDigite qual informação deseja alterar:");
                System.out.println("1 - Nome\n2 - CNPJ\n3 - E-mail\n4 - Senha\n5 - Telefone\n6 - Endereço\n7 - UF");
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
                        System.out.println("Digite o Novo CNPJ:");
                        this.setCnpj(leitura.nextLine());
                        controle = true;
                        leitura.close();
                        return "cnpj";
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

    public void editarFornecedor(Connection conn, String nome){
        try {
            Conexao con = new Conexao();
            String result = this._editarFornecedor();
            int idFornecedor = Integer.parseInt(con.buscarDado(conn, "Fornecedor", "nome", nome, null, "id", "asc"));
            switch (result) {
                case "nome":
                    String queryNome = "update fornecedor set nome =" + "\"" + this.getNome() + "\"" + " where id ="
                            + idFornecedor;
                    PreparedStatement stmtCase1 = conn.prepareStatement(queryNome);
                    stmtCase1.execute();
                    stmtCase1.close();
                    break;
                case "cnpj":
                    String queryCnpj = "update fornecedor set cnpj =" + "\"" + this.getCnpj() + "\"" + " where id ="
                            + idFornecedor;
                    PreparedStatement stmtCase2 = conn.prepareStatement(queryCnpj);
                    stmtCase2.execute();
                    stmtCase2.close();
                    break;
                case "uf":
                    String queryUf = "update fornecedor set uf =" + "\"" + this.getUf() + "\"" + "where id ="
                            + idFornecedor;
                    PreparedStatement stmtCase4 = conn.prepareStatement(queryUf);
                    stmtCase4.execute();
                    stmtCase4.close();
                    break;
                case "endereco":
                    String queryEnd = "update fornecedor set endereco =" + "\"" + this.getEndereco() + "\""
                            + "where id =" + idFornecedor;
                    PreparedStatement stmtCase5 = conn.prepareStatement(queryEnd);
                    stmtCase5.execute();
                    stmtCase5.close();
                    break;
                case "telefone":
                    String queryTell = "update fornecedor set telefone =" + "\"" + this.getTelefone() + "\""
                            + "where id =" + idFornecedor;
                    PreparedStatement stmtCase6 = conn.prepareStatement(queryTell);
                    stmtCase6.execute();
                    stmtCase6.close();
                    break;
                case "senha":
                    String queryPass = "update fornecedor set senha =" + "\"" + this.getSenha() + "\"" + "where id ="
                            + idFornecedor;
                    PreparedStatement stmtCase7 = conn.prepareStatement(queryPass);
                    stmtCase7.execute();
                    stmtCase7.close();
                    break;
                case "email":
                    String queryEmail = "update fornecedor set email =" + "\"" + this.getEmail() + "\"" + "where id ="
                            + idFornecedor;
                    PreparedStatement stmtCase8 = conn.prepareStatement(queryEmail);
                    stmtCase8.execute();
                    stmtCase8.close();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Não foi possivel encontrar os dados a serem alterados");
            }
            JOptionPane.showMessageDialog(null, "Dados do Fornecedor alterado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void mostrarFornecedores(Connection conn){
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Fornecedor");
            if (rs.next()) {
                do {
                    System.out.println("| ID: " + rs.getString("id") +
                            " | CNPJ: " + rs.getString("cnpj") +
                            " | Nome: " + rs.getString("nome") +
                            " | Telefone: " + rs.getString("telefone") +
                            " | UF: " + rs.getString("uf") +
                            " | Endereço: " + rs.getString("endereco") +
                            " | E-mail: " + rs.getString("email") +
                            " | Senha: " + rs.getString("senha") + " |");
                } while (rs.next());
            } else
                JOptionPane.showMessageDialog(null, "Não foi encontrado dados na tabela Fornecedor");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public static String[] getFornecedores(){
        Connection conn = Conexao.conectarBD();
        Conexao con = new Conexao();
        String[] dados = con.buscarDado(conn, "Fornecedor", "Nome", "*", "Nome", "Dados", "asc").split("/");
        String[] fornecedorNomes = new String[dados.length];
        int count = 0;
        for (int i = 0; i < dados.length; i++) {
            fornecedorNomes[count] = dados[i];
            count++;
        }
        return fornecedorNomes;
    }

    public void removerFornecedor(Connection conn, int idFornecedor){
        try {
            String query = "delete from fornecedor where id = " + idFornecedor;
            PreparedStatement ps = conn.prepareCall(query);
            ps.execute();
            ps.close();
            JOptionPane.showMessageDialog(null, "Fornecedor removido com Sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
