package bc;

import java.util.Date;

public class Block {
	public String hash;
	public String previousHash;
	private long timeStamp; 
	private int nonce;
	public String merkleRoot;
	
	//Block Constructor.
	public Block(String merkleRoot, String previousHash ) {
		this.previousHash = previousHash;
		this.timeStamp = new Date().getTime();
		this.hash = calculateHash(); 
		this.merkleRoot=merkleRoot;
	}
	
	//Calculate new hash based on blocks contents
	public String calculateHash() {
		String calculatedhash = StringUtil.applySha256( 
				previousHash +
				Long.toString(timeStamp) +
				Integer.toString(nonce) +
				merkleRoot
				);
		return calculatedhash;
	}
	
	//Increases nonce value until hash target is reached.
	public void mineBlock(int difficulty) {
		String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
	
}

