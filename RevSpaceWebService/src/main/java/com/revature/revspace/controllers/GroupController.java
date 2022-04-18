package com.revature.revspace.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.revspace.models.GroupInfo;
import com.revature.revspace.models.GroupPost;
import com.revature.revspace.models.GroupThread;
import com.revature.revspace.models.User;
import com.revature.revspace.services.GroupInfoService;
import com.revature.revspace.services.GroupPostService;
import com.revature.revspace.services.GroupThreadService;

@RestController
@CrossOrigin(origins = "http//localhost:4200")
@RequestMapping("/groups")
public class GroupController 
{
	@Autowired
	private GroupThreadService service;
	
	@Autowired
	private GroupInfoService iService;
	
	@Autowired 
	private GroupPostService pService;
	
	
	@PostMapping
	public ResponseEntity<String> addGroupThread(@RequestBody GroupThread obj)
	{
		ResponseEntity<String> res = null;
		
		if(obj == null)
			res = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<String>( "Adding GroupThread in GroupController |\nResult ->" + service.addGroupThread(obj), HttpStatus.OK); 
		
		return res;
	}
	@DeleteMapping("/Delete/Thread{threadId}")
	public ResponseEntity<String> deleteGroupThread(@PathVariable("threadId") int id)
	{
		ResponseEntity<String> res = null;
		
		if(id <= 0)
			res = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		else
		{
			boolean flag = service.deleteGroupThread(id);
			res = new ResponseEntity<String>("GroupThread " + id + (flag ? " was deleted" : " was not deleted"), HttpStatus.OK);
		}
		
		return res;
	}
	
