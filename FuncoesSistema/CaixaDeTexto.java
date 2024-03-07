package FuncoesSistema;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import java.sql.Connection;

public class CaixaDeTexto extends JFrame {
    private JTextField email;
    private JPasswordField senha;
    private JButton login, limpa;
    private JLabel user, pass;

    public CaixaDeTexto() {
        super("Login em Java");
        setLayout(new FlowLayout());

        user = new JLabel("Email: ");
        add(user);

        email = new JTextField(50);
        add(email);

        pass = new JLabel("Senha:   ");
        add(pass);

        senha = new JPasswordField(10);
        add(senha);

        login = new JButton("Entrar");
        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                Connection con = Conexao.conectarBD();
                Conexao conn = new Conexao();
                String result = conn.autenticacao(con, email.getText(), getName());
                if (result!=null) {
                    JOptionPane.showMessageDialog(null, "Parabéns, você entrou na Matrix!");
                    Conexao.fecharBD(con);
                }

                else {
                    JOptionPane.showMessageDialog(null, "Senha errada! Estude Java!");
                    Conexao.fecharBD(con);
                }

            }
        });
        add(login);

        limpa = new JButton("Limpar");
        limpa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evento) {
                if (evento.getSource() == limpa) {
                    email.setText("");
                    senha.setText("");
                }
            }
        });
        add(limpa);
    }

    public static void main(String[] args) {
        CaixaDeTexto texfield = new CaixaDeTexto();

        texfield.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        texfield.setSize(310, 110);
        texfield.setVisible(true);
    }
}
