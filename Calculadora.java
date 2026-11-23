
package calculadora;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;

public class Calculadora {

    private JFrame frmJoaoPaulo;
    private JTextField JTFResultado;

    double valor1, valor2;
    String op = "";
    boolean novoNumero = true;


    private static final String NOME = "João Paulo de Souza Tavares";


    private static final Color FUNDO = new Color(116, 111, 205);

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Calculadora window = new Calculadora();
                    window.frmJoaoPaulo.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Calculadora() {
        initialize();
    }

    private void digitar(String digito) {
        if (novoNumero || JTFResultado.getText().equals("0")
                || JTFResultado.getText().equals("Erro")) {
            JTFResultado.setText(digito);
            novoNumero = false;
        } else {
            JTFResultado.setText(JTFResultado.getText() + digito);
        }
    }

    private void virgula() {
        if (novoNumero || JTFResultado.getText().equals("Erro")) {
            JTFResultado.setText("0,");
            novoNumero = false;
        } else if (!JTFResultado.getText().contains(",")) {
            JTFResultado.setText(JTFResultado.getText() + ",");
        }
    }

    private double lerVisor() {
        return Double.parseDouble(
                JTFResultado.getText().replace(",", "."));
    }

    private void mostrar(double valor) {
        if (valor == Math.rint(valor) && Math.abs(valor) < 1e15) {
            JTFResultado.setText(String.valueOf((long) valor));
        } else {
            JTFResultado.setText(
                    String.valueOf(valor).replace(".", ","));
        }
    }

    public double adicao(double a, double b) {
        return a + b;
    }

    public double subtracao(double a, double b) {
        return a - b;
    }

    public double multiplicacao(double a, double b) {
        return a * b;
    }

    public double divisao(double a, double b) {
        return a / b;
    }

    public double potencia(double a, double b) {
        return Math.pow(a, b);
    }

    private void erro() {
        JTFResultado.setText("Erro");
        op = "";
        novoNumero = true;
    }

    private boolean calcular() {
        valor2 = lerVisor();

        if (op.equals("soma")) {
            valor1 = adicao(valor1, valor2);

        } else if (op.equals("subtracao")) {
            valor1 = subtracao(valor1, valor2);

        } else if (op.equals("multiplicacao")) {
            valor1 = multiplicacao(valor1, valor2);

        } else if (op.equals("divisao")) {
            if (valor2 == 0) {
                erro();
                return false;
            }
            valor1 = divisao(valor1, valor2);

        } else if (op.equals("potencia")) {
            valor1 = potencia(valor1, valor2);
        }

        if (Double.isNaN(valor1) || Double.isInfinite(valor1)) {
            erro();
            return false;
        }

        mostrar(valor1);
        return true;
    }

    private void operacao(String novaOp) {
        if (JTFResultado.getText().equals("Erro")) {
            return;
        }

        if (!op.isEmpty() && !novoNumero) {
            if (!calcular()) {
                return;
            }
        } else {
            valor1 = lerVisor();
        }

        op = novaOp;
        novoNumero = true;
    }

    private void igual() {
        if (op.isEmpty() || JTFResultado.getText().equals("Erro")) {
            return;
        }

        if (calcular()) {
            op = "";
        }

        novoNumero = true;
    }

    private void limpar() {
        JTFResultado.setText("0");
        valor1 = 0;
        valor2 = 0;
        op = "";
        novoNumero = true;
    }

    private void initialize() {

        frmJoaoPaulo = new JFrame();

        frmJoaoPaulo.setTitle(NOME + " - RA: 0025697");
        frmJoaoPaulo.getContentPane().setBackground(FUNDO);
        frmJoaoPaulo.getContentPane().setLayout(null);

        JTFResultado = new JTextField();
        JTFResultado.setText("0");
        JTFResultado.setEditable(false);
        JTFResultado.setHorizontalAlignment(SwingConstants.RIGHT);
        JTFResultado.setFont(new Font("Arial Black", Font.PLAIN, 50));
        JTFResultado.setBounds(10, 30, 439, 73);

        frmJoaoPaulo.getContentPane().add(JTFResultado);
        JTFResultado.setColumns(10);

        criarBotao("0", 104, 427, () -> digitar("0"));
        criarBotao("1", 10, 341, () -> digitar("1"));
        criarBotao("2", 104, 341, () -> digitar("2"));
        criarBotao("3", 201, 341, () -> digitar("3"));
        criarBotao("4", 10, 268, () -> digitar("4"));
        criarBotao("5", 104, 268, () -> digitar("5"));
        criarBotao("6", 201, 268, () -> digitar("6"));
        criarBotao("7", 10, 193, () -> digitar("7"));
        criarBotao("8", 104, 193, () -> digitar("8"));
        criarBotao("9", 201, 193, () -> digitar("9"));

        criarBotao("C", 10, 114, () -> limpar());
        criarBotao("^", 272, 114, () -> operacao("potencia"));
        criarBotao("/", 364, 114, () -> operacao("divisao"));
        criarBotao("*", 364, 193, () -> operacao("multiplicacao"));
        criarBotao("-", 364, 268, () -> operacao("subtracao"));
        criarBotao("+", 364, 341, () -> operacao("soma"));
        criarBotao("=", 364, 437, () -> igual());
        criarBotao(",", 272, 437, () -> virgula());

        frmJoaoPaulo.setBackground(FUNDO);
        frmJoaoPaulo.setBounds(100, 100, 475, 565);
        frmJoaoPaulo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmJoaoPaulo.setResizable(false);
        frmJoaoPaulo.setLocationRelativeTo(null);
    }

    private void criarBotao(String texto, int x, int y, Runnable acao) {

        JButton botao = new JButton(texto);

        botao.addActionListener(e -> acao.run());
        botao.setFont(new Font("Tahoma", Font.PLAIN, 50));
        botao.setBounds(x, y, 72, 64);

        frmJoaoPaulo.getContentPane().add(botao);
    }
}
