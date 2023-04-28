package persistence

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import models.Wishlist
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
// https://www.baeldung.com/jackson-yaml
class YAMlSerializer(private val file: File) : Serializer {
    override fun write(obj: Any?) {
        var mapper = ObjectMapper(YAMLFactory()) // Enable YAML parsing
        mapper.registerModule(KotlinModule()) // Enable Kotlin support
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        // mapper = ObjectMapper(YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER))
        mapper.writeValue(file, obj)
    }

    override fun read(): Any? {
        val path = Paths.get(file.path)
        val mapper = ObjectMapper(YAMLFactory()) // Enable YAML parsing
        mapper.registerModule(KotlinModule()) // Enable Kotlin support
        mapper.findAndRegisterModules()
        // mapper.readValue(File("src/main/resources/orderInput.yaml"), Order::class.java)
        return Files.newBufferedReader(path).use {
            mapper.readValue(it, Wishlist::class.java)
        }
    }
}
