import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


public class BST<Key extends Comparable<Key>, Value>
{
    private Node root;

    private class Node
    {
        private Key key;
        private Value value;
        private Node left;
        private Node right;
        private int N;

        public Node(Key key, Value value, int N){
            this.key = key;
            this.value = value;
            this.N = N;
        }
    }

    public int size(){
        return size(root);
    }

    private int size(Node x){
        if (x == null) return 0;
        else          return x.N;
    }

    public Value get(Key key){
        return get(root, key);
    }

    private Value get(Node x, Key key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if         (cmp > 0) return get(x.right, key);
        else if    (cmp < 0) return get(x.left, key);
        else                 return x.value;
    }

    private Value get_use_loop(Key key){
        Node current = root;
        while(current != null){
            int cmp = key.compareTo(current.key);
            if          (cmp == 0) return current.value;
            else if     (cmp > 0)  current = current.right;
            else if     (cmp < 0)  current = current.left;
        }
        return null;
    }

    public void put(Key key, Value value){
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value){
        if (x == null) return new Node(key, value, 1);

        int cmp = key.compareTo(x.key);
        if          (cmp < 0) x.left = put(x.left, key, value);
        else if     (cmp > 0) x.right = put(x.right, key, value);
        else                  x.value = value;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void put_use_loop(Key key, Value value){
        // if this method is used, N will lose its meaning.
        if (root == null){
            root = new Node(key, value, 1);
            return;
        }
        Node current = root;
        while(current != null){
            int cmp = key.compareTo(current.key);
            if (cmp == 0) {
                current.value = value;
                return;
            }

            else if (cmp > 0)
            {
                if (current.right == null){
                    current.right = new Node(key, value, 1);
                    return;
                }else{
                    current = current.right;
                }
            }

            else if (cmp < 0)
            {
                if (current.left == null){
                    current.left = new Node(key, value, 1);
                    return;
                }else{
                    current = current.left;
                }
            }
        }
    }

    public Key min(){
        return min(root).key;
    }

    private Node min(Node x){
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key min_use_loop(){
        
        if (root.left == null)
            return root.key;

        Node current = root;
        Key temp = null;
        while(current.left != null){
            temp = current.left.key;
            current = current.left;
        }
        return temp;
    }

    public Key max(){
        return max(root).key;
    }

    private Node max(Node x){
        if (x.right == null) return x;
        return max(x.right);
    }

    public Key floor(Key key){
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0)  return floor(x.left ,key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else           return x;
    }

    public Key ceiling(Key key){
        Node x = ceiling(root, key);
        if (x == null) return null;
        return x.key;
    }

    private Node ceiling(Node x ,Key key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp > 0) return ceiling(x.right, key);
        Node t = ceiling(x.left, key);
        if (t != null) return t;
        else           return x;
    }

    public Key select(int k){
        return select(root, k).key;
    }

    private Node select(Node x, int k){
        if (x == null) return null;
        int t = size(x.left);
        if      (t > k) return select(x.left, k);
        else if (t < k) return select(x.right, k - t - 1);
        else            return x;
    }

    public int rank(Key key){
        return rank(key, root);
    }

    private int rank(Key key, Node x){
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else              return size(x.left);
    }

    public void deleteMin(){
        root = deleteMin(root);
    }

    private Node deleteMin(Node x){
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(Key key){
        root = delete(root, key);
    }

    private Node delete(Node x, Key key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else{
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public int height(){
        return height(root);
    }

    private int height(Node x){
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    private boolean isBinaryTree(Node x){
        return isBinaryTree(root, null, null);
    }

    private boolean isBinaryTree(Node x, Key min, Key max){
        if (x == null) return true;   // empty tree;
        if (min != null && x.key.compareTo(min) <= 0) return false;
        if (max != null && x.key.compareTo(max) >= 0) return false;
        return isBinaryTree(x.left, min, x.key) && isBinaryTree(x.right, x.key, max);
    }

    public Iterable<Key> keys(){
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi){
        Queue<Key> queue = new Queue<Key>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi){
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public static void main(String[] args) { 
        BST<String, Integer> st = new BST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}