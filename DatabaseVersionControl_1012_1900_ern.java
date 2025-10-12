// 代码生成时间: 2025-10-12 19:00:55
package com.example.dbversion;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.Reader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DatabaseVersionControl is responsible for managing database version control.
 * It uses MyBatis to interact with the database and handle versioning.
 */
public class DatabaseVersionControl {

    private static final String MAPPER_XML = "VersionMapper.xml";
    private SqlSessionFactory sqlSessionFactory;
    private ConcurrentHashMap<String, AtomicInteger> versionMap;

    public DatabaseVersionControl(String resource) throws Exception {
        // Initialize the version map
        versionMap = new ConcurrentHashMap<>();

        // Build the SqlSessionFactory from the MyBatis configuration resource
        try (Reader reader = Resources.getResourceAsReader(resource)) {
            this.sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        }
    }

    /**
     * Update the database to the specified version.
     * @param version The version to update to.
     * @return A result message indicating success or failure.
     */
    public String updateDatabaseVersion(int version) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            VersionMapper mapper = session.getMapper(VersionMapper.class);
            int currentVersion = mapper.getCurrentVersion();

            if (currentVersion > version) {
                return "Update failed, cannot downgrade database from version " + currentVersion + " to version " + version;
            } else if (currentVersion == version) {
                return "Database is already at version " + version;
            } else {
                // Apply migrations up to the target version
                for (int i = currentVersion + 1; i <= version; i++) {
                    mapper.applyMigration(i);
                }
                session.commit();
                return "Database updated to version " + version;
            }
        } catch (Exception e) {
            // Rollback on error
            return "Failed to update database: " + e.getMessage();
        }
    }

    /**
     * Get the current database version.
     * @return The current database version.
     */
    public int getCurrentDatabaseVersion() {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            VersionMapper mapper = session.getMapper(VersionMapper.class);
            return mapper.getCurrentVersion();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get current database version", e);
        }
    }

    // Interface for the MyBatis mapper
    public interface VersionMapper {
        int getCurrentVersion();
        void applyMigration(int version);
    }
}
