/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binary.tree.problem;

/**
 *
 * @author Santi Mercado
 */
public class BinaryTreeProblem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Tree tree = new Tree(); //We create a new binary tree
        for (int i =0; i<= 9; i++){  //We generate 10 random numbers and add them to the tree.
            boolean reroll = false;
            int num = (int) Math.floor(Math.random()*(9999-0+1)+0);
            if (tree.SearchNode(tree.root, num) != null){ //We check if for some reason the random number exists in the tree already and change it.
                reroll = true; //I know the subroutine checks it in the tree class before adding the node, but I do it here so that I can create a new value in that case, and always end up with 10 nodes.
            }
            while (reroll == true){
             num = (int) Math.floor(Math.random()*(99999-0+1)+0);
             if (tree.SearchNode(tree.root, num) == null){
                 reroll = false;
             }
            }
            
            tree.AddNode(num);
        }
        System.out.println("El árbol 1 queda de la siguiente forma: ");
        tree.NormalTraverse(tree.root, 0);
        System.out.println(" ");
        System.out.println("El número más grande del árbol es " + tree.MaxVal(tree.root));
        System.out.println("El número más pequeño del árbol es " + tree.MinVal(tree.root));
        int left = tree.Sum(tree.root.left);
        int right = tree.Sum(tree.root.right);
        System.out.println("La suma de los valores del subarbol izquierdo es " + left);
        System.out.println("La suma de los valores del subarbol derecho es " + right);
        Tree pitree = tree.PseudoInvertTree(tree.root);
        if (tree.SearchNode(tree.root, left) == null){ //We check if the sum of the left subtree is a number already in the right subtree. If it's not, we add it to the inverted tree. (It's quite the stretch but never underestimate bad luck XD).
          pitree.AddNodeI(left); 
        }
        pitree.AddNodeI(right); 
        System.out.println(" ");
        System.out.println("El árbol 2 queda de la siguiente forma: ");
        pitree.NormalTraverse(pitree.root, 0);
        Tree itree = tree.InvertTree(pitree);
        System.out.println(" ");
        System.out.println("El árbol 3 queda de la siguiente forma: ");
        itree.NormalTraverse(itree.root, 0);
    }
    
}
