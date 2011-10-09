import java.util.Comparator;


public class comparadorNodo implements Comparator<Nodo> {
	
	
	public int compare(Nodo x, Nodo y) 
    { 
        // Assume neither string is null. Real code should 
        // probably be more robust 
        if (x.getCosto() < y.getCosto()) 
        { 
            return -1; 
        } 
        if (x.getCosto() > y.getCosto()) 
        { 
            return 1; 
        } 
        return 0; 
    } 

}
