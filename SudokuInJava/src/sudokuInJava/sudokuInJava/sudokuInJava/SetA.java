package proyectosegundoparcial_gtzzapien.zapata.verduzco;

import java.util.Iterator;
import java.util.*;

/**
 * @author Mariel S. Gtz. Zapien, Mariana Zapata Covarrubias & Mauricio Verduzco Chavira
 */

public class SetA<T> implements SetADT<T> {
    private T[] Set;
    private int cardinalidad;
    private final int MAX=1000;
    
    public SetA(){
        Set=(T[])new Object[MAX];
        cardinalidad=0;
    } 
   
    public SetA(int max){
        max=MAX;
        Set=(T[])new Object[max];
        cardinalidad=0;
    } 
    
    public int getCardinalidad(){
        return cardinalidad;
    }
    
    public boolean isEmpty(){
        return cardinalidad==0;
    }

    public T remove(T dato){
        T result;
        int i=0;
        result=null;
        while(i<cardinalidad && !dato.equals(Set[i])){
            i++;
        }
        if(i<cardinalidad){//si no lo encontro
            result=Set[i];
            Set[i]=Set[cardinalidad-1];
            Set[cardinalidad-1]=null;
            cardinalidad--;
        }
        return result;
    }
    
    public Iterator<T> iterator(){
        return new IteradorArreglo(Set, cardinalidad);
    }
    
    private void expandCapacity(){
        T[] nuevo=(T[]) new Object[Set.length*2];
        for(int i=0;i<Set.length;i++)
            nuevo[i]=Set[i];
        Set=nuevo;
    }
    
    public void add(T element){
        if(!(contains(element))){
            
            if(cardinalidad==Set.length)
                expandCapacity();
            
            Set[cardinalidad] = element;
            cardinalidad++;
        }
    }
    
    public boolean contains(T dato){
        Iterator <T> it = this.iterator();
        boolean resp = false;
        
        while(it.hasNext() && !resp){
            resp=it.next().equals(dato);
        }
        return resp;
    }
    
    public SetADT<T> union(SetADT<T> otro){
        Iterator <T> itT = this.iterator(), itO = otro.iterator();
        SetADT<T> un = new SetA();
        T dato;
        
        while(itT.hasNext()){
            dato = itT.next();
            if(!otro.contains(dato)){
                un.add(dato);
            }
        }
        
        while(itO.hasNext()){
            un.add(itO.next());
   
        }
        return un; 
    }
 
    public SetADT<T> inter(SetADT<T> otro){
        Iterator <T> it = this.iterator();
        SetADT<T> inter = new SetA();
        T dato;
        
        while(it.hasNext()){
            dato = it.next();
            if(otro.contains(dato)){
                inter.add(dato);
            }
        }
        return inter;
    }

    public SetADT<T> diferencia(SetADT<T> otro){
        Iterator<T> it = this.iterator();
        SetADT<T> dif = new SetA();
        T dato;
        
        while(it.hasNext()){
            dato = it.next();
            if(!otro.contains(dato)){
                dif.add(dato);
            }
        }
        return dif;
    }
    
    public boolean equals(SetADT<T> otro){
        if(this.getCardinalidad() != otro.getCardinalidad()){   //EB
            return false;
        }else{
            return equals(otro.iterator());
        }
    }    
    
}