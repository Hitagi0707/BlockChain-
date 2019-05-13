package bc;

import java.util.ArrayList;
import java.util.List;

public class smartContract {
	public String courseInfo;
	public String studentInfo;
	public int courseScore;
	public int absenceTimes;
	private int scoreNeed=60;
	private int absenceNeed=3;
	public String hash;
	
//	智能合约构造函数，包含课程信息，学生信息,及学生学习情况（考试分数及缺勤次数）
	public smartContract(String courseInfo,String studentInfo,int courseScore,int absenceTimes) {
		this.courseInfo=courseInfo;
		this.studentInfo=studentInfo;
		this.courseScore=courseScore;
		this.absenceTimes=absenceTimes;
	}
	
//	执行智能合约
	public void smart_contract(){
//		学生考试分数不低于60，缺勤次数不超过3次满足课程学习认证要求，执行智能合约，生成证明学习成果的数字徽章
		if(courseScore>=scoreNeed & absenceTimes<=absenceNeed) {
//			将课程信息，学生信息，课程考核标准和学习情况等信息输入加密，输出数字徽章的hash值
			System.out.println("Authentication Success ");
			List<String> smart_contract_info = new ArrayList<String>();
			smart_contract_info.add(courseInfo);
			smart_contract_info.add("Student Information: "+studentInfo);
			smart_contract_info.add("Student Course Score: "+Integer.toString(courseScore));
			smart_contract_info.add("Student Absence Times: "+Integer.toString(absenceTimes));
			smart_contract_info.add("Course Required Lowst Score:  "+Integer.toString(scoreNeed));
			smart_contract_info.add("Course Stipulated the Ceiling of Absence Times: "+Integer.toString(absenceNeed));
			hash=StringUtil.applySha256(smart_contract_info.get(0)+
					smart_contract_info.get(1)+
					smart_contract_info.get(2)+
					smart_contract_info.get(3)+
					smart_contract_info.get(4)+
					smart_contract_info.get(5));
		}
		else {
//			课程学习成果认证失败，终止程序
			System.out.println("Authentication Fail ");
			System.exit(0);
	}
}
//	返回数字徽章的哈希值
	public String getHash() {
		return hash;
	}
	
}
