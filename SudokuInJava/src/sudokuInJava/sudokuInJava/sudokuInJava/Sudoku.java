package proyectosegundoparcial_gtzzapien.zapata.verduzco;

/**
 * @author Mariel S. Gtz. Zapien, Mariana Zapata Covarrubias & Mauricio Verduzco Chavira
 */

public class Sudoku {
    private static int [][] matrix;                                 //Matriz que representa nuestro tablero de sudoku
    private static SetADT <Integer> arrayRows[] = new SetA[9];      //Arreglo de conjuntos que representan los renglones
    private static SetADT <Integer> arrayCols[] = new SetA[9];      //Arreglo de conjuntos que representan las columnas
    private static SetADT <Integer> arraySubMatrix[] = new SetA[9]; //Arreglo de conjuntos que represantan las regiones

    /*
    Función principal que recibe un sudoku (matriz) sin resolver.
    Primero guarda los datos de una matriz en los conjuntos (y evalúa su veracidad).
    Después de eso hace la llamada a la función recursiva y regresa el sudoku (matriz) resuelto.
    */
    public static int [][] funcionEjecutable(int [][] matrizEntrada){
        int matrizSalida[][]=new int[9][9];             //Matriz salida
        int numero;                                     //Auxiliar
        
        for(int i = 0; i < 9; i++) {                    //Instanciamos todos nuestros conjuntos
            arrayRows[i] = new SetA<>();
            arrayCols[i] = new SetA<>();
            arraySubMatrix[i] = new SetA<>();
        }
        
        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++) {
                numero = matrizEntrada[row][column];    // Guardaremos en nuestro auxiliar el valor (i,j) de la matriz
                
                if(numero != 0) {                       // Si el número es distinto a cero
                    if(!evaluaPosibilidad(row, column, numero)){ //Si el número rompe las reglas del sudoku, regresaremos la excepción
                        throw new ErroneousDataException("Los números ingresados no cumplen con las reglas de Sudoku");
                    
                    }else{                              // Si el número no rompe las reglas del sudoku
                    arrayRows[row].add(numero);         // Agregamos el número al conjunto renglón correspondiente
                    arrayCols[column].add(numero);      // Agregamos el número al conjunto columna correspondiente
                    arraySubMatrix[defineRegion(row, column)].add(numero);  // Agregamos el número al conjunto región correspondiente
                    }
                }
                
                matrizSalida[row][column]=numero;       //Metemos en la matriz de salida los números
            }  
        }
        
        if  (algortimoPrincipal(matrizSalida)){ //Llamada a Función Recursiva
            return matrizSalida;                // Si nuestro algoritmo funciona, regresaremos el sudoku resuelto
        }else{
            throw new ErroneousDataException("Sudoku sin solución");    // Si no funciona, regresaremos la excepción
        } 
        //OJO: para este punto, las pruebas anteriores deberían funcionar de tal manera que no se intentaría aplicar 
        //el algoritmo a un sudoku que no tenga solución, pero por temas de robustés decidimos agregar esta línea de código
    }

    //Esta función recursiva es la que resuelve el sudoku, recibe una matriz y regresa un valor booleano
    private static boolean algortimoPrincipal(int [][] matrixEntrada){
       
        for(int row=0;row <9;row++){
            for (int column=0;column<9;column++){
                
                if(matrixEntrada[row][column]==0){  //si el valor (i,j) es cero, significa que es una casilla vacía.
                    
                    for (int numero=1;numero<=9;numero++){ //intentaremos los números del 1 al 9
                        
                        if(evaluaPosibilidad(row,column,numero)){   //Se evaluará la posibilidad de ese número
                            matrixEntrada[row][column]=numero;      //Si el número funciona, se agrega a la matriz
                            arrayRows[row].add(numero);             // Agregamos el número al conjunto renglón correspondiente
                            arrayCols[column].add(numero);          // Agregamos el número al conjunto columna correspondiente
                            arraySubMatrix[defineRegion(row, column)].add(numero);  // Agregamos el número al conjunto región correspondiente
                            
                            if(algortimoPrincipal(matrixEntrada)){ //Llamada recursiva con la nueva matriz
                                return true;                       // Si se completa el algoritmo concluye regresaremos true
                                
                            }else{  //Si el algoritmo no pudo concluir
                                matrixEntrada[row][column]=0;       //Borraremos el último número de la matriz
                                arrayRows[row].remove(numero);      //Borraremos del conjunto renglón
                                arrayCols[column].remove(numero);//Borraremos del conjunto columna
                                arraySubMatrix[defineRegion(row, column)].remove(numero);   //Borraremos del conjunto región
                            }
                        }
                    } // Se regresará falso hasta que todos los espacios del sudoku tengan un número
                    return false;
                }
            }
        }
        return true; //Entonces solo con la matriz completa, el algoritmo concluso 
        //y las condiciones de los conjuntos podremos regresar true.
    }
    
    //La función booleana evalua posibilidad recibe un número que quiere ser probado en (x,y) coordenadas.
    private static boolean evaluaPosibilidad(int row,int column, int element){
        return !(arrayRows[row].contains(element) || // Si no hay otro número igual es ese renglon
                 arrayCols[column].contains(element) ||     // Si no hay otro número igual es esa columna
                 arraySubMatrix[defineRegion(row, column)].contains(element));  // Si no hay otro número igual es ese renglon
    }
    // la función refine región secciona el tablero en 9 regiones y de acuerdo con una coordenada, regresa a qué región pertenece.
    private static int defineRegion(int row, int column) {
        return row/3 * 3 + column/3;
    }
  
}