package io.github.dunwu.module.cas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.dunwu.tool.data.validator.annotation.EditCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

/**
 * 部门
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("cas_dept")
@ApiModel(value = "Dept", description = "部门")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @NotNull(groups = EditCheck.class)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "上级部门ID")
    @TableField("`pid`")
    private Long pid;

    @ApiModelProperty(value = "子部门数目")
    @TableField("`sub_count`")
    private Integer subCount;

    @ApiModelProperty(value = "名称")
    @NotNull
    @TableField("`name`")
    private String name;

    @ApiModelProperty(value = "排序")
    @Range(min = 0, max = 999)
    @TableField("`sequence`")
    private Integer sequence;

    @ApiModelProperty(value = "状态")
    @TableField("`enabled`")
    private Boolean enabled;

    @ApiModelProperty(value = "备注")
    @TableField("`note`")
    private String note;

    @JsonIgnore
    @ApiModelProperty(value = "创建者")
    @TableField("`create_by`")
    private String createBy;

    @JsonIgnore
    @ApiModelProperty(value = "更新者")
    @TableField("`update_by`")
    private String updateBy;

    @JsonIgnore
    @ApiModelProperty(value = "创建时间")
    @TableField("`create_time`")
    private LocalDateTime createTime;

    @JsonIgnore
    @ApiModelProperty(value = "更新时间")
    @TableField("`update_time`")
    private LocalDateTime updateTime;

    public static final String ID = "id";
    public static final String PID = "pid";
    public static final String SUB_COUNT = "sub_count";
    public static final String NAME = "name";
    public static final String SEQUENCE = "sequence";
    public static final String ENABLED = "enabled";
    public static final String NOTE = "note";
    public static final String CREATE_BY = "create_by";
    public static final String UPDATE_BY = "update_by";
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";

}
