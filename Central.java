//central eh um server multithread
package Central;

import Toten.Pedido_Senha;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import Guiche.Guiche;
import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.net.ServerSocket;

public class Central implements Runnable {

    private static Socket socket;
    private static Socket crocket;
    private static ServerSocket ss;
    private final Queue<String> filaN = new LinkedList<>();
    private final Queue<String> filaP = new LinkedList<>();
    private static ArrayList<Guiche> guiches;//lista de guiches operando
    private final ArrayList<String> anteriores = new ArrayList<>();//Senhas que ja foram
    private static ArrayList<Integer> guiche_numbers;//lista de guiches operando
    private int send;
    private String senha; //o que irá receber da classe Pedido_Senha
    private static Pedido_Senha pedido;
    private static Senha_Guiche_Painel nova;

    public void gerenciar_fila() {
        String aux = senha;
        if (aux.contains("P")) {
            filaP.add(aux);
        } else {
            filaN.add(aux);
        }
    }

    public void gerar_relatorio() {
        //A fazer...
    }

    public void adiciona_guiche(int aux) {

        Guiche guiche = new Guiche(aux, null);
        //Atendente at1 = new Atendente();
        guiches.add(guiche);
        send = guiche.getNumero();
        guiche_numbers.add(send);
        System.out.println("O guichê foi cadastrado!");
        //guiche.getGuiches(guiches);
        //at1.send_list(guiche_numbers);

    }

    public void gera_lista() {

    }
    
