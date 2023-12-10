package _11._2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.RecursiveAction;

public class SiteHierarchyBuilder extends RecursiveAction {
    private final SiteHierarchy siteHierarchy;
    private static final CopyOnWriteArrayList linksPool = new CopyOnWriteArrayList();

    public SiteHierarchyBuilder(SiteHierarchy siteHierarchy) {
        this.siteHierarchy = siteHierarchy;
    }

    @Override
    protected void compute() {
        linksPool.add(siteHierarchy.getUrl());

        ConcurrentSkipListSet<String> links = ParseHTML.getLinks(siteHierarchy.getUrl());
        for (String link : links) {
            if (!linksPool.contains(link)) {
                linksPool.add(link);
                siteHierarchy.addChildren(new SiteHierarchy(link));
            }
        }

        List<SiteHierarchyBuilder> taskList = new ArrayList<>();
        for (SiteHierarchy child : siteHierarchy.getSiteMapChildren()) {
            SiteHierarchyBuilder task = new SiteHierarchyBuilder(child);
            task.fork();
            taskList.add(task);
        }
        for (SiteHierarchyBuilder task : taskList) {
            task.join();
        }
    }
}
