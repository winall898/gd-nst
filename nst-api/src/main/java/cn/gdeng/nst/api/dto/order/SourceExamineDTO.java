package cn.gdeng.nst.api.dto.order;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.gdeng.nst.enums.SourceExamineStatusEnum;

public class SourceExamineDTO  implements java.io.Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -1635518064797775290L;

	/**
    *主键ID
    */
    private Integer id;

    /**
    *关联货源id
    */
    private Integer sourceId;
    /**
     *订单编号
     */
     private String orderNo;
    /**
    *运单Id
    */
    private Integer orderInfoId;

    /**
    *验货说明
    */
    private String description;

    /**
    *验货图片1
    */
    private String imageUrl;

    /**
    *验货图片2
    */
    private String imageUrl2;

    /**
    *验货图片3
    */
    private String imageUrl3;

    /**
    *验货图片4
    */
    private String imageUrl4;

    /**
    *验货图片5
    */
    private String imageUrl5;

    /**
    *验货状态 1 通过 2 不通过
    */
    private Byte status;

    /**
    *创建人员ID
    */
    private String createUserId;

    /**
    *创建时间
    */
    private Date createTime;

    /**
    *修改人员ID
    */
    private String updateUserId;

    /**
    *修改时间
    */
    private Date updateTime;

    private List<String> imageUrlList;
    /**
     *验货状态 通过  不通过
     */
     private String statusVar;
    
    
    public List<String> getImageUrlList() {
    	if(imageUrl!=null){
    	imageUrlList =new ArrayList<String>();
    	imageUrlList.add(imageUrl);
       	if(imageUrl2!=null){
    	imageUrlList.add(imageUrl2);
       	}
       	if(imageUrl3!=null){
        	imageUrlList.add(imageUrl3);
        }
       	if(imageUrl4!=null){
        	imageUrlList.add(imageUrl4);
        }
       	if(imageUrl5!=null){
        	imageUrlList.add(imageUrl5);
        }
    	}
		return imageUrlList;
	}
	public void setImageUrlList(List<String> imageUrlList) {
		this.imageUrlList = imageUrlList;
	}
	public Integer getId(){

        return this.id;
    }
    public void setId(Integer id){

        this.id = id;
    }
   
    public String getDescription(){

        return this.description;
    }
    public void setDescription(String description){

        this.description = description;
    }
    public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getOrderInfoId() {
		return orderInfoId;
	}
	public void setOrderInfoId(Integer orderInfoId) {
		this.orderInfoId = orderInfoId;
	}
	public String getImageUrl(){

        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl){

        this.imageUrl = imageUrl;
    }
    public String getImageUrl2(){

        return this.imageUrl2;
    }
    public void setImageUrl2(String imageUrl2){

        this.imageUrl2 = imageUrl2;
    }
    public String getImageUrl3(){

        return this.imageUrl3;
    }
    public void setImageUrl3(String imageUrl3){

        this.imageUrl3 = imageUrl3;
    }
    public String getImageUrl4(){

        return this.imageUrl4;
    }
    public void setImageUrl4(String imageUrl4){

        this.imageUrl4 = imageUrl4;
    }
    public String getImageUrl5(){

        return this.imageUrl5;
    }
    public void setImageUrl5(String imageUrl5){

        this.imageUrl5 = imageUrl5;
    }
    public Byte getStatus(){

        return this.status;
    }
    public void setStatus(Byte status){

        this.status = status;
    }
    public String getCreateUserId(){

        return this.createUserId;
    }
    public void setCreateUserId(String createUserId){

        this.createUserId = createUserId;
    }
    public Date getCreateTime(){

        return this.createTime;
    }
    public void setCreateTime(Date createTime){

        this.createTime = createTime;
    }
    public String getUpdateUserId(){

        return this.updateUserId;
    }
    public void setUpdateUserId(String updateUserId){

        this.updateUserId = updateUserId;
    }
    public Date getUpdateTime(){

        return this.updateTime;
    }
    public void setUpdateTime(Date updateTime){

        this.updateTime = updateTime;
    }
	public Integer getSourceId() {
		return sourceId;
	}
	public void setSourceId(Integer sourceId) {
		this.sourceId = sourceId;
	}
	public String getStatusVar() {
		return SourceExamineStatusEnum.getNameByCode(getStatus());
	}
	public void setStatusVar(String statusVar) {
		this.statusVar = statusVar;
	}
    
    
}

