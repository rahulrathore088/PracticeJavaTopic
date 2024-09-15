package ds;

public class Trie_Test {
	static class Node {
		private boolean eow;
		private Node[] children;

		Node() {
			eow = false;
			children = new Node[26];
		}
		
	static Node root = new Node();
	
	public static void insertData(String word) {
		Node curr = root;
		for (int i = 0; i < word.length(); i++) {
			int idx = word.charAt(i) - 'a';
			if (curr.children[idx] == null) {
				curr.children[idx] = new Node();
			}
			if (i == word.length() - 1) {
				curr.children[idx].eow = true;
			}
		}
	}
	
	public static boolean searchWord(String key) {
		Node curr = root;
		for (int i = 0; i < key.length(); i++) {
			int idx = key.charAt(i) - 'a';
			if (curr.children[idx] == null) {
				return false;
			}
			if (i == key.length() - 1 && curr.children[idx].eow == false) {
				return false;
			}
			curr = curr.children[idx];
		}
		return true;
	}
	
	public static void main(String... s) {

	}
	}
	

}
