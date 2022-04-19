package com.revature.revspace.models.groups;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "grouplike")
public class GroupLike 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int likeId;
	
	@OneToOne
	@JoinColumn(name="userId")
	private User user;
	
	@OneToOne
	@JoinColumn(name="postId")
	private GroupPost post;
}
