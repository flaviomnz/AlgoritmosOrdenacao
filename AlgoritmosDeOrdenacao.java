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
    // Algoritmo Quick Sort
    public static void rapido(int[] array, int baixo, int alto) {
        if (baixo < alto) {
            int pi = particao(array, baixo, alto);
            rapido(array, baixo, pi - 1);
            rapido(array, pi + 1, alto);
        }
    }

    public static int particao(int[] array, int baixo, int alto) {
        int pivo = array[alto];
        int i = (baixo - 1);
        for (int j = baixo; j < alto; j++) {
            if (array[j] <= pivo) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        int temp = array[i + 1];
        array[i + 1] = array[alto];
        array[alto] = temp;

        return i + 1;
    }

    // Algoritmo Heap Sort
    public static void heap(int[] array) {
        int n = array.length;

        for (int i = n / 2 - 1; i >= 0; i--)
            heapificar(array, n, i);

        for (int i = n - 1; i > 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapificar(array, i, 0);
        }
    }

    public static void heapificar(int[] array, int n, int i) {
        int maior = i;
        int esquerda = 2 * i + 1;
        int direita = 2 * i + 2;

        if (esquerda < n && array[esquerda] > array[maior])
            maior = esquerda;

        if (direita < n && array[direita] > array[maior])
            maior = direita;

        if (maior != i) {
            int temp = array[i];
            array[i] = array[maior];
            array[maior] = temp;
            heapificar(array, n, maior);
        }
    }

    // Método principal
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            // Solicitar o nome do arquivo de entrada
            System.out.print("Digite o nome do arquivo de entrada: ");
            String nomeArquivoEntrada = scanner.nextLine();

            // Gerar o nome do arquivo de saída automaticamente
            String nomeArquivoSaida = "output_" + nomeArquivoEntrada;
            System.out.println("O arquivo de saída será: " + nomeArquivoSaida);

            // Mostrar as opções de algoritmos
            System.out.println("Escolha o algoritmo:");
            System.out.println("1 - Bubble Sort");
            System.out.println("2 - Insertion Sort");
            System.out.println("3 - Selection Sort");
            System.out.println("4 - Merge Sort");
            System.out.println("5 - Quick Sort");
            System.out.println("6 - Heap Sort");

            // Ler a escolha do usuário
            System.out.print("Digite o número correspondente ao algoritmo escolhido: ");
            int escolhaAlgoritmo = scanner.nextInt();

            // Ler o arquivo de entrada
            int[] numeros = lerDoArquivo(nomeArquivoEntrada);

            // Ordenar os números e medir o tempo de execução
            medirEOrdenar(numeros, escolhaAlgoritmo, nomeArquivoSaida);

        } catch (IOException e) {
            System.out.println("Erro ao processar o arquivo: " + e.getMessage());
        }

        scanner.close();
    }
}