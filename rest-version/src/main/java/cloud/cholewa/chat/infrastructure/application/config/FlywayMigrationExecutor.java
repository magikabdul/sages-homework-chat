package cloud.cholewa.chat.infrastructure.application.config;

import org.flywaydb.core.Flyway;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class FlywayMigrationExecutor {

    @Resource(lookup = "java:jboss/datasources/PostgresDS")
    private DataSource dataSource;

    @PostConstruct
    public void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .schemas("public")
                .load();

        try {
            flyway.migrate();
        } catch (IllegalStateException e) {
            flyway.baseline();
            flyway.migrate();
        }
    }
}
