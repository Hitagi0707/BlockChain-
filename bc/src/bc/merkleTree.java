package bc;

import java.util.ArrayList;
import java.util.List;

public class merkleTree {
	  // 信息列表
	  List<String> infoList;
	  // 默克尔树根
	  String root;
	  
	  //默克尔树构造函数
	  public merkleTree(List<String> infoList) {
		    this.infoList = infoList;
		    root = "";
		  }
	  
//	  根据存储信息条目构造默克尔树
	  public void merkle_Tree() {
		    List<String> info_List = new ArrayList<String>();
		    for (int i = 0; i < this.infoList.size(); i++) {
		      info_List.add(this.infoList.get(i));
		    }
		    List<String> newInfoList = getNewInfoList(info_List);
		    while (newInfoList.size() != 1) {
		      newInfoList = getNewInfoList(newInfoList);
		    }
		    this.root = newInfoList.get(0);
		  }
	  
//	  迭代构建默克尔树
	  private List<String> getNewInfoList(List<String> InfoList) {
		    List<String> newInfoList = new ArrayList<String>();
		    int index = 0;
		    while (index < InfoList.size()) {
		      // 左子树构建
		      String left = InfoList.get(index);
		      index++;
		      // 右子树构建
		      String right = "";
		      if (index != InfoList.size()) {
		        right = InfoList.get(index);
		      }
		      // 左右分支合并计算哈希值
		      String sha2HexValue = StringUtil.applySha256(left+right);
		      newInfoList.add(sha2HexValue);
		      index++;
		    }
		    return newInfoList;
		  }
	  
//	  返回树根值
	  public String getRoot() {
		    return this.root;
		  }
}
