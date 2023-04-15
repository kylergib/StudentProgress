package com.kgibs87.studentprogress.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

public class StudentDatabase extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "student.db";
    private static StudentDatabase mStudentDb;

    private StudentDatabase(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public enum SubjectSortOrder { ALPHABETIC, UPDATE_DESC, UPDATE_ASC }

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

        // Create terms table with foreign key that cascade deletes
        sqLiteDatabase.execSQL("create table " + TermTable.TABLE + " (" +
                TermTable.COL_ID + " integer primary key autoincrement, " +
                TermTable.COL_NAME + ", " +
                TermTable.COL_START_DATE + ", " +
                TermTable.COL_END_DATE+ ")");

        // Create courses table with foreign key that cascade deletes
        sqLiteDatabase.execSQL("create table " + CourseTable.TABLE + " (" +
                CourseTable.COL_ID + " integer primary key autoincrement, " +
                CourseTable.COL_NAME + ", " +
                CourseTable.COL_START_DATE + ", " +
                CourseTable.COL_END_DATE + ", " +
                CourseTable.COL_STATUS + ", " +
                CourseTable.COL_TERM + ", " +
                "foreign key(" +CourseTable.COL_TERM + ") references " +
                TermTable.TABLE + "(" + TermTable.COL_NAME + ") on delete cascade)");


        // Create assessments table with foreign key that cascade deletes
        sqLiteDatabase.execSQL("create table " + AssessmentTable.TABLE + " (" +
                AssessmentTable.COL_ID + " integer primary key autoincrement, " +
                AssessmentTable.COL_TYPE + ", " +
                AssessmentTable.COL_TITLE + ", " +
                AssessmentTable.COL_END_DATE + ", " +
                AssessmentTable.COL_COURSE + ", " +
                "foreign key(" + AssessmentTable.COL_COURSE + ") references " +
                CourseTable.TABLE + "(" + CourseTable.COL_NAME + ") on delete cascade)");

        // Create instructors table with foreign key that cascade deletes
        sqLiteDatabase.execSQL("create table " + InstructorTable.TABLE + " (" +
                InstructorTable.COL_ID + " integer primary key autoincrement, " +
                InstructorTable.COL_NAME + ", " +
                InstructorTable.COL_NUMBER + ", " +
                InstructorTable.COL_EMAIL + ", " +
                InstructorTable.COL_COURSE + ", " +
                "foreign key(" + InstructorTable.COL_COURSE + ") references " +
                CourseTable.TABLE + "(" + CourseTable.COL_NAME + ") on delete cascade)");

        // Create notes table with foreign key that cascade deletes
        sqLiteDatabase.execSQL("create table " + NoteTable.TABLE + " (" +
                NoteTable.COL_ID + " integer primary key autoincrement, " +
                NoteTable.COL_MESSAGE + ", " +
                NoteTable.COL_COURSE + ", " +
                "foreign key(" + NoteTable.COL_COURSE + ") references " +
                CourseTable.TABLE + "(" + CourseTable.COL_NAME + ") on delete cascade)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //todo: add all tables later, example below
//        db.execSQL("drop table if exists " + AssessmentTable.TABLE);
//        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                db.execSQL("pragma foreign_keys = on;");
            } else {
                db.setForeignKeyConstraintsEnabled(true);
            }
        }
    }


}
