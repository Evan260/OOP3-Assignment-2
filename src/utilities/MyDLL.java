package utilities;

/**
* MyDList.java
*
* A doubly-linked list implementation that allows for adding and removing elements
* from both ends of the list. Uses MyDLLNode for node implementation.
*/
public class MyDLL<E> {
   // Instance variables for list structure
   private MyDLLNode<E> head;  // First node in the list
   private MyDLLNode<E> tail;  // Last node in the list 
   private int size;           // Number of elements in the list

   /**
    * Constructs an empty doubly linked list.
    */
   public MyDLL() {
       this.head = null;
       this.tail = null;
       this.size = 0;
   }

   /**
    * Returns the first node in the list.
    * @return The head node
    * @throws IllegalStateException if the list is empty
    */
   public MyDLLNode<E> getHead() {
       if (head == null) {
           throw new IllegalStateException("List is empty");
       }
       return head;
   }

   /**
    * Adds a new element to the front of the list.
    * @param item The element to add
    */
   public void addFirst(E item) {
       MyDLLNode<E> newNode = new MyDLLNode<>(item);
       if (head == null) {
           head = tail = newNode;
       } else {
           newNode.next = head;
           head.prev = newNode;
           head = newNode;
       }
       size++;
   }

   /**
    * Adds a new element to the end of the list.
    * @param item The element to add
    */
   public void addLast(E item) {
       MyDLLNode<E> newNode = new MyDLLNode<>(item);
       if (tail == null) {
           head = tail = newNode;
       } else {
           newNode.prev = tail;
           tail.next = newNode;
           tail = newNode;
       }
       size++;
   }

   /**
    * Removes and returns the first element in the list.
    * @return The removed element
    * @throws IllegalStateException if the list is empty
    */
   public E removeFirst() {
       if (head == null) {
           throw new IllegalStateException("List is empty");
       }
       
       E removedElement = head.element;
       if (head == tail) {
           head = tail = null;
       } else {
           head = head.next;
           head.prev = null;
       }
       size--;
       return removedElement;
   }

   /**
    * Removes and returns the last element in the list.
    * @return The removed element
    * @throws IllegalStateException if the list is empty
    */
   public E removeLast() {
       if (head == null) {
           throw new IllegalStateException("List is empty");
       }
       
       E removedElement = tail.element;
       if (head == tail) {
           head = tail = null;
       } else {
           tail = tail.prev;
           tail.next = null;
       }
       size--;
       return removedElement;
   }

   /**
    * Returns the current size of the list.
    * @return Number of elements in the list
    */
   public int size() {
       return size;
   }

   /**
    * Checks if the list is empty.
    * @return true if the list contains no elements
    */
   public boolean isEmpty() {
       return size == 0;
   }
}