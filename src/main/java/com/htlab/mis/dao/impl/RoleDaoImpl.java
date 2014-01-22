package com.htlab.mis.dao.impl;

import org.springframework.stereotype.Repository;

import com.htlab.mis.dao.RoleDao;
import com.htlab.mis.entity.Role;

@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role, Integer> implements RoleDao {}
