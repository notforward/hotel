package com.epam.project.hotel.sql;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

public class CreateDB {

    private final static String PATH =
            "A:\\hotel\\java_src\\src\\main\\java\\com\\epam\\project\\hotel\\sql\\create-db.sql";

    public static void createDB() throws DBException {
        Connection con = DataSource.getConnection();
        ScriptRunner sr = new ScriptRunner(con);
        BufferedReader bf;
        File file;
        try {
            file = new File(PATH);
            bf = new BufferedReader(new FileReader(file));
            System.out.println("START SUCCESSFULLY");
        } catch (FileNotFoundException e) {
            throw new DBException("Problem with reading file", e);
        }
        sr.runScript(bf);
    }
}
