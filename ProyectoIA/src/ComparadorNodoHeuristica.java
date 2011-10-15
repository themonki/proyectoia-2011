import java.util.Comparator;

public class ComparadorNodoHeuristica implements Comparator<Nodo> {
	
	
	public int compare(Nodo x, Nodo y) 
    { 
        // Assume neither string is null. Real code should 
        // probably be more robust 
        if (x.getValHeuristica()< y.getValHeuristica()) 
        { 
            return -1; 
        } 
        if (x.getValHeuristica() > y.getValHeuristica()) 
        { 
            return 1; 
        } 
        return 0; 
    } 

}