    public void envia_painel(){
        try {
            Senha_Guiche_Painel resp;
            crocket = new Socket("localhost", 2900);
            ObjectOutputStream requestObjectToServer = new ObjectOutputStream(crocket.getOutputStream());
            requestObjectToServer.writeObject(nova);
            
            ObjectInputStream replyObjecFromServer = new ObjectInputStream(crocket.getInputStream());
            resp = (Senha_Guiche_Painel) replyObjecFromServer.readObject();
            
            crocket.close();
        } catch (IOException ex) {
            Logger.getLogger(Central.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Central.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void cadastra_guiche() {

        Scanner in = new Scanner(System.in);
        System.out.print("Entre com o numero do guiche que deseja cadastrar: ");
        String num = in.nextLine();
        try {
            int aux = Integer.valueOf(num);
            Guiche outra = new Guiche();
            if (guiches.isEmpty()) {
                adiciona_guiche(aux);
            } else if (guiche_numbers.contains(aux)) {
                System.out.println("Numero repetido, favor inserir outro.");
                cadastra_guiche();
            } else {
                adiciona_guiche(aux);
            }
        } catch (NumberFormatException e) {
            System.out.println("Cadastro de guichês apenas com números! Por favor, entre com um guichê novamente.");
        }
    }

    @Override
    public void run() {

        System.out.println("tamanho da lista a ser enviada: " + guiche_numbers.size());
        /*Request request = (Request) requestfromClient.readObject();//Cast Object to Request 
         //Print some request information
         System.out.println("Client sent : " + request.getOperation());              
         */
        //ArrayList<Integer> aux = new ArrayList();
        try {
            ss = new ServerSocket(2800);//Start a new server socket on port 280
            socket = ss.accept();
            ObjectInputStream requestfromClient = new ObjectInputStream(socket.getInputStream());
            Object coisa = requestfromClient.readObject();

            if (coisa instanceof ArrayList) {
                System.out.print("é uma lista");
                ArrayList<Integer> aux = (ArrayList<Integer>) coisa;

                ObjectOutputStream replyObjectToClient = new ObjectOutputStream(socket.getOutputStream());
                replyObjectToClient.writeObject(guiche_numbers);
            } else if (coisa instanceof Pedido_Senha) {
                System.out.print("é um pedido de senha");
                chama_senha();

                ObjectOutputStream replyObjectToClient = new ObjectOutputStream(socket.getOutputStream());
                replyObjectToClient.writeObject(coisa);
                envia_painel();
            }

            //ObjectOutputStream requestObjectToServer = new ObjectOutputStream(socket.getOutputStream());//Create a Request Object Buffer
            // requestObjectToServer.writeObject(guiche_numbers); //Send Request to the server
            //Accept when a request arrives
            //ObjectOutputStream requestObjectToServer = new ObjectOutputStream(socket.getOutputStream());
            //requestObjectToServer.writeObject(guiche_numbers);
            //  ObjectOutputStream replyObjectToClient = new ObjectOutputStream(socket.getOutputStream());
            //  replyObjectToClient.writeObject(guiche_numbers);
            socket.close();

        } catch (IOException ex) {
            Logger.getLogger(Central.class.getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Central.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    /*    @Override
//    public void run() {
//        System.out.println("tamanho da lista a ser enviada: " + guiche_numbers.size());
//        /*Request request = (Request) requestfromClient.readObject();//Cast Object to Request 
//         //Print some request information
//         System.out.println("Client sent : " + request.getOperation());              
//         */
//        //ArrayList<Integer> aux = new ArrayList();
//        try {
//            ss = new ServerSocket(2800);//Start a new server socket on port 280
//            socket = ss.accept();
//            ObjectInputStream requestfromClient = new ObjectInputStream(socket.getInputStream());
//            ArrayList<Integer> aux = (ArrayList<Integer>) requestfromClient.readObject();
//
//            ObjectOutputStream replyObjectToClient = new ObjectOutputStream(socket.getOutputStream());
//
//            //Create Reply
//            replyObjectToClient.writeObject(guiche_numbers);//Send Reply back to Client
//
//            //ObjectOutputStream requestObjectToServer = new ObjectOutputStream(socket.getOutputStream());//Create a Request Object Buffer
//           // requestObjectToServer.writeObject(guiche_numbers); //Send Request to the server
//
//            //Accept when a request arrives
////            ObjectOutputStream requestObjectToServer = new ObjectOutputStream(socket.getOutputStream());
////            requestObjectToServer.writeObject(guiche_numbers);
//            //  ObjectOutputStream replyObjectToClient = new ObjectOutputStream(socket.getOutputStream());
//            //  replyObjectToClient.writeObject(guiche_numbers);
//            socket.close();
//
//        } catch (IOException ex) {
//            Logger.getLogger(Central.class.getName()).log(Level.SEVERE, null, ex);
//
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(Central.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }*/
//    
    public void showAllGuiches() {
        if (guiches.isEmpty()) {
            System.out.println("Não há nenhum guichê cadastrado!");
        } else {
            Guiche aux = new Guiche();
            for (int i = 0; i < guiches.size(); i++) {
                aux = guiches.get(i);
                System.out.print("Guichê: ");
                System.out.println(aux.getNumero() + " " + aux.getStatus());
            }
        }
    }

    public void atualiza_guiche() {
//        Receber obj;
//        varro a lista ate achar o obj de numero igual ao recebido;
//        obj que eu achei na lista e mudar o status;

    }

    public void reset() {
        guiches.clear();
        filaN.clear();
        filaP.clear();
        anteriores.clear();
    }

    public void chama_senha() {
        String prox_senha = pedido.getSenha();
        int cont = 0;
        for (int i = 0; !anteriores.isEmpty(); i++) {
            if (i == 2) {
                break;
            }
            String aux = anteriores.get(i);
            if (aux.contains("N")) {
                cont++;
            }
        }
        if ((cont == 2) && (!filaP.isEmpty())) {
            prox_senha = filaP.poll();
            anteriores.add(0, prox_senha);
        } else if (!filaN.isEmpty()) {
            prox_senha = filaN.poll();
            anteriores.add(0, prox_senha);
        }

        cont = 0;
        Guiche teta = new Guiche();

        for (int i = 0; i < guiches.size(); i++) {
            teta = guiches.get(i);
            if (teta.getStatus().equals("Livre")) {
                int aux = teta.getNumero();
                nova.setGuiche(aux);
                nova.setSenha(prox_senha);
                teta.changeStatus("Ocupado");
                break;
            }
            cont++;
            if (cont == guiches.size()) {
                i = 0; //fica no loop até surgir um guiche livre
            }
        }
    }

    public static void main(String[] args) {

        Thread central = new Thread((Runnable) new Central());
        guiches = new ArrayList<Guiche>();
        guiche_numbers = new ArrayList<Integer>();

        Central cent = new Central();
        System.out.println("=========================================");
        System.out.println("||Bem vindo a Central de Gerenciamento!||");
        System.out.println("||Escolha uma das opções do menu:      ||");
        System.out.println("||\"1\" Para cadastro de guichê          ||");
        System.out.println("||\"2\" Para visualizar todos os guichês ||");
        System.out.println("||\"3\" Para resetar o sistema           ||");
        System.out.println("||\"4\" Para chamar Thread              ||");
        System.out.println("||\"5\" Para sair                        ||");
        System.out.println("=========================================");

        while (true) {
            int menu;
            System.out.print("Opção: ");
            Scanner in = new Scanner(System.in);// Objeto  Leitor
            String opcao = in.nextLine();
            menu = Integer.valueOf(opcao);
            if (menu == 5) {
                System.out.println("Você saiu da Central.");
                break;
            }
            switch (menu) {
                case (1):
                    cent.cadastra_guiche();
                    break;
                case (2):
                    cent.showAllGuiches();
                    break;
                case (3):
                    cent.reset();
                    break;
//                case (4):
//                    nova = cent.chama_senha();
//                    //System.out.println("Senha escolhida " + pw);
//                    break;
                case (4):
                    central.start();
                    break;
                default:
                    System.out.println("Nenhuma opção válida.");
            }
        }

    }
}
