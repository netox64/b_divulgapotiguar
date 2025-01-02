package com.oficinadobrito.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringBootTest
@SpringJUnitConfig
@TestPropertySource(locations = "classpath:application.properties")
public class ApplicationPropertiesTests {
    @Value("${spring.application.name}")
    String applicationName;

    @Value("${spring.datasource.username}")
    String datasourceUsername;

    @Value("${spring.datasource.password}")
    String datasourcePassword;

    @Value("${spring.datasource.url}")
    String datasourceUrl;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    String hibernateDialect;

    @Value("${spring.jpa.database-platform}")
    String databasePlatform;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    String ddlAuto;

    @Value("${spring.jpa.hibernate.naming.physical-strategy}")
    String namingStrategy;

    @Value("${spring.jpa.show-sql}")
    boolean showSql;

    @Value("${spring.jpa.properties.hibernate.format_sql}")
    boolean formatSql;

    @Value("${spring.jpa.properties.hibernate.show_sql}")
    boolean hibernateShowSql;

    @Value("${spring.jpa.properties.hibernate.use_sql_comments}")
    boolean useSqlComments;

    @Value("${jwt.secret}")
    String jwtSecrets;

    @DisplayName("Test whether the properties required to connect to the database are present in the file ")
    @Test
    void testAplicationProperties(){
        Assertions.assertEquals(this.applicationName,"divulgapotiguar",() -> "error when entering the API name");
        Assertions.assertEquals(this.datasourceUsername.toUpperCase(),"root".toUpperCase(),() -> "the username not for the bank corresponds to ´root´");
        Assertions.assertEquals(this.datasourcePassword.toUpperCase(),"root".toUpperCase(),() -> "the password not for the bank corresponds to ´root´");
        Assertions.assertEquals(this.datasourceUrl,"jdbc:mysql://localhost:3306/api_potiguar?allowPublicKeyRetrieval=true&rewriteBatchedStatements=true&useSSL=false&useUnicode=yes&characterEncoding=UTF-8&useLegacyDatetimeCode=true&createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=UTC",() -> "the database connection does not correspond to a mysql connection or the database name is incorrect");
        Assertions.assertEquals(this.hibernateDialect,"org.hibernate.dialect.MySQL8Dialect",() -> "the bank dialect configuration does not match the mysql dialect");
        Assertions.assertEquals(this.databasePlatform,"org.hibernate.dialect.MySQL8Dialect",() -> "plataform error");
    }

    @DisplayName("Test whether the hibernate config required to connect to the database are present in the file ")
    @Test
    void testHibernateProperties(){
        Assertions.assertEquals(this.ddlAuto,"create",() -> "auto ddl not is create");
        Assertions.assertEquals(this.namingStrategy,"org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl",() -> "A strategy required ");
        Assertions.assertFalse(this.showSql,() -> "show sql not is false");
        Assertions.assertTrue(this.formatSql,() -> "format not is sql");
        Assertions.assertFalse(this.hibernateShowSql,() -> "show sql not is false");
        Assertions.assertTrue(this.useSqlComments,() -> "sql comment not is true");
        Assertions.assertNotNull(this.jwtSecrets,() -> "jwt secrets not is present");
    }
}
