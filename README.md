# School System

This is a practice project development with Java 11 and SpringBoot 2.7.11. The purpose of this project is practicing the backend and software design skills.

System to manage a school with class rooms, students, teachers, subjects and qualifications.
System to manage a school with **classrooms**, **students**, **teachers**, **subjects** and notes. This project focuses only in the logic of *create activities and exams* by the teacher and *submit a response* by the students, something like an ACTIVITIES PLATFORM.

## Scope.

*At this moment the API focuses only on activities and exams, submit the response of the students and assign a note by the teachers. This project doesn’t record the notes history of the students or automatically define the main note of the student.*

## Tech Stack
- Java 11
- Spring Boot 2.7
- Spring JPA
  - SQL / Postgresql
- Spring Security
  - JWT 

## Requirements.

### Logic.

1. A school has **classrooms**, **teachers**, **students** and **subjects**.
2. In a classroom there are classes taught by a **teacher**, with *one specific **subject***, with *two or more **students***.
3. All the classroom information will be saved on a **Record**, where the record of the *students of the class*, the **activities** and **exams** assigned by the teacher will be stored.
4. All the students of a class can *submit* their activities *content* and *answer* all the exams.
5. The teacher *assigns notes* to all the activities submitted and exams answered by the students.

### Functionality

1. Manager, Teachers and students will have their own credentials to sing-in in the web application.
2. The different roles will have different permissions to make different things in the same web application.
3. The manager creates teachers, students, subjects and assign them to a classroom.
4. The teacher creates activities, exams and assign note to each student. Teachers can watch the classroom which their teach, all the deliveries of the activities and all the answers of the exams.
5. The students can access to the different classroom where they are. They can check the activities and exams assigned in the class and submit a response for each one.

# Branches to implement.

## Login

Works with all the login logic for the three entities: Manager, Teacher and Student lead by a User entity which contanins the login credentials for each entity.

## Subject

Functionality to manage the subjects available in the school. Each subject can be
added to a classroom and can be taken by students and teachers.

## Classroom.
Classroom branch to implement all the individual modules which work in a classroom, such as
**Class**, **Teacher**, **Student**, **Exam** and **Activity**.
Each entity perform its own activities.
### Teacher.
- Assign tasks to students.
- Assign exams to students.
- Assign notes to activities and exams.
### Student.
- Submit activities.
- Respond exams.
- Check their notes per activitie.
- Check their notes per exmas. 
### Class
- Has one teacher.
- Has two or more students.
- Has a subject.
### Exam.
Only works with the note of the exam, not the content itself.
- Has a subject specified.
- Has a teacher and a student.
- Has a note.
### Activity.
Only works with the note of the activity, not the content itself.
- Has a subject specified.
- Has a teacher and a student.
- Has a note.
- Has comments by the teacher.




