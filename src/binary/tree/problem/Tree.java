/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package binary.tree.problem;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Santi Mercado
 */
public class Tree {
     Node root;
     
      private void AddNode2(Node n, int data){ //To add a node we start traversing the tree according to the value given. 
            boolean c = true;
            while(c == true){
            if (data == n.value){ //If the value already exist, a new node is not created.
                c = false;
            } else {
            if (data < n.value){ //We take advantage of the properties of a search tree to minimize the iterations required, we look at the current value and if its lower than the given value, we go to the right, else we go left.
                if (n.left == null){ //Once we find the spot where the value should go, we create a new node with the value given, link it to the tree and end the iterations.
                   n.left = new Node(data); 
                   c = false;
                } else {
                    n = n.left;
                }
            } else {
                if (n.right == null){
                   n.right = new Node(data); 
                   c = false;
                } else {
                    n = n.right;
                }
        }
        }
            }
    
    }
    
    public void AddNode(int data){ //We create a secondary subroutine for easier balancing of the tree
        if (this.root == null){ //If there are no nodes, we create the new node with the given value and assign it as the root.
            this.root = new Node(data); 
        } else {
        this.AddNode2(root, data); //We use the secondary subroutine explained above 
        boolean keep = this.RebalanceNeeded(root); //We check if the tree is unbalanced after adding the node and after each rebalance, and keep rebalancing it until the tree is fully balanced.
        while (keep == true){
          this.RebalancingTraverse(root, data); 
          keep = this.RebalanceNeeded(root);
        }
        
        }
    }
    
     public void NormalTraverse (Node n, int level){ 
        if (n == null){ //We check if the tree is empty
            System.out.println("El árbol está vacío");
        } else {
            if (level > this.Height(this.root)){ //We stop once we print all levels in the tree.
            return;
        } else {
          System.out.println("LEVEL: " + level); //We print by levels using a secondary subroutine.
          this.RecursiveTraverse(root, level);   
        }
        }
        
        NormalTraverse(n, level + 1); //We call the subroutine again (recursion!) to print the next level
    }
    
    private void RecursiveTraverse(Node n, int l){ //A simple level traverse search but with the use of recursion (kinda). 
        if (n == null){ //We check if the node exists
            return;
        } else { //This subroutine only prints the nodes in a given level.
            if (l == 0){ //It only prints the nodes in the given level
                System.out.println("     Valor: " + n.value);
            } else if(l > 0){ //The l, acts as a counter variable, every time we move to the child, we dimish it becasue we diminish the distance in levels between the one we're in and the one we're looking for.
                RecursiveTraverse(n.left, l-1); //Calls itself (see, recursion!) but with the childs to look for all the nodes with the given level in the tree.
                RecursiveTraverse(n.right, l-1);
            }
        }
    }
    
    private Node rotateR(Node x){ //A simple rotation from right to left.
        Node child = x.right;
        x.right = child.left;
        child.left = x;
        return child;
    }
    
    private Node rotateL(Node x){ //A simple rotation from left to right.
        Node child = x.left;
        x.left = child.right;
        child.right = x;
        return child;
    }
    
    private int Height(Node n){ //We look for the given nodes height. If the root is used as argument, we get the tree's height.
        if (n == null){
             return -1; //If the tree is empty, we return -1.
        } else {
            int l = Height(n.left); //We calculate the heigth of the right and left subtrees, and return the highest value of the 2, plus 1.
            int r = Height(n.right);
            if (l > r){
                return l+1; //This way, we offset the -1 into a 0 if the node has no childs. 
            } else {
               return r+1; 
            }
        }
    }
    
    public int GetHeight(Node n){ //a Getter to be used in the JFrame.
        return Height(n);
    }
    
    private int BFactor(Node n){
        return Height(n.right)-Height(n.left); //We look for the balance factor by comparing the right and left subtrees' heights.
    }
    
