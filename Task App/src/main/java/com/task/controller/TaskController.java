package com.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.task.dto.MessageDto;
import com.task.exception.InvalidIdException;
import com.task.model.Task;
import com.task.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@PostMapping("/add")
	public Task addTask(@RequestBody Task task)
	{
		return taskService.addTask(task);
	}
	
	@GetMapping("/retrieve/all")
	private List<Task> getAllTask()
	{
		return taskService.getAllTask();
	}
	
	@GetMapping("/retrieve/id/{taskId}")
	public ResponseEntity<?> getByTaskId(@PathVariable int taskId,MessageDto dto)
	{
		try {
			Task tasks = taskService.getByTaskId(taskId);
			return ResponseEntity.ok(tasks);
		} catch (InvalidIdException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
	}
	
	@PutMapping("/update/status/{taskId}")
	public ResponseEntity<?> updateStatus(@PathVariable int taskId,@RequestBody Task newTask,MessageDto dto)
	{
		try {
			Task tasks = taskService.updateStatus(taskId,newTask);
			return ResponseEntity.ok(tasks);
		} catch (InvalidIdException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}
	
	@DeleteMapping("/delete/id/{taskId}")
	public ResponseEntity<?> deleteTaskById(@PathVariable int taskId,MessageDto dto)
	{
		try {
			taskService.deleteTaskById(taskId);
			dto.setMsg("Successfully deleted");
			return ResponseEntity.ok(dto);
		} catch (InvalidIdException e) {
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
	}

}
