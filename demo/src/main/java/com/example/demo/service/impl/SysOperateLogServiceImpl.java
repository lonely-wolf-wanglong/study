package com.example.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.entity.SysOperateLog;
import com.example.demo.mapper.SysOperateLogMapper;
import com.example.demo.service.ISysOperateLogService;
import org.springframework.stereotype.Service;

@Service
public class SysOperateLogServiceImpl extends ServiceImpl<SysOperateLogMapper, SysOperateLog> implements ISysOperateLogService {
}
