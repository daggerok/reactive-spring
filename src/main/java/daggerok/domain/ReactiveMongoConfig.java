package daggerok.domain;

import com.mongodb.ConnectionString;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.connection.ServerSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import static java.lang.String.format;

@Slf4j
@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = MessageRepository.class)
public class ReactiveMongoConfig extends AbstractReactiveMongoConfiguration {

  @Value("${spring.data.mongodb.host}") String host;
  @Value("${spring.data.mongodb.port}") Integer port;

  @Override
  public MongoClient mongoClient() {

    val connectionString = new ConnectionString(format("mongodb://%s:%d", host, port));
    return MongoClients.create(connectionString);
  }

  @Override
  protected String getDatabaseName() {
    return "reactive";
  }
}
