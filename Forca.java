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

    public String getImagemForca() {
        return "imagens/forca" + erros + ".png";  
    }
}
