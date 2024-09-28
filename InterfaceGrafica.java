import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Collectors;
import java.util.List;

public class InterfaceGrafica {
    private Jogo jogo;
    private JFrame frame;
    private JLabel palavraLabel, tentativasLabel, forcaLabel, letrasEscolhidasLabel, letrasRestantesLabel;
    private JTextField inputLetra;
    private JButton confirmarButton;
    private JLabel imagemForcaLabel;
    private JPanel painelJogo;

    public InterfaceGrafica() {
        frame = new JFrame("Jogo da Forca");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        telaInicial();  // Chama a tela inicial ao abrir o jogo
    }

    private void telaInicial() {
        // Painel inicial com o título e o botão para começar o jogo
        JPanel painelInicial = new JPanel();
        painelInicial.setLayout(new BoxLayout(painelInicial, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Jogo da Forca");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton comecarButton = new JButton("Começar Jogo");
        comecarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        comecarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJogo();
            }
        });

        painelInicial.add(Box.createRigidArea(new Dimension(0, 50)));  // Espaçamento
        painelInicial.add(titulo);
        painelInicial.add(Box.createRigidArea(new Dimension(0, 20)));  // Espaçamento
        painelInicial.add(comecarButton);

        frame.add(painelInicial);
        frame.setVisible(true);
    }

    private void iniciarJogo() {
        // Limpa a tela inicial
        frame.getContentPane().removeAll();
        frame.revalidate();
        frame.repaint();

        // Inicializa o jogo
        jogo = new Jogo();

        // Painel do jogo
        painelJogo = new JPanel();
        painelJogo.setLayout(new BoxLayout(painelJogo, BoxLayout.Y_AXIS));

        palavraLabel = new JLabel("Palavra: " + jogo.getPalavraParcial());
        tentativasLabel = new JLabel("Tentativas Restantes: " + jogo.getTentativasRestantes());
        letrasEscolhidasLabel = new JLabel("Letras Escolhidas: ");
        letrasRestantesLabel = new JLabel("Letras Restantes: " + getLetrasRestantes());

        imagemForcaLabel = new JLabel(new ImageIcon(jogo.getForca().getImagemForca()));

        // Painel para a linha de entrada
        JPanel painelEntrada = new JPanel();
        inputLetra = new JTextField(1);  // Campo para digitar a letra
        confirmarButton = new JButton("Confirmar");
        confirmarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char letra = inputLetra.getText().toUpperCase().charAt(0);
                if (jogo.tentarLetra(letra)) {
                    atualizarTela();
                }
            }
        });

        painelEntrada.add(inputLetra);
        painelEntrada.add(confirmarButton);

        painelJogo.add(palavraLabel);
        painelJogo.add(tentativasLabel);
        painelJogo.add(letrasEscolhidasLabel);
        painelJogo.add(letrasRestantesLabel);  // Adiciona letras restantes
        painelJogo.add(imagemForcaLabel);
        painelJogo.add(painelEntrada);  // Adiciona o painel de entrada

        frame.add(painelJogo);
        frame.revalidate();
        frame.repaint();
    }

    // Método que gera as letras restantes, subtraindo as já escolhidas do alfabeto
    private String getLetrasRestantes() {
        String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        List<Character> letrasEscolhidas = jogo.getLetrasEscolhidas();

        return alfabeto.chars()
                       .mapToObj(c -> (char) c)
                       .filter(c -> !letrasEscolhidas.contains(c))
                       .map(String::valueOf)
                       .collect(Collectors.joining(", "));
    }

    private void atualizarTela() {
        palavraLabel.setText("Palavra: " + jogo.getPalavraParcial());
        tentativasLabel.setText("Tentativas Restantes: " + jogo.getTentativasRestantes());

        // Atualiza letras escolhidas
        String letrasEscolhidasStr = jogo.getLetrasEscolhidas()
                                          .stream()
                                          .map(String::valueOf)
                                          .collect(Collectors.joining(", "));
        letrasEscolhidasLabel.setText("Letras Escolhidas: " + letrasEscolhidasStr);

        // Atualiza letras restantes
        letrasRestantesLabel.setText("Letras Restantes: " + getLetrasRestantes());

        // Atualiza a imagem da forca
        imagemForcaLabel.setIcon(new ImageIcon(jogo.getForca().getImagemForca()));

        if (jogo.jogoTerminado()) {
            mostrarTelaFinal(jogo.getTentativasRestantes() > 0);
        }
    }

    private void mostrarTelaFinal(boolean venceu) {
        String mensagem = venceu ? "Você venceu!" : "Você perdeu!";
        int opcao = JOptionPane.showOptionDialog(frame, mensagem + "\nDeseja jogar novamente?", "Fim de Jogo",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Jogar Novamente", "Sair"}, "Jogar Novamente");

        if (opcao == JOptionPane.YES_OPTION) {
            iniciarJogo();  // Reinicia o jogo
        } else {
            frame.dispose();  // Fecha o jogo
        }
    }

    public static void main(String[] args) {
        new InterfaceGrafica();
    }
}
