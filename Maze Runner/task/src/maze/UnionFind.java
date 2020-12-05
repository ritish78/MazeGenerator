package maze;

public class UnionFind {

    private int numElements;       //no. of elements in union find
    private int[] size;             //size of each component
    private int[] id;               //id points to parent of i, if id[i] = i, then i is a root node
    private int numComponents;      //no. of components in union find (starting from all to 1)

    public UnionFind(int numElements){
        //Checking if the provided size is more than 0
        if (numElements <= 0){
            throw new IllegalArgumentException("Size needs to be bigger than 0");
        }

        //Assigning start values to the variables
        this.numElements = numElements;
        this.numComponents = numElements;
        this.size = new int[numElements];
        this.id = new int[numElements];

        for (int i = 0; i < numElements; i++){
            //At first, every element is of size 1, and can be increased to size of numElements
            size[i] = 1;
            //Then, we need to link every component to itself for reference as it is it's own root
            id[i] = i;
        }
    }


    //For finding which component 'm' belongs to
    public int find(int m){
        //First, finding root of component
        int root = m;
        while (root != id[root]){
            root = id[root];
        }

        //compressing the path which leads back to the root.
        //This operation is also called path compression
        //it gives us amortized constant time complexity
        while (m != root){
            int next = id[m];
            id[m] = root;
            m = next;
        }

        return root;
    }

    //Returning true if 'm' and 'n' are in same components
    public boolean isConnected(int m, int n){
        return find(m) == find(n);
    }

    //Returning the size of component in which 'm' belongs to
    public int getComponentSize(int m){
        return size[find(m)];
    }

    //Returning the number of elements in this UnionFind
    public int getSize(){
        return numElements;
    }

    //Returning the number of remaining components
    public int getNumComponents(){
        return numComponents;
    }


    //Unify components which contains elements 'm' and 'n'
    public void unify(int m, int n){
        int mRoot = find(m);
        int nRoot = find(n);

        //Checking if the elements are in the same group
        if (mRoot == nRoot){
            return;
        }

        //If the elements are not in the same group then merge
        //the two components, from smaller to larger
        if (size[mRoot] < size[nRoot]){
            size[nRoot] += size[mRoot];
            id[mRoot] = nRoot;
        }else{
            size[mRoot] += size[nRoot];
            id[nRoot] = mRoot;
        }

        //Finally, after we merge the components, we decrease the count of components
        numComponents--;
    }
}
