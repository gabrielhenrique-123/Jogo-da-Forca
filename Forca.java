public class Forca {
  private int erros;

  public Forca() {
    this.erros = 0; 
  }

  public void incrementarErros() {
    if (erros < 6) { 
      erros++;
    }
  }

  public void setErros(int erros) {
    this.erros = erros; 
  }

  //Busca a imagem da forca na pasta imagens/forca1.png por exemplo
  public String getImagemForca() {
    return "imagens/forca" + erros + ".png"; 
  }
}
