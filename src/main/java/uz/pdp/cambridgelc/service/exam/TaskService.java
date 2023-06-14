package uz.pdp.cambridgelc.service.exam;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import uz.pdp.cambridgelc.entity.dto.TaskDto;
import uz.pdp.cambridgelc.entity.exam.ExamEntity;
import uz.pdp.cambridgelc.entity.exam.TaskDifficulty;
import uz.pdp.cambridgelc.entity.exam.TaskEntity;
import uz.pdp.cambridgelc.entity.user.UserEntity;
import uz.pdp.cambridgelc.exceptions.DataNotFoundException;
import uz.pdp.cambridgelc.exceptions.RequestValidationException;
import uz.pdp.cambridgelc.repository.ExamRepository;
import uz.pdp.cambridgelc.repository.TaskRepository;
import uz.pdp.cambridgelc.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final ExamRepository examRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public TaskEntity save(TaskDto taskDto, UUID examId, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        ExamEntity examEntity = examRepository.findById(examId).orElseThrow(
                () -> new DataNotFoundException("Exam not found!")
        );
        TaskEntity map = modelMapper.map(taskDto, TaskEntity.class);
        try{
            map.setDifficulty(TaskDifficulty.valueOf(taskDto.getDifficulty()));
        }catch (Exception e) {
            throw new DataNotFoundException("Task difficulty not found!");
        }
        List<TaskEntity> tasks = examEntity.getTasks();
        tasks.add(map);
        examEntity.setTasks(tasks);
        ExamEntity save = examRepository.save(examEntity);
        TaskEntity taskEntity = taskRepository.findTaskEntityByTitle(map.getTitle()).orElseThrow(
                () -> new DataNotFoundException("Task not added!")
        );
        return save.getTasks().get(save.getTasks().indexOf(taskEntity));
    }

    public Boolean check(UUID taskId, String answer, Principal principal,UUID examId) {
        ExamEntity examEntity = examRepository.findById(examId).orElseThrow(
                () -> new DataNotFoundException("Exam not found!")
        );
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(
                () -> new DataNotFoundException("Task not found!")
        );
        UserEntity userEntity = userRepository.findUserEntityByUsername(principal.getName()).orElseThrow(
                () -> new DataNotFoundException("User not found!")
        );
        if(!examEntity.getStarted()) {
            throw new DataNotFoundException("Exam not started!");
        }
        if(Objects.equals(answer,taskEntity.getAnswer())) {
            userEntity.setCredits(userEntity.getCredits()+taskEntity.getReward());
            return true;
        }
        return false;
    }
    public TaskEntity assign(UUID taskId,UUID examId) {
        TaskEntity taskEntity = taskRepository.findById(taskId).orElseThrow(
                () -> new DataNotFoundException("Task not found!")
        );
        ExamEntity examEntity = examRepository.findById(examId).orElseThrow(
                () -> new DataNotFoundException("Exam not found!")
        );
        List<TaskEntity> tasks = examEntity.getTasks();
        tasks.add(taskEntity);
        examEntity.setTasks(tasks);
        return taskEntity;
    }
    public void delete(UUID taskId) {
        TaskEntity task = taskRepository.findById(taskId).orElseThrow(
                () -> new DataNotFoundException("task not found")
        );
        List<ExamEntity> all = examRepository.findAll();
        for(ExamEntity exam:all) {
            List<TaskEntity> tasks = exam.getTasks();
            tasks.remove(task);
            exam.setTasks(tasks);
            examRepository.save(exam);
        }
        taskRepository.deleteTaskEntityById(taskId);
    }
}
