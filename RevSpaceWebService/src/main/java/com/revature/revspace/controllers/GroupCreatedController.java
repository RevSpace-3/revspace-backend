package com.revature.revspace.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.revspace.models.GroupInfo;
import com.revature.revspace.models.GroupMembership;
import com.revature.revspace.models.GroupInfo;
import com.revature.revspace.services.GroupService;

@RestController
@CrossOrigin(origins = "http//localhost:4200")
@RequestMapping("/groupcreated")

public class GroupCreatedController {

	@Autowired
	private GroupService groupService;
	
	
	
	
	/************************************************************************************************************/
	// Posts
	
	@PostMapping
	public ResponseEntity<String> addGroup(@RequestBody GroupMembership object)
	{
		ResponseEntity<String> res = null;
		
		if(object== null)
			res = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<String>( "Creating New Group in Our Platform |\nResult ->" + groupService.addGroup(object) , HttpStatus.OK); 
		
		return res;
	}
	
	
	/************************************************************************************************************/
	// Puts	
	
	@PutMapping("{groupId}")
	public ResponseEntity<String> updateGroup(@PathVariable ("groupId") int id , @RequestBody GroupMembership object)
	{

		ResponseEntity<String> res = null;
		if(id <= 0 || object == null)
			res = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<String>( "Updating GroupInfo  with new member |\nResult ->" + groupService.updateGroup(id, object) , HttpStatus.OK); 
		
		return res;
	}
	
	/************************************************************************************************************/
	// Deletes
	
	@DeleteMapping
	public ResponseEntity<String> deleteGroup(@PathVariable("groupId") int id,  @RequestBody  GroupMembership object)
	{
		ResponseEntity<String> res = null;
		
		if(id <= 0)
			res = new ResponseEntity<String>(HttpStatus.FORBIDDEN);
		else
			res = new ResponseEntity<String>( "Deleting GroupInfo in GroupCreatedController | GroupInfo -> " + object
					+ " |\nResult -> " + groupService.deleteGroup(), HttpStatus.OK);
		
		return res;
	}


	


/************************************************************************************************************/
// Gets

	
	@GetMapping
	public ResponseEntity<List<GroupMembership>> getGroups()
	{
		ResponseEntity<List<GroupMembership>> res = null;
		
		List<GroupMembership> groupList = groupService.getGroups();
		
		if(groupList == null)
			res = new ResponseEntity<List<GroupMembership >>(HttpStatus.NO_CONTENT);
		else if(groupList.isEmpty())
			res = new ResponseEntity<List<GroupMembership >>(HttpStatus.NOT_FOUND);
		else
			res = new ResponseEntity<List<GroupMembership>>(groupList, HttpStatus.OK);
		
		return res;
	}

	
	
	
	
	
	
	
@GetMapping("{groupId}")
public ResponseEntity<GroupInfo> getGroupById(@PathVariable("groupId") int id)
{
	ResponseEntity<GroupInfo> res = null;

	if(id <= 0)
		res = new ResponseEntity<GroupInfo>(HttpStatus.NO_CONTENT);
	else
		res = new ResponseEntity<GroupInfo>(groupService.getGroup(id), HttpStatus.OK);
	
	return res;
}


//List<GroupInfo> getGroup(GroupInfo group )
//public List<Doctor> getDoctoryBySpecialty(String spec); //Doctor.SpecialtyType type);
//public List<GroupInfo> getDoctoryByType(String type);
@GetMapping("/GetGroupByType/{specialty} ")
public ResponseEntity<List<GroupMembership>> getGroupByType(@PathVariable("type") String interests )//group by type
{
	ResponseEntity<List<GroupMembership>> res = null;
	List<GroupMembership> groupList = groupService.getInterests(interests);
	List<GroupMembership> result = new ArrayList<GroupMembership>();

	for(GroupMembership g : groupList)
	{
		List<GroupMembership> temp = groupService.getGroups();
		
		for(GroupMembership i : temp)
		{
			if(i.getGroupInfo().getInterests() != null)
			{
				result.add(g);
				break;
			}
		}
	}
	
	
	
	if(result == null)
		res = new ResponseEntity<List<GroupMembership>>(HttpStatus.NO_CONTENT);
	else
		res = new ResponseEntity<List<GroupMembership>>(result, HttpStatus.OK);
		
	return res;
}



	
	
}
