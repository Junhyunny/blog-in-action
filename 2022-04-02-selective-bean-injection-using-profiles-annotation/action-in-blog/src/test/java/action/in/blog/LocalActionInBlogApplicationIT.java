package action.in.blog;

import action.in.blog.proxy.LocalPokemonProxy;
import action.in.blog.proxy.PokemonProxy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;

@ActiveProfiles("local")
@SpringBootTest
class LocalActionInBlogApplicationIT {

    @Autowired
    PokemonProxy pokemonProxy;

    @Test
    void contextLoads() {
        assertThat(pokemonProxy, instanceOf(LocalPokemonProxy.class));
    }

}
