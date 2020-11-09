package com.honestpeak.result;

import java.util.List;

import com.honestpeak.utils.QEncodeUtil;

/**
 * @ClassName: Tree
 * @Description: TreeVO
 * @author Jeabev
 * @date 2016年7月30日 下午2:56:37
 */
public class Tree implements java.io.Serializable {

    private static final long serialVersionUID = 980682543891282923L;
    private Long id;
    private String name;
    private boolean open = true;// open:true,closed:false
    private boolean checked = false;
    private Object attributes;
    private List<Tree> children;
    private String iconCls;
    private Long pId;
	public Long getId() {
		return id;
	}
	public String getIdEncrypt() throws Exception {
		return id==null?null:QEncodeUtil.encryptId(id);
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isOpen() {
		return open;
	}
	public void setOpen(boolean open) {
		this.open = open;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public Object getAttributes() {
		return attributes;
	}
	public void setAttributes(Object attributes) {
		this.attributes = attributes;
	}
	public List<Tree> getChildren() {
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Long getpId() {
		return pId;
	}
	public String getpIdEncrypt() throws Exception {
		return pId==null?null:QEncodeUtil.encryptId(pId);
	}
	public void setpId(Long pId) {
		this.pId = pId;
	}
}