    private boolean RebalanceNeeded(Node n){ //A simple level traverse in which we look if the tree needs rebalancing using the balance factors (if a single node requires rebalancing then the tree requires rebalancing too).
         Queue<Node> queue = new LinkedList<Node>();
        queue.add(n);
        while (queue.isEmpty()==false){
            Node temp = queue.poll();
            if (this.BFactor(temp) > 1 || this.BFactor(temp) < -1){ //We check the balance factors and return if the tree needs balancing or not.
                return true;
            }
            if (temp.right != null){ //We take don't take advantage of the properties of the search tree to check only the changed subtree bc that way we'll be able to use this in the inverted tree too.
                queue.add(temp.right);
            }
            if (temp.left != null){
                queue.add(temp.left);
            }
        }
        return false;
    }
    
    private void RebalancingTraverse(Node n, int data){ //In order to rebalance from bottom to top, we make use of the level traverse explained further ahead.
        for (int level=this.Height(this.root); level >= 1; level--){ 
            this.RecursiveTraverseR(root, level, data);          
        }
        this.root = this.Rebalance(this.root);
        
    }
    
     private void RecursiveTraverseR(Node n, int l, int data){ //A simple level traverse but with the use of recursion (kinda). 
        if (n == null){ //We check if the node exists
            return; 
        } else { 
                if (n.value > data){ //We take advantages of the properties of a search tree.
                    if (l-1 == 0){ //The l, acts as a counter variable, every time we move to the child, we dimish it becasue we diminish the distance in levels between the one we're in and the one we're looking for.
                        if (n.left != null){
                            n.left = this.Rebalance(n.left);
                        }
                    } else { 
                       RecursiveTraverseR(n.left, l-1, data);  //Calls itself (see, recursion!) but with the childs to look for all the nodes with the given level in the tree.
                    }
                   
                } else {
                    if (l-1 == 0){
                        if (n.right != null){
                            n.right = this.Rebalance(n.right);
                        }   
                    } else {
                        RecursiveTraverseR(n.right, l-1, data); 
                    }
                   
                }                
        }
    }
    
    
    
    private Node Rebalance(Node x){ //We rebalance a given node.
        if (this.BFactor(x) == 2){ //First we check if the node needs rebalancing or not., and which side is the one that is unbalanced. 
            if (Height(x.right.right) > Height(x.right.left)){ //After knowing which side of the tree is unbalanced, whe check if the node requires a simple rotation or a double rotation, and balance accordingly.
               x = rotateR(x); 
            } else {
                x.right = rotateL(x.right);
                x = rotateR(x);
            }
        } else if(this.BFactor(x) == -2){
            if (Height(x.left.left) > Height(x.left.right)){
                x = rotateL(x);
            } else {
                x.left = rotateR(x.left);
                x = rotateL(x);
            }
        }
        return x;
    }
    
    public int MinVal(Node n){ //We keep moving to the left of the tree, until there are no more left childs. Due to the nature of a search tree, the lowest value is in the leftmost part.
      while (n.left != null){
          n = n.left;
      }  
      return n.value;
    }
    
    public int MaxVal (Node n){ //We keep moving to the right of the tree, until there are no more right childs. Due to the nature of a search tree, the highest value is in the rightmost part.
       while (n.right != null){
          n = n.right;
      }  
      return n.value; 
    }
    
    public int Sum (Node n){ //A simple level traverse in which n is the starting point.
        int sum = 0;
        Queue<Node> queue = new LinkedList<Node>(); //We assume that the node we're using as a starting point for exists because we know that there are 10 nodes.
        queue.add(n);
        while (queue.isEmpty()==false){
            Node temp = queue.poll();
            sum = sum + temp.value; //We add the value of all the nodes that we traverse and return that value.
            if (temp.right != null){
                queue.add(temp.right);
            }
            if (temp.left != null){
                queue.add(temp.left);
            }
        }
        return sum; //If n is the root, we would get the sum of all the tree. However, if we use one of the root's child as starting point, we would get the sum of only one of the subtrees.
    }
    
