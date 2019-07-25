package tasker.tasker.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;

@Component
public class OricaMapperManager {

    private final MapperFactory mapperFactory;

    public OricaMapperManager() {
        this.mapperFactory = new DefaultMapperFactory.Builder().build();
    }

    public <S, D> D map(S s, Class<D> type) {
        return this.mapperFactory.getMapperFacade().map(s, type);
    }

    public <S, D> D map(S s, D type) {
        this.mapperFactory.getMapperFacade().map(s, type);

        return type;
    }
}