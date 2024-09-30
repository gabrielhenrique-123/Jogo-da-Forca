import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogo {
    private Palavra palavra;                  // Representa a palavra que o jogador deve adivinhar
    private Forca forca;                      // Representa a forca, que acompanha o número de erros
    private int tentativasRestantes;          // Número de tentativas que o jogador ainda tem
    private ArrayList<Character> letrasEscolhidas;  // Lista de letras já escolhidas pelo jogador
    private String dica;  // A dica associada à palavra

    // Construtor da classe Jogo
    public Jogo(int dificuldade) {
        Random random = new Random();
        int choosen = random.nextInt(5);
        palavra = new Palavra(selecionaPalavraAleatoria(choosen, dificuldade));  // Inicializa a palavra com uma palavra aleatória
        dica = selecionaDicaPalavraAleatoria(choosen, dificuldade);  // Inicializa a dica associada
        forca = new Forca();                                 // Inicializa a forca
        tentativasRestantes = 6;                             // Define o número máximo de tentativas
        letrasEscolhidas = new ArrayList<>();                // Inicializa a lista de letras escolhidas
    }

    // Método que seleciona aleatoriamente uma palavra de uma lista predefinida
    public String selecionaPalavraAleatoria(int choosen, int dificuldade) {
        dificuldade--;
        String[][] palavras = {
            // Fácil
            {"CASA", "GATO", "SOL", "ÁGUA", "FLOR"},
            // Médio
            {"COMPUTADOR", "LIVRO", "TELEFONE", "ESCOLA", "JANELA"},
            // Difícil
            {"PSICÓLOGO", "FOTOGRAFIA", "TORNADO", "ASTRONOMIA", "ARQUITETURA"}
        };
        return palavras[dificuldade][choosen];    
    }

    // Método que seleciona a dica associada à palavra
    public String selecionaDicaPalavraAleatoria(int choosen, int dificuldade) {
        dificuldade--;
        String[][] dicas = {
            // Dicas para as palavras fáceis
            {"Lugar onde se mora.", "Animal de estimação que gosta de dormir.", "Estrela que ilumina o dia.", "Líquido essencial para a vida.", "Parte colorida das plantas."},
            // Dicas para as palavras médias
            {"Máquina que processa informações.", "Conjunto de folhas com informações escritas.", "Dispositivo usado para se comunicar à distância.", "Lugar onde se aprende.", "Abertura em uma parede para passagem de luz e ar."},
            // Dicas para as palavras difíceis
            {"Profissional que estuda a mente humana.", "Arte de capturar imagens.", "Fenômeno meteorológico de ventos fortes e giratórios.", "Ciência que estuda os astros e o universo.", "Arte e ciência de projetar edifícios."}
        };
        return dicas[dificuldade][choosen];    
    }

    // Método para processar a tentativa de adivinhar uma letra
    public boolean tentarLetra(char letra) {
        // Verifica se a letra já foi escolhida
        if (letrasEscolhidas.contains(letra)) {
            return false;  // Letra já foi usada, não faz nada
        }

        letrasEscolhidas.add(letra);  // Adiciona a letra à lista de escolhidas

        // Verifica se a palavra contém a letra escolhida
        if (!palavra.contemLetra(letra)) {
            tentativasRestantes--;      // Se não contém, decrementa as tentativas restantes
            forca.incrementarErros();   // E incrementa o número de erros (atualizando a forca)
        }
        return true;
    }

    // Método que retorna a dica associada à palavra
    public String getDica() {
        return dica;
    }

    // Método que retorna a lista de letras escolhidas pelo jogador
    public List<Character> getLetrasEscolhidas() {
        return letrasEscolhidas;
    }

    // Método que retorna o objeto Forca
    public Forca getForca() {
        return forca;
    }

    // Método que retorna a palavra parcialmente descoberta
    public String getPalavraParcial() {
        return palavra.getPalavraParcial();
    }

    // Método que retorna o número de tentativas restantes
    public int getTentativasRestantes() {
        return tentativasRestantes;
    }

    // Verifica se o jogo terminou, seja por vitória ou por derrota
    public boolean jogoTerminado() {
        return tentativasRestantes == 0 || palavra.foiAdivinhada();
    }
}
