# Advisor User Guide

![Screenshot of GUI for advisor chatbot with a introductory message, list command is used and advisor returns a 
message showing all the tasks](Ui.png)

> "I wish someone could help me keep track of my tasks 😭 " — you, probably

Advisor is here to help you do exactly that! It is:
- text-based
- easy to learn
- __very__ quick to use

To get started, simply
1. Download the latest version [here](https://github.com/reyestyh/ip/releases)
2. Double click the program
3. Add your tasks

## Features

### Tasks

__todo__

This command adds a todo task with no fixed deadline or time.

Usage: `todo <task description>`

Example: `todo Buy Groceries`

Expected output:
```
The following task has been added:
    [T][ ] Buy groceries
There are now 1 tasks in the list.
```

__deadline__

This command adds a task with a deadline.

Usage: `deadline <task description> /by <deadline>`

Example: `deadline Complete homework /by 2026-03-04 1500`

Expected output:
```
The following task has been added:
    [D][ ] Complete homework (by: Mar 4 2026 3:00 pm)
There are now 2 tasks in the list.
```

__event__

This command adds an event with a specified start time and end time.

Usage: `event <task description> /from <start time> /to <end time>`

Example: `event Birthday Party /from 2026-04-05 1600 /to 2026-04-05 2100`

Expected output:
```
The following task has been added:
    [E][ ] Birthday Party (from: Apr 5 2026 4:00 pm || to: Apr 5 2026 9:00 pm)
There are now 3 tasks in the list.
```

__list__

This command lists all tasks with their description, completion status, and any other relevant information.

Usage: `list`

Example output:
```
Current tasks:
1.  [T][ ] Buy groceries
2.  [D][ ] Complete homework (by: Mar 4 2026 3:00 pm)
3.  [E][ ] Birthday Party (from: Apr 5 2026 4:00 pm || to: Apr 5 2026 9:00 pm)
```

### Editing tasks
__mark__

This command marks a task as complete, specified using its index.

Usage: `mark <index>`

Example: `mark 2`

Expected output:
```
The following task is now marked as done:
    [D][X] Complete homework (by: Mar 4 2026 3:00 pm)
```

__unmark__

This command marks a task as incomplete, specified using its index.

Usage: `unmark <index>`

Example: `unmark 2`

Expected output:
```
The following task is now marked as undone:
    [D][ ] Complete homework (by: Mar 4 2026 3:00 pm)
```

__delete__

This command deletes a stored task, specified using its index.

Usage: `delete <index>`

Example: `delete 2`

Expected output:
```
The following task has been removed:
    [D][ ] Complete homework (by: Mar 4 2026 3:00 pm)
Remaining tasks stored: 2
```

### Other task list operations 
__sort__

This command sorts tasks based on their type, then by its dates if it has a date.

<details>

<summary>Sorting order</summary> 

To-do tasks are listed first, followed by deadline tasks, and lastly events.
Within deadline tasks, tasks with an earlier deadline are listed first.
Within events, events which start earlier are listed first.
Each type of task or event is lastly sorted in alphabetical order.

</details>

Usage: `sort`

Example output:
```
Tasks sorted. 
1.  [T][ ] Buy clothes
2.  [T][ ] Buy groceries
3.  [T][X] Cook dinner
4.  [D][X] Homework (by: Mar 5 2025 4:00 pm)
5.  [D][ ] Application (by: Jan 30 2026 5:00 pm)
6.  [E][ ] Birthday (from: Jan 3 2026 12:00 pm || to: Jan 3 2026 6:00 pm)
7.  [E][ ] Meeting (from: Feb 27 2026 12:00 pm || to: Feb 27 2026 4:00 pm)
8.  [E][X] Wedding (from: Mar 1 2026 1:00 pm || to: Mar 1 2026 6:00 pm)
```


__find__

This command finds tasks matching a keyword, specified by user input.

Usage: `find <search term>`

Example: `find buy`

Expected output:
```
Matching tasks found for 'buy':
    [T][ ] Buy groceries
    [T][ ] Buy clothes
```

### Exit

The `bye` command can be used to exit the app.

Usage: `bye`

Expected output:
```
Data file successfully updated.
End of Session. Goodbye!
```
Closing the window also triggers the `bye` command.

