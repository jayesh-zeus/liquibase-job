job() {
    steps {
        liquibaseUpdate {
          changeLogFile('/app/src/auth/db.changelog-master.yml')
          url('jdbc:mysql://127.0.0.1:3306/arjuna_auth')
          //driverClassname('org.postgresql.Driver')
          // instead of driverClassname, you can set databaseEngine to MySQL, Derby, Postgres, Derby, or Hypersonic
          databaseEngine('MySQL')
          credentialsId('local-db')
          // liquibasePropertiesPath('/etc/liquibase.properties')
          // contexts('staging')  // can be comma delimited list
          // changelog parameters are supplied as a map of key/value pairs
      }
    } 
}