    public Tree InvertTree(Node n){ //Same principle as the above subroutine. Only difference is that instead of making a sum, we are creting a node with the same value in another tree class.
        Tree itree = new Tree();
        Queue<Node> queue = new LinkedList<Node>(); //We assume that the node we're using as a starting point for exists because we know that there are 10 nodes.
        queue.add(n);
        while (queue.isEmpty()==false){
            Node temp = queue.poll();
            itree.AddNodeI(temp.value);
            if (temp.right != null){
                queue.add(temp.right);
            }
            if (temp.left != null){
                queue.add(temp.left);
            }
        }
        return itree;
    } 
    
    public void AddNode2I(Node n, int data){ //To add a node we start traversing the tree according to the value given. We do exactly the same that in AddNode2, but flipping the > and <.
            boolean c = true;
            while(c == true){ //Here we don't verify if the value already exist because we know that it won't happen.
            if (data > n.value){ //We take advantage of the properties of a search tree to minimize the iterations required, we look at the current value and if its lower than the given value, we go to the left, else we go right (bc we're inverting the tree).
                if (n.left == null){ //Once we find the spot where the value should go, we create a new node with the value given, link it to the tree and end the iterations.
                   n.left = new Node(data); 
                   c = false;
                } else {
                    n = n.left;
                }
            } else {
                if (n.right == null){
                   n.right = new Node(data); 
                   c = false;
                } else {
                    n = n.right;
                }
        }
        
            }
    
    }
    
    public void AddNodeI(int data){ //We create a secondary subroutine for easier balancing of the tree
        if (this.root == null){ //If there are no nodes, we create the new node with the given value and assign it as the root.
            this.root = new Node(data); 
        } else {
            this.AddNode2I(this.root, data); //We use the secondary subroutine explained above 
            boolean keep = this.RebalanceNeeded(this.root); //We check if the tree is unbalanced after adding the node and after each rebalance, and keep rebalancing it until the tree is fully balanced. If a single node in the whole tree needs rebalance, then this will return true.
            while (keep == true){
           this.RebalancingTraverse(this.root); //We apply a rebalance to every single node in the tree and inside the rebalance subroutine decide if it should change the node or not.
          keep = this.RebalanceNeeded(this.root);
        }
        
        }
    }
    
    private void RebalancingTraverse(Node n){ //The same as the subroutine with the same name, but without a data argument.
        for (int level=this.Height(this.root); level >= 1; level--){ 
            this.RecursiveTraverseR(root, level);          
        }
        this.root = this.Rebalance(this.root);
    }
    
     
    
     private void RecursiveTraverseR(Node n, int l){ //The same as the subroutine with the same name, but without a data argument.
        if (n == null){
            return; 
        } else { 
                    if (l-1 == 0){ //We don't use the properties of a search tree, and instead apply the rebalance to every node.
                        if (n.left != null){
                            n.left = this.Rebalance(n.left);
                        }
                        if (n.right != null){
                            n.right = this.Rebalance(n.right);
                        }
                    } else { 
                       RecursiveTraverseR(n.left, l-1);  
                        RecursiveTraverseR(n.right, l-1); 
                    }                              
        }
    }
    
    public Node SearchNode(Node n, int data){ //A simple level traverse search to return a node with a given value.
        Queue<Node> queue = new LinkedList<Node>();
        if (n == null){
            return null;
        }
        queue.add(n);
        while (queue.isEmpty()==false){
            Node temp = queue.poll();
             if (data == temp.value){ //We check if the current node has the given value, if true we return it, else we keep looking.
                return temp;
            }
            if (temp.right != null && data > temp.value){
                queue.add(temp.right);
            }
            if (temp.left != null && data < temp.value){
                queue.add(temp.left);
            } 
        }
        return null; //In case there isn't a node with the given value in the tree.
    }
}
