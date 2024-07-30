package com.youlai.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.youlai.system.common.result.PageResult;
import com.youlai.system.common.result.Result;
import com.youlai.system.model.form.ConfigForm;
import com.youlai.system.model.query.ConfigPageQuery;
import com.youlai.system.model.vo.ConfigVO;
import com.youlai.system.service.SysConfigService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


/**
 * 系统配置前端控制层
 *
 * @author Theo
 * @since 2024-07-30 11:25
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "系统配置")
@RequestMapping("/api/v1/config")
public class SysConfigController {

    private final SysConfigService sysConfigService;

    @GetMapping("/page")
    @Operation(summary = "系统配置分页列表")
    @PreAuthorize("@ss.hasPerm('sys:config:query')")
    public PageResult<ConfigVO> page(@ParameterObject ConfigPageQuery configPageQuery) {
        IPage<ConfigVO> result = sysConfigService.page(configPageQuery);
        return PageResult.success(result);
    }

    @Operation(summary = "新增系统配置")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:config:add')")
    public Result<?> save(@RequestBody @Valid ConfigForm configForm) {
        return Result.judge(sysConfigService.save(configForm));
    }

    @Operation(summary = "获取系统配置表单数据")
    @GetMapping("/{id}/form")
    public Result<ConfigForm> getConfigForm(
            @Parameter(description = "系统配置ID") @PathVariable Long id
    ) {
        ConfigForm formData = sysConfigService.getConfigFormData(id);
        return Result.success(formData);
    }

    @Operation(summary = "刷新系统配置缓存")
    @PatchMapping
    @PreAuthorize("@ss.hasPerm('sys:config:refresh')")
    public Result<ConfigForm> refreshCache() {
        return Result.judge(sysConfigService.refreshCache());
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "修改系统配置")
    @PreAuthorize("@ss.hasPerm('sys:config:update')")
    public Result<?> update(@Valid @PathVariable Long id, @RequestBody ConfigForm configForm) {
        return Result.judge(sysConfigService.edit(id, configForm));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除系统配置")
    @PreAuthorize("@ss.hasPerm('sys:config:delete')")
    public Result<?> delete(@PathVariable Long id) {
        return Result.judge(sysConfigService.delete(id));
    }

}
