package util.graph;

import util.graph.*;
import util.intset.*;

import java.awt.*;
import java.util.*;
import java.io.*;

public class ColorGraph {
    public  Graph          G;
    public  int            R;
    public  int            K;
    private Stack<Integer> pile;
    public  IntSet         removed;
    public  IntSet         spill;
    public  int[]          couleur;
    public  Node[]         int2Node;
    static  int            NOCOLOR = -1;

    public ColorGraph(Graph G, int K, int[] phi){
	this.G       = G;
	this.K       = K;
	pile         = new Stack<Integer>(); 
	R            = G.nodeCount();
	couleur      = new int[R];
	removed      = new IntSet(R);
	spill        = new IntSet(R);
	int2Node     = G.nodeArray();
	for(int v=0; v < R; v++){
	    int preColor = phi[v];
	    if(preColor >= 0 && preColor < K)
		couleur[v] = phi[v];
	    else
		couleur[v] = NOCOLOR;
	}
    }

    /*-------------------------------------------------------------------------------------------------------------*/
    /* associe une couleur à tous les sommets se trouvant dans la pile */
    /*-------------------------------------------------------------------------------------------------------------*/
    
    public void selection()
    {
        //TODO finir la fonction
        while(!pile.empty()){
            int s = pile.pop();
            IntSet c = couleursVoisins(s);
            if(c.getSize() != K) couleur[s] = choisisCouleur(//C -c);
        //couleur[s] = choisisCouleur(couleursVoisins(s)); pour remplacer le if ??
        }
    }
    
    /*-------------------------------------------------------------------------------------------------------------*/
    /* récupère les couleurs des voisins de t */
    /*-------------------------------------------------------------------------------------------------------------*/
    
    public IntSet couleursVoisins(int t)
    {
        IntSet neighbourColors = new IntSet(K);
        Node node = int2Node[t];
        NodeList neighbours = node.adj();
        // Parcours les voisins et ajoute dans neighbourColors les couleurs déjà utilisées par les voisins
        // du noeud dont l'indexe est passé en argument à la fonction
        for(NodeList q=neighbours; q!=null; q=q.tail) {
            // Si la couleur est déjà ajoutée au set : continue
            if(neighbourColors.isMember(couleur[q.head.mykey])){
                continue;
            } else{
                // Aussi non on rajoute la couleur utilisée par le noeud au set de couleur
                neighbourColors.add(couleur[q.head.mykey]);
            }

        }
        return neighbourColors;
    }
    
    /*-------------------------------------------------------------------------------------------------------------*/
    /* recherche une couleur absente de colorSet */
    /*-------------------------------------------------------------------------------------------------------------*/
    
    public int choisisCouleur(IntSet colorSet)
    {
        //choisit une couleur dans l’ensemble de couleurs ColorSet
        int size = colorSet.getSize();
        for (int i = 0; i < size; i++) {
            if(!colorSet.isMember(i)) return i;
        }
        //valeur par default cad NOCOLOR
        return -1;
    }
    
    /*-------------------------------------------------------------------------------------------------------------*/
    /* calcule le nombre de voisins du sommet t */
    /*-------------------------------------------------------------------------------------------------------------*/
    
    public int nbVoisins(int t)
    {
        Node node = int2Node[t];
        NodeList neighbours = node.adj();
        int count = 0;
        //Parcours les voisins du noeud passé en argument pour les dénombrer
        for(NodeList q=neighbours; q!=null; q=q.tail) {
            count++;
        }
        return count;
    }

    /*-------------------------------------------------------------------------------------------------------------*/
    /* simplifie le graphe d'interférence g                                                                        */
    /* la simplification consiste à enlever du graphe les temporaires qui ont moins de k voisins                   */
    /* et à les mettre dans une pile                                                                               */
    /* à la fin du processus, le graphe peut ne pas être vide, il s'agit des temporaires qui ont au moins k voisin */
    /*-------------------------------------------------------------------------------------------------------------*/

    public void simplification()
    {
        boolean modif = true;
        while (pile.size() != R && modif){
            modif = false;
            for (var node: int2Node) {
                if (nbVoisins(node.mykey) < K && couleur[node.mykey] == NOCOLOR) {
                   pile.push(node.mykey);
                   removed.add(node.mykey);
                   modif = true;
                }
            }
        }
    }
    
    /*-------------------------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------------------------*/
    
    public void debordement()
    {
        spill = new IntSet(R);
        while (pile.size() != R){
            int s = choisi_sommet();
            pile.push(s);
            removed.add(s);
            spill.add(s);
            simplification();
        }
    }

    private int choisi_sommet(){
        for (int i = 0; i < removed.getSize(); ++i)
            if (!removed.isMember(i) && couleur[i] == NOCOLOR) return i;

        return -1;
    }


    /*-------------------------------------------------------------------------------------------------------------*/
    /*-------------------------------------------------------------------------------------------------------------*/

    public void coloration()
    {
	this.simplification();
	this.debordement();
	this.selection();
    }

    void affiche()
    {
	System.out.println("vertex\tcolor");
	for(int i = 0; i < R; i++){
	    System.out.println(i + "\t" + couleur[i]);
	}
    }
    
    

}
