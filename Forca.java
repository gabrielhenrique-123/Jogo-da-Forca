public class Forca {
    private int erros;

    public Forca() {
        this.erros = 0;
    }

    public void incrementarErros() {
        erros++;
    }

    public int getErros() {
        return erros;
    }

    public String desenharForca() {
        switch (erros) {
            case 1: return "Cabeca";
            case 2: return "Cabeca, Tronco";
            case 3: return "Cabeca, Tronco, Braco Esquerdo";
            case 4: return "Cabeca, Tronco, Bracos";
            case 5: return "Cabeca, Tronco, Bracos, Perna Esquerda";
            case 6: return "Cabeca, Tronco, Bracos, Pernas";
            default: return "Vazia";
        }
    }

    public String getImagemForca() {
        return "imagens/forca" + erros + ".png";  
    }
}
