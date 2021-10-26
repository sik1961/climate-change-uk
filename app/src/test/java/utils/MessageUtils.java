package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class MessageUtils {
    public static Message getMessageFromFile(String template, String ... otherReplacements){
        byte[] outBytes = getMessageBodyFromFile(template, otherReplacements);

        return asMessage(outBytes);
    }

    public static byte[] getMessageBodyFromFile(String template, String[] replacements) {
        return getBytes(template, replacements);
    }

    public static String getFileContent(String template, String ... replacements){
        try {
            return new String(getBytes(template, replacements), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Should never get this as utf-8 is valid");
        }
    }

    public static byte[] getBytes(String template, String ... replacements) {
        if (!template.startsWith("src/test/resources/")){
            template = "src/test/resources/" + template;
        }

        try {
            Path path = Paths.get(template);
            byte[] bytes = Files.readAllBytes(path);

            return getBytesWithRepacements(bytes, replacements);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static byte[] getBytesWithRepacements(byte[] bytes, String ... replacementArray) {
        String replace = null;
        try {
            replace = new String(bytes, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        List<String> replacements = Arrays.asList(replacementArray);

        for (int i = 0; i < replacements.size(); i+=2) replace = replace.replace(replacements.get(i), replacements.get(i+1));

        return replace.getBytes();
    }

    public static Message asMessage(byte[] bytes) {
        MessageProperties props = new MessageProperties();
        props.setContentType("application/json");
        props.setContentEncoding("utf-8");
        return new Message(bytes, props);
    }

    public static Message asMessage(String filename, Long deliveryTag, String ... replacements){
        Message messageFromFile = getMessageFromFile(filename, replacements);
        Optional.of(deliveryTag).ifPresent(messageFromFile.getMessageProperties()::setDeliveryTag);
        return messageFromFile;
    }

    public static Message asMessage(byte[] bytes, Long deliveryTag, String ... replacements) {
        Message messageFromFile = asMessage(getBytesWithRepacements(bytes, replacements));
        MessageProperties props = messageFromFile.getMessageProperties();
        Optional.of(deliveryTag).ifPresent(props::setDeliveryTag);
        return messageFromFile;
    }

    public static <T> String toJson(T message) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(message);
    }

    public static <T> T fromJson(String message, Class<T> clazz) throws IOException {
        return getObjectMapper().readValue(message, clazz);
    }

    private static ObjectMapper mapper;
    private static ObjectMapper getObjectMapper() {
        if (mapper == null) {
            AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
            AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
            AnnotationIntrospector pair = AnnotationIntrospector.pair(introspector, secondary);
            HashMap<Class<?>, JsonDeserializer<?>> deserializers = new HashMap<>();
            mapper = Jackson2ObjectMapperBuilder
                    .json()
                    .annotationIntrospector(pair)
                    .deserializersByType(deserializers)
                    .build().findAndRegisterModules();
        }
        return mapper;
    }
}
