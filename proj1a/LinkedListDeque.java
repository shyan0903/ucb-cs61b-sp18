/* A LinkedListDeque Class of Generic Type */
public class LinkedListDeque<T>{
	private class Node{
		public Node prev;
		public T item;
		public Node next;

		public Node(Node previous, T i, Node n){
			prev = previous;
			item = i;
			next = n;
		}
	}
	/* A sentinel node that is always there. The first element goes after it. */
	private Node sentinel;
	private int size;

	/* Creates an empty LinkedListDeque. */
	public LinkedListDeque(){
		sentinel = new Node(null, null, null);
		sentinel.next = sentinel;
		sentinel.prev = sentinel;
		size = 0;
	}

	/* The constructor for LinkedListDeque that takes in a value for the first element. */
	public LinkedListDeque(T item){
		sentinel = new Node(null, null, null);
		sentinel.next = new Node(sentinel, item, sentinel);
		sentinel.prev = sentinel.next;
		size = 1;
	}

	/* Adds an item of type T to the front of the deque. */
	public void addFirst(T item){
		size++;
		sentinel.next = new Node(sentinel, item, sentinel.next);
		if (size == 1) {
			sentinel.prev = sentinel.next;
		}
	}

	/* Adds an item of type T to the back of the deque. */
	public void addLast(T item){
		size++;
		Node originalLast = sentinel.prev;
		Node last = new Node(originalLast, item, sentinel);
		originalLast.next = last;
		sentinel.prev = last;
		if (size == 1){
			sentinel.next = sentinel.prev;
		}
	}

	/* Returns true if deque is empty, false otherwise. */
	public boolean isEmpty(){
		return size == 0;
	}

	/* Returns the number of items in the deque. */
	public int size(){
		return size;
	}

	/* Prints the items in the deque from first to last, separated by a space. */
	public void printDeque(){
		if (size > 0) {
			Node ptr = sentinel.next;
			while (ptr != sentinel) {
				System.out.print(ptr.item + " ");
				ptr = ptr.next;
			}
			System.out.println();
		}
	}

	/* Removes and returns the item at the front of the deque.
		If no such item exists, returns null. */
	public T removeFirst(){
		if (size > 0){
			size--;
			T removed = sentinel.next.item;
			sentinel.next = sentinel.next.next;
			if (size == 0){
				sentinel.prev = sentinel;
			}
			return removed;
		}
		return null;
	}

	/* Removes and returns the item at the back of the deque.
		If no such item exists, returns null. */
	public T removeLast(){
		if (size > 0){
			size--;
			T removed = sentinel.prev.item;
			Node secondToLast = sentinel.prev.prev;
			sentinel.prev = secondToLast;
			secondToLast.next = sentinel;
			if (size == 0){
				sentinel.next = sentinel;
			}
			return removed;
		}
		return null;
	}

	/* Gets the item at the given index, where 0 is the front,
	 	1 is the next item, and so forth. If no such item exists,
	 	returns null. */
	public T get(int index){
		// when index is out of bound
		if (index + 1 > size) {
			return null;
		}
		// gets the first item
		if (index == 0){
			return sentinel.next.item;
		}
		// move the ptr to the correct position
		Node ptr = sentinel.next;
		while (index > 0) {
			ptr = ptr.next;
			index--;
		}
		return ptr.item;
	}

	/* A recursive get method. Needs a helper function since
	* 	it cannot be called on a Node. */
	public T getRecursive(int index) {
		if (index + 1 > size){
			return null;
		}
		return getRecursive(index, sentinel.next);
	}

	public T getRecursive(int index, Node n){
		if (index == 0){
			return n.item;
		}
		return getRecursive(index - 1, n.next);

	}
}