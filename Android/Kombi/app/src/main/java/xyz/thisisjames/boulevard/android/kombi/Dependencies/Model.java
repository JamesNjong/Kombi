package xyz.thisisjames.boulevard.android.kombi.Dependencies;

public class Model {

    String taskName, taskDescription, taskType,taskRepeatType, taskDateString, taskStatus;

    Boolean taskRescheduled, taskIsHabit, taskDeleted;

    Long taskStartTime, taskEndTime, taskDate;


    public Model() {
    }

    public Model(String taskName, String taskDescription, String taskType, String taskRepeatType,
                 String taskDateString, String taskStatus, Boolean taskRescheduled, Boolean taskIsHabit,
                 Boolean taskDeleted, Long taskStartTime, Long taskEndTime, Long taskDate) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskType = taskType;
        this.taskRepeatType = taskRepeatType;
        this.taskDateString = taskDateString;
        this.taskStatus = taskStatus;
        this.taskRescheduled = taskRescheduled;
        this.taskIsHabit = taskIsHabit;
        this.taskDeleted = taskDeleted;
        this.taskStartTime = taskStartTime;
        this.taskEndTime = taskEndTime;
        this.taskDate = taskDate;
    }


    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskRepeatType() {
        return taskRepeatType;
    }

    public void setTaskRepeatType(String taskRepeatType) {
        this.taskRepeatType = taskRepeatType;
    }

    public String getTaskDateString() {
        return taskDateString;
    }

    public void setTaskDateString(String taskDateString) {
        this.taskDateString = taskDateString;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Boolean getTaskRescheduled() {
        return taskRescheduled;
    }

    public void setTaskRescheduled(Boolean taskRescheduled) {
        this.taskRescheduled = taskRescheduled;
    }

    public Boolean getTaskIsHabit() {
        return taskIsHabit;
    }

    public void setTaskIsHabit(Boolean taskIsHabit) {
        this.taskIsHabit = taskIsHabit;
    }

    public Boolean getTaskDeleted() {
        return taskDeleted;
    }

    public void setTaskDeleted(Boolean taskDeleted) {
        this.taskDeleted = taskDeleted;
    }

    public Long getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Long taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Long getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Long taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public Long getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Long taskDate) {
        this.taskDate = taskDate;
    }


}
