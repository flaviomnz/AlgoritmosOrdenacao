import java.io.*;
import java.util.*;

public class AlgoritmosDeOrdenacao {

    public static int[] lerDoArquivo(String nomeArquivo) throws IOException {
        BufferedReader leitor = new BufferedReader(new FileReader(nomeArquivo));
        List<Integer> numeros = new ArrayList<>();
        String linha;
        while ((linha = leitor.readLine()) != null) {
            numeros.add(Integer.parseInt(linha.trim()));
        }
        leitor.close();
        return numeros.stream().mapToInt(i -> i).toArray();
    }

    public static void escreverParaArquivo(String nomeArquivo, int[] numeros) throws IOException {
        BufferedWriter escritor = new BufferedWriter(new FileWriter(nomeArquivo));
        for (int num : numeros) {
            escritor.write(num + "\n");
        }
        escritor.close();
    }

    public static void medirEOrdenar(int[] numeros, int escolhaAlgoritmo, String nomeArquivoSaida) throws IOException {
        long inicio = System.nanoTime();

        switch (escolhaAlgoritmo) {
            case 1:
                bubble(numeros);
                System.out.println("Bubble Sort: ");
                break;
            case 2:
                insert(numeros);
                System.out.println("Insertion Sort: ");
                break;
            case 3:
                selection(numeros);
                System.out.println("Selection Sort: ");
                break;
            case 4:
                merge(numeros, 0, numeros.length - 1);
                System.out.println("Merge Sort: ");
                break;
            case 5:
                quick(numeros, 0, numeros.length - 1);
                System.out.println("Quick Sort: ");
                break;
            case 6:
                heap(numeros);
                System.out.println("Heap Sort: ");
                break;
            default:
                System.out.println("Escolha inválida. Execute o programa novamente.");
                return;
        }

        long fim = System.nanoTime();
        long duracao = (fim - inicio) / 1000000; // Convertendo para milissegundos
        System.out.println("O algoritmo levou " + duracao + " ms.");

        // Escrever o resultado ordenado no arquivo de saída
        escreverParaArquivo(nomeArquivoSaida, numeros);
    }

    //Bubble Sort
    public static void bubble(int[] array) {
        int n = array.length;
        boolean trocado;
        for (int i = 0; i < n - 1; i++) {
            trocado = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    trocado = true;
                }
            }
            if (!trocado) break;
        }
    }

    //Insertion Sort
    public static void insert(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; ++i) {
            int chave = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > chave) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = chave;
        }
    }

    //Selection Sort
    public static void selection(int[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }
            int temp = array[minIdx];
            array[minIdx] = array[i];
            array[i] = temp;
        }
    }

    //Merge Sort
    public static void merge(int[] array, int esquerda, int direita) {
        if (esquerda < direita) {
            int meio = (esquerda + direita) / 2;
            merge(array, esquerda, meio);
            merge(array, meio + 1, direita);
            fundir(array, esquerda, meio, direita);
        }
    }

    public static void fundir(int[] array, int esquerda, int meio, int direita) {
        int n1 = meio - esquerda + 1;
        int n2 = direita - meio;

        int[] esquerdaArray = new int[n1];
        int[] direitaArray = new int[n2];

        System.arraycopy(array, esquerda, esquerdaArray, 0, n1);
        System.arraycopy(array, meio + 1, direitaArray, 0, n2);

        int i = 0, j = 0;
        int k = esquerda;
        while (i < n1 && j < n2) {
            if (esquerdaArray[i] <= direitaArray[j]) {
                array[k] = esquerdaArray[i];
                i++;
            } else {
                array[k] = direitaArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = esquerdaArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = direitaArray[j];
            j++;
            k++;
        }
    }
}