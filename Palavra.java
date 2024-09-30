public class Palavra {
    private String palavra;
    private boolean[] letrasAdivinhadas;  // Array para verificar quais letras foram adivinhadas

    public Palavra(String palavra) {
        this.palavra = palavra;
        letrasAdivinhadas = new boolean[palavra.length()];  // Inicializa o array com o tamanho da palavra
    }

    public String getPalavra(){
        return palavra;
    }

    // Verifica se a palavra contém a letra e atualiza as letras adivinhadas
    public boolean contemLetra(char letra) {
        boolean acertou = false;
        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) == letra) {
                letrasAdivinhadas[i] = true;  // Marca como adivinhada
                acertou = true;
            }
        }
        return acertou;
    }

    // Retorna a palavra parcialmente descoberta com base nas letras adivinhadas
    public String getPalavraParcial() {
        StringBuilder parcial = new StringBuilder();
        for (int i = 0; i < palavra.length(); i++) {
            if (letrasAdivinhadas[i]) {
                parcial.append(palavra.charAt(i));
            } else {
                parcial.append('_');  // Mostra _ para letras não adivinhadas
            }
            parcial.append(' ');
        }
        return parcial.toString().trim();
    }

    // Retorna o comprimento da palavra
    public int length() {
        return palavra.length();
    }

    // Verifica se a palavra foi completamente adivinhada
    public boolean foiAdivinhada() {
        for (boolean adivinhada : letrasAdivinhadas) {
            if (!adivinhada) {
                return false;
            }
        }
        return true;
    }

    // Retorna se uma letra na posição específica foi adivinhada
    public boolean isLetraAdivinhada(int index) {
        return letrasAdivinhadas[index];
    }
}
