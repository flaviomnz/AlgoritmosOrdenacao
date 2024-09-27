import java.io.*;
import java.util.*;

public class GeradorDeDados {

    // Gerar números em ordem crescente
    public static int[] gerarCrescente(int tamanho, boolean distinto) {
        int[] numeros = new int[tamanho];
        for (int i = 0; i < tamanho; i++) {
            numeros[i] = distinto ? i + 1 : (int) (Math.random() * tamanho);
        }
        Arrays.sort(numeros);  // Ordenar para garantir que estão em ordem crescente
        return numeros;
    }

    // Gerar números em ordem decrescente
    public static int[] gerarDecrescente(int tamanho, boolean distinto) {
        int[] numeros = gerarCrescente(tamanho, distinto);
        for (int i = 0; i < tamanho / 2; i++) {
            int temp = numeros[i];
            numeros[i] = numeros[tamanho - i - 1];
            numeros[tamanho - i - 1] = temp;
        }
        return numeros;
    }

    // Gerar números aleatórios
    public static int[] gerarAleatorio(int tamanho, boolean distinto) {
        int[] numeros = new int[tamanho];
        if (distinto) {
            List<Integer> lista = new ArrayList<>();
            for (int i = 1; i <= tamanho; i++) {
                lista.add(i);
            }
            Collections.shuffle(lista);
            for (int i = 0; i < tamanho; i++) {
                numeros[i] = lista.get(i);
            }
        } else {
            for (int i = 0; i < tamanho; i++) {
                numeros[i] = (int) (Math.random() * tamanho);
            }
        }
        return numeros;
    }

    // Salvar os números em um arquivo
    public static void salvarParaArquivo(String nomeArquivo, int[] numeros) throws IOException {
        BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo));
        for (int num : numeros) {
            escritor.write(num + "\n");
        }
        escritor.close();
    }

    public static void main(String[] args) throws IOException {
        int[] tamanhos = {100000, 160000, 220000, 280000, 340000, 400000, 460000, 520000, 580000, 640000, 700000};
        boolean[] valoresDistintos = {true, false}; // true para distinto, false para repetido

        for (int tamanho : tamanhos) {
            for (boolean distinto : valoresDistintos) {
                String repeticao = distinto ? "SemRepeticao" : "ComRepeticao";
                
                // Gerar e salvar arquivos para os diferentes cenários
                int[] crescente = gerarCrescente(tamanho, !distinto);
                salvarParaArquivo(tamanho + "_Crescente" + repeticao + ".txt", crescente);

                int[] decrescente = gerarDecrescente(tamanho, !distinto);
                salvarParaArquivo(tamanho + "_Decrescente" + repeticao + ".txt", decrescente);

                int[] aleatorio = gerarAleatorio(tamanho, !distinto);
                salvarParaArquivo(tamanho + "_Aleatorio" + repeticao + ".txt", aleatorio);
            }
        }
        System.out.println("Conjuntos de dados gerados com sucesso!");
    }
}