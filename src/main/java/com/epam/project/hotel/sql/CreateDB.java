package com.epam.project.hotel.sql;

import com.epam.project.hotel.listener.AppListener;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

/**
 * Class that recreates db from selected file
 */
public class CreateDB {
    private static final Logger log = LogManager.getLogger(AppListener.class);

    private final static String PATH =
            "A:\\hotel\\java_src\\src\\main\\java\\com\\epam\\project\\hotel\\sql\\create-db.sql";

    public static void createDB() throws AppException {
        log.info("#createDB");
        Connection con;
        ScriptRunner sr;
        BufferedReader bf;
        File file;
        try {
            con = DataSource.getConnection();
            sr = new ScriptRunner(con);
            file = new File(PATH);
            bf = new BufferedReader(new FileReader(file));
            log.info("Started successfully");
        } catch (FileNotFoundException e) {
            log.error("Problem in createDB", e);
            throw new AppException("Problem with reading file");
        }
        sr.runScript(bf);
    }
}
