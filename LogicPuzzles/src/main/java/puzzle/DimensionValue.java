package puzzle;

public class DimensionValue {
	//------------------------------------------------------------------------------
	//Atributos
	//------------------------------------------------------------------------------
	private PuzzleDimension dimension;
	private int valorEntero;
	private String valorString;
	//------------------------------------------------------------------------------
	//Constructor
	//------------------------------------------------------------------------------
	public DimensionValue(PuzzleDimension d)
	{
	dimension = d;
	}
	//------------------------------------------------------------------------------
	//Metodos
	//------------------------------------------------------------------------------
	public void setValorEntero(int val)
	{
	valorEntero = val;
	}
	public int getValorEntero()
	{
	return valorEntero;
	}
	public void setValorString(String val)
	{
	valorString = val;
	}
	public String getValorString()
	{
	return valorString;
	}

}
