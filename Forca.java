public class Forca {
    private int erros;

    public Forca() {
        this.erros = 0; // Inicializa com 0 erros
    }

    public void incrementarErros() {
        if (erros < 6) { // Apenas incrementa se os erros forem menores que 6
            erros++;
        }
    }

    public void setErros(int erros) {
        this.erros = erros; // Define o número de erros
    }

    public String getImagemForca() {
        // Retorna o caminho da imagem correspondente ao número de erros
        return "imagens/forca" + erros + ".png"; // Exemplo: "forca0.png", "forca1.png", etc.
    }
}