	@GetMapping("/GetAll")
	public ResponseEntity<List<GroupThread>> getAllGroupThreads()
	{
		ResponseEntity<List<GroupThread>> res = null;
		List<GroupThread> gList = service.getAllGroupThreads();
		
		if(gList == null)
			res = new ResponseEntity<List<GroupThread>>(HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<List<GroupThread>>(gList, HttpStatus.OK); 
		
		return res;
	}
	
	@GetMapping("/ByOwner")
	public ResponseEntity<List<GroupThread>> getGroupThreadsByOwner(@RequestBody User owner)
	{
		ResponseEntity<List<GroupThread>> res = null;
		List<GroupThread> gList = service.getGroupThreadsByOwner(owner);
		
		if(gList == null)
			res = new ResponseEntity<List<GroupThread>>(HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<List<GroupThread>>(gList, HttpStatus.OK); 
		
		return res;
	}
	
	@GetMapping("/ByMembership/{id}")
	public ResponseEntity<List<GroupInfo>> getGroupThreadsByUser(@PathVariable("id") int id)
	{
		ResponseEntity<List<GroupInfo>> res = null;
		List<GroupInfo> gList = service.getGroupThreadsByUser(id);
		
		if(gList == null)
			res = new ResponseEntity<List<GroupInfo>>(HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<List<GroupInfo>>(gList, HttpStatus.OK); 
		
		return res;
	}
	
	/*************************************************************************************************************/
	// Threads
	
	@GetMapping("/ByUnique")
	public ResponseEntity<List<GroupThread>> getUniqueGroupThreads()
	{
		ResponseEntity<List<GroupThread>> res = null;
		List<GroupThread> gList = service.getAllUniqueThreads();
		
		if(gList == null)
			res = new ResponseEntity<List<GroupThread>>(HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<List<GroupThread>>(gList, HttpStatus.OK); 
		
		return res;
	}

	@GetMapping("/GetThreads/{infoId}")
	public ResponseEntity<List<GroupThread>> getThreadsByInfo(@PathVariable("infoId") int id)
	{
		ResponseEntity<List<GroupThread>> res = null;
		List<GroupThread> gList = service.getGroupThreadsByInfo(id);
		
		if(gList == null)
			res = new ResponseEntity<List<GroupThread>>(HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<List<GroupThread>>(gList, HttpStatus.OK); 
		
		return res;
	}
	
	@GetMapping("/GetAllChildren/{groupId}")
	public ResponseEntity<List<GroupThread>> getAllChildren(@PathVariable("groupId") int id)
	{
		ResponseEntity<List<GroupThread>> res = null;
		List<GroupThread> gList = service.getGroupThreadsByInfo(id);
		
		if(gList == null)
			res = new ResponseEntity<List<GroupThread>>(HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<List<GroupThread>>(gList, HttpStatus.OK); 
		
		return res;
	}

	/*************************************************************************************************************/
	// Group Info
	
	@DeleteMapping("/Delete/{id}")
	public ResponseEntity<String> deleteGroupInfo(@PathVariable("id") int id) // Always returns false
	{
		ResponseEntity<String> res = null;
		
		// Delete the user threads to the group /
		boolean f = service.deleteAllByInfoId(id);
		
		// Delete the actual group
		boolean flag = iService.deleteGroupById(id);
		
		if(id <= 0 )
			res = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<String>("Deleted Object " + id + " was " + (flag ? "successful" : "unsuccessful"), HttpStatus.OK);
		
		return res;
	}
	
	@GetMapping("/GetOthers/{id}")
	public ResponseEntity<List<GroupInfo>> getOthers(@PathVariable("id") int id)
	{
		ResponseEntity<List<GroupInfo>> res = null;
		List<GroupInfo> gList = service.getOtherGroups(id);
		
		if(gList == null)
			res = new ResponseEntity<List<GroupInfo>>(HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<List<GroupInfo>>(gList, HttpStatus.OK); 
		
		return res;
	}
	@GetMapping("/{interestString}")
	public ResponseEntity<List<GroupInfo>> getGroupsByInterest(@PathVariable("interestString") String input)
	{
		ResponseEntity<List<GroupInfo>> res = null;
		List<GroupInfo> gList = iService.getGroupsByInterests(input);
		
		if(gList == null)
			res = new ResponseEntity<List<GroupInfo>>(HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<List<GroupInfo>>(gList, HttpStatus.OK); 
		
		return res;
	}
	
	/*************************************************************************************************************/
	// Group Posts, this really should be its own controller. But I've had to redo this like 3 times. So its here.
	@PostMapping("/GroupPosts/Add")
	public ResponseEntity<GroupPost> addGroupPost(@RequestBody GroupPost post)
	{
		ResponseEntity<GroupPost> res = null;
		
		if(post == null)
			res = new ResponseEntity<GroupPost>(HttpStatus.NO_CONTENT);
		else
		{
//			GroupPost object = pService.unboxMsg(post);
//			GroupPost resAdd = pService.addGroupPost(object);
//			GroupPostMsg response = new GroupPostMsg(resAdd); // I can do this on a single line this is just so you understand what is going on...
			
			res = new ResponseEntity<GroupPost>(pService.addGroupPost(post), HttpStatus.OK);
		}
		
		return res;
	}
	@DeleteMapping("/GroupPosts/Delete/{id}")
	public ResponseEntity<String> deleteGroupPost(@PathVariable("id") int id)
	{
		ResponseEntity<String> res = null;
		
		if(id <= 0)
			res = new ResponseEntity<String>(HttpStatus.NO_CONTENT);
		else
		{
			boolean flag = pService.deleteGroupPost(id);
			res = new ResponseEntity<String>(
				"Result of deletePost on id: " + id + " was " + (flag ? "successful" : "unsuccessful"), HttpStatus.OK);
		}
		
		return res;
	}
	
	@GetMapping("/GroupPosts/{postHeadId}")
	public ResponseEntity<List<List<GroupPost>>> getGroupPosts(@PathVariable("postHeadId") int id)
	{
		ResponseEntity<List<List<GroupPost>>> res = null;
		List<List<GroupPost>> resList = pService.getGroupPosts(id);
		
		if(resList != null && !resList.isEmpty())
		{
			for(List<GroupPost> list : resList)
			{
				Collections.sort(list, (obj1, obj2) -> { return obj2.getPostId() - obj1.getPostId(); });
			}
		}
		
		if(id <= 0)
			res = new ResponseEntity<List<List<GroupPost>>> (HttpStatus.NO_CONTENT);
		else
			res = new ResponseEntity<List<List<GroupPost>>> (resList, HttpStatus.OK);
		
		return res;
	}
}
