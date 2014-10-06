package puzzle;

import java.util.Arrays;

public class Dimension {
	
	//-------------------------------------------------------------------------------
	//Constantes
	//-------------------------------------------------------------------------------
	
	/**
	 * Tama�o del arreglo que llevar� los valores posibles de cada dimensi�n
	 */
	public final static int NUM_ATRS = 4;
	
	/**
	 * Para usar cuando el tipo de datos que maneja la dimensi�n son enteros
	 */
	public final static int DATOS_ENTEROS = 1;
	
	/**
	 * Para usar cuando el tipo de datos que maneja la dimensi�n son Strings
	 */
	public final static int DATOS_STRING = 2;
	
	//-------------------------------------------------------------------------------
	//Atributos
	//-------------------------------------------------------------------------------
	
	private String[] arregloStrings;
	
	private int[] arregloEnteros;
	
	private int tipoDatoArreglo;
	
	//-------------------------------------------------------------------------------
	//Constructor
	//-------------------------------------------------------------------------------
	
	public Dimension(int tipoDatos)
	{
		tipoDatoArreglo = tipoDatos;
		
		if(tipoDatos == DATOS_ENTEROS)
		{
			arregloEnteros = new int[NUM_ATRS];
		}
		else if(tipoDatos == DATOS_STRING)
		{
			arregloStrings = new String[NUM_ATRS];
		}
	}
	
	//-------------------------------------------------------------------------------
	//Metodos
	//-------------------------------------------------------------------------------
	
	/**
	 * Metodo para poblar el arreglo de enteros con los valores correspondientes
	 */
	public void poblarArregloEnteros(int[] datos)
	{
		for(int i=0; i<NUM_ATRS; i++)
		{
			arregloEnteros[i] = datos[i];
		}
	}
	
	/**
	 * Metodo para poblar el arreglo de enteros con los valores correspondientes
	 */
	public void poblarArregloStrings(String[] datos)
	{
		for(int i=0; i<NUM_ATRS; i++)
		{
			arregloStrings[i] = datos[i];
		}
	}
	
	/**
	 * Metodo para ordenar ascendentemente el arreglo de enteros
	 */
	public void ordenarArrayEnteros()
	{
		Arrays.sort(arregloEnteros);
	}
	
	/**
	 * Metodo para ordenar ascendentemente el arreglo de strings
	 */
	public void ordenarArrayString()
	{
		Arrays.sort(arregloStrings);
	}
}
