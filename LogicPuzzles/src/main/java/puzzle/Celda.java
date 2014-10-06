package puzzle;

public class Celda {
	
	//------------------------------------------------------------------------------
	//Constantes
	//------------------------------------------------------------------------------
	
	public final static int CELDA_NULA = 0;
	
	public final static int CELDA_ESCOGIDA = 1;
	
	public final static int CELDA_ELIMINADA = 2;
	
	//------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------
	
	private int fila;
	
	private int columna;
	
	private int estado;
	
	private ValorDimension valDim;

	//------------------------------------------------------------------------------
	//Constructor
	//------------------------------------------------------------------------------
	
	public Celda(int fil, int col, ValorDimension vd)
	{
		fila = fil;
		columna = col;
		valDim = vd;
		
		estado = CELDA_NULA;
	}
	
	//------------------------------------------------------------------------------
	//Constructor
	//------------------------------------------------------------------------------
	
	public void setEstado(int est)
	{
		estado = est;
	}
	
	public int getEstado()
	{
		return estado;
	}
	
	public int getColumna()
	{
		return columna;
	}
	
	public int getFila()
	{
		return fila;
	}
	
	public ValorDimension getValorDim()
	{
		return valDim;
	}
}
