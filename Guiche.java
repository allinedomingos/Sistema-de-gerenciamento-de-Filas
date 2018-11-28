package Guiche;

import java.util.ArrayList;
public class Guiche {
    private int numero;
    private String status;
    private ArrayList<Guiche> guiches = new ArrayList<>();//lista de guiches operando
    private final ArrayList<Integer> all = new ArrayList<>();//lista de guiches operando apenas numero
    private int n = 0;
    public Guiche(){}
    
    public Guiche(int numero, String status) {
        this.numero = numero;
        this.status = status;
    }
    public int getNumero() {
        //System.out.println("o num eh " + numero);
        return numero;
    }
    public String getStatus() {
        //System.out.println("O guichê " + numero + " está com o status de " + status);
        return status;
    }
    public void changeStatus(String status) {
        this.status = status;
    }
    
    public void getGuiches(ArrayList<Guiche> g1 ) {
        this.guiches = g1;
    }
 
    public void obtemGuiches() {
            int aux;
            Guiche gui = new Guiche();
            for (int i = 0; i < guiches.size(); i++) {
                gui = guiches.get(i);
                aux = gui.getNumero();
                all.add(aux);
               // System.out.print("Guichê: ");
                System.out.println(all.get(i) + " eh um guiche" );
            }    
    }
}