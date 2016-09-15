/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Algos.Assn1;
import java.util.*;
/**
 *
 * @author DJ
 */

class BinomialHeap
{
    public BinomialHeapNode Nodes;
    private int size;
 
    
    public BinomialHeap()
    {
        Nodes = null;
        size = 0;
    }
    
    public boolean isEmpty()
    {
        return Nodes == null;
    }
    
    public int getSize()
    {
        return size;
    }
    
    public void makeEmpty()
    {
        Nodes = null;
        size = 0;
    }
        
    public void insert(int value) 
    {
        if (value > 0)
        {
            BinomialHeapNode temp = new BinomialHeapNode(value);
            if (Nodes == null) 
            {
                Nodes = temp;
                size = 1;
            } 
            else 
            {
                unionNodes(temp);
                size++;
            }
        }
    }
    
    public void merge(BinomialHeapNode binHeap) 
    {
        BinomialHeapNode temp1 = Nodes, temp2 = binHeap;
 
        while ((temp1 != null) && (temp2 != null)) 
        {
            if (temp1.degree == temp2.degree) 
            {
                BinomialHeapNode tmp = temp2;
                temp2 = temp2.sibling;
                tmp.sibling = temp1.sibling;
                temp1.sibling = tmp;
                temp1 = tmp.sibling;
            } 
            else 
            {
                if (temp1.degree < temp2.degree) 
                {
                    if ((temp1.sibling == null) || (temp1.sibling.degree > temp2.degree)) 
                    {
                        BinomialHeapNode tmp = temp2;
                        temp2 = temp2.sibling;
                        tmp.sibling = temp1.sibling;
                        temp1.sibling = tmp;
                        temp1 = tmp.sibling;
                    }
                    else 
                    {
                        temp1 = temp1.sibling;
                    }
                }
                else 
                {
                    BinomialHeapNode tmp = temp1;
                    temp1 = temp2;
                    temp2 = temp2.sibling;
                    temp1.sibling = tmp;
                    if (tmp == Nodes) 
                    {
                        Nodes = temp1;
                    }
                    else 
                    {
 
                    }
                }
            }
        }
        if (temp1 == null) 
        {
            temp1 = Nodes;
            while (temp1.sibling != null) 
            {
                temp1 = temp1.sibling;
            }
            temp1.sibling = temp2;
        } 
        else
        {
 
        }
    }
    
    public void unionNodes(BinomialHeapNode binHeap) 
    {
        merge(binHeap);
 
        BinomialHeapNode prevTemp = null, temp = Nodes, nextTemp = Nodes.sibling;
 
        while (nextTemp != null) 
        {
            if ((temp.degree != nextTemp.degree) || ((nextTemp.sibling != null) && (nextTemp.sibling.degree == temp.degree))) 
            {                
                prevTemp = temp;
                temp = nextTemp;
            } 
            else
            {
                if (temp.key <= nextTemp.key) 
                {
                    temp.sibling = nextTemp.sibling;
                    nextTemp.parent = temp;
                    nextTemp.sibling = temp.child;
                    temp.child = nextTemp;
                    temp.degree++;
                } 
                else 
                {
                    if (prevTemp == null) 
                    {
                        Nodes = nextTemp;
                    }
                    else 
                    {
                        prevTemp.sibling = nextTemp;
                    }
                    temp.parent = nextTemp;
                    temp.sibling = nextTemp.child;
                    nextTemp.child = temp;
                    nextTemp.degree++;
                    temp = nextTemp;
                }
            }
            nextTemp = temp.sibling;
        }
    }
    
    public int findMinimum() 
    {
        return Nodes.findMinNode().key;
    }
    
    public void delete(int value) 
    {
        if ((Nodes != null) && (Nodes.findANodeWithKey(value) != null)) 
        {
            decreaseKeyValue(value, findMinimum() - 1);
            extractMin();
        }
    }
    
    public void decreaseKeyValue(int old_value, int new_value) 
    {
        BinomialHeapNode temp = Nodes.findANodeWithKey(old_value);
        if (temp == null)
            return;
        temp.key = new_value;
        BinomialHeapNode tempParent = temp.parent;
 
        while ((tempParent != null) && (temp.key < tempParent.key)) 
        {
            int z = temp.key;
            temp.key = tempParent.key;
            tempParent.key = z;
 
            temp = tempParent;
            tempParent = tempParent.parent;
        }
    }
    
    public int extractMin() 
    {
        if (Nodes == null)
            return -1;
 
        BinomialHeapNode temp = Nodes, prevTemp = null;
        BinomialHeapNode minNode = Nodes.findMinNode();
 
        while (temp.key != minNode.key) 
        {
            prevTemp = temp;
            temp = temp.sibling;
        }
 
        if (prevTemp == null) 
        {
            Nodes = temp.sibling;
        }
        else
        {
            prevTemp.sibling = temp.sibling;
        }
 
        temp = temp.child;
        BinomialHeapNode fakeNode = temp;
 
        while (temp != null) 
        {
            temp.parent = null;
            temp = temp.sibling;
        }
 
        if ((Nodes == null) && (fakeNode == null))
        {
            size = 0;
        } 
        else
        {
            if ((Nodes == null) && (fakeNode != null)) 
            {
                Nodes = fakeNode.reverse(null);
                size = Nodes.getSize();
            }
            else
            {
                if ((Nodes != null) && (fakeNode == null))
                {
                    size = Nodes.getSize();
                }
                else
                {
                    unionNodes(fakeNode.reverse(null));
                    size = Nodes.getSize();
                }
            }
        }
 
        return minNode.key;
    }
 
    public void displayHeap()
    {
        System.out.print("\nHeap : ");
        displayHeap(Nodes);
        System.out.println("\n");
    }
    private void displayHeap(BinomialHeapNode r)
    {
        if (r != null)
        {
            displayHeap(r.child);
            System.out.print(r.key +" ");
            displayHeap(r.sibling);
            //System.out.println("\n");
        }
    }    
}   