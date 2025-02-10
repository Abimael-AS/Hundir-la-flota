import java.util.Random;
import java.util.Scanner;

public class JugarHundirLaFlota {
    private static int intentos;
    private static int barcosRestantes;
    private static int lanchas;
    private static int buques;
    private static int acorazados;
    private static int portaaviones;
    private static char[][] tablero;

    public static void mostrarReglas() {
        System.out.println("Bienvenido a Hundir la Flota!");
        System.out.println("Reglas del juego:");
        System.out.println("1. El tablero es de 10x10.");
        System.out.println("2. Hay diferentes tipos de barcos con diferentes tamaños.");
        System.out.println("3. Introduce las coordenadas para disparar (ej. A5).");
        System.out.println("4. Tienes un número limitado de intentos dependiendo de la dificultad.");
        System.out.println("5. Ganas si hundes todos los barcos antes de que se acaben los intentos.");
        System.out.println("Buena suerte!");
    }

    public static void seleccionarDificultad() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Selecciona la dificultad: 1 (Fácil), 2 (Medio), 3 (Difícil)");
        String dificultad = scanner.nextLine();

        switch (dificultad) {
            case "1":
                intentos = 50;
                barcosRestantes = 10;
                lanchas = 5;
                buques = 3;
                acorazados = 1;
                portaaviones = 1;
                break;
            case "2":
                intentos = 30;
                barcosRestantes = 5;
                lanchas = 2;
                buques = 1;
                acorazados = 1;
                portaaviones = 1;
                break;
            case "3":
                intentos = 10;
                barcosRestantes = 2;
                lanchas = 1;
                buques = 1;
                acorazados = 0;
                portaaviones = 0;
                break;
            default:
                System.out.println("Dificultad no válida. Seleccionando dificultad fácil por defecto.");
                intentos = 50;
                barcosRestantes = 10;
                lanchas = 5;
                buques = 3;
                acorazados = 1;
                portaaviones = 1;
                break;
        }
        System.out.println("Dificultad seleccionada: " + dificultad);
    }

    public static char[][] tableroBarcos() {
        tablero = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tablero[i][j] = '-';
            }
        }
        return tablero;
    }

    public static void colocarBarcoAleatorio(char caracter, int cantidad, int longitud, boolean vertical) {
        Random barcosRandom = new Random();
        int contar = 0;
        while (contar < cantidad) {
            int fila = barcosRandom.nextInt(10);
            int columna = barcosRandom.nextInt(10);
            boolean espacioDisponible = true;

            // Verificar si hay espacio disponible para colocar el barco
            if (vertical) {
                if (fila + longitud <= 10) {
                    for (int i = 0; i < longitud; i++) {
                        if (tablero[fila + i][columna] != '-') {
                            espacioDisponible = false;
                            break;
                        }
                    }
                    if (espacioDisponible) {
                        for (int i = 0; i < longitud; i++) {
                            tablero[fila + i][columna] = caracter;
                        }
                        contar++;
                    }
                }
            } else {
                if (columna + longitud <= 10) {
                    for (int i = 0; i < longitud; i++) {
                        if (tablero[fila][columna + i] != '-') {
                            espacioDisponible = false;
                            break;
                        }
                    }
                    if (espacioDisponible) {
                        for (int i = 0; i < longitud; i++) {
                            tablero[fila][columna + i] = caracter;
                        }
                        contar++;
                    }
                }
            }
        }
    }

    public static void posicionBarcos() {
        colocarBarcoAleatorio('L', lanchas, 1, false);
        colocarBarcoAleatorio('B', buques, 3, false); // Longitud de 3 caracteres
        colocarBarcoAleatorio('Z', acorazados, 4, false); // Longitud de 4 caracteres
        colocarBarcoAleatorio('P', portaaviones, 5, true); // Longitud de 5 caracteres, solo en vertical
    }

    public static void imprimirTablero() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++){
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void imprimirTableroJugador() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero[i][j] == 'A' || tablero[i][j] == 'X') {
                    System.out.print(tablero[i][j] + " ");
                } else {
                    System.out.print("- ");
                }
            }
        }
    }

    public static void imprimirTablerosLadoALado() {
        // System.out.println("Tablero de Barcos\t\tTablero del Jugador");
        // for (int i = 0; i < 10; i++) {
        //     for (int j = 0; j < 10; j++) {
        //         System.out.print(tablero[i][j] + " ");
        //     }
        //     System.out.print("\t\t");
        //     for (int j = 0; j < 10; j++) {
        //         if (tablero[i][j] == 'A' || tablero[i][j] == 'X') {
        //             System.out.print(tablero[i][j] + " ");
        //         } else {
        //             System.out.print("- ");
        //         }
        //     }
        //     System.out.println();
        // }
        System.out.println("Tablero del Jugador");
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero[i][j] == 'A' || tablero[i][j] == 'X') {
                    System.out.print(tablero[i][j] + " ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println();
        }
    }

    public static void tableroDisparado(){
        Scanner scanner = new Scanner(System.in);
        while (intentos > 0 && barcosRestantes > 0) {
            System.out.println("Introduce las coordenadas para disparar");
            String disparo = scanner.nextLine().toUpperCase(); // Convertir a mayúsculas

            if (disparo.length() < 2 || disparo.length() > 3 || !Character.isLetter(disparo.charAt(0)) || !Character.isDigit(disparo.charAt(1))) {
                System.out.println("Coordenadas no válidas. Inténtalo de nuevo.");
                continue; // Volver a pedir el disparo
            }

            char fila = disparo.charAt(0);
            int columna;
            if (disparo.length() == 3) {
                columna = 9; // Si la longitud es 3, entonces es la columna 10 (índice 9)
            } else {
                columna = Integer.parseInt(disparo.substring(1)) - 1; // Convertir a índice 0
            }
            int filaInt = fila - 'A';
            if (filaInt < 0 || filaInt >= 10 || columna < 0 || columna >= 10) {
                System.out.println("Coordenadas fuera de rango. Inténtalo de nuevo.");
                continue; // Volver a pedir el disparo
            }

            if (tablero[filaInt][columna] == '-') {
                System.out.println("Agua!");
                tablero[filaInt][columna] = 'A';
                intentos--;
            } else if (tablero[filaInt][columna] == 'X' || tablero[filaInt][columna] == 'A') {
                System.out.println("Ya has disparado en esta posición. Inténtalo de nuevo.");
                continue; // Volver a pedir el disparo
            } else {
                System.out.println("Barco alcanzado!");
                tablero[filaInt][columna] = 'X';
                barcosRestantes--;
                intentos--;
            }
            imprimirTablerosLadoALado(); // Imprimir ambos tableros después de cada disparo
        }

        if (barcosRestantes == 0) {
            System.out.println("CONGRATULATIONS. Has hundido todos los barcos.");
        } else if (intentos == 0) {
            System.out.println("GAME OVER. Lo sentimos no conseguiste destruir todos los barcos.");
        }

        // Mostrar el mapa completo al final del juego
        System.out.println("Mapa completo:");
        imprimirTablero();
    }

    public static void main(String[] args) {
        mostrarReglas();
        seleccionarDificultad();
        tableroBarcos();
        posicionBarcos();
        imprimirTablero(); // Comentar esta línea para no imprimir el mapa
        tableroDisparado();
    }
}