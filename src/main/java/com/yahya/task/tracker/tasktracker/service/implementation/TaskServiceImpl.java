package com.yahya.task.tracker.tasktracker.service.implementation;

import com.yahya.task.tracker.tasktracker.dao.TaskDao;
import com.yahya.task.tracker.tasktracker.model.Task;
import com.yahya.task.tracker.tasktracker.model.TaskPerson;
import com.yahya.task.tracker.tasktracker.model.Track;
import com.yahya.task.tracker.tasktracker.service.TaskPersonService;
import com.yahya.task.tracker.tasktracker.service.TaskService;
import com.yahya.task.tracker.tasktracker.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;
    private final TaskPersonService taskPersonService;
    private final TrackService trackService;

    @Autowired
    public TaskServiceImpl(TaskDao taskDao, TaskPersonService taskPersonService, TrackService trackService) {
        this.taskDao = taskDao;
        this.taskPersonService = taskPersonService;
        this.trackService = trackService;
    }

    @Override
    public Task findById(int id) {
        return taskDao.findById(id).orElseThrow();
    }

    @Override
    public Task save(Task item) {
        item = taskDao.save(item);
        final Task finalItem = item;
        item.getAssignees().forEach(taskPerson -> {
            taskPerson.setTask(finalItem);
            taskPersonService.save(taskPerson);
        });
        item.getTracks().forEach(track -> {
            track.setTask(finalItem);
            trackService.save(track);
        });
        return item;
    }

    @Override
    public Task saveNew(Task item) {
        Track initialTrack = new Track("Task Created", "Task has been created", LocalDate.now());
        item.addTrack(initialTrack);
        return save(item);
    }
    @Override
    public List<Task> findAll() {
        return taskDao.findAll();
    }

    @Override
    public boolean deleteById(Integer id) {
        taskDao.deleteById(id);
        return true;
    }

    @Override
    public Set<TaskPerson> findPersonByTask(Task task) {
        return task.getAssignees();
    }

    @Override
    public Set<TaskPerson> findPersonByTaskId(int taskId) {
        return findPersonByTask(findById(taskId));
    }

    @Override
    public Set<Track> findTracksByTask(Task task) {
        return task.getTracks();
    }

    @Override
    public Set<Track> findTracksByTaskId(int taskId) {
        return findTracksByTask(findById(taskId));
    }


}
