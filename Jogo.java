import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jogo {
    private Palavra palavra;                  // Representa a palavra que o jogador deve adivinhar
    private Forca forca;                      // Representa a forca, que acompanha o número de erros
    private int tentativasRestantes;          // Número de tentativas que o jogador ainda tem
    private ArrayList<Character> letrasEscolhidas;  // Lista de letras já escolhidas pelo jogador
    private String dica;  // A dica associada à palavra

    // Construtor para iniciar um novo jogo com base na dificuldade
    public Jogo(int dificuldade) {
        Random random = new Random();
        int choosen = random.nextInt(10);  // Aumentado para 10 palavras
        palavra = new Palavra(selecionaPalavraAleatoria(choosen, dificuldade));  // Inicializa a palavra com uma palavra aleatória
        dica = selecionaDicaPalavraAleatoria(choosen, dificuldade);  // Inicializa a dica associada
        forca = new Forca();                                 // Inicializa a forca
        tentativasRestantes = 6;                             // Define o número máximo de tentativas
        letrasEscolhidas = new ArrayList<>();                // Inicializa a lista de letras escolhidas
    }

// Construtor para carregar um jogo salvo
    public Jogo(String palavraSalva, String letrasEscolhidasSalvas, int tentativasRestantes, String dica) {
        this.palavra = new Palavra(palavraSalva);  
        this.tentativasRestantes = tentativasRestantes;  
        this.letrasEscolhidas = new ArrayList<>();  
        for (char letra : letrasEscolhidasSalvas.toCharArray()) {
            this.letrasEscolhidas.add(letra);  
        }
        this.forca = new Forca();  
        this.dica = dica;  

        // Atualiza as letras adivinhadas na palavra
        for (char letra : letrasEscolhidas) {
            this.palavra.contemLetra(letra);  // Marca cada letra como adivinhada
        }

        // Atualiza a forca com base nas letras escolhidas
        forca.setErros(6 - tentativasRestantes); // Ajusta a forca com o número de erros cometidos
    }


    // Método que seleciona aleatoriamente uma palavra de uma lista predefinida
    public String selecionaPalavraAleatoria(int choosen, int dificuldade) {
        dificuldade--;
        String[][] palavras = {
            // Fácil
            {"CASA", "GATO", "SOL", "AGUA", "FLOR", "BOLA", "PEIXE", "LIVRO", "TIGRE", "CACHORRO"},
            // Médio
            {"COMPUTADOR", "LIVRO", "TELEFONE", "ESCOLA", "JANELA", "MONTANHA", "FESTIVAL", "AERONAVE", "SANDUÍCHE", "CARRO"},
            // Difícil
            {"PSICOLOGO", "FOTOGRAFIA", "TORNADO", "ASTRONOMIA", "ARQUITETURA", "TRANSCENDÊNCIA", "HIPOTENUSA", "PARALELEPÍPEDO", "CIBERNÉTICA", "ANTROPOMORFO"}
        };
        return palavras[dificuldade][choosen];    
    }

    // Método que seleciona a dica associada à palavra
    public String selecionaDicaPalavraAleatoria(int choosen, int dificuldade) {
        dificuldade--;
        String[][] dicas = {
            // Dicas para as palavras fáceis
            {"Lugar onde se mora.", "Animal de estimação que gosta de dormir.", "Estrela que ilumina o dia.", "Líquido essencial para a vida.", "Parte colorida das plantas.", "Objeto redondo usado em jogos.", "Animal que nada.", "Conjunto de páginas encadernadas.", "Felino feroz que vive na selva.", "Animal leal que é considerado o melhor amigo do homem."},
            // Dicas para as palavras médias
            {"Máquina que processa informações.", "Conjunto de folhas com informações escritas.", "Dispositivo usado para se comunicar à distância.", "Lugar onde se aprende.", "Abertura em uma parede para passagem de luz e ar.", "Eleva-se acima do nível do mar.", "Celebração com música e dança.", "Veículo que voa pelo ar.", "Alimento entre duas fatias de pão.", "Transporte de quatro rodas."},
            // Dicas para as palavras difíceis
            {"Profissional que estuda a mente humana.", "Arte de capturar imagens.", "Fenômeno meteorológico de ventos fortes e giratórios.", "Ciência que estuda os astros e o universo.", "Arte e ciência de projetar edifícios.", "Supera o que é normal ou esperado.", "Lado oposto do triângulo em relação ao ângulo reto.", "Forma geométrica com seis faces.", "Ramo da ciência que estuda a computação.", "Diz respeito à forma humana."}
        };
        return dicas[dificuldade][choosen];    
    }

    // Método para processar a tentativa de adivinhar uma letra
    public boolean tentarLetra(char letra) {
        if (letrasEscolhidas.contains(letra)) {
            return false;  // Letra já foi usada
        }

        letrasEscolhidas.add(letra);  // Adiciona a letra à lista de escolhidas

        if (!palavra.contemLetra(letra)) {
            tentativasRestantes--;      // Decrementa as tentativas restantes
            forca.incrementarErros();   // Atualiza a forca
        }
        return true;
    }

    public List<Character> getLetrasNaoAdivinhadas() {
        List<Character> letrasNaoAdivinhadas = new ArrayList<>();
        for (int i = 0; i < palavra.length(); i++) {
            if (!palavra.isLetraAdivinhada(i)) {
                letrasNaoAdivinhadas.add(palavra.getPalavra().charAt(i));
            }
        }
        return letrasNaoAdivinhadas;
    }

    // Método que retorna a palavra em forma de String
    public String getPalavra() {
        return palavra.getPalavra();
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
