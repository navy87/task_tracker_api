package com.yahya.task.tracker.tasktracker.restController;

import com.yahya.task.tracker.tasktracker.model.Task;
import com.yahya.task.tracker.tasktracker.model.TaskPerson;
import com.yahya.task.tracker.tasktracker.model.Track;
import com.yahya.task.tracker.tasktracker.service.TaskPersonService;
import com.yahya.task.tracker.tasktracker.service.TaskService;
import com.yahya.task.tracker.tasktracker.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/task")
@CrossOrigin("*")
public class TaskController implements BasicRestControllerSkeleton<Task> {

    private final TaskService taskService;
    private final TrackService trackService;

    @Autowired
    public TaskController(TaskService taskService, TrackService trackService) {
        this.taskService = taskService;
        this.trackService = trackService;
    }

    @Override
    @GetMapping("")
    public List<Task> getAll() {
        return taskService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    public Task get(@PathVariable Integer id) {
        return taskService.findById(id);
    }

    @Override
    @PostMapping("")
    public Task add(@RequestBody Task item) {
        item.setId(0);
        return taskService.saveNew(item);
    }

    @Override
    @PutMapping("/{id}")
    public Task update(@PathVariable Integer id, @RequestBody Task updatedItem) {
        updatedItem.setId(id);

//        Task oldItem = taskService.findById(id);
//        oldItem.getAssignees().forEach(updatedItem::addAssignee);
//        oldItem.getTracks().forEach(updatedItem::addTrack);

        return taskService.save(updatedItem);
    }

    @Override
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        taskService.deleteById(id);
    }

//    Custom Controllers

    //    Task Person
    @GetMapping("/{id}/persons")
    public Set<TaskPerson> getAssignees(@PathVariable Integer id) {
        return taskService.findPersonByTaskId(id);
    }

    @PostMapping("/{id}/persons")
    public TaskPerson getAssignees(@PathVariable Integer id, @RequestBody TaskPerson taskPerson) {
//        Task task = taskService.findById(id);
//        taskPerson.setTask(task);
//        return taskPersonService.save(taskPerson);
        return taskService.saveTaskPerson(id, taskPerson);
    }

    @PutMapping("/{task_id}/persons/{taskPerson_id}")
    public TaskPerson updateAssignee(@PathVariable Integer task_id,
                                     @PathVariable Integer taskPerson_id,
                                     @RequestBody TaskPerson updatedTaskPerson) {
        updatedTaskPerson.setId(taskPerson_id);
        return taskService.saveTaskPerson(task_id, updatedTaskPerson);
    }

    @DeleteMapping("/{task_id}/persons/{taskPerson_id}")
    public void removeAssignee(@PathVariable Integer task_id, @PathVariable Integer taskPerson_id) {
        taskService.deleteTaskPersonById(taskPerson_id);
    }


//    Tracks Controller
    @GetMapping("/{id}/tracks")
    public Set<Track> getTracks(@PathVariable Integer id) {
        return taskService.findTracksByTaskId(id);
    }

    @PostMapping("/{id}/tracks")
    public Track addTrack(@PathVariable Integer id, @RequestBody Track track) {
        Task task = taskService.findById(id);
        track.setTask(task);
        return trackService.save(track);
    }

    @DeleteMapping("/{task_id}/tracks/{track_id}")
    public void deleteTask(@PathVariable Integer task_id, @PathVariable Integer track_id) {
        trackService.deleteById(track_id);
    }
}
