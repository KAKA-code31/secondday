package com.honestpeak.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.honestpeak.mapper.JudgeMapper;
import com.honestpeak.mapper.JudgeSelectCourseMapper;
import com.honestpeak.model.Judge;
import com.honestpeak.model.JudgeSelectCourse;
import com.honestpeak.result.Tree;
import com.honestpeak.service.JudgeService;
import com.honestpeak.utils.Config;
@Service
public class JudgeServiceImpl implements JudgeService{
	@Autowired
	private JudgeSelectCourseMapper judgeSelectCourseMapper;
	@Autowired
	private JudgeMapper judgeMapper;
	@Override
	public List<Long> findJudgeIdListBySelectcourseId(Long id) {
		if(id!=null){
			return judgeSelectCourseMapper.findJudgeIdListBySelectcourseId(id);
		}else{
			return null;
		}
	}
	@Override
	public int updateJudgeSelectcourse(Long id, String judges) {
		//查询出来，全部删除
		List<Long> judgeSelectcourse = judgeSelectCourseMapper.findJudgeSelectcourse(id);
		if(judgeSelectcourse!=null && judgeSelectcourse.size()>0){
			Long[] rpArray = new Long[judgeSelectcourse.size()];
			judgeSelectCourseMapper.deleteAll(judgeSelectcourse.toArray(rpArray));
		}
		
		if(judges!=null ){
			StringTokenizer st = new StringTokenizer(judges, ",");
			String[] strArray = new String[st.countTokens()];
			int strLeng = st.countTokens();
			for (int i=0; i<strLeng; i++) {
			strArray[i] = st.nextToken();
			}
			if(strArray.length>0){
			JudgeSelectCourse rp = null;
			for(String string : strArray){
				rp = new JudgeSelectCourse();
				rp.setJudgeId(Long.parseLong(string));
				rp.setSelectcourseId(id);
				judgeSelectCourseMapper.insert(rp);
			}
			}
		}
		return 1;
	}
	@Override
	public String deleteJudge(String id) {
		int num = judgeMapper.deleteByPrimaryKey(Long.valueOf(id));
		return "success";
	}
	@Override
	public String addOrUpdateJudge(Judge judge) {
		int numFlag = 0;
		//根据id查询分类信息
		if (judge.getId() ==null) {
			return "error";
		}
		int num = judgeMapper.findJudgeById(judge.getId());
		if (num > 0) {//更新信息
			String [] strs = judge.getName().split("[-]");
			if(strs.length == 2){
				 judge.setName(strs[0]);
				 judge.setScore(Double.valueOf(strs[1]));
			 }else{
				 judge.setName(judge.getName());
			 }
			numFlag = judgeMapper.updateByPrimaryKeySelective(judge);
		} else {//插入信息
			if (judge.getParentId().equals("null")) {
				judge.setParentId(0l);
			}
			//int orderId = libraryMapper.findLastLibrary(library);
			//	orderId++;
			String [] strs = judge.getName().split("[-]");
			if(strs.length == 2){
				 judge.setName(strs[0]);
				 judge.setScore(Double.valueOf(strs[1]));
			 }else{
				 judge.setName(judge.getName());
			 }
			numFlag = judgeMapper.insert(judge);
		}
		return "success";
	}
	@Override
	public List<Object> findAllJudge() {
			List<Object> listZTree = new ArrayList<Object>();
			List<Judge> listLibrary = judgeMapper.findAllJudge();
			String str = "";
			for (int i = 0; i < listLibrary.size(); i++) {
				Judge library = listLibrary.get(i);//分类信息
				str = "{id:'" + library.getId() + "', pId:'" + library.getParentId() + "', name:\"" + library.getName() + "\" }";//封装ztree需要格式的字符串
				listZTree.add(str);
			}
			return listZTree;
	}
	@Override
	public Long insertJudgeof(Judge judge) {
	/*	Judge judge = new Judge();
		judge.setParentId(Long.valueOf(pId));
		judge.setName(name);*/
		return judgeMapper.insertof(judge);
	}
	@Override
	public Object selectByPIdAndName(String pId, String name) {
		Long pId1 = Long.valueOf(pId);
		return judgeMapper.selectByPIdAndName(pId1, name);
	}
	@Override
	public Long selectMaxId() {
		return judgeMapper.selectMaxId();
	}
	@Override
	public void updateJudges(Long id, Long flag) {
		judgeMapper.updateJudges(id,flag);
	}
	@Override
	public int getCountByCourseId(Long cid) {
		return judgeMapper.getCountByCourseId(cid);
	}
	@Override
	public List<Judge> getByCourseId(Long cid) {
		return judgeMapper.getByCourseId(cid);
	}
	@Override
	public List<Judge> selectByPId(Long id) {
		return  judgeMapper.selectByPId(id);
	}
	
	@Override
	public Judge getParentById(Long id){
		return  judgeMapper.getParentById(id);
	}
	@Override
	public List<Judge> selectJudgeIdBySelId(Long selectcourseId) {
		return judgeMapper.selectJudgeIdBySelId(selectcourseId);
	}
	@Override
	public List<Judge> selectByPrimaryKey(Long id) {
		return judgeMapper.selectByPrimaryKey1(id);
	}
	@Override
	public Double getScoreById(Long id) {
		return judgeMapper.getScoreById(id);
	}
}
