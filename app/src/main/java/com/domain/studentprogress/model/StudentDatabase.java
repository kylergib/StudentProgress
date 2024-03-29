package com.domain.studentprogress.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.domain.studentprogress.holder.AssessmentHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class StudentDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "student.db";
    private static StudentDatabase mStudentDb;

    private StudentDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static StudentDatabase getInstance(Context context) {
        if (mStudentDb == null) {
        mStudentDb = new StudentDatabase(context);
        }
        return mStudentDb;
    }


    private static final class TermTable {
        private static final String TABLE = "Terms";
        private static final String COL_ID = "_id";
        private static final String COL_NAME = "name";
        private static final String COL_START_DATE = "startDate";
        private static final String COL_END_DATE = "endDate";
    }

    private static final class CourseTable {
        private static final String TABLE = "Courses";
        private static final String COL_ID = "_id";
        private static final String COL_NAME = "name";
        private static final String COL_START_DATE = "startDate";
        private static final String COL_END_DATE = "endDate";
        private static final String COL_STATUS = "status";
        private static final String COL_TERM = "term";
    }


    private static final class AssessmentTable {
        private static final String TABLE = "assessments";
        private static final String COL_ID = "_id";
        private static final String COL_TYPE = "type";
        private static final String COL_TITLE = "title";
        private static final String COL_START_DATE = "startDate";
        private static final String COL_END_DATE = "endDate";
        private static final String COL_COURSE = "course";
    }

    private static final class InstructorTable {
        private static final String TABLE = "instructors";
        private static final String COL_ID = "_id";
        private static final String COL_NAME = "name";
        private static final String COL_NUMBER = "number";
        private static final String COL_EMAIL = "email";
        private static final String COL_COURSE = "course";
    }

    private static final class NoteTable {
        private static final String TABLE = "notes";
        private static final String COL_ID = "_id";
        private static final String COL_MESSAGE = "message";
        private static final String COL_COURSE = "course";
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("create table " + TermTable.TABLE + " (" +
                TermTable.COL_ID + " integer primary key autoincrement, " +
                TermTable.COL_NAME + ", " +
                TermTable.COL_START_DATE + ", " +
                TermTable.COL_END_DATE+ ")");

        sqLiteDatabase.execSQL("create table " + CourseTable.TABLE + " (" +
                CourseTable.COL_ID + " integer primary key autoincrement, " +
                CourseTable.COL_NAME + ", " +
                CourseTable.COL_START_DATE + ", " +
                CourseTable.COL_END_DATE + ", " +
                CourseTable.COL_STATUS + ", " +
                CourseTable.COL_TERM + " integer, " +
                "foreign key(" + CourseTable.COL_TERM + ") references " +
                TermTable.TABLE + "(" + TermTable.COL_ID + ") on delete cascade)");


        sqLiteDatabase.execSQL("create table " + AssessmentTable.TABLE + " (" +
                AssessmentTable.COL_ID + " integer primary key autoincrement, " +
                AssessmentTable.COL_TYPE + ", " +
                AssessmentTable.COL_TITLE + ", " +
                AssessmentTable.COL_START_DATE + ", " +
                AssessmentTable.COL_END_DATE + ", " +
                AssessmentTable.COL_COURSE + " integer, " +
                "foreign key(" + AssessmentTable.COL_COURSE + ") references " +
                CourseTable.TABLE + "(" + CourseTable.COL_ID + ") on delete cascade)");

        sqLiteDatabase.execSQL("create table " + InstructorTable.TABLE + " (" +
                InstructorTable.COL_ID + " integer primary key autoincrement, " +
                InstructorTable.COL_NAME + ", " +
                InstructorTable.COL_NUMBER + ", " +
                InstructorTable.COL_EMAIL + ", " +
                InstructorTable.COL_COURSE + " integer, " +
                "foreign key(" + InstructorTable.COL_COURSE + ") references " +
                CourseTable.TABLE + "(" + CourseTable.COL_ID + ") on delete cascade)");

        sqLiteDatabase.execSQL("create table " + NoteTable.TABLE + " (" +
                NoteTable.COL_ID + " integer primary key autoincrement, " +
                NoteTable.COL_MESSAGE + ", " +
                NoteTable.COL_COURSE + " integer, " +
                "foreign key(" + NoteTable.COL_COURSE + ") references " +
                CourseTable.TABLE + "(" + CourseTable.COL_ID + ") on delete cascade)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                db.execSQL("pragma foreign_keys = on;");
            } else {
                db.setForeignKeyConstraintsEnabled(true);
            }
        }
    }

    public long addTerm(Term term) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TermTable.COL_NAME, term.getName());
        values.put(TermTable.COL_START_DATE, String.valueOf(term.getStartDate()));
        values.put(TermTable.COL_END_DATE, String.valueOf(term.getEndDate()));
        long termId = db.insert(TermTable.TABLE, null, values);
        return termId;
    }
    public long updateTerm(Term term) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TermTable.COL_NAME, term.getName());
        values.put(TermTable.COL_START_DATE, String.valueOf(term.getStartDate()));
        values.put(TermTable.COL_END_DATE, String.valueOf(term.getEndDate()));

        String whereClause = TermTable.COL_ID + " = ?";
        String[] whereArgs = { String.valueOf(term.getId()) };

        long termId = db.update(TermTable.TABLE, values,whereClause,whereArgs);
        return termId;
    }
    public List<Term> getTerms() {
        List<Term> terms = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + TermTable.TABLE + " order by " + TermTable.COL_START_DATE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                long termId = cursor.getLong(0);
                String termName = cursor.getString(1);
                LocalDate startDate = LocalDate.parse(cursor.getString(2));
                LocalDate endDate = LocalDate.parse(cursor.getString(3));
                Term term = new Term(termName,startDate,endDate,termId);
                terms.add(term);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return terms;
    }
    public Term getSingleTerm(long termId) {
        Term term = null;
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + TermTable.TABLE + " where " + TermTable.COL_ID + " = ?";
        Cursor cursor = db.rawQuery(sql,  new String[] {Long.toString(termId)});
        if (cursor.moveToFirst()) {
            do {
                String termName = cursor.getString(1);
                LocalDate startDate = LocalDate.parse(cursor.getString(2));
                LocalDate endDate = LocalDate.parse(cursor.getString(3));
                term = new Term(termName,startDate,endDate,termId);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return term;
    }
    public boolean deleteTerm(long termId) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete(TermTable.TABLE, TermTable.COL_ID + " = ?",
                new String[] {Long.toString(termId)});
        return rowsDeleted > 0;

    }

    public boolean deleteCourse(long courseId) {
        SQLiteDatabase db = getWritableDatabase();
        int rowsDeleted = db.delete(CourseTable.TABLE, CourseTable.COL_ID + " = ?",
                new String[] {Long.toString(courseId)});
        return rowsDeleted > 0;

    }
    public long addCourse(Course course) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CourseTable.COL_NAME, course.getCourseName());
        values.put(CourseTable.COL_START_DATE, course.getCourseStartDate().toString());
        values.put(CourseTable.COL_END_DATE, course.getCourseEndDate().toString());
        values.put(CourseTable.COL_STATUS, course.getStatus());
        values.put(CourseTable.COL_TERM, course.getTermID());

        long courseId = db.insert(CourseTable.TABLE, null, values);
        return courseId;
    }
    public long updateCourse(Course course) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CourseTable.COL_NAME, course.getCourseName());
        values.put(CourseTable.COL_START_DATE, course.getCourseStartDate().toString());
        values.put(CourseTable.COL_END_DATE, course.getCourseEndDate().toString());
        values.put(CourseTable.COL_STATUS, course.getStatus());
        values.put(CourseTable.COL_TERM, course.getTermID());

        String whereClause = CourseTable.COL_ID + " = ?";
        String[] whereArgs = { String.valueOf(course.getId()) };

        long courseId = db.update(CourseTable.TABLE, values,whereClause,whereArgs);
        return courseId;
    }
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + CourseTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Long courseID = cursor.getLong(0);
                String courseName = cursor.getString(1);
                LocalDate startDate = LocalDate.parse(cursor.getString(2));
                LocalDate endDate = LocalDate.parse(cursor.getString(3));
                String status = cursor.getString(4);
                Long courseTermId = cursor.getLong(5);

                Course course = new Course(courseName, startDate, endDate,status,
                        courseID, courseTermId);

                courses.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return courses;
    }
    public List<Course> getCoursesForTerm(long termId) {
        int termIdInt = 1;
        List<Course> courses = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();

        String sql = "select * from " + CourseTable.TABLE + " where " + CourseTable.COL_TERM + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {Long.toString(termId)});

        if (cursor.moveToFirst()) {
            do {
                Log.d("StudentDatabase", "found course");
                Long courseID = cursor.getLong(0);
                String courseName = cursor.getString(1);
                LocalDate startDate = LocalDate.parse(cursor.getString(2));
                LocalDate endDate = LocalDate.parse(cursor.getString(3));
                String status = cursor.getString(4);
                Long courseTermId = cursor.getLong(5);

                Course course = new Course(courseName, startDate, endDate,status,
                        courseID, courseTermId);

                courses.add(course);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return courses;
    }
    public long addInstructor(Instructor instructor) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InstructorTable.COL_NAME,instructor.getInstructorName());
        values.put(InstructorTable.COL_NUMBER,instructor.getInstructorPhoneNumber());
        values.put(InstructorTable.COL_EMAIL,instructor.getInstructorEmail());
        values.put(InstructorTable.COL_COURSE,instructor.getCourseID());

        long instructorId = db.insert(InstructorTable.TABLE, null, values);
        return instructorId;
    }
    public long updateInstructor(Instructor instructor) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(InstructorTable.COL_NAME,instructor.getInstructorName());
        values.put(InstructorTable.COL_NUMBER,instructor.getInstructorPhoneNumber());
        values.put(InstructorTable.COL_EMAIL,instructor.getInstructorEmail());
        values.put(InstructorTable.COL_COURSE,instructor.getCourseID());
        String whereClause = InstructorTable.COL_ID + " = ?";
        String[] whereArgs = { String.valueOf(instructor.getId()) };

        long instructorId = db.update(InstructorTable.TABLE, values,whereClause,whereArgs);
        return instructorId;
    }
    public List<Instructor> getInstructorsForCourse(long courseId) {
        List<Instructor> instructors = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();


        String sql = "select * from " + InstructorTable.TABLE + " where " + InstructorTable.COL_COURSE + " = ?";
        Log.d("StudentDatabase", sql);
        Cursor cursor = db.rawQuery(sql, new String[] {Long.toString(courseId)});

        if (cursor.moveToFirst()) {
            do {
                Long instructorId = cursor.getLong(0);
                String instructorName = cursor.getString(1);
                String instructorNumber = cursor.getString(2);
                String instructorEmail = cursor.getString(3);
                Long instructorCourseId = cursor.getLong(4);

                Instructor instructor = new Instructor(instructorId,instructorName,
                        instructorNumber,instructorEmail);
                instructor.setCourseID(instructorCourseId);

                instructors.add(instructor);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return instructors;
    }
    public long addAssessment(Assessment assessment) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AssessmentTable.COL_TYPE , assessment.getAssessmentType());
        values.put(AssessmentTable.COL_TITLE , assessment.getAssessmentTitle());
        values.put(AssessmentTable.COL_START_DATE , String.valueOf(assessment.getAssessmentStartDate()));
        values.put(AssessmentTable.COL_END_DATE , String.valueOf(assessment.getAssessmentEndDate()));
        values.put(AssessmentTable.COL_COURSE , assessment.getCourseID());

        long assessmentId = db.insert(AssessmentTable.TABLE, null, values);
        return assessmentId;
    }
    public long updateAssessment(Assessment assessment) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AssessmentTable.COL_TYPE , assessment.getAssessmentType());
        values.put(AssessmentTable.COL_TITLE , assessment.getAssessmentTitle());
        values.put(AssessmentTable.COL_START_DATE , String.valueOf(assessment.getAssessmentStartDate()));
        values.put(AssessmentTable.COL_END_DATE , String.valueOf(assessment.getAssessmentEndDate()));
        values.put(AssessmentTable.COL_COURSE , assessment.getCourseID());

        String whereClause = AssessmentTable.COL_ID + " = ?";
        String[] whereArgs = { String.valueOf(assessment.getAssessmentId()) };

        long assessmentId = db.update(AssessmentTable.TABLE, values,whereClause,whereArgs);
        return assessmentId;
    }
    public List<Assessment> getAllAssessments() {
        List<Assessment> assessments = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String sql = "select * from " + AssessmentTable.TABLE;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Long assessmentId = cursor.getLong(0);
                String assessmentType = cursor.getString(1);
                String assessmentTitle = cursor.getString(2);
                String assessmentStartDate = cursor.getString(3);
                String assessmentEndDate = cursor.getString(4);
                Long assessmentCourseId = cursor.getLong(5);
                Assessment assessment = new Assessment(assessmentId, assessmentType,
                        assessmentTitle, LocalDate.parse(assessmentStartDate),
                        LocalDate.parse(assessmentEndDate),assessmentCourseId);

                assessments.add(assessment);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return assessments;
    }

    public List<Assessment> getAssessmentsForCourse(long courseId) {
        List<Assessment> assessments = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();


        String sql = "select * from " + AssessmentTable.TABLE + " where " + AssessmentTable.COL_COURSE + " = ?";
        Cursor cursor = db.rawQuery(sql, new String[] {Long.toString(courseId)});

        if (cursor.moveToFirst()) {
            do {
                Long assessmentId = cursor.getLong(0);
                String assessmentType = cursor.getString(1);
                String assessmentTitle = cursor.getString(2);
                String assessmentStartDate = cursor.getString(3);
                String assessmentEndDate = cursor.getString(4);
                Long assessmentCourseId = cursor.getLong(5);

                Assessment assessment = new Assessment(assessmentId, assessmentType,
                        assessmentTitle, LocalDate.parse(assessmentStartDate),
                        LocalDate.parse(assessmentEndDate),assessmentCourseId);

                assessments.add(assessment);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return assessments;
    }

    public long addNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteTable.COL_MESSAGE , note.getMessage());
        values.put(NoteTable.COL_COURSE , note.getCourseID());

        long noteId = db.insert(NoteTable.TABLE, null, values);
        return noteId;
    }
    public long updateNote(Note note) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteTable.COL_MESSAGE , note.getMessage());
        values.put(NoteTable.COL_COURSE , note.getCourseID());
        String whereClause = NoteTable.COL_ID + " = ?";
        String[] whereArgs = { String.valueOf(note.getId()) };
        long noteId = db.update(NoteTable.TABLE, values,whereClause,whereArgs);
        return noteId;
    }

    public List<Note> getNotesForCourse(long courseId) {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();


        String sql = "select * from " + NoteTable.TABLE + " where " + NoteTable.COL_COURSE + " = ?";

        Cursor cursor = db.rawQuery(sql, new String[] {Long.toString(courseId)});

        if (cursor.moveToFirst()) {
            do {
                Long noteId = cursor.getLong(0);
                String noteMessage = cursor.getString(1);
                Long noteCourseId = cursor.getLong(2);

               Note note = new Note(noteMessage, noteId, noteCourseId);

                notes.add(note);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return notes;
    }
}
