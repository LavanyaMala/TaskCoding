package com.task.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.task.exception.InvalidIdException;
import com.task.model.Task;
import com.task.model.UserInfo;
import com.task.repo.TaskRepository;
import com.task.repo.UserRepository;

@Service
public class TaskService {
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired 
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;

	public Task addTask(Task task) {
		
		UserInfo user = task.getUser();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole("ROLE_EMPLOYEE");
		user = userRepository.save(user);
		
		task.setUser(user);
		return taskRepository.save(task);
		
	}

	public List<Task> getAllTask() {
		return taskRepository.findAll();
		
	}

	public Task getByTaskId(int taskId) throws InvalidIdException {
		Optional<Task> optional = taskRepository.findById(taskId);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("Invalid Task Id");
			
			
		}
		return optional.get();
		
	}

	public Task updateStatus(int taskId, Task newTask) throws InvalidIdException {
		Optional<Task> optional = taskRepository.findById(taskId);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("Invalid Task Id");
			
			
		}
		
		Task taskDB = optional.get();
		taskDB.setStatus(newTask.getStatus());
		
		return taskRepository.save(taskDB);
	}

	public void deleteTaskById(int taskId) throws InvalidIdException {
		Optional<Task> optional = taskRepository.findById(taskId);
		if(optional.isEmpty())
		{
			throw new InvalidIdException("Invalid Task Id");
			
			
		}
		taskRepository.deleteById(taskId);
		
	}
	
	

}
