package blog.in.action.history;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor
@Service
public class AccessHistoryService {

    private final AccessHistoryRepository repository;

//    @Transactional
//    public void createAccessHistories(List<String> paths, String userId) {
//        try {
//            for (String path : paths) {
//                AccessHistoryEntity entity = AccessHistoryEntity.builder()
//                        .accessPath(path)
//                        .accessUserId(userId)
//                        .build();
//                repository.save(entity);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage());
//        }
//    }

    public void createAccessHistories(List<String> paths, String userId) {
        try {
            List<AccessHistoryEntity> entities = paths.stream()
                    .map(path -> AccessHistoryEntity.builder()
                            .accessPath(path)
                            .accessUserId(userId)
                            .build())
                    .collect(Collectors.toList());

            repository.saveAll(entities);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
