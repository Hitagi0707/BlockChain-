# BlockChain
电子商务与应用 第九组 区块链在在线学习监督与评定中的应用和创新 小组作业

前期准备：
1. 火狐浏览器 + geckodriver驱动
2. 爬虫包 selenium-java-3
3. gson-2.6.2

运行情况：
主程序文件为mainBC，爬虫文件CrList
由于课程信息已经爬好并存储成txt文件，所以默认直接从本地读取课程信息；
要想直接从爬虫开始运行程序，只需将mainBC文件中24～26三行取消注释，并把后面29～44行注释即可，如下所示：

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
 
 爬虫情况截图：
 
  
