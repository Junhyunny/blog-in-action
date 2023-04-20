package action.in.blog.service;

import action.in.blog.store.BarStore;
import org.springframework.stereotype.Service;

@Service
public class BarService {

    private final BarStore barStore;

    public BarService(BarStore barStore) {
        this.barStore = barStore;
    }

    public void createBar() {
        barStore.createBar();
    }
}
