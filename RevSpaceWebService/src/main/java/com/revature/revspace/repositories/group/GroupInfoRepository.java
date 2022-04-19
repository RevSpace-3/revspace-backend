package com.revature.revspace.repositories.group;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.revature.revspace.models.User;
import com.revature.revspace.models.groups.GroupInfo;

public interface GroupInfoRepository extends CrudRepository<GroupInfo, Integer>
{

}
