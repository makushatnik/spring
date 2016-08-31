package com.cdesign.spittr.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.inject.Inject;
import java.util.Arrays;

/**
 * Created by Ageev Evgeny on 30.08.2016.
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.cdesign.spittr.data")
public class MongoConfig extends AbstractMongoConfiguration {

    @Inject
    private Environment env;

    @Override
    protected String getDatabaseName() {
        return ConstantManager.MONGODB_NAME;
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoCredential credential = MongoCredential.createMongoCRCredential(
                env.getProperty("mongo.username"),
                ConstantManager.MONGODB_NAME,
                env.getProperty("mongo.password").toCharArray());
        return new MongoClient(
                new ServerAddress("localhost", 27017),
                Arrays.asList(credential)
        );
    }
}
