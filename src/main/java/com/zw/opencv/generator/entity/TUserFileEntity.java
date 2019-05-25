package com.zw.opencv.generator.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author zhangwei
 * @email zhangwei@gmail.com
 * @date 2019-05-14 16:28:16
 */
@Data
@TableName("t_user_file")
public class TUserFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Integer userId;
	/**
	 * 
	 */
	private Integer projectId;
	/**
	 * 
	 */
	private Integer authWeight;
	/**

	/**
	 * 
	 */
	private Date createTime;
	/**
	 * 
	 */
	private Date modifyTime;
	/**
	 * 
	 */
	private String imgUrl;

	private Integer bTemp;

	private Integer dTemp;

	private Integer type;

	/**
	 *
	 */
	@TableField(exist = false)
	private String projectName;


	@TableField(exist = false)
	private String downloadFile;

	@TableField(exist = false)
	private Integer joinNum;



}
