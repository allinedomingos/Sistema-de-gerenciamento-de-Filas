/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Toten;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class Pedido_Senha implements Serializable{

    private String senha = "1P";
    private String departamento;
    private boolean confirma_envio = false;
    private ArrayList<String> controle_senhas = new ArrayList<>();

    public boolean isConfirma_envio() {
        return confirma_envio;
    }

    public Pedido_Senha() {
        //inicializa();
    }

    private void inicializa() {
        String caminho = System.getProperty("user.home");
        caminho = caminho + "\\PROJETO";
        Path path = Paths.get(caminho);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException ex) {
                //Logger.getLogger(Pedido_Senha.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("ERRO NA CRIACAO DO DIRETORIO");
            }
        }
        try {
            PrintWriter writer = new PrintWriter(caminho + "\\senhas.txt");
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pedido_Senha.class.getName()).log(Level.SEVERE, null, ex);
        }
        //atualiza_senhas();
    }

    private void atualiza_senhas() {
        try {
            String caminho = System.getProperty("user.home");
            caminho = caminho + "\\PROJETO";
            Path path = Paths.get(caminho);
            if (!Files.exists(path)) {
                inicializa();
            } else {
                FileReader le = new FileReader(caminho + "\\senhas.txt");
                BufferedReader buf = new BufferedReader(le);

                controle_senhas.clear();
                String aux = buf.readLine();
                controle_senhas.add(aux);

                while (aux != null) {
                    aux = buf.readLine();
                    controle_senhas.add(aux);
                }
                buf.close();
                le.close();
            }
        } catch (IOException e) {
            System.out.println("Arquivo nao encontrado");
        }
    }

    private void cadastra_senha() {
        try {
            String caminho = System.getProperty("user.home");
            caminho = caminho + "\\PROJETO";
            FileWriter escreve = new FileWriter(caminho + "\\senhas.txt", true);
            BufferedWriter buf = new BufferedWriter(escreve);

            buf.write(this.senha);
            buf.newLine();

            buf.close();
            escreve.close();

        } catch (IOException e) {
            System.out.println("Arquivo nao encontrado");
            e.printStackTrace();
        }
    }

    //gera sempre um numero acima do da ultima senha
    private int verifica_num_preferencial() {
        int cont = 0;
        String aux;
        if (!controle_senhas.isEmpty()) {
            for (int i = 0; i < controle_senhas.size() - 1; i++) {
                aux = controle_senhas.get(i);
                if (aux.contains("P")) {
                    cont++;
                }
            }
            cont++;
            return cont;
        } else {
            cont = 1;
            return cont;
        }
    }

    public String getSenha() {
        return senha;
    }

    public String getDepartamento() {
        return departamento;
    }

    //gera sempre um numero acima do da ultima senha
    private int verifica_num_normal() {
        int cont = 0;
        String aux;
        if (!controle_senhas.isEmpty()) {
            for (int i = 0; i < controle_senhas.size() - 1; i++) {
                aux = controle_senhas.get(i);
                if (aux.contains("N")) {
                    cont++;
                }
            }
            cont++;
            return cont;
        } else {
            cont = 1;
            return cont;
        }
    }

    public void pedir_senha(String uma_senha) {
        atualiza_senhas();
        if ("Preferencial".equals(uma_senha)) {
            int i = verifica_num_preferencial();
            this.senha = Integer.toString(i) + "P";
        } else {
            int i = verifica_num_normal();
            this.senha = Integer.toString(i) + "N";
        }
        controle_senhas.add(this.senha);

        cadastra_senha();
    }

    public void setDpto(String um_departamento) {
        this.departamento = um_departamento;
    }

}