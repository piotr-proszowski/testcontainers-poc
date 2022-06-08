package io.github.proszkie.testcontainers.config

import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import org.springframework.beans.factory.ObjectProvider
import org.springframework.boot.autoconfigure.mongo.MongoClientFactory
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.boot.autoconfigure.mongo.MongoPropertiesClientSettingsBuilderCustomizer
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoDatabaseFactorySupport
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory
import java.util.stream.Collectors

@ConstructorBinding
@ConfigurationProperties(prefix = "db.mongodb")
data class ShardedMongoProperties(
    val shardedCluster: MongoProperties,
)

@Configuration
class ShardedMongoConfig(
    val environment: Environment,
    val properties: ShardedMongoProperties,
) {
    @Bean
    fun mongoShardTemplate(
        mongoShardFactory: MongoDatabaseFactory,
    ): MongoTemplate =
        MongoTemplate(mongoShardFactory)

    @Bean
    fun mongoShardFactory(
        mongoShardClient: MongoClient
    ): MongoDatabaseFactorySupport<*> =
        SimpleMongoClientDatabaseFactory(
            mongoShardClient,
            properties.shardedCluster.mongoClientDatabase
        )

    @Bean
    fun mongoShardClient(
        builderCustomizers: ObjectProvider<ShardMongoPropertiesClientSettingsBuilderCustomizer>
    ): MongoClient =
        MongoClientFactory(
            builderCustomizers.orderedStream().collect(Collectors.toList())
                .filterIsInstance(MongoPropertiesClientSettingsBuilderCustomizer::class.java)
        )
            .createMongoClient(mongoClientSettings())

    @Bean
    fun mongoClientSettings(): MongoClientSettings =
        MongoClientSettings.builder().build()

    @Bean
    fun mongoShardPropertiesCustomizer(): ShardMongoPropertiesClientSettingsBuilderCustomizer =
        ShardMongoPropertiesClientSettingsBuilderCustomizer(properties.shardedCluster, environment)

    class ShardMongoPropertiesClientSettingsBuilderCustomizer(
        mongoProperties: MongoProperties,
        environment: Environment
    ) : MongoPropertiesClientSettingsBuilderCustomizer(mongoProperties, environment)
}
