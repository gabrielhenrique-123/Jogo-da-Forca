public class Palavra {
    private String palavra;
    private String dica;
    private boolean[] letrasAdivinhadas;

    public Palavra(String palavra) {
        this.palavra = palavra;
        letrasAdivinhadas = new boolean[palavra.length()];
    }

    public boolean contemLetra(char letra) {
        boolean acertou = false;
        for (int i = 0; i < palavra.length(); i++) {
            if (palavra.charAt(i) == letra) {
                letrasAdivinhadas[i] = true;
                acertou = true;
            }
        }
        return acertou;
    }

    public boolean foiAdivinhada() {
        for (boolean adivinhada : letrasAdivinhadas) {
            if (!adivinhada) {
                return false;
            }
        }
        return true;
    }

    public String getPalavraParcial() {
        StringBuilder parcial = new StringBuilder();
        for (int i = 0; i < palavra.length(); i++) {
            if (letrasAdivinhadas[i]) {
                parcial.append(palavra.charAt(i));
            } else {
                parcial.append('_');
            }
            parcial.append(' ');
        }
        return parcial.toString().trim();
    }
}
