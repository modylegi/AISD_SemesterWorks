package AISD_SemesterWorks.DataStructures;


import org.w3c.dom.Node;

import java.util.Random;

public class SplayTree {
   private Node root;
   public SplayTree() {
       root = null;
   }
   private void printHelper(Node currPtr, String indent, boolean last) {
       if (currPtr != null) {
           System.out.print(indent);
           if (last) {
               System.out.print("R----");
               indent += " ";
           } else {
               System.out.print("L----");
               indent += "| ";
           }

           System.out.println(currPtr.data);

           printHelper(currPtr.left, indent, false);
           printHelper(currPtr.right, indent, true);
       }
   }

   private Node searchTreeHelper(Node node, int key) {
       if (node == null || key == node.data) {
           return node;
       }

       if (key < node.data) {
           return searchTreeHelper(node.left, key);
       }
       return searchTreeHelper(node.right, key);
   }

   void deleteNode(int data) {
       deleteNodeHelper(this.root, data);
   }
   private void deleteNodeHelper(Node node, int key) {
       Node x = null;
       Node t = null;
       Node s = null;
       while (node != null){
           if (node.data == key) {
               x = node;
           }
           if (node.data <= key) {
               node = node.right;
           } else {
               node = node.left;
           }
       }
       if (x == null) {
           System.out.println("Couldn't find key in the tree");
           return;
       }
       splay(x);
       if (x.right != null) {
           t = x.right;
           t.parent = null;
       } else {
           t = null;
       }
       s = x;
       s.right = null;
       x = null;
       if (s.left != null){
           s.left.parent = null;
       }
       root = join(s.left, t);
       s = null;
   }

   private void leftRotate(Node x) {
       Node y = x.right;
       x.right = y.left;
       if (y.left != null) {
           y.left.parent = x;
       }
       y.parent = x.parent;
       if (x.parent == null) {
           this.root = y;
       } else if (x == x.parent.left) {
           x.parent.left = y;
       } else {
           x.parent.right = y;
       }
       y.left = x;
       x.parent = y;
   }

   private void rightRotate(Node x) {
       Node y = x.left;
       x.left = y.right;
       if (y.right != null) {
           y.right.parent = x;
       }
       y.parent = x.parent;
       if (x.parent == null) {
           this.root = y;
       } else if (x == x.parent.right) {
           x.parent.right = y;
       } else {
           x.parent.left = y;
       }
       y.right = x;
       x.parent = y;
   }
   private void splay(Node x) {
       while (x.parent != null) {
           if (x.parent.parent == null) {
               if (x == x.parent.left) {
                   rightRotate(x.parent);
               } else {
                   leftRotate(x.parent);
               }
           } else if (x == x.parent.left && x.parent == x.parent.parent.left) {
               rightRotate(x.parent.parent);
               rightRotate(x.parent);
           } else if (x == x.parent.right && x.parent == x.parent.parent.right) {
               leftRotate(x.parent.parent);
               leftRotate(x.parent);
           } else if (x == x.parent.right && x.parent == x.parent.parent.left) {
               leftRotate(x.parent);
               rightRotate(x.parent);
           } else {// zag-zig
               rightRotate(x.parent);
               leftRotate(x.parent);
           }
       }
   }
   private Node join(Node tree1, Node tree2){
       if (tree1 == null) {
           return tree2;
       }

       if (tree2 == null) {
           return tree1;
       }
       Node x = maximum(tree1);
       splay(x);
       x.right = tree2;
       tree2.parent = x;
       return x;
   }
   private void preOrderHelper(Node node) {
       if (node != null) {
           System.out.print(node.data + " ");
           preOrderHelper(node.left);
           preOrderHelper(node.right);
       }
   }
   private void inOrderHelper(Node node) {
       if (node != null) {
           inOrderHelper(node.left);
           System.out.print(node.data + " ");
           inOrderHelper(node.right);
       }
   }

   private void postOrderHelper(Node node) {
       if (node != null) {
           postOrderHelper(node.left);
           postOrderHelper(node.right);
           System.out.print(node.data + " ");
       }
   }

   public void preorder() {
       preOrderHelper(this.root);
   }
   public void inorder() {
       inOrderHelper(this.root);
   }
   public void postorder() {
       postOrderHelper(this.root);
   }

   public Node searchTree(int k) {
       Node x = searchTreeHelper(root, k);
       if (x != null) {
           splay(x);
       }
       return x;
   }

   public Node minimum(Node node) {
       while (node.left != null) {
           node = node.left;
       }
       return node;
   }

   public Node maximum(Node node) {
       while (node.right != null) {
           node = node.right;
       }
       return node;
   }


   public Node successor(Node x) {
       if (x.right != null) {
           return
                   minimum(x.right);
       }

       Node y = x.parent;
       while (y != null && x == y.right) {
           x = y;
           y = y.parent;
       }
       return y;
   }

   public Node predecessor(Node x) {
       if (x.left != null) {
           return maximum(x.left);
       }

       Node y = x.parent;
       while (y != null && x == y.left) {
           x = y;
           y = y.parent;
       }

       return y;
   }


   public void insert(int key) {
       Node node = new Node(key);
       Node y = null;
       Node x = this.root;

       while (x != null) {
           y = x;
           if (node.data < x.data) {
               x = x.left;
           } else {
               x = x.right;
           }
       }

       node.parent = y;
       if (y == null) {
           root = node;
       } else if (node.data < y.data) {
           y.left = node;
       } else {
           y.right = node;
       }
       splay(node);
   }

   public void prettyPrint() {
       printHelper(this.root, "", true);
   }

   private static final long SEED = 2022;
   public static void main(String [] args) {
       // добавление
       SplayTree tree = new SplayTree();
       Random random = new Random();
       for (int i = 0; i < 100000; i++) {
           tree.insert(random.nextInt(100));
       }
       long start = System.nanoTime();
       long finish = System.nanoTime();
       long difference = finish - start;
       System.out.println("Lead time: " + difference / 1000);

       // удаление

       SplayTree tree1 = new SplayTree();
       Random random1 = new Random();
       for (int i = 0; i < 100000; i++) {
           tree1.deleteNode(random1.nextInt(100));
       }
       long start1 = System.nanoTime();
       long finish1 = System.nanoTime();
       long difference1 = finish1 - start1;
       System.out.println("Lead time: " + difference1 / 1000);


   }
}
