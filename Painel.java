
package Painel;

  //painel eh um server de thread unic
import Central.Senha_Guiche_Painel;
import Guiche.Guiche;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



    //fazer função que retorna ultimo e penultimo para escrever nos ultimos chamados
    //função que le hora da maquina: get time using class date
    //arrumar interface

public class Painel implements Runnable {
    
    private static Socket s; //Create Socket
    private static ServerSocket ss;//Create a Server Socket
    private Senha_Guiche_Painel aux;
    String num;
    int guiche;
    
    @Override
    public void run() {
        try {
            ss = new ServerSocket(2900);//Start a new server socket on port 2900
            while(true){
                s = ss.accept();//Accept when a request arrives
                ObjectInputStream requestfromClient = new ObjectInputStream(s.getInputStream());
                aux = (Senha_Guiche_Painel) requestfromClient.readObject();
                
                
                this.aux=(Senha_Guiche_Painel) requestfromClient.readObject();
          
                
                guiche = aux.getGuiche();
                num = aux.getSenha();
                Sistema er = new Sistema();
            
               
                
            }
            
            
        } catch (IOException ex) {
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Painel.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
    
    
}
