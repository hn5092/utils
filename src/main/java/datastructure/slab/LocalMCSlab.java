package datastructure.slab;

import java.util.HashMap;
import java.util.Map;
/**
 * 
 * @author maoyidao
 *
 * @param <K>
 * @param <V>
 */
public class LocalMCSlab<K, V> {
    /**
     * The map the keys and values are stored in.
     */
    protected Map<Object, CacheObject> map;
    protected Map<K, Object> hashKeyMap;
    private int chunkSize; 
    
    /**
     * Linked list to maintain time that cache objects were initially added
     * to the cache, most recently added to oldest added.
     */
    protected LinkedList ageList;
    
    public LocalMCSlab(int chunkSize) {
        // Our primary data structure is a HashMap. The default capacity of 11
        // is too small in almost all cases, so we set it bigger.
        map = new HashMap<Object, CacheObject>(101);
        hashKeyMap = new HashMap();
        ageList = new LinkedList();
        
        this.chunkSize = chunkSize;
    }

	public synchronized boolean put(K key, byte[] value, boolean removeLRU) {
       	if(removeLRU) {
       		LinkedListNode lastNode = ageList.removeLast();
        	Object lasthashKey = hashKeyMap.remove(lastNode.object);
        	
        	if(lasthashKey == null) {
        		return false;
        	}
        	
        	Stat.inc("eviction[" + this.chunkSize + "]");
        	CacheObject<byte[]> data = map.get(lasthashKey);
        	System.arraycopy(value, 0, data.object, 0, value.length);
        	data.length = value.length;
        	
        	// update key / hashkey mapping
        	hashKeyMap.put(key, lasthashKey);
        	
        	lastNode.object = key;
        	ageList.addFirst(lastNode);
        }
        else {
        	byte[] data = new byte[this.chunkSize];
        	System.arraycopy(value, 0, data, 0, value.length);
        	CacheObject<byte[]> cacheObject = new CacheObject<byte[]>(data);
        	cacheObject.length = value.length;
        	Object hashKey = new Object();
        	hashKeyMap.put(key, hashKey);
        	map.put(hashKey, cacheObject);
        	LinkedListNode ageNode =  ageList.addFirst(key);
        	cacheObject.ageListNode = ageNode;
        	
        	Stat.inc("addnew[" + chunkSize + "]");
        }
        
        return true;
    }

    public synchronized byte[] get(Object key) {
    	Object hashkey = hashKeyMap.get(key);
    	
    	if(hashkey == null) {
    		return null;
    	}
    	
        CacheObject<byte[]> cacheObject = map.get(hashkey);

        if (cacheObject == null) {
            return null;
        }

        byte result[] = java.util.Arrays.copyOfRange(cacheObject.object, 0, cacheObject.length);
        return result;
    }

    public synchronized void remove(Object key) {
    	map.remove(key);
    }
    
    public synchronized byte[] removeChunk(Object key) {
    	Object hashkey = hashKeyMap.remove(key);
        CacheObject<byte[]> cacheObject = map.get(hashkey);
        
        // If the object is not in cache, stop trying to remove it.
        if (cacheObject == null) {
            return null;
        }
        // remove from the hash map
        map.remove(key);
        // remove from the cache order list
        cacheObject.ageListNode.remove();
        // remove references to linked list nodes
        cacheObject.ageListNode = null;
        
        byte result[] = java.util.Arrays.copyOfRange(cacheObject.object, 0, cacheObject.length);
        return result;
    }
    
    public synchronized Object firstKey() {
    	LinkedListNode node = ageList.getFirst();
    	
    	if(node == null)
    		return null;
    	
    	return node.object;
    }
    
    public synchronized Object lastKey() {
    	LinkedListNode node = ageList.getLast();
    	
    	if(node == null)
    		return null;
    	
    	return node.object;
    }

    public synchronized void clear() {
        Object[] keys = map.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            remove(keys[i]);
        }

