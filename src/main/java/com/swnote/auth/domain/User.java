package com.swnote.auth.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户信息
 * 
 * @author lzj
 * @since 1.0
 * @date [2019-04-16]
 */
@Data
@NoArgsConstructor
@TableName("auth_user")
public class User implements Serializable {
    private static final long serialVersionUID = -609988047799499431L;

    /**
     * 主键
     */
    @TableId(value = "userId", type = IdType.UUID)
    private String userId;

    /**
     * 用户代码
     */
    @TableField("code")
    private String code;

    /**
     * 用户名
     */
    @TableField("loginName")
    private String loginName;
    
    /**
     * 电子邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 真实姓名
     */
    @TableField("realName")
    private String realName;

    /**
     * 手机号码
     */
    @TableField("cellphone")
    private String cellphone;

    /**
     * 身份证号
     */
    @TableField("idCard")
    private String idCard;

    /**
     * 身份证照片路径
     */
    @TableField("idCardImgPath")
    private String idCardImgPath;

    /**
     * 实名认证状态，0：未实名认证，1：已实名认证，-1：实名认证失败
     */
    @TableField("realStatus")
    private Integer realStatus;

    /**
     * 性别，0：表示女，1：表示男，-1：表示保密
     */
    @TableField("sex")
    private Integer sex;

    /**
     * 头像图片路径
     */
    @TableField("picture")
    private String picture;

    /**
     * 个人简介
     */
    @TableField("introduce")
    private String introduce;

    /**
     * 是否激活，0：未激活，1：已激活
     */
    @TableField("isActive")
    private Integer isActive;

    /**
     * 账号状态，0：禁用，1：启用
     */
    @TableField("status")
    private Integer status;

    /**
     * 关注用户数量
     */
    @TableField("follows")
    private Integer follows;

    /**
     * 粉丝数量
     */
    @TableField("fans")
    private Integer fans;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 创建时的ip地址
     */
    @TableField("createIp")
    private String createIp;
    
    /**
     * 更新时间
     */
    @TableField("updateTime")
    private Date updateTime;

    /**
     * 最近一次登录的时间
     */
    @TableField("lastLoginTime")
    private Date lastLoginTime;

    /**
     * 最近一次登录的ip
     */
    @TableField("lastLoginIp")
    private String lastLoginIp;

    /**
     * 实名认证状态 - 未实名认证
     */
    public final static int REAL_STATUS_NO = 0;
    
    /**
     * 实名认证状态 - 已实名认证
     */
    public final static int REAL_STATUS_SUCCESS = 1;
    
    /**
     * 实名认证状态 - 实名认证失败
     */
    public final static int REAL_STATUS_FAILED = -1;
    
    /**
     *  性别 - 女
     */
    public final static int SEX_FEMALE = 0;
    
    /**
     *  性别 - 男
     */
    public final static int SEX_MALE = 1;
    
    /**
     *  性别 - 保密
     */
    public final static int SEX_SECRET = -1;

    /**
     * 是否激活 - 未激活
     */
    public final static int ACTIVE_NO = 0;
    
    /**
     * 是否激活 - 已激活
     */
    public final static int ACTIVE_YES = 1;
    
    /**
     * 账号状态 - 禁用
     */
    public final static int STATUS_NO = 0;
    
    /**
     * 账号状态 - 启用
     */
    public final static int STATUS_YES = 1;
    
    /**
     * 是否是管理员 - 不是
     */
    public final static int ADMIN_NO = 0;
    
    /**
     * 是否是管理员 - 普通管理
     */
    public final static int ADMIN_YES = 1;
    
    /**
     * 是否是管理员 - 超级管理员
     */
    public final static int ADMIN_YES_SUPER = 2;
}