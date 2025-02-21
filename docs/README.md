# EunAI User Guide ✨✨✨

Welcome EunAI - your all in one task manager. EunAI isn’t just another reminders app or notebook but a powerful task manager so you can focus on what you enjoy without stressing over your to-do list. 

With a simple and easy to use chatbot interface, EunAI is suitable for users ages 12 and over. Here’s a quick guide to get started!

EunAI supports 3 different types of tasks: 
- To do
- Deadline
- Event

## Adding To Dos, Deadlines and Events

### Adding To Dos
```
todo revise for cs2103 exam
```
This adds "revise for cs2103 exam" as a new To Do in your list!

### Adding Deadlines
```
deadline submit cs2103 ip /by 21/02/2025 2359
```
This adds "submit cs2103 ip" as a new task with deadline of 21 February 2025.

### Adding Events
```
event watch movie with friends! /from 19/02/2025 1720 /to 19/02/2025 2000
```
This adds "watch movie with friends" as a new event from 5:20pm to 8.00pm on 19 February 2025.

## Managing Tasks (List, Mark, Unmark, Delete)

### View list of tasks
```
list
```
Displays the list of tasks with its index based on the chronological order it was added

### Mark a task as done
```
mark 1
```
Marks the task at index 1 as done

### Unmark a task
```
unmark 2
```
Marks the task at index 2 as not done 

### Delete a task
```
delete 3
```
Delete the task at index 3

## Find
```
find book
```
Finds all tasks with "book" in the description (E.g. read book)
```
find <todo>
```
Finds all To Dos
```
find <deadline>
```
Finds all Deadlines
```
find <event>
```
Finds all Events

## Others
### Ending the chat
```
bye
```
Ends chat and closes EunAI task manager. Tasks are then saved locally.

### Managing Multiple Tasks in One Command (Mark, Unmark, Delete)
```
delete 1, 3
```
Deletes tasks at index 1 and 3
```
mark 1, 3
```
Marks tasks at index 1 and 3 as done
```
unmark 1, 3
```
Marks tasks at index 1 and 3 as not done

## Supported Date Formats
EunAI supports multiple date formats! For example,  
"2 December 2019" can be written as:  

**📅 Slash Formats:**  
- `02/12/2019`  
- `02/12/2019 1800`  
- `2/12/2019`  
- `2/12/2019 1800`  
- `02/12/19 1800`  

**🗓️ Month Name Formats:**  
- `Dec 02 2019`  
- `Dec 02 2019 1800`  

**📆 ISO Formats:**  
- `2019-12-02`  
- `2019-12-02 18:00`  
- `2019-12-02T18:00`  
- `2025-02-01T00:00:00`


That's the features that EunAI has to offer FOR NOW! Look out for future updates where new features could be added 👀