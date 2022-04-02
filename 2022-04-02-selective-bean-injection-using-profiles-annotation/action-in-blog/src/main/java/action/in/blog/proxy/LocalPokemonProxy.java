package action.in.blog.proxy;

import action.in.blog.domain.PokemonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Profile("local")
@Component
public class LocalPokemonProxy implements PokemonProxy {

    private ObjectMapper objectMapper = new ObjectMapper();

    private PokemonResponse getFromJson(String fileName) {
        try (
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return objectMapper.readValue(buffer.toString(), PokemonResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PokemonResponse getPokemons() {
        return getFromJson("pokemons.json");
    }
}