        // Now, reset all containers.
        map.clear();
        ageList.clear();
        ageList = new LinkedList();
    }

    public int size() {
        return map.size();
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    /**
     * Wraps a cached object collection to return a view of its inner objects
     */
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    /**
     * Wrapper for all objects put into cache. It's primary purpose is to maintain
     * references to the linked lists that maintain the creation time of the object
     * and the ordering of the most used objects.
     */
    private static class CacheObject<V> {

        /**
         * Underlying object wrapped by the CacheObject.
         */
        public V object;
        public int length;
        /**
         * A reference to the node in the age order list. We keep the reference
         * here to avoid linear scans of the list. The reference is used if the
         * object has to be deleted from the list.
         */
        public LinkedListNode ageListNode;

        public CacheObject(V object) {
            this.object = object;
        }
    }
    
    private static class LinkedListNode {
        public LinkedListNode previous;
        public LinkedListNode next;
        public Object object;
        /**
         * Constructs a new linked list node.
         *
         * @param object   the Object that the node represents.
         * @param next     a reference to the next LinkedListNode in the list.
         * @param previous a reference to the previous LinkedListNode in the list.
         */
        public LinkedListNode(Object object, LinkedListNode next,
                              LinkedListNode previous) {
            this.object = object;
            this.next = next;
            this.previous = previous;
        }

        /**
         * Removes this node from the linked list that it is a part of.
         */
        public void remove() {
            previous.next = next;
            next.previous = previous;
        }

        /**
         * Returns a String representation of the linked list node by calling the
         * toString method of the node's object.
         *
         * @return a String representation of the LinkedListNode.
         */
        @Override
    	public String toString() {
            return object == null ? "null" : object.toString();
        }
    }
    
    /**
     * Simple LinkedList implementation. The main feature is that list nodes
     * are public, which allows very fast delete operations when one has a
     * reference to the node that is to be deleted.<p>
     *
     * @author Jive Software
     */
    public static class LinkedList {

        /**
         * The root of the list keeps a reference to both the first and last
         * elements of the list.
         */
        private LinkedListNode head = new LinkedListNode("head", null, null);

        /**
         * Creates a new linked list.
         */
        public LinkedList() {
            head.next = head.previous = head;
        }

        /**
         * Returns the first linked list node in the list.
         *
         * @return the first element of the list.
         */
        public LinkedListNode getFirst() {
            LinkedListNode node = head.next;
            if (node == head) {
                return null;
            }
            return node;
        }

        /**
         * Returns the last linked list node in the list.
         *
         * @return the last element of the list.
         */
        public LinkedListNode getLast() {
            LinkedListNode node = head.previous;
            if (node == head) {
                return null;
            }
            return node;
        }
        
        public LinkedListNode removeLast() {
            LinkedListNode node = head.previous;
            if (node == head) {
                return null;
            }
            
            head.previous = node.previous;
            return node;
        }

        /**
         * Adds a node to the beginning of the list.
         *
         * @param node the node to add to the beginning of the list.
         */
        public LinkedListNode addFirst(LinkedListNode node) {
        	node.next = head.next;
            head.next = node;
            node.previous = head;
            node.next.previous = node;
            return node;
        }

        /**
         * Adds an object to the beginning of the list by automatically creating a
         * a new node and adding it to the beginning of the list.
         *
         * @param object the object to add to the beginning of the list.
         * @return the node created to wrap the object.
         */
        public LinkedListNode addFirst(Object object) {
            LinkedListNode node = new LinkedListNode(object, head.next, head);
            node.previous.next = node;
            node.next.previous = node;
            return node;
        }

        /**
         * Adds an object to the end of the list by automatically creating a
         * a new node and adding it to the end of the list.
         *
         * @param object the object to add to the end of the list.
         * @return the node created to wrap the object.
         */
        public LinkedListNode addLast(Object object) {
            LinkedListNode node = new LinkedListNode(object, head, head.previous);
            node.previous.next = node;
            node.next.previous = node;
            return node;
        }

        /**
         * Erases all elements in the list and re-initializes it.
         */
        public void clear() {
            //Remove all references in the list.
            LinkedListNode node = getLast();
            while (node != null) {
                node.remove();
                node = getLast();
            }

            //Re-initialize.
            head.next = head.previous = head;
        }

        /**
         * Returns a String representation of the linked list with a comma
         * delimited list of all the elements in the list.
         *
         * @return a String representation of the LinkedList.
         */
        @Override
    	public String toString() {
            LinkedListNode node = head.next;
            StringBuilder buf = new StringBuilder();
            while (node != head) {
                buf.append(node.toString()).append(", ");
                node = node.next;
            }
            return buf.toString();
        }
    }
}



