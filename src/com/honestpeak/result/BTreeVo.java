package com.honestpeak.result;

public class BTreeVo {
	private Byte num;	//关键字个数
	private Byte isLeaf;//是否为叶子节点，是叶子节点1;非叶子节点0
	private Integer key;//
	private Integer[] points;
	private BTreeVo next;
	public Byte getNum() {
		return num;
	}
	public void setNum(Byte num) {
		this.num = num;
	}
	public void setNum(int num) {
		this.num = (byte)num;
	}
	
	public Byte getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Byte isLeaf) {
		this.isLeaf = isLeaf;
	}
	public void setIsLeaf(int isLeaf) {
		this.isLeaf = (byte)isLeaf;
	}
	
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public Integer[] getPoints() {
		return points;
	}
	public void setPoints(Integer[] points) {
		this.points = points;
	}
	/*public void setPoints(BPlusTree<Long, Long>.Node<Integer, String> [] points) {
		this.points = points;
	}*/
	
	public BTreeVo getNext() {
		return next;
	}
	public void setNext(BTreeVo next) {
		this.next = next;
	}
	/**
	 * @Title: changeValue
	 * @Description: 模拟实现无符号整形
	 * @param obj
	 * @return
	 */
	public Integer changeValue(long obj) {
		return (int) (obj & 0xffffffff);
	}
}
