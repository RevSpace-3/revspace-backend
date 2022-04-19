package com.revature.revspace.models.groups;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.revspace.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@Entity
@Table(name = "groupthread")
public class GroupThread 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int groupId;
	
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="infoId")
	private GroupInfo groupInfo;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User member;
	
//	@ManyToMany
//	@JoinTable(name = "users", joinColumns = {@JoinColumn(name = "user_Id"), @JoinColumn(name = "userId")})
//	private List<User> memberList = new ArrayList<User>();
	
//	@OneToMany(mappedBy="userId", fetch=FetchType.LAZY)
//	//@JsonBackReference
//	private List<User> groupMembers = new ArrayList<User>();

}
