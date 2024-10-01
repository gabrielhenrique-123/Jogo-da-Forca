import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class InterfaceGrafica {
  private Jogo jogo;
  private JFrame frame;
  private JLabel palavraLabel, tentativasLabel, letrasEscolhidasLabel, letrasRestantesLabel, dicaLabel;
  private JPanel painelLetras;
  private JButton[] botoesLetras;  
  private JLabel imagemForcaLabel;
  private JPanel painelJogo;
  private int vitorias, derrotas;  
  private final String pontuacaoFile = "pontuacao.txt";  
  private final String estadoJogoFile = "estado_jogo.txt"; 
  private final String historicoFile = "historico.txt";  // Arquivo para salvar o histórico de palavras e pontuação
  private boolean jogoAnteriorExiste;  

  public InterfaceGrafica() {
    carregarPontuacao();  
    carregarEstadoJogo();  // Verifica se há um jogo salvo
    frame = new JFrame("Jogo da Forca");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700, 700);
    telaInicial();  // Chama a tela inicial ao abrir o jogo
  }

  private void telaInicial() {
    // Limpa todos os componentes anteriores da tela
    frame.getContentPane().removeAll();
    frame.revalidate();
    frame.repaint();

    // Painel inicial com o título e os botões para começar o jogo
    JPanel painelInicial = new JPanel();
    painelInicial.setLayout(new BoxLayout(painelInicial, BoxLayout.Y_AXIS));

    JLabel titulo = new JLabel("Jogo da Forca");
    titulo.setFont(new Font("Arial", Font.BOLD, 24));
    titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

    // ComboBox para seleção de dificuldade
    String[] opcoesDificuldade = {"Fácil", "Médio", "Difícil"};
    JComboBox<String> comboDificuldade = new JComboBox<>(opcoesDificuldade);
    JPanel painelCombo = new JPanel(new FlowLayout(FlowLayout.CENTER));
    painelCombo.add(comboDificuldade);
    comboDificuldade.setPreferredSize(new Dimension(100, 30));
      
    JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.CENTER));
    
    // Botão para iniciar um novo jogo
    JButton comecarButton = new JButton("Começar Novo Jogo");
    painelBotao.add(comecarButton);

    comecarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        int dificuldade = comboDificuldade.getSelectedIndex() + 1;  
        iniciarJogo(dificuldade);
      }
    });

    // Botão para continuar o jogo anterior
    JButton continuarButton = new JButton("Continuar Jogo Anterior");
    continuarButton.setEnabled(jogoAnteriorExiste);
    painelBotao.add(continuarButton);

    continuarButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        continuarJogoAnterior();  
      }
    });

    painelInicial.add(Box.createRigidArea(new Dimension(0, 50)));  
    painelInicial.add(titulo);
    painelInicial.add(Box.createRigidArea(new Dimension(0, 20)));  
    painelInicial.add(painelCombo);  
    painelInicial.add(Box.createRigidArea(new Dimension(0, 20)));  
    painelInicial.add(painelBotao);  

    frame.add(painelInicial);
    frame.setVisible(true);
  }

  private void iniciarJogo(int dificuldade) {
    // Limpa a tela inicial
    frame.getContentPane().removeAll();
    frame.revalidate();
    frame.repaint();

    // Inicializa o jogo
    jogo = new Jogo(dificuldade);
    montarTelaJogo();
  }

  // Função para continuar o jogo anterior
  private void continuarJogoAnterior() {
    frame.getContentPane().removeAll();
    frame.revalidate();
    frame.repaint();    
    montarTelaJogo();
  }

  // Função para montar a tela do jogo 
  private void montarTelaJogo() {
    // Painel do jogo
    painelJogo = new JPanel();
    painelJogo.setLayout(new BoxLayout(painelJogo, BoxLayout.Y_AXIS));

    palavraLabel = new JLabel("Palavra: " + jogo.getPalavraParcial());
    palavraLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    tentativasLabel = new JLabel("Tentativas Restantes: " + jogo.getTentativasRestantes());
    tentativasLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    letrasEscolhidasLabel = new JLabel("Letras Escolhidas: " + jogo.getLetrasEscolhidas());
    letrasEscolhidasLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    letrasRestantesLabel = new JLabel("Letras Restantes: " + getLetrasRestantes());
    letrasRestantesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    dicaLabel = new JLabel("Dica: " + jogo.getDica());
    dicaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    imagemForcaLabel = new JLabel(new ImageIcon(jogo.getForca().getImagemForca()));
    imagemForcaLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    // CreateRigidArea para fazer o espaçamento na tela
    painelJogo.add(palavraLabel);
    painelJogo.add(Box.createRigidArea(new Dimension(0, 20)));  
    painelJogo.add(tentativasLabel);
    painelJogo.add(Box.createRigidArea(new Dimension(0, 20)));
    painelJogo.add(letrasEscolhidasLabel);
    painelJogo.add(Box.createRigidArea(new Dimension(0, 20)));
    painelJogo.add(letrasRestantesLabel);  
    painelJogo.add(Box.createRigidArea(new Dimension(0, 20)));
    painelJogo.add(dicaLabel);  
    painelJogo.add(Box.createRigidArea(new Dimension(0, 20)));
    painelJogo.add(imagemForcaLabel);
    painelJogo.add(Box.createRigidArea(new Dimension(0, 30)));  

    // Criação dos botões das letras do alfabeto
    painelLetras = new JPanel();
    painelLetras.setLayout(new GridLayout(3, 9, 10, 10));  
    botoesLetras = new JButton[26];
    char[] alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    for (int i = 0; i < alfabeto.length; i++) {
      char letra = alfabeto[i];
      botoesLetras[i] = new JButton(String.valueOf(letra));

      botoesLetras[i].setPreferredSize(new Dimension(50, 50));
      botoesLetras[i].setMargin(new Insets(5, 5, 5, 5));  // Margem dos botões

      botoesLetras[i].setBackground(new Color(255, 102, 102));  // Cor vermelha para o fundo dos botões
      botoesLetras[i].setOpaque(true);
      botoesLetras[i].setBorderPainted(false);

      // Verifica se a letra foi escolhida e desativa o botão se necessário
      if (jogo.getLetrasEscolhidas().contains(letra)) {
        botoesLetras[i].setEnabled(false);  
        botoesLetras[i].setBackground(Color.DARK_GRAY);  
      }

      botoesLetras[i].addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          tentarLetra(letra);
          atualizarTela();
          salvarEstadoJogo();  // Salva o estado do jogo após cada jogada
        }
      });
      painelLetras.add(botoesLetras[i]);
    }

    painelLetras.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  // Espaçamento uniforme em torno dos botões
    painelJogo.add(painelLetras);  

    // Botão de dica
    JButton dicaButton = new JButton("Usar Dica");
    dicaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    dicaButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        mostrarDica();
      }
    });
    painelJogo.add(Box.createRigidArea(new Dimension(0, 20)));  
    painelJogo.add(dicaButton);  

    frame.add(painelJogo);
    frame.revalidate();
    frame.repaint();
  }

  // Tenta a letra
  private void tentarLetra(char letra) {
    if (jogo.tentarLetra(letra)) {
      for (JButton botao : botoesLetras) {
        if (botao.getText().charAt(0) == letra) {
          botao.setEnabled(false);  
          botao.setBackground(Color.DARK_GRAY);  
          break;
        }
      }
    }
  } 

  // Atualiza a tela baseado nas condições do momento
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
      if (jogo.getTentativasRestantes() > 0) {
        vitorias++;
      } else {
          derrotas++;
      }
      salvarPontuacao();  // Salva a pontuação após cada rodada
      mostrarTelaFinal(jogo.getTentativasRestantes() > 0);
    }
  }
  

  private String getLetrasRestantes() {
    String alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    List<Character> letrasEscolhidas = jogo.getLetrasEscolhidas();

    return alfabeto.chars()
            .mapToObj(c -> (char) c)
            .filter(c -> !letrasEscolhidas.contains(c))
            .map(String::valueOf)
            .collect(Collectors.joining(", "));
  }

  // Mostra a tela final de vitória ou derrota
  private void mostrarTelaFinal(boolean venceu) {
    String mensagem = venceu ? "Você venceu!" : "Você perdeu!";
    mensagem += String.format("\nVitórias: %d\nDerrotas: %d", vitorias, derrotas);  // Mostra a pontuação
    
    // Salva a palavra e o resultado no histórico
    salvarHistorico(jogo.getPalavra(), venceu); 

    int opcao = JOptionPane.showOptionDialog(frame, mensagem + "\nDeseja jogar novamente?", "Fim de Jogo",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new Object[]{"Jogar Novamente", "Sair"}, "Jogar Novamente");

    if (opcao == JOptionPane.YES_OPTION) {
      frame.getContentPane().removeAll();
      telaInicial();
    } else {
        frame.dispose();  // Fecha o jogo
    }
  }

  // Método para salvar o histórico de palavras e pontuação
  private void salvarHistorico(String palavra, boolean venceu) {
    try (PrintWriter pw = new PrintWriter(new FileWriter(historicoFile, true))) {
      pw.println(String.format("Palavra: %s | Resultado: %s | Vitórias: %d | Derrotas: %d", 
          palavra, venceu ? "Venceu" : "Perdeu", vitorias, derrotas));
    } catch (IOException e) {
        System.out.println("Erro ao salvar histórico: " + e.getMessage());
    }
  }

  // Carrega a pontuação do arquivo
  private void carregarPontuacao() {
    try (BufferedReader br = new BufferedReader(new FileReader(pontuacaoFile))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] partes = line.split(":");
        if (partes[0].trim().equals("Vitórias")) {
          vitorias = Integer.parseInt(partes[1].trim());
        } else if (partes[0].trim().equals("Derrotas")) {
            derrotas = Integer.parseInt(partes[1].trim());
        }
      }
    } catch (IOException e) {
        System.out.println("Erro ao carregar pontuação: " + e.getMessage());
    }
  }

  // Salva a pontuação no arquivo
  private void salvarPontuacao() {
    try (PrintWriter pw = new PrintWriter(new FileWriter(pontuacaoFile))) {
      pw.println("Vitórias: " + vitorias);
      pw.println("Derrotas: " + derrotas);
    } catch (IOException e) {
        System.out.println("Erro ao salvar pontuação: " + e.getMessage());
    }
  }

  // Carrega o estado do jogo de um arquivo
  private void carregarEstadoJogo() {
    try (BufferedReader br = new BufferedReader(new FileReader(estadoJogoFile))) {
      String palavra = br.readLine();
      String letrasEscolhidas = br.readLine();
      int tentativasRestantes = Integer.parseInt(br.readLine());
      String dica = br.readLine();
      // Reconstruindo o jogo usando o novo constructor
      jogo = new Jogo(palavra, letrasEscolhidas, tentativasRestantes, dica);
      jogoAnteriorExiste = true;  // Há um jogo salvo
    } catch (IOException e) {
        System.out.println("Nenhum estado de jogo anterior encontrado: " + e.getMessage());
    }
  }

  // Salvar o estado do jogo em um arquivo
  private void salvarEstadoJogo() {
    try (PrintWriter pw = new PrintWriter(new FileWriter(estadoJogoFile))) {
      pw.println(jogo.getPalavra());  // Salva a palavra
      pw.println(jogo.getLetrasEscolhidas().stream()
              .map(String::valueOf)
              .collect(Collectors.joining()));  // Salva as letras escolhidas
      pw.println(jogo.getTentativasRestantes());  // Salva o número de tentativas restantes
      pw.println(jogo.getDica());
    } catch (IOException e) {
        System.out.println("Erro ao salvar estado do jogo: " + e.getMessage());
    }
  }

  // Mostra uma letra aleatoria não adivinha ainda
  private void mostrarDica() {
    List<Character> letrasRestantes = jogo.getLetrasNaoAdivinhadas();

    if (!letrasRestantes.isEmpty()) {
      Random random = new Random();
      char letraDica = letrasRestantes.get(random.nextInt(letrasRestantes.size()));
      jogo.tentarLetra(letraDica);
      atualizarTela();
    } else {
        JOptionPane.showMessageDialog(frame, "Não há mais letras disponíveis para dica!", "Dica", JOptionPane.INFORMATION_MESSAGE);
    }
  }

  public static void main(String[] args) {
    new InterfaceGrafica();
  }
}
