# School System

This is a practice project development with Java 11 and SpringBoot 2.7.11. The purpose of this project is practice the backend and software design skills.

System to manage a school with class rooms, students, teachers, subjects and qualifications.

## Scope.

****At the moment, the project is only responsible for assigning grades to students by the teachers in a specific subject****

## Requirements.

### Logic.

1. A school has classrooms, teachers, students and subjects.
2. In a classroom there are classes taught by a teacher, with a one specific subject, with two or more students.
3. The students will have a grade per subject, assigned by the teacher.
4. The students has a grade history where are stored all the subjects taken by the student.
5. The teacher asign grades per subject to all the students.

### Functionality

1. Administrator, Teachers and students will have their own credentials to sing in in the web application.
2. The different roles will have different permissions to make different things in the same web application.
3. The administrator creates teachers, students, subjects and asign them to a classroom.
4. The teacher asign grades per subject to all the students.
5. The students can check their grades history with all the taken subjects.

# Branches to implement.

## Login

Works with all the login logic for the three entities: Manager, Teacher and Student.

## Subject

Functionality to manage the subjects available in the school. Each subject can be
added to a classroom and can be taken by students and teachers.

## Teacher.
Teacher module to perform all the teacher activities
- Assign notes per subject.
- Assign tasks to students.
- Assign exams to students.



