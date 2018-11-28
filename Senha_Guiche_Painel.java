package Central;


public class Senha_Guiche_Painel {
    private String senha;
    private int guiche;

    public Senha_Guiche_Painel() {
    }

    public Senha_Guiche_Painel(String senha, int guiche) {
        this.senha = senha;
        this.guiche = guiche;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setGuiche(int guiche) {
        this.guiche = guiche;
    }

    public int getGuiche() {
        return guiche;
    }
}
