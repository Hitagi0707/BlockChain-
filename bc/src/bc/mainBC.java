package bc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Scanner;

public class mainBC {
	
	public static ArrayList<Block> blockchain = new ArrayList<Block>();//创建动态区块链链表
	public static ArrayList<merkleTree> merkleTrees=new ArrayList<merkleTree>();//创建对应默克尔树链表
	public static ArrayList<smartContract> smartContracts=new ArrayList<smartContract>();//创建列表存储需要认证的学生的学习成果
	public static int difficulty = 5;//定义静态挖矿难度
	
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("search course list...");		
//		启动爬虫从中国大学MOOC网抓取课程信息
//		ArrayList<String> courseList = new ArrayList<String>();
//		CrList cr= new CrList();
//		cr.crawl(courseList);
		
		// 直接从本地读取爬好的课程txt文件,载入到courseList中
		ArrayList<String> courseList = new ArrayList<>();
		try {
			File file = new File("/Users/zhijun/eclipse-workspace/bc/Courses.txt");
			InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file));
			BufferedReader bf = new BufferedReader(inputReader);
			// 按行读取课程并输出
			String str;
			while ((str = bf.readLine()) != null) {
				courseList.add(str);
				System.out.println(str);
			}
			bf.close();
			inputReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
		//add blocks to the blockchain ArrayList:		
//		Block1
		smartContract sc1=new smartContract(courseList.get(0), "20190500", 80, 2);//将学生A学习课程B的情况输入到智能合约中
		sc1.smart_contract();//课程认证
		smartContracts.add(sc1);//存储通过认证的学生学习成果
		ArrayList<String> digitalCertificats1 = new ArrayList<String>();//创建数字徽章列表存储该区块下所有的数字徽章
		digitalCertificats1.add(sc1.getHash());
		System.out.println("Trying to Mine block 1... ");
		merkleTree merkleTree1=new merkleTree(digitalCertificats1);//将该区块存储的数字徽章打包存储到默克尔树中
		merkleTree1.merkle_Tree();//默克尔树构建
		merkleTrees.add(merkleTree1);//将该区块对应默克尔树添加到默克尔树链表中
		addBlock(new Block(merkleTree1.getRoot(), "0"));//Block1上链
		
//		Block2
//		上传学习成果
		smartContract sc2=new smartContract(courseList.get(1), "20190501", 81, 1);
		sc2.smart_contract();
		smartContracts.add(sc2);
		smartContract sc3=new smartContract(courseList.get(2), "20190502", 82, 1);
		sc3.smart_contract();
		smartContracts.add(sc3);
//		学习成果通过认证生成数字徽章
		ArrayList<String> digitalCertificats2 = new ArrayList<String>();
		digitalCertificats2.add(sc2.getHash());
		digitalCertificats2.add(sc3.getHash());
		System.out.println("Trying to Mine block 2... ");
//		将数字徽章存储到区块中
		merkleTree merkleTree2=new merkleTree(digitalCertificats2);
		merkleTree2.merkle_Tree();
		merkleTrees.add(merkleTree2);
		addBlock(new Block(merkleTree2.getRoot(),blockchain.get(blockchain.size()-1).hash));
		
//		Block3
		smartContract sc4=new smartContract(courseList.get(3), "20190503", 83, 0);
		sc4.smart_contract();
		smartContracts.add(sc4);
		smartContract sc5=new smartContract(courseList.get(4), "20190504", 84, 1);
		sc5.smart_contract();
		smartContracts.add(sc5);
		smartContract sc6=new smartContract(courseList.get(5), "20190505", 85, 2);
		sc6.smart_contract();
		smartContracts.add(sc6);
		ArrayList<String> digitalCertificats3 = new ArrayList<String>();
		digitalCertificats3.add(sc4.getHash());
		digitalCertificats3.add(sc5.getHash());
		digitalCertificats3.add(sc6.getHash());
		System.out.println("Trying to Mine block 3... ");
		merkleTree merkleTree3=new merkleTree(digitalCertificats3);
		merkleTree3.merkle_Tree();
		merkleTrees.add(merkleTree3);
		addBlock(new Block(merkleTree3.getRoot(),blockchain.get(blockchain.size()-1).hash));	
		
//		Block4
		smartContract sc7=new smartContract(courseList.get(6), "20190506", 86, 0);
		sc7.smart_contract();
		smartContracts.add(sc7);
		smartContract sc8=new smartContract(courseList.get(7), "20190507", 87, 0);
		sc8.smart_contract();
		smartContracts.add(sc8);
		smartContract sc9=new smartContract(courseList.get(8), "20190508", 88, 0);
		sc9.smart_contract();
		smartContracts.add(sc9);
		smartContract sc10=new smartContract(courseList.get(9), "20190509", 89, 0);
		sc10.smart_contract();
		smartContracts.add(sc10);
		ArrayList<String> digitalCertificats4 = new ArrayList<String>();
		digitalCertificats4.add(sc7.getHash());
		digitalCertificats4.add(sc8.getHash());
		digitalCertificats4.add(sc9.getHash());
		digitalCertificats4.add(sc10.getHash());
		System.out.println("Trying to Mine block 4... ");
		merkleTree merkleTree4=new merkleTree(digitalCertificats4);
		merkleTree4.merkle_Tree();
		merkleTrees.add(merkleTree4);
		addBlock(new Block(merkleTree4.getRoot(),blockchain.get(blockchain.size()-1).hash));
		
//		验证区块链
		System.out.println("Blockchain is Valid: " + isChainValid());
//		输出区块链信息
		String blockchainJson = StringUtil.getJson(blockchain);
		System.out.println("The block chain: ");
		System.out.println(blockchainJson);
		
//		学习成果查询
		System.out.println("input the block number you want to query: ");
		Scanner sc=new Scanner(System.in);
		int i=sc.nextInt();
		System.out.println("input the credential number of this block you want to query: ");
		int j=sc.nextInt();
		CQuery(i,j);
		sc.close();
	}
	
//	区块链验证
	public static Boolean isChainValid() {
		Block currentBlock; 
		Block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
			//check if hash is solved
			if(!currentBlock.hash.substring( 0, difficulty).equals(hashTarget)) {
				System.out.println("This block hasn't been mined");
				return false;
			}
			// check if data is changed
			if (!merkleTrees.get(i).getRoot().equals(currentBlock.merkleRoot)) {
				System.out.println("The block has been changed");
				return false;
			}
		}
		return true;
	}
	
//	新区块添加
	public static void addBlock(Block newBlock) {
		newBlock.mineBlock(difficulty);
		blockchain.add(newBlock);
	}
	
//	学习成果查询
	public static void CQuery(int blockNumber,int smartContractNumber) {
//		输出该区块对应的默克尔树信息
		String merkleTreesJson=StringUtil.getJson(merkleTrees.get(blockNumber-1));
		System.out.println("The merkleTrees: ");
		System.out.println(merkleTreesJson);
		
//		输出该数字徽章下对应的学习证明
		String smartContractsJson=StringUtil.getJson(smartContracts.get(smartContractNumber-1));
		System.out.println("The credential: ");
		System.out.println(smartContractsJson);	
	}

}
