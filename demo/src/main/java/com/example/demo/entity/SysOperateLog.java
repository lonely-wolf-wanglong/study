package com.example.demo.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
@TableName("sys_operate_log")
@Schema(description = "操作日志")
public class SysOperateLog  extends BaseEntity{

    private static final long serialVersionUID = 1L;
    @Schema(description = "主键")
    private Long id;
    @Schema(description = "模块名称")
    private String moduleName;
    @Schema(description = "菜单名称")
    private String menuName;
    @Schema(description = "操作名称")
    private String operName;
    @Schema(description = "登录账号")
    private String userId;
    @Schema(description = "登录时间")
    private LocalDateTime operTime;
    @Schema(description = "IP地址")
    private String operIp;
    @Schema(description = "请求方式")
    private String requestMethod;
    @Schema(description = "请求方法")
    private String operMethod;
    @Schema(description = "请求参数")
    private String operParam;
    @Schema(description = "登录状态")
    private String operStatus;
    @Schema(description = "登录结果")
    private String operResult;
}
