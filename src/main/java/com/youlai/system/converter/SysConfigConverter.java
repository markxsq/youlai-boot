package com.youlai.system.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.youlai.system.model.entity.SysConfig;
import com.youlai.system.model.form.ConfigForm;
import com.youlai.system.model.vo.ConfigVO;
import org.mapstruct.Mapper;

/**
 * 系统配置对象转换器
 *
 * @author Theo
 * @since 2024-7-29 11:42:49
 */
@Mapper(componentModel = "spring")
public interface SysConfigConverter {

    Page<ConfigVO> convertToPageVo(Page<SysConfig> page);

    SysConfig toEntity(ConfigForm configForm);

    ConfigForm toForm(SysConfig entity);
}
